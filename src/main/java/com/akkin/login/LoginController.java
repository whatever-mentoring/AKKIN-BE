package com.akkin.login;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @GetMapping("/oauth2/apple")
    public ResponseEntity<Void> appleOauthLogin(@RequestParam("code") String code) throws Exception {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/demo/{id}")
    public ResponseEntity<Void> demoOauthLogin(@PathVariable("id") Integer id) throws Exception {
        return ResponseEntity.ok().build();
    }
}
