package com.akkin.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage {

    private String title;       // "You do not have enough credit.",
    private String detail;      // "Your current balance is 30, but that costs 50.",
    private String instance;    // /account/12345/msgs/abc",
}
