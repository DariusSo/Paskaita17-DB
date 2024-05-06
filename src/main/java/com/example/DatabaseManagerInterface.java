package com.example;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseManagerInterface {

    void registruotiKlienta(Klientas klientas) throws SQLException;
}
