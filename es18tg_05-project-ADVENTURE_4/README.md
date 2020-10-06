# Adventure Builder [![Build Status](https://travis-ci.com/tecnico-softeng/es18tg_05-project.svg?token=AxNx9wcybfDzx14WWtky&branch=develop)](https://travis-ci.com/tecnico-softeng/es18tg_05-project) [![codecov](https://codecov.io/gh/tecnico-softeng/es18tg_05-project/branch/master/graph/badge.svg?token=ZOuBdcHdd5)](https://codecov.io/gh/tecnico-softeng/es18tg_05-project)

To run tests execute: mvn clean install

To see the coverage reports, go to <module name>/target/site/jacoco/index.html.


|   Number   |          Name           |            	Email	  			|   Name GitHUb  | Grupo |
| ---------- | ----------------------- | ---------------------------------- | -------------- | ----- |
|69354       |Tiago Gamito V. Gonçalves|tiagovg15@gmail.com 				|TGarrocha	 	 |   5   |
|84717       |Francisco Rola           |francisco.rola@tecnico.ulisboa.pt 	|Francisco-Rola  |   5   |
|84725       |Henrique Almeida         |henrialmeida@netcabo.pt	 			|Henrique97      |   5   |
|84732       |João Barbosa             |joaoftnbarbosa@gmail.com 			|joaobarbosa97   |   5   |
|84733       |João Maria Marques Tiago |joaomtiago96@gmail.com   			|joaotiago96     |   5   |
|84739       |Luis Guilherme Todo-Bom  |guitbom@gmail.com        			|LuisTodoBom     |   5   |
|84773       |Tomás Oliveira           |tomas.olliv@gmail.com    			|tomas-olliv     |   5   |

The initial group was split into two subgroups, and each subgroup was assigned a task, according to the following table.

| Subgroup number |					  Members	   				  | 											   Task     							    			|
| --------------- | --------------------------------------------- | --------------------------------------------------------------------------------------------------- |
| 		 1		  | 		  Henrique, Luís and Tomás 			  | Car module 					|
|		 2		  | Francisco, João Barbosa, João Tiago and Tiago | Tax module 					|

### Infrastructure

This project includes the persistent layer, as offered by the FénixFramework.
This part of the project requires to create databases in mysql as defined in `resources/fenix-framework.properties` of each module.

See the lab about the FénixFramework for further details.

#### Docker (Alternative to installing Mysql in your machine)

To use a containerized version of mysql, follow these stesp:

```
docker-compose -f local.dev.yml up -d
docker exec -it mysql sh
```

Once logged into the container, enter the mysql interactive console

```
mysql --password
```

And create the 7 databases for the project as specified in
the `resources/fenix-framework.properties`.

##### Badges

Codecov: 5a40f9e3-a949-491a-9acc-83eecb140d27
