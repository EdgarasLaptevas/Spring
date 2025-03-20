package techin.lt.perlaikymas.controller;

import techin.lt.perlaikymas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TokenController {

    public final UserService userService;

    private final JwtEncoder jwtEncoder;

    @Autowired
    public TokenController(UserService userService, JwtEncoder jwtEncoder) {
        this.userService = userService;
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping("/token")
    public String token(Authentication authentication) {
        Instant now = Instant.now();

        String scope = authentication.getAuthorities().stream()
                .map((s) -> s.getAuthority())
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .expiresAt(now)
                .expiresAt(now.plusSeconds(36000))
                .subject(authentication.getName())
                .claim("scope", scope)
                .claim("userId", userService.findUserByUsername(authentication.getName()).get().getId())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
