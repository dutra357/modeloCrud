
## CRUD para entidade Cliente

### Sobre o projeto

O presente CRUD foi desenvolvido no contexto de exercício para o curso Java Spring Professional, da DevSuperior.

Inclui um gerenciamento simples de entidade Cliente, contando com validação (Java bean) pelo DTO de entrada.

O campo de cadastramento de CPF de um Client aceita tanto o formato de apenas números (00000000000), quanto com pontos e traço (000.000.000-00).

O dto de input apresenta uma primeira validação, ou filtro, onde restringe o campo entre 11 a 14 caracteres, conforme os padrões acima discriminados.

Além dele, inserimos um método formatador para a String correspondente ao CPF dentro do Service, onde, utilizando Expressões Regulares, realiza os demais filtros (a exemplo de ter exatamente 11 dígitos, fora pontos e traço).

Referido método também salva no banco de dados já no formato padrão de '000.000.000-00' para uso imediato de eventual Front-end.

~~~java
    private String cpfFormatter(String cpf) {
        cpf = cpf.replaceAll("\\D", "");

        if ((cpf.length() != 11)) {
            throw new IllegalArgumentException("The CPF entered must have exactly 11 digits, without dots and dash.");
        }
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }
~~~

Ademais, agregamos os tratamentos de erros solicitados pelo projeto, bem como os códigos de retorno adequados a cada um deles.

O sistema ainda conta com o campo CPF assinalado como sendo chave única junto ao banco de dados, retornando uma exceção devidamente tratada para o caso de cadastro de outro Client com o mesmo CPF (ainda que não seja uma exigência específica do exercício). Permanece o 'id', entretanto, como chave primária.


~~~java
    @ExceptionHandler(JdbcSQLIntegrityConstraintViolationException.class)
    public ResponseEntity<CustomError> integrityConstraintViolation(JdbcSQLIntegrityConstraintViolationException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        CustomError error = new CustomError(Instant.now(), status.value(), "CPF already present in DB. Unique index/primary key violation.", request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }
~~~

Por fim, acrescentamos uma classe de configuração a fim de permitir a adequada serialização dos objetos via DTO no momento da listagem geral de Clients (findAll).

A confirguração visa a evitar a perda de dados, bem como o alerta (WARN) de 'Serializing PageImpl instances as-is is not supported..'. Classe cliente, com igual fim, implementa a interface Serializeble.

~~~java
@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class WebConfig {
}
~~~

Para construção dos Dto's, de seu turno, optamos pelo 'record', ao invés da classe tradicional. Nada obstante, aplicadas as validações solicitadas.

### Tecnologias utilizadas
#### Back end
- Java
- Spring Boot
- JPA / Hibernate
- Maven
- DB H2 (http://localhost:8080/h2-console)
- SQL (seeding: data.sql)


### Artefato
modeloCrud

### Clonar repositório
https://github.com/dutra357/modeloCrud

### Autor

David Alves Dutra

contato: dadutra@hotmail.com


https://github.com/dutra357


