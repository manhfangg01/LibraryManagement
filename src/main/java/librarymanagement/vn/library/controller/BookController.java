package librarymanagement.vn.library.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import librarymanagement.vn.library.domain.model.Book;
import librarymanagement.vn.library.domain.model.Category;
import librarymanagement.vn.library.domain.service.BookService;
import librarymanagement.vn.library.domain.service.CategoryService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "books/create";
    }

    @PostMapping("/books")
    public String postCreatedBook(@ModelAttribute("book") Book book, Model model) {
        if (book != null) {
            if (this.bookService.fetchBookByTitle(book.getTitle()).isPresent()) {
                model.addAttribute("ObjectExisted", "Đã tồn tại sách này rồi");
                model.addAttribute("newbook", book); // giữ lại dữ liệu người dùng nhập
                return "/categories/create";
            } else {
                this.bookService.saveBook(book);
            }
        }
        return "redirect:/books";

    }

    @GetMapping("books/edit/{id}")
    public String getUpdatePage(@PathVariable("id") long id, Model model) {
        Book book = bookService.fetchBookById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        model.addAttribute("book", book);
        model.addAttribute("id", id);
        return "/books/edit";
    }

    @PutMapping("/books/edit")
    public String updateBook(@ModelAttribute("book") Book book, Model model) {
        if (this.bookService.fetchBookByTitle(book.getTitle()).isPresent()) {
            model.addAttribute("errors", "Đã tồn tại sách này rồi");
            model.addAttribute("book", book); // giữ lại dữ liệu người dùng nhập
            return "/categories/create";
        } else {
            this.bookService.saveBook(book);
        }
        return "redirect:/books";
    }

    @PostMapping("books/delete/{id}")
    public String deleteBook(@PathVariable("id") long id) {
        if (this.bookService.fetchBookById(id).isEmpty()) {
            throw new RuntimeException("book not found");
        }
        this.bookService.deleteById(id);
        return "redirect:/books";
    }

}
