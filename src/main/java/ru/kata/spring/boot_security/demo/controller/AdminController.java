package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.*;

@Controller
public class AdminController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/admin")
    public ModelAndView listOfUsers(ModelAndView modelAndView) {
        modelAndView.setViewName("admin");
        modelAndView.addObject("listOfUsers", userService.listOfUsers());
        User newUser = new User();
        modelAndView.addObject("newUser", newUser);
        return modelAndView;
    }

    @PostMapping(value = "/admin/user-create")
    public String createUser(@ModelAttribute User newUser, @RequestParam("NewListRoles") ArrayList<String> roles) {
        userService.save(newUser, roles);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/user-edit/{id}")
    public ModelAndView editUser(@ModelAttribute("user") User user,
                                 @RequestParam("EditListRoles") ArrayList<String> roles,
                                 ModelAndView modelAndView) {
        userService.edit(roles, user);
        modelAndView.setViewName("redirect:/admin");
        return modelAndView;
    }

    @PostMapping(value = "/admin/user-delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") Long id, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/admin");
        userService.remove(id);
        return modelAndView;
    }


}
