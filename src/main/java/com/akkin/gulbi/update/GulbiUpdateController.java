package com.akkin.gulbi.update;

import com.akkin.auth.aop.AuthRequired;
import com.akkin.gulbi.update.dto.GulbiUpdateForm;
import com.akkin.gulbi.update.dto.GulbiUpdateResponse;
import com.akkin.auth.dto.AuthMember;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/gulbis")
@RestController
public class GulbiUpdateController implements GulbiUpdateControllerDocs {

    private final GulbiUpdateService gulbiUpdateService;

    @Override
    @AuthRequired
    @PatchMapping("/{id}")
    public GulbiUpdateResponse updateGulbi(HttpServletRequest request,
        @PathVariable("id") Long gulbiId, @RequestBody GulbiUpdateForm form) {
        AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        GulbiUpdateResponse gulbiUpdateResponse = gulbiUpdateService.updateGulbi(authMember.getId(),
            gulbiId, form);
        return gulbiUpdateResponse;
    }
}
