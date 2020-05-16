DROP DATABASE DBlistas;
CREATE DATABASE DBlistas;
USE DBlistas;
CREATE TABLE alumnos(
matricula INT UNSIGNED NOT NULL,
primerAp VARCHAR(30),
segundoAp VARCHAR(30),
nombres VARCHAR(50),
PRIMARY KEY (Matricula));

SELECT * FROM alumnos;

#drop procedure if exists sp_llenaGrupos;
delimiter $$
create procedure sp_consultaListaAlumnos()
begin
	declare idCont 		int default 0;
    declare existe		int default 0;
    
    set idCont =(select count(*)from alumnos);
    
	if idCont = 0 then
		SELECT existe;
	else
		SELECT * FROM alumnos;
    end if;
end$$
delimiter ;

#drop procedure if exists sp_llenaGrupos;
delimiter $$
create procedure sp_registraAlumno(IN boleta INT UNSIGNED,IN materno VARCHAR(30),IN paterno VARCHAR(30),IN nomb VARCHAR(50))
begin
	declare idCont 		int default 0;
    declare existe		int default 0;
    
    set idCont =(select count(*)from alumnos WHERE matricula = boleta);
    
	if idCont = 0 then
		INSERT INTO alumnos (matricula,primerAp,segundoAp,nombres) VALUES (boleta,materno,paterno,nomb);
		SET existe = 0;
		SELECT existe;
	else
		SET existe = 1;
		SELECT existe;
    end if;
end$$
delimiter ;

#drop procedure if exists sp_llenaGrupos;
delimiter $$
create procedure sp_modificaAlumno(IN boleta INT UNSIGNED,IN materno VARCHAR(30),IN paterno VARCHAR(30),IN nomb VARCHAR(50))
begin
	declare idCont 		int default 0;
    declare existe		int default 0;
    
    set idCont =(select count(*)from alumnos WHERE matricula = boleta);
    
	if idCont = 0 then
		SET existe=0;
		SELECT existe;
	else
		UPDATE alumnos SET primerAp = materno, segundoAp = paterno, nombres = nomb WHERE matricula = boleta;
		SET existe = 1;
		SELECT existe;
    end if;
end$$
delimiter ;

#drop procedure if exists sp_llenaGrupos;
delimiter $$
create procedure sp_eliminaAlumno(IN boleta INT UNSIGNED)
begin
	declare idCont 		int default 0;
    declare existe		int default 0;
    
    set idCont =(select count(*)from alumnos WHERE matricula = boleta);
    
	if idCont = 0 then
		SET existe=0;
		SELECT existe;
	else
		DELETE FROM alumnos WHERE alumnos.matricula = boleta;
		SET existe = 1;
		SELECT existe;
    end if;
end$$
delimiter ;
