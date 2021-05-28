SELECT pg_catalog.setval(pg_get_serial_sequence('account_role', 'id'), (SELECT MAX(id) FROM account_role)+1);

SELECT pg_catalog.setval(, (SELECT MAX(id) FROM account_role)+1);

SELECT pg_get_serial_sequence('account_role', 'id');

SELECT pg_catalog.setval(pg_get_serial_sequence('account_role', 'id'), MAX(id)) FROM account_role;

select pg_get_serial_sequence('account', 'id');



SELECT setval(, (SELECT MAX(id) FROM account_role));
select pg_get_serial_sequence('account_role_pkey');
SELECT MAX(id) FROM account_role;
SELECT MAX(account_role_pkey) FROM account_role;
SELECT setval(pg_get_serial_sequence('account_role', 'id'), MAX(id)) FROM account_role;
SELECT nextval('account_role_pkey');

SELECT MAX(id) FROM account_role;

SELECT nextval('the_primary_key_sequence');



SELECT table_catalog, table_schema, column_name, table_name, column_default
FROM information_schema.columns
WHERE table_name = 'account_role' AND column_name = 'id';

SELECT table_catalog, table_schema, column_name, table_name, column_default
FROM information_schema.columns
WHERE table_name = 'account' AND column_name = 'id';

SELECT nextval('account_role_id_seq');


ALTER SEQUENCE seq RESTART WITH 1;
UPDATE t SET idcolumn=nextval('seq');


select * from public.account_role;
SELECT currval('accountrole_id_seq');
SELECT nextval('accountrole_id_seq');
SELECT setval(pg_get_serial_sequence('accountrole_id_seq', 'id'), (SELECT MAX(id) FROM public.account_role));

SELECT setval('accountrole_id_seq', (SELECT MAX(id) FROM public.account_role));




select
* 
    from
        comment comment0_ 
    where
        comment0_.post_id=41
    order by
        comment0_.comment_grp asc,
        comment0_.comment_odr asc;