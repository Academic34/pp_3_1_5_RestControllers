package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String showAllUsers(Model model) {
        model.addAttribute("listUsers", userService.getAllUsers());
        return "usersAll";
    }

    @GetMapping("/edit")
    public String showUserInEditMode(Model model, @RequestParam("id") long id) {
        model.addAttribute("allRoles", roleService.getAllRole());
        model.addAttribute("user", userService.getUserById(id));
        System.out.println(roleService.getAllRole());
        return "edit";
    }

//    @PostMapping("/updateUser")
//    public String updateUser(@ModelAttribute("user") User user) {
//        System.out.println("We were");
//        userService.updateUser(user);
//        return "redirect:/admin";
//    }

    @PostMapping("/edit/{id}")
    public String updateUser(@ModelAttribute User user, @PathVariable("id") long id){
        userService.updateUser(user);
        return "redirect:/admin/";
    }



}
