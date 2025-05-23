package librarymanagement.vn.library.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import librarymanagement.vn.library.domain.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
