# 🛒 Microservice Commandes

> Service de gestion des commandes — Application de livraison de repas à domicile  
> Java · Jakarta EE · JAX-RS · Clean Architecture

---

## 📋 Présentation

Ce microservice fait partie d'une **architecture microservices** pour une application de livraison de repas.

**Son rôle** : gérer la création des commandes clients.

Il permet de :
- Recevoir une demande de commande (liste de menus + quantités)
- Appeler le **microservice Menus** pour récupérer les détails et les prix
- Calculer automatiquement le **prix total** de la commande
- Retourner une commande complète avec toutes les informations

Le service **ne stocke pas les menus**. Il les récupère en temps réel depuis le service Menus, respectant ainsi la séparation des responsabilités entre microservices.

---

## ⚙️ Fonctionnalités

| Fonctionnalité | Description |
|---|---|
| Création de commande | À partir d'une liste d'IDs de menus et de quantités |
| Appel inter-service | Récupération des menus via HTTP (microservice Menus) |
| Calcul du prix total | Somme automatique (prix unitaire × quantité) par ligne |
| Validation | Vérification des données d'entrée (menus, quantités, adresse) |
| Gestion d'erreurs | Réponses structurées (400, 404, 500) |

---

## 🏗️ Architecture

Le projet suit une **Clean Architecture** organisée en couches avec des responsabilités clairement séparées.

### Couches du projet

```
┌─────────────────────────────────────────────┐
│  PRÉSENTATION (adapters/in)                 │
│  CommandeController · CommandeMapper        │
│  → Expose les endpoints REST                │
│  → Convertit DTO ↔ Domain                   │
├─────────────────────────────────────────────┤
│  APPLICATION (usecase)                      │
│  CreerCommandeUseCase · CreerCommandeCommand│
│  MenuClientPort (interface)                 │
│  → Orchestre la logique applicative          │
│  → Définit les ports de sortie              │
├─────────────────────────────────────────────┤
│  DOMAINE (domain)                           │
│  Commande · LigneCommande · Menu            │
│  → Entités métier immuables                 │
│  → Logique business (calcul prix total)     │
├─────────────────────────────────────────────┤
│  INFRASTRUCTURE (adapters/out)              │
│  MenuClientAdapter                          │
│  → Appel HTTP vers l'API Menus              │
│  → Implémente le port de sortie             │
├─────────────────────────────────────────────┤
│  DTO (dto)                                  │
│  CommandeRequest · CommandeResponse         │
│  ErrorResponse                              │
│  → Objets de transfert JSON (entrée/sortie) │
└─────────────────────────────────────────────┘
```

### Principes respectés

- **Séparation des responsabilités** : chaque couche a un rôle unique et précis.
- **Dépendances unidirectionnelles** : les couches externes dépendent des couches internes, jamais l'inverse. Le domaine ne connaît rien de l'extérieur.
- **Inversion de dépendance** : le use case définit un port (interface), l'adapter l'implémente. Cela permet de changer l'infrastructure sans toucher à la logique métier.

### Structure du projet

```
orderservice/
├── config/
│   └── AppConfig.java                  ← Composition Root (assemblage)
├── adapters/
│   ├── in/
│   │   ├── CommandeController.java     ← Endpoint REST
│   │   └── CommandeMapper.java         ← Conversion DTO ↔ Domain
│   └── out/
│       └── MenuClientAdapter.java      ← Appel HTTP API Menus
├── usecase/
│   ├── CreerCommandeUseCase.java       ← Logique applicative
│   ├── CreerCommandeCommand.java       ← Objet de commande (entrée)
│   └── port/
│       └── MenuClientPort.java         ← Interface du port de sortie
├── domain/
│   ├── Commande.java                   ← Entité principale
│   ├── LigneCommande.java             ← Ligne de commande (Value Object)
│   └── Menu.java                       ← Menu récupéré (Value Object)
├── dto/
│   ├── CommandeRequest.java            ← DTO d'entrée
│   ├── CommandeResponse.java           ← DTO de sortie
│   └── ErrorResponse.java             ← DTO d'erreur
└── exception/
    ├── CommandeInvalideException.java  ← Erreur de validation
    └── MenuNotFoundException.java      ← Menu introuvable
```

---

## 🔗 Communication entre microservices

```
┌──────────────┐       HTTP GET        ┌──────────────┐
│   Service    │ ───────────────────▶  │   Service    │
│  Commandes   │  /menus/{id}          │    Menus     │
│  (ce projet) │ ◀─────────────────── │ (json-server)│
└──────────────┘    JSON (Menu)        └──────────────┘
```

- Le service Commandes **appelle** le service Menus pour chaque menu de la commande.
- Les menus sont récupérés **à la volée** via HTTP (pas de copie locale).
- Chaque service gère **ses propres données** : Menus gère les menus, Commandes gère les commandes.

---

## 🔄 Exemple de fonctionnement

**1.** Le client envoie une requête `POST /api/commandes` :

```json
{
  "abonneId": 3,
  "adresseLivraison": "7 avenue du Prado, 13008 Marseille",
  "menuIds": [1, 3],
  "quantites": [2, 1]
}
```

**2.** Le service appelle l'API Menus pour récupérer les détails :
- `GET http://localhost:3004/menus/1` → Menu Provence (27.50€)
- `GET http://localhost:3004/menus/3` → Menu Gourmand (22.00€)

**3.** Le prix total est calculé automatiquement :
- Menu Provence × 2 = 55.00€
- Menu Gourmand × 1 = 22.00€
- **Total = 77.00€**

**4.** La commande complète est retournée :

```json
{
  "id": 1,
  "abonneId": 3,
  "dateCommande": "2026-04-08T14:32:00",
  "adresseLivraison": "7 avenue du Prado, 13008 Marseille",
  "lignes": [
    { "menuId": 1, "menuNom": "Menu Provence", "quantite": 2, "prixUnitaire": 27.50, "prixLigne": 55.00 },
    { "menuId": 3, "menuNom": "Menu Gourmand", "quantite": 1, "prixUnitaire": 22.00, "prixLigne": 22.00 }
  ],
  "prixTotal": 77.00
}
```

---

## 📐 Endpoints

| Méthode | URL | Description |
|---|---|---|
| `POST` | `/api/commandes` | Créer une commande |
| `GET` | `/api/commandes/test` | Tester avec une commande pré-remplie |

---

## 📊 Diagrammes

- **Diagramme de classes UML** : fourni (PlantUML), structuré par couches architecturales
- **Schéma de flux** : représentation du parcours d'une requête à travers les couches

---

## 🛠️ Stack technique

| Technologie | Rôle |
|---|---|
| Java 11 | Langage |
| Jakarta EE | Framework serveur |
| JAX-RS | API REST |
| JSON-B | Sérialisation JSON |
| Maven | Build |
| json-server | Mock de l'API Menus |

---

## 🚀 Lancer le projet

```bash
# 1. Démarrer le mock de l'API Menus
npx json-server menus.json --port 3004

# 2. Compiler le projet
./mvnw compile

# 3. Déployer le WAR sur un serveur Jakarta EE (Payara, WildFly, TomEE...)

# 4. Tester
# Via navigateur : http://localhost:8080/order-service/api/commandes/test
```
