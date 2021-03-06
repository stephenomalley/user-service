(function(){
    var app = angular.module('userService', [
        'ngRoute',
    ]);

    app.config(['$routeProvider', function ($routeProvider) {
      $routeProvider
        // Home
        .when("/", {templateUrl: "assets/partials/home.html", controller: "PageCtrl"})
        .when("/errordocs/:errorCd", {templateUrl: "assets/partials/errors.html", controller: "ErrorCtrl"})
       }]);
})();
