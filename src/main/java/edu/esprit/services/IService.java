package edu.esprit.services;

import edu.esprit.entities.User;
import javafx.collections.ObservableList;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public interface IService<T>{
    public List<T> getAll();
    public void supprimer(T t) throws SQLException ;
    public int ajouter(T t) throws SQLException, NoSuchAlgorithmException;
    public int modifer(T t,T t2) throws SQLException;
}
