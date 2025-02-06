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
        Category.COM.setUrl(webhookUrlProperties.comUrl());
        Category.CLS.setUrl(webhookUrlProperties.clsUrl());
        Category.CLG.setUrl(webhookUrlProperties.clgUrl());
        Category.JOB.setUrl(webhookUrlProperties.jobUrl());
        Category.SCH.setUrl(webhookUrlProperties.schUrl());
        Category.EVT.setUrl(webhookUrlProperties.evtUrl());
        Category.ETC.setUrl(webhookUrlProperties.etcUrl());
    }
}
