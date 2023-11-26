package com.akkin.gulbi.presentation;

import static com.akkin.gulbi.application.GulbiService.DEFAULT_GULBI_PAGE_SIZE;

import com.akkin.auth.aop.AuthRequired;
import com.akkin.auth.dto.AuthMember;
import com.akkin.gulbi.application.GulbiService;
import com.akkin.gulbi.dto.request.GulbiCreateForm;
import com.akkin.gulbi.dto.request.GulbiUpdateForm;
import com.akkin.gulbi.dto.response.GulbiListResponse;
import java.net.URI;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/gulbis")
@RestController
public class GulbiApiController implements GulbiApiControllerDocs {

    private final GulbiService gulbiService;

    @Override
    @AuthRequired
    @PostMapping
    public ResponseEntity<Void> saveGulbi(@RequestBody final GulbiCreateForm form, HttpServletRequest request) {
        final AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        gulbiService.create(authMember.getId(), form);
        return ResponseEntity.created(URI.create("/api/gulbis")).build();
    }

    @Override
    @AuthRequired
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGulbi(@PathVariable("id") final Long gulbiId, HttpServletRequest request) {
        final AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        gulbiService.delete(authMember.getId(), gulbiId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @AuthRequired
    @GetMapping
    public ResponseEntity<GulbiListResponse> getGulbis(@RequestParam(value = "lastId", required = false) Long lastId,
                                                       HttpServletRequest request) {
        final AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        if (lastId == null) {
            lastId = Long.MAX_VALUE;
        }
        GulbiListResponse response = gulbiService.getGublis(authMember.getId(), lastId, DEFAULT_GULBI_PAGE_SIZE);
        if (response.getLastId() == 0L) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @Override
    @AuthRequired
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateGulbi(@PathVariable("id") final Long gulbiId,
                                            @RequestBody final GulbiUpdateForm form,
        HttpServletRequest request) {
        final AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        gulbiService.update(authMember.getId(), gulbiId, form);
        return ResponseEntity.ok().build();
    }
}
