package librarymanagement.vn.library.domain.service.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import librarymanagement.vn.library.domain.model.Category;
import librarymanagement.vn.library.domain.model.Category_;

@Service
public class CategorySpecs {
    public Specification<Category> nameLike(String name) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(Category_.NAME),
                "%" + name + "%");
    }

}
