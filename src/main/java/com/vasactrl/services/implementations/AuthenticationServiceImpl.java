package com.vasactrl.services.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mangut.basedomain.dtos.EmailEvent;
import com.mangut.basedomain.dtos.Receiver;
import com.vasactrl.dtos.*;
import com.vasactrl.kafka.AddUserProducer;
import com.vasactrl.models.User;
import com.vasactrl.repositories.UserRepository;
import com.vasactrl.services.interfaces.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Collection;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private static final String AES_ALGORITHM = "AES";
    private static final String SECRET_KEY = "7D+jDrNuzuTZQfix";

    private final UserRepository repository;
    private final JwtService jwtService;
    private final AddUserProducer producer;
    @Autowired
    private ReactiveUserDetailsService userDetailsService;

    @Override
    public Mono<User> create(RegisterRequest registerRequest){

        String password = generateRandomPassword(8);

        var user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .role(registerRequest.getRole())
                .branchId(registerRequest.getBranchId())
                .departmentId(registerRequest.getDepartmentId())
                .password(passwordEncoder.encode(password))
                .build();



        return repository.save(user)
                .doOnSuccess(u->{
                    u.setPassword(password);
                    this.sendRegistrationEmail(u);
                });

    }

    @Override
    public Flux<User> all() {
        return repository.findAll();
    }

    @Override
    public Mono<ResponseEntity<AuthenticationResponse<String>>> authenticate(AuthenticationRequest authenticationRequest)
        throws ExpiredJwtException, MalformedJwtException {

        try {
            Mono<UserDetails> foundUser = userDetailsService.findByUsername(authenticationRequest.getEmail()).defaultIfEmpty(new UserDetails() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return null;
                }

                @Override
                public String getPassword() {
                    return null;
                }

                @Override
                public String getUsername() {
                    return null;
                }

                @Override
                public boolean isAccountNonExpired() {
                    return false;
                }

                @Override
                public boolean isAccountNonLocked() {
                    return false;
                }

                @Override
                public boolean isCredentialsNonExpired() {
                    return false;
                }

                @Override
                public boolean isEnabled() {
                    return false;
                }
            });


            return foundUser.map(user -> {
                if (user.getUsername() == null) {
                    return ResponseEntity.status(404).body(
                            new AuthenticationResponse<>("User not found", "")
                    );
                }
                if (passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
                    UserDTO userDto = convertUserToDto((User) user);
                    return ResponseEntity.status(HttpStatus.OK).body(
                            new AuthenticationResponse<>(convertObjectToJson(userDto), jwtService.generateToken(user.getUsername()))
                    );
                }
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        new AuthenticationResponse<>("", "Invalid Credentials")
                );
            });
        } catch (MalformedJwtException e) {
            throw new MalformedJwtException(e.getMessage());
        }


    }

    //    These methods are just to handle the user dto and convert it to presentable json which is sent in the response
    private UserDTO convertUserToDto(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        userDto.setDepartmentId(user.getDepartmentId());
        userDto.setBranchId(user.getBranchId());
        return userDto;
    }

    private String convertObjectToJson(UserDTO userDto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(userDto);
        } catch (JsonProcessingException e) {
            // Handle the exception as needed
            return e.getMessage();
        }
    }

    private void sendRegistrationEmail(User user) {
        try {
            EmailEvent emailEvent = new EmailEvent();
            emailEvent.setMessage("Hello, welcome to light OmniOffice Pro. Please find your initial login credentials.");
            emailEvent.setStatus("Active");

            Receiver receiver = new Receiver();
            receiver.setId(user.getId());
            receiver.setPassword(encrypt(user.getPassword()));
            receiver.setEmail(user.getEmail());
            receiver.setName(user.getName());
            emailEvent.setReceiver(receiver);

            producer.sendEmail(emailEvent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String encrypt(String password) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private static String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&()=";
        StringBuilder password = new StringBuilder();

        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            password.append(characters.charAt(randomIndex));
        }

        return password.toString();
    }


}
