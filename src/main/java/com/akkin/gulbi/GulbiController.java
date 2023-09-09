package com.akkin.gulbi;

import com.akkin.gulbi.dto.GulbiCreateForm;
import com.akkin.gulbi.dto.GulbiCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/gulbis")
@RestController
public class GulbiController {

    private final GulbiService gulbiService;

    @PostMapping
    public GulbiCreateResponse saveGulbi(@RequestBody GulbiCreateForm form) {
        return gulbiService.createGulbi(1L, form);
    }

}
