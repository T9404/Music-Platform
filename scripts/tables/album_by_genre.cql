CREATE TABLE example.album_by_genre
(
    genre        TEXT,
    album_name   TEXT,
    signer_name  TEXT,
    release_date TIMESTAMP,
    songs        SET<FROZEN<example.song>>,
    PRIMARY KEY ((genre), signer_name)
) WITH CLUSTERING ORDER BY (signer_name ASC);
