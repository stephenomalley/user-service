(function(){
    angular.module('userService')
        .directive("userList", function(){
            return {
                restrict: 'E',
                templateUrl: 'assets/partials/users.html'
            }
        })
})();