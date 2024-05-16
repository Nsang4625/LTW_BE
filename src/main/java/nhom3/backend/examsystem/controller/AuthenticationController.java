package nhom3.backend.examsystem.controller;

import nhom3.backend.examsystem.dto.LoginResponseDto;
import nhom3.backend.examsystem.dto.RegistrationDto;
import nhom3.backend.examsystem.model.User;
import nhom3.backend.examsystem.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public User registerUser (@RequestBody RegistrationDto body
                              ){
        return authenticationService.registerUser(body.getUsername(), body.getPassword());
    }

    @PostMapping("/registerAdmin")
    public User registerAdmin(@RequestBody RegistrationDto body){
        return authenticationService.addAdmin(body.getUsername(), body.getPassword());
    }

    @PutMapping("/updatePassword")
    public Optional<User> updateUserPassword(@RequestBody RegistrationDto body){
        return authenticationService.updatePassword(body.getUsername(), body.getPassword());
    }
    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable Long id){
        authenticationService.deleteUser(id);
        return;
    }

    @PostMapping("/login")
    public LoginResponseDto loginUser(@RequestBody RegistrationDto body){
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }
}
