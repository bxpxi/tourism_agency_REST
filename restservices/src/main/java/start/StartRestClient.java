package start;

import agency.ServiceException;
import client.NewFlightsClient;
import model.Flight;
import org.springframework.web.client.RestClientException;

public class StartRestClient {
    private final static NewFlightsClient flightsClient=new NewFlightsClient();
    public static void main(String[] args) {

        Flight flightT=new Flight("New York", "2025-08-08", "12:00", "NY Airport", 120);
        try {
            System.out.println("Adding a new flight " + flightT);
            show(() -> {
                Flight created = flightsClient.create(flightT);
                flightT.setId(created.getId());
                System.out.println(created);
            });

            System.out.println("\nUpdating the newly created flight...");
            flightT.setAvailableSeats(99);
            show(() -> {
                Flight updated = flightsClient.update(flightT);
                System.out.println("Updated flight: " + updated);
            });

            System.out.println("\nPrinting all flights ...");
            show(() -> {
                Flight[] res = flightsClient.getAll();
                for (Flight f : res) {
                    System.out.println(f.getId() + ": " + f.getDestination() + ", " + f.getDepartureDate() + ": " + f.getDepartureTime() + ": " + f.getAirport() + ": " + f.getAvailableSeats());
                }
            });
        } catch (RestClientException ex) {
            System.out.println("Exception ... " + ex.getMessage());
        }

        System.out.println("\nInfo for flight with id=95");
        show(()-> System.out.println(flightsClient.getById("95")));

        System.out.println("\nDeleting flight with id="+flightT.getId());
        show(()-> flightsClient.delete((flightT.getId()).toString()));
    }

    private static void show(Runnable task) {
        try {
            task.run();
        } catch (ServiceException e) {
            //  LOG.error("Service exception", e);
            System.out.println("Service exception"+ e);
        }
    }
}
