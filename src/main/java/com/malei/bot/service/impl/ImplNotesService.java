package com.malei.bot.service.impl;

import com.malei.bot.service.INotesService;
import com.malei.bot.dao.impl.NotesDao;
import com.malei.bot.entities.Notes;

import java.util.List;

public class ImplNotesService implements INotesService {

    NotesDao notesDao = new NotesDao();

    @Override
    public void createNotes(Notes ob) {
        notesDao.create(ob);
    }

    @Override
    public void updateNotes(Notes ob) {
        notesDao.update(ob);
    }

    @Override
    public void deleteNotes(Notes ob) {
        notesDao.delete(ob);
    }

    @Override
    public List<Notes> getAllNotes() {
        return notesDao.getAll();
    }

    @Override
    public void deleteAll() {
        notesDao.deleteAll();
    }

    @Override
    public Notes getNotesByID(Integer id) {
        return notesDao.getById(id);
    }
}
