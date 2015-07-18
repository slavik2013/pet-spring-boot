/**
 * Created by slavik on 04.04.15.
 */
var app = angular.module('app', [
    'ngRoute',
    'controllers',
    'pascalprecht.translate'
]);

//var translations_en = {
//    MY_PROFILE: 'My profile'
//};
//
//var translations_ru = {
//    MY_PROFILE: 'Мой профиль'
//};
//
//app.config(['$translateProvider', function ($translateProvider) {
//    // add translation table
//    $translateProvider
//        .translations('en', translations_en)
//        .translations('ru', translations_ru)
//        .preferredLanguage('ru');
//}]);

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
            when('/petlist/:category', {
                templateUrl: 'partials/petlist.html',
                controller: 'petlistController'
            }).
            when('/petlist/:category/page/:page', {
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
            when('/myaccount', {
                templateUrl: 'partials/myaccount.html',
                controller: 'accountController'
            }).
            otherwise({
                redirectTo: '/index'
            });
    }]);



