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
});

app.controller("LastPageController", function ($scope, $http) {
  //Initialize page with default data which is blank in this example
  $scope.sell = [];
  //Now load the data from server
  $scope.lastPageDataReload = lastPageData;
  lastPageData();

  function lastPageData() {
    $http({
      method: 'GET',
      url: '/sell/getAll'
    }).then(function successCallback(response) {
      $scope.sell = response.data;
    }, function errorCallback(response) {
      console.log(response.statusText);
    });
  }

  SellManagement.updateCost = function updateCost(updVal) {
    var shopBoxId = "shopBox";
    var shopBox = document.getElementById(shopBoxId);
    $http({
        method: 'POST',
        url: '/shop/updCost?shopId=' + shopBox.item(shopBox.selectedIndex).attributes["key"].value
        + '&updVal=' + updVal
      }).then(function successCallback(response) {
        lastPageData();
      }, function errorCallback(response) {
        console.log(response.statusText);
      });
  }

});