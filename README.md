# test-segurosunimed
test-segurosunimed


para acessar o swagger http://localhost:8080/swagger-ui/index.html#

apenas o swagger e o end-point http://localhost:8080/customers estão liberado 
os demais tem que add nas chamada a autorização 
Authorization :  dayvison

Para construir a imagem Docker, vá até o diretório onde está o Dockerfile e execute o seguinte comando:

docker build -t segurosunimed:tag .



Para executar o contêiner com a imagem criada:

docker run -p 8080:8080 -d segurosunimed:tag

