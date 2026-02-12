# Moteur de recherche littéraire
Projet de moteur de recherche sur une partie de la biblilothèque littéraire du projet *[Gutenberg](https://www.gutenberg.org/)*.
<br>
Il répond à la problématique suivante : *Comment concevoir un moteur de recherche efficace et pertinent pour une grande
bibliothèque numérique ?*

## Spécifications techniques

Le projet a été réalisé avec les technologies suivantes :

- _MariaDB_ via Docker pour la sauvegarde des stems, livres et index inversé
- _Java v25 Springboot_ pour le backend et création de l'API
- _Angular v19_ pour le frontend

Notre API récupère les métadata des livres par [Gutendex](https://gutendex.com/), l'API officielle de Gutenberg.
Gutendex respecte la politique des robots mais rencontre par conséquent des limites de vitesse.

## Utilisation

### Pré-requis

Afin de pouvoir lancer le projet, il est important de disposer des fichiers _.txt_ des livres
Gutenberg ([informations supplémentaires ici](https://www.gutenberg.org/policy/robot_access.html)).

Il est également requis d'avoir les technologies utilisées d'installées (SDK de Java, Node et MariaDB).

### Lancement

Pour lancer le projet, il faut :

- démarrer MariaDB d'après la config d'`application.properties` suivante ou de les modifier directement :

```
spring.datasource.url=jdbc:mariadb://localhost:3306/gutenberg
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
```

- démarrer le backend en lançant la méthode `main` de la classe `BackApplication`
- démarrer le frontend Angular avec la commande `ng serve`.

NB : Il convient de paramétrer la variable d'environnement `gutenberg.stemming.indexing` de `application.properties` à
`true` pour lancer le scraping avec définition des stems etc et à `false` pour utiliser simplement le moteur.
