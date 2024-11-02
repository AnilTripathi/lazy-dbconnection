package com.lazy.db.connection.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lazy.db.connection.entity.Book;
import com.lazy.db.connection.repository.BookRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookService {
	@Autowired
	private BookRepository bookRepository;
	
	public void fetchBooks() {
		List<Book> bookList=bookRepository.findAll();
		log.info("PRINTING RECORDS Size={}",bookList.size());
		bookList.forEach(bk->log.info("{}",bk));
	}

}
