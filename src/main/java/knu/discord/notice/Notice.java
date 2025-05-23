package knu.discord.notice;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import knu.discord.global.entity.BaseEntity;
import knu.discord.global.converter.CategoryConverter;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
@Setter
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String link;

    @NotNull
    private int visited;

    @Convert(converter = CategoryConverter.class)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Send send;

    @NotNull
    private LocalDate uploadDate;
}
