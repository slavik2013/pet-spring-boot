/**
 * Created by slavik on 04.04.15.
 */
var controllers = angular.module('controllers', ['angularFileUpload', 'ui.bootstrap','ngTable','ngResource']);

controllers.filter('milisecondsToDateTime', [function() {
    return function(seconds) {
        return new Date(seconds);
    };
}])


controllers.directive('ngThumb', function($window) {
    var helper = {
        support: !!($window.FileReader && $window.CanvasRenderingContext2D),
        isFile: function(item) {
            return angular.isObject(item) && item instanceof $window.File;
        },
        isImage: function(file) {
            var type =  '|' + file.type.slice(file.type.lastIndexOf('/') + 1) + '|';
            return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
        }
    };

    return {
        restrict: 'A',
        template: '<canvas/>',
        link: function(scope, element, attributes) {
            if (!helper.support) return;

            var params = scope.$eval(attributes.ngThumb);

            if (!helper.isFile(params.file)) return;
            if (!helper.isImage(params.file)) return;

            var canvas = element.find('canvas');
            var reader = new FileReader();

            reader.onload = onLoadFile;
            reader.readAsDataURL(params.file);

            function onLoadFile(event) {
                var img = new Image();
                img.onload = onLoadImage;
                img.src = event.target.result;
            }

            function onLoadImage() {
                var width = params.width || this.width / this.height * params.height;
                var height = params.height || this.height / this.width * params.width;
                canvas.attr({ width: width, height: height });
                canvas[0].getContext('2d').drawImage(this, 0, 0, width , height);
            }
        }
    };
});



var categoriesCache = null;

function getCategories($scope, $http){
    var req = {
        method: 'GET',
        url: 'api/category',
        headers: {
            'Content-Type': undefined
        }
    }

    $http(req).success(function(data){
        $scope.categories = data;
        categoriesCache = data;
    }).error(function(){
        //alert('error');
    });
}

function getUser($scope, $http){
    var req = {
        method: 'GET',
        url: 'user',
        headers: {
            'Content-Type': undefined
        }
    }

    $http(req).success(function(data){
        $scope.authenticatedUser = data;
    }).error(function(){
        //alert('error');
    });
}

function getPets($scope, $http){
    var req = {
        method: 'GET',
        url: 'api/pet',
        headers: {
            'Content-Type': undefined
        }
    }

    $http(req).success(function(data){
        $scope.pets = data;
    }).error(function(){
        //alert('error');
    });
}

function getPets($scope, $http, category){
    var req = {
        method: 'GET',
        url: 'api/pet/category/'+category,
        headers: {
            'Content-Type': undefined
        }
    }

    $http(req).success(function(data){
        $scope.pets = data;
    }).error(function(){
        //alert('error');
    });
}

controllers.controller('loginUserController', function ($scope, $http){
    //alert("get user");
    getUser($scope, $http);
});

controllers.controller('mainController', function ($scope, $routeParams, $http, $location) {

    //if(categoriesCache == null)
    getCategories($scope, $http);
    //getUser($scope, $http);
    //$scope.authenticatedUser = $cacheFactory('userCache').get('user');
    //alert(JSON.stringify($scope.authenticatedUser));

});


controllers.controller('petlistController', function ($scope, $routeParams, $http) {

    $scope.pets = {}
    getPets($scope, $http, $routeParams.category);


});

