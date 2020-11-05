package com.docentes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.docentes.model.AlumnoCursada;


public interface AlumnosCursadaDao {


    public List<AlumnoCursada> findAllPureJdbc();

    public List<AlumnoCursada> findAll();

    public List<AlumnoCursada> findByName(String name);


    public AlumnoCursada findById(Long id);
    
    public List<AlumnoCursada> findByPorDocenteYMateria(int idDocente, int idMateria);


    public int count();

    public int deleteAll();


    public Integer callProcedure(String name);

    public Integer callFunction(String name);

        
    //UPDATE NOTA
    public int updateNotaAlumnoCursada(AlumnoCursada alumnoCursada);

}