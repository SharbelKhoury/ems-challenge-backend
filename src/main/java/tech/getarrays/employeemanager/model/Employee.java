package tech.getarrays.employeemanager.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@Entity
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String name;
    private String email;
    private String jobTitle;
    private String phone;
    private LocalDate date_of_birth;
    private String department;
    private int salary;
    private LocalDate start_date;
    private LocalDate end_date;
    private String employee_photo;
    private String employee_CV;
    private String employee_id_card_photo;
    private Boolean clean_criminal_record;
    private Boolean employee_over_18;
    private Boolean salary_above_min_wage;
    private Boolean employee_id_uploaded;
    public int min_wage = 800;
    
    @Column(nullable = false, updatable = false)
    private String employeeCode;

    public Employee() {}

    public Employee(String name, String email, String jobTitle, String phone, LocalDate date_of_birth,
			String department, int salary, LocalDate start_date, LocalDate end_date, String employee_photo, String employee_CV,
			String employee_id_card_photo, String employeeCode, Boolean clean_criminal_record) {
    	additionalChecks(date_of_birth, salary, employee_id_card_photo);
		/*this.id = id;*/
		this.name = name;
		this.email = email;
		this.jobTitle = jobTitle;
		this.phone = phone;
		this.date_of_birth = date_of_birth;
		this.department = department;
		this.salary = salary;
		this.start_date = start_date;
		this.end_date = end_date;
		this.employee_photo = employee_photo;
		this.employee_CV = employee_CV;
		this.employee_id_card_photo = employee_id_card_photo;
		this.employeeCode = employeeCode;
		this.clean_criminal_record = clean_criminal_record;
    }
    
    public void additionalChecks(LocalDate date_of_birth, int salary,String employee_id_card_photo) {
    	LocalDate today = LocalDate.now();
		Period p = Period.between(date_of_birth, today);
    	if(p.getYears() > 18) {this.setEmployee_over_18(true);}else this.setEmployee_over_18(false);
		if(salary>this.min_wage) {this.setSalary_above_min_wage(true);}else{this.setSalary_above_min_wage(false);}
		if(employee_id_card_photo != null || this.employee_id_card_photo != "") {this.setEmployee_id_uploaded(true);}else this.setEmployee_id_uploaded(false);
    }


	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getDate_of_birth() {
		return date_of_birth;
	}

	public void setDate_of_birth(LocalDate date_of_birth) {
		this.date_of_birth = date_of_birth;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	public Boolean getClean_criminal_record() {
		return clean_criminal_record;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
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

	public String getemployee_photo() {
		return employee_photo;
	}

	public void setemployee_photo(String employee_photo) {
		this.employee_photo = employee_photo;
	}

	public String getEmployee_CV() {
		return employee_CV;
	}

	public void setEmployee_CV(String employee_CV) {
		this.employee_CV = employee_CV;
	}

	public String getEmployee_id_card_photo() {
		return employee_id_card_photo;
	}

	public void setEmployee_id_card_photo(String employee_id_card_photo) {
		this.employee_id_card_photo = employee_id_card_photo;
	}

	public Boolean getEmployee_over_18() {
		return employee_over_18;
	}

	public void setEmployee_over_18(Boolean employee_over_18) {
		this.employee_over_18 = employee_over_18;
	}

	public Boolean getSalary_above_min_wage() {
		return salary_above_min_wage;
	}

	public void setSalary_above_min_wage(Boolean salary_above_min_wage) {
		this.salary_above_min_wage = salary_above_min_wage;
	}

	public Boolean getEmployee_id_uploaded() {
		return employee_id_uploaded;
	}

	public void setEmployee_id_uploaded(Boolean employee_id_uploaded) {
		this.employee_id_uploaded = employee_id_uploaded;
	}

	public int getMin_wage() {
		return min_wage;
	}

	public void setMin_wage(int min_wage) {
		this.min_wage = min_wage;
	}

	public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    @Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", email=" + email + ", jobTitle=" + jobTitle + ", phone="
				+ phone + ", date_of_birth=" + date_of_birth + ", department=" + department + ", salary=" + salary
				+ ", start_date=" + start_date + ", end_date=" + end_date + ", employee_photo=" + employee_photo
				+ ", employee_CV=" + employee_CV + ", employee_id_card_photo=" + employee_id_card_photo
				+ ", employee_over_18=" + employee_over_18 + ", salary_above_min_wage=" + salary_above_min_wage
				+ ", employee_id_uploaded=" + employee_id_uploaded + ", employeeCode=" + employeeCode + "]";
	}

	public void setClean_criminal_record(Boolean clean_criminal_record) {
		this.clean_criminal_record = clean_criminal_record;
	}
}
