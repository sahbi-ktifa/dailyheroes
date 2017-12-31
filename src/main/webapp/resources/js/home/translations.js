angular.module("HomeApp").run(function (gettextCatalog) {
    gettextCatalog.setCurrentLanguage(navigator.language || navigator.userLanguage);
    gettextCatalog.setStrings("fr", {
        'Add an extra player.':'Ajouter un joueur.',
        'An error occured, please try again later.':'Une erreur s\'est produite, merci de réessayer ultérieurement.',
        'Confirm Password':'Confirmer le mot de passe',
        'Create a game':'Créer une partie',
        'Enter extra player name':'Saisir le nom du joueur supplémentaire',
        'Enter player 1 name':'Saisir le nom du joueur 1',
        'Enter player 2 name':'Saisir le nom du joueur 2',
        'Join game':'Rejoindre une partie',
        'Log In':'Connexion',
        'Password':'Mot de passe',
        'Register':'S\'enregister',
        'Registration complete, you can now log in.':'Enregistrement terminé, vous pouvez vous connecter.',
        'Unknown user or incorrect password.':'Nom d\'utilisateur inconnu ou mot de passe incorrect.',
        'Username':'Nom d\'utilisateur',
        'Username already exists.':'Ce nom d\'utilisateur existe déjà.'
    });
});