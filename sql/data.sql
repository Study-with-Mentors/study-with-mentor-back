-- create field
INSERT INTO field(field_id, name, description, version, code)
VALUES ('7DE575A8-C635-4A22-39F9-B13139392B1F', 'Physics', 'mi pede, nonummy ut, molestie in, tempus eu, ligula.', 0,
        'PH'),
       ('5D317627-9199-766D-E531-32D9282E19E6', 'Math', 'lobortis risus. In mi pede, nonummy ut,', 0, 'MA'),
       ('70D2986E-2EE2-A7C9-DD49-BD07731614A2', 'Computer science', 'Integer vulputate, risus a ultricies', 0, 'CS'),
       ('06187EA9-0447-AB9E-4D53-7BF6C2CB8507', 'Sociology',
        'Proin vel nisl. Quisque fringilla euismod enim. Etiam gravida', 0, 'SC'),
       ('3AF0EEA4-A9C9-8B14-112F-68B18F67737A', 'Philosophy',
        'lorem, vehicula et, rutrum eu, ultrices sit amet, risus. Donec', 0, 'PL');

-- create user profile
-- TODO: change the url
INSERT INTO image (image_id, asset_id, public_id, url, version)
VALUES ('42477450-39A2-4123-80C5-4B87F7B117D8', '1', '1', '1', 0),
       ('63BBC8B6-798F-A84E-AED8-63ACF7AE4BE1', '2', '2', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQyIkWCF_2NnTM-imjWtFuzsPEWFYYH-Vk76A&usqp=CAU', 0),
       ('56BF6B8E-673D-4C50-B185-5878D86219A2', '3', '3', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTsQ-YHX2i3RvTDDmpfnde4qyb2P8up7Wi3Ww&usqp=CAU', 0),
       ('26177D9D-86CF-65C0-4758-B1ABA6FA566A', '4', '4', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTIcoD_hp0DavkkRVB542L1kEYmPkEebzIXwg&usqp=CAU', 0),
       ('8CDA824B-87E7-8218-FB7E-2C7AE5BA9DC2', '5', '5', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQyIkWCF_2NnTM-imjWtFuzsPEWFYYH-Vk76A&usqp=CAU', 0),
       ('68CAB493-CDD9-3461-F3B3-6B722EC6137D', '6', '6', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTsQ-YHX2i3RvTDDmpfnde4qyb2P8up7Wi3Ww&usqp=CAU', 0),
       ('2E213D1A-1D65-1583-D4CC-89C6622738E9', '7', '7', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTIcoD_hp0DavkkRVB542L1kEYmPkEebzIXwg&usqp=CAU', 0),
       ('8585F8A4-7918-61D4-9A1D-268AA9094D37', '8', '8', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQyIkWCF_2NnTM-imjWtFuzsPEWFYYH-Vk76A&usqp=CAU', 0),
       ('DFA97EAB-76C7-3294-D97D-C4AD2F5F6A94', '9', '9', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTsQ-YHX2i3RvTDDmpfnde4qyb2P8up7Wi3Ww&usqp=CAU', 0),
       ('BDC85B38-0818-2C18-464F-8CAD01D0376E', '10', '10', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTIcoD_hp0DavkkRVB542L1kEYmPkEebzIXwg&usqp=CAU', 0);

-- create user
INSERT INTO [user] (user_id, first_name, last_name, email, password, profile_image_id, gender, birthdate, role, version)
VALUES ('42477450-39A2-4123-80C5-4B87F7B117D8', 'Kasimir', 'Blair', 'admin@gmail.com',
        '{bcrypt}$2a$10$nLG7abuUxPVWUKnhwtrUGe6xB4iw.9Z66JQHtN92uG5SoLCB43joe', '42477450-39A2-4123-80C5-4B87F7B117D8',
        'MALE',
        '2023-03-20', 'ADMIN', 0),
       ('63BBC8B6-798F-A84E-AED8-63ACF7AE4BE1', 'Howard', 'Irwin', 'student1@gmail.com',
        '{bcrypt}$2a$10$eXsvSWJaoRkANixk1pGoGuk3mKlWz7Ii4Gi/CVzCpDIv3cLg31hJm', '63BBC8B6-798F-A84E-AED8-63ACF7AE4BE1',
        'MALE',
        '2024-04-07', 'USER', 0),
       ('56BF6B8E-673D-4C50-B185-5878D86219A2', 'Fredericka', 'Swanson', 'student2@gmail.com',
        '{bcrypt}$2a$10$lVcCnNXrTbeullqIZpOPYuflnUcu6mEzYgwX5533gs945b4HWkmNa', '56BF6B8E-673D-4C50-B185-5878D86219A2',
        'FEMALE',
        '2024-05-18', 'USER', 0),
       ('26177D9D-86CF-65C0-4758-B1ABA6FA566A', 'Hermione', 'Brady', 'mentor1@gmail.com',
        '{bcrypt}$2a$10$Dd6Cnppxy1m9Ukbr8G3d1OLejJ1atAVCsjDV0mwFfXllartrBobki', '26177D9D-86CF-65C0-4758-B1ABA6FA566A',
        'MALE',
        '2022-08-04', 'USER', 0),
       ('8CDA824B-87E7-8218-FB7E-2C7AE5BA9DC2', 'Shoshana', 'Boyd', 'mentor2@gmail.com',
        '{bcrypt}$2a$10$NJD4t441o6KVblbW01Y.SetG4FPB/mVaI4xwQDQ8H86WXTk05ahHe', '8CDA824B-87E7-8218-FB7E-2C7AE5BA9DC2',
        'FEMALE',
        '2023-11-13', 'USER', 0),
       ('68CAB493-CDD9-3461-F3B3-6B722EC6137D', 'Kevin', 'Mccarty', 'student3@gmail.com',
        '{bcrypt}$2a$10$r3OOEi0TYx4sV7Vmbxj8eectYfq0NPpcO3ONnd8516xEHsLIXUhvG',
        '68CAB493-CDD9-3461-F3B3-6B722EC6137D', 'MALE', '2022-11-27', 'USER', 0),
       ('2E213D1A-1D65-1583-D4CC-89C6622738E9', 'Odysseus', 'Lewis', 'student4@gmail.com',
        '{bcrypt}$2a$10$xvVtRjMwyFmI1NvqjdRy5.LoSZz5MK.9E4xGIqRG7CoCTB1qJw72.',
        '2E213D1A-1D65-1583-D4CC-89C6622738E9', 'MALE', '2024-02-15', 'USER', 0),
       ('8585F8A4-7918-61D4-9A1D-268AA9094D37', 'Damon', 'Mccray', 'student5@gmail.com',
        '{bcrypt}$2a$10$DS4cpp/hyQ8wEHaaFJUs1.HUU5zMaxEG60QgWKD3tnWiOG.xyXi8u',
        '8585F8A4-7918-61D4-9A1D-268AA9094D37', 'MALE', '2023-07-07', 'USER', 0),
       ('DFA97EAB-76C7-3294-D97D-C4AD2F5F6A94', 'Chancellor', 'Guerrero', 'mentor3@gmail.com',
        '{bcrypt}$2a$10$ZaivEffu7o7YctCfOHNL7.pztyiPmpIR2KG4WQbHnDtWLyVRlU4wy',
        'DFA97EAB-76C7-3294-D97D-C4AD2F5F6A94', 'FEMALE', '2022-11-12', 'USER', 0),
       ('BDC85B38-0818-2C18-464F-8CAD01D0376E', 'Griffith', 'Frazier', 'mentor4@gmail.com',
        '{bcrypt}$2a$10$8xHL.8K9lbAM3uWkYC6b/e1Ax1ym2dfk8X/Uqu8QkH3E0TTtclNAi',
        'BDC85B38-0818-2C18-464F-8CAD01D0376E', 'FEMALE', '2024-06-04', 'USER', 0);
-- create mentor
INSERT INTO mentor (mentor_id, bio, degree, version, field_id)
VALUES ('26177D9D-86CF-65C0-4758-B1ABA6FA566A', 'some forth one introduction in here', 'MASTER', 0,
        '70D2986E-2EE2-A7C9-DD49-BD07731614A2'), -- Computer science
       ('8CDA824B-87E7-8218-FB7E-2C7AE5BA9DC2', 'some fifth one introduction in here', 'MASTER', 0,
        '06187EA9-0447-AB9E-4D53-7BF6C2CB8507'), -- Sociology
       ('DFA97EAB-76C7-3294-D97D-C4AD2F5F6A94', 'some more mentor introduction here', 'MASTER', 0,
        '7DE575A8-C635-4A22-39F9-B13139392B1F'), -- Physics
       ('BDC85B38-0818-2C18-464F-8CAD01D0376E', 'some some more more more inductiondu', 'BACHELOR', 0,
        '3AF0EEA4-A9C9-8B14-112F-68B18F67737A');
-- Philosophy

-- create student
INSERT INTO student (student_id, education, bio, experience, version, year)
VALUES ('63BBC8B6-798F-A84E-AED8-63ACF7AE4BE1', 'COLLEGE', 'first student bio', 'nothing', 0, 2022),
       ('56BF6B8E-673D-4C50-B185-5878D86219A2', 'BACHELOR', 'second student bio', 'nothing', 0, 2019),
       ('68CAB493-CDD9-3461-F3B3-6B722EC6137D', 'COLLEGE', 'i dont know', 'nothing', 0, 2022),
       ('2E213D1A-1D65-1583-D4CC-89C6622738E9', 'COLLEGE', 'maybe a bio', 'nothing', 0, 2022),
       ('8585F8A4-7918-61D4-9A1D-268AA9094D37', 'COLLEGE', 'not a really good bio', 'nothing', 0, 2022);

-- create course image
-- TODO: change url
INSERT INTO image (image_id, asset_id, public_id, url, version)
VALUES ('C7CF563D-5EEC-E521-A8C2-363C425435D3', '1', '1', 'https://cdn.britannica.com/05/155405-050-F8969EE6/Spring-flowers-fruit-trees-bloom.jpg', 0),
       ('0DCE39B1-E5D8-0446-82FC-F82A9FAC8B9E', '2', '2', 'https://cdn.msruas.ac.in/ruas/imager/images/fac_social/programmes/2021/510310/Sociology.f1616144414_37c3d9953bc62f6c35bd93f93217bddd.png', 0),
       ('121D7DC1-5E94-C931-B461-732EB3D2D3BB', '3', '3', 'https://www.farmersalmanac.com/wp-content/uploads/2023/05/Hello-Spring-945x630-v2.png', 0),
       ('25DDBA99-E339-5888-A2C1-C91DD6764391', '4', '4', 'https://i0.wp.com/calmatters.org/wp-content/uploads/2021/08/math-curriculum.jpg?fit=2000%2C1500&ssl=1', 0),
       ('B5262A68-DE7E-7885-317F-72F7E52751E4', '5', '5', 'https://cdn.britannica.com/65/216665-050-A83A782E/Sisyphus-Titian-1548-49-Prado-Museum-Madrid.jpg', 0);

-- create course
INSERT INTO course (course_id, image_id, course_level, description, full_name, intended_learner, learning_outcome,
                    short_name,
                    status, version, field_id, user_id)
VALUES ('C7CF563D-5EEC-E521-A8C2-363C425435D3', 'C7CF563D-5EEC-E521-A8C2-363C425435D3', 'FUNDAMENTAL',
        'Introduction to CS',
        'Introduction to CS: everything you need to know', 'COLLEGE', 'understand cs', 'CS introduction', 'DRAFTING',
        0, '70D2986E-2EE2-A7C9-DD49-BD07731614A2', '26177D9D-86CF-65C0-4758-B1ABA6FA566A'),
       ('0DCE39B1-E5D8-0446-82FC-F82A9FAC8B9E', '0DCE39B1-E5D8-0446-82FC-F82A9FAC8B9E', 'FUNDAMENTAL',
        'Introduction to sociology', 'Sociology, what is it',
        'COLLEGE', 'know what sociology is', 'socilogoy introduction', 'OPEN', 0,
        '06187EA9-0447-AB9E-4D53-7BF6C2CB8507', '8CDA824B-87E7-8218-FB7E-2C7AE5BA9DC2'),
       ('121D7DC1-5E94-C931-B461-732EB3D2D3BB', '121D7DC1-5E94-C931-B461-732EB3D2D3BB', 'ADVANCE', 'Advance spring',
        'Everything you need to know about spring',
        'COLLEGE', 'backend dev', 'Spring advance', 'CLOSE', 0, '70D2986E-2EE2-A7C9-DD49-BD07731614A2',
        '26177D9D-86CF-65C0-4758-B1ABA6FA566A'),
       ('25DDBA99-E339-5888-A2C1-C91DD6764391', '25DDBA99-E339-5888-A2C1-C91DD6764391', 'ADVANCE', 'Deep dive physics',
        'Physics and math', 'COLLEGE',
        'more math', 'Physics course', 'CLOSE', 0, '7DE575A8-C635-4A22-39F9-B13139392B1F',
        'DFA97EAB-76C7-3294-D97D-C4AD2F5F6A94'),
       ('B5262A68-DE7E-7885-317F-72F7E52751E4', 'B5262A68-DE7E-7885-317F-72F7E52751E4', 'ADVANCE', 'HOHO Phil',
        'what is this', 'COLLEGE', 'i dont know',
        'phil', 'DISABLE', 0, '3AF0EEA4-A9C9-8B14-112F-68B18F67737A', 'BDC85B38-0818-2C18-464F-8CAD01D0376E');

-- create session
INSERT INTO session (session_id, description, resource, session_name, session_num, type, course_id, version)
VALUES ('C7CF563D-5EEC-E521-A8C2-363C425435D3', 'orci. Phasellus dapibus quam quis diam. Pellentesque habitant',
        'dis parturient montes, nascetur ridiculus mus. Aenean eget magna. Suspendisse', 'Introduction to CS session 1',
        1, 'NONE', 'C7CF563D-5EEC-E521-A8C2-363C425435D3', 0),
       ('0DCE39B1-E5D8-0446-82FC-F82A9FAC8B9E', 'nunc nulla vulputate dui, nec', 'diam. Duis mi',
        'Introduction to CS session 2', 2, 'NONE', 'C7CF563D-5EEC-E521-A8C2-363C425435D3', 0),
       ('121D7DC1-5E94-C931-B461-732EB3D2D3BB', 'luctus sit amet, faucibus ut, nulla.', 'malesuada. Integer',
        'Introduction to CS session 3', 3, 'NONE', 'C7CF563D-5EEC-E521-A8C2-363C425435D3', 0);

INSERT INTO session (session_id, description, resource, session_name, session_num, type, course_id, version)
VALUES ('4BDBD033-4645-7C94-53BD-30A2B774B9A8', 'mollis. Phasellus libero mauris, aliquam eu, accumsan',
        'rutrum, justo. Praesent luctus. Curabitur egestas nunc', 'Sociology session 1', 1, 'NONE',
        '0DCE39B1-E5D8-0446-82FC-F82A9FAC8B9E', 0),
       ('E644167D-4A69-422A-3B35-663C17393CD7', 'tincidunt aliquam arcu. Aliquam ultrices iaculis odio. Nam interdum',
        'sit amet, risus. Donec', 'Sociology session 2', 2, 'NONE', '0DCE39B1-E5D8-0446-82FC-F82A9FAC8B9E', 0),
       ('30CFE172-E1A2-E66D-7106-D8232664C1E0', 'aliquet vel, vulputate eu,', 'ut, pellentesque eget, dictum',
        'Socialoasdf sessoin 3', 3, 'NONE', '0DCE39B1-E5D8-0446-82FC-F82A9FAC8B9E', 0),
       ('2E1E3961-90B2-FE34-4D65-1EC294B78786', 'cursus. Nunc mauris elit, dictum eu, eleifend nec, malesuada', 'et',
        'some thing about socilogoy session 4', 4, 'NONE', '0DCE39B1-E5D8-0446-82FC-F82A9FAC8B9E', 0);

INSERT INTO session (session_id, description, resource, session_name, session_num, type, course_id, version)
VALUES ('6A05335B-9C53-38BE-538C-7C5D23A171F8', 'at risus. Nunc ac sem ut dolor',
        'non magna. Nam ligula elit, pretium et, rutrum non,', 'Advance spring session 1', 1, 'NONE',
        '121D7DC1-5E94-C931-B461-732EB3D2D3BB', 0),
       ('431E1761-A114-BE11-885A-808DBE14D5E6', 'at pretium aliquet, metus urna convallis erat, eget',
        'vitae, posuere at, velit. Cras lorem lorem, luctus ut,', 'Autowired and session 2', 2, 'NONE',
        '121D7DC1-5E94-C931-B461-732EB3D2D3BB', 0);

INSERT INTO session (session_id, description, resource, session_name, session_num, type, course_id, version)
VALUES ('144DABC6-FA09-C8A7-33DA-7C8EE0E75A55', 'fermentum metus. Aenean', 'Proin nisl sem, consequat',
        'physics session 1', 1, 'NONE', '25DDBA99-E339-5888-A2C1-C91DD6764391', 0),
       ('E8AE6066-1C4C-2AF5-1339-B63C9B5E8796', 'eu neque pellentesque massa lobortis ultrices. Vivamus', 'risus.',
        'physics session 2', 2, 'NONE', '25DDBA99-E339-5888-A2C1-C91DD6764391', 0),
       ('E78BD6F3-CCE5-37DC-FB75-D5C1CC5BC7E9', 'Cras vehicula aliquet libero. Integer in magna. Phasellus dolor',
        'Sed congue, elit sed consequat', 'Newton and physics session 3', 3, 'NONE',
        '25DDBA99-E339-5888-A2C1-C91DD6764391', 0);

INSERT INTO session (session_id, description, resource, session_name, session_num, type, course_id, version)
VALUES ('396EC61E-819C-3ADF-8C24-2C7263A9C517', 'Morbi quis urna. Nunc quis arcu vel quam dignissim',
        'in, hendrerit consectetuer, cursus et, magna. Praesent', 'Philos something 1', 1, 'NONE',
        'B5262A68-DE7E-7885-317F-72F7E52751E4', 0),
       ('D2DB3812-6E96-DD18-4954-7582C3E2B0EA', 'posuere', 'cursus purus. Nullam', 'Phil other 2', 2, 'NONE',
        'B5262A68-DE7E-7885-317F-72F7E52751E4', 0),
       ('54DB99C7-4800-76E6-77D3-8EB24B1B9B2A', 'lorem ipsum sodales', 'Duis sit amet diam', 'Phil session 3', 3,
        'NONE', 'B5262A68-DE7E-7885-317F-72F7E52751E4', 0);

-- create activity
INSERT INTO activity (activity_id, title, description, session_id, version)
VALUES ('CCEEA4D2-625C-8429-FEC9-FC6663ACCC36', 'sollicitudin a, malesuada id, erat. Etiam vestibulum massa',
        'Integer tincidunt aliquam arcu. Aliquam ultrices iaculis odio. Nam interdum enim non',
        'C7CF563D-5EEC-E521-A8C2-363C425435D3', 0),
       ('65D5C65C-CC1E-3CD0-6E84-02C61C84C92E', 'imperdiet nec, leo. Morbi neque tellus, imperdiet non,',
        'et libero. Proin mi. Aliquam', 'C7CF563D-5EEC-E521-A8C2-363C425435D3', 0),
       ('542E7BFA-5662-F5C9-3C81-36738224BFBA', 'Aliquam ornare, libero at auctor ullamcorper,',
        'sapien molestie orci tincidunt adipiscing. Mauris molestie pharetra nibh.',
        '0DCE39B1-E5D8-0446-82FC-F82A9FAC8B9E', 0),
       ('8B87CF23-2806-C70E-5EB2-30C6C55DEDE2', 'et pede. Nunc sed orci lobortis augue scelerisque',
        'semper cursus. Integer mollis. Integer tincidunt aliquam arcu. Aliquam ultrices iaculis odio. Nam interdum',
        '0DCE39B1-E5D8-0446-82FC-F82A9FAC8B9E', 0),
       ('BB8D61AB-AE8D-6509-D39D-FE9C7669932E', 'elit fermentum risus,',
        'sed libero. Proin sed turpis nec mauris blandit mattis. Cras eget nisi',
        '121D7DC1-5E94-C931-B461-732EB3D2D3BB', 0),
       ('0E33BD3C-2571-2334-B408-9AC0AA5137F7', 'Etiam vestibulum massa rutrum',
        'vitae diam. Proin dolor. Nulla semper tellus id nunc interdum feugiat. Sed nec metus',
        '121D7DC1-5E94-C931-B461-732EB3D2D3BB', 0),
       ('343806F8-5A78-F55C-2536-EB57937117B8', 'turpis vitae',
        'tellus. Aenean egestas hendrerit neque. In ornare sagittis', '121D7DC1-5E94-C931-B461-732EB3D2D3BB', 0);

INSERT INTO activity (activity_id, title, description, session_id, version)
VALUES ('005D4FEE-E26D-5CF2-8811-8C9261F3D337', 'ullamcorper viverra. Maecenas iaculis aliquet diam. Sed diam',
        'nulla. In tincidunt congue turpis. In condimentum. Donec at arcu. Vestibulum ante ipsum primis in',
        '4BDBD033-4645-7C94-53BD-30A2B774B9A8', 0),
       ('D01BC8E9-D0A0-B47E-675D-58CA5DD3F39C', 'sem molestie sodales. Mauris blandit enim consequat',
        'dolor. Donec fringilla. Donec feugiat metus sit amet ante. Vivamus non lorem vitae odio',
        'E644167D-4A69-422A-3B35-663C17393CD7', 0),
       ('4598148C-95B0-EDBC-A4FB-E4D768730576',
        'felis, adipiscing fringilla, porttitor vulputate, posuere vulputate, lacus. Cras interdum.',
        'Proin velit. Sed malesuada augue ut lacus. Nulla tincidunt, neque vitae semper egestas, urna',
        '30CFE172-E1A2-E66D-7106-D8232664C1E0', 0),
       ('1975268C-8154-775F-0E66-90C12D798333', 'sed sem egestas blandit. Nam nulla magna, malesuada',
        'Integer eu lacus. Quisque imperdiet, erat nonummy ultricies ornare, elit',
        '30CFE172-E1A2-E66D-7106-D8232664C1E0', 0),
       ('1F6EDC63-5F13-6CFC-DA86-661A9174CB91', 'non magna. Nam ligula elit, pretium et,',
        'orci lobortis augue scelerisque mollis. Phasellus libero mauris,', '2E1E3961-90B2-FE34-4D65-1EC294B78786', 0);

INSERT INTO activity (activity_id, title, description, session_id, version)
VALUES ('0557160A-AD51-A544-A2CB-257365451592', 'vitae odio sagittis semper. Nam',
        'ultrices posuere cubilia Curae Phasellus ornare.', '6A05335B-9C53-38BE-538C-7C5D23A171F8', 0),
       ('CB733AE9-292B-FA61-184B-319889825DC6', 'fames ac turpis egestas. Aliquam fringilla cursus purus.',
        'senectus et netus et malesuada fames ac turpis egestas. Fusce aliquet magna a',
        '6A05335B-9C53-38BE-538C-7C5D23A171F8', 0),
       ('2917E15B-4B40-372A-DBBD-C4E2DC51F8AB', 'venenatis a, magna. Lorem ipsum dolor',
        'pharetra ut, pharetra sed, hendrerit a,', '431E1761-A114-BE11-885A-808DBE14D5E6', 0);

INSERT INTO activity (activity_id, title, description, session_id, version)
VALUES ('5A630DE6-CBE5-DF7E-637E-5AAE7E9BC61E', 'at, iaculis quis, pede. Praesent eu dui.',
        'Nulla tincidunt, neque vitae semper egestas, urna justo faucibus lectus, a sollicitudin orci sem',
        '144DABC6-FA09-C8A7-33DA-7C8EE0E75A55', 0),
       ('60E562CC-96F1-2B38-7E17-E7FAC7811A14', 'Praesent eu nulla at sem molestie sodales. Mauris blandit',
        'Phasellus libero mauris, aliquam eu, accumsan sed, facilisis vitae, orci. Phasellus dapibus',
        '144DABC6-FA09-C8A7-33DA-7C8EE0E75A55', 0),
       ('C48E4DDA-8B80-3427-7DE6-4A2FE6809C93', 'Phasellus libero mauris, aliquam eu, accumsan sed, facilisis',
        'rutrum. Fusce dolor quam, elementum at, egestas a, scelerisque sed, sapien. Nunc',
        'E8AE6066-1C4C-2AF5-1339-B63C9B5E8796', 0),
       ('A3852454-1B9B-DC1A-6A77-1827678440A1', 'at arcu. Vestibulum ante ipsum primis in faucibus',
        'rutrum eu, ultrices sit amet, risus. Donec nibh enim, gravida sit amet, dapibus',
        'E8AE6066-1C4C-2AF5-1339-B63C9B5E8796', 0),
       ('371C659E-55BB-9537-A1ED-527AC52E356B', 'nec urna et arcu',
        'quam quis diam. Pellentesque habitant morbi tristique senectus et netus et',
        'E78BD6F3-CCE5-37DC-FB75-D5C1CC5BC7E9', 0);

INSERT INTO activity (activity_id, title, description, session_id, version)
VALUES ('A1F1D999-85E7-0E29-4A49-CA27104EB35A', 'Quisque', 'mauris elit, dictum eu, eleifend nec, malesuada ut,',
        '396EC61E-819C-3ADF-8C24-2C7263A9C517', 0),
       ('1172C4DE-B2A8-F87B-50D4-CD6761BA8E11', 'nibh enim,',
        'Maecenas mi felis, adipiscing fringilla, porttitor vulputate, posuere vulputate, lacus. Cras interdum. Nunc sollicitudin',
        'D2DB3812-6E96-DD18-4954-7582C3E2B0EA', 0),
       ('CCE68744-470D-06A9-FD1B-975D4D04C570', 'auctor non, feugiat nec, diam.',
        'consequat enim diam vel arcu. Curabitur ut odio vel est tempor bibendum. Donec',
        '54DB99C7-4800-76E6-77D3-8EB24B1B9B2A', 0),
       ('63CEAE5B-B704-BE8D-A4DC-A281E97D1E04', 'non justo. Proin',
        'mollis lectus pede et risus. Quisque libero lacus, varius et,', '54DB99C7-4800-76E6-77D3-8EB24B1B9B2A', 0);

-- 1 open, 1 in progress, 1 cancel, 1 complete
INSERT INTO class (class_id, end_date, enrollment_end_date, price, start_date, version, course_id, status)
VALUES ('F4217968-7CA1-AD8F-27C6-19481DB88298', '2022-12-16', '2022-07-31', 20000, '2022-11-12', 0,
        'B5262A68-DE7E-7885-317F-72F7E52751E4', 'COMPLETE'),
       ('AE32BCEE-7878-EA94-5E1A-4029F6AC52B1', '2023-08-21', '2023-05-20', 50000, '2023-05-26', 0,
        '121D7DC1-5E94-C931-B461-732EB3D2D3BB', 'IN_PROGRESS'),
       ('E87883A9-9212-762A-78D3-DD050BCD9A0B', '2023-07-10', '2023-05-03', 80000, '2023-06-23', 0,
        'B5262A68-DE7E-7885-317F-72F7E52751E4', 'CANCEL'),
       ('9A6AC665-9898-C30E-D336-3799F56154CC', '2024-04-06', '2023-08-05', 60000, '2024-02-01', 0,
        '121D7DC1-5E94-C931-B461-732EB3D2D3BB', 'OPEN'),
       ('1B9438E5-CB7C-49FF-97C8-A6B9803F9463', '2023-08-10', '2023-05-11', 40000, '2023-07-21', 0,
        '121D7DC1-5E94-C931-B461-732EB3D2D3BB', 'OPEN');

INSERT INTO lesson (lession_id, end_time, lesson_num, location, start_time, version, class_id, session_id)
VALUES ('73295E29-53DB-E6B3-F264-28C884E0A08E', '2022-11-12 21:00:00', 1, 'ONLINE', '2022-11-12 19:00:00', 0,
        'F4217968-7CA1-AD8F-27C6-19481DB88298', '396EC61E-819C-3ADF-8C24-2C7263A9C517'),
       ('91ECD898-88AC-DD98-E928-6944C068BAD1', '2022-11-24 21:00:00', 2, 'ONLINE', '2022-11-24 19:00:00', 0,
        'F4217968-7CA1-AD8F-27C6-19481DB88298', 'D2DB3812-6E96-DD18-4954-7582C3E2B0EA'),
       ('C8CC30BC-2468-A36C-CC9D-C4966F13287E', '2022-12-16 21:00:00', 3, 'ONLINE', '2022-12-16 19:00:00', 0,
        'F4217968-7CA1-AD8F-27C6-19481DB88298', '54DB99C7-4800-76E6-77D3-8EB24B1B9B2A');

INSERT INTO lesson (lession_id, end_time, lesson_num, location, start_time, version, class_id, session_id)
VALUES ('143AC812-9DE4-CADB-D6CE-CBE3383C87C7', '2023-05-26 21:00:00', 1, 'ONLINE', '2023-05-26 19:00:00', 0,
        'AE32BCEE-7878-EA94-5E1A-4029F6AC52B1', '6A05335B-9C53-38BE-538C-7C5D23A171F8'),
       ('7784F7D5-156C-225F-E607-A5D431D3D955', '2023-08-21 21:00:00', 2, 'ONLINE', '2023-08-21 19:00:00', 0,
        'AE32BCEE-7878-EA94-5E1A-4029F6AC52B1', '431E1761-A114-BE11-885A-808DBE14D5E6');

INSERT INTO lesson (lession_id, end_time, lesson_num, location, start_time, version, class_id, session_id)
VALUES ('04A88121-8CA4-C8A1-7664-AABC67A651BE', '2023-06-23 21:00:00', 1, 'ONLINE', '2023-06-23 19:00:00', 0,
        'E87883A9-9212-762A-78D3-DD050BCD9A0B', '396EC61E-819C-3ADF-8C24-2C7263A9C517'),
       ('7BB462D7-707C-E38E-935B-6721BBC6B524', '2023-07-01 21:00:00', 2, 'ONLINE', '2023-07-01 19:00:00', 0,
        'E87883A9-9212-762A-78D3-DD050BCD9A0B', 'D2DB3812-6E96-DD18-4954-7582C3E2B0EA'),
       ('945BD1A8-8E4F-26B8-4361-236B78B6F89B', '2023-07-10 21:00:00', 3, 'ONLINE', '2023-07-10 19:00:00', 0,
        'E87883A9-9212-762A-78D3-DD050BCD9A0B', '54DB99C7-4800-76E6-77D3-8EB24B1B9B2A');

INSERT INTO lesson (lession_id, end_time, lesson_num, location, start_time, version, class_id, session_id)
VALUES ('B7676A5F-D9AC-545E-73F4-AA2C28856B45', '2024-02-01 21:00:00', 1, 'ONLINE', '2024-02-01 19:00:00', 0,
        '9A6AC665-9898-C30E-D336-3799F56154CC', '6A05335B-9C53-38BE-538C-7C5D23A171F8'),
       ('03397211-FAD6-BD7E-C0E6-424D665855E2', '2024-04-06 21:00:00', 1, 'ONLINE', '2024-04-06 19:00:00', 0,
        '9A6AC665-9898-C30E-D336-3799F56154CC', '431E1761-A114-BE11-885A-808DBE14D5E6');

INSERT INTO lesson (lession_id, end_time, lesson_num, location, start_time, version, class_id, session_id)
VALUES ('CFD6973E-BFA5-7030-5685-13CB7F18A572', '2023-07-21 21:00:00', 1, 'ONLINE', '2023-07-21 19:00:00', 0,
        '1B9438E5-CB7C-49FF-97C8-A6B9803F9463', '6A05335B-9C53-38BE-538C-7C5D23A171F8'),
       ('5858C778-49E1-8E32-490A-915AED34413A', '2023-08-10 21:00:00', 1, 'ONLINE', '2023-08-10 19:00:00', 0,
        '1B9438E5-CB7C-49FF-97C8-A6B9803F9463', '431E1761-A114-BE11-885A-808DBE14D5E6');

INSERT INTO enrollment (enrollment_id, user_id, class_id, enrollment_date, status, version)
VALUES ('EAFE12DF-2C39-4CA1-7EC4-67475B015B9B', '63BBC8B6-798F-A84E-AED8-63ACF7AE4BE1',
        'F4217968-7CA1-AD8F-27C6-19481DB88298', '2022-06-30', 'EXPIRED', 0),
       ('A5450C0C-18D3-E328-85C4-36264123F044', '56BF6B8E-673D-4C50-B185-5878D86219A2',
        'F4217968-7CA1-AD8F-27C6-19481DB88298', '2022-07-30', 'EXPIRED', 0),
       ('B97BACF8-C27F-9EF5-D542-BEFCA870EF6C', '68CAB493-CDD9-3461-F3B3-6B722EC6137D',
        'F4217968-7CA1-AD8F-27C6-19481DB88298', '2022-07-18', 'EXPIRED', 0);

INSERT INTO enrollment (enrollment_id, user_id, class_id, enrollment_date, status, version)
VALUES ('04A88121-8CA4-C8A1-7664-AABC67A651BE', '63BBC8B6-798F-A84E-AED8-63ACF7AE4BE1',
        'AE32BCEE-7878-EA94-5E1A-4029F6AC52B1', '2023-05-30', 'ENROLLED', 0),
       ('7BB462D7-707C-E38E-935B-6721BBC6B524', '8585F8A4-7918-61D4-9A1D-268AA9094D37',
        'AE32BCEE-7878-EA94-5E1A-4029F6AC52B1', '2023-08-01', 'ENROLLED', 0),
       ('945BD1A8-8E4F-26B8-4361-236B78B6F89B', '56BF6B8E-673D-4C50-B185-5878D86219A2',
        'AE32BCEE-7878-EA94-5E1A-4029F6AC52B1', '2022-07-02', 'ENROLLED', 0);

INSERT INTO enrollment (enrollment_id, user_id, class_id, enrollment_date, status, version)
VALUES ('B643D3B2-7E45-9AF6-A4D5-2BE9937C0EAE', '56BF6B8E-673D-4C50-B185-5878D86219A2',
        '1B9438E5-CB7C-49FF-97C8-A6B9803F9463', '2023-04-14', 'ENROLLED', 0),
       ('D7782BE4-01C2-22BB-778C-D5EB21E51675', '2E213D1A-1D65-1583-D4CC-89C6622738E9',
        '1B9438E5-CB7C-49FF-97C8-A6B9803F9463', '2023-04-29', 'ENROLLED', 0);

INSERT INTO invoice (invoice_id, pay_date, status, total_price, type, version)
VALUES ('EAFE12DF-2C39-4CA1-7EC4-67475B015B9B', '2023-05-30', 'PAYED', 8, 'VNPAY', 0),
       ('A5450C0C-18D3-E328-85C4-36264123F044', '2023-05-30', 'PAYED', 8, 'VNPAY', 0),
       ('B97BACF8-C27F-9EF5-D542-BEFCA870EF6C', '2023-05-30', 'PAYED', 8, 'VNPAY', 0);

INSERT INTO invoice (invoice_id, pay_date, status, total_price, type, version)
VALUES ('04A88121-8CA4-C8A1-7664-AABC67A651BE', '2023-05-30', 'PAYED', 8, 'VNPAY', 0),
       ('7BB462D7-707C-E38E-935B-6721BBC6B524', '2023-05-30', 'PAYED', 8, 'VNPAY', 0),
       ('945BD1A8-8E4F-26B8-4361-236B78B6F89B', '2023-05-30', 'PAYED', 8, 'VNPAY', 0);

INSERT INTO invoice (invoice_id, pay_date, status, total_price, type, version)
VALUES ('B643D3B2-7E45-9AF6-A4D5-2BE9937C0EAE', '2023-05-30', 'PAYED', 8, 'VNPAY', 0),
       ('D7782BE4-01C2-22BB-778C-D5EB21E51675', '2023-05-30', 'PAYED', 8, 'VNPAY', 0);

GO;