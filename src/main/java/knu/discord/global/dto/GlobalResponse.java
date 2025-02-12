package knu.discord.global.dto;

import lombok.Builder;

@Builder
public record GlobalResponse(
        String message
) {
}
