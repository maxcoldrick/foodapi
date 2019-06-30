INSERT INTO foods VALUES (generate_series(1,1000000000), md5(random()::text));

SELECT pg_size_pretty(pg_relation_size('foods'));
