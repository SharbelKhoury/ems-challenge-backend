package tech.getarrays.employeemanager.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tech.getarrays.employeemanager.model.Employee;
import tech.getarrays.employeemanager.model.Timesheet;
import tech.getarrays.employeemanager.service.EmployeeService;
import tech.getarrays.employeemanager.service.TimesheetService;

import java.util.List;

@RestController
@RequestMapping("/timesheets")
public class TimesheetResource {
    private final TimesheetService timesheetService;
    private final EmployeeService employeeService;

    public TimesheetResource(TimesheetService timesheetService,EmployeeService employeeService) {
        this.timesheetService = timesheetService;
        this.employeeService = employeeService;
    }
                                                                                                                                                                                                                                                                                                                                
    @GetMapping("/all")
    public ResponseEntity<List<Timesheet>> getAllTimesheets () {
        List<Timesheet> timesheets = timesheetService.findAllTimesheets();
        return new ResponseEntity<>(timesheets, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Timesheet> getTimesheetById (@PathVariable("id") Long id) {
    	Timesheet timesheet = timesheetService.findTimesheetById(id);
        return new ResponseEntity<>(timesheet, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Timesheet> addTimesheet(@RequestBody Timesheet timesheet) {
        Timesheet newTimesheet = timesheetService.addTimesheet(timesheet);
        return new ResponseEntity<>(newTimesheet, HttpStatus.CREATED);
    }
    
    @PutMapping("/update")
    public ResponseEntity<Timesheet> updateTimesheet(@RequestBody Timesheet timesheet) {
        try {
            // Log the incoming timesheet for debugging
            System.out.println("Received timesheet for update: " + timesheet);

            // Check if the employee object is provided and has a valid ID
            if (timesheet.getEmployee() == null || timesheet.getEmployee().getId() == null) {
                return ResponseEntity.badRequest().body(null); // Handle case where employee is not provided
            }

            // Fetch the employee based on the employee_id
            Employee employee = employeeService.findEmployeeById(timesheet.getEmployee().getId());
            if (employee == null) {
                return ResponseEntity.badRequest().body(null); // Handle case where employee is not found
            }

            // Set the employee in the timesheet
            timesheet.setEmployee(employee);

            // Update the timesheet
            Timesheet updatedTimesheet = timesheetService.updateTimesheet(timesheet);
            return new ResponseEntity<>(updatedTimesheet, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception for debugging
            System.err.println("Error updating timesheet: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> getTimesheetCount() {
        long count = timesheetService.getTimesheetCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTimesheet(@PathVariable("id") Long id) {
    	timesheetService.deleteTimesheet(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}