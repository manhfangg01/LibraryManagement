package librarymanagement.vn.library.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import librarymanagement.vn.library.domain.model.Member;
import librarymanagement.vn.library.domain.service.MemberService;
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
            @RequestParam("page") Optional<Integer> optionalPage,
            @RequestParam("size") Optional<Integer> optionalSize) {
        int page = optionalPage.orElse(1);
        int size = optionalSize.orElse(5);

        Page<Member> pageMembers = memberService.fetchAllMembersWithPagination(page, size);
        List<Member> members = pageMembers.getContent();

        model.addAttribute("members", members);
        model.addAttribute("currentPage", page);
        model.addAttribute("sizePerPage", size);
        model.addAttribute("totalPages", pageMembers.getTotalPages());

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
