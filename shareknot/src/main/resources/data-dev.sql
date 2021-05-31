INSERT INTO public.account_role (id,"name") VALUES
	 (1,'ROLE_ADMIN'),
	 (2,'ROLE_USER');
SELECT setval('accountrole_id_seq', (SELECT MAX(id) FROM public.account_role));

INSERT INTO public.account (id,bio,email,email_check_token,email_check_token_generate_at,email_login_token,email_login_token_generate_at,email_verified,joined_at,"location",nickname,occupation,party_created_by_email,party_created_by_web,party_enrolment_result_by_email,party_enrolment_result_by_web,party_updated_by_email,party_updated_by_web,"password",profile_image,url) VALUES
	 (2,NULL,'kkjaehwan@gmail.com','98d4e886-a924-4074-b238-231e55c97da2','2021-05-19 17:59:41.271297',NULL,NULL,false,NULL,NULL,'kkjaehwan',NULL,false,true,false,true,false,true,'{bcrypt}$2a$10$RsuLR4pg0/pXqcxqPLdBPOqVMs8eFllvv9dwYRNYdS8dggnRJCusq',NULL,NULL),
	 (3,NULL,'kjaehwan89@gmail.com','98d4e886-a924-4074-b238-231e55c97da2','2021-05-19 17:59:41.271297',NULL,NULL,false,NULL,NULL,'kjaehwan89',NULL,false,true,false,true,false,true,'{bcrypt}$2a$10$RsuLR4pg0/pXqcxqPLdBPOqVMs8eFllvv9dwYRNYdS8dggnRJCusq',NULL,NULL),
	 (1,NULL,'shareknot@gmail.com','98d4e886-a924-4074-b238-231e55c97da2','2021-05-19 17:59:41.271297',NULL,NULL,false,NULL,NULL,'shareknot',NULL,false,true,false,true,false,true,'{bcrypt}$2a$10$RsuLR4pg0/pXqcxqPLdBPOqVMs8eFllvv9dwYRNYdS8dggnRJCusq',NULL,'');
SELECT setval('account_id_seq', (SELECT MAX(id) FROM account));
	 	 
INSERT INTO public.users_roles(user_id,role_id) values(1,1);
INSERT INTO public.users_roles(user_id,role_id) values(2,2);

INSERT INTO public.tag (id,title) VALUES
	 (1,'bread');
SELECT setval('tag_id_seq', (SELECT MAX(id) FROM tag));
	 
INSERT INTO public.account_tags (account_id,tags_id) VALUES
	 (2,1),
	 (1,1);
	 

INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (1,'Andong','South Korea','안동시','North Gyeongsang'),
	 (2,'Ansan','South Korea','안산시','Gyeonggi'),
	 (3,'Anseong','South Korea','안성시','Gyeonggi'),
	 (4,'Anyang','South Korea','안양시','Gyeonggi'),
	 (5,'Asan','South Korea','아산시','South Chungcheong'),
	 (6,'Boryeong','South Korea','보령시','South Chungcheong'),
	 (7,'Bucheon','South Korea','부천시','Gyeonggi'),
	 (8,'Busan','South Korea','부산광역시','none'),
	 (9,'Changwon','South Korea','창원시','South Gyeongsang'),
	 (10,'Cheonan','South Korea','천안시','South Chungcheong');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (11,'Cheongju','South Korea','청주시','North Chungcheong'),
	 (12,'Chuncheon','South Korea','춘천시','Gangwon'),
	 (13,'Chungju','South Korea','충주시','North Chungcheong'),
	 (14,'Daegu','South Korea','대구광역시','none'),
	 (15,'Daejeon','South Korea','대전광역시','none'),
	 (16,'Dangjin','South Korea','당진시','South Chungcheong'),
	 (17,'Dongducheon','South Korea','동두천시','Gyeonggi'),
	 (18,'Donghae','South Korea','동해시','Gangwon'),
	 (19,'Gangneung','South Korea','강릉시','Gangwon'),
	 (20,'Geoje','South Korea','거제시','South Gyeongsang');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (21,'Gimcheon','South Korea','김천시','North Gyeongsang'),
	 (22,'Gimhae','South Korea','김해시','South Gyeongsang'),
	 (23,'Gimje','South Korea','김제시','North Jeolla'),
	 (24,'Gimpo','South Korea','김포시','Gyeonggi'),
	 (25,'Gongju','South Korea','공주시','South Chungcheong'),
	 (26,'Goyang','South Korea','고양시','Gyeonggi'),
	 (27,'Gumi','South Korea','구미시','North Gyeongsang'),
	 (28,'Gunpo','South Korea','군포시','Gyeonggi'),
	 (29,'Gunsan','South Korea','군산시','North Jeolla'),
	 (30,'Guri','South Korea','구리시','Gyeonggi');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (31,'Gwacheon','South Korea','과천시','Gyeonggi'),
	 (32,'Gwangju','South Korea','광주광역시','none'),
	 (33,'Gwangju','South Korea','광주시','Gyeonggi'),
	 (34,'Gwangmyeong','South Korea','광명시','Gyeonggi'),
	 (35,'Gwangyang','South Korea','광양시','South Jeolla'),
	 (36,'Gyeongju','South Korea','경주시','North Gyeongsang'),
	 (37,'Gyeongsan','South Korea','경산시','North Gyeongsang'),
	 (38,'Gyeryong','South Korea','계룡시','South Chungcheong'),
	 (39,'Hanam','South Korea','하남시','Gyeonggi'),
	 (40,'Hwaseong','South Korea','화성시','Gyeonggi');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (41,'Icheon','South Korea','이천시','Gyeonggi'),
	 (42,'Iksan','South Korea','익산시','North Jeolla'),
	 (43,'Incheon','South Korea','인천광역시','none'),
	 (44,'Jecheon','South Korea','제천시','North Chungcheong'),
	 (45,'Jeongeup','South Korea','정읍시','North Jeolla'),
	 (46,'Jeonju','South Korea','전주시','North Jeolla'),
	 (47,'Jeju','South Korea','제주시','Jeju'),
	 (48,'Jinju','South Korea','진주시','South Gyeongsang'),
	 (49,'Naju','South Korea','나주시','South Jeolla'),
	 (50,'Namyangju','South Korea','남양주시','Gyeonggi');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (51,'Namwon','South Korea','남원시','North Jeolla'),
	 (52,'Nonsan','South Korea','논산시','South Chungcheong'),
	 (53,'Miryang','South Korea','밀양시','South Gyeongsang'),
	 (54,'Mokpo','South Korea','목포시','South Jeolla'),
	 (55,'Mungyeong','South Korea','문경시','North Gyeongsang'),
	 (56,'Osan','South Korea','오산시','Gyeonggi'),
	 (57,'Paju','South Korea','파주시','Gyeonggi'),
	 (58,'Pocheon','South Korea','포천시','Gyeonggi'),
	 (59,'Pohang','South Korea','포항시','North Gyeongsang'),
	 (60,'Pyeongtaek','South Korea','평택시','Gyeonggi');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (61,'Sacheon','South Korea','사천시','South Gyeongsang'),
	 (62,'Sangju','South Korea','상주시','North Gyeongsang'),
	 (63,'Samcheok','South Korea','삼척시','Gangwon'),
	 (64,'Sejong','South Korea','세종특별자치시','none'),
	 (65,'Seogwipo','South Korea','서귀포시','Jeju'),
	 (66,'Seongnam','South Korea','성남시','Gyeonggi'),
	 (67,'Seosan','South Korea','서산시','South Chungcheong'),
	 (68,'Seoul','South Korea','서울특별시','none'),
	 (69,'Siheung','South Korea','시흥시','Gyeonggi'),
	 (70,'Sokcho','South Korea','속초시','Gangwon');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (71,'Suncheon','South Korea','순천시','South Jeolla'),
	 (72,'Suwon','South Korea','수원시','Gyeonggi'),
	 (73,'Taebaek','South Korea','태백시','Gangwon'),
	 (74,'Tongyeong','South Korea','통영시','South Gyeongsang'),
	 (75,'Uijeongbu','South Korea','의정부시','Gyeonggi'),
	 (76,'Uiwang','South Korea','의왕시','Gyeonggi'),
	 (77,'Ulsan','South Korea','울산광역시','none'),
	 (78,'Wonju','South Korea','원주시','Gangwon'),
	 (79,'Yangju','South Korea','양주시','Gyeonggi'),
	 (80,'Yangsan','South Korea','양산시','South Gyeongsang');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (81,'Yeoju','South Korea','여주시','Gyeonggi'),
	 (82,'Yeongcheon','South Korea','영천시','North Gyeongsang'),
	 (83,'Yeongju','South Korea','영주시','North Gyeongsang'),
	 (84,'Yeosu','South Korea','여수시','South Jeolla'),
	 (85,'Yongin','South Korea','용인시','Gyeonggi'),
	 (86,'Airdrie','Canada','Airdrie','Alberta'),
	 (87,'Beaumont','Canada','Beaumont','Alberta'),
	 (88,'Brooks','Canada','Brooks','Alberta'),
	 (89,'Calgary','Canada','Calgary','Alberta'),
	 (90,'Camrose','Canada','Camrose','Alberta');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (91,'Chestermere','Canada','Chestermere','Alberta'),
	 (92,'Cold Lake','Canada','Cold Lake','Alberta'),
	 (93,'Edmonton','Canada','Edmonton','Alberta'),
	 (94,'Fort Saskatchewan','Canada','Fort Saskatchewan','Alberta'),
	 (95,'Grande Prairie','Canada','Grande Prairie','Alberta'),
	 (96,'Lacombe','Canada','Lacombe','Alberta'),
	 (97,'Leduc','Canada','Leduc','Alberta'),
	 (98,'Lethbridge','Canada','Lethbridge','Alberta'),
	 (99,'Lloydminster','Canada','Lloydminster','Alberta'),
	 (100,'Medicine Hat','Canada','Medicine Hat','Alberta');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (101,'Red Deer','Canada','Red Deer','Alberta'),
	 (515,'Truro','Canada','Truro','Nova Scotia'),
	 (102,'Spruce Grove','Canada','Spruce Grove','Alberta'),
	 (103,'St. Albert','Canada','St. Albert','Alberta'),
	 (104,'Wetaskiwin','Canada','Wetaskiwin','Alberta'),
	 (105,'Abbotsford','Canada','Abbotsford','British Columbia'),
	 (106,'Armstrong','Canada','Armstrong','British Columbia'),
	 (107,'Burnaby','Canada','Burnaby','British Columbia'),
	 (108,'Campbell River','Canada','Campbell River','British Columbia'),
	 (109,'Castlegar','Canada','Castlegar','British Columbia');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (110,'Chilliwack','Canada','Chilliwack','British Columbia'),
	 (111,'Colwood','Canada','Colwood','British Columbia'),
	 (112,'Coquitlam','Canada','Coquitlam','British Columbia'),
	 (113,'Courtenay','Canada','Courtenay','British Columbia'),
	 (114,'Cranbrook','Canada','Cranbrook','British Columbia'),
	 (115,'Dawson Creek','Canada','Dawson Creek','British Columbia'),
	 (116,'Delta','Canada','Delta','British Columbia'),
	 (117,'Duncan','Canada','Duncan','British Columbia'),
	 (118,'Enderby','Canada','Enderby','British Columbia'),
	 (119,'Fernie','Canada','Fernie','British Columbia');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (120,'Fort St. John','Canada','Fort St. John','British Columbia'),
	 (121,'Grand Forks','Canada','Grand Forks','British Columbia'),
	 (122,'Greenwood','Canada','Greenwood','British Columbia'),
	 (123,'Kamloops','Canada','Kamloops','British Columbia'),
	 (124,'Kelowna','Canada','Kelowna','British Columbia'),
	 (125,'Kimberley','Canada','Kimberley','British Columbia'),
	 (126,'Langford','Canada','Langford','British Columbia'),
	 (127,'Langley','Canada','Langley','British Columbia'),
	 (128,'Maple Ridge','Canada','Maple Ridge','British Columbia'),
	 (129,'Merritt','Canada','Merritt','British Columbia');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (130,'Nanaimo','Canada','Nanaimo','British Columbia'),
	 (131,'Nelson','Canada','Nelson','British Columbia'),
	 (132,'New Westminster','Canada','New Westminster','British Columbia'),
	 (133,'North Vancouver','Canada','North Vancouver','British Columbia'),
	 (134,'Parksville','Canada','Parksville','British Columbia'),
	 (135,'Penticton','Canada','Penticton','British Columbia'),
	 (136,'Pitt Meadows','Canada','Pitt Meadows','British Columbia'),
	 (137,'Port Alberni','Canada','Port Alberni','British Columbia'),
	 (138,'Port Coquitlam','Canada','Port Coquitlam','British Columbia'),
	 (139,'Port Moody','Canada','Port Moody','British Columbia');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (140,'Powell River','Canada','Powell River','British Columbia'),
	 (141,'Prince George','Canada','Prince George','British Columbia'),
	 (142,'Prince Rupert','Canada','Prince Rupert','British Columbia'),
	 (143,'Quesnel','Canada','Quesnel','British Columbia'),
	 (144,'Revelstoke','Canada','Revelstoke','British Columbia'),
	 (145,'Richmond','Canada','Richmond','British Columbia'),
	 (146,'Rossland','Canada','Rossland','British Columbia'),
	 (147,'Salmon Arm','Canada','Salmon Arm','British Columbia'),
	 (148,'Surrey','Canada','Surrey','British Columbia'),
	 (149,'Terrace','Canada','Terrace','British Columbia');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (150,'Trail','Canada','Trail','British Columbia'),
	 (151,'Vancouver','Canada','Vancouver','British Columbia'),
	 (152,'Vernon','Canada','Vernon','British Columbia'),
	 (153,'Victoria','Canada','Victoria','British Columbia'),
	 (154,'West Kelowna','Canada','West Kelowna','British Columbia'),
	 (155,'White Rock','Canada','White Rock','British Columbia'),
	 (156,'Williams Lake','Canada','Williams Lake','British Columbia'),
	 (157,'Brandon','Canada','Brandon','Manitoba'),
	 (158,'Dauphin','Canada','Dauphin','Manitoba'),
	 (159,'Flin Flon','Canada','Flin Flon','Manitoba');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (160,'Morden','Canada','Morden','Manitoba'),
	 (161,'Portage la Prairie','Canada','Portage la Prairie','Manitoba'),
	 (162,'Selkirk','Canada','Selkirk','Manitoba'),
	 (163,'Steinbach','Canada','Steinbach','Manitoba'),
	 (164,'Thompson','Canada','Thompson','Manitoba'),
	 (165,'Winkler','Canada','Winkler','Manitoba'),
	 (166,'Winnipeg','Canada','Winnipeg','Manitoba'),
	 (167,'Bathurst','Canada','Bathurst','New Brunswick'),
	 (168,'Campbellton','Canada','Campbellton','New Brunswick'),
	 (169,'Dieppe','Canada','Dieppe','New Brunswick');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (170,'Edmundston','Canada','Edmundston','New Brunswick'),
	 (171,'Fredericton','Canada','Fredericton','New Brunswick'),
	 (172,'Miramichi','Canada','Miramichi','New Brunswick'),
	 (173,'Moncton','Canada','Moncton','New Brunswick'),
	 (174,'Saint John','Canada','Saint John','New Brunswick'),
	 (175,'Corner Brook','Canada','Corner Brook','Newfoundland and Labrador'),
	 (176,'Mount Pearl','Canada','Mount Pearl','Newfoundland and Labrador'),
	 (177,'St. John''s','Canada','St. John''s','Newfoundland and Labrador'),
	 (178,'Yellowknife','Canada','Yellowknife','Northwest Territories'),
	 (179,'Iqaluit','Canada','Iqaluit','Nunavut');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (180,'Barrie','Canada','Barrie','Ontario'),
	 (181,'Belleville','Canada','Belleville','Ontario'),
	 (182,'Brampton','Canada','Brampton','Ontario'),
	 (183,'Brant','Canada','Brant','Ontario'),
	 (184,'Brantford','Canada','Brantford','Ontario'),
	 (185,'Brockville','Canada','Brockville','Ontario'),
	 (186,'Burlington','Canada','Burlington','Ontario'),
	 (187,'Cambridge','Canada','Cambridge','Ontario'),
	 (188,'Clarence-Rockland','Canada','Clarence-Rockland','Ontario'),
	 (189,'Cornwall','Canada','Cornwall','Ontario');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (190,'Dryden','Canada','Dryden','Ontario'),
	 (191,'Elliot Lake','Canada','Elliot Lake','Ontario'),
	 (192,'Greater Sudbury','Canada','Greater Sudbury','Ontario'),
	 (193,'Guelph','Canada','Guelph','Ontario'),
	 (194,'Haldimand County','Canada','Haldimand County','Ontario'),
	 (195,'Hamilton','Canada','Hamilton','Ontario'),
	 (196,'Kawartha Lakes','Canada','Kawartha Lakes','Ontario'),
	 (197,'Kenora','Canada','Kenora','Ontario'),
	 (198,'Kingston','Canada','Kingston','Ontario'),
	 (199,'Kitchener','Canada','Kitchener','Ontario');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (200,'London','Canada','London','Ontario'),
	 (201,'Markham','Canada','Markham','Ontario'),
	 (202,'Mississauga','Canada','Mississauga','Ontario'),
	 (203,'Niagara Falls','Canada','Niagara Falls','Ontario'),
	 (204,'Norfolk County','Canada','Norfolk County','Ontario'),
	 (205,'North Bay','Canada','North Bay','Ontario'),
	 (206,'Orillia','Canada','Orillia','Ontario'),
	 (207,'Oshawa','Canada','Oshawa','Ontario'),
	 (208,'Ottawa','Canada','Ottawa','Ontario'),
	 (209,'Owen Sound','Canada','Owen Sound','Ontario');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (210,'Pembroke','Canada','Pembroke','Ontario'),
	 (211,'Peterborough','Canada','Peterborough','Ontario'),
	 (212,'Pickering','Canada','Pickering','Ontario'),
	 (213,'Port Colborne','Canada','Port Colborne','Ontario'),
	 (214,'Prince Edward County','Canada','Prince Edward County','Ontario'),
	 (215,'Quinte West','Canada','Quinte West','Ontario'),
	 (216,'Richmond Hill','Canada','Richmond Hill','Ontario'),
	 (217,'Sarnia','Canada','Sarnia','Ontario'),
	 (218,'Sault Ste. Marie','Canada','Sault Ste. Marie','Ontario'),
	 (219,'St. Catharines','Canada','St. Catharines','Ontario');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (220,'St. Thomas','Canada','St. Thomas','Ontario'),
	 (221,'Stratford','Canada','Stratford','Ontario'),
	 (222,'Temiskaming Shores','Canada','Temiskaming Shores','Ontario'),
	 (223,'Thorold','Canada','Thorold','Ontario'),
	 (224,'Thunder Bay','Canada','Thunder Bay','Ontario'),
	 (225,'Timmins','Canada','Timmins','Ontario'),
	 (226,'Toronto','Canada','Toronto','Ontario'),
	 (227,'Vaughan','Canada','Vaughan','Ontario'),
	 (228,'Waterloo','Canada','Waterloo','Ontario'),
	 (229,'Welland','Canada','Welland','Ontario');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (230,'Windsor','Canada','Windsor','Ontario'),
	 (231,'Woodstock','Canada','Woodstock','Ontario'),
	 (232,'Charlottetown','Canada','Charlottetown','Prince Edward Island'),
	 (233,'Summerside','Canada','Summerside','Prince Edward Island'),
	 (234,'Acton Vale','Canada','Acton Vale','Quebec'),
	 (235,'Alma','Canada','Alma','Quebec'),
	 (236,'Amos','Canada','Amos','Quebec'),
	 (237,'Amqui','Canada','Amqui','Quebec'),
	 (238,'Baie-Comeau','Canada','Baie-Comeau','Quebec'),
	 (239,'Baie-D''Urf?','Canada','Baie-D''Urf?','Quebec');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (240,'Baie-Saint-Paul','Canada','Baie-Saint-Paul','Quebec'),
	 (241,'Barkmere','Canada','Barkmere','Quebec'),
	 (242,'Beaconsfield','Canada','Beaconsfield','Quebec'),
	 (243,'Beauceville','Canada','Beauceville','Quebec'),
	 (244,'Beauharnois','Canada','Beauharnois','Quebec'),
	 (245,'Beaupr?','Canada','Beaupr?','Quebec'),
	 (246,'B?cancour','Canada','B?cancour','Quebec'),
	 (247,'Bedford','Canada','Bedford','Quebec'),
	 (248,'Belleterre','Canada','Belleterre','Quebec'),
	 (249,'Beloeil','Canada','Beloeil','Quebec');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (250,'Berthierville','Canada','Berthierville','Quebec'),
	 (251,'Blainville','Canada','Blainville','Quebec'),
	 (252,'Boisbriand','Canada','Boisbriand','Quebec'),
	 (253,'Bois-des-Filion','Canada','Bois-des-Filion','Quebec'),
	 (254,'Bonaventure','Canada','Bonaventure','Quebec'),
	 (255,'Boucherville','Canada','Boucherville','Quebec'),
	 (256,'Lac-Brome','Canada','Lac-Brome','Quebec'),
	 (257,'Bromont','Canada','Bromont','Quebec'),
	 (258,'Brossard','Canada','Brossard','Quebec'),
	 (259,'Brownsburg-Chatham','Canada','Brownsburg-Chatham','Quebec');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (260,'Candiac','Canada','Candiac','Quebec'),
	 (261,'Cap-Chat','Canada','Cap-Chat','Quebec'),
	 (262,'Cap-Sant?','Canada','Cap-Sant?','Quebec'),
	 (263,'Carignan','Canada','Carignan','Quebec'),
	 (264,'Carleton-sur-Mer','Canada','Carleton-sur-Mer','Quebec'),
	 (265,'Causapscal','Canada','Causapscal','Quebec'),
	 (266,'Chambly','Canada','Chambly','Quebec'),
	 (267,'Chandler','Canada','Chandler','Quebec'),
	 (268,'Chapais','Canada','Chapais','Quebec'),
	 (269,'Charlemagne','Canada','Charlemagne','Quebec');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (270,'Ch?teauguay','Canada','Ch?teauguay','Quebec'),
	 (271,'Ch?teau-Richer','Canada','Ch?teau-Richer','Quebec'),
	 (272,'Chibougamau','Canada','Chibougamau','Quebec'),
	 (273,'Clermont','Canada','Clermont','Quebec'),
	 (274,'Coaticook','Canada','Coaticook','Quebec'),
	 (275,'Contrecoeur','Canada','Contrecoeur','Quebec'),
	 (276,'Cookshire-Eaton','Canada','Cookshire-Eaton','Quebec'),
	 (277,'C?te Saint-Luc','Canada','C?te Saint-Luc','Quebec'),
	 (278,'Coteau-du-Lac','Canada','Coteau-du-Lac','Quebec'),
	 (279,'Cowansville','Canada','Cowansville','Quebec');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (280,'Danville','Canada','Danville','Quebec'),
	 (281,'Daveluyville','Canada','Daveluyville','Quebec'),
	 (282,'D?gelis','Canada','D?gelis','Quebec'),
	 (283,'Delson','Canada','Delson','Quebec'),
	 (284,'Desbiens','Canada','Desbiens','Quebec'),
	 (285,'Deux-Montagnes','Canada','Deux-Montagnes','Quebec'),
	 (286,'Disraeli','Canada','Disraeli','Quebec'),
	 (287,'Dolbeau-Mistassini','Canada','Dolbeau-Mistassini','Quebec'),
	 (288,'Dollard-des-Ormeaux','Canada','Dollard-des-Ormeaux','Quebec'),
	 (289,'Donnacona','Canada','Donnacona','Quebec');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (290,'Dorval','Canada','Dorval','Quebec'),
	 (291,'Drummondville','Canada','Drummondville','Quebec'),
	 (292,'Dunham','Canada','Dunham','Quebec'),
	 (293,'Duparquet','Canada','Duparquet','Quebec'),
	 (294,'East Angus','Canada','East Angus','Quebec'),
	 (295,'Est?rel','Canada','Est?rel','Quebec'),
	 (296,'Farnham','Canada','Farnham','Quebec'),
	 (297,'Fermont','Canada','Fermont','Quebec'),
	 (298,'Forestville','Canada','Forestville','Quebec'),
	 (299,'Fossambault-sur-le-Lac','Canada','Fossambault-sur-le-Lac','Quebec');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (300,'Gasp?','Canada','Gasp?','Quebec'),
	 (301,'Gatineau','Canada','Gatineau','Quebec'),
	 (302,'Gracefield','Canada','Gracefield','Quebec'),
	 (303,'Granby','Canada','Granby','Quebec'),
	 (304,'Grande-Rivi?re','Canada','Grande-Rivi?re','Quebec'),
	 (305,'Hampstead','Canada','Hampstead','Quebec'),
	 (306,'Hudson','Canada','Hudson','Quebec'),
	 (307,'Huntingdon','Canada','Huntingdon','Quebec'),
	 (308,'Joliette','Canada','Joliette','Quebec'),
	 (309,'Kingsey Falls','Canada','Kingsey Falls','Quebec');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (310,'Kirkland','Canada','Kirkland','Quebec'),
	 (311,'La Malbaie','Canada','La Malbaie','Quebec'),
	 (312,'La Pocati?re','Canada','La Pocati?re','Quebec'),
	 (313,'La Prairie','Canada','La Prairie','Quebec'),
	 (314,'La Sarre','Canada','La Sarre','Quebec'),
	 (315,'La Tuque','Canada','La Tuque','Quebec'),
	 (316,'Lac-Delage','Canada','Lac-Delage','Quebec'),
	 (317,'Lachute','Canada','Lachute','Quebec'),
	 (318,'Lac-M?gantic','Canada','Lac-M?gantic','Quebec'),
	 (319,'Lac-Saint-Joseph','Canada','Lac-Saint-Joseph','Quebec');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (320,'Lac-Sergent','Canada','Lac-Sergent','Quebec'),
	 (321,'L''Ancienne-Lorette','Canada','L''Ancienne-Lorette','Quebec'),
	 (322,'L''Assomption','Canada','L''Assomption','Quebec'),
	 (323,'Laval','Canada','Laval','Quebec'),
	 (324,'Lavaltrie','Canada','Lavaltrie','Quebec'),
	 (325,'Lebel-sur-Qu?villon','Canada','Lebel-sur-Qu?villon','Quebec'),
	 (326,'L''?piphanie','Canada','L''?piphanie','Quebec'),
	 (327,'L?ry','Canada','L?ry','Quebec'),
	 (328,'L?vis','Canada','L?vis','Quebec'),
	 (329,'L''?le-Cadieux','Canada','L''?le-Cadieux','Quebec');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (330,'L''?le-Dorval','Canada','L''?le-Dorval','Quebec'),
	 (331,'L''?le-Perrot','Canada','L''?le-Perrot','Quebec'),
	 (332,'Longueuil','Canada','Longueuil','Quebec'),
	 (333,'Lorraine','Canada','Lorraine','Quebec'),
	 (334,'Louiseville','Canada','Louiseville','Quebec'),
	 (335,'Macamic','Canada','Macamic','Quebec'),
	 (336,'Magog','Canada','Magog','Quebec'),
	 (337,'Malartic','Canada','Malartic','Quebec'),
	 (338,'Maniwaki','Canada','Maniwaki','Quebec'),
	 (339,'Marieville','Canada','Marieville','Quebec');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (340,'Mascouche','Canada','Mascouche','Quebec'),
	 (341,'Matagami','Canada','Matagami','Quebec'),
	 (342,'Matane','Canada','Matane','Quebec'),
	 (343,'Mercier','Canada','Mercier','Quebec'),
	 (344,'M?tabetchouan?Lac-?-la-Croix','Canada','M?tabetchouan?Lac-?-la-Croix','Quebec'),
	 (345,'M?tis-sur-Mer','Canada','M?tis-sur-Mer','Quebec'),
	 (346,'Mirabel','Canada','Mirabel','Quebec'),
	 (347,'Mont-Joli','Canada','Mont-Joli','Quebec'),
	 (348,'Mont-Laurier','Canada','Mont-Laurier','Quebec'),
	 (349,'Montmagny','Canada','Montmagny','Quebec');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (350,'Montreal','Canada','Montreal','Quebec'),
	 (351,'Montreal West','Canada','Montreal West','Quebec'),
	 (352,'Montr?al-Est','Canada','Montr?al-Est','Quebec'),
	 (353,'Mont-Saint-Hilaire','Canada','Mont-Saint-Hilaire','Quebec'),
	 (354,'Mont-Tremblant','Canada','Mont-Tremblant','Quebec'),
	 (355,'Mount Royal','Canada','Mount Royal','Quebec'),
	 (356,'Murdochville','Canada','Murdochville','Quebec'),
	 (357,'Neuville','Canada','Neuville','Quebec'),
	 (358,'New Richmond','Canada','New Richmond','Quebec'),
	 (359,'Nicolet','Canada','Nicolet','Quebec');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (360,'Normandin','Canada','Normandin','Quebec'),
	 (361,'Notre-Dame-de-l''?le-Perrot','Canada','Notre-Dame-de-l''?le-Perrot','Quebec'),
	 (362,'Notre-Dame-des-Prairies','Canada','Notre-Dame-des-Prairies','Quebec'),
	 (363,'Otterburn Park','Canada','Otterburn Park','Quebec'),
	 (364,'Pasp?biac','Canada','Pasp?biac','Quebec'),
	 (365,'Perc?','Canada','Perc?','Quebec'),
	 (366,'Pincourt','Canada','Pincourt','Quebec'),
	 (367,'Plessisville','Canada','Plessisville','Quebec'),
	 (368,'Poh?n?gamook','Canada','Poh?n?gamook','Quebec'),
	 (369,'Pointe-Claire','Canada','Pointe-Claire','Quebec');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (370,'Pont-Rouge','Canada','Pont-Rouge','Quebec'),
	 (371,'Port-Cartier','Canada','Port-Cartier','Quebec'),
	 (372,'Portneuf','Canada','Portneuf','Quebec'),
	 (373,'Pr?vost','Canada','Pr?vost','Quebec'),
	 (374,'Princeville','Canada','Princeville','Quebec'),
	 (375,'Qu?bec','Canada','Qu?bec','Quebec'),
	 (376,'Repentigny','Canada','Repentigny','Quebec'),
	 (377,'Richelieu','Canada','Richelieu','Quebec'),
	 (378,'Richmond','Canada','Richmond','Quebec'),
	 (379,'Rigaud','Canada','Rigaud','Quebec');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (380,'Rimouski','Canada','Rimouski','Quebec'),
	 (381,'Rivi?re-du-Loup','Canada','Rivi?re-du-Loup','Quebec'),
	 (382,'Rivi?re-Rouge','Canada','Rivi?re-Rouge','Quebec'),
	 (383,'Roberval','Canada','Roberval','Quebec'),
	 (384,'Rosem?re','Canada','Rosem?re','Quebec'),
	 (385,'Rouyn-Noranda','Canada','Rouyn-Noranda','Quebec'),
	 (386,'Saguenay','Canada','Saguenay','Quebec'),
	 (387,'Saint-Augustin-de-Desmaures','Canada','Saint-Augustin-de-Desmaures','Quebec'),
	 (388,'Saint-Basile','Canada','Saint-Basile','Quebec'),
	 (389,'Saint-Basile-le-Grand','Canada','Saint-Basile-le-Grand','Quebec');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (390,'Saint-Bruno-de-Montarville','Canada','Saint-Bruno-de-Montarville','Quebec'),
	 (391,'Saint-C?saire','Canada','Saint-C?saire','Quebec'),
	 (392,'Saint-Charles-Borrom?e','Canada','Saint-Charles-Borrom?e','Quebec'),
	 (393,'Saint-Colomban','Canada','Saint-Colomban','Quebec'),
	 (394,'Saint-Constant','Canada','Saint-Constant','Quebec'),
	 (395,'Sainte-Ad?le','Canada','Sainte-Ad?le','Quebec'),
	 (396,'Sainte-Agathe-des-Monts','Canada','Sainte-Agathe-des-Monts','Quebec'),
	 (397,'Sainte-Anne-de-Beaupr?','Canada','Sainte-Anne-de-Beaupr?','Quebec'),
	 (398,'Sainte-Anne-de-Bellevue','Canada','Sainte-Anne-de-Bellevue','Quebec'),
	 (399,'Sainte-Anne-des-Monts','Canada','Sainte-Anne-des-Monts','Quebec');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (400,'Sainte-Anne-des-Plaines','Canada','Sainte-Anne-des-Plaines','Quebec'),
	 (401,'Sainte-Catherine','Canada','Sainte-Catherine','Quebec'),
	 (402,'Sainte-Catherine-de-la-Jacques-Cartier','Canada','Sainte-Catherine-de-la-Jacques-Cartier','Quebec'),
	 (403,'Sainte-Julie','Canada','Sainte-Julie','Quebec'),
	 (404,'Sainte-Marguerite-du-Lac-Masson','Canada','Sainte-Marguerite-du-Lac-Masson','Quebec'),
	 (405,'Sainte-Marie','Canada','Sainte-Marie','Quebec'),
	 (406,'Sainte-Marthe-sur-le-Lac','Canada','Sainte-Marthe-sur-le-Lac','Quebec'),
	 (407,'Sainte-Th?r?se','Canada','Sainte-Th?r?se','Quebec'),
	 (408,'Saint-Eustache','Canada','Saint-Eustache','Quebec'),
	 (409,'Saint-F?licien','Canada','Saint-F?licien','Quebec');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (410,'Saint-Gabriel','Canada','Saint-Gabriel','Quebec'),
	 (411,'Saint-Georges','Canada','Saint-Georges','Quebec'),
	 (412,'Saint-Hyacinthe','Canada','Saint-Hyacinthe','Quebec'),
	 (413,'Saint-Jean-sur-Richelieu','Canada','Saint-Jean-sur-Richelieu','Quebec'),
	 (414,'Saint-J?r?me','Canada','Saint-J?r?me','Quebec'),
	 (415,'Saint-Joseph-de-Beauce','Canada','Saint-Joseph-de-Beauce','Quebec'),
	 (416,'Saint-Joseph-de-Sorel','Canada','Saint-Joseph-de-Sorel','Quebec'),
	 (417,'Saint-Lambert','Canada','Saint-Lambert','Quebec'),
	 (418,'Saint-Lazare','Canada','Saint-Lazare','Quebec'),
	 (419,'Saint-Lin-Laurentides','Canada','Saint-Lin-Laurentides','Quebec');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (420,'Saint-Marc-des-Carri?res','Canada','Saint-Marc-des-Carri?res','Quebec'),
	 (421,'Saint-Ours','Canada','Saint-Ours','Quebec'),
	 (422,'Saint-Pamphile','Canada','Saint-Pamphile','Quebec'),
	 (423,'Saint-Pascal','Canada','Saint-Pascal','Quebec'),
	 (424,'Saint-Pie','Canada','Saint-Pie','Quebec'),
	 (425,'Saint-Raymond','Canada','Saint-Raymond','Quebec'),
	 (426,'Saint-R?mi','Canada','Saint-R?mi','Quebec'),
	 (427,'Saint-Sauveur','Canada','Saint-Sauveur','Quebec'),
	 (428,'Saint-Tite','Canada','Saint-Tite','Quebec'),
	 (429,'Salaberry-de-Valleyfield','Canada','Salaberry-de-Valleyfield','Quebec');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (430,'Schefferville','Canada','Schefferville','Quebec'),
	 (431,'Scotstown','Canada','Scotstown','Quebec'),
	 (432,'Senneterre','Canada','Senneterre','Quebec'),
	 (433,'Sept-?les','Canada','Sept-?les','Quebec'),
	 (434,'Shawinigan','Canada','Shawinigan','Quebec'),
	 (435,'Sherbrooke','Canada','Sherbrooke','Quebec'),
	 (436,'Sorel-Tracy','Canada','Sorel-Tracy','Quebec'),
	 (437,'Stanstead','Canada','Stanstead','Quebec'),
	 (438,'Sutton','Canada','Sutton','Quebec'),
	 (439,'T?miscaming','Canada','T?miscaming','Quebec');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (440,'T?miscouata-sur-le-Lac','Canada','T?miscouata-sur-le-Lac','Quebec'),
	 (441,'Terrebonne','Canada','Terrebonne','Quebec'),
	 (442,'Thetford Mines','Canada','Thetford Mines','Quebec'),
	 (443,'Thurso','Canada','Thurso','Quebec'),
	 (444,'Trois-Pistoles','Canada','Trois-Pistoles','Quebec'),
	 (445,'Trois-Rivi?res','Canada','Trois-Rivi?res','Quebec'),
	 (446,'Valcourt','Canada','Valcourt','Quebec'),
	 (447,'Val-d''Or','Canada','Val-d''Or','Quebec'),
	 (448,'Val-des-Sources','Canada','Val-des-Sources','Quebec'),
	 (449,'Varennes','Canada','Varennes','Quebec');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (450,'Vaudreuil-Dorion','Canada','Vaudreuil-Dorion','Quebec'),
	 (451,'Victoriaville','Canada','Victoriaville','Quebec'),
	 (452,'Ville-Marie','Canada','Ville-Marie','Quebec'),
	 (453,'Warwick','Canada','Warwick','Quebec'),
	 (454,'Waterloo','Canada','Waterloo','Quebec'),
	 (455,'Waterville','Canada','Waterville','Quebec'),
	 (456,'Westmount','Canada','Westmount','Quebec'),
	 (457,'Windsor','Canada','Windsor','Quebec'),
	 (458,'Estevan','Canada','Estevan','Saskatchewan'),
	 (459,'Flin Flon','Canada','Flin Flon','Saskatchewan');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (460,'Humboldt','Canada','Humboldt','Saskatchewan'),
	 (461,'Lloydminster','Canada','Lloydminster','Saskatchewan'),
	 (462,'Martensville','Canada','Martensville','Saskatchewan'),
	 (463,'Meadow Lake','Canada','Meadow Lake','Saskatchewan'),
	 (464,'Melfort','Canada','Melfort','Saskatchewan'),
	 (465,'Melville','Canada','Melville','Saskatchewan'),
	 (466,'Moose Jaw','Canada','Moose Jaw','Saskatchewan'),
	 (467,'North Battleford','Canada','North Battleford','Saskatchewan'),
	 (468,'Prince Albert','Canada','Prince Albert','Saskatchewan'),
	 (469,'Regina','Canada','Regina','Saskatchewan');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (470,'Saskatoon','Canada','Saskatoon','Saskatchewan'),
	 (471,'Swift Current','Canada','Swift Current','Saskatchewan'),
	 (472,'Warman','Canada','Warman','Saskatchewan'),
	 (473,'Weyburn','Canada','Weyburn','Saskatchewan'),
	 (474,'Whitehorse','Canada','Whitehorse','Yukon'),
	 (475,'Cape Breton','Canada','Cape Breton','Nova Scotia'),
	 (476,'Halifax','Canada','Halifax','Nova Scotia'),
	 (477,'Queens','Canada','Queens','Nova Scotia'),
	 (478,'Annapolis','Canada','Annapolis','Nova Scotia'),
	 (479,'Antigonish','Canada','Antigonish','Nova Scotia');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (480,'Colchester','Canada','Colchester','Nova Scotia'),
	 (481,'Cumberland','Canada','Cumberland','Nova Scotia'),
	 (482,'Inverness','Canada','Inverness','Nova Scotia'),
	 (483,'Kings','Canada','Kings','Nova Scotia'),
	 (484,'Pictou','Canada','Pictou','Nova Scotia'),
	 (485,'Richmond','Canada','Richmond','Nova Scotia'),
	 (486,'Victoria','Canada','Victoria','Nova Scotia'),
	 (487,'Argyle','Canada','Argyle','Nova Scotia'),
	 (488,'Barrington','Canada','Barrington','Nova Scotia'),
	 (489,'Chester','Canada','Chester','Nova Scotia');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (490,'Clare','Canada','Clare','Nova Scotia'),
	 (491,'Digby','Canada','Digby','Nova Scotia'),
	 (492,'East Hants','Canada','East Hants','Nova Scotia'),
	 (493,'Guysborough','Canada','Guysborough','Nova Scotia'),
	 (494,'Lunenburg','Canada','Lunenburg','Nova Scotia'),
	 (495,'Shelburne','Canada','Shelburne','Nova Scotia'),
	 (496,'St. Mary''s','Canada','St. Mary''s','Nova Scotia'),
	 (497,'West Hants','Canada','West Hants','Nova Scotia'),
	 (498,'Yarmouth','Canada','Yarmouth','Nova Scotia'),
	 (499,'Amherst','Canada','Amherst','Nova Scotia');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (500,'Annapolis Royal','Canada','Annapolis Royal','Nova Scotia'),
	 (501,'Berwick','Canada','Berwick','Nova Scotia'),
	 (502,'Bridgewater','Canada','Bridgewater','Nova Scotia'),
	 (503,'Clark''s Harbour','Canada','Clark''s Harbour','Nova Scotia'),
	 (504,'Kentville','Canada','Kentville','Nova Scotia'),
	 (505,'Lockeport','Canada','Lockeport','Nova Scotia'),
	 (506,'Mahone Bay','Canada','Mahone Bay','Nova Scotia'),
	 (507,'Middleton','Canada','Middleton','Nova Scotia'),
	 (508,'Mulgrave','Canada','Mulgrave','Nova Scotia'),
	 (509,'New Glasgow','Canada','New Glasgow','Nova Scotia');
