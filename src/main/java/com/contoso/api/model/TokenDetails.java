package com.contoso.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDetails {
    private UUID userId;
    private String userName;
    private String email;
    private Boolean isTokenExpired;
}
