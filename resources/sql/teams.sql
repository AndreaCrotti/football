-- name: insert-team<!
-- Create a new team
INSERT INTO teams (players) VALUES (:players::json);

-- name: fetch-teams
-- Fetch all the teams
SELECT * FROM teams;
