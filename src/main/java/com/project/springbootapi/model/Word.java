package com.project.springbootapi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "words")
public class Word {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "word", length = 500)
	@Size(min = 1, max = 500, message 
    = "Word must be between 1 and 500 characters")
	@NotNull(message = "Word cannot be null")
	private String word;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "word_id")
	private List<Definition> definitions = new ArrayList<>();
	
	public Word(String word) {
		this.word = word;
	}
	
	protected Word() {
		
	}
	
	public Long getId() {
		return id;
	}
	
	public String getWord() {
		return word;
	}
	
	public List<Definition> getDefinitions() {
		return definitions;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
}

