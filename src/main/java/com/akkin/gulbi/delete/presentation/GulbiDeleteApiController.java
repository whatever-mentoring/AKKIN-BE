package com.akkin.gulbi.delete.presentation;

import com.akkin.auth.aop.AuthRequired;
import com.akkin.auth.dto.AuthMember;
import com.akkin.gulbi.delete.GulbiDeleteService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/gulbis")
@RestController
public class GulbiDeleteApiController implements GulbiDeleteApiControllerDocs {

    private final GulbiDeleteService gulbiDeleteService;

    @Override
    @AuthRequired
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGulbi(@PathVariable("id") Long gulbiId,
        HttpServletRequest request) {
        AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        gulbiDeleteService.deleteGulbi(authMember.getId(), gulbiId);
        return ResponseEntity.ok().build();
    }
}