controllers.controller('addadvertController', function ($scope, $http, $location, FileUploader) {

    var uploader = $scope.uploader = new FileUploader({
        url: 'api/saveimage',
        queueLimit: 10,
        autoUpload: true
    });

    // FILTERS

    //uploader.filters.push({
    //    name: 'imageFilter',
    //    fn: function(item /*{File|FileLikeObject}*/, options) {
    //        var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
    //        return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1 && item.file.size <= 100 * 1024 * 1024;
    //    }
    //});

    uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
        console.info('onWhenAddingFileFailed', item, filter, options);
    };
    uploader.onAfterAddingFile = function(fileItem) {
        console.info('onAfterAddingFile', fileItem);
    };
    uploader.onAfterAddingAll = function(addedFileItems) {
        console.info('onAfterAddingAll', addedFileItems);
    };
    uploader.onBeforeUploadItem = function(item) {
        console.info('onBeforeUploadItem', item);
    };
    uploader.onProgressItem = function(fileItem, progress) {
        console.info('onProgressItem', fileItem, progress);
    };
    uploader.onProgressAll = function(progress) {
        console.info('onProgressAll', progress);
    };
    uploader.onSuccessItem = function(fileItem, response, status, headers) {
        console.info('onSuccessItem', fileItem, response, status, headers);
    };
    uploader.onErrorItem = function(fileItem, response, status, headers) {
        console.info('onErrorItem', fileItem, response, status, headers);
    };
    uploader.onCancelItem = function(fileItem, response, status, headers) {
        console.info('onCancelItem', fileItem, response, status, headers);
    };
    uploader.onCompleteItem = function(fileItem, response, status, headers) {
        $scope.images[response.name] = response;
        console.info('onCompleteItem', fileItem, response, status, headers);
        //alert("uploaded response.name = " + response.name + " ; response.id = " + response.id);
    };
    uploader.onCompleteAll = function() {
        console.info('onCompleteAll');
    };

    console.info('uploader', uploader);

    //uploader.filters.push({
    //    name: 'imageFilterQueueLimit',
    //    fn: function(item /*{File|FileLikeObject}*/, options) {
    //        if (this.queue.size == 10) {
    //            alert("max images is 10");
    //            return false;
    //        } else {
    //            return true;
    //        }
    //    }
    //});

    //uploader.filters.push({
    //    name:'imageFilter',
    //    fn: function (item, options) {
    //    return item.file.size <= 10 * 1024 * 1024; // 10 MiB to bytes
    //}});

    $scope.pet = {};

    $scope.images = {};

    /*if(!categoriesCache)
        $scope.categories = categoriesCache;
    else*/
        getCategories($scope, $http);

    $scope.deleteImage = function(item) {

        var imageId = $scope.images[item.file.name].id;
        var req = {
            method: 'GET',
            url: 'api/deleteimage/' + imageId
        }

        $http(req).success(function(data){

        }).error(function(){

        });
        delete $scope.images[item.file.name];
        item.remove();
    };


    $scope.submit = function() {

        $scope.pet.imageEntity = [];

        for (var imageEntityId in $scope.images){
            $scope.pet.imageEntity.push($scope.images[imageEntityId]);
        }
        var req = {
            method: 'POST',
            url: 'api/pet',
            data: $scope.pet,
            headers: {
                'Content-Type': 'application/json'
            }
        };

        $http(req).success(function(data){

        }).error(function(){

        });

        $location.path("/adsuccess");
    };

    $scope.breeds = [];

    $scope.getBreeds = function(){

        $scope.pet.price.currency = 'грн';

        //if(!$scope.breeds) {
            var req = {
                method: 'GET',
                url:"api/breeds/" + $scope.pet.category.name,
                headers: {
                    'Content-Type': undefined
                }
            };
            $http(req).success(function (data) {
/*                var  categoryBreed = {};
                categoryBreed[$scope.pet.category.name] = data;
                $scope.breeds.push(categoryBreed);*/
                $scope.breeds = data;
            });
        //}
    }

});

controllers.controller('dateController', function ($scope){
    $scope.date = new Date();
});


function getPetById($scope, $http, petId){
    var req = {
        method: 'GET',
        url: 'api/pet/' + petId,
        headers: {
            'Content-Type': undefined
        }
    }

    $http(req).success(function(data){
        $scope.petSingle = data;
    }).error(function(){
        //alert('error');
    });
}

controllers.controller('petController', function ($scope, $routeParams, $http){
    //alert("petId = " + $routeParams.petId);
    getPetById($scope, $http, $routeParams.petId);
});


