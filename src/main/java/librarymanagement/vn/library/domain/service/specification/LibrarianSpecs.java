package librarymanagement.vn.library.domain.service.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import librarymanagement.vn.library.domain.model.Librarian;
import librarymanagement.vn.library.domain.model.Librarian_;

@Service
public class LibrarianSpecs {
    public Specification<Librarian> nameLike(String name) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(Librarian_.NAME),
                "%" + name + "%");
    }

}
