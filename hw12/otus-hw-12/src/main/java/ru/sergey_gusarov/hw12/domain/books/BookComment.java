package ru.sergey_gusarov.hw12.domain.books;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document
public class BookComment {
    @Id
    String id;
    String text;
    Book book;

    public BookComment(String text, Book book) {
        this.text = text;
        this.book = book;
    }

    public BookComment() {
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(60);
        sb.append("BookComment{ id=")
                .append(getId())
                .append(", text='")
                .append(getText())
                .append("' book = ");
        Book bookObj = getBook();

        if(bookObj == null)
            sb.append("No book");
        else{
            sb.append("[ title = '");
            String bookTitle = bookObj.getTitle();
            if (bookTitle == null)
                sb.append("No title");
            else
                sb.append(bookTitle);
            sb.append("']");
        }
        return sb.toString();
    }
}
