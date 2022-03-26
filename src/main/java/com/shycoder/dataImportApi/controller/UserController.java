package com.shycoder.dataImportApi.controller;

import com.shycoder.dataImportApi.entity.User;
import com.shycoder.dataImportApi.model.AuthenticationRequest;
import com.shycoder.dataImportApi.model.AuthenticationResponse;
import com.shycoder.dataImportApi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //login: validate user credentials and create token if credentials are valid.
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        if (userService.isValidCredentials(authenticationRequest.getUsername(), authenticationRequest.getPassword())) {
            final String jwt = userService.getToken(authenticationRequest.getUsername());
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
