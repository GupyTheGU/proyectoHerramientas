DROP DATABASE DBlistas;
CREATE DATABASE DBlistas;
USE DBlistas;

CREATE TABLE profesores(
numero INT UNSIGNED NOT NULL,
primerAp VARCHAR(30),
segundoAp VARCHAR(30),
nombres VARCHAR(50),
PRIMARY KEY (numero));

CREATE TABLE alumnos(
matricula INT UNSIGNED NOT NULL,
primerAp VARCHAR(30),
segundoAp VARCHAR(30),
nombres VARCHAR(50),
PRIMARY KEY (matricula));

CREATE TABLE rel_ProfAlmn(
idRel 	INT UNSIGNED AUTO_INCREMENT,
idProf	INT UNSIGNED NOT NULL,
idAlmn	INT UNSIGNED NOT NULL,
PRIMARY KEY (idRel)); 

## Creacion de FK profesores -> rel_ProfAlmn;
alter table rel_ProfAlmn
	add foreign key(idProf)
	references profesores(numero) ON DELETE RESTRICT ON UPDATE RESTRICT;
	#DESCRIBE rel_ProfAlmn;

## Creacion de FK alumnos -> rel_ProfAlmn;
alter table rel_ProfAlmn
	add foreign key(idAlmn)
	REFERENCES alumnos(matricula) ON DELETE RESTRICT ON UPDATE RESTRICT;
	#describe rel_ProfAlmn;

## para resultados   0-no existe | 1-existe_ej_exito | 2-existe pero no se puede ejecutar

#drop procedure if exists sp_consultaListaProfesores;----------------------------------PROFESORES
delimiter $$
create procedure sp_consultaListaProfesores()
begin
	declare idCont 		int default 0;
    declare existe		int default 0;
    
    set idCont =(select count(*)FROM profesores);
    
	if idCont = 0 then
		SELECT existe;
	else
		SELECT * FROM profesores;
    end if;
end$$
delimiter ;

#drop procedure if exists sp_consultaProf;
delimiter $$
create procedure sp_consultaProf(IN num INT UNSIGNED )
begin
	declare idCont 		int default 0;
    declare existe		int default 0;
    
    set idCont =(select count(*)FROM profesores WHERE numero=num);
    
	if idCont = 0 then
		SELECT existe;
	else
		SELECT * FROM profesores WHERE numero=num;
    end if;
end$$
delimiter ;

#drop procedure if exists sp_registraProfesor;
delimiter $$
create procedure sp_registraProfesor(IN num INT UNSIGNED,IN materno VARCHAR(30),IN paterno VARCHAR(30),IN nomb VARCHAR(50))
begin
	declare idCont 		int default 0;
    declare existe		int default 0;
    
    set idCont =(select count(*)from profesores WHERE numero = num);
    
	if idCont = 0 then
		INSERT INTO profesores (numero,primerAp,segundoAp,nombres) VALUES (num,materno,paterno,nomb);
		SET existe = 0;
		SELECT existe;
	else
		SET existe = 1;
		SELECT existe;
    end if;
end$$
delimiter ;

#drop procedure if exists sp_modificaProfesor;
delimiter $$
create procedure sp_modificaProfesor(IN num INT UNSIGNED,IN materno VARCHAR(30),IN paterno VARCHAR(30),IN nomb VARCHAR(50))
begin
	declare idCont 		int default 0;
    declare existe		int default 0;
    
    set idCont =(select count(*)from profesores WHERE numero = num);
    
	if idCont = 0 then
		SET existe=0;
		SELECT existe;
	else
		UPDATE profesores SET primerAp = materno, segundoAp = paterno, nombres = nomb WHERE numero = num;
		SET existe = 1;
		SELECT existe;
    end if;
end$$
delimiter ;

