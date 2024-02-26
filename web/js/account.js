$('.account_number').click(function (e) {
  e.stopPropagation();
  var copyText = $(this).text().trim();
  /* Copy the text inside the text field */
  navigator.clipboard.writeText(copyText);

});

$('#download_pdf').submit(function (e) {
  e.preventDefault(); // don't submit multiple times
  $('.statement_download').hide('slow');
  unblur();
  this.submit(); // use the native submit method of the form element
});

$('.popup form').on('submit', function (e) {
  //e.preventDefault();
  var criteria = $(this).find('input,select').filter(function () {
    return ((!!this.value) && (!!this.name));
  }).serializeArray();

  var formData = JSON.stringify(criteria);
  localStorage.setItem('savedValues', formData);
});


function cardsScrollRight() {
  $('.cards').animate({
    scrollLeft: 9000
  }, "slow");
}

function cardsScrollRightAR() {
  $('.cards').animate({
    scrollLeft: -9000
  }, "slow");
}


function cardsScrollLeft() {
  $('.cards').animate({
    scrollLeft: 0
  }, "slow");
}


//search
function searchAccount(value) {
  $('.account ').each(function () {
    var content = $(this).find('table tr td').text();
    if (content.toUpperCase().includes(value.trim().toUpperCase())) {
      $(this).show();
    } else {
      $(this).hide();
    }
  });
}



$(document).ready(function () {
/////////////////////////////////////
  //hide request account form
  $('.request_account').hide();
  //hide download statement form
  $('.statement_download').hide();
  //hide delete account warning
  $('.delete_account_div').hide();
  var id_transaction = 0;
  $('#table-scroll tr').hide();


  if ($.cookie('SUCCES') !== "SUCCES") {
    $.cookie('SUCCES', "", {path: '/'});
  }

//check last active account stateby cookie value
  if (typeof $.cookie('active_account') === 'undefined') {
    //first account is active after loading
    $('.cards .account:first').addClass('active');
    $('.cards .account:first').find('.transafer_button').css('display', 'block');
    id_transaction = $('.cards .account:first').attr("id");
    $('.card_div').hide();
    if ($('.card_div.' + id_transaction).length > 1) {
      $('.empty_card_div').hide();
    }
    if ($('.card_div.' + id_transaction).length === 0) {
      $('.empty_card_div').show();
    }
    $('.' + id_transaction).show();
    var expDate = new Date();
    expDate.setTime(expDate.getTime() + (15 * 60 * 1000));
    $.cookie('active_account', id_transaction, {path: '/', expires: expDate});
  } else {
    var id = $.cookie('active_account');
    var account = $('.cards').find('#' + id + '').length;
    if (account === 0) {
      //first account is active after loading
      $('.cards .account:first').addClass('active');
      $('.cards .account:first').find('.transafer_button').css('display', 'block');
      id_transaction = $('.cards .account:first').attr("id");
      $('.card_div').hide();
      if ($('.card_div.' + id_transaction).length > 1) {
        $('.empty_card_div').hide();
      }
      if ($('.card_div.' + id_transaction).length === 0) {
        $('.empty_card_div').show();
      }
      $('.' + id_transaction).show();
      var expDate = new Date();
      expDate.setTime(expDate.getTime() + (15 * 60 * 1000));
      $.cookie('active_account', id_transaction, {path: '/', expires: expDate});
    } else {
      $('.cards').animate({
        scrollLeft: 0
      }, 200);
      $('.account').removeClass('active');
      $('.account').find('.transafer_button').css('display', 'none');
      $('#' + id + '').addClass('active');
      $('#' + id + '').find('.transafer_button').css('display', 'block');
      id_transaction = id;
      if ($('.card_div.' + id_transaction).length > 1) {
        $('.empty_card_div').hide();
      }
      if ($('.card_div.' + id_transaction).length === 0) {
        $('.empty_card_div').show();
      }
      $('.card_div').hide();
      $('.' + id_transaction).show();
    }

  }


  var SUCCES = $.cookie('SUCCES');
  //check opened popup state by cookie value
  if (typeof $.cookie('active_popup') !== 'undefined') {
    var popup = $.cookie('active_popup');
    if (popup !== null && SUCCES !== "SUCCES") {

      $("#accounts_lists_account_delete option").prop('disabled', false);
      //disable account to delete option
      $("#accounts_lists_account_delete option[value='" + $.cookie('active_account') + "']").prop('disabled', true);
      $('.' + popup + '').show();
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
      var popup = $.cookie('active_popup');
      $('.' + popup).hide();
    }
  }
  //set active account card
  $('.account').click(function () {
    $('.cards').animate({
      scrollLeft: 0
    }, 200);
    $('.account').removeClass('active');
    $('.account').find('.transafer_button').css('display', 'none');
    $(this).addClass('active');
    $(this).find('.transafer_button').css('display', 'block');
    var expDate = new Date();
    expDate.setTime(expDate.getTime() + (15 * 60 * 1000));
    $.cookie('active_account', $(this).attr("id"), {path: '/', expires: expDate});

    $('#table-scroll tr').hide();
    $('.card_div').hide();
    id_transaction = $(this).attr("id");
    if ($('.card_div.' + id_transaction).length > 1) {
      $('.empty_card_div').hide();
    }

    if ($('.card_div.' + id_transaction).length === 0) {
      $('.empty_card_div').show();
    }
    $('.' + id_transaction).show();
    $('.error').hide();


    //$('#' + id_transaction).show();
  });

  if ($.cookie('langue') !== 'ar') {
//scroll right arrow
    $('.cards').scroll(function () {
      var _this = this;
      if (Math.abs(_this.scrollLeft) === _this.scrollWidth - _this.clientWidth) {
        $('#accounts_scroll').hide('fast');
      } else {
        $('#accounts_scroll').show('fast');
      }
    });
    $('#accounts_scroll_left').hide('fast');
//left scroll arrow on my accounts show or hide if there is an overflow
    $('.cards').scroll(function () {
      var _this = this;
      if (Math.abs(_this.scrollLeft) === 0) {
        $('#accounts_scroll_left').hide('fast');
      } else {
        $('#accounts_scroll_left').show('fast');
      }
    });
//right scroll arrow on my accounts show or hide if there is an overflow
    if ($('.cards').prop('scrollWidth') > $('.cards').width()) {
      $('#accounts_scroll').show('fast');
    } else {
      $('#accounts_scroll').hide('fast');
    }
  } else if ($.cookie('langue') === 'ar') {
    //scroll right arrow
    $('.cards').scroll(function () {
      var _this = this;
      if (Math.abs(_this.scrollLeft) === _this.scrollWidth - _this.clientWidth) {
        $('#accounts_scroll').show('fast');
      } else {
        $('#accounts_scroll').hide('fast');
      }
    });
    $('#accounts_scroll_left').show('fast');
//left scroll arrow on my accounts show or hide if there is an overflow
    $('.cards').scroll(function () {
      var _this = this;
      if (Math.abs(_this.scrollLeft) === 0) {
        $('#accounts_scroll_left').show('fast');
      } else {
        $('#accounts_scroll_left').hide('fast');
      }
    });
//right scroll arrow on my accounts show or hide if there is an overflow
    if ($('.cards').prop('scrollWidth') > $('.cards').width()) {
      $('#accounts_scroll').hide('fast');
    } else {
      $('#accounts_scroll').show('fast');
    }
  }
});

