package tech.getarrays.employeemanager.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

import tech.getarrays.employeemanager.model.Employee;
import tech.getarrays.employeemanager.service.EmployeeService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeResource {
	
    private final String uploadDir = "C:/uploads/";
	@Autowired
    private final EmployeeService employeeService;
    private final FileStorageService fileStorageService;
    private final ResourceLoader resourceLoader;

    public EmployeeResource(EmployeeService employeeService, FileStorageService fileStorageService,ResourceLoader resourceLoader) {
        this.employeeService = employeeService;
        this.fileStorageService = fileStorageService;
        this.resourceLoader = resourceLoader;
    }
    
    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Path filePath = Paths.get("C:/uploads/" + filename);
        Resource file = resourceLoader.getResource("file:" + filePath.toString());
        
        System.out.println("Checking file at: " + filePath); // Log the file path

        if (!file.exists()) {
            System.out.println("File not found: " + filePath); // Log if the file is not found
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .body(file);
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> getEmployeeCount() {
        long count = employeeService.getEmployeeCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    /*all*/
    @GetMapping("/")
    public ResponseEntity<List<Employee>> getAllEmployees () {
        List<Employee> employees = employeeService.findAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
    /*find by id*/	
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById (@PathVariable("id") Long id) {
        Employee employee = employeeService.findEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Employee> addEmployee(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam(value = "date_of_birth", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
            @RequestParam("jobTitle") String jobTitle,
            @RequestParam("department") String department,
            @RequestParam("salary") Integer salary,
            @RequestParam("clean_criminal_record") Boolean clean_criminal_record,
            @RequestParam(value = "start_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "end_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "employee_photo", required = false) MultipartFile employeePhoto,
            @RequestParam(value = "employee_CV", required = false) MultipartFile employeeCV,
            @RequestParam(value = "employee_id_card_photo", required = false) MultipartFile employeeIdCardPhoto) {
	        // Create an Employee object from the parameters
	        Employee employee = new Employee();
	        employee.setName(name);
	        employee.setEmail(email);
	        employee.setPhone(phone);
	        employee.setDate_of_birth(dateOfBirth);
	        employee.setJobTitle(jobTitle);
	        employee.setDepartment(department);
	        employee.setSalary(salary);
	        employee.setStart_date(startDate);
	        employee.setEnd_date(endDate);
	        employee.setClean_criminal_record(clean_criminal_record);

        // Handle file uploads
        try {
            if (employeePhoto != null) {
                String photoPath = fileStorageService.saveFile(employeePhoto);
                employee.setemployee_photo(photoPath);
            }
            if (employeeCV != null) {
                String cvPath = fileStorageService.saveFile(employeeCV);
                employee.setEmployee_CV(cvPath);
            }
            if (employeeIdCardPhoto != null) {
                String idCardPath = fileStorageService.saveFile(employeeIdCardPhoto);
                employee.setEmployee_id_card_photo(idCardPath);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        // Update the employee in the database
        Employee savedEmployee = employeeService.addEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam(value = "date_of_birth", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
            @RequestParam("jobTitle") String jobTitle,
            @RequestParam("department") String department,
            @RequestParam("salary") Integer salary,
            @RequestParam("clean_criminal_record") Boolean clean_criminal_record,
            @RequestParam(value = "start_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "end_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "employee_photo", required = false) MultipartFile employeePhoto,
            @RequestParam(value = "employee_CV", required = false) MultipartFile employeeCV,
            @RequestParam(value = "employee_id_card_photo", required = false) MultipartFile employeeIdCardPhoto) {

        // Create an Employee object from the parameters
        Employee employee = new Employee();
        employee.setId(id);
        employee.setName(name);
        employee.setEmail(email);
        employee.setPhone(phone);
        employee.setDate_of_birth(dateOfBirth);
        employee.setJobTitle(jobTitle);
        employee.setDepartment(department);
        employee.setSalary(salary);
        employee.setStart_date(startDate);
        employee.setEnd_date(endDate);
        employee.setClean_criminal_record(clean_criminal_record);

        // Handle file uploads
        try {
            if (employeePhoto != null) {
                String photoPath = fileStorageService.saveFile(employeePhoto);
                employee.setemployee_photo(photoPath);
            }
            if (employeeCV != null) {
                String cvPath = fileStorageService.saveFile(employeeCV);
                employee.setEmployee_CV(cvPath);
            }
            if (employeeIdCardPhoto != null) {
                String idCardPath = fileStorageService.saveFile(employeeIdCardPhoto);
                employee.setEmployee_id_card_photo(idCardPath);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        // Update the employee in the database
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
