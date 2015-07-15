/**
 * Created by slavik on 04.04.15.
 */
var app = angular.module('app', [
    'ngRoute',
    'controllers',
    'ngCookies'
]);



app.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/index', {
                templateUrl: 'partials/main.html',
                controller: 'mainController'
            }).
            when('/addadvert', {
                templateUrl: 'partials/addadvert.html',
                controller: 'addadvertController'
            }).
            when('/petlist', {
                templateUrl: 'partials/petlist.html',
                controller: 'petlistController'
            }).
            when('/adsuccess', {
                templateUrl: 'partials/adsuccess.html'
            }).
            when('/pet/:petId', {
                templateUrl: 'partials/pet.html',
                controller: 'petController'
            }).
            when('/login', {
                templateUrl: 'partials/login.html',
                controller: 'loginController'
            }).
            when('/registration', {
                templateUrl: 'partials/registration.html',
                controller: 'registrationController'
            }).
            otherwise({
                redirectTo: '/index'
            });
    }]);



