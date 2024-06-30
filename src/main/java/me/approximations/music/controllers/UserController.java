package me.approximations.music.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import me.approximations.music.entities.User;
import me.approximations.music.security.entities.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value="/user")
@RestController
public class UserController {
    @Operation(summary="Return info of a user by authentication token.")
    @ApiResponses(value={
            @ApiResponse(responseCode="200", description="Valid user."),
            @ApiResponse(responseCode="403", description="Unauthorized."),
    })
    @GetMapping("/me")
    public ResponseEntity<User> me(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(userDetails.getUser());
    }

}
