package com.akkin.gulbi;

import com.akkin.auth.AuthRequired;
import com.akkin.gulbi.dto.GulbiCreateForm;
import com.akkin.gulbi.dto.GulbiCreateResponse;
import com.akkin.gulbi.dto.GulbiCreateResponses;
import com.akkin.gulbi.dto.GulbiUpdateForm;
import com.akkin.gulbi.dto.GulbiUpdateResponse;
import com.akkin.gulbi.service.GulbiDeleteService;
import com.akkin.gulbi.service.GulbiUpdateService;
import com.akkin.login.dto.AuthMember;
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
public class GulbiController {

    private final GulbiService gulbiService;
    private final GulbiDeleteService gulbiDeleteService;
    private final GulbiUpdateService gulbiUpdateService;

    @AuthRequired
    @PostMapping
    public GulbiCreateResponse saveGulbi(@RequestBody GulbiCreateForm form, HttpServletRequest request) {
        AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        return gulbiService.createGulbi(authMember.getId(), form);
    }

    @AuthRequired
    @GetMapping
    public GulbiCreateResponses getGulbis(HttpServletRequest request) {
        AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        return gulbiService.getGulbis(authMember.getId());
    }

    @AuthRequired
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGulbi(HttpServletRequest request, @PathVariable("id") Long gulbiId) {
        AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        gulbiDeleteService.deleteGulbi(authMember.getId(), gulbiId);
        return ResponseEntity.ok().build();
    }

    @AuthRequired
    @PatchMapping("/{id}")
    public GulbiUpdateResponse updateGulbi(HttpServletRequest request, @PathVariable("id") Long gulbiId, @RequestBody GulbiUpdateForm form) {
        AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        GulbiUpdateResponse gulbiUpdateResponse = gulbiUpdateService.updateGulbi(authMember.getId(), gulbiId, form);
        return gulbiUpdateResponse;
    }

}
