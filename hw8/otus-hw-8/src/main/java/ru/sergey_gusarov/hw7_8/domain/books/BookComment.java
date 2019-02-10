package ru.sergey_gusarov.hw7_8.domain.books;

import javax.persistence.*;

@Entity
@Table(name = "BOOK_COMMENT")
public class BookComment {
    @Id
    @SequenceGenerator(name = "BookCommentSeqGen", sequenceName = "BookCommentSeq", initialValue = 1, allocationSize = 0)
    @GeneratedValue(generator = "BookCommentSeqGen")
    long id;
    String text;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "BOOK_ID", nullable = false)
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
