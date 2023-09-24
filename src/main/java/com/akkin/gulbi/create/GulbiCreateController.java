package com.akkin.gulbi.create;

import com.akkin.auth.AuthRequired;
import com.akkin.gulbi.create.dto.GulbiCreateForm;
import com.akkin.gulbi.create.dto.GulbiCreateResponse;
import com.akkin.login.dto.AuthMember;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/gulbis")
@RestController
public class GulbiCreateController implements GulbiCreateControllerDocs {

    private final GulbiCreateService gulbiCreateService;

    @Override
    @AuthRequired
    @PostMapping
    public GulbiCreateResponse saveGulbi(@RequestBody GulbiCreateForm form,
        HttpServletRequest request) {
        AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        return gulbiCreateService.createGulbi(authMember.getId(), form);
    }
}
