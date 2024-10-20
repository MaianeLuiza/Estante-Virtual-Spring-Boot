package com.nam.estante_virtual.controller;

import com.nam.estante_virtual.model.Role;
import com.nam.estante_virtual.model.User;
import com.nam.estante_virtual.repository.RoleRepository;
import com.nam.estante_virtual.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/new")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "/public-new-user";
    }

    @PostMapping("/save")
    public String saveUser(@Valid User user, BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()){
            return "/public-new-user";
        }

        User usr = userRepository.findByLogin(user.getLogin());
        if (usr != null){
            model.addAttribute("loginExists", "Login já cadastrado");
            return "/public-new-user";
        }

        Role role = roleRepository.findByRole("USER");
        List<Role> roles = new ArrayList<Role>();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);
        attributes.addFlashAttribute("message", "Usuário salvo com sucesso!");
        return "redirect:/user/new";
    }

    @RequestMapping("/admin/list")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/auth/admin/admin-list-users";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model){
        User user = userRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("Id inválido: " + id));
        userRepository.delete(user);
        return "redirect:/user/admin/list";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") long id, Model model){
        Optional<User> userOld = userRepository.findById(id);
        if (!userOld.isPresent()) {
            throw new IllegalArgumentException("Usuário inválido:" + id);
        }
        User user= userOld.get();
        model.addAttribute("user", user);
        return "auth/user/user-edit-user";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") long id, @Valid User user, BindingResult result){
        if (result.hasErrors()) {
            user.setId(id);
            return "auth/user/user-edit-user";
        }
        userRepository.save(user);
        return "redirect:/user/admin/list";
    }

    @GetMapping("/editRole/{id}")
    public String selectRole(@PathVariable("id") long id, Model model){
        Optional<User> userOld = userRepository.findById(id);
        if (!userOld.isPresent()){
            throw new IllegalArgumentException("Usuário inválido: " + id);
        }
        User user = userOld.get();
        model.addAttribute("user", user);

        model.addAttribute("listRoles", roleRepository.findAll());

        return "/auth/admin/admin-edit-role-user";
    }

}
