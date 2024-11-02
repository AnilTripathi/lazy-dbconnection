package com.lazy.db.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.lazy.db.connection.service.BookService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LazyDBConnectionRunner implements CommandLineRunner{
	@Autowired
	private BookService bkService;
	
	@Override
	public void run(String... args) throws Exception {
		StopWatch stopWatch=new StopWatch();
		stopWatch.start();
		bkService.fetchBooks();
		stopWatch.stop();
		log.info("Time taken to execute query : {}",stopWatch.shortSummary());
	}

}
