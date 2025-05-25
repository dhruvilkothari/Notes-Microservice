package com.example.notes_app.Notes.App.controller;

import com.example.notes_app.Notes.App.dto.NotesDto;
import com.example.notes_app.Notes.App.dto.NotesResponseDto;
import com.example.notes_app.Notes.App.dto.NotesUpdateDto;
import com.example.notes_app.Notes.App.service.NotesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
@Slf4j
@RequiredArgsConstructor
public class NotesController {
    private final NotesService notesService;

    @PostMapping("/createNotes")
    public ResponseEntity<NotesResponseDto> createNotes(@RequestBody NotesDto notesDto){
        return notesService.createNotes(notesDto);
    }

    @GetMapping("/getAllMyNotes")
    public ResponseEntity<List<NotesResponseDto>> createNotes(){
        return notesService.getAllNotes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotesResponseDto> getNoteById(@PathVariable Long id){
        System.out.println(id);
        return notesService.getNoteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotesResponseDto> updateNoteById(@PathVariable Long id, @RequestBody NotesUpdateDto notesDto){
        return notesService.updateNoteById(id, notesDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNoteById(@PathVariable Long id){
        return notesService.deleteNoteById(id);
    }
}
