package dev.yoon.gridgetest.domain.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class LogController {

    @GetMapping
    public void test() {
        log.info("test message");
        log.error("test message");
        log.trace("test message");
        log.debug("test message");
        log.warn("test message");


    }
}
