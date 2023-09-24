package com.akkin.gulbi.read;

import com.akkin.auth.AuthRequired;
import com.akkin.gulbi.read.dto.GulbiReadResponses;
import com.akkin.login.dto.AuthMember;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/gulbis")
@RestController
public class GulbiReadController implements GulbiReadControllerDocs {

    private final GulbiReadService gulbiReadService;

    @Override
    @AuthRequired
    @GetMapping
    public GulbiReadResponses getGulbis(HttpServletRequest request) {
        AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        return gulbiReadService.getGulbis(authMember.getId());
    }
}
