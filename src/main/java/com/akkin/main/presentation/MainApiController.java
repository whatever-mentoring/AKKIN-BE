package com.akkin.main.presentation;

import com.akkin.auth.aop.AuthRequired;
import com.akkin.auth.dto.AuthMember;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MainApiController implements MainApiControllerDocs {

    @Override
    @AuthRequired
    @GetMapping
    public ResponseEntity<Void> enterMain(HttpServletRequest request) {
        final AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        // 임시로 null
        return ResponseEntity.ok().build();
    }
}
