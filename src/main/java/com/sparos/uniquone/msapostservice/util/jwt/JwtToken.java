package com.sparos.uniquone.msapostservice.util.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class JwtToken {
    private String token;
    private String refreshToken;

    public JwtToken(String token, String refreshToken){
        this.token = token;
        this.refreshToken = refreshToken;
    }
}