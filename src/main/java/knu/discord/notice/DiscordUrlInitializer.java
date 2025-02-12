package knu.discord.notice;

import jakarta.annotation.PostConstruct;
import knu.discord.global.properties.WebhookUrlProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiscordUrlInitializer {

    private final WebhookUrlProperties webhookUrlProperties;

    @PostConstruct
    public void init() {
        Category.COM.setUrl(System.getenv("COM_URL"));
        Category.CLS.setUrl(System.getenv("CLS_URL"));
        Category.CLG.setUrl(System.getenv("CLG_URL"));
        Category.JOB.setUrl(System.getenv("JOB_URL"));
        Category.SCH.setUrl(System.getenv("SCH_URL"));
        Category.EVT.setUrl(System.getenv("EVT_URL"));
        Category.ETC.setUrl(System.getenv("ETC_URL"));
    }
}
