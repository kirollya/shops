var app = angular.module("SellManagement", []);

//Controller Part
app.controller("SellManagementController", function ($scope, $http) {
  //Initialize page with default data which is blank in this example
  $scope.shops = [];
  $scope.items = [];
  //Now load the data from server
  _refreshPageData();

  function _refreshPageData() {
    $http({
      method: 'GET',
      url: '/shops/getAll'
    }).then(function successCallback(response) {
      $scope.shops = response.data;
    }, function errorCallback(response) {
      console.log(response.statusText);
    });
    $http({
      method: 'GET',
      url: '/items/getAll'
    }).then(function successCallback(response) {
      $scope.items = response.data;
    }, function errorCallback(response) {
      console.log(response.statusText);
    });
  }
  function _success(response) {
    _refreshPageData();
  }
  function _error(response) {
    alert(response.data.message || response.statusText);
  }
});