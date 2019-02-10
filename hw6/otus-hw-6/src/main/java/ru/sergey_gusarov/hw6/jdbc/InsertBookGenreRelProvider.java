package ru.sergey_gusarov.hw6.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Types;

@Component
public class InsertBookGenreRelProvider extends BatchSqlUpdate {
	public static final String TABLE_NAME = "BOOK_GENRE_REL";
	public static final String BOOK_ID_COLUMN = "BOOK_ID";
	public static final String GENRE_ID_COLUMN = "GENRE_ID";
	
	public static final String BOOK_ID_PARAMETER = "BOOK_ID";
	public static final String GENRE_ID_PARAMETER = "GENRE_ID";
	
	private static final String SQL_INSERT_BOOKS_GENRES = "INSERT INTO " + TABLE_NAME
			+ " (" + BOOK_ID_COLUMN + ", " + GENRE_ID_COLUMN + ")"
			+ " VALUES (:" + BOOK_ID_PARAMETER + ", :" + GENRE_ID_PARAMETER + ")";
	
	private static final int BATCH_SIZE = 10;
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	public void init() {
		setDataSource(dataSource);
		setSql(SQL_INSERT_BOOKS_GENRES);
		setBatchSize(BATCH_SIZE);
		declareParameter(new SqlParameter(BOOK_ID_PARAMETER, Types.INTEGER));
		declareParameter(new SqlParameter(GENRE_ID_PARAMETER, Types.INTEGER));
	}
}