package librarymanagement.vn.library.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import librarymanagement.vn.library.domain.model.Book;
import librarymanagement.vn.library.domain.repository.BookRepository;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> fetchAllBooks() {
        return this.bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        return this.bookRepository.save(book);

    }

}
