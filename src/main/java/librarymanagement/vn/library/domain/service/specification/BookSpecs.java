package librarymanagement.vn.library.domain.service.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import librarymanagement.vn.library.domain.model.Book;
import librarymanagement.vn.library.domain.model.Book_;

@Service
public class BookSpecs {
    public Specification<Book> titleLike(String title) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(Book_.TITLE), "%" + title + "%");
    }

}
