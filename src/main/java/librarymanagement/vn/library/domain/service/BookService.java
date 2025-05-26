package librarymanagement.vn.library.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Optional<Book> fetchBookByTitle(String title) {
        return this.bookRepository.findByTitle(title);
    }

    public Optional<Book> fetchBookById(long id) {
        return this.bookRepository.findById(id);
    }

    public void deleteById(long id) {
        this.bookRepository.deleteById(id);
    }

    public Page<Book> fetchAllBooksWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return this.bookRepository.findAll(pageable);
    }

}
