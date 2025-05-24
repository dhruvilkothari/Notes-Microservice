package com.example.notes_app.Notes.App.repository;

import com.example.notes_app.Notes.App.entity.NotesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepository extends JpaRepository<NotesEntity, Long> {
    List<NotesEntity> findAllByEmail(String email);
}
