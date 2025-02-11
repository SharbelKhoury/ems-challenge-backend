package tech.getarrays.employeemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.getarrays.employeemanager.exception.UserNotFoundException;
import tech.getarrays.employeemanager.model.Timesheet;
import tech.getarrays.employeemanager.repo.TimesheetRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TimesheetService {
    private final TimesheetRepo timesheetRepo;

    @Autowired
    public TimesheetService(TimesheetRepo timesheetRepo) {
        this.timesheetRepo = timesheetRepo;
    }

    public Timesheet addTimesheet(Timesheet timesheet) {
        return timesheetRepo.save(timesheet);
    }

    public List<Timesheet> findAllTimesheets() {
        return timesheetRepo.findAll();
    }
    
    public Timesheet updateTimesheet(Timesheet timesheet) {
        // Check if the timesheet exists
        if (timesheet.getId() == null || !timesheetRepo.existsById(timesheet.getId())) {
            throw new IllegalArgumentException("Timesheet ID must be provided and must exist");
        }
        return timesheetRepo.save(timesheet); // This will update the existing timesheet
    }
    public long getTimesheetCount() {
        return timesheetRepo.count(); 
    }
    
    public Timesheet findTimesheetById(Long id) {
        return timesheetRepo.findTimesheetById(id)
                .orElseThrow(() -> new UserNotFoundException("User  by id " + id + " was not found"));
    }

    public void deleteTimesheet(Long id){
    	timesheetRepo.deleteTimesheetById(id);
    }
}