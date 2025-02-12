package knu.discord.global.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import knu.discord.notice.Category;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<Category, String> {

    // Enum -> DB 저장 (한글 값)
    @Override
    public String convertToDatabaseColumn(Category category) {
        if (category == null) {
            return null;
        }
        return category.getDisplayName(); // 한글 값 저장
    }

    // DB 값 -> Enum 변환
    @Override
    public Category convertToEntityAttribute(String dbData) {
        return Category.fromDisplayName(dbData); // 한글 -> Enum 변환
    }
}