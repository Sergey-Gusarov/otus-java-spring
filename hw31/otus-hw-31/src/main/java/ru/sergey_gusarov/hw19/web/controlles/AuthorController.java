package ru.sergey_gusarov.hw19.web.controlles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sergey_gusarov.hw19.domain.books.Author;
import ru.sergey_gusarov.hw19.domain.books.Book;
import ru.sergey_gusarov.hw19.exception.NotFoundException;
import ru.sergey_gusarov.hw19.service.books.AuthorService;
import ru.sergey_gusarov.hw19.service.books.BookService;

import java.util.List;

@Controller
public class AuthorController {
    private final AuthorService authorService;
    private final BookService bookService;

    @Value("${error.message}")
    private String errorMessage;

    @Autowired
    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }


    @GetMapping("/authors")
    public String listAuthorPage(Model model) {
        List<Author> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        return "authorsList";
    }

    @RequestMapping("/newAuthor")
    public String newAuthorPage(@ModelAttribute Author author, Model model) {
        authorService.save(author);
        return "authorEdit";
    }

    @RequestMapping("/newAuthorForBook")
    public String newAuthorFromBookPage(@ModelAttribute Book book, Model model) {
        book = bookService.findById(book.getId()).get();
        Author author = new Author();
        authorService.save(author);
        book.getAuthors().add(author);
        bookService.save(book);
        model.addAttribute(author);
        return "redirect:/newAuthor?id=" + author.getId();
    }

    @RequestMapping("/author")
    public String authorPage(@ModelAttribute Author author, Model model) {
        Author authorFromDb = authorService.getById(author.getId()).orElseThrow(NotFoundException::new);
        model.addAttribute("author", authorFromDb);
        authorService.save(authorFromDb);
        return "authorEdit";
    }

    @RequestMapping(value = "/author", method = RequestMethod.POST)
    public String editAuthor(@ModelAttribute Author author, Model model) {
        if (author.getName().isEmpty()) {
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/author?id=" + author.getId();
        } else
            authorService.save(author);
        return "redirect:/authors";
    }

    @RequestMapping(value = "/deleteAuthor")
    public String deleteAuthor(@ModelAttribute Author author) {
        authorService.deleteById(author.getId());
        return "redirect:/authors";
    }
}
