package edu.esprit.services;

<<<<<<< HEAD
import java.sql.SQLException;
import java.util.Set;

public interface IService <T> {
    public void ajouter(T t) throws SQLException;
    public void modifier(T t) throws SQLException;
    public void supprimer(int id) throws SQLException;
    public T getOneById(int id) throws SQLException;
    public Set<T> getAll() throws SQLException;
=======
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
>>>>>>> f12a914103991af015b68c0d57be6adf7314bf24
}
