# Calculadora Distribuida


Calculadora Distribu�da utilizando socket em java

Disciplina Sistemas Distribu�dos - FACAPE

O objetivo deste projeto, foi desenvolver um pequeno sistema distribu�do de servidores utilizando sockets.

O projeto foi composto de algumas etapas, que foram avaliadas separadamente e tamb�m em conjunto.

O sistema/programa foi implementado em Java e executado no Linux/ Windows.

O sistema � composto por:
- um servidor concorrente principal que receber� requisi��es via socket e encaminhar� as mesmas, via sockets para servidores escravos (servidorPrincipal).
- servidores "zumbis" que se registrar�o no servidor principal e ser�o encarregados de realizar as opera��es.
- algoritimo para decis�o para escolha do servidor escravo (sugerido: round-robin).
- servidores "zumbis" especializados que receber�o requisi��es espec�ficas, delegadas pelos servidor principal.
- clientes (calculadoras) que far�o requisi��es para o servidor principal.
- As 4 opera��es b�sicas devem ser executadas nos servidores zumbis b�sicos.
- As opera��es de Porcentagem, raiz quadrada e potencia��o devem ser executadas nos servidores zumbis especiais.

# 