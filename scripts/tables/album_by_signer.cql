CREATE TABLE example.album_by_signer
(
    signer_name  TEXT,
    genre        TEXT,
    album_name   TEXT,
    release_date TIMESTAMP,
    songs        SET<FROZEN<example.song>>,
    PRIMARY KEY ((signer_name), album_name)
) WITH CLUSTERING ORDER BY (album_name ASC);
