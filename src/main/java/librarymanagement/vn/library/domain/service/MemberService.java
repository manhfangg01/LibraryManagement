package librarymanagement.vn.library.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import librarymanagement.vn.library.domain.model.Member;
import librarymanagement.vn.library.domain.repository.MemberRepository;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> fetchAllMember() {
        return this.memberRepository.findAll();
    }

    public Optional<Member> fetchMemberById(long id) {
        return this.memberRepository.findById(id);
    }

    public void create(Member member) {
        this.memberRepository.save(member);
    }

    public void update(Member member) {
        Member realMember = this.memberRepository.findById(member.getId()).get();
        realMember.setAddress(member.getAddress());
        realMember.setBorrows(member.getBorrows());
        realMember.setEmail(member.getEmail());
        realMember.setMembershipType(member.getMembershipType());
        realMember.setMembershipDate(member.getMembershipDate());
        realMember.setName(member.getName());
        realMember.setPhone(member.getPhone());
        realMember.setStatus(member.isStatus());
        this.memberRepository.save(realMember);
    }

    public void delete(Member member) {
        this.memberRepository.delete(member);
    }

    public Page<Member> fetchAllMembersWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size); // Spring tính từ 0
        return memberRepository.findAll(pageable);
    }

}
