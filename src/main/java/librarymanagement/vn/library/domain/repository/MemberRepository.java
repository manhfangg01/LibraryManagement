package librarymanagement.vn.library.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import librarymanagement.vn.library.domain.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
