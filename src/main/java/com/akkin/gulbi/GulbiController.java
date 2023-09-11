package com.akkin.gulbi;

import com.akkin.auth.AuthRequired;
import com.akkin.auth.dto.AuthToken;
import com.akkin.gulbi.dto.GulbiCreateForm;
import com.akkin.gulbi.dto.GulbiCreateResponse;
import com.akkin.gulbi.dto.GulbiCreateResponses;
import com.akkin.login.dto.AuthMember;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/gulbis")
@RestController
public class GulbiController {

    private final GulbiService gulbiService;

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

}
