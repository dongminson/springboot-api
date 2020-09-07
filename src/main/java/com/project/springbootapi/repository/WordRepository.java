package com.project.springbootapi.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.springbootapi.model.Word;

public interface WordRepository extends JpaRepository<Word, Long> {
	List<Word> findByWordContaining(String word);
}