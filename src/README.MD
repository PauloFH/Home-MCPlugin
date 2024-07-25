
# Home Plugin

## Introdução

O `Home Plugin` é um plugin para criar, listar, deletar e teleportar para as localizações definidas (homes).
pode ser configurado pela administração do servidor ajustando cooldown, partículas e número máximo de homes para os jogadores.

## Instalação

### automatica:
1. acesse o reposítório com a automação [docker](https://github.com/PauloFH/Teste-de-Recrutamento-Developer-Java-Junior)
2. siga as instruções do repositório
3. 
### manual:
1. Baixe o arquivo `.jar` do plugin localizado em: `build/libs/Home-1.0-SNAPSHOT.jar`
2. Coloque o arquivo  na pasta `plugins` do seu servidor Minecraft.
3. Reinicie o servidor.

## Configuração

Após a primeira inicialização, um arquivo de configuração (`config.yml`) será gerado na pasta do plugin. Este arquivo permite ajustar várias opções:

```yaml
cooldown: 10
teleport-particles: true
particles-count: 100
max-homes: 5
```

- `cooldown`: O tempo (em segundos) que um jogador deve esperar entre usos do comando `/home`.
- `teleport-particles`: Define se partículas devem ser exibidas durante o teleporte.
- `particles-count`: O número de partículas exibidas durante o teleporte.
- `max-homes`: O número máximo de homes que um jogador pode definir.

## Comandos

### `/home <nome_da_home>`

Teleporta o jogador para a home especificada.

- **Uso**: `/home <nome_da_home>`
- **Permissão**: Nenhuma

### `/sethome <nome_da_home>`

Define a localização atual do jogador como uma home com o nome especificado.

- **Uso**: `/sethome <nome_da_home>`
- **Permissão**: Nenhuma

### `/homes`

Lista todas as homes do jogador.

- **Uso**: `/homes`
- **Permissão**: Nenhuma

### `/delhome <nome_da_home>`

Deleta a home especificada.

- **Uso**: `/delhome <nome_da_home>`
- **Permissão**: Nenhuma

### `/homeconfig <configuração> <valor>`

Permite aos administradores alterar as configurações do plugin em tempo real.

- **Uso**: `/homeconfig <cooldown|particles|particles-count|max-homes> <valor>`
- **Permissão**: `operador do servidor`



## Exemplos de Uso

### Definindo e Teleportando para uma Home

1. Defina uma home:
   ```
   /sethome minha_casa
   ```
2. Teleporte-se para a home:
   ```
   /home minha_casa
   ```

### Listando Homes

Para listar todas as suas homes, use o comando:
```
/homes
```

### Deletando uma Home

Para deletar uma home específica, use o comando:
```
/delhome minha_casa
```

### Alterando Configurações

Para alterar o cooldown para 15 segundos, um administrador pode usar:
```
/homeconfig cooldown 15
```

Para desativar as partículas de teleporte, um administrador pode usar:
```
/homeconfig particles false
```

## Dependências
Esse plugin usa mysql como banco de dados, então é necessário ter um servidor mysql rodando na mesma máquina que o servidor minecraft.
ou configurado para acessar um servidor mysql remoto.


## Contato

Para dúvidas, entre em contato:
- **Nome**: Paulo Holanda
- **Email**: [paulorfholanda@gmail.com](mailto:paulorfholanda@gmail.com)
- **GitHub**: [PauloFH](https://github.com/PauloFH)