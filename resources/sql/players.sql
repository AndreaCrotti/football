-- name: fetch-players
-- Fetch all the players
SELECT * FROM players;

-- name: insert-player<!
-- Create a new player
INSERT INTO players (name, position, skills) VALUES (:name, :position, :skills::json);
