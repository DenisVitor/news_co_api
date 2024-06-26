package com.news_co_api.session;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.news_co_api.modules.viewer.ViewerEntity;
import com.news_co_api.modules.viewer.ViewerRepository;
import com.news_co_api.security.JwtTokenProvider;

@RestController
@RequestMapping("/auth")
public class SessionController {
    @Autowired
    private ViewerRepository viewerRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder encoder;


    @CrossOrigin(origins = "https://news-co-alpha.vercel.app")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody ViewerEntity payload) {
        ViewerEntity foundViewer = viewerRepo.findByUsername(payload.getUsername()).orElse(null);
        if (foundViewer != null) {
            System.out.println(foundViewer);
            var message = new HashMap<String, String>();
            message.put("error", "Viewer with this username already exists");
            return ResponseEntity.badRequest().body(message);
        }
        var viewerBuild = ViewerEntity.builder()
                .username(payload.getUsername())
                .password(encoder.encode(payload.getPassword()))
                .email(payload.getEmail())
                .avatar(payload.getAvatar());

        if (payload.getAdmin()) {
            var adminRole = Arrays.asList("ADMIN");
            var admin = viewerBuild.roles(adminRole).build();
            var viewer = viewerRepo.save(admin);
            return ResponseEntity.status(201).body(viewer);
        }
        var commomRole = Arrays.asList("COMMON");
        var common = viewerBuild.roles(commomRole).build();
        var viewer = viewerRepo.save(common);
        return ResponseEntity.status(201).body(viewer);
    }

    @CrossOrigin(origins = "https://news-co-alpha.vercel.app")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ViewerEntity payload) throws Exception {
        var errorMsg = new HashMap<String, String>();
        errorMsg.put("Error", "Username/Password are incorrect");
        Optional<ViewerEntity> viewer = viewerRepo.findByUsername(payload.getUsername());
        if (viewer.isPresent() == false) {
            return ResponseEntity.status(400).body(errorMsg);
        }
        var samePassword = encoder.matches(payload.getPassword(), viewer.get().getPassword());
        if (!samePassword) {
            return ResponseEntity.status(400).body(errorMsg);

        }

        var authToken = new UsernamePasswordAuthenticationToken(
                payload.getUsername(), payload.getPassword());

        var authentication = authenticationManager.authenticate(authToken);

        var token = jwtTokenProvider.createToken(authentication);

        var msg = new HashMap<String, String>();
        msg.put("token", token);

        return ResponseEntity.status(200).body(msg);
    }
}
