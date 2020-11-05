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

import com.docentes.dao.AlumnosExamenFinalDao;
import com.docentes.model.AlumnoExamenFinal;

@Repository
public class AlumnosExamenFinalDaoImpl implements AlumnosExamenFinalDao {

    private static final Logger logger = LogManager.getLogger(AlumnosExamenFinalDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public AlumnosExamenFinalDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AlumnoExamenFinal> findAllPureJdbc() {
        List<AlumnoExamenFinal> results = new LinkedList<AlumnoExamenFinal>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM AlumnoExamenFinal");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
            	//idInscriptosExamen, ExamenesidExamenes, datosAlumno, nota, asistencia, recordatorio, createdAt, updatedAt
                AlumnoExamenFinal AlumnoExamenFinal = new AlumnoExamenFinal(resultSet.getInt("idInscriptosExamen"), resultSet.getString("datosAlumno"),
                		resultSet.getInt("nota"), resultSet.getInt("asistencia"), resultSet.getInt("ExamenesidExamenes"),resultSet.getInt("recordatorio"), 
                		resultSet.getDate("createdAt"),resultSet.getDate("updatedAt"));

                results.add(AlumnoExamenFinal);
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

    public List<AlumnoExamenFinal> findAll() {
        return jdbcTemplate.query("SELECT * FROM AlumnoExamenFinal", new AlumnoExamenFinalRowMapper());
    }

    public List<AlumnoExamenFinal> findByName(String name) {
        return jdbcTemplate.query("SELECT * FROM AlumnoExamenFinal WHERE name LIKE ?", new Object[] { name },
                new AlumnoExamenFinalRowMapper());
    }

    public AlumnoExamenFinal findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM AlumnoExamenFinal WHERE idInscriptosExamen = ?",
                new Object[] { id }, new AlumnoExamenFinalRowMapper());
    }

    public int count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM AlumnoExamenFinal", Integer.class);
    }

    public int deleteAll() {
        return jdbcTemplate.update("DELETE from AlumnoExamenFinal");
    }
/*
    public void insertWithQuery(String name, int population) {
        jdbcTemplate.update("INSERT INTO AlumnoExamenFinal (name, population) VALUES(?,?)", name, population);
    }

    public long insert(String name, int population) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("AlumnoExamenFinal").usingGeneratedKeyColumns("id");

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

    private static class AlumnoExamenFinalRowMapper implements RowMapper<AlumnoExamenFinal> {
        @Override
        public AlumnoExamenFinal mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return  new AlumnoExamenFinal(resultSet.getInt("idInscriptosExamen"), resultSet.getString("datosAlumno"),
            		resultSet.getInt("nota"), resultSet.getInt("asistencia"), resultSet.getInt("ExamenesidExamenes"),resultSet.getInt("recordatorio"), 
            		resultSet.getDate("createdAt"),resultSet.getDate("updatedAt"));

        }
    }
    
    //UPDATE NOTA
    public int updateNotaAlumnoExamenFinal(AlumnoExamenFinal AlumnoExamenFinal) {

	 int[] types = {Types.INTEGER, Types.BIGINT};    	  
	 return jdbcTemplate.update("UPDATE alumnosexamenfinal  SET nota = ? where idInscriptosExamen = ?",
    			  new Object[]{ AlumnoExamenFinal.getNota(), AlumnoExamenFinal.getIdInscriptosExamen()}, types);
    }
    

}