<<<<<<< HEAD
<<<<<<< HEAD
package com.example.reclamation.services;
=======
package edu.esprit.services;
>>>>>>> ba038a7 (metiers+api)
=======
package edu.esprit.services;
>>>>>>> GestionReclamations

import java.sql.SQLException;
import java.util.List;

public interface CRUD<T> {
    void insertOne(T t) throws SQLException;
    void updateOne(T t) throws SQLException;
    void deleteOne(T t) throws SQLException;
    List<T> selectAll() throws SQLException;
    T selectOne_by_id(int id)throws SQLException;

}
