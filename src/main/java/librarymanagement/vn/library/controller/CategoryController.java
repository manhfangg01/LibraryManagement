package librarymanagement.vn.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import librarymanagement.vn.library.domain.model.Category;
import librarymanagement.vn.library.domain.service.CategoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public String getAllCategories(Model model) {
        model.addAttribute("categories", this.categoryService.fetchAllCategories());
        return "/categories/show";
    }

    @GetMapping("/categories/create")
    public String getCreateCategoryPage(Model model) {
        model.addAttribute("newCategory", new Category());
        return "/categories/create";
    }

    @PostMapping("/categories/create")
    public String postCreateCategory(Model model, @ModelAttribute("newCategory") Category category) {
        if (category != null) {
            if (this.categoryService.fetchCategoryByName(category.getName()).isPresent()) {
                model.addAttribute("ObjectExisted", "Đã tồn tại thể loại này rồi");
                model.addAttribute("newCategory", category); // giữ lại dữ liệu người dùng nhập
                return "/categories/create";
            } else {
                this.categoryService.saveCategory(category);
            }
        }
        return "redirect:/categories";
    }

    // Hiển thị form cập nhật
    @GetMapping("/categories/edit/{id}")
    public String getEditCategoryPage(Model model, @PathVariable("id") long id) {

        Category category = categoryService.fetchCategoryById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        model.addAttribute("category", category);
        model.addAttribute("id", id);
        return "/categories/edit";
    }

    // Xử lý cập nhật
    @PostMapping("/categories/edit")
    public String postEditCategory(@ModelAttribute("category") Category category, Model model) {

        if (this.categoryService.fetchCategoryByName(category.getName()).isPresent()) {
            model.addAttribute("ObjectExisted", "Đã tồn tại thể loại này rồi");
            model.addAttribute("newCategory", category); // giữ lại dữ liệu người dùng nhập
            return "/categories/edit";
        } else {
            this.categoryService.saveCategory(category);
        }
        return "redirect:/categories";

    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") long id) {
        categoryService.delete(id);
        return "redirect:/categories";
    }

}
