package com.cricket.User.Application.Service;

import com.cricket.User.Application.Errors.UserErrorResponse;
import com.cricket.User.Application.Model.User;
import com.cricket.User.Application.Model.UserLogin;
import com.cricket.User.Application.Model.UserResponse;
import com.cricket.User.Application.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    public static final String USER_ALREADY_EXISTS = "Username/Email already exists";
    public static final String FIX_ERROR_MESSAGE = "Fix the errors and try again";
    public static final Instant startInstant = Instant.now();
    public static final Clock clock = Clock.systemUTC();
    @Autowired
    private UserRepository userRepository;
    @Transactional
    public ResponseEntity<Object> registerUser(User user, BindingResult bindingResult) {
        UserErrorResponse userErrorResponse = new UserErrorResponse();
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(x -> x.getDefaultMessage())
                    .collect(Collectors.toList());
            userErrorResponse.setErrorMessage(errorMessages);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new UserErrorResponse(
                            UserResponse.calculateExecutionTimeInMillis(startInstant, clock),
                            Instant.now(clock).toString(),
                            FIX_ERROR_MESSAGE,
                            errorMessages
                    ));
        } else if (getUserAlreadyExists(user.getUsername(), user.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(new UserErrorResponse(
                            UserResponse.calculateExecutionTimeInMillis(startInstant, clock),
                            Instant.now(clock).toString(),
                            USER_ALREADY_EXISTS
                    ));
        } else {
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new UserResponse(
                            UserResponse.calculateExecutionTimeInMillis(startInstant, clock),
                            Instant.now(clock).toString(),
                            user.getUsername() + " registered successfully"
                    ));
        }
    }
    public boolean getUserAlreadyExists(String userName, String email) {
        return (userRepository.existsByUsername(userName) || userRepository.existsByEmail(email));
    }
    public ResponseEntity<Object> loginUser(UserLogin userLogin) {
        boolean loginUser = userRepository.existsByUsernameAndPassword(userLogin.getUsername(), userLogin.getPassword());
        return loginUser ? (ResponseEntity.status(HttpStatus.OK)
                .body(new UserResponse(
                        UserResponse.calculateExecutionTimeInMillis(startInstant, clock),
                        Instant.now(clock).toString(),
                        "Welcome " + userLogin.getUsername()))) : ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new UserErrorResponse(
                        "Inavlid Credentials. Try again!"
                ));
    }
    public List<User> getTeamPlayers (String teamName) {
        return userRepository.findByTeamName(teamName);
    }
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }
    public ResponseEntity<Object> updateUserPassword(String username, String  password) {
        User user = userRepository.findByUsername(username);
        user.setPassword(password);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new UserResponse(
                        UserResponse.calculateExecutionTimeInMillis(startInstant, clock),
                        Instant.now(clock).toString(),
                        "Password updated successfully for user " + username
                ));
    }
    public ResponseEntity<Object> deleteUser(String userName) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new UserResponse(
                        UserResponse.calculateExecutionTimeInMillis(startInstant, clock),
                        Instant.now(clock).toString(),
                        userName + " deleted successfully"
                ));
    }
}
