package agency;

import model.Flight;
import notification.AgencyNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.interfaces.IFlightRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("agency/flights")
public class FlightsController {
    @Autowired
    private IFlightRepository flightRepository;

    @Autowired
    private AgencyNotificationService agencyNotificationService;


    @GetMapping("/test")
    public String test(@RequestParam(value = "name", defaultValue = "Hello") String name) { return name.toUpperCase(); }

    @PostMapping
    public Flight create(@RequestBody Flight flight) {
        System.out.println("Creating flight: " + flight);
        //return flightRepository.save(flight);
        Flight saved = flightRepository.save(flight);

        List<Flight> flightList = new ArrayList<>();
        flightRepository.findAll().forEach(flightList::add);
        Flight[] allFlights = flightList.toArray(new Flight[0]);

        agencyNotificationService.flightUpdated(allFlights);

        return saved;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Flight flight) {
        System.out.println("Updating flight: " + flight);
        Flight existingFlight = flightRepository.findById(flight.getId());
        if(existingFlight != null) {
            System.out.println("Updating existing flight: " + existingFlight);
            flightRepository.update(flight);

            List<Flight> flightList = new ArrayList<>();
            flightRepository.findAll().forEach(flightList::add);
            Flight[] allFlights = flightList.toArray(new Flight[0]);
            agencyNotificationService.flightUpdated(allFlights);

            return new ResponseEntity<>("Flight updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Flight not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        System.out.println("Deleting flight: " + id);
        Flight existingFlight = flightRepository.findById(id);
        if(existingFlight != null) {
            flightRepository.delete(id);

            List<Flight> flightList = new ArrayList<>();
            flightRepository.findAll().forEach(flightList::add);
            Flight[] allFlights = flightList.toArray(new Flight[0]);
            agencyNotificationService.flightUpdated(allFlights);

            return new ResponseEntity<>("Flight deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Flight not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public Collection<Flight> getAll() {
        System.out.println("Getting all flights");
        Collection<Flight> all = (Collection<Flight>) flightRepository.findAll();
        return all;
    }

    /*
    @GetMapping
    public Collection<Flight> filterByDestinationAndDepartureDate(@RequestParam String destination, @RequestParam String departureDate) {
        System.out.println("Filtering flights by destination and departure date");
        Collection<Flight> all = (Collection<Flight>) flightRepository.findByDestinationAndDepartureDate(destination, departureDate);
        return all;
    }
     */

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        System.out.println("Getting flight by id: " + id);
        Flight flight = flightRepository.findById(id);
        if (flight == null)
            return new ResponseEntity<String>("Entity not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Flight>(flight, HttpStatus.OK);
    }
}
