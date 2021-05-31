truncate table account_role restart identity cascade;
truncate table account restart identity cascade;
truncate table users_roles restart identity cascade;
truncate table party restart identity cascade;
truncate table party_managers restart identity cascade;
truncate table board restart identity cascade;
truncate table post restart identity cascade;

INSERT INTO public.account_role(id, "name") VALUES (1,'ROLE_ADMIN');
INSERT INTO public.account_role(id, "name") VALUES (2,'ROLE_USER');
SELECT setval('accountrole_id_seq', (SELECT MAX(id) FROM public.account_role));


INSERT INTO public.account (id,bio,email,email_check_token,email_check_token_generate_at,email_login_token,email_login_token_generate_at,email_verified,joined_at,"location",nickname,occupation,party_created_by_email,party_created_by_web,party_enrolment_result_by_email,party_enrolment_result_by_web,party_updated_by_email,party_updated_by_web,"password",profile_image,url) VALUES
	 (1,NULL,'shareknot@gmail.com','98d4e886-a924-4074-b238-231e55c97da2','2021-05-19 17:59:41.271297',NULL,NULL,false,NULL,NULL,'shareknot',NULL,false,true,false,true,false,true,'{bcrypt}$2a$10$RsuLR4pg0/pXqcxqPLdBPOqVMs8eFllvv9dwYRNYdS8dggnRJCusq',NULL,NULL),
	 (2,NULL,'kjaehwan89@gmail.com','98d4e886-a924-4074-b238-231e55c97da2','2021-05-19 17:59:41.271297',NULL,NULL,false,NULL,NULL,'kjaehwan89',NULL,false,true,false,true,false,true,'{bcrypt}$2a$10$RsuLR4pg0/pXqcxqPLdBPOqVMs8eFllvv9dwYRNYdS8dggnRJCusq',NULL,NULL);
SELECT setval('account_id_seq', (SELECT MAX(id) FROM public.account));

INSERT INTO public.users_roles(user_id,role_id) values((SELECT ID FROM public.account WHERE nickname='shareknot'),(SELECT ID FROM public.account_role WHERE name='ROLE_ADMIN'));
INSERT INTO public.users_roles(user_id,role_id) values((SELECT ID FROM public.account WHERE nickname='kjaehwan89'),(SELECT ID FROM public.account_role WHERE name='ROLE_USER'));
