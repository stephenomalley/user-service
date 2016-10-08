angular.module("userService")
    .controller("UserCtrl", function(){
        this.user = {};
        this.addUser = function(users){
            users.push(this.user);
            this.user = {};
        }
    });