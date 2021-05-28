INSERT INTO public.account_role(id, "name") VALUES (1,'ROLE_ADMIN');
INSERT INTO public.account_role(id, "name") VALUES (2,'ROLE_USER');
SELECT setval('accountrole_id_seq', (SELECT MAX(id) FROM public.account));

INSERT INTO public.account (id,bio,email,email_check_token,email_check_token_generate_at,email_login_token,email_login_token_generate_at,email_verified,joined_at,"location",nickname,occupation,party_created_by_email,party_created_by_web,party_enrolment_result_by_email,party_enrolment_result_by_web,party_updated_by_email,party_updated_by_web,"password",profile_image,url) VALUES
	 (1,NULL,'shareknot@gmail.com','98d4e886-a924-4074-b238-231e55c97da2','2021-05-19 17:59:41.271297',NULL,NULL,false,NULL,NULL,'shareknot',NULL,false,true,false,true,false,true,'{bcrypt}$2a$10$RsuLR4pg0/pXqcxqPLdBPOqVMs8eFllvv9dwYRNYdS8dggnRJCusq',NULL,NULL),
	 (2,NULL,'kkjaehwan@gmail.com','98d4e886-a924-4074-b238-231e55c97da2','2021-05-19 17:59:41.271297',NULL,NULL,false,NULL,NULL,'kkjaehwan',NULL,false,true,false,true,false,true,'{bcrypt}$2a$10$RsuLR4pg0/pXqcxqPLdBPOqVMs8eFllvv9dwYRNYdS8dggnRJCusq',NULL,NULL);
SELECT setval('account_id_seq', (SELECT MAX(id) FROM account));
	 	 
INSERT INTO public.users_roles(user_id,role_id) values(1,1);
INSERT INTO public.users_roles(user_id,role_id) values(2,2);

INSERT INTO public.party (id,closed,closed_date_time,full_description,image,member_count,"path",published,published_date_time,recruiting,recruiting_updated_date_time,short_description,title,use_banner) VALUES
	(1 ,false,NULL,'127276','157229',0,'shareknot'  ,true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party'  ,'shareknot'  ,false),
	(2 ,false,NULL,'127276','157229',0,'shareknot2' ,true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party2 ','shareknot2' ,false),
	(3 ,false,NULL,'127276','157229',0,'shareknot3' ,true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party3 ','shareknot3' ,false),
	(4 ,false,NULL,'127276','157229',0,'shareknot4' ,true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party4 ','shareknot4' ,false),
	(5 ,false,NULL,'127276','157229',0,'shareknot5' ,true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party5 ','shareknot5' ,false),
	(6 ,false,NULL,'127276','157229',0,'shareknot6' ,true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party6 ','shareknot6' ,false),
	(7 ,false,NULL,'127276','157229',0,'shareknot7' ,true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party7 ','shareknot7' ,false),
	(8 ,false,NULL,'127276','157229',0,'shareknot8' ,true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party8 ','shareknot8' ,false),
	(9 ,false,NULL,'127276','157229',0,'shareknot9' ,true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party9 ','shareknot9' ,false),
	(10,false,NULL,'127276','157229',0,'shareknot10',true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party10','shareknot10',false),
	(11,false,NULL,'127276','157229',0,'shareknot11',true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party11','shareknot11',false),
	(12,false,NULL,'127276','157229',0,'shareknot12',true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party12','shareknot12',false),
	(13,false,NULL,'127276','157229',0,'shareknot13',true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party13','shareknot13',false),
	(14,false,NULL,'127276','157229',0,'shareknot14',true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party14','shareknot14',false),
	(15,false,NULL,'127276','157229',0,'shareknot15',true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party15','shareknot15',false),
	(16,false,NULL,'127276','157229',0,'shareknot16',true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party16','shareknot16',false),
	(17,false,NULL,'127276','157229',0,'shareknot17',true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party17','shareknot17',false),
	(18,false,NULL,'127276','157229',0,'shareknot18',true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party18','shareknot18',false);;
SELECT setval('party_id_seq', (SELECT MAX(id) FROM party));

INSERT INTO public.party_managers(party_id,managers_id) values
(1,1),
(2,1),
(3,1),
(4,1),
(5,1),
(6,1),
(7,1),
(8,1),
(9,1),
(10,1),
(11,1),
(12,1),
(13,1),
(14,1),
(15,1),
(16,1),
(17,1),
(18,1);

INSERT INTO public.board (id,title) VALUES (1,'Forum');
INSERT INTO public.board (id,title) VALUES (2,'Canada');
INSERT INTO public.board (id,title) VALUES (3,'Toronto');
INSERT INTO public.board (id,title) VALUES (4,'Korea');
INSERT INTO public.board (id,title) VALUES (5,'Seoul');
SELECT setval('board_id_seq', (SELECT MAX(id) FROM board));

INSERT INTO post (id,"content",created_date_time,title,view_count,comment_count,author_id,board_id) VALUES
	 (1,'75330','2021-05-14 23:34:05.92853','test1',0,0,1,1),
	 (2,'75330','2021-05-15 23:34:05.92853','test2',0,0,1,1),
	 (3,'75330','2021-05-16 23:34:05.92853','test3',0,0,1,1),
	 (4,'75330','2021-05-17 23:34:05.92853','test4',0,0,1,1),
	 (5,'75330','2021-05-18 23:34:05.92853','test5',0,0,1,1),
	 (6,'75330','2021-05-19 23:34:05.92853','test6',0,0,1,1),
	 (7,'75330','2021-05-20 23:34:05.92853','test7',0,0,1,1),
	 (8,'75330','2021-05-21 23:34:05.92853','test8',0,0,1,1),
	 (9,'75330','2021-05-22 23:34:05.92853','test9',0,0,1,1),
	 (10,'75330','2021-05-23 23:34:05.92853','test11',0,0,1,1),
	 (11,'75330','2021-05-24 23:34:05.92853','test11',0,0,1,1),
	 (12,'75330','2021-05-25 23:34:05.92853','test12',0,0,1,1),
	 (13,'75330','2021-05-26 23:34:05.92853','test13',0,0,1,1),
	 (14,'75330','2021-05-27 23:34:05.92853','test14',0,0,1,1),
	 (15,'75330','2021-05-28 23:34:05.92853','test15',0,0,1,1),
	 (16,'75330','2021-05-28 23:34:05.92853','test16',0,0,1,1),
	 (17,'75330','2021-05-29 23:34:05.92853','test17',0,0,1,1),
	 (18,'75330','2021-05-30 23:34:05.92853','test18',0,0,1,1),
	 (19,'75330','2021-05-01 23:34:05.92853','test19',0,0,1,1),
	 (20,'75330','2021-05-02 23:34:05.92853','test20',0,0,1,1),
	 (21,'75330','2021-05-03 23:34:05.92853','test21',0,0,1,1),
	 (22,'75330','2021-05-04 23:34:05.92853','test22',0,0,1,1),
	 (23,'75330','2021-05-05 23:34:05.92853','test23',0,0,1,1),
	 (24,'75330','2021-05-06 23:34:05.92853','test24',0,0,1,1),
	 (25,'75330','2021-05-07 23:34:05.92853','test25',0,0,1,1),
	 (26,'75330','2021-05-08 23:34:05.92853','test26',0,0,1,1),
	 (27,'75330','2021-05-09 23:34:05.92853','test27',0,0,1,1),
	 (28,'75330','2021-05-10 23:34:05.92853','test28',0,0,1,1),
	 (29,'75330','2021-05-11 23:34:05.92853','test29',0,0,1,1),
	 (30,'75330','2021-05-12 23:34:05.92853','test30',0,0,1,1),
	 (31,'75330','2021-05-13 23:34:05.92853','test31',0,0,1,1),
	 (32,'75330','2021-05-14 23:34:05.92853','test32',0,0,1,1),
	 (33,'75330','2021-05-15 23:34:05.92853','test33',0,0,1,1),
	 (34,'75330','2021-05-16 23:34:05.92853','test34',0,0,1,1),
	 (35,'75330','2021-05-17 23:34:05.92853','test35',0,0,1,1),
	 (36,'75330','2021-05-18 23:34:05.92853','test36',0,0,1,1),
	 (37,'75330','2021-05-19 23:34:05.92853','test37',0,0,1,1),
	 (38,'75330','2021-05-20 10:34:05.92853','test38',0,0,1,1),
	 (39,'75330','2021-05-20 20:34:05.92853','test39',1,0,1,1),
	 (41,'90271','2021-05-20 22:34:05.92853','test'  ,2,0,1,1),
	 (42,'90272','2021-05-20 23:34:06.06912','test'  ,3,0,1,1);
SELECT setval('post_id_seq', (SELECT MAX(id) FROM post));