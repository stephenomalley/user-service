angular.module('userService')
    .controller('ErrorCtrl', function ($scope, $routeParams){
         var data = this;
         setErrors();
        function setErrors(){
            var errorData = [
                    {
                        name: "User not found",
                        code:100,
                        description:"This error message is returned when the requested user can't be found in database"
                    },
                    {
                        name: "No data received",
                        code:101,
                        description:"No data was sent to API to create a user. Please try and submit your request again with valid application/json."
                    },
                    {
                        name: "Invalid data",
                        code:102,
                        description:"When trying to add a user you have submitted invalid data. Remember usernames are required and must be unique"
                    },
             ];
            console.log(errorData.find(findError));
            data.error = errorData.find(findError);
        };

        function findError(error){
            return error.code == $routeParams.errorCd;
        };
    }


    );