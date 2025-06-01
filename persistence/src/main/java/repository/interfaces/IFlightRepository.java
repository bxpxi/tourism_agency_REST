package repository.interfaces;

import model.Flight;
import repository.IRepository;

public interface IFlightRepository extends IRepository<Integer, Flight> {
    Iterable<Flight> findByDestinationAndDepartureDate(String destination, String departureDate);
}