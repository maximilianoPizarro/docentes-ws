package com.docentes.dao.impl;

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

import com.docentes.dao.AlumnosCursadaDao;
import com.docentes.model.AlumnoCursada;

@Repository
public class AlumnosCursadaDaoImpl implements AlumnosCursadaDao{

    private static final Logger logger = LogManager.getLogger(AlumnosCursadaDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public AlumnosCursadaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AlumnoCursada> findAllPureJdbc() {
        List<AlumnoCursada> results = new LinkedList<AlumnoCursada>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM AlumnoCursada");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
            	//idalumnosCursada, datosAlumno, notaCursada, MateriasIdMaterias, recordatorio, createdAt, updatedAt
                AlumnoCursada AlumnoCursada = new AlumnoCursada(resultSet.getInt("idalumnosCursada"), resultSet.getString("datosAlumno"),
                		resultSet.getInt("notaCursada"), resultSet.getInt("MateriasIdMaterias"),resultSet.getInt("recordatorio"), 
                		resultSet.getDate("createdAt"),resultSet.getDate("updatedAt"));

                results.add(AlumnoCursada);
            }
        } catch (SQLException ex) {
            logger.error(ex);
            throw new RuntimeException(ex);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    logger.warn(ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    logger.warn(ex);
                }
            }
        }

        return results;
    }

    public List<AlumnoCursada> findAll() {
        return jdbcTemplate.query("SELECT * FROM AlumnoCursada", new AlumnoCursadaRowMapper());
    }

    public List<AlumnoCursada> findByName(String name) {
        return jdbcTemplate.query("SELECT * FROM AlumnoCursada WHERE name LIKE ?", new Object[] { name },
                new AlumnoCursadaRowMapper());
    }

    public AlumnoCursada findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM AlumnoCursada WHERE idalumnosCursada = ?",
                new Object[] { id }, new AlumnoCursadaRowMapper());
    }
    
    public List<AlumnoCursada> findByPorDocenteYMateria(int idDocente, int idMateria) {
    	
	   String SQL_QUERY ="SELECT idalumnosCursada,datosAlumno,notaCursada, alumnoscursada.MateriasIdMaterias  FROM alumnoscursada " + 
    		   		"inner join curso on alumnoscursada.MateriasIdMaterias = curso.MateriasIdMaterias " + 
    		   		"where alumnoscursada.MateriasIdMaterias = ? and JSON_UNQUOTE(datosDocente->\"$.id\") = ? " ;
    	
	   System.out.print(SQL_QUERY);
       return jdbcTemplate.query(SQL_QUERY,
                new Object[] { idMateria, idDocente }, new AlumnoCursadaRowMapper());
    }

    public int count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM AlumnoCursada", Integer.class);
    }

    public int deleteAll() {
        return jdbcTemplate.update("DELETE from AlumnoCursada");
    }
/*
    public void insertWithQuery(String name, int population) {
        jdbcTemplate.update("INSERT INTO AlumnoCursada (name, population) VALUES(?,?)", name, population);
    }

    public long insert(String name, int population) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("AlumnoCursada").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("name", name);
        parameters.put("population", population);
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }
*/

    public Integer callProcedure(String name) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("search");

        SqlParameterSource in = new MapSqlParameterSource().addValue("name", name);

        Map<String, Object> out = simpleJdbcCall.execute(in);
        return (Integer) out.get("total");
    }

    public Integer callFunction(String name) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withFunctionName("search2");

        SqlParameterSource in = new MapSqlParameterSource().addValue("name", name);

        return simpleJdbcCall.executeFunction(Integer.class, in);
    }

    private static class AlumnoCursadaRowMapper implements RowMapper<AlumnoCursada> {
        @Override
        public AlumnoCursada mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return new AlumnoCursada(resultSet.getInt("idalumnosCursada"), resultSet.getString("datosAlumno"),
            		resultSet.getInt("notaCursada"), resultSet.getInt("MateriasIdMaterias"),resultSet.getInt("recordatorio"), 
            		resultSet.getDate("createdAt"),resultSet.getDate("updatedAt"));
        }
    }
    
    //UPDATE NOTA
    public int updateNotaAlumnoCursada(AlumnoCursada alumnoCursada) {
//update alumnoscursada set notaCursada = 1 where idalumnosCursada = 10 
    	 
	 int[] types = {Types.VARCHAR, Types.BIGINT};    	  
	 return jdbcTemplate.update("UPDATE AlumnosCursada  SET notaCursada = ? where idalumnosCursada = ?",
    			  new Object[]{ alumnoCursada.getNotaCursada(), alumnoCursada.getIdalumnosCursada()}, types);
    }
    

}