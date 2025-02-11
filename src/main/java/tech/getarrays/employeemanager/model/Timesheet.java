package tech.getarrays.employeemanager.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Timesheet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String timesheet_name;
    private LocalDate start_date;
    private LocalDate end_date;
    
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
    @Lob // Use @Lob for large text fields
    private String summary;

    public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Timesheet() {}

	public Timesheet(Long id, String timesheet_name, LocalDate start_date, LocalDate end_date, Employee employee) {
		super();
		this.id = id;
		this.timesheet_name = timesheet_name;
		this.start_date = start_date;
		this.end_date = end_date;
		this.employee = employee;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTimesheet_name() {
		return timesheet_name;
	}

	public void setTimesheet_name(String timesheet_name) {
		this.timesheet_name = timesheet_name;
	}

	public LocalDate getStart_date() {
		return start_date;
	}

	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}

	public LocalDate getEnd_date() {
		return end_date;
	}

	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "Timesheet [id=" + id + ", timesheet_name=" + timesheet_name + ", start_date=" + start_date
				+ ", end_date=" + end_date + ", employee=" + employee + "]";
	}
}
