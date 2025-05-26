package librarymanagement.vn.library.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import librarymanagement.vn.library.domain.model.Borrow;
import librarymanagement.vn.library.domain.repository.BorrowRepository;

@Service
public class BorrowService {
    private BorrowRepository borrowRepository;

    public BorrowService(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    public Optional<Borrow> fetchBorrowById(long id) {
        return this.borrowRepository.findById(id);
    }

    public List<Borrow> fetchAllBorrows() {
        return this.borrowRepository.findAll();
    }

    public void create(Borrow borrow) {
        this.borrowRepository.save(borrow);
    }

    public void update(Borrow borrow) {
        Borrow realBorrow = this.borrowRepository.findById(borrow.getId()).get();
        realBorrow.setBook(borrow.getBook());
        realBorrow.setBorrowDate(borrow.getBorrowDate());
        realBorrow.setDueDate(borrow.getDueDate());
        realBorrow.setLibrarian(borrow.getLibrarian());
        realBorrow.setMember(borrow.getMember());
        realBorrow.setReturnDate(borrow.getReturnDate());
        realBorrow.setStatus(borrow.getStatus());
        this.borrowRepository.save(borrow);
    }

    public void delete(long id) {
        Optional<Borrow> optionalBorrow = this.fetchBorrowById(id);
        if (optionalBorrow.isEmpty()) {
            throw new RuntimeException("Lượt mượn này không tồn tại");
        }
        this.borrowRepository.delete(optionalBorrow.get());
    }
}
