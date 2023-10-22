package com.akkin.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorMessage {

    private final String title;       // "You do not have enough credit.",
    private final String detail;      // "Your current balance is 30, but that costs 50.",
    private final String instance;    // /account/12345/msgs/abc",

    @Builder
    public ErrorMessage(final String title, final String detail, final String instance) {
        this.title = title;
        this.detail = detail;
        this.instance = instance;
    }
}
