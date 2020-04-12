var app = angular.module('userregistrationsystem',['ngRoute','ngResource']);

app.config(function($routeProvider){
    $routeProvider.when('/list-all-users', {
        templateUrl : '/template/listuser.html',
        controller : 'listUserController'
    }).when('/register-new-user', {
           templateUrl : '/template/userregistration.html',
           controller : 'registerUserController'
    }).when('/update-user/:id',{
            templateUrl : '/template/userupdation.html',
            controller : 'usersDetailsController'
    }).when('/search-a-user',{
            templateUrl : 'template/searchuser.html',
            controller : 'searchByNameController'
    }).otherwise({
        redirectTo : '/home',
        templateUrl : '/template/home.html',
    });
});