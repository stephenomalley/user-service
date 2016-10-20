(function(){
    angular.module('userService')
        .controller('PageCtrl', ['$http', function ($http) {
            home = this;
            home.users = [];
            $http.get("users/").success(function(data){
                home.users = data;
            });
        }]);
})();
