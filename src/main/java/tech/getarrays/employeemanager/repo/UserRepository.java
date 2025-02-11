package tech.getarrays.employeemanager.repo;

import tech.getarrays.employeemanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}