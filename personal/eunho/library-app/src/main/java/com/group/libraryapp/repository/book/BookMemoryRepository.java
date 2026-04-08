package com.group.libraryapp.repository.book;

import com.group.libraryapp.dto.book.request.SaveBookRequest;

import java.util.ArrayList;
import java.util.List;

public class BookMemoryRepository implements BookRepository {

    private final List<String> books = new ArrayList();

    @Override
    public void save(String bookName) {
        books.add(bookName);
    }
}
