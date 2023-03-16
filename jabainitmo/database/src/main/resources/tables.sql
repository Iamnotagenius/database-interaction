CREATE TABLE
  streets (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    post_index INTEGER NOT NULL
  );

CREATE TABLE
  houses (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    date DATE,
    floors INTEGER NOT NULL,
    type VARCHAR(11) DEFAULT 'Unknown',
    street INTEGER references streets (id)
  );
