package repository.interfaces;

import model.Employee;
import repository.IRepository;

public interface IEmployeeRepository extends IRepository<Integer, Employee> {
    Employee findByAgencyNameAndPassword(String agencyName, String password);
}