/**
 * Created by slavik on 04.04.15.
 */
var app = angular.module('app', [
    'ngRoute',
    'controllers'
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
            when('/advertlist/:category', {
                templateUrl: 'partials/advertlist.html',
                controller: 'advertlistController'
            }).
            when('/advertlist/:category/:subcategory', {
                templateUrl: 'partials/advertlist.html',
                controller: 'advertlistController'
            }).
            when('/advertlist/:category/page/:page', {
                templateUrl: 'partials/advertlist.html',
                controller: 'advertlistController'
            }).
            when('/adsuccess', {
                templateUrl: 'partials/adsuccess.html'
            }).
            when('/advert/:advertId', {
                templateUrl: 'partials/advert.html',
                controller: 'advertController'
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
            when('/addcategory', {
                templateUrl: 'partials/addcategory.html',
                controller: 'addCategoryController'
            }).
            otherwise({
                redirectTo: '/index'
            });
    }]);



