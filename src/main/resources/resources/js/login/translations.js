angular.module("LoginApp").run(function (gettextCatalog) {
    gettextCatalog.setCurrentLanguage(language);
    gettextCatalog.setStrings("fr", {
        'Add an extra player.':'Ajouter un joueur.',
        'An error occured, please try again later.':'Une erreur s\'est produite, merci de réessayer ultérieurement.',
        'Confirm Password':'Confirmer le mot de passe',
        'Create a game':'Créer une partie',
        'Enter dashboard name':'Saisir le nom de la partie',
        'Enter extra player name':'Saisir le nom du joueur supplémentaire',
        'Enter player 1 name':'Saisir le nom du joueur 1',
        'Enter player 2 name':'Saisir le nom du joueur 2',
        'Join game':'Rejoindre une partie',
        'Log In':'Connexion',
        'Password':'Mot de passe',
        'Password should be at least 6 characters long.':'Le mot de passe doit au minimum faire 6 caractères.',
        'Passwords do not match.':'Les mots de passes ne correspondent pas.',
        'Register':'S\'enregister',
        'Registration complete, you can now log in.':'Enregistrement terminé, vous pouvez vous connecter.',
        'Unknown user or incorrect password.':'Nom d\'utilisateur inconnu ou mot de passe incorrect.',
        'Username':'Nom d\'utilisateur',
        'Username should be at least 4 characters long.':'Le nom d\'utilisateur doit au minimum faire 4 caractères.',
        'WARNING, user already exists and will be added to your game.':'ATTENTION, cet utilisateur existe déjà et sera ajouté à votre partie.'
    });
});