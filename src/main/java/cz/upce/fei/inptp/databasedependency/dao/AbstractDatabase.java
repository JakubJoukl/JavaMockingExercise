package cz.upce.fei.inptp.databasedependency.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

public interface AbstractDatabase {
    public void open();
    public Statement createStatement() throws SQLException;
    public PreparedStatement prepareStatement(String sql) throws SQLException;
    public void commit() throws SQLException;
    public void rollback() throws SQLException;
    public void close() throws SQLException;
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException;
    public void rollback(Savepoint savepoint) throws SQLException;
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException;
}
