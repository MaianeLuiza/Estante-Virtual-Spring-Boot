package com.nam.estante_virtual.controller;

import com.nam.estante_virtual.model.User;
import com.nam.estante_virtual.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/new")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "/public-new-user";
    }

    @PostMapping("/save")
    public String saveUser(@Valid User user, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()){
            return "/public-new-user";
        }
        userRepository.save(user);
        attributes.addFlashAttribute("message", "Usuário salvo com sucesso!");
        return "redirect:/user/new";
    }
}
