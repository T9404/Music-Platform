package ru.cft.shift.intensive.template.entity;

import java.sql.Timestamp;
import java.util.Set;

public interface Albums {
    Timestamp getReleaseDate();
    Set<Song> getSongs();
}
