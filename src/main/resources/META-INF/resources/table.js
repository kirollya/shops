var SellManagement = angular.module("SellManagement");
$('.save').hide();
$('.add').hide();

$(document).on('click', '.edit', function() {
  $(this).parent().siblings('td.data').each(function() {
    var content = $(this).html();
    $(this).append('<input value="' + content + '" />');
  });
  $(this).siblings('.save').show();
  $(this).siblings('.delete').hide();
  $(this).hide();
});
$(document).on('click', '.save', function() {
  var content = [];
  $('input').each(function() {
      content.push($(this).val());
      $(this).remove();
    });

  if ($(this).parents('tr')[0].attributes["key"].nodeValue == null || $(this).parents('tr')[0].attributes["key"].nodeValue == '') {
    SellManagement.addSell(
        content[0],
        content[1],
        content[3],
        content[2]
    );
  } else {
    SellManagement.updateSell(
        $(this).parents('tr')[0].attributes["key"].nodeValue,
        content[0],
        content[1],
        content[3],
        content[2]
    );
  }
  $(this).siblings('.edit').show();
  $(this).siblings('.delete').show();
  $(this).hide();
});
$(document).on('click', '.delete', function() {
    SellManagement.deleteSellById($(this).parents('tr')[0].cells[0].innerText);
});
$(document).on('click', '.add', function() {
    $(".add").hide();
    $('.save').show();
    var itemBoxId = "itemBox";
    var shopBoxId = "shopBox";
    var shopBox = document.getElementById(shopBoxId);
    var itemBox = document.getElementById(itemBoxId);
    $(this).parent().siblings('td.data').each(function() {
        if ($(this)[0] == $("td.shop_col")[0])
            $(this).append('<input value="' + shopBox.item(shopBox.selectedIndex).value + '" />');
        else if ($(this)[0] == $("td.item_col")[0])
            $(this).append('<input value="' + itemBox.item(itemBox.selectedIndex).value + '" />');
        else
            $(this).append('<input value="" />');
      });
});