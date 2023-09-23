package com.akkin.login.apple;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url = "https://appleid.apple.com")
public interface AppleClient {
}
