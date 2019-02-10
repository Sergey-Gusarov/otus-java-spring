package ru.sergey_gusarov.hw6.dao.books.dict;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.sergey_gusarov.hw6.domain.books.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Repository
public class DictAuthorDaoJdbc implements DictAuthorRepository {
    public static final String SQL_COUNT = "select count(*) from " + DictAuthorRepository.TABLE_NAME;
    public static final String SQL_FIND_ALL = "select * from " + DictAuthorRepository.TABLE_NAME;
    public static final String SQL_FIND_BY_ID = SQL_FIND_ALL + " where " + DictAuthorRepository.ID_COLUMN +
            " = :" + DictAuthorRepository.ID_COLUMN;
    public static final String SQL_FIND_BY_NAME = SQL_FIND_ALL + " where " + DictAuthorRepository.NAME_COLUMN +
            " = :" + DictAuthorRepository.NAME_COLUMN;
    public static final String SQL_INSERT = "insert into " + DictAuthorRepository.TABLE_NAME +
            " (" + DictAuthorRepository.ID_COLUMN + ", " + DictAuthorRepository.NAME_COLUMN +
            ") values (:" + DictAuthorRepository.ID_COLUMN + " ,:" + DictAuthorRepository.NAME_COLUMN + ")";
    public static final String SQL_UPDATE = "update " + DictAuthorRepository.TABLE_NAME +
            " set " + DictAuthorRepository.NAME_COLUMN + " = ? where " + DictAuthorRepository.ID_COLUMN +
            " = :" + DictAuthorRepository.ID_COLUMN;
    public static final String SQL_DELETE = "delete from " + DictAuthorRepository.TABLE_NAME +
            " where " + DictAuthorRepository.ID_COLUMN + " = :" + DictAuthorRepository.ID_COLUMN;

    private final NamedParameterJdbcOperations jdbc;

    public DictAuthorDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int count() {
        return jdbc.queryForObject(SQL_COUNT, Collections.EMPTY_MAP, Integer.class);
    }

    @Override
    public int insert(Author author) {
        final HashMap<String, Object> params = new HashMap<>(2);
        params.put(DictAuthorRepository.ID_COLUMN, author.getId());
        params.put(DictAuthorRepository.NAME_COLUMN, author.getName());
        return jdbc.update(SQL_INSERT, params);
    }

    @Override
    public Author getById(int id) {
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put(DictAuthorRepository.ID_COLUMN, id);
        return jdbc.queryForObject(SQL_FIND_BY_ID, params, new AuthorMapper());
    }

    @Override
    public Author getByName(String name) {
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put(DictAuthorRepository.NAME_COLUMN, name);
        return jdbc.queryForObject(SQL_FIND_BY_NAME, params, new AuthorMapper());
    }

    @Override
    public List<Author> findAll() {
        return jdbc.query(SQL_FIND_ALL, new AuthorMapper());
    }

    @Override
    public int update(Author author) {
        final HashMap<String, Object> params = new HashMap<>(2);
        params.put(DictAuthorRepository.ID_COLUMN, author.getId());
        params.put(DictAuthorRepository.NAME_COLUMN, author.getName());
        return jdbc.update(SQL_UPDATE, params);
    }

    @Override
    public int delete(Author author) {
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put(DictAuthorRepository.ID_COLUMN, author.getId());
        return jdbc.update(SQL_DELETE, params);
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt(DictAuthorRepository.ID_COLUMN);
            String name = resultSet.getString(DictAuthorRepository.NAME_COLUMN);
            return new Author(id, name);
        }
    }

}
