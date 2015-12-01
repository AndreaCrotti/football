-- name: create-skills-table
-- Create skills table
CREATE TABLE skills (
       id serial PRIMARY KEY,
       control int,
       speed int,
       dribbling int,
       shoot int
);

-- name: create-players-table
-- Create players table
CREATE TABLE players (
       --TODO: should use something that is not an id maybe?
       id serial PRIMARY KEY,
       name varchar(256),
       --TODO: this should probably be an enum or a relationship
       position varchar(10),
       skills json
);

-- name: create-teams-table
-- Create teams table
CREATE TABLE teams (
       id serial PRIMARY KEY,
       -- player int references players(id)
       --TODO: this is how we could use the JSON storage
       players json
);

-- name: create-games-table
-- Create games table
CREATE TABLE games (
       id serial PRIMARY KEY,
       --TODO: might be another good case for a JSON structure
       team1 int references teams(id),
       team2 int references teams(id),
       team1_goals int,
       team2_goals int,
       day date
);
