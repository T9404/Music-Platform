CREATE TABLE example.album_by_singer
(
    singer_name  TEXT,
    genre        TEXT,
    album_name   TEXT,
    release_date TIMESTAMP,
    songs        SET<FROZEN<example.song>>,
    PRIMARY KEY ((singer_name), album_name)
) WITH CLUSTERING ORDER BY (album_name ASC);
