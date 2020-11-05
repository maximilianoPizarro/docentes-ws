package com.docentes.dao.impl;

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

import com.docentes.dao.MateriasDao;
import com.docentes.model.Materia;

@Repository
public class MateriasDaoImpl implements MateriasDao {

    private static final Logger logger = LogManager.getLogger(MateriasDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public MateriasDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Materia> findAllPureJdbc() {
        List<Materia> results = new LinkedList<Materia>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Materia");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
            	//(int idMateria, String nombre, Date inicioInscripcion, Date finInscripcion, int carrerasIdCarreras,
    			//Date createdAt, Date updatedAt, int planIdPlan, int formaAprobacionIdformaAprobacion)
                Materia Materia = new Materia(resultSet.getInt("idMaterias"), 
                		resultSet.getString("nombre"), resultSet.getDate("inicioInscripcion"),
                		resultSet.getDate("finInscripcion"),resultSet.getInt("carrerasIdCarreras"),
                		resultSet.getDate("createdAt"), resultSet.getDate("updatedAt"),       
                		resultSet.getInt("planIdPlan"),resultSet.getInt("formaAprobacionIdformaAprobacion"));

                results.add(Materia);
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

    public List<Materia> findAll() {
        return jdbcTemplate.query("SELECT * FROM Materia", new MateriaRowMapper());
    }

    public List<Materia> findByName(String nombre) {
        return jdbcTemplate.query("SELECT * FROM Materia WHERE nombre LIKE ?", new Object[] { nombre },
                new MateriaRowMapper());
    }
    
    public List<Materia> findByDocente(int idDocente) {
    	String SQL_QUERY = "SELECT * FROM materias inner join curso on materias.idMaterias = curso.MateriasIdMaterias " + 
    	 "where JSON_UNQUOTE(datosDocente->\"$.id\") = ? ;";
    	System.out.print(SQL_QUERY);
    	
        return jdbcTemplate.query(SQL_QUERY, new Object[] { idDocente },
                new MateriaRowMapper());
    }

    public Materia findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Materia WHERE idMaterias = ?",
                new Object[] { id }, new MateriaRowMapper());
    }

    public int count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Materia", Integer.class);
    }

    public int deleteAll() {
        return jdbcTemplate.update("DELETE from Materia");
    }

    public void insertWithQuery(String name, int population) {
        jdbcTemplate.update("INSERT INTO Materia (name, population) VALUES(?,?)", name, population);
    }

    public void insertBatch(List<Materia> countries, int batchSize) {
        String sql = "INSERT INTO Materia (name, population) VALUES(?,?)";

        jdbcTemplate.batchUpdate(sql, countries, batchSize,
                (PreparedStatement ps, Materia Materia) -> {
                    ps.setString(1, Materia.getNombre());
                }
        );
    }

    public long insert(String name, int population) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("Materia").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("name", name);
        parameters.put("population", population);
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

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

    private static class MateriaRowMapper implements RowMapper<Materia> {
        @Override
        public Materia mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return  new Materia(resultSet.getInt("idMaterias"), 
            		resultSet.getString("nombre"), resultSet.getDate("inicioInscripcion"),
            		resultSet.getDate("finInscripcion"),resultSet.getInt("carrerasIdCarreras"),
            		resultSet.getDate("createdAt"), resultSet.getDate("updatedAt"),       
            		resultSet.getInt("planIdPlan"),resultSet.getInt("formaAprobacionIdformaAprobacion"));
        }
    }

}