/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
function blur() {
  $('.popup_div').show();
  $('.home_content').css('filter', 'blur(5px)');
  $('.sidebar').css('filter', 'blur(5px)');
  $('.profile_content').css('filter', 'blur(5px)');
}

function unblur() {
  $('.error').hide();
  $('.popup_div').hide();
  $('.home_content').css('filter', 'none');
  $('.sidebar').css('filter', 'none');
  $('.profile_content').css('filter', 'none');
  $.removeCookie('active_popup_card', {path: '/'});
  $.removeCookie("transfer_error", {path: '/'});
  localStorage.clear();
}

$('.popup form').on('submit', function (e) {
  //e.preventDefault();
  var criteria = $(this).find('input,select').filter(function () {
    return ((!!this.value) && (!!this.name));
  }).serializeArray();

  var formData = JSON.stringify(criteria);
  localStorage.setItem('savedValues', formData);
});

var id_card = 0;
$(document).ready(function () {
  
  if ($.cookie('SUCCES') !== "SUCCES") {
    $.cookie('SUCCES', "", {path: '/'});
  }
  var SUCCES = $.cookie('SUCCES');
  //check opened popup state by cookie value
  if (typeof $.cookie('active_popup_card') !== 'undefined') {
    var popup = $.cookie('active_popup_card');
    if (popup !== null && SUCCES !== "SUCCES") {
      $('.' + popup + '').show();

      //get saved values from local storage
      var savedValues = JSON.parse(localStorage.getItem('savedValues'));
      var _this = $('.' + popup + '');
      if (savedValues !== null) {
        $.each(savedValues, function (i, v) {
          _this.find('table tr td input[name=' + v.name + ']').val(v.value);
          if (_this.find('table tr td select[name=' + v.name + ']').val() === "") {
            _this.find('table tr td select[name=' + v.name + ']').val(v.value);
          }
        });

      }

      blur();
    } else if (SUCCES !== null && SUCCES === "SUCCES") {
      blur();
      $('.succes_transfer').show();
      var popup = $.cookie('active_popup_card');
      $('.' + popup).hide();
    }
  }

//Active card and it's transactions list
  if (typeof $.cookie('active_card') === 'undefined') {
    //set first card in list as active card
    $('.active_card').find('.card_div').hide();
    $('.active_card').find('.card_div:first').show();
    //get the id of the card
    id_card = $('.active_card').find('.card_div:first').attr("id");
    console.log(id_card);
    //hide the card
    $('.cards_container .card_div').show();
    $('.cards_container').find('#' + id_card).hide();
    //show card info
    $('.card_infos').find('.table_info_container').hide();
    $('.card_infos').find('#' + id_card).show();

    //show card history
    $('.main-table tr').hide();
    $('.main-table tr.'+id_card).show();

    var expDate = new Date();
    expDate.setTime(expDate.getTime() + (15 * 60 * 1000));
    $.cookie('active_card', id_card, {path: '/', expires: expDate});
  } else {

    id_card = $.cookie('active_card');
    //Show active card
    $('.active_card').find('.card_div').hide();
    $('.active_card').find('#' + id_card).show();

    //hide it from cards container
    $('.cards_container .card_div').show();
    $('.cards_container').find('#' + id_card).hide();

    //show card info
    $('.card_infos').find('.table_info_container').hide();
    $('.card_infos').find('#' + id_card).show();

    //show card history
    $('.main-table tr').hide();
    $('.main-table tr.' + id_card).show();
  }

});

$('.cards_container .card_div').click(function (e) {
  id_card = $(this).attr("id");
  $('.cards_container .card_div').css('order', '0');
  $(this).css('order', '1');
//
  $('.active_card .card_div').hide();
  $('.active_card').find('#' + id_card).show();
//
  $('.cards_container .card_div').show();
  $(this).hide();
//
  $('.card_infos').find('.table_info_container').hide();
  $('.card_infos').find('#' + id_card).show();

  //show card history
  $('.main-table tr').hide();
  $('.main-table tr.'+id_card).show();

  var expDate = new Date();
  expDate.setTime(expDate.getTime() + (15 * 60 * 1000));
  $.cookie('active_card', $(this).attr("id"), {path: '/', expires: expDate});
});

//toggle dropdown menus
$('.account_menu_btn').click(function (e) {
  $(this).find('.account_menu').toggle();
  $('.account_menu_btn').not(this).find('.account_menu').hide('slow');
  e.stopPropagation();
});


//open transfer div
function openTransferDiv(value, cardNumber) {
  $('.card_transfer_div').show();
  $('#transfer_accountfrom').val(value);
  $('#transfer_cardfrom').val(cardNumber);
  $('.error').hide();

  var expDate = new Date();
  expDate.setTime(expDate.getTime() + (15 * 60 * 1000));
  $.cookie('active_popup_card', "card_transfer_div", {path: '/', expires: expDate});
  blur();
  //save values
  var criteria = $('.card_transfer_div').find('input, select, textarea').filter(function () {
    return ((!!this.value) && (!!this.name));
  }).serializeArray();

  var formData = JSON.stringify(criteria);
  localStorage.setItem('savedValues', formData);
}

function closeSuccesDiv() {
  $('.succes_transfer').hide();
  unblur();
}