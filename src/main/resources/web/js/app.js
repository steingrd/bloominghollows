angular.module('bhApp', ['ngRoute'])
	.config(['$routeProvider', function($routeProvider) {
		$routeProvider
    		.when('/', { 
    			templateUrl: 'partials/brew-list.html', 
    			controller: BrewListController
    		})
    		.when('/brews/:brewId', { 
    			templateUrl: 'partials/brew-temperatures.html', 
    			controller: TemperaturesController 
    		})
    		.otherwise({ 
    			redirectTo: '/' 
    		})
	}]);

function TemperaturesController ($scope) {
	
}

function BrewListController ($scope, $http) {
	
	$scope.refresh = function () {
		$http.get('/service/brews').success(function(data) {
			$scope.brews = data;
		});
	};
	
	$scope.createBrew = function() {
		$http({
			url: '/service/brews',
			method: 'POST',
			data: { name: $scope.brewName },
			headers: { 'X-Auth-Token': $scope.authToken },
		}).success(function(data) {
			$scope.refresh();
		});
		
	};
	
	$scope.brews = [];
	$scope.refresh();
}