package librarymanagement.vn.library.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import librarymanagement.vn.library.domain.model.Borrow;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {

}
