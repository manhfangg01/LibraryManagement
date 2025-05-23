package librarymanagement.vn.library.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import librarymanagement.vn.library.domain.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Optional<Category> findByName(String name);
}
