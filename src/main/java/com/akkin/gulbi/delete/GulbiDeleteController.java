package com.akkin.gulbi.delete;

import com.akkin.auth.AuthRequired;
import com.akkin.login.dto.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RequestMapping("/api/gulbis")
@RestController
public class GulbiDeleteController {

    private final GulbiDeleteService gulbiDeleteService;


    @AuthRequired
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGulbi(HttpServletRequest request, @PathVariable("id") Long gulbiId) {
        AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        gulbiDeleteService.deleteGulbi(authMember.getId(), gulbiId);
        return ResponseEntity.ok().build();
    }
}
