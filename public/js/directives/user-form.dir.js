(function(){
    angular.module('userService')
        .directive("userForm", function(){
            return {
                restrict: 'E',
                templateUrl: 'assets/partials/user-form.html'
            }
        })
})();