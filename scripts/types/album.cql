CREATE TYPE example.album
(
    name        TEXT,
    genre       TEXT,
    owner       TEXT,
    releaseDate TIMESTAMP,
    songs       SET<FROZEN<example.song>>
);
