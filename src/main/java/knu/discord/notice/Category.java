package knu.discord.notice;

public enum Category {
    COM("공통"),
    CLS("수업"),
    CLG("학적"),
    JOB("취업"),
    SCH("장학"),
    EVT("행사"),
    ETC("기타");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    // 한글명으로 Category 찾기 (역매핑)
    public static Category fromDisplayName(String displayName) {
        for (Category category : Category.values()) {
            if (category.getDisplayName().equals(displayName)) {
                return category;
            }
        }
        return ETC; // 기본값 (매칭 실패 시)
    }
}
