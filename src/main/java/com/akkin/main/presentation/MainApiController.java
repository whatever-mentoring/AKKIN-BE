package com.akkin.main.presentation;

import static com.akkin.gulbi.application.GulbiService.DEFAULT_GULBI_PAGE_SIZE;

import com.akkin.auth.aop.AuthRequired;
import com.akkin.auth.dto.AuthMember;
import com.akkin.gulbi.application.GulbiService;
import com.akkin.gulbi.dto.response.GulbiListResponse;
import com.akkin.main.application.MainService;
import com.akkin.main.dto.response.MainResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MainApiController implements MainApiControllerDocs {

    private final MainService mainService;

    private final GulbiService gulbiService;

    @Override
    @AuthRequired
    @GetMapping
    public ResponseEntity<MainResponse> enterMain(HttpServletRequest request) {
        final AuthMember authMember = (AuthMember) request.getAttribute("authMember");
        GulbiListResponse firstPage = gulbiService.getFirstPage(authMember.getId(), DEFAULT_GULBI_PAGE_SIZE);
        GulbiListResponse today = mainService.parseTodayGulbis(firstPage);
        MainResponse mainResponse = new MainResponse(today, firstPage);
        return ResponseEntity.ok(mainResponse);
    }
}
