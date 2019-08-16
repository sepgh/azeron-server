package io.pinect.azeron.server.controller;

import io.pinect.azeron.server.config.properties.AzeronServerNatsProperties;
import io.pinect.azeron.server.domain.dto.SeenDto;
import io.pinect.azeron.server.domain.dto.SeenResponseDto;
import io.pinect.azeron.server.service.SeenService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/v1/api")
@Log4j2
public class ApiController {
    private final AzeronServerNatsProperties azeronServerNatsProperties;
    private final SeenService seenService;

    @Autowired
    public ApiController(AzeronServerNatsProperties azeronServerNatsProperties, SeenService seenService) {
        this.azeronServerNatsProperties = azeronServerNatsProperties;
        this.seenService = seenService;
    }

    @GetMapping("/nats")
    public @ResponseBody AzeronServerNatsProperties getNatsDetails(HttpServletRequest httpServletRequest){
        log.trace("Nats fetch request from " + httpServletRequest.getRemoteAddr());
        return azeronServerNatsProperties;
    }

    @GetMapping("/ping")
    public @ResponseBody String ping(HttpServletRequest httpServletRequest){
        log.trace("Ping request from " + httpServletRequest.getRemoteAddr());
        return "pong";
    }

    @PutMapping("/seen")
    public @ResponseBody
    SeenResponseDto seenMessages(@Valid @RequestBody SeenDto seenDto){
        return seenService.seen(seenDto);
    }
}
