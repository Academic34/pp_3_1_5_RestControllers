package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.security.Principal;


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
    public String showAllUsers(Model model, @AuthenticationPrincipal User principalUser) {
        model.addAttribute("listUsers", userService.getAllUsers());
        model.addAttribute("allRoles", roleService.getAllRole());
        model.addAttribute("principalUser", userService.getUserByName(principalUser.getEmail()));
        model.addAttribute("newUser", new User());
        return "usersAll";
    }

    @PostMapping()
    public String saveNewUser(@ModelAttribute("user") User user,
                              @RequestParam(value = "roles") String[] selectRoles) {
        userService.addUser(user, selectRoles);
        return "redirect:/admin/";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "roles") String[] selectRoles){
        userService.updateUser(user, selectRoles);
        return "redirect:/admin/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.removeUser(id);
        return "redirect:/admin/";
    }
}
