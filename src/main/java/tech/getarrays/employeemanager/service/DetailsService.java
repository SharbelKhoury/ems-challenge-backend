package tech.getarrays.employeemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.getarrays.employeemanager.model.User; // Assuming you have a User model
import tech.getarrays.employeemanager.repo.UserRepository; // Assuming you have a UserRepository

@Service
public class DetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username); // Implement this method in your UserRepository
        if (user == null) {
            throw new UsernameNotFoundException("User  not found");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles((String[]) user.getRoles().toArray(new String[0])) // Assuming you have roles
                .build();
    }
}