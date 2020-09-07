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

import com.project.springbootapi.model.Definition;
import com.project.springbootapi.repository.DefinitionRepository;

@RestController
@RequestMapping("/api")
public class DefinitionController {

  @Autowired
  DefinitionRepository definitionRepository;

  @GetMapping("/definitions")
  public ResponseEntity<List<Definition>> getAllDefinitions(@RequestParam(required = false) String partOfSpeech) {
    try {
      List<Definition> definitions = new ArrayList<Definition>();
      
      if (partOfSpeech == null) {
    	  definitionRepository.findAll().forEach(definitions::add);
      } else {
          definitionRepository.findByPartOfSpeechContaining(partOfSpeech).forEach(definitions::add);
      }
      if (definitions.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(definitions, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/definitions/{id}")
  public ResponseEntity<Definition> getDefinitionById(@PathVariable("id") long id) {
    Optional<Definition> definitionData = definitionRepository.findById(id);

    if (definitionData.isPresent()) {
      return new ResponseEntity<>(definitionData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/definitions")
  public ResponseEntity<Definition> createDefinition(@RequestBody Definition definition) {
    try {
      Definition _definition = definitionRepository
          .save(new Definition(definition.getPartOfSpeech(), definition.getDefinition(), definition.getWordId()));
      return new ResponseEntity<>(_definition, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  @PutMapping("/definitions/{id}")
  public ResponseEntity<Definition> updateDefinition(@PathVariable("id") long id, @RequestBody Definition definition) {
    Optional<Definition> definitionData = definitionRepository.findById(id);

    if (definitionData.isPresent()) {
      Definition _definition = definitionData.get();
      _definition.setDefinition(definition.getDefinition());
      _definition.setPartOfSpeech(definition.getPartOfSpeech());
      _definition.setWordId(definition.getWordId());
      return new ResponseEntity<>(definitionRepository.save(_definition), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/definitions/{id}")
  public ResponseEntity<HttpStatus> deleteDefinition(@PathVariable("id") long id) {
    try {
      definitionRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }

  @DeleteMapping("/definitions")
  public ResponseEntity<HttpStatus> deleteAllDefinitions() {
    try {
      definitionRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

  }

}