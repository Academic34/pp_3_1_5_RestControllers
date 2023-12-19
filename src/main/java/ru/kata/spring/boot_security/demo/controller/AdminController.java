package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
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
    public String showAllUsers(Model model) {
        model.addAttribute("listUsers", userService.getAllUsers());
        return "usersAll";
    }

    @GetMapping("/new")
    public String openTheNewUserCreationView(Model model){
        model.addAttribute("allRoles", roleService.getAllRole());
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("/saveUser")
    public String saveNewUser(@ModelAttribute("user") User user,
                              @RequestParam(value = "roles") String[] selectRoles) {
        userService.addUser(user, selectRoles);
        return "redirect:/admin/";
    }

    @GetMapping("/edit")
    public String showUserInEditMode(Model model, @RequestParam("id") long id) {
        model.addAttribute("allRoles", roleService.getAllRole());
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "roles") String[] selectRoles){
        userService.updateUser(user, selectRoles);
        return "redirect:/admin/";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.removeUser(id);
        return "redirect:/admin/";
    }
}
