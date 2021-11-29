package com.example.Authentication.api;

import com.example.Authentication.domain.Role;
import com.example.Authentication.domain.User;
import com.example.Authentication.dto.AuthTokenDto;
import com.example.Authentication.dto.LoginUserDto;
import com.example.Authentication.dto.RoleToUser;
import com.example.Authentication.security.TokenProvider;
import com.example.Authentication.security.UnauthorizedEntryPoint;
import com.example.Authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final TokenProvider jwtTokenUtil;


    public UserController(UserService userService, AuthenticationManager authenticationManager, TokenProvider jwtTokenUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }
    @GetMapping("list")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("save")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/v1/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }
    @PostMapping("role/save")
    public ResponseEntity<Role> saveUser(@RequestBody Role role){
        return ResponseEntity.ok().body(userService.saveRole(role));
    }
    @PostMapping("addToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUser form){
        userService.addRoleToUser(form.getUserName(),form.getRoleName());
        return ResponseEntity.ok().build();
    }
    @PostMapping("authenticate")
    public ResponseEntity<?> generateToken(@RequestBody LoginUserDto loginUserDto) throws Exception {

        User user = userService.getUser(loginUserDto.getUsername());
        if(user == null){
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        else if(!user.getPassword().equals(loginUserDto.getPassword())){
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
        for(Role role : user.getRoles()){
            updatedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        UsernamePasswordAuthenticationToken authentication  = new UsernamePasswordAuthenticationToken(user.getUsername(),
                user.getPassword(), updatedAuthorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthTokenDto(token));
    }
}
