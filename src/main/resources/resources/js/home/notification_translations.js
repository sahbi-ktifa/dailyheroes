angular.module("HomeApp").run(function (gettextCatalog) {
    gettextCatalog.setStrings("fr", {
        'Congratulations, you are leveling up! You are now level':'Félicitations, votre niveau a augmenté ! Vous êtes maintenant niveau',
        'Congratulations, you have been awarded with:':'Félicitations, vous avez dévérouillé :',
        'has completed a task, please review it:':'a terminé une tâche, merci de confirmer :',
        'has created a task, check if you can do it:':'a créé une nouvelle tâche, peut-être pouvez-vous la réaliser :',
        'has validated a task, well done:':'a validé une tâche, bien joué :',
        'is now level':'est maintenant niveau',
        'New items have been unlocked, check your profile!':'De nouveaux items ont été dévérouillés, aller voir votre profil!',
        'You were added in a new game, join it now:':'Vous avez été ajouté à une nouvelle partie, rejoignez-là dès maintenant :'
    });
});