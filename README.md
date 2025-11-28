# SimpleCashSI – Document de Conception
**Projet Java / Spring Boot 3.5.8 – Gestion bancaire (Clients, Comptes, Conseillers, Audit)**  
**Auteur : Aziaka Christian**

---

## 1. Architecture du système

Le projet suit une architecture en couches classique :

- **Couche présentation (REST / Controllers)**
  - `ClientController`
  - `AccountController`
  - `AdvisorController`
  - `AuditController`

- **Couche métier (Services)**
  - Interfaces : `ClientService`, `AdvisorService`, `AccountService`, `AuditService`
  - Implémentations : `ClientServiceImpl`, `AdvisorServiceImpl`, `AccountServiceImpl`, `AuditServiceImpl`

- **Couche données (Repositories)**
  - `ClientRepository`
  - `AdvisorRepository`
  - `AccountRepository`

- **Couche persistante (Entities JPA)**
  - `Client`
  - `Advisor`
  - `Account`
  - `AccountType`

- **Couche DTO + Mappers**
  - `ClientDTO`, `AdvisorDTO`, `AccountDTO`
  - `ClientMapper`, `AdvisorMapper`, `AccountMapper`

- **Technologies**
  - Spring Boot 3.5.8
  - Spring Web
  - Spring Data JPA
  - H2 Database (en mémoire)
  - Lombok
  - Swagger (OpenAPI)

---

## 2. Diagramme UML (classes métier)

Le diagramme PlantUML se trouve dans `diagramme.puml`.

Il présente :
- Les entités
- Les relations OneToMany / ManyToOne
- Le type de compte (enum)
- Les liens entre clients, comptes et conseillers

---

## 3. User Stories

### US1 – Création d’un client
En tant que **conseiller**, je veux créer un client pour pouvoir lui ouvrir des comptes.

### US2 – Consultation des clients
En tant que **conseiller**, je veux consulter les clients et leurs informations.

### US3 – Attribution d’un conseiller
En tant que **conseiller**, je veux être attribué à un client (max 10 clients par conseiller).

### US4 – Création d’un compte
En tant que **conseiller**, je veux créer des comptes (courant/épargne) pour mes clients.

### US5 – Crédit / Débit
En tant que **conseiller**, je veux créditer ou débiter un compte selon ses règles :
- Courant : découvert possible dans la limite autorisée.
- Épargne : solde jamais négatif.

### US6 – Virement standard
Virement entre deux comptes si le solde permet l’opération.

### US7 – Virement par conseiller (advisor transfer)
Un conseiller peut transférer entre **deux comptes appartenant à ses clients** uniquement.

### US8 – Modification d’un client par son conseiller
Un conseiller peut modifier **uniquement ses propres clients**.

### US9 – Suppression d’un client
Possible seulement si **tous ses comptes ont un solde = 0**.

### US10 – Audit global
L’administrateur peut consulter :
- le solde global,
- la liste des comptes positifs ou négatifs.

---

## 4. Bilan du projet

### Fonctionnalités implémentées
- CRUD Client + DTO
- CRUD Advisor + DTO
- Création de compte (courant/épargne)
- Crédit / Débit / Virement
- Transfert entre comptes du même conseiller
- Audit : total, positifs, négatifs
- Suppression sécurisée d’un client
- MapStruct-like mappers simples (faits main)
- Swagger UI
- H2-console

---

### Difficultés rencontrées
- Mauvaise configuration du pom → H2-console inaccessible  
  **Solution :** nettoyer et corriger le pom.xml.
- Boucles infinies JSON entre entités  
  **Solution :** utilisation de DTO.
- Problème lors de l’update client (doublon)  
  **Solution :** différencier création / mise à jour dans `save()`.
- Gestion du découvert / règles métier complexes  
  **Solution :** implémentation simple et progressive dans les services.

---

### Reste à faire / améliorations
- Ajout de validation (`@NotNull`, `@Size`) dans les DTO.
- Sécurisation via Spring Security.
- Tests unitaires (JUnit & Mockito).
- Logs d’audit avancés sur les opérations bancaires.
- Interface front-end simple.

---

## 5. Annexes

### Swagger UI
Disponible après lancement du projet :  
👉 http://localhost:8080/swagger-ui/index.html

### H2 Console
👉 http://localhost:8080/h2-console  
JDBC : `jdbc:h2:mem:testdb`

---

**Fin du document**

