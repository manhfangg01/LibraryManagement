package librarymanagement.vn.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController {
    @GetMapping("/books")
    public String getMethodName() {
        return new String();
    }

}