#drop procedure if exists sp_eliminaProfesor;
delimiter $$
create procedure sp_eliminaProfesor(IN num INT UNSIGNED)
begin
	declare idCont 		int default 0;
    declare existe		int default 0;
    
    set idCont =(select count(*)from profesores WHERE numero = num);
    
	if idCont = 0 then
		SET existe=0;
	else
		SET idCont =(SELECT COUNT(*)FROM rel_ProfAlmn WHERE idProf = num);
		if idCont = 0 then
			DELETE FROM profesores WHERE profesores.numero = num;
			SET existe = 1;
		ELSE 
			SET existe = 2;
		END if;
    end if;
    SELECT existe;
end$$
delimiter ;

#drop procedure if exists sp_consultaListaAlumnos;---------------------------------------------ALUMNOS
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

#drop procedure if exists sp_registraAlumno;
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

#drop procedure if exists sp_modificaAlumno;
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

#drop procedure if exists sp_eliminaAlumno;
delimiter $$
create procedure sp_eliminaAlumno(IN boleta INT UNSIGNED,IN num INT UNSIGNED )
begin
	 declare idCont 		int default 0;
    declare existe		int default 0;
    
    set idCont =(select count(*)from alumnos WHERE matricula = boleta);
    #SET FOREIGN_KEY_CHECKS=0;
	if idCont = 0 then
		SET existe=0;
	else
		SET idCont =(select COUNT(*) from rel_ProfAlmn WHERE idAlmn=boleta);
		if idCont = 1 then
			DELETE FROM rel_profalmn WHERE idProf=num and idAlmn=boleta;
			DELETE FROM alumnos WHERE alumnos.matricula = boleta;
			SET existe=1;
		ELSEIF idCont >1 then
			DELETE FROM rel_profalmn WHERE idProf=num and idAlmn=boleta;
			SET existe=2;
		END if;
    END if;
    #SET FOREIGN_KEY_CHECKS=1;
    SELECT existe;
end$$
delimiter ;
#CALL sp_eliminaAlumno(2014090139,87126038);
#select count(*)from rel_ProfAlmn WHERE idAlmn=2014090139;
#select count(*)from alumnos WHERE matricula = 2014090139;
#SELECT * FROM rel_profalmn;

#drop procedure if exists sp_consultaAlumnosProf;#___________________________REL_PROFALMN
delimiter $$
create procedure sp_consultaAlumnosProf(IN num INT UNSIGNED)
begin
	declare idCont 		int default 0;
    declare existe		int default 0;
    
    set idCont =(select count(*)from rel_ProfAlmn WHERE idProf = num);
    
	if idCont = 0 then
		SET existe=0;
		SELECT existe;
	else
		SELECT alumnos.matricula,alumnos.primerAp,alumnos.segundoAp,alumnos.nombres
			FROM alumnos INNER join rel_ProfAlmn WHERE rel_ProfAlmn.idProf=num and rel_ProfAlmn.idAlmn=alumnos.matricula;
		SET existe = 1;
		SELECT existe;
    end if;
end$$
delimiter ;

#drop procedure if exists sp_inscribirAlumno;
delimiter $$
create procedure sp_inscribirAlumno(IN num INT UNSIGNED,IN boleta INT UNSIGNED,IN materno VARCHAR(30),IN paterno VARCHAR(30),IN nomb VARCHAR(50))
begin
	declare idCont 		int default 0;
    declare existe		int default 0;
    SET idCont =(SELECT COUNT(*)FROM alumnos WHERE matricula=boleta);
    
    if idCont=0 then
    	INSERT INTO alumnos (matricula,primerAp,segundoAp,nombres) VALUES (boleta,materno,paterno,nomb);
		INSERT INTO rel_ProfAlmn(idProf,idAlmn)VALUES (num,boleta);
		SET existe=0;
	else
		set idCont =(select count(*)from rel_ProfAlmn WHERE idProf = num AND idAlmn = boleta);	
		if idCont = 0 then
			INSERT INTO rel_ProfAlmn(idProf,idAlmn)VALUES (num,boleta);
			SET existe=1;
		else
			SET existe=2;
		END if;
    end if;
    SELECT existe;
end$$
delimiter ;