$('#link_card_from').submit(function (e) {
  e.preventDefault(); // don't submit multiple times
  $('#link_card_id_account').attr('value', $.cookie('active_account'));
  this.submit(); // use the native submit method of the form element
});
//show/hide fields on request account form based on account type choice
//savings accounts have saving goal
//checkings accounts have budget plan and amount in  a chosen period 
function SelectedAccountType(value) {
  if (value === "saving") {
    $('.row_savings').show();
    $('.row_checking').hide();
  }
  if (value === "checking") {
    $('.row_checking').show();
    $('.row_savings').hide();
  }
  if (value === "") {
    $('.row_checking').hide();
    $('.row_savings').hide();
  }

}
//toggle front and back of the card

$('.front').click(function (e) {
  $('.front').hide('slow');
  $('.back').show('slow');
  e.stopPropagation();
});

$('.back').click(function () {
  $('.back').hide('slow');
  $('.front').show('slow');

});
//toggle dropdown menus
$('.account_menu_btn').click(function (e) {
  $(this).find('.account_menu').toggle();
  $('.account_menu_btn').not(this).find('.account_menu').hide('fast');
  e.stopPropagation();
});

$('.popup_div').click(function (e) {
  e.stopPropagation();
});



function blur() {
  $('.popup_div').show();
  $('.home_content').css('filter', 'blur(5px)');
  $('.sidebar').css('filter', 'blur(5px)');
  $('.profile_content').css('filter', 'blur(5px)');
}

