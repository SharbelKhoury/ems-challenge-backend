package tech.getarrays.employeemanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.getarrays.employeemanager.model.Timesheet;

import java.util.Optional;

public interface TimesheetRepo extends JpaRepository<Timesheet, Long> {
    void deleteTimesheetById(Long id);

    Optional<Timesheet> findTimesheetById(Long id);
}
