package knu.discord.notice;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "discord")
public record WebhookUrlProperties(
        String comUrl,
        String clsUrl,
        String clgUrl,
        String jobUrl,
        String schUrl,
        String evtUrl,
        String etcUrl,
        String weeklyUrl
) {
}
