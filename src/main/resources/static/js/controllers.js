/**
 * Created by slavik on 04.04.15.
 */
var controllers = angular.module('controllers', ['angularFileUpload', 'ui.bootstrap','ngTable','ngResource','pascalprecht.translate', 'ngCookies']);

controllers.filter('milisecondsToDateTime', [function() {
    return function(seconds) {
        return new Date(seconds);
    };
}]);

var translations_en = {
    LANGUAGE: 'Language',
    MY_PROFILE: 'My profile',
    ADD_ADVERT: 'Add advert',
    SEARCH: 'Search',
    FAST_EASY_FREE : 'Fast, Easy and Free',
    FREE_AND_WITHOUT_REGISTRATION :'Free and without registration',
    UP_TO_8_PHOTOS_IN_ADVERT: 'Up to 8 photos in advert',
    IS_ACTIVE_UP_TO_30_DAYS:'Is active up to 30 days',
    ADDED: 'Added',
    IN: 'in',
    NUMBER : 'number',
    VIEWS:'Views',
    WRITE_TO_AUTHOR:'Write to author',
    SHOW_ON_MAP: 'Show on the map',
    TO_FAVORITES: 'To favorites',
    CHANGE: 'Change',
    COMPLAINT: 'Complaint'
};

var translations_ru = {
    LANGUAGE: 'Язык',
    MY_PROFILE: 'Мой профиль',
    ADD_ADVERT: 'Подать объявление',
    SEARCH: 'Поиск',
    FAST_EASY_FREE : 'Быстро, Просто и Бесплатно',
    FREE_AND_WITHOUT_REGISTRATION :'Бесплатно и без регистрации',
    UP_TO_8_PHOTOS_IN_ADVERT: 'До 8 фото в объявлении',
    IS_ACTIVE_UP_TO_30_DAYS:'Активно до 30 дней',
    ADDED: 'Добавлено',
    IN: 'в',
    NUMBER : 'номер',
    VIEWS:'Просмотров',
    WRITE_TO_AUTHOR:'Написать автору',
    SHOW_ON_MAP: 'Показать на карте',
    TO_FAVORITES: 'В избранные',
    CHANGE: 'Изменить',
    COMPLAINT: 'Жалоба'
};

var translations_ua = {
    LANGUAGE: 'Мова',
    MY_PROFILE: 'Мій профіль',
    ADD_ADVERT: 'Додати оголошення',
    SEARCH: 'Пошук',
    FAST_EASY_FREE : 'Швидко, Просто і Безкоштовно',
    FREE_AND_WITHOUT_REGISTRATION :'Безкоштовно і без реєстрації',
    UP_TO_8_PHOTOS_IN_ADVERT: 'До 8 фотографій в оголошенні',
    IS_ACTIVE_UP_TO_30_DAYS:'Активне до 30 днів',
    ADDED: 'Опубліковано',
    IN: 'в',
    NUMBER : 'номер',
    VIEWS:'Переглядів',
    WRITE_TO_AUTHOR:'Написати автору',
    SHOW_ON_MAP: 'Показати на карті',
    TO_FAVORITES: 'В обрані',
    CHANGE: 'Змінити',
    COMPLAINT: 'Скарга'
};

controllers.config(['$translateProvider', function ($translateProvider) {

    $translateProvider
        .translations('en', translations_en)
        .translations('ru', translations_ru)
        .translations('ua', translations_ua);

    $translateProvider.preferredLanguage('ru');

    var $cookies;
    angular.injector(['ngCookies']).invoke(function(_$cookies_) {
        $cookies = _$cookies_;
    });

    if ($cookies.get('language')) {
        $translateProvider.preferredLanguage($cookies.get('language'));
    }

}]);

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

function getCategoriesList($scope, $http){
    var req = {
        method: 'GET',
        url: 'api/category',
        headers: {
            'Content-Type': undefined
        },
        cache: true
    };

    $http(req).success(function(data){
        $scope.categoriesList = data;
    }).error(function(){

    });
}



function getTopCategoriesList($scope, $http){
    var req = {
        method: 'GET',
        url: 'api/topcategory',
        headers: {
            'Content-Type': undefined
        },
        cache: true
    };

    $http(req).success(function(data){
        $scope.categoriesList = data;
    }).error(function(){

    });
}

function getUser($scope, $http){
    var req = {
        method: 'GET',
        url: 'user',
        headers: {
            'Content-Type': undefined
        }
    };

    $http(req).success(function(data){
        $scope.authenticatedUser = data;
    }).error(function(){
        //alert('error');
    });
}

