package com.akkin.gulbi.presentation;

import com.akkin.auth.aop.AuthRequired;
import com.akkin.auth.dto.AuthMember;
import com.akkin.gulbi.application.GulbiService;
import com.akkin.gulbi.dto.request.GulbiCreateForm;
import com.akkin.gulbi.dto.response.GulbiCreateResponse;
import com.akkin.gulbi.dto.response.GulbiReadResponses;
import com.akkin.gulbi.dto.request.GulbiUpdateForm;
import com.akkin.gulbi.dto.response.GulbiUpdateResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/gulbis")
@RestController
public class GulbiApiController implements GulbiApiControllerDocs{

    private final GulbiService gulbiService;

    @Override
    @AuthRequired
    @PostMapping
    public GulbiCreateResponse saveGulbi(@RequestBody GulbiCreateForm form, HttpServletRequest request) {
        AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        return gulbiService.createGulbi(authMember.getId(), form);
    }

    @Override
    @AuthRequired
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGulbi(@PathVariable("id") Long gulbiId,
        HttpServletRequest request) {
        AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        gulbiService.deleteGulbi(authMember.getId(), gulbiId);
        return ResponseEntity.ok().build();
    }

    @Override
    @AuthRequired
    @GetMapping
    public GulbiReadResponses getGulbis(HttpServletRequest request) {
        AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        return gulbiService.getGulbis(authMember.getId());
    }

    @Override
    @AuthRequired
    @PatchMapping("/{id}")
    public GulbiUpdateResponse updateGulbi(HttpServletRequest request,
        @PathVariable("id") Long gulbiId, @RequestBody GulbiUpdateForm form) {
        AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        GulbiUpdateResponse gulbiUpdateResponse = gulbiService.updateGulbi(authMember.getId(),
            gulbiId, form);
        return gulbiUpdateResponse;
    }
}
