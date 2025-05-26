package librarymanagement.vn.library.domain.service.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import librarymanagement.vn.library.domain.model.Member;
import librarymanagement.vn.library.domain.model.Member_;

@Service
public class MemberSpecs {
    public Specification<Member> nameLike(String name) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(Member_.NAME), "%" + name + "%");
    }

}
