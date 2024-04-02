var SellManagement = angular.module("SellManagement");

var btn = document.getElementById("getSellBtn");

SellManagement.controller("SellTableController", function ($scope, $http) {
    $scope.sell = [];

    SellManagement.deleteSellById = function (id) {
        $http({
             method: 'POST',
             url: '/sell/deleteById/' + id
            }).then(function successCallback(response) {
                //SellManagement.findByShopAndItem();
            }, function errorCallback(response) {
              console.log(response.statusText);
            });
    }

    SellManagement.updateSell = function (sell, shop, item, cost, count) {
        $http({
             method: 'GET',
             url: '/shops/getByName/' + shop
            }).then(function successCallback(response) {
                shop = response.data;
                $http({
                     method: 'GET',
                     url: '/items/getByName/' + item
                    }).then(function successCallback(response) {
                        item = response.data;
                        sell = JSON.parse(sell);
                        sell.shop = shop;
                        sell.item = item;
                        sell.cost = parseInt(cost);
                        sell.count = parseInt(count);
                        $http.post('/sell/update', JSON.stringify(sell))
                            .then(function successCallback(response) {
                                $http({
                                     method: 'GET',
                                     url: '/sell/findByShopAndItem?itemId=' +
                                           itemBox.item(itemBox.selectedIndex).attributes["key"].value +
                                           '&shopId=' + shopBox.item(shopBox.selectedIndex).attributes["key"].value
                                    }).then(function successCallback(response) {
                                        $scope.sell = response.data;
                                        if ($scope.sell == null) {
                                            $(".edit").hide();
                                            $(".delete").hide();
                                            $(".save").hide();
                                            $(".add").show();
                                        } else {
                                            $(".edit").show();
                                            $(".delete").show();
                                            $(".save").hide();
                                            $(".add").hide();
                                        }
                                    }, function errorCallback(response) {
                                      console.log(response.statusText);
                                    });
                            });
                    }, function errorCallback(response) {
                      console.log(response.statusText);
                    });
            }, function errorCallback(response) {
              console.log(response.statusText);
            });
    }

    SellManagement.addSell = function (shop, item, cost, count) {
        $http({
                     method: 'GET',
                     url: '/shops/getByName/' + shop
                    }).then(function successCallback(response) {
                        shop = response.data;
                        $http({
                             method: 'GET',
                             url: '/items/getByName/' + item
                            }).then(function successCallback(response) {
                                item = response.data;
        $http.post('/addSell', '{"cost": ' + cost + ',"item": ' + JSON.stringify(item) + ',"shop": ' + JSON.stringify(shop) + ',"count": ' + count + '}')
            .then(function successCallback(response) {
                               $http({
                                    method: 'GET',
                                    url: '/sell/findByShopAndItem?itemId=' +
                                          itemBox.item(itemBox.selectedIndex).attributes["key"].value +
                                          '&shopId=' + shopBox.item(shopBox.selectedIndex).attributes["key"].value
                                   }).then(function successCallback(response) {
                                       $scope.sell = response.data;
                                       if ($scope.sell == null) {
                                           $(".edit").hide();
                                           $(".delete").hide();
                                           $(".save").hide();
                                           $(".add").show();
                                       } else {
                                           $(".edit").show();
                                           $(".delete").show();
                                           $(".save").hide();
                                           $(".add").hide();
                                       }
                                   }, function errorCallback(response) {
                                     console.log(response.statusText);
                                   });
                           }
            );
        });
        });
    }

    function getSell() {
        var itemBoxId = "itemBox";
        var shopBoxId = "shopBox";

        var shopBox = document.getElementById(shopBoxId);
        var itemBox = document.getElementById(itemBoxId);

        $scope.sell = [];

        $http({
         method: 'GET',
         url: '/sell/findByShopAndItem?itemId=' +
               itemBox.item(itemBox.selectedIndex).attributes["key"].value +
               '&shopId=' + shopBox.item(shopBox.selectedIndex).attributes["key"].value
        }).then(function successCallback(response) {
            $scope.sell = response.data;
            if ($scope.sell == null || $scope.sell == "") {
                $(".edit").hide();
                $(".delete").hide();
                $(".add").show();
            } else {
                $(".edit").show();
                $(".delete").show();
                $(".add").hide();
            }
        }, function errorCallback(response) {
          console.log(response.statusText);
        });
    }
    
    btn.onclick = getSell;
});