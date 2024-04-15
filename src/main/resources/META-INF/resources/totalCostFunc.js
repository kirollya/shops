var SellManagement = angular.module("SellManagement");

var btn = document.getElementById("setNameBtn");
var firstRepBtn = document.getElementById("createFirstReportBtn");
var secondRepBtn = document.getElementById("createSecondReportBtn");

SellManagement.controller("TotalCostController", function ($scope, $http) {
  //Initialize page with default data which is blank in this example
  $scope.totalCostShops = [];
  //Now load the data from server
  _refreshTotalCostPageData();

  function _refreshTotalCostPageData() {
    var shopUrl = '/getTotalCosts';
    if ($scope.shopName && $scope.shopName != '')
        shopUrl += "?shopName=" + $scope.shopName;
    $scope.totalCostShops = [];
    $http({
      method: 'GET',
      url: shopUrl
    }).then(function successCallback(response) {
      var respData = response.data;
      for (var i = 0; i < respData.length; i++) {
        $scope.totalCostShops.push(respData[i]);
      }
    }, function errorCallback(response) {
      console.log(response.statusText);
    });
  }

  function setShopName() {
    $scope.shopName = "";
    $(this).siblings('input.shopNameInput').each(function() {
        $scope.shopName = $(this).val();
    });
    var elements = $(this).siblings('table.totalShopCostTable.data')[0].children[2].children;
    for (; elements.length != 0;)
          elements[0].remove();
    _refreshTotalCostPageData();
  }

  function createFirstReport() {
      $http({
        method: 'POST',
        url: "/report/create/1"
      });
  }

  function createSecondReport() {
      $http({
        method: 'POST',
        url: "/report/create/2"
      });
  }

  btn.onclick = setShopName;
  firstRepBtn.onclick = createFirstReport;
  secondRepBtn.onclick = createSecondReport;

});