package com.cricket.User.Application.Controller;

import com.cricket.User.Application.Model.User;
import com.cricket.User.Application.Model.UserLogin;
import com.cricket.User.Application.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/registerUser")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        return userService.registerUser(user, bindingResult);
    }
    @PostMapping("/loginUser")
    public ResponseEntity<Object> loginUser(@RequestBody UserLogin userLogin) {
        return userService.loginUser(userLogin);
    }
    @GetMapping("/getTeamPlayers")
    public List<User> getTeamPlayers(@RequestParam String teamName) {
        return userService.getTeamPlayers(teamName);
    }
    @GetMapping("/getUser")
    public User getUser(@RequestParam String userName) {
        return userService.getUser(userName);
    }
    @PutMapping("/updateUser")
    public ResponseEntity<Object> updateUserPassword(@RequestParam String userName, @RequestParam String newPassword) {
        return userService.updateUserPassword(userName, newPassword);
    }
    @DeleteMapping("/deleteUser")
    public ResponseEntity<Object> deleteUser(@RequestParam String userName) {
        return userService.deleteUser(userName);
    }
}
