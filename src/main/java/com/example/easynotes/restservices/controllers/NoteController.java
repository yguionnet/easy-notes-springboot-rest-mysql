package com.example.easynotes.restservices.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.easynotes.restservices.exceptions.ResourceNotFoundException;
import com.example.easynotes.restservices.model.Note;
import com.example.easynotes.restservices.repositories.NoteRepository;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

	@Autowired	
	private NoteRepository noteRepository;
		
	
	// Get All Notes
	@GetMapping
	public List<Note> getAllNotes() {
	    return noteRepository.findAll();
	}
	
	// Create a new Note
	@PostMapping
	public Note createNote(@Valid @RequestBody Note note) {
	    return noteRepository.save(note);
	}
	
	// Get a Single Note
	@GetMapping("/{id}")
	public Note getNoteById(@PathVariable(value = "id") Long noteId) {
	    Optional<Note> optionalNote=noteRepository.findById(noteId);
	    if (!optionalNote.isPresent())
	    	throw new ResourceNotFoundException("Note", "id", noteId);
	    
		Note note=optionalNote.get();
		return note;
	}
	
	// Update a Note
	@PutMapping("/{id}")
	public Note updateNote(@PathVariable(value = "id") Long noteId,
	                                        @Valid @RequestBody Note noteDetails) {

		Optional<Note> optionalNote=noteRepository.findById(noteId);
	    if (!optionalNote.isPresent())
	    	throw new ResourceNotFoundException("Note", "id", noteId);
	    Note note=optionalNote.get();
	    note.setTitle(noteDetails.getTitle());
	    note.setContent(noteDetails.getContent());

	    Note updatedNote = noteRepository.save(note);
	    return updatedNote;
	}
	
	// Delete a Note
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId) {
		Optional<Note> optionalNote=noteRepository.findById(noteId);
	    if (!optionalNote.isPresent())
	    	throw new ResourceNotFoundException("Note", "id", noteId);
	    Note note=optionalNote.get();
		
	    noteRepository.delete(note);

	    return ResponseEntity.ok().build();
	}
	
}
