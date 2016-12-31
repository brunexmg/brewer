CREATE TABLE estado (
	codigo BIGINT PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	sigla VARCHAR(2) NOT NULL
);

CREATE TABLE cidade (
	codigo BIGSERIAL PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	codigo_estado BIGINT NOT NULL
);

ALTER TABLE cidade ADD CONSTRAINT cidade_estado_fk
	FOREIGN KEY(codigo_estado) REFERENCES estado(codigo);

INSERT INTO estado (codigo, nome, sigla) VALUES (1,'Acre', 'AC');
INSERT INTO estado (codigo, nome, sigla) VALUES (2,'Alagoas', 'AL');
INSERT INTO estado (codigo, nome, sigla) VALUES (3,'Amapá', 'AP');
INSERT INTO estado (codigo, nome, sigla) VALUES (4,'Amazonas', 'AM');
INSERT INTO estado (codigo, nome, sigla) VALUES (5,'Bahia', 'BA');
INSERT INTO estado (codigo, nome, sigla) VALUES (6,'Ceará', 'CE');
INSERT INTO estado (codigo, nome, sigla) VALUES (7,'Distrito Federal', 'DF');
INSERT INTO estado (codigo, nome, sigla) VALUES (8,'Espírito Santo', 'ES');
INSERT INTO estado (codigo, nome, sigla) VALUES (9,'Goiás', 'GO');
INSERT INTO estado (codigo, nome, sigla) VALUES (10,'Maranhão', 'MA');
INSERT INTO estado (codigo, nome, sigla) VALUES (11,'Mato Grosso', 'MT');
INSERT INTO estado (codigo, nome, sigla) VALUES (12,'Mato Grosso do Sul', 'MS');
INSERT INTO estado (codigo, nome, sigla) VALUES (13,'Minas Gerais', 'MG');
INSERT INTO estado (codigo, nome, sigla) VALUES (14,'Pará', 'PA');
INSERT INTO estado (codigo, nome, sigla) VALUES (15,'Paraíba', 'PB');
INSERT INTO estado (codigo, nome, sigla) VALUES (16,'Paraná', 'PR');
INSERT INTO estado (codigo, nome, sigla) VALUES (17,'Pernambuco', 'PE');
INSERT INTO estado (codigo, nome, sigla) VALUES (18,'Piauí', 'PI');
INSERT INTO estado (codigo, nome, sigla) VALUES (19,'Rio de Janeiro', 'RJ');
INSERT INTO estado (codigo, nome, sigla) VALUES (20,'Rio Grande do Norte', 'RN');
INSERT INTO estado (codigo, nome, sigla) VALUES (21,'Rio Grande do Sul', 'RS');
INSERT INTO estado (codigo, nome, sigla) VALUES (22,'Rondônia', 'RO');
INSERT INTO estado (codigo, nome, sigla) VALUES (23,'Roraima', 'RR');
INSERT INTO estado (codigo, nome, sigla) VALUES (24,'Santa Catarina', 'SC');
INSERT INTO estado (codigo, nome, sigla) VALUES (25,'São Paulo', 'SP');
INSERT INTO estado (codigo, nome, sigla) VALUES (26,'Sergipe', 'SE');
INSERT INTO estado (codigo, nome, sigla) VALUES (27,'Tocantins', 'TO');


INSERT INTO cidade (nome, codigo_estado) VALUES ('Rio Branco', 1);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Cruzeiro do Sul', 1);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Salvador', 5);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Porto Seguro', 5);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Santana', 5);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Goiânia', 9);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Itumbiara', 9);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Novo Brasil', 9);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Belo Horizonte', 13);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Uberlândia', 13);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Montes Claros', 13);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Florianópolis', 24);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Criciúma', 24);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Camboriú', 24);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Lages', 24);
INSERT INTO cidade (nome, codigo_estado) VALUES ('São Paulo', 25);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Ribeirão Preto', 25);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Campinas', 25);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Curitiba', 16);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Leópolis', 16);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Londrina', 16);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Sertaneja', 16);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Cornélio Procópio', 16);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Rancho Alegre', 16);