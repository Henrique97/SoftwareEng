# Relatório acerca do impacto da política ótimista da FenixFramework na invocação de serviços remotos
#### Grupo 5

## Análise

A fénix framework utiliza uma política otimista a nível de controlo de concorrência, isto implica que o acesso a dados partilhados é feito sem adquirir acesso exclusivo aos dados, isto é, não são utilizadas estruturas como trincos para garantir que não existem problemas inerentes ao acesso concurrente a este tipo de dados. Não obstante são fornecidos os mecanismos de commit e rollback de transações, bem como invocação atómica de métodos por forma a que quem utiliza esta framework consiga manipular o acesso a dados partilhados, sendo assim implementado um attribute based access control.

No que diz respeito ao setup dos testes de carga, os resultados obtidos estão de acordo com o esperado, durante as inicializações dos módulos não ocorreu qualquer erro visto que apenas existe uma thread, evitando-se assim problemas de concorrência. Oo processamentos das adventures e as leituras de dados são feitos por 100 threads em simultâneo, originando acessos concorrentes a dados partilhados que, por sua vez, geram erros na aplicação. 


### Load-Test 100% Writes

Analisando o relatório gerado pelo Jmeter o APDEX (Application Performance Index) geral foi de 0.789 e, em particular, o do Process Adventure foi de 0.230. O tempo de resposta médio obtido foi de 10059.99 ms o que leva a concluir que a aplicação não consegue responder dentro dos limites aceitáveis, segundo os padrões estabelecidos na literatura, que estabelecem 10 segundos como o tempo que um utilizador espera por uma resposta por parte da aplicação sem perder o foco ou decidir realizar outra tarefa. 

O número de threads diminui ao longo do tempo enquanto o response time aumenta, inundando assim o serviço, o gráfico que representa o byte throughput sobre o tempo é concordante com esta tese, tendo um valor máximo no instante inicial e estritamente decrescente ao longo do tempo, visto que a aplicação deixa de ter capacidade de resposta dado o número de pedidos realizados via Jmeter.

A taxa de erro obtido no teste de 100 writes (4.22%) prende-se com o facto de as threads concorrentes tentarem aceder aos mesmos dados originando erros que levam a que a transação falhe, os writes, ao contrário dos reads alteram informação, sendo necessário fazer um rollback das alterações efetuadas até ao momento do erro, o que origina os picos a nível de tempo de resposta, chegando a um limite máximo de 40 segundos.

### Load-Test 30% Writes & 70% Reads

Analisando o relatório gerado pelo Jmeter o APDEX (Application Performance Index) geral foi de 0.805 e, em particular, o do Process Adventure foi de 0.545, com a média dos reads a 0.8. O tempo de resposta médio obtido foi de 884.63 ms.

A taxa de erro obtido no teste de 30 writes (15.3%) prende-se com o facto de as threads concorrentes tentarem aceder aos mesmos dados originando erros que levam a que a transação falhe, os writes, ao contrário dos reads alteram informação, sendo necessário fazer um rollback das alterações efetuadas até ao momento do erro. A simultaneadade de accessos de escrito e leitura não permite a base de dados otimizar apenas um tipo de operação, levando a um número de erros muito superior aos testes que fazem exclusivamente uma operação.

### Load-Test 100% Reads

Analisando o relatório gerado pelo Jmeter o APDEX (Application Performance Index) geral foi de 0.618. O tempo de resposta médio obtido foi de 1083.69 ms.
A taxa de erro obtido no teste de 100 reads foi de 0%. Este valor prende-se com o facto de os reads não alterarem a informação já existente na base de dados, pelo que, mesmo com threads concorrentes a executar operações, desde que o sistema seja minimamente capaz de aguentar a carga, não ocorrerão erros nos acessos e todos os pedidos serão satisfeitos.
