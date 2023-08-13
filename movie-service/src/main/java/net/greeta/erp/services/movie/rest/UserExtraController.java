package net.greeta.erp.services.movie.rest;

import net.greeta.erp.services.movie.model.UserExtra;
import net.greeta.erp.services.movie.rest.dto.UserExtraRequest;
import net.greeta.erp.services.movie.service.UserExtraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/userextras")
public class UserExtraController {

    private final UserExtraService userExtraService;

    @GetMapping("/me")
    public UserExtra getUserExtra(JwtAuthenticationToken token) {
        return userExtraService.validateAndGetUserExtra(token.getName());
    }

    @PostMapping("/me")
    public UserExtra saveUserExtra(@Valid @RequestBody UserExtraRequest updateUserExtraRequest,
                                   JwtAuthenticationToken token) {
        Optional<UserExtra> userExtraOptional = userExtraService.getUserExtra(token.getName());
        UserExtra userExtra = userExtraOptional.orElseGet(() -> new UserExtra(token.getName()));
        userExtra.setAvatar(updateUserExtraRequest.getAvatar());
        return userExtraService.saveUserExtra(userExtra);
    }
}