function unblur() {
  $('.popup_div').hide();
  $('.home_content').css('filter', 'none');
  $('.sidebar').css('filter', 'none');
  $('.profile_content').css('filter', 'none');
  $.removeCookie('active_popup', {path: '/'});
  $.removeCookie("transfer_error", {path: '/'});
  localStorage.clear();

}


function openRequestCard() {
    $('.link_card').show();
    $('#pin').focus();
    blur();
    var expDate = new Date();
    expDate.setTime(expDate.getTime() + (15 * 60 * 1000));
    $.cookie('active_popup', "link_card", {path: '/', expires: expDate});
  }

//open transfer div
function openTransferDiv(value) {
  $('.transfer_div').show();
  $('#transfer_accountfrom').val(value);

  var expDate = new Date();
  expDate.setTime(expDate.getTime() + (15 * 60 * 1000));
  $.cookie('active_popup', "transfer_div", {path: '/', expires: expDate});
  blur();
  //save values
  var criteria = $('.transfer_div').find('input, select, textarea').filter(function () {
    return ((!!this.value) && (!!this.name));
  }).serializeArray();

  var formData = JSON.stringify(criteria);
  localStorage.setItem('savedValues', formData);
}
//show request account request form and blur background
$('.add_account').on('click', function () {
  $('.request_account').show();
  $('#request_account_name').focus();
  blur();
  var expDate = new Date();
  expDate.setTime(expDate.getTime() + (15 * 60 * 1000));
  $.cookie('active_popup', "request_account", {path: '/', expires: expDate});
});

//close request account form and remove bulr filter from background
function colseAccountRequest() {
  $('.request_account').hide();
  unblur();
}


//show statement download form and blur background
$('.statement_download_btn').click(function () {
  $('.statement_download').show();
  $('#start-date').focus();
  var expDate = new Date();
  expDate.setTime(expDate.getTime() + (15 * 60 * 1000));
  $.cookie('active_popup', "statement_download", {path: '/', expires: expDate});
  blur();

});
//close statement download div button
function colseStatementDownload() {
  $('.statement_download').hide();
  unblur();
}
;

//show delete account form and blur background
function openAccountDelete(value) {
  $('.delete_account_div').show();
  //disable account to delete option
  $("#accounts_lists_account_delete option").not(this).prop('disabled', false);
  //enable other options
  $("#accounts_lists_account_delete option[value='" + value + "']").prop('disabled', true);
  //set account id
  $('#account_to_delete').val(value);
  $('.delete_account_div form input[type="submit"]').attr('name', value);
  blur();
  var expDate = new Date();
  expDate.setTime(expDate.getTime() + (15 * 60 * 1000));
  $.cookie('active_popup', "delete_account_div", {path: '/', expires: expDate});
  blur();
}
;

//close delete account div button event
function colseAccountDelete() {
  $('.delete_account_div').hide();
  unblur();
}
;

function openAccountEdit(value, name, type, currency, goal_or_budget, period) {
  $('.edit_account_div').show();
  //
  if (type === "saving") {
    $('.row_savings').show();
    $('#saving_goal_edit').val(goal_or_budget);
    $('.row_checking').hide();
  }
  if (type === "checking") {
    $('.row_checking').show();
    $('#budget_period_edit').val(period);
    $('#budget_amount').val(goal_or_budget);
    $('.row_savings').hide();
  }
  if (type === "") {
    $('.row_checking').hide();
    $('.row_savings').hide();
  }
  //rest
  $('#edit_account_name').val(name);
  $('#account_type_edit').val(type);
  $('#currencyedit').val(currency);

  $('#request_account_name_edit').focus();
  $('#account_to_edit').val(value);
  $('.edit_account_div form input[type="submit"]').attr('name', value);

  blur();

  var expDate = new Date();
  expDate.setTime(expDate.getTime() + (15 * 60 * 1000));
  $.cookie('active_popup', "edit_account_div", {path: '/', expires: expDate});

  var criteria = $('.edit_account_div').find('input,select').filter(function () {
    return ((!!this.value) && (!!this.name));
  }).serializeArray();

  var formData = JSON.stringify(criteria);
  localStorage.setItem('savedValues', formData);
}

function closeAccountEdit() {
  $('.row_checking').hide();
  $('.row_savings').hide();
  $('.edit_account_div').hide();
  unblur();
}

function closeSuccesDiv() {
  $('.succes_transfer').hide();
  unblur();
}
//
function openRemoveCardDiv() {
  $('.delete_card').show();
  blur();
}

function closeRemoveCardDiv() {
  $('.delete_card').hide();
  unblur();
}





