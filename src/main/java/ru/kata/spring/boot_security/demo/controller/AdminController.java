package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> showAllUsers() {
//        model.addAttribute("listUsers", userService.getAllUsers());
//        model.addAttribute("allRoles", roleService.getAllRole());
//        model.addAttribute("principalUser", userService.getUserByName(principalUser.getEmail()));
//        model.addAttribute("newUser", new User());
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/showInfoAboutAdmin")
    public ResponseEntity<User> showAllUsers(@AuthenticationPrincipal User principalUser) {
        return new ResponseEntity<>(userService.getUserByName(principalUser.getEmail()), HttpStatus.OK);
    }

    @GetMapping("/editUser/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping("/newUser")
    public ResponseEntity<HttpStatus> saveNewUser(@RequestBody User user) {
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/editUser")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userService.removeUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PostMapping("/updateUser")
//    public String updateUser(@ModelAttribute("user") User user,
//                             @RequestParam(value = "roles") String[] selectRoles){
//        userService.updateUser(user, selectRoles);
//        return "redirect:/admin/";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteUser(@PathVariable("id") Long id) {
//        userService.removeUser(id);
//        return "redirect:/admin/";
//    }
}
