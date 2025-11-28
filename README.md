# SimpleCashSI – Document de Conception
**Projet Java / Spring Boot 3.5.8 – Gestion bancaire (Clients, Comptes, Conseillers, Audit)**  
**Auteur : Aziaka Christian**

---

## 1. Architecture du système

Le projet suit une architecture en couches classique :

### 🟦 Couche présentation (REST / Controllers)
- `ClientController`
- `AccountController`
- `AdvisorController`
- `AuditController`

### 🟥 Couche métier (Services)
- Interfaces : `ClientService`, `AdvisorService`, `AccountService`, `AuditService`
- Implémentations :  
  `ClientServiceImpl`, `AdvisorServiceImpl`, `AccountServiceImpl`, `AuditServiceImpl`

### 🟩 Couche données (Repositories)
- `ClientRepository`
- `AdvisorRepository`
- `AccountRepository`

### 🟧 Entités JPA (données persistées)
- `Client`
- `Advisor`
- `Account`
- `AccountType`

### 🟨 DTO & Mappers
- DTO : `ClientDTO`, `AdvisorDTO`, `AccountDTO`
- Mappers : `ClientMapper`, `AdvisorMapper`, `AccountMapper`

### 🟪 Technologies utilisées
- Spring Boot 3.5.8
- Spring Web
- Spring Data JPA
- H2 Database (in memory)
- Lombok
- Swagger / OpenAPI

---

## 2. Diagramme UML

Le diagramme UML complet se trouve dans le fichier `diagramme.puml`.

Il représente :
- Les entités métier
- Les relations OneToMany / ManyToOne
- L'enum `AccountType`
- Les dépendances entre Client, Advisor et Account

---

## 3. User Stories

### US1 – Création d’un client
En tant que **conseiller**, je veux créer un client pour pouvoir lui ouvrir des comptes.

### US2 – Consultation des clients
En tant que **conseiller**, je veux consulter les clients et leurs informations complètes.

### US3 – Attribution d’un conseiller
En tant que **conseiller**, je veux être attribué à un client (max 10 clients / conseiller).

### US4 – Création d’un compte
En tant que **conseiller**, je veux créer des comptes (courant/épargne) pour mes clients.

### US5 – Crédit / Débit
En tant que **conseiller**, je veux créditer ou débiter un compte selon ses règles métier :
- Courant : découvert autorisé
- Épargne : jamais négatif

### US6 – Virement standard
Faire un virement entre deux comptes.

### US7 – Virement par conseiller (advisor transfer)
Un conseiller peut transférer *depuis un compte appartenant à un de ses clients* vers n’importe quel compte.

### US8 – Modification d’un client par son conseiller
Un conseiller peut modifier **uniquement ses propres clients**.

### US9 – Suppression d’un client
Suppression possible seulement si **tous ses comptes ont un solde = 0**.

### US10 – Audit global
Lister :
- le solde total,
- le nombre de comptes positifs,
- le nombre de comptes négatifs.

---

## 4. Bilan du projet

### ✔ Fonctionnalités implémentées

- CRUD Client + DTO
- CRUD Advisor + DTO
- Comptes bancaires (courant / épargne)
- Crédit, débit, virement classique
- Virement spécial conseiller (`advisorTransfer`)
- Audit complet (solde total, comptes positifs / négatifs)
- Suppression client sécurisée
- DTO + Mappers pour éviter d’exposer les entités
- Swagger + H2-console

---

## Difficultés rencontrées

### 🟧 Difficulté 1 — Configuration H2 et Maven
La H2-console ne fonctionnait pas à cause d’une mauvaise configuration du `pom.xml`  
(starter webmvc au lieu de starter web).

➡ **Solution :** correction complète du pom.xml + activation H2.

---

### 🟧 Difficulté 2 — Boucles JSON infinies
Liaison Client → Accounts → Client → Advisor → etc.  
Cela provoquait des boucles d’objets en JSON.

➡ **Solution :** utilisation de DTO + Mappers simples.

---

### 🟧 Difficulté 3 — Gestion création / modification Client
La règle "Client already exists" empêchait l’update d’un client existant.

➡ **Solution :** distinguer création (`id == null`) et mise à jour (`id != null`).

---

### 🟥 Difficulté 4 — Règles métier complexes des comptes
Découvert, interdiction de solde négatif, virement, transferts → complexité.

➡ **Solution :** coder des règles progressives dans `AccountServiceImpl`.

---

### 🟥 Difficulté 5 — Fonction "transfert conseiller" (LA PLUS DIFFICILE)
La fonctionnalité où un conseiller peut effectuer un virement **depuis un compte appartenant à un de ses clients**  
vers n’importe quel autre compte a été la partie la plus compliquée à implémenter.

Les problèmes rencontrés :

- vérifier correctement que le conseiller est bien assigné au compte source,
- gérer le solde, le découvert, les erreurs métier,
- ne pas exiger que le compte destination appartienne au même conseiller,
- gérer les cas d’erreur 400 / 403 / 404,
- gérer les types de compte (CURRENT vs SAVINGS).

’ai vraiment galéré à implémenter cette fonction**, elle m’a pris beaucoup de temps.  
 J’ai réussi à produire une version fonctionnelle et propre, mais elle pourrait être améliorée avec plus de recul et de temps.

---

## 5. Reste à faire / améliorations

- Ajouter Bean Validation (`@NotNull`, `@Size`, `@Email`, etc.)
- Ajouter Spring Security avec rôles conseillers / admin
- Écrire des tests unitaires (JUnit + Mockito)
- Ajouter du logging d’audit (transferts, modifications sensibles)
- Développer un petit front-end ou une interface web
- Ajouter gestion d’historique des opérations

---

## 6. Annexes

### 🚀 Swagger UI
http://localhost:8080/swagger-ui/index.html

### 🗄 H2 Console
http://localhost:8080/h2-console  
**JDBC URL :** `jdbc:h2:mem:testdb`  
User : `sa` | Password : *(vide)*

---

**Fin du document**
