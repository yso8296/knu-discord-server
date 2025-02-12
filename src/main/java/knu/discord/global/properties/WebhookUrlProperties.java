package knu.discord.global.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "discord")
public class WebhookUrlProperties {

    String comUrl;
    String clsUrl;
    String clgUrl;
    String jobUrl;
    String schUrl;
    String evtUrl;
    String etcUrl;
    String weeklyUrl;
}
