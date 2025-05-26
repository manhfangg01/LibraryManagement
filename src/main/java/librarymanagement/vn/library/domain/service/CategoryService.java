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

    public Page<Category> fetchAllCategoriesWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size); // page - 1 vì PageRequest bắt đầu từ 0
        return categoryRepository.findAll(pageable);
    }

}