//function getPets($scope, $http){
//    var req = {
//        method: 'GET',
//        url: 'api/advert',
//        headers: {
//            'Content-Type': undefined
//        }
//    }
//
//    $http(req).success(function(data){
//        $scope.adverts = data;
//    }).error(function(){
//        //alert('error');
//    });
//}

function getPets($scope, $http, category){
    var req = {
        method: 'GET',
        url: 'api/advert/category/'+category,
        headers: {
            'Content-Type': undefined
        }
    };

    $http(req).success(function(data){
        $scope.adverts = data;
    }).error(function(){
        //alert('error');
    });
}

function getAdsByCategoryByPage($scope, $http, category, page, itemsPerPage){
    var req = {
        method: 'GET',
        url: 'api/advert/category/'+category+'/page/' + page + '/itemsperpage/' + itemsPerPage,
        headers: {
            'Content-Type': undefined
        }
    };

    $http(req).success(function(data){
        $scope.adverts = data;
    }).error(function(){
    });
}

function getAdsCountByCategoryByPage($scope, $http, category){
    var requestCount = {
        method: 'GET',
        url: 'api/advert/category/'+category+'/count',
        headers: {
            'Content-Type': undefined
        },
        cache: true
    };

    $http(requestCount).success(function(data){
        $scope.totalItems = data;

        $scope.maxSize = data / $scope.itemsPerPage;

        if ((data % $scope.itemsPerPage) != 0) {
            $scope.maxSize++;
        }

        $scope.numPages = $scope.maxSize - ($scope.maxSize % 1);

        if($scope.maxSize > 10)
            $scope.maxSize = 10;

    }).error(function(){

    });
}


function userLogout($scope, $http, $window){
    var req = {
        method: 'GET',
        url: '/logout',
        headers: {
            'Content-Type': undefined
        }
    };

    $http(req).success(function(data){

    }).error(function(){

    });
    $scope.authenticatedUser = undefined;
    $window.location.href = '/';
}

controllers.controller('loginUserController', function ($scope, $http, $translate, $window, $cookies,$route){
    //alert("get user");
    getUser($scope, $http);

    $scope.status = {
        isopen: false
    };

    $scope.toggleDropdown = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.status.isopen = !$scope.status.isopen;
    };

    $scope.changeLanguage = function (key) {
        $translate.use(key);
        $cookies.put('language', key.toLowerCase());
        $route.reload();
    };

    $scope.userLogout = function(){
        userLogout($scope, $http, $window);
    }
});

function getCategoryTitle(category, $cookies){
    var currentLanguage = 'ru';
    if($cookies.get('language'))
        currentLanguage = $cookies.get('language');

    var titles = category.titles;

    var name = {};
    for(var i = 0; i < titles.length; i++){
        var title = titles[i];
        if (title.language == currentLanguage) {
            name = title.title;
            break;
        }
    }
    return name;
}


controllers.controller('mainController', function ($scope, $routeParams, $http, $location, $cookies) {

    getTopCategoriesList($scope, $http);
    $scope.currentCategory = {};
    $scope.changeCategory = function(category){
        if ($scope.currentCategory == category)
            $scope.currentCategory = undefined;
        else
            $scope.currentCategory = category;
    };

    $scope.getCategoryName = function(category){
        return getCategoryTitle(category, $cookies);
    };
});


controllers.controller('advertlistController', function ($scope, $routeParams, $http, $location, $cookies) {

    $scope.adverts = {};
    //getPets($scope, $http, $routeParams.category);

    $scope.itemsPerPageSelectList = [5, 10, 20, 40];

    $scope.totalItems = 10;
    $scope.currentPage = 1;
    if($routeParams.page)
        $scope.currentPage = $routeParams.page;
    //$scope.maxSize = 2;


    if($cookies.get('itemsPerPage'))
        $scope.itemsPerPage = $cookies.get('itemsPerPage');
    else
        $scope.itemsPerPage = 20;

    $cookies.put('itemsPerPage', $scope.itemsPerPage);

    $scope.requestCategory = $routeParams.category;
    if ($routeParams.subcategory)
        $scope.requestCategory = $routeParams.category  + "/" + $routeParams.subcategory;

    getAdsCountByCategoryByPage($scope, $http, $scope.requestCategory);


    $scope.itemsPerPageListener = function(){
        $cookies.put('itemsPerPage', $scope.itemsPerPage);
        $location.path("/advertlist/" + $scope.requestCategory);
    };

    getAdsByCategoryByPage($scope, $http, $scope.requestCategory, $scope.currentPage, $scope.itemsPerPage);

    $scope.setPage = function (pageNo) {
        $scope.currentPage = pageNo;
    };

    $scope.pageChanged = function() {
        $location.path("/advertlist/" + $scope.requestCategory + "/page/" + $scope.currentPage);
        //getAdsByCategoryByPage($scope, $http, $routeParams.category, $scope.currentPage, $scope.itemsPerPage)
    };
});


