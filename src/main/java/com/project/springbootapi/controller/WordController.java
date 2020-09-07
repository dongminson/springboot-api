package com.project.springbootapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.springbootapi.model.Word;
import com.project.springbootapi.repository.WordRepository;

@RestController
@RequestMapping("/api")
public class WordController {

  @Autowired
  WordRepository wordRepository;

  @GetMapping("/words")
  public ResponseEntity<List<Word>> getAllWords(@RequestParam(required = false) String word) {
    try {
      List<Word> words = new ArrayList<Word>();
      
      if (word == null) {
      	wordRepository.findAll().forEach(words::add);
      } else {
          wordRepository.findByWordContaining(word).forEach(words::add);
      }
      if (words.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(words, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/words/{id}")
  public ResponseEntity<Word> getWordById(@PathVariable("id") long id) {
    Optional<Word> wordData = wordRepository.findById(id);

    if (wordData.isPresent()) {
      return new ResponseEntity<>(wordData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/words")
  public ResponseEntity<Word> createDefinition(@RequestBody Word word) {
    try {
      Word _word = wordRepository
          .save(new Word(word.getWord()));
      return new ResponseEntity<>(_word, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  @PutMapping("/words/{id}")
  public ResponseEntity<Word> updateDefinition(@PathVariable("id") long id, @RequestBody Word word) {
    Optional<Word> wordData = wordRepository.findById(id);

    if (wordData.isPresent()) {
      Word _word = wordData.get();
      _word.setWord(word.getWord());
      return new ResponseEntity<>(wordRepository.save(_word), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/words/{id}")
  public ResponseEntity<HttpStatus> deleteDefinition(@PathVariable("id") long id) {
    try {
      wordRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }

  @DeleteMapping("/words")
  public ResponseEntity<HttpStatus> deleteAllDefinitions() {
    try {
      wordRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

  }

}
