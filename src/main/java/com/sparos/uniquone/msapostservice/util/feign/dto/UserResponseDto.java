package com.sparos.uniquone.msapostservice.util.feign.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponseDto {

    private Long userId;
    private String nickname;

}