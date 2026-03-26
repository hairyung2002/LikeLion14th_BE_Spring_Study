package com.group.libraryapp.controller.book;

import com.group.libraryapp.dto.book.request.SaveBookRequest;
import com.group.libraryapp.service.book.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/book")
    public void saveBook(@RequestBody SaveBookRequest request){
        bookService.saveBook(request);
    }
}
