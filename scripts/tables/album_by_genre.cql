CREATE TABLE example.album_by_genre
(
    genre        TEXT,
    album_name   TEXT,
    singer_name  TEXT,
    release_date TIMESTAMP,
    songs        SET<FROZEN<example.song>>,
    PRIMARY KEY ((genre), singer_name)
) WITH CLUSTERING ORDER BY (singer_name ASC);
