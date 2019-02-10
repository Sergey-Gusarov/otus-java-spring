package ru.sergey_gusarov.hw6.dao.books.dict;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.sergey_gusarov.hw6.domain.books.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Repository
public class DictGenreDaoJdbc implements DictGenreDao {
    public static final String SQL_COUNT = "select count(*) from " + DictGenreDao.TABLE_NAME;
    public static final String SQL_FIND_ALL = "select * from " + DictGenreDao.TABLE_NAME;
    public static final String SQL_FIND_BY_ID = SQL_FIND_ALL + " where " + DictGenreDao.ID_COLUMN +
            " = :" + DictGenreDao.ID_COLUMN;
    public static final String SQL_FIND_BY_NAME = SQL_FIND_ALL + " where " + DictGenreDao.NAME_COLUMN +
            " = :" + DictGenreDao.NAME_COLUMN;
    public static final String SQL_INSERT = "insert into " + DictGenreDao.TABLE_NAME +
            " (" + DictGenreDao.ID_COLUMN + ", " + DictGenreDao.NAME_COLUMN +
            ") values (:" + DictGenreDao.ID_COLUMN + " ,:" + DictGenreDao.NAME_COLUMN + ")";
    public static final String SQL_UPDATE = "update " + DictGenreDao.TABLE_NAME +
            " set " + DictGenreDao.NAME_COLUMN + " = ? where " + DictGenreDao.ID_COLUMN +
            " = :" + DictGenreDao.ID_COLUMN;
    public static final String SQL_DELETE = "delete from " + DictGenreDao.TABLE_NAME +
            " where " + DictGenreDao.ID_COLUMN + " = :" + DictGenreDao.ID_COLUMN;

    private final NamedParameterJdbcOperations jdbc;

    public DictGenreDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int count() {
        return jdbc.queryForObject(SQL_COUNT, Collections.EMPTY_MAP, Integer.class);
    }

    @Override
    public int insert(Genre genre) {
        final HashMap<String, Object> params = new HashMap<>(2);
        params.put(DictGenreDao.ID_COLUMN, genre.getId());
        params.put(DictGenreDao.NAME_COLUMN, genre.getName());
        return jdbc.update(SQL_INSERT, params);
    }

    @Override
    public Genre getById(int id) {
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put(DictGenreDao.ID_COLUMN, id);
        return jdbc.queryForObject(SQL_FIND_BY_ID, params, new GenreMapper());
    }

    @Override
    public Genre getByName(String name) {
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put(DictGenreDao.NAME_COLUMN, name);
        return jdbc.queryForObject(SQL_FIND_BY_NAME, params, new GenreMapper());
    }

    @Override
    public List<Genre> findAll() {
        return jdbc.query(SQL_FIND_ALL, new GenreMapper());
    }

    @Override
    public int update(Genre genre) {
        final HashMap<String, Object> params = new HashMap<>(2);
        params.put(DictGenreDao.ID_COLUMN, genre.getId());
        params.put(DictGenreDao.NAME_COLUMN, genre.getName());
        return jdbc.update(SQL_UPDATE, params);
    }

    @Override
    public int delete(Genre genre) {
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put(DictGenreDao.ID_COLUMN, genre.getId());
        return jdbc.update(SQL_DELETE, params);
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt(DictGenreDao.ID_COLUMN);
            String name = resultSet.getString(DictGenreDao.NAME_COLUMN);
            return new Genre(id, name);
        }
    }
}
