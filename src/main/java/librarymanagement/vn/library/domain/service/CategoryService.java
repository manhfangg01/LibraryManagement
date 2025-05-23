package librarymanagement.vn.library.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import librarymanagement.vn.library.domain.model.Category;
import librarymanagement.vn.library.domain.repository.BookRepository;
import librarymanagement.vn.library.domain.repository.CategoryRepository;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;

    public CategoryService(CategoryRepository categoryRepository, BookRepository bookRepository) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
    }

    public List<Category> fetchAllCategories() {
        return this.categoryRepository.findAll();
    }

    public Category saveCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    public Optional<Category> fetchCategoryById(long id) {
        return this.categoryRepository.findById(id);
    }

    public Optional<Category> fetchCategoryByName(String name) {
        return this.categoryRepository.findByName(name);
    }

    public void delete(long id) {
        this.categoryRepository.deleteById(id);
    }

}
