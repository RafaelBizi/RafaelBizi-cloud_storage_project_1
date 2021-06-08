package com.example.demo;

import com.example.demo.mapper.NoteMapper;
import com.example.demo.model.Notes;
import com.example.demo.service.NoteService;
import com.example.demo.service.implimentation.NoteServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author RafaelBizi
 * @project cloud-storage-project-1
 */

public class NoteTesting extends ApplicationTests{

    @Autowired
    private NoteMapper noteMapper;

    @Test
    public void createReadTest() throws InterruptedException {
        String noteTitle = "Note test";
        String noteDescription = "Note testing description.";
        HomePage homePage= getHomePage();
        assertEquals("Home", driver.getTitle());
        createNote(noteTitle,noteDescription,homePage);
        homePage.openNotesTab();
        homePage = new HomePage(driver);
        Notes note = homePage.getFirstNote();
        Assertions.assertEquals(noteTitle, note.getNoteTitle());
        Assertions.assertEquals(noteDescription, note.getNoteDescription());
        deleteNote(homePage);
        Thread.sleep(500);
        homePage.logout();
    }
    @Test
    public void noteUpdateTest() throws InterruptedException {
        String noteTitle = "My Note";
        String noteDescription = "This is my note test.";
        HomePage homePage = getHomePage();
        createNote(noteTitle, noteDescription, homePage);
        homePage.openNotesTab();
        homePage = new HomePage(driver);
        homePage.editNote();
        String modifiedNoteTitle = "Modified Note";
        homePage.modifyNoteTitle(modifiedNoteTitle);

        String modifiedNoteDescription = "This is my modified note.";
        homePage.modifyNoteDescription(modifiedNoteDescription);

        homePage.saveNoteChanges();
        homePage.openNotesTab();
        Notes note = homePage.getFirstNote();
        Assertions.assertEquals(modifiedNoteTitle, note.getNoteTitle());
        Assertions.assertEquals(modifiedNoteDescription, note.getNoteDescription());
    }
    @Test
    public void deleteNoteTest() throws InterruptedException {
        String noteTitle = "My Note";
        String noteDescription = "This is my note.";
        HomePage homePage = getHomePage();
        createNote(noteTitle, noteDescription, homePage);
        homePage.openNotesTab();
        homePage = new HomePage(driver);
        Thread.sleep(500);
        Assertions.assertFalse(homePage.noNotes(driver));

        Thread.sleep(500);
        deleteNote(homePage);

        NoteServiceImpl noteService = new NoteServiceImpl(noteMapper);
        List<Notes> notesListResult = noteService.getAllNotes(1);
        boolean isEmpty = notesListResult.isEmpty();
        Assertions.assertEquals(true, isEmpty);

        Thread.sleep(500);
        Assertions.assertTrue(homePage.noNotes(driver));
    }

    private void createNote(String noteTitle, String noteDescription, HomePage homePage) {
        homePage.openNotesTab();
        homePage.addNewNote();
        homePage.setNoteTitle(noteTitle);
        homePage.setNoteDescription(noteDescription);
        homePage.saveNoteChanges();
        homePage.openNotesTab();
    }

    private void deleteNote(HomePage homePage) {
        homePage.deleteNote();
    }

}