INSERT INTO public."zone" (id,city,country,local_name_of_city,province) VALUES
	 (510,'Oxford','Canada','Oxford','Nova Scotia'),
	 (511,'Port Hawkesbury','Canada','Port Hawkesbury','Nova Scotia'),
	 (512,'Stellarton','Canada','Stellarton','Nova Scotia'),
	 (513,'Stewiacke','Canada','Stewiacke','Nova Scotia'),
	 (514,'Trenton','Canada','Trenton','Nova Scotia'),
	 (516,'Westville','Canada','Westville','Nova Scotia'),
	 (517,'Windsor','Canada','Windsor','Nova Scotia'),
	 (518,'Wolfville','Canada','Wolfville','Nova Scotia');	 
SELECT setval('zone_id_seq', (SELECT MAX(id) FROM "zone"));
	 
	 
INSERT INTO public.account_zones (account_id,zones_id) VALUES
	 (2,226),
	 (1,226);
	 
INSERT INTO public.party (id,closed,closed_date_time,full_description,image,member_count,"path",published,published_date_time,recruiting,recruiting_updated_date_time,short_description,title,use_banner) VALUES
	 (1,false,NULL,NULL,NULL,1,'ShareKnot',true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party','ShareKnot',true),
	 (2,false,NULL,NULL,NULL,0,'Toronto',true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party','Toronto',false),
	 (3,false,NULL,NULL,NULL,0,'Canada',true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party','Canada',true),
	 (4,false,NULL,NULL,NULL,0,'Korea',true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party','Korea',false),
	 (5,false,NULL,NULL,NULL,0,'Seoul',true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party','Seoul',true),
	 (6,false,NULL,NULL,NULL,0,'Developer',true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party','Developer',false),
	 (7,false,NULL,NULL,NULL,0,'Java',true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party','Java',true),
	 (8,false,NULL,NULL,NULL,0,'Music',true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party','Music',false),
	 (9,false,NULL,NULL,NULL,0,'Pizza',true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party','Pizza',true),
	 (10,false,NULL,NULL,NULL,0,'Cat',true,'2021-05-26 17:19:24.117325',true,'2021-05-26 17:18:59.56765','Shareknot Default Party','Cat',false),
	 (11,false,NULL,NULL,NULL,0,'kkjaehwan',true,'2021-05-28 23:29:22.038717',true,'2021-05-28 23:29:24.048701','kkjaehwan''s party','kkjaehwan',false);
SELECT setval('party_id_seq', (SELECT MAX(id) FROM party));

INSERT INTO public.party_managers (party_id,managers_id) VALUES
	 (1,1),
	 (2,1),
	 (3,1),
	 (4,1),
	 (5,1),
	 (6,1),
	 (7,1),
	 (8,1),
	 (9,1),
	 (10,1);
INSERT INTO public.party_managers (party_id,managers_id) VALUES
	 (11,2);
	 
INSERT INTO public.party_members (party_id,members_id) VALUES
	 (1,2);
	 
INSERT INTO public.party_tags (party_id,tags_id) VALUES
	 (11,1),
	 (1,1);
	 
INSERT INTO public.party_zones (party_id,zones_id) VALUES
	 (11,226),
	 (1,226);

INSERT INTO public.board (id,title) VALUES (1,'Forum');
INSERT INTO public.board (id,title) VALUES (2,'Canada');
INSERT INTO public.board (id,title) VALUES (3,'Toronto');
INSERT INTO public.board (id,title) VALUES (4,'Korea');
INSERT INTO public.board (id,title) VALUES (5,'Seoul');
SELECT setval('board_id_seq', (SELECT MAX(id) FROM board));

INSERT INTO post (id,"content",created_date_time,title,view_count,comment_count,author_id,board_id) VALUES
	 (1,NULL,'2021-05-14 23:34:05.92853','Pillow Thoughts by Courtney Peppernell.',0,0,1,1),
	 (2,NULL,'2021-05-15 23:34:05.92853','I hope this reaches her in time by r.h. Sin.',0,0,1,1),
	 (3,NULL,'2021-05-16 23:34:05.92853','When You Ask Me Where Im Going by Jasmine Kaur.',0,0,1,1),
	 (4,NULL,'2021-05-17 23:34:05.92853','The Sun and Her Flowers by Rupi Kaur.',0,0,1,1),
	 (5,NULL,'2021-05-18 23:34:05.92853','The Collected Poems by Sylvia Plath.',0,0,1,1),
	 (6,NULL,'2021-05-19 23:34:05.92853','There Are More Beautiful Things Than Beyoncé by Morgan Parker.',0,0,1,1),
	 (7,NULL,'2021-05-20 23:34:05.92853','The Hill We Climb: An Inaugural Poem for the Country',0,0,1,1),
	 (8,NULL,'2021-05-21 23:34:05.92853','The Hill We Climb : An Inaugural Poem ',0,0,1,1),
	 (9,NULL,'2021-05-22 23:34:05.92853','Project Hail Mary',0,0,1,1),
	 (10,NULL,'2021-05-23 23:34:05.92853','The Last Thing He Told Me',0,0,1,1),
	 (11,NULL,'2021-05-24 23:34:05.92853','Oh, the Places Youll Go',0,0,1,1),
	 (12,NULL,'2021-05-25 23:34:05.92853','The Midnight Library',0,0,1,1),
	 (13,NULL,'2021-05-26 23:34:05.92853','While Justice Sleeps',0,0,1,1),
	 (14,NULL,'2021-05-27 23:34:05.92853','The Four Winds',0,0,1,1),
	 (15,NULL,'2021-05-28 23:34:05.92853','Sooley',0,0,1,1),
	 (16,NULL,'2021-05-28 23:34:05.92853','The Blind Date',0,0,1,1),
	 (17,NULL,'2021-05-29 23:34:05.92853','Shadow and Bone',0,0,1,1),
	 (18,NULL,'2021-05-30 23:34:05.92853','People We Meet on Vacation',0,0,1,1),
	 (19,NULL,'2021-05-01 23:34:05.92853','That Summer',0,0,1,1),
	 (20,NULL,'2021-05-02 23:34:05.92853','Magic Hour',0,0,1,1),
	 (21,NULL,'2021-05-03 23:34:05.92853','Private Player',0,0,1,1),
	 (22,NULL,'2021-05-04 23:34:05.92853','He Who Fights with Monsters 2',0,0,1,1),
	 (23,NULL,'2021-05-05 23:34:05.92853','Where the Crawdads Sing',0,4,1,1);
SELECT setval('post_id_seq', (SELECT MAX(id) FROM post));


INSERT INTO public."comment" (id,comment_grp,comment_odr,"content",created_date_time,author_id,parent_comment_id,post_id) VALUES
	 (1,0,0,'Hi, nice to meet you','2021-05-28 23:16:23.316828',2,NULL,23),
	 (2,0,1,'[kkjaehwan] Hi :) long time no see','2021-05-28 23:17:01.911114',3,1,23),
	 (3,0,2,'[kjaehwan89] :) How are you doing?','2021-05-28 23:18:04.457321',2,2,23),
	 (4,0,3,'[kkjaehwan] ... Why are you guys doing this here?','2021-05-28 23:19:31.018875',1,3,23);
SELECT setval('comment_id_seq', (SELECT MAX(id) FROM comment));



INSERT INTO public."event" (id,created_date_time,description,end_date_time,end_enrolment_date_time,event_type,limit_of_enrolments,start_date_time,title,created_by_id,party_id) VALUES
	 (1,'2021-05-28 23:24:28.196454',NULL,'2021-05-31 23:24:00','2021-05-29 23:24:00','FCFS',2,'2021-05-30 23:24:00','shareknot test meeting',1,1),
	 (2,'2021-05-28 23:25:05.75091',NULL,'2021-07-07 23:25:00','2021-06-29 23:24:00','FCFS',2,'2021-07-01 23:24:00','shareknot future test meeting',1,1),
	 (3,'2021-05-28 23:26:09.622647',NULL,'2021-06-01 23:26:00','2021-05-29 23:26:00','CONFIRMATIVE',2,'2021-05-30 23:26:00','kkjaehwan''s party meeting',2,11),
	 (4,'2021-05-28 23:28:56.757479',NULL,'2021-06-05 23:28:00','2021-05-29 23:28:00','CONFIRMATIVE',2,'2021-06-01 23:28:00','test meeting',1,1);
SELECT setval('event_id_seq', (SELECT MAX(id) FROM event));
	 


INSERT INTO public.enrolment (id,accepted,attended,enroled_at,account_id,event_id) VALUES
	 (1,true,false,'2021-05-28 23:24:32.98718',1,1),
	 (2,true,false,'2021-05-28 23:25:10.661356',1,2),
	 (3,true,false,'2021-05-28 23:26:13.774091',2,3),
	 (4,true,false,'2021-05-28 23:26:49.225585',2,4),
	 (5,true,false,'2021-05-28 23:27:01.522927',2,1),
	 (6,false,false,'2021-05-28 23:29:01.155995',1,3);
SELECT setval('enrolment_id_seq', (SELECT MAX(id) FROM enrolment));