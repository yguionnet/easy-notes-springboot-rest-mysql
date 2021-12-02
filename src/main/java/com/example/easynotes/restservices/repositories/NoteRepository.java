package com.example.easynotes.restservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.easynotes.restservices.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long>{
	

}
