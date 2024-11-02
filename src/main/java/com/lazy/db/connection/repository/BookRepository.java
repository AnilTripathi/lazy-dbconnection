package com.lazy.db.connection.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lazy.db.connection.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
