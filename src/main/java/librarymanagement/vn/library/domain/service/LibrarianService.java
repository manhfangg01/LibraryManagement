package librarymanagement.vn.library.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import librarymanagement.vn.library.domain.model.Librarian;
import librarymanagement.vn.library.domain.repository.LibrarianRepository;

@Service
public class LibrarianService {

    private final LibrarianRepository librarianRepository;

    public LibrarianService(LibrarianRepository librarianRepository) {
        this.librarianRepository = librarianRepository;
    }

    public List<Librarian> fetchAllLibrarian() {
        return this.librarianRepository.findAll();
    }

    public void create(Librarian librarian) {
        this.librarianRepository.save(librarian);
    }

    public Optional<Librarian> fetchLibrarianById(long id) {
        return this.librarianRepository.findById(id);
    }

    public void update(Librarian librarian) {
        Librarian realLibrarian = this.librarianRepository.findById(librarian.getId()).get();
        realLibrarian.setEmail(librarian.getEmail());
        realLibrarian.setBorrows(librarian.getBorrows());
        realLibrarian.setName(librarian.getName());
        realLibrarian.setRole(librarian.getRole());
        this.librarianRepository.save(realLibrarian);
    }

    public void delete(Librarian librarian) {
        this.librarianRepository.delete(librarian);
    }

    public Page<Librarian> fetchAllLibrariansWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size); // page bắt đầu từ 0
        return librarianRepository.findAll(pageable);
    }

}
