angular.module("HomeApp").run(function (gettextCatalog) {
    gettextCatalog.setStrings("fr", {
        'Big hug':'Gros câlin',
        'Tender kiss':'Gros et tendre bisous'
    });
});