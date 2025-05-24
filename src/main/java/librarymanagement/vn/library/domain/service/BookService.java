package librarymanagement.vn.library.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import librarymanagement.vn.library.domain.model.Book;
import librarymanagement.vn.library.domain.repository.BookRepository;
import librarymanagement.vn.library.domain.repository.CategoryRepository;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Book> fetchAllBooks() {
        return this.bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        return this.bookRepository.save(book);
    }

    public Optional<Book> fetchBookByTitle(String title) {
        return this.bookRepository.findByTitle(title);
    }

    public Optional<Book> fetchBookById(long id) {
        return this.bookRepository.findById(id);
    }

    public void deleteById(long id) {
        this.bookRepository.deleteById(id);
    }

}
