CREATE TABLE usuario (
	codigo SERIAL PRIMARY KEY,
	nome VARCHAR(100) NOT NULL,
	email VARCHAR(60) NOT NULL,
	senha VARCHAR(130) NOT NULL,
	ativo BOOLEAN default true,
	data_nascimento DATE
);

CREATE TABLE grupo (
	codigo BIGINT PRIMARY KEY,
	nome VARCHAR(50) NOT NULL
);

CREATE TABLE permissao (
	codigo BIGINT PRIMARY KEY,
	nome VARCHAR(50) NOT NULL
);

CREATE TABLE usuario_grupo (
	codigo_usuario BIGINT NOT NULL,
	codigo_grupo BIGINT NOT NULL,
 	PRIMARY KEY(codigo_usuario, codigo_grupo)
);
ALTER TABLE usuario_grupo ADD CONSTRAINT usuario_grupo_usuario_fk
		FOREIGN KEY(codigo_usuario) REFERENCES usuario(codigo);
ALTER TABLE usuario_grupo ADD CONSTRAINT usuario_grupo_grupo_fk
		FOREIGN KEY(codigo_grupo) REFERENCES grupo(codigo);

CREATE TABLE grupo_permissao (
	codigo_grupo BIGINT NOT NULL,
	codigo_permissao BIGINT NOT NULL,
	PRIMARY KEY(codigo_grupo, codigo_permissao)
);
ALTER TABLE grupo_permissao	ADD CONSTRAINT grupo_permissao_grupo_fk
		FOREIGN KEY(codigo_grupo) REFERENCES grupo(codigo);
ALTER TABLE grupo_permissao	ADD CONSTRAINT grupo_permissao_permissao_fk
		FOREIGN KEY(codigo_permissao) REFERENCES permissao(codigo);