package librarymanagement.vn.library.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import librarymanagement.vn.library.domain.dto.MemberFilterCriteriaDTO;
import librarymanagement.vn.library.domain.model.Member;
import librarymanagement.vn.library.domain.service.MemberService;
import librarymanagement.vn.library.util.constant.MembershipType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members")
    public String getAllMembers(
            Model model,
            @RequestParam(value = "page", defaultValue = "1") int page, // Đặt giá trị mặc định là 1 (1-indexed cho
                                                                        // người dùng)
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy, // Mặc định sắp xếp theo 'id'
            @RequestParam(defaultValue = "asc") String sortDir, // Mặc định sắp xếp thèo tăng dần

            @ModelAttribute MemberFilterCriteriaDTO memberFilterCriteriaDTO) {

        // Chuyển đổi page từ 1-indexed (frontend) sang 0-indexed (Pageable)
        // Đảm bảo page index không bao giờ nhỏ hơn 0
        Sort sort = sortDir.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(Math.max(0, page - 1), size, sort);

        Page<Member> pageMembers = this.memberService.fetchAllMembersWithPaginationAndSpecification(
                memberFilterCriteriaDTO, pageable);
        List<Member> members = pageMembers.getContent();

        // Truyền tất cả các giá trị của enum MembershipType để populate dropdown
        model.addAttribute("allMembershipTypes", Arrays.asList(MembershipType.values()));

        model.addAttribute("members", members);
        model.addAttribute("currentPage", page); // Hiển thị trang hiện tại (1-indexed)
        model.addAttribute("sizePerPage", size);
        model.addAttribute("totalPages", pageMembers.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);

        // Rất quan trọng: truyền DTO trở lại view để giữ giá trị trong form
        model.addAttribute("memberFilterCriteriaDTO", memberFilterCriteriaDTO);

        return "members/show"; // trang hiển thị danh sách members
    }

    @GetMapping("/members/create")
    public String getCreateMemberPage(Model model) {
        model.addAttribute("member", new Member());
        return "/members/create";
    }

    @PostMapping("/members/create")
    public String postMethodName(@ModelAttribute("member") Member member) {
        this.memberService.create(member);
        return "redirect:/members";
    }

    @GetMapping("/members/edit/{id}")
    public String getEditMemberPage(@PathVariable("id") long id, Model model) {
        Optional<Member> optionalMember = this.memberService.fetchMemberById(id);
        if (optionalMember.isEmpty()) {
            throw new RuntimeException("Không tồn tại thủ thư này");
        }
        model.addAttribute("member", optionalMember.get());
        return "/members/edit";
    }

    @PostMapping("/members/edit")
    public String updateMember(@ModelAttribute("member") Member member) {
        this.memberService.update(member);

        return "redirect:/members";
    }

    @PostMapping("/members/delete/{id}")
    public String deleteMember(@PathVariable("id") long id) {
        if (this.memberService.fetchMemberById(id).isEmpty()) {
            throw new RuntimeException("member not found");
        }
        this.memberService.delete(this.memberService.fetchMemberById(id).get());
        return "redirect:/members";
    }

}
