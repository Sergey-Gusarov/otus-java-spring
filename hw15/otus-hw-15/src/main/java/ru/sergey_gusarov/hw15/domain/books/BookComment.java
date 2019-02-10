package ru.sergey_gusarov.hw15.domain.books;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookComment {
    private String text;

    public BookComment(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(60);
        sb.append("BookComment{ ")
                .append("text='")
                .append(getText())
                .append("'}");
        return sb.toString();
    }
}
