package knu.discord.notice;

import lombok.Getter;

@Getter
public enum Category {
    COM("공통", null),
    CLS("수업", null),
    CLG("학적", null),
    JOB("취업", null),
    SCH("장학", null),
    EVT("행사", null),
    ETC("기타", null);

    private final String displayName;
    private String url; // 나중에 초기화할 수 있도록 final이 아님

    Category(String displayName, String url) {
        this.displayName = displayName;
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 한글명으로 Category 찾기 (역매핑)
    public static Category fromDisplayName(String displayName) {
        for (Category category : Category.values()) {
            if (category.getDisplayName().equals(displayName)) {
                return category;
            }
        }
        return ETC;
    }
}
