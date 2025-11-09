# Proposta de Projeto: StudyLink

## Descrição
StudyLink é uma aplicação que permite a alunos guardar e partilhar (ou não) as suas anotações com outros colegas.  
A arquitetura segue o padrão MVVM (Model–View–ViewModel), separando a lógica de apresentação (Views) da lógica de dados e de negócio (ViewModels).

---

## Objetivos (Requisitos)

### 1. Integração com o Firebase
- Conexão da aplicação ao Firebase para autenticação e armazenamento de dados.

### 2. Autenticação
- Login de utilizador
- Registo de novo utilizador

### 3. Perfil de Utilizador
- Página de perfil onde o utilizador pode:
  - 3.1: Criar (caso seja a primeira vez) ou editar o nome e o username (campos obrigatórios)
  - 3.2: Ver informações como:
    - Número e lista de itens criados
    - Data de criação da conta

### 4. Gestão de Itens (Notas)
Disponível apenas após criação do perfil.

Cada item contém:
- Título
- Conteúdo
- Opcionalmente: username do criador, data de criação, última edição e tags

Campos obrigatórios ao criar um item:
- Título
- Conteúdo  
  (ou seja, dois blocos de texto)

Funcionalidades:
- Criar item
- Atualizar item
- Remover item  
