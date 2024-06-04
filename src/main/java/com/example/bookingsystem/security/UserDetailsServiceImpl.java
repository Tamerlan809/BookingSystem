package com.example.bookingsystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new ConcreteUserDetails(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        //Tar bort rollen först
        user.getRoles().clear();

        userRepository.save(user);
        userRepository.delete(user);
    }

    public void saveUserWithRoles(User user, List<UUID> roleIds) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        List<Role> roles = roleRepository.findAllById(roleIds);

        List<Role> currentRoles = user.getRoles();
        if (currentRoles == null) {
            currentRoles = new ArrayList<>();
            user.setRoles(currentRoles);
        }


        for (Role role : roles) {
            if (!currentRoles.contains(role)) {
                currentRoles.add(role);
            }
        }
        userRepository.save(user);
    }
}