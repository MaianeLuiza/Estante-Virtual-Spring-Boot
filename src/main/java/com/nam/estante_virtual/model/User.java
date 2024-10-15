package com.nam.estante_virtual.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_entity")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 4, message = "O nome deve ter no mínimo 4 caracteres")
    private String name;

    @CPF(message = "CPF inválido")
    private String cpf;

    @Basic
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    @Email(message = "Email inválido")
    private String email;

    @NotEmpty(message = "A senha deve ser informada")
    @Size(min = 7, message = "A senha deve ter no mínimo 7 caracteres")
    private String password;

    @NotEmpty(message = "O login deve ser informada")
    @Size(min = 4, message = "O login deve ter no mínimo 4 caracteres")
    private String login;

    private boolean active;

}
