package edu.esprit.services;

<<<<<<< HEAD

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public interface IService<T>{
    public List<T> getAll();
    public void supprimer(T t) throws SQLException ;
    public int ajouter(T t) throws SQLException, NoSuchAlgorithmException;
    public int modifer(T t,T t2) throws SQLException;
=======
import java.sql.SQLException;
import java.util.Set;

public interface IService <T> {
    public void ajouter(T t) throws SQLException;
    public void modifier(T t) throws SQLException;
    public void supprimer(int id) throws SQLException;
    public T getOneById(int id) throws SQLException;
    public Set<T> getAll() throws SQLException;
>>>>>>> daf1b4baae6ebf60433e287beadfdc6a98f3144f
}
