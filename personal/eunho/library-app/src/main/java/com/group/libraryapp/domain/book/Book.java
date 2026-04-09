package com.group.libraryapp.domain.book;

import javax.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column(nullable = false)
    private String name;

    protected Book() { } // JPA는 생성자가 있어서 객체를 만들어 줄 수 있는데, public은 의도치 않은 사용이 있을 수 있기 떄문 / private는 접근 못 함.

    public Book(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    String.format("잘못된 name(%s)이 들어왔습니다", name));
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
