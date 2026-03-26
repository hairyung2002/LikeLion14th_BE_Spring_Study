package com.group.libraryapp.service.book;

import com.group.libraryapp.dto.book.request.SaveBookRequest;
import com.group.libraryapp.repository.book.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    // 이제 new로 넣어줄 필요가 없다. IOC로 컨테이너에게 제어권한을 넘겼기 때문에.
    // 근데 어떤 BookRepository를 써야하는가? -> @Primary 가 걸린애를 우선으로 사용한다.
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void saveBook(SaveBookRequest request){
        bookRepository.save(request.getName());
    }

}
