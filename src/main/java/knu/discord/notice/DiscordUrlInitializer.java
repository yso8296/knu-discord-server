package knu.discord.notice;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiscordUrlInitializer {

    private final WebhookUrlProperties webhookUrlProperties;

    @PostConstruct
    public void init() {
        Category.COM.setUrl(webhookUrlProperties.getComUrl());
        Category.CLS.setUrl(webhookUrlProperties.getClsUrl());
        Category.CLG.setUrl(webhookUrlProperties.getClgUrl());
        Category.JOB.setUrl(webhookUrlProperties.getJobUrl());
        Category.SCH.setUrl(webhookUrlProperties.getSchUrl());
        Category.EVT.setUrl(webhookUrlProperties.getEvtUrl());
        Category.ETC.setUrl(webhookUrlProperties.getEtcUrl());
    }
}
