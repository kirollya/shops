var SellManagement = angular.module("SellManagement");

$(document).on('click', '.updCostBtn', function() {
    var content = [];
    $('.valInput').each(function() {
          content.push($(this).val());
    });
    SellManagement.updateCost(content[0]);
})