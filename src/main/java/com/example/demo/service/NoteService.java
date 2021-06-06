package com.example.demo.service;

import com.example.demo.model.Notes;

import java.util.List;

/**
 * @author RafaelBizi
 * @project cloud-storage-project-1
 */

public interface NoteService {

    int addNote(Notes note);
    void deleteNote(int id);
    void updateNote(Notes note);
    List<Notes> getAllNotes(int userId);
    Notes findNote(int id);
}
