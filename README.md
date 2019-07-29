# HumineKingdomV5

## les commandes
utilisez gradle pour compiler et profiter au mieux de la structure du projet.
pour l'utiliser utilisez le script ```gradlew``` *(qui correspond a votre machine)*.
*(il est important de l'executer avant de commencer a coder !)*

## les tasks
Les tasks permette de faire des actions sur le projet comme par exemple le compiler.
Pour les utiliser il faut lancer le script ````gradlew``` *qui correspond a votre machine*,
et passer en parametre le nom de la tache a executer.

Liste des tasks gradle pour le projet:
* serve: ```Créer un serveur minecraft et le lance avec le projet```
* copyAssets: ```Place les assets dans le serveur```
* servePlugin: ```Compile le projet et le place dans le serveur```
* buildPlugin: ```Compile proprement le projet a incrémente la version de celui-ci```
* fullCleanProject: ```Supprime tout les dossier inutile au code source```
## les dépendance
Si le plugin utilise une API il sufit de glisser le jar dans le dossier ````libs/```.
**Spigot est téléchargé automatiquement avec la version du projet PAS BESOIN DE LE FAIRE**.
