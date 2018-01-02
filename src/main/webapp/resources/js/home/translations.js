angular.module("HomeApp").run(function (gettextCatalog) {
    gettextCatalog.setCurrentLanguage(navigator.language || navigator.userLanguage);
    gettextCatalog.setStrings("fr", {
        'Add a task':'Ajouter une tâche',
        'Cancel':'Annuler',
        'Category':'Catégorie',
        'Clear':'Vider',
        'Close':'Fermer',
        'Complexity':'Complexité',
        'Create task':'Créer une tâche',
        'Daily':'Tous les jours',
        'Dashboard':'Accueil',
        'Due date':'Date limite',
        'Edit':'Modifier',
        'Edit task':'Éditer une tâche',
        'Gifts and rewards':'Cadeaux et récompenses',
        'I did it':'C\'est fait',
        'Level':'Niveau',
        'Monthly':'Tous les mois',
        'My profile':'Mon profil',
        'Name *':'Nom *',
        'Notifications list':'Liste des notifications',
        'Players list':'Liste des joueurs',
        'Profile':'Profil',
        'Redundancy':'Récurrence',
        'Submit':'Valider',
        'Task created successfully.':'Tâche crée avec succès.',
        'Task is done':'Tâche réalisée',
        'Tasks list':'Liste des tâches disponibles',
        'Today':'Aujourd\'hui',
        'Weekly':'Toutes les semaines'
    });
});