package com.project.springbootapi.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.springbootapi.model.Definition;

public interface DefinitionRepository extends JpaRepository<Definition, Long> {
	List<Definition> findByPartOfSpeechContaining(String partOfSpeech);
}