package com.docentes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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

import com.docentes.model.Materia;


public interface MateriasDao {

    
    public List<Materia> findAllPureJdbc();

    public List<Materia> findAll();

    public List<Materia> findByName(String nombre);
    
    public List<Materia> findByDocente(int idDocente);

    public Materia findById(Long id);

    public int count();

    public int deleteAll();

    public void insertWithQuery(String name, int population);

    public void insertBatch(List<Materia> countries, int batchSize); 

    public long insert(String name, int population);

    public Integer callProcedure(String name);

    public Integer callFunction(String name);

}