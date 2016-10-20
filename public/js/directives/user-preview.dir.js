(function(){
    angular.module('userService')
        .directive("userPreview", function(){
            return {
                restrict: 'E',
                templateUrl: 'assets/partials/user-preview.html'
            }
        })
})();