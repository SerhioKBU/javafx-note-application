package com.javafx.exampl.service;

import com.javafx.exampl.dao.DaoException;
import com.javafx.exampl.dao.NoteDao;
import com.javafx.exampl.entity.Note;
import lombok.SneakyThrows;

import java.util.List;

public class NoteService {

    private NoteDao noteDao = new NoteDao();


    @SneakyThrows
    public Note create(Note note){
        return noteDao.create(note);
    }

    @SneakyThrows
    public List<Note> findAll() throws ServiceException {
        return noteDao.findAll();
    }

    @SneakyThrows
    public void delete(Note note) {
        noteDao.delete(note);
    }
}
