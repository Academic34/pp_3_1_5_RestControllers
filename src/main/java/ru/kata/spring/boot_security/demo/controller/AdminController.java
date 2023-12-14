package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;


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
        model.addAttribute("principalUser", principalUser);
        return "usersAll";
    }

    @GetMapping("/new")
    public String openTheNewUserCreationView(Model model){
        model.addAttribute("allRoles", roleService.getAllRole());
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("/saveUser")
    public String saveNewUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/edit")
    public String showUserInEditMode(Model model, @RequestParam("id") long id) {
        model.addAttribute("allRoles", roleService.getAllRole());
        System.out.println(roleService.getAllRole());
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.removeUser(id);
        return "redirect:/admin/";
    }
}
