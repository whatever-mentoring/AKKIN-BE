package com.akkin.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage {

    @Schema(description = "Exception 종류")
    private String title;       // "You do not have enough credit.",

    @Schema(description = "Exception 메시지")
    private String detail;      // "Your current balance is 30, but that costs 50.",

    @Schema(description = "Exception 발생한 api")
    private String instance;    // /account/12345/msgs/abc",

}
