package dev.yoon.gridgetest.domain.user.api;

import dev.yoon.gridgetest.domain.user.application.token.TokenService;
import dev.yoon.gridgetest.domain.user.dto.token.AccessTokenRes;
import dev.yoon.gridgetest.global.validator.TokenValidator;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/token")
public class TokenApi {

    private final TokenValidator tokenValidator;
    private final TokenService tokenService;


    @Operation(summary = "JWT 재발급")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt refresh token", dataType = "string", value = "jwt refresh token", required = true, paramType = "header")
    })
    @PostMapping("/reissue")
    public ResponseEntity<AccessTokenRes> updateAccessToken(
            HttpServletRequest request
    ) {

        String authorization = request.getHeader("Authorization");
        tokenValidator.validateAuthorization(authorization);

        String refreshToken = authorization.split(" ")[1];
        AccessTokenRes accessTokenRes = tokenService.updateAccessToken(refreshToken, LocalDateTime.now());

        return ResponseEntity.ok(accessTokenRes);
    }
}
