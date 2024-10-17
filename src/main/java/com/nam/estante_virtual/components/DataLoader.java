package com.nam.estante_virtual.components;

import com.nam.estante_virtual.model.Role;
import com.nam.estante_virtual.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        String[] roles = {"ADMIN", "USER", "BIBLIOTECARIO"};

        for (String roleString: roles) {
            Role role = roleRepository.findByRole(roleString);
            if (role == null){
                role = new Role(roleString);
                roleRepository.save(role);
            }
        }
    }
}
