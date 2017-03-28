package com.malei.bot.service;

import com.malei.bot.entities.Notes;

import java.util.List;

public interface INotesService {

    public void createNotes(Notes ob);

    public void updateNotes(Notes ob);

    public void deleteNotes (Notes ob);

    public List<Notes> getAllNotes();

    public void deleteAll();

    public Notes getNotesByID(Integer id);

}
