INSERT INTO users (`email`, `password`,`uloga`) VALUES('rade@admin.com', '123rr321', 'ADMIN');
INSERT INTO users (`email`, `password`,`uloga`) VALUES('rade@staff.com', '123rr321', 'STAFF');
INSERT INTO users (`email`, `password`,`uloga`) VALUES('rade@pac.com', '123rr321', 'PATIENTS');

INSERT INTO vesti (naziv, sadrzaj, vreme_objavljivanja) VALUE ('Vakcine za sve', 'Stigle vakcine za sve gradjane.', '2022-12-29T18:56');
INSERT INTO vesti (naziv, sadrzaj, vreme_objavljivanja) VALUE ('Vakcine na izmaku', 'Nema dovoljno vakcina.', '2022-12-27T14:24');
INSERT INTO vesti (naziv, sadrzaj, vreme_objavljivanja) VALUE ('Zalihe na izmaku', 'Strucnjaci predvidjaju kolaps.', '2022-12-20T10:51');

INSERT INTO dnevne_statistike (hospitalizovani, na_respiratoru, oboleli_24, testirani_24, ukupno_oboleli, vreme_objavljivanja)
    VALUE (3241, 256, 7564, 20134, 17564, '2022-12-20T10:51');
INSERT INTO dnevne_statistike (hospitalizovani, na_respiratoru, oboleli_24, testirani_24, ukupno_oboleli, vreme_objavljivanja)
    VALUE (3241, 256, 7564, 20134, 17564, '2022-12-21T10:51');