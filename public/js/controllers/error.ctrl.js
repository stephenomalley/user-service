(function(){
angular.module('userService')
    .controller('ErrorCtrl', ['$http', '$routeParams', function ($http, $routeParams ){
         var data = this;

         $http.get("assets/json/errors.json").success(function(errors){
            data.error = errors.find(findError);
         });

        function findError(error){
            return error.code == $routeParams.errorCd;
        };
    }]);
})();