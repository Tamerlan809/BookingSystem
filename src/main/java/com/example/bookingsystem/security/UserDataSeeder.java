package com.example.bookingsystem.security;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDataSeeder {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public void seed() {
        if (roleRepository.findByName("Admin") == null) {
            addRole("Admin");
        }
        if (roleRepository.findByName("Receptionist") == null) {
            addRole("Receptionist");
        }
        if (userRepository.getUserByUsername("stefan.holmberg@systementor.se") == null) {
            addUser("stefan.holmberg@systementor.se", "Admin");
        }
        if (userRepository.getUserByUsername("oliver.holmberg@systementor.se") == null) {
            addUser("oliver.holmberg@systementor.se", "Receptionist");
        }
    }

    @Transactional
    public void addUser(String mail, String group) {
        Role role = roleRepository.findByName(group);
        if (role == null) {
            throw new IllegalArgumentException("Role " + group + " not found");
        }

        List<Role> roles = new ArrayList<>();
        roles.add(role);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("Hejsan123#");

        User user = User.builder()
                .enabled(true)
                .password(hash)
                .username(mail)
                .roles(roles)
                .build();

        userRepository.save(user);
    }

    @Transactional
    public void addRole(String name) {
        Role role = Role.builder().name(name).build();
        roleRepository.save(role);
    }
}
