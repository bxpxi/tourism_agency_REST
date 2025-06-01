package notification;

import model.Flight;

public interface AgencyNotificationService {
    public void flightUpdated(Flight[] flights);
}