controllers.controller('ModalInstanceCtrl', function ($scope, $modalInstance, categoriesList, $cookies) {

    $scope.categoriesList = categoriesList;
    $scope.selected = {
        category: $scope.categoriesList[0]
    };

    $scope.ok = function () {
        $modalInstance.close($scope.selected.category);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };

    $scope.getCategoryName = function(category){
        return getCategoryTitle(category, $cookies);
    };

});


controllers.controller('addadvertController', function ($scope, $http, $location, $modal, FileUploader, $cookies) {

    var uploader = $scope.uploader = new FileUploader({
        url: 'api/saveimage',
        queueLimit: 10,
        autoUpload: true
    });

    // FILTERS

    uploader.filters.push({
        name: 'imageFilter',
        fn: function(item /*{File|FileLikeObject}*/, options) {
            var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
            return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;// && item.file.size <= 100 * 1024 * 1024;
        }
    });

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

    $scope.advert = {};

    $scope.images = {};

    /*if(!categoriesCache)
        $scope.categories = categoriesCache;
    else*/
        getTopCategoriesList($scope, $http);

    $scope.deleteImage = function(item) {

        var imageId = $scope.images[item.file.name].id;
        var req = {
            method: 'GET',
            url: 'api/deleteimage/' + imageId
        };

        $http(req).success(function(data){

        }).error(function(){

        });
        delete $scope.images[item.file.name];
        item.remove();
    };


    $scope.submit = function() {

        $scope.advert.imageEntity = [];

        $scope.advert.categories = [];

        //$scope.advert.categories.push($scope.advert.category);
        //
        //delete $scope.advert.category;

        $scope.advert.categories.push($scope.selected);

        for (var imageEntityId in $scope.images){
            $scope.advert.imageEntity.push($scope.images[imageEntityId]);
        }
        var req = {
            method: 'POST',
            url: 'api/advert',
            data: $scope.advert,
            headers: {
                'Content-Type': 'application/json'
            }
        };

        $http(req).success(function(data){
            $location.path("/adsuccess");
        }).error(function(){
            $scope.addAdError = "sorry, some error occur, please try again"
        });


    };




    $scope.animationsEnabled = true;

    $scope.open = function (size) {

        var modalInstance = $modal.open({
            animation: $scope.animationsEnabled,
            templateUrl: 'myModalContent.html',
            controller: 'ModalInstanceCtrl',
            size: size,
            resolve: {
                categoriesList: function () {
                    return $scope.categoriesList;
                }
            }
        });

        modalInstance.result.then(function (selectedItem) {
            $scope.selected = selectedItem;
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

    $scope.toggleAnimation = function () {
        $scope.animationsEnabled = !$scope.animationsEnabled;
    };

    $scope.getCategoryName = function(category){
        return getCategoryTitle(category, $cookies);
    };

});

controllers.controller('dateController', function ($scope){
    $scope.date = new Date();
});


function getPetById($scope, $http, advertId){
    var req = {
        method: 'GET',
        url: 'api/advert/' + advertId,
        headers: {
            'Content-Type': undefined
        }
    };

    $http(req).success(function(data){
        $scope.advertSingle = data;
    }).error(function(){
        //alert('error');
    });
}

controllers.controller('advertController', function ($scope, $routeParams, $http){
    //alert("advertId = " + $routeParams.advertId);
    getPetById($scope, $http, $routeParams.advertId);
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
        url:'api/user/adverts/' + adState,
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
    $location.path("/advert/" + advertId);
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


controllers.controller('addCategoryController', function($scope, $http, $location, $window){

    $scope.category = {};
    $scope.category.titles = [];

    getCategoriesList($scope, $http);

    $scope.submitCategory = function(){

        $scope.category.titles.push({language:'ru', title: $scope.category.titleRU});
        $scope.category.titles.push({language:'ua', title: $scope.category.titleUA});
        $scope.category.titles.push({language:'en', title: $scope.category.titleEN});

        delete $scope.category.titleRU;
        delete $scope.category.titleUA;
        delete $scope.category.titleEN;

        var req = {
            method: 'POST',
            url: '/api/category',
            data: $scope.category,
            headers: {
                'Content-Type': 'application/json'
            }
        };

        $http(req).success(function (data) {
            $scope.category = {};
            $route.reload();
        }).error(function () {

        });
    }
});