package repository.interfaces;

import model.Flight;
import model.Ticket;
import repository.IRepository;

public interface ITicketRepository extends IRepository<Integer, Ticket> {
    Iterable<Ticket> findByFlight(Flight flight);
}