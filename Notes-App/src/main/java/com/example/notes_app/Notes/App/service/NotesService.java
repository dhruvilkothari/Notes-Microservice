package com.example.notes_app.Notes.App.service;

import com.example.notes_app.Notes.App.context.UserContext;
import com.example.notes_app.Notes.App.dto.NotesDto;
import com.example.notes_app.Notes.App.dto.NotesResponseDto;
import com.example.notes_app.Notes.App.dto.NotesUpdateDto;
import com.example.notes_app.Notes.App.entity.NotesEntity;
import com.example.notes_app.Notes.App.repository.NotesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotesService {
    private final NotesRepository notesRepository;
    private final ModelMapper modelMapper;

    public ResponseEntity<NotesResponseDto> createNotes(NotesDto notesDto){
        String email = UserContext.getEmail();
        NotesEntity notesEntity = modelMapper.map(notesDto,NotesEntity.class);
        notesEntity.setEmail(email);
        NotesEntity savedNotes = notesRepository.save(notesEntity);
        NotesResponseDto notesResponseDto = modelMapper.map(savedNotes,NotesResponseDto.class);
        return ResponseEntity.ok(notesResponseDto);
    }

    public ResponseEntity<List<NotesResponseDto>> getAllNotes(){
        String email = UserContext.getEmail();
        List<NotesEntity> notesEntities = notesRepository.findAllByEmail(email);
        List<NotesResponseDto> notesResponseDtos = notesEntities.stream()
                .map(notesEntity -> {
                    return modelMapper.map(notesEntity,NotesResponseDto.class);
                }).toList();
        return ResponseEntity.ok(notesResponseDtos);
    }

    public ResponseEntity<NotesResponseDto> getNoteById(Long id) {
        Optional<NotesEntity> notesEntity = notesRepository.findById(id);
        if (notesEntity.isEmpty()==true){
            return ResponseEntity.notFound().build();
        }
        NotesEntity notesEntity1 = notesEntity.get();
        NotesResponseDto notesResponseDto = modelMapper.map(notesEntity1,NotesResponseDto.class);
        return ResponseEntity.ok(notesResponseDto);
    }

    public ResponseEntity<NotesResponseDto> updateNoteById(Long id, NotesUpdateDto notesUpdateDto){
        Optional<NotesEntity> notesEntity = notesRepository.findById(id);
        if (notesEntity.isEmpty()==true){
            return ResponseEntity.notFound().build();
        }
        NotesEntity notesEntity1 = notesEntity.get();
        if(notesUpdateDto.getTitle()!=null){
            notesEntity1.setTitle(notesUpdateDto.getTitle());
        }
        if(notesUpdateDto.getDescription()!=null){
            notesEntity1.setDescription(notesUpdateDto.getDescription());
        }
        NotesEntity savedNotes = notesRepository.save(notesEntity1);
        NotesResponseDto notesResponseDto = modelMapper.map(savedNotes, NotesResponseDto.class);
        return ResponseEntity.ok(notesResponseDto);
    }


}
