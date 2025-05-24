package com.example.notes_app.Notes.App.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotesResponseDto {
    private String title;
    private String description;
    private String email;
    private Long id;
}
