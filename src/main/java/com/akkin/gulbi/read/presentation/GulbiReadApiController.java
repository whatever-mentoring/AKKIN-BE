package com.akkin.gulbi.read.presentation;

import com.akkin.auth.aop.AuthRequired;
import com.akkin.gulbi.read.GulbiReadService;
import com.akkin.gulbi.read.dto.GulbiReadResponses;
import com.akkin.auth.dto.AuthMember;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/gulbis")
@RestController
public class GulbiReadApiController implements GulbiReadApiControllerDocs {

    private final GulbiReadService gulbiReadService;

    @Override
    @AuthRequired
    @GetMapping
    public GulbiReadResponses getGulbis(HttpServletRequest request) {
        AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        return gulbiReadService.getGulbis(authMember.getId());
    }
}
