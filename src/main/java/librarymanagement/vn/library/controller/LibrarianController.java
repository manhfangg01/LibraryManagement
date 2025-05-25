package librarymanagement.vn.library.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import librarymanagement.vn.library.domain.model.Librarian;
import librarymanagement.vn.library.domain.service.LibrarianService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LibrarianController {
    private final LibrarianService librarianService;
    private final PasswordEncoder passwordEncoder;

    public LibrarianController(LibrarianService librarianService, PasswordEncoder passwordEncoder) {
        this.librarianService = librarianService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/librarians")
    public String getAllLibrarians(Model model) {
        List<Librarian> librarians = this.librarianService.fetchAllLibrarian();
        model.addAttribute("librarians", librarians);
        return "/librarians/show";
    }

    @GetMapping("/librarians/create")
    public String getCreateLibrarianPage(Model model) {
        model.addAttribute("librarian", new Librarian());
        return "/librarians/create";
    }

    @PostMapping("/librarians/create")
    public String createLibrarian(@ModelAttribute("librarian") Librarian librarian) {
        librarian.setPassword(passwordEncoder.encode(librarian.getPassword()));
        this.librarianService.create(librarian);
        return "redirect:/librarians";
    }

    @GetMapping("/librarians/edit/{id}")
    public String getEditLibrarianPage(@PathVariable("id") long id, Model model) {
        Optional<Librarian> optionalLibrarian = this.librarianService.fetchLibrarianById(id);
        if (optionalLibrarian.isEmpty()) {
            throw new RuntimeException("Không tồn tại thủ thư này");
        }
        model.addAttribute("librarian", optionalLibrarian.get());
        return "/librarians/edit";
    }

    @PostMapping("/librarians/edit")
    public String updateLibrarian(@ModelAttribute("librarian") Librarian librarian) {
        this.librarianService.update(librarian);

        return "redirect:/librarians";
    }

    @PostMapping("/librarians/delete/{id}")
    public String deleteLibrarian(@PathVariable("id") long id) {
        if (this.librarianService.fetchLibrarianById(id).isEmpty()) {
            throw new RuntimeException("book not found");
        }
        this.librarianService.delete(this.librarianService.fetchLibrarianById(id).get());
        return "redirect:/librarians";
    }

}
