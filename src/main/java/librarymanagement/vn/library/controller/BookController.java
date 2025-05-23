package librarymanagement.vn.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import librarymanagement.vn.library.domain.model.Book;
import librarymanagement.vn.library.domain.service.BookService;
import librarymanagement.vn.library.domain.service.CategoryService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class BookController {
    private final BookService bookService;
    private final CategoryService categoryService;

    public BookController(BookService bookService, CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @GetMapping("/books")
    public String getBooks(Model model) {
        model.addAttribute("books", this.bookService.fetchAllBooks());
        return "/books/show";
    }

    @GetMapping("/books/create")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryService.fetchAllCategories());
        return "book-form";
    }

    @PostMapping("/books")
    public String postMethodName(@RequestBody String entity) {
        // TODO: process POST request

        return entity;
    }

}
