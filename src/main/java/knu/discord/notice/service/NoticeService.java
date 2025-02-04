package knu.discord.notice.service;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class NoticeService {

    public void sendNotice(String category, String title, String link, String createdAt, String author, String webHookUrl) {
        // Embed 메시지 구성
        Map<String, Object> embed = Map.of(
                "title", "[" + category + "] " + title,
                "url", link,
                "color", 0, // 검정색 사이드바 (#000000)
                "fields", new Map[]{ // 필드로 구성해 줄 간격 확보
                        Map.of(
                                "name", "\u200B", // 빈 줄 추가 (Zero Width Space)
                                "value", createdAt + "\n#" + author, // author 작성자
                                "inline", false
                        )
                }
        );

        Map<String, Object> payload = Map.of(
                "content", ":loudspeaker: UPDATE",
                "embeds", new Map[]{embed}
        );

        send(payload, webHookUrl);
    }

    private void send(Map<String, Object> payload, String webHookUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        // Discord Webhook URL
        //String url = "https://discordapp.com/api/webhooks/1333034749513699328/ZUq-vGUSfMmDLUwneNjGDKaa-YsWtUFpbBfQjoFF70quyyiS0CJjlZiUqXQqaT4nh25f";
        restTemplate.postForObject(webHookUrl, entity, String.class);
    }
}
