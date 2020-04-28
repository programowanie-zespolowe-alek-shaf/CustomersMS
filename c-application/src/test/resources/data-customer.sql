use customer;

--@formatter:on
--VALUES                  username   password first_name last_name email                   phone            address                           enabled
INSERT INTO user VALUES('user997', '$2y$12$9tLmBKZfuC9LZUcgesXDo.J2kuBiDBtcQxLeGibxnTB8gRNohHw2O',  'John',    'Doe',    'twojastara@gmail.com', '+48500600700',  '123 Elf Road, North Pole, 8888', 1);
INSERT INTO user VALUES('user999', '$2y$12$LOXQ8B6U6qYWt7lpq5t2Cezsoe6kBGJtE4tbS2daO0LaXloF/NjmK', 'Robert',  'Smith',  'nietwoja@gmail.com',   '+911943243233', '123 Elf Road, North Pole, 8888', 1);

--VALUES                      id  username  role
INSERT INTO user_roles VALUES(1, 'user997', 'ROLE_USER');
INSERT INTO user_roles VALUES(2, 'user997', 'ROLE_ADMIN');
INSERT INTO user_roles VALUES(3, 'user999', 'ROLE_USER');

--@formatter:off