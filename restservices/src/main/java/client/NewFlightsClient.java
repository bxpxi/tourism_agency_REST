package client;

import agency.ServiceException;
import model.Flight;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.concurrent.Callable;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class NewFlightsClient {
    RestClient restClient = RestClient.builder().
            requestInterceptor(new CustomRestClientInterceptor()).
            build();

    public static final String URL = "http://localhost:8080/agency/flights";
    private <T> T execute(Callable<T> callable) {
        try {
            return callable.call();
        } catch (ResourceAccessException | HttpClientErrorException e) { // server down, resource exception
            throw new ServiceException(e);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
    public Flight[] getAll() {
        return execute(() -> restClient.get().uri(URL).retrieve().body( Flight[].class));
    }

    public Flight getById(String id) {

        return execute(() -> restClient.get(). uri(String.format("%s/%s", URL, id)).retrieve().body( Flight.class));
    }

    public Flight create(Flight flight) {
        return execute(() -> restClient.post().uri(URL).contentType(APPLICATION_JSON).body(flight).retrieve().body(Flight.class));
    }

    public void delete(String id){
        execute(() -> restClient.delete().uri(String.format("%s/%s", URL, id)).retrieve().toBodilessEntity());
    }

    public Flight update(Flight flight) {
        return execute(() ->
                restClient.put()
                        .uri(String.format("%s/%d", URL, flight.getId()))
                        .contentType(APPLICATION_JSON)
                        .body(flight)
                        .retrieve()
                        .body(Flight.class)
        );
    }

    public class CustomRestClientInterceptor
            implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(
                HttpRequest request,
                byte[] body,
                ClientHttpRequestExecution execution) throws IOException {
            System.out.println("Sending a "+request.getMethod()+ " request to "+request.getURI()+ " and body ["+new String(body)+"]");
            ClientHttpResponse response=null;
            try {
                response = execution.execute(request, body);
                System.out.println("Got response code " + response.getStatusCode());
            }catch(IOException ex){
                System.err.println("Eroare executie "+ex);
            }
            return response;
        }
    }
}