controllers.controller('loginController', function ($scope, $routeParams, $http, $location, $window){

    $scope.user = {};
    //$scope.authenticatedUserCache = $cacheFactory('userCache');

    //alert($scope.authenticatedUserCache.get('user'));

    $scope.submit = function() {

        var req = {
            method: 'POST',
            url: '/login',
            data: $.param($scope.user),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        };

        $http(req).success(function(data){
            //$cookieStore.put('user', JSON.stringify(data));
            //$scope.authenticatedUserCache.put("user", JSON.stringify(data));
            //$location.path("/index");
            $window.location.href = '/';
        }).error(function(data){
            $scope.loginError = "email or password is wrong"
        });

}
});

controllers.controller('registrationController', function ($scope, $routeParams, $http, $location){
    $scope.user = {};

    $scope.submit = function() {

        //alert(JSON.stringify($scope.user));

        $scope.registrationError = null;

        if ($scope.user.email && $scope.user.password && $scope.user.passwordconfirm &&
            $scope.user.password == $scope.user.passwordconfirm) {

            delete $scope.user.passwordconfirm;

            var req = {
                method: 'POST',
                url: '/register',
                data: $scope.user,
                headers: {
                    'Content-Type': 'application/json'
                }
            };

            $http(req).success(function (data) {

            }).error(function () {

            });
        }
        //$location.path("/adsuccess");
    }

});



function getMyads($scope, $http, adState){

    var req = {
        method: 'GET',
        url:'api/text/' + adState,
        headers: {
            'Content-Type': undefined
        }
    };
    $http(req).success(function (data) {

        $scope.rowCollection = data;
        $scope.adverts = data;


    }).error(function(data){

    });
}

function confirmAd($scope, $http, adId){

    var req = {
        method: 'GET',
        url:'confirmAd/' + adId,
        headers: {
            'Content-Type': undefined
        }
    };
    $http(req).success(function (data) {

    }).error(function(data){

    });
}

function deactivateAd($scope, $http, adId){

    var req = {
        method: 'GET',
        url:'deactivateAd/' + adId,
        headers: {
            'Content-Type': undefined
        }
    };
    $http(req).success(function (data) {

    }).error(function(data){

    });
}

function deleteItemFromAdverts(adverts, advertId){
    for (var i = 0, len = adverts.length; i<len; i++) {
        if (adverts[i].id == advertId) {
            adverts.splice(i, 1);
            break;
        }
    };
}

function viewAdvert($location, advertId){
    $location.path("/pet/" + advertId);
}

controllers.controller('accountController', function ($scope, $routeParams, $http, $location, NgTableParams, $resource){


    $scope.adverts = {};

    $scope.rowCollection = {};
    $scope.activeAdvertsDataList = {};

    $scope.viewAdvert = function(advertId){
        viewAdvert($location, advertId);
    };

    $scope.confirmAd = function(advertId){
        confirmAd($scope, $http, advertId);
        deleteItemFromAdverts($scope.adverts, advertId);
    };

    $scope.deactivateAd = function(advertId){
        deactivateAd($scope, $http, advertId);
        deleteItemFromAdverts($scope.adverts, advertId);
    };

    $scope.activateAd = function(advertId){
        confirmAd($scope, $http, advertId);
        deleteItemFromAdverts($scope.adverts, advertId);
    };


    $scope.activeAdverts = function(){
        getMyads($scope, $http, "ACTIVE");
    };

    $scope.waitingAdverts = function(){
        getMyads($scope, $http, "WAITING");
    };

    $scope.nonActiveAdverts = function(){
        getMyads($scope, $http, "NONACTIVE");
    };

    $scope.inboxMessages = function(){

    };

    $scope.outboxMessages = function(){

    };

    $scope.archiveMessages = function(){

    };

    $scope.paymentsAndAccount = function(){

    };

    $scope.accountPreferences = function(){

    };

});