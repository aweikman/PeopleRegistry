-- dob is set by  default

INSERT INTO people(first_name,  last_name)
SELECT firstname, lastname
FROM firstnames, lastnames
ORDER BY RAND() LIMIT 10000;

-- resets AI back to 0 in order to re-add all people from id 1
TRUNCATE TABLE people;

