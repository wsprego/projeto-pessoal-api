### Como usar 
Baixe o projeto <br>
Crie um banco de dados `MySQL` <br>
Mude o que for preciso no `application.properties`, normalmente e o nome do banco de dados, nome de usuario e senha <br>
Em uma IDE rode o projeto <br>
Usando o Insomnia ou outro de preferencia, faça as requisições <br>

### Para os teste http

Para Listar Todos episodeos `Get` - `localhost:8080/episodes`, listarar todos os episodeos <br>
Para Listar episodeos por Id `Get` - `localhost:8080/episodes/1`, no lugar do 1 coloqie o Id desejado <br>
Para Salva um novo episodeo `Post` - `localhost:8080/episodes/save`, lembre de colocar as informações no formato Json <br>
Para Editar episodeos por Id `Put` - `localhost:8080/episodes/edit/1`, no lugar do 1 coloqie o Id desejado <br>
Para Editar episodeos por Id `Delete` - `localhost:8080/episodes/delete/1`, no lugar do 1 coloqie o Id desejado

### Exemplo do formato JSON

  { <br>
		"seriesTitle": "Game of Thrones", <br>
		"episodeTitle": "O Dragão voltou", <br>
		"episodeDescription": "o dragaõ volta para sua dona e ajuda ela ganha a gerra...", <br>
		"episodeImage": "https://veja.abril.com.br/wp-content/uploads/2022/08/APOIO-SERIADO-GAME-OF-THRONES-321456.jpg.jpg?crop=1&resize=1212,909" <br>
	} <br>


### Exemplo do application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/nomedobanco?useSSL=false&serverTimezone=UTC <br>
spring.datasource.username=seuusuario <br>
spring.datasource.password=suasenha <br>
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver <br>
spring.jpa.hibernate.ddl-auto=update <br>
spring.jpa.show-sql=true <br>
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect <br>
spring.jpa.open-in-view=false <br>

