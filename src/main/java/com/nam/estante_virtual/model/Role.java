package com.nam.estante_virtual.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String role;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private List<User> users;
}
