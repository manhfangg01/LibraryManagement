package librarymanagement.vn.library.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import librarymanagement.vn.library.domain.model.Borrow;
import librarymanagement.vn.library.domain.repository.MemberRepository;
import librarymanagement.vn.library.domain.service.BookService;
import librarymanagement.vn.library.domain.service.BorrowService;
import librarymanagement.vn.library.domain.service.LibrarianService;
import librarymanagement.vn.library.domain.service.MemberService;
import librarymanagement.vn.library.util.constant.BorrowStatus;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class BorrowController {

    private final MemberRepository memberRepository;
    private final BorrowService borrowService;
    private final BookService bookService;
    private final MemberService memberService;
    private final LibrarianService librarianService;

    public BorrowController(BorrowService borrowService, BookService bookService, MemberService memberService,
            LibrarianService librarianService, MemberRepository memberRepository) {
        this.borrowService = borrowService;
        this.bookService = bookService;
        this.memberService = memberService;
        this.librarianService = librarianService;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/borrows")
    public String getAllBorrows(Model model) {
        model.addAttribute("borrows", this.borrowService.fetchAllBorrows());
        return "/borrows/show";
    }

    @GetMapping("/borrows/create")
    public String getCreateBorrowPage(Model model) {
        model.addAttribute("borrow", new Borrow());
        model.addAttribute("books", this.bookService.fetchAllBooks());
        model.addAttribute("members", this.memberService.fetchAllMember());
        model.addAttribute("librarians", this.librarianService.fetchAllLibrarian());
        return "borrows/create";
    }

    @PostMapping("/borrows/create")
    public String createBorrow(@ModelAttribute("borrow") Borrow borrow) {
        this.borrowService.create(borrow);
        return "redirect:/borrows";
    }

    @GetMapping("/borrows/edit/{id}")
    public String getUpdateBorrowPage(@PathVariable("id") long id, Model model) {
        Optional<Borrow> optionalBorrow = this.borrowService.fetchBorrowById(id);
        if (optionalBorrow.isEmpty()) {
            throw new RuntimeException("Lượt mượn này không tồn tại");
        }
        Borrow borrow = optionalBorrow.get();
        model.addAttribute("statuses", BorrowStatus.values());
        model.addAttribute("borrow", borrow);
        model.addAttribute("books", this.bookService.fetchAllBooks());
        model.addAttribute("members", this.memberService.fetchAllMember());
        model.addAttribute("librarians", this.librarianService.fetchAllLibrarian());
        return "borrows/edit";
    }

    @PostMapping("/borrows/edit")
    public String updateBorrow(@ModelAttribute("borrow") Borrow borrow) {
        this.borrowService.update(borrow);
        return "redirect:/borrows";

    }

    @PostMapping("/borrows/delete/{id}")
    public String deleteBorrow(@PathVariable("id") long id) {
        this.borrowService.delete(id);

        return "redirect:/borrows";

    }

}
