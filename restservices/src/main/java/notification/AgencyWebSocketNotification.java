package notification;

import model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import websockets.AgencyWebsocketHandler;

@Component
public class AgencyWebSocketNotification implements AgencyNotificationService {
    @Autowired
    private AgencyWebsocketHandler websocketHandler;

    public AgencyWebSocketNotification() { System.out.println("Creating AgencyWebSocketNotification"); }

    @Override
    public void flightUpdated(Flight[] flights) { websocketHandler.sendFlighgtsAll(flights);}
}
