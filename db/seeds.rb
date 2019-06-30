CREATE TABLE t_random AS SELECT s, md5(random()::text) FROM generate_Series(1,5) s;
INSERT INTO t_random VALUES (generate_series(1,1000000000), md5(random()::text));

SELECT pg_size_pretty(pg_relation_size('t_random'));
