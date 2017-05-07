# Calculadora Distribuida


Calculadora Distribuída utilizando socket em java

Disciplina Sistemas Distribuídos - FACAPE

O objetivo deste projeto, foi desenvolver um pequeno sistema distribuído de servidores utilizando sockets.

O projeto foi composto de algumas etapas, que foram avaliadas separadamente e também em conjunto.

O sistema/programa foi implementado em Java e executado no Linux/ Windows.

O sistema é composto por:
- um servidor concorrente principal que receberá requisições via socket e encaminhará as mesmas, via sockets para servidores escravos (servidorPrincipal).
- servidores "zumbis" que se registrarão no servidor principal e serão encarregados de realizar as operações.
- algoritimo para decisão para escolha do servidor escravo (sugerido: round-robin).
- servidores "zumbis" especializados que receberão requisições específicas, delegadas pelos servidor principal.
- clientes (calculadoras) que farão requisições para o servidor principal.
- As 4 operações básicas devem ser executadas nos servidores zumbis básicos.
- As operações de Porcentagem, raiz quadrada e potenciação devem ser executadas nos servidores zumbis especiais.

# 