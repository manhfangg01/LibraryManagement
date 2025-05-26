package librarymanagement.vn.library.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import librarymanagement.vn.library.domain.model.Book;
import librarymanagement.vn.library.domain.model.Category;
import librarymanagement.vn.library.domain.repository.BookRepository;
import librarymanagement.vn.library.domain.repository.CategoryRepository;
import librarymanagement.vn.library.domain.service.specification.CategorySpecs;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final CategorySpecs categorySpecs;

    public CategoryService(CategoryRepository categoryRepository, BookRepository bookRepository,
            CategorySpecs categorySpecs) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
        this.categorySpecs = categorySpecs;
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
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy category"));

        // Gỡ category khỏi tất cả các book
        for (Book book : category.getBooks()) {
            book.getCategories().remove(category);
            bookRepository.save(book); // Lưu lại từng book
        }

        // Xóa category
        categoryRepository.delete(category);
    }

    public Page<Category> fetchAllCategoriesWithPagination(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Page<Category> fetchAllCategoriesWithPaginationAndNameSpecification(Pageable pageable, String name) {
        return this.categoryRepository.findAll(this.categorySpecs.nameLike(name), pageable);
    }

}
