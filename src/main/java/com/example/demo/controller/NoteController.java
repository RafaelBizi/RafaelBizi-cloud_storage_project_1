package com.example.demo.controller;

import com.example.demo.model.Notes;
import com.example.demo.model.User;
import com.example.demo.service.NoteService;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @author RafaelBizi
 * @project cloud-storage-project-1
 */

@Controller
@RequestMapping("/notes")
public class NoteController {
    private Logger logger = LoggerFactory.getLogger(NoteController.class);

    private String redirectPath = "redirect:/home";
    
    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;

    @GetMapping
    public List<Notes> getNotes(int userId){
        return noteService.getAllNotes(userId);
    }

    @PostMapping
    public String addNote(Authentication authentication, Notes note, RedirectAttributes redirectAttributes){
        String username= authentication.getName();
        User user = userService.getUser(username);

        if(note.getNoteId().intValue() > 0){
            try{
                noteService.updateNote(note);
                redirectAttributes.addFlashAttribute("successMessage", "Your note was updated successful.");
                return redirectPath;
            }catch (Exception ex){
                logger.error("Error: " + ex.getCause() + ". Message: " + ex.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the note update. Please try again!");
                return redirectPath;
            }
        } else {
            try{
                note.setUserId(user.getUserId());
                noteService.addNote(note);
                redirectAttributes.addFlashAttribute("successMessage", "The note was added properly");
                return redirectPath;
            }catch (Exception ex){
                logger.error("Error: " + ex.getCause() + ". Message: " + ex.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the note creation. Please try again.");
                return redirectPath;
            }

        }
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable int noteId, RedirectAttributes redirectAttributes) {
        try {
            noteService.deleteNote(noteId);
            redirectAttributes.addFlashAttribute("successMessage", "The noteId=" + noteId + " was deleted properly.");
            return redirectPath;
        } catch (Exception e) {
            logger.error("Error: " + e.getCause() + ". Message: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the note delete. Please try again!");
            return redirectPath;
        }
    }
}
