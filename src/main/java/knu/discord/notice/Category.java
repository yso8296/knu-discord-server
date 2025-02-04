package knu.discord.notice;

import lombok.Getter;

@Getter
public enum Category {
    COM("공통", "https://discordapp.com/api/webhooks/1333034749513699328/ZUq-vGUSfMmDLUwneNjGDKaa-YsWtUFpbBfQjoFF70quyyiS0CJjlZiUqXQqaT4nh25f"),
    CLS("수업", "https://discordapp.com/api/webhooks/1336341496302927893/GJGqdi32Dkt5AEwBnBE2MgD9g-_lq6XRUoHbgf8MpAnRfV6nytWIzb3RhoirYpCthHTE"),
    CLG("학적", "https://discordapp.com/api/webhooks/1336341810125209600/njyrBH-UrMzeB8ulApsdsC4NlemhPlAk19_TeVOLNUSiPvtiHyogzOkc1KUY5Plqns7n"),
    JOB("취업", "https://discordapp.com/api/webhooks/1336341888244121620/GAq6kXH0S5Se1CkCVq2C9M0iR1e73HTYh8cDUUqPw19nib6rQmr49fatlUuZZ_NET9h3"),
    SCH("장학", "https://discordapp.com/api/webhooks/1336341934893039717/2a4ivm8Gt_r0q1pmpqccVyFaHS7GPsdy17ONHRpHP5wf0B0-T1Hw9J9BO9D7E76vG6Vn"),
    EVT("행사", "https://discordapp.com/api/webhooks/1336341981835563119/Fmkt0wi4-T5Dqf8t-sBI4OmgkV5Aset9UuGa20k6WrKi-Ur4sC3CS11-6z68cSR3SPHd"),
    ETC("기타", "https://discordapp.com/api/webhooks/1336342030548205661/uXgCPU5gzJx1bl8AQZ5YW5HULeyxKxF4_eT-nJhAJ8oTaU-uF85eRH7PDjTUq5iHYPOB");

    private final String displayName;
    private final String url;

    Category(String displayName, String url) {
        this.displayName = displayName;
        this.url = url;
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
