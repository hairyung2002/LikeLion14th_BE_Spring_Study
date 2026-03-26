package com.group.libraryapp.repository.book;

import com.group.libraryapp.dto.book.request.SaveBookRequest;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository {

    public void save(String bookName);

}
