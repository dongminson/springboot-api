package com.project.springbootapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "definitions")
public class Definition {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="part_of_speech", length = 500)
	@Size(min = 1, max = 500, message = "Part of speech must be between 1 and 500 characters")
	@NotNull(message = "Part of speech cannot be null")
	private String partOfSpeech;
	
	@Column(name="definition", length = 2000)
	@Size(min = 10, max = 500, message = "Definition must be between 10 and 2000 characters")
	@NotNull(message = "Definition cannot be null")
	private String definition;
	
	@Column(name="word_id")
	@NotNull(message = "Word Id cannot be null")
	private Long wordId;
	
	public Definition(String partOfSpeech, String definition, Long wordId) {
		this.partOfSpeech = partOfSpeech;
		this.definition = definition;
		this.wordId = wordId;
	}
	
	protected Definition() {
		
	}
	
	public Long getId() {
		return id;
	}
	
	public String getPartOfSpeech() {
		return partOfSpeech;
	}
	
	public String getDefinition() {
		return definition;
	}
	
	public Long getWordId() {
		return wordId;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setPartOfSpeech(String partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}
	
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	
	public void setWordId(Long wordId) {
		this.wordId = wordId;
	}
}
