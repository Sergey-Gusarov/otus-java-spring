package ru.sergey_gusarov.hw6.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.stereotype.Component;
import ru.sergey_gusarov.hw6.dao.books.dict.DictGenreDao;
import ru.sergey_gusarov.hw6.dao.books.dict.DictGenreDaoJdbc;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Types;

@Component
public class InsertGenre extends BatchSqlUpdate {
    public static final String ID_PARAMETER = DictGenreDao.ID_COLUMN;
    public static final String NAME_PARAMETER = DictGenreDao.NAME_COLUMN;
    private static final String SQL_INSERT_GENRE = DictGenreDaoJdbc.SQL_INSERT;;
    private static final int BATCH_SIZE = 10;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void init() {
        setDataSource(dataSource);
        setSql(SQL_INSERT_GENRE);
        declareParameter(new SqlParameter(ID_PARAMETER, Types.INTEGER));
        declareParameter(new SqlParameter(NAME_PARAMETER, Types.VARCHAR));
        setBatchSize(BATCH_SIZE);
    }
}