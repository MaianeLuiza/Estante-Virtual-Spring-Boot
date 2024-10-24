package com.nam.estante_virtual.security;

import com.nam.estante_virtual.model.Role;
import com.nam.estante_virtual.model.User;
import com.nam.estante_virtual.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
@Transactional
public class UserDetailService implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username);

        if (user != null && user.isActive()) {
            Set<GrantedAuthority> userRoles = new HashSet<GrantedAuthority>();
            for(Role role: user.getRoles()) {
                GrantedAuthority ff = new SimpleGrantedAuthority(role.getRole());
                userRoles.add(ff);
            }
            return new org.springframework.security.core.userdetails.User(
              user.getLogin(),
              user.getPassword(),
                    userRoles
            );
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

    }
}
