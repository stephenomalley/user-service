(function(){
    angular.module("userService")
        .controller("UserCtrl", ['$http', '$scope', function($http, $scope){
            this.user = {};

            this.addUser = function(){
                $http.post("users/", this.user).success(function(data){

                });
                this.user = {};
            }
        }]);
})();