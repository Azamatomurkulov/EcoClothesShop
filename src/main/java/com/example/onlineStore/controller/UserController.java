package com.example.onlineStore.controller;

import com.example.onlineStore.dto.MvcDto.UserMvcDto;
import com.example.onlineStore.dto.UserDto;
import com.example.onlineStore.exceptions.UserNotFoundException;
import com.example.onlineStore.jwtUtil.JWTUtil;
import com.example.onlineStore.security.AuthenticationRequest;
import com.example.onlineStore.security.AuthenticationResponse;
import com.example.onlineStore.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.List;
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JWTUtil jwtUtil;
    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthneticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect user or password");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
    @GetMapping("/logout")
    public String logout(){
        return "logged out";
    }
    @GetMapping("/loggedOut")
    public String loggedOut(){
        return "logged out";
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public UserDto getById(@PathVariable @Min(1) Long id) throws UserNotFoundException {
        return userService.getById(id);
    }
    @GetMapping("/photo/{id}")
    public UserMvcDto getByIdWithPhoto(@PathVariable Long id) throws UserNotFoundException {
        return userService.getByIdWithPhoto(id);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('admin:update')")
    public List<UserDto> getAll() throws UserNotFoundException {
        return userService.getAll();
    }

    @GetMapping("/allWithImage")
    public List<UserMvcDto> getAllWithImage() throws UserNotFoundException {
        return userService.getAllMvc();
    }
    @PostMapping("/create")
    public UserDto addNewUser(@RequestBody UserDto dto) throws UserNotFoundException {
        return userService.create(dto);
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public UserDto updateUser(@PathVariable Long id,
                              @RequestBody UserDto dto) throws UserNotFoundException {
        return userService.update(id,dto);
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public String deleteById(@PathVariable Long id) throws UserNotFoundException {
        return userService.deleteById(id);
    }

    @PutMapping("/newPassword/{id}/{token}")
    public String newPassword(@PathVariable Long id,
                              @PathVariable String token,
                              @RequestParam("password") String password) throws UserNotFoundException {
        return userService.setNewPassword(id,token,password);
    }
    @PutMapping("/resetPassword")
    public String resetPassword(@RequestParam("email") String email) throws UserNotFoundException {
        return userService.resetPassword(email);
    }
    @PostMapping("/upload")
    public String uploadImage(@RequestParam("userId") Long userId,
                              @RequestParam("imageFile") MultipartFile file) throws UserNotFoundException {
        return userService.uploadImage(userId, file);
    }


    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageById(@PathVariable("id") Long id) throws Exception {
        return userService.getImageById(id);
    }
    @PutMapping("/resetPasswordThruService")
    public String resetPasswordThru(@RequestParam("email") String email) throws UserNotFoundException, IOException {
        return userService.sendEmailToService(email);
    }
    @GetMapping("/oauthSuccess")
    public String oauthSuccess(){
        return "Авторизация GOOGLE для Eco Clothes Shop прошла успешно.";
    }
    @GetMapping("/oauth2-failure")
    public String oauthFailure(){
        return "Вход не выполнен.";
    }
}
