CREATE TABLE
  streets (
    id INTEGER GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(50) NOT NULL,
    post_index INTEGER NOT NULL,
    PRIMARY KEY (id)
  );

CREATE TABLE
  houses (
    id INTEGER GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(50) NOT NULL,
    date DATE,
    floors INTEGER NOT NULL,
    type VARCHAR(10) DEFAULT 'Unknown',
    street INTEGER references streets (id),
    PRIMARY KEY (id)
  );
