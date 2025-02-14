package knu.discord.notice.controller.dto;

import lombok.Builder;

public class NoticeResponse {

    @Builder
    public record Link(
            String link
    ) {

        public static NoticeResponse.Link from(String link) {
            return Link.builder()
                    .link(link)
                    .build();
        }
    }
}
