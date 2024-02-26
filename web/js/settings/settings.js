/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function showProfile(e) {
  $('.' + e.className).removeClass('active_setting');
  $('#' + e.id).addClass('active_setting');

  $('.profile_settigns').show();
  $('.language_settigns').hide();
  $('.security_settings').hide();
  var expDate = new Date();
  expDate.setTime(expDate.getTime() + (15 * 60 * 1000));
  $.cookie('active_setting', 'profile_settigns', {path: '/', expires: expDate});
}

function showLanguage(e) {
  $('.' + e.className).removeClass('active_setting');
  $('#' + e.id).addClass('active_setting');
  $('.profile_settigns').hide();
  $('.language_settigns').show();
  $('.security_settings').hide();

  var expDate = new Date();
  expDate.setTime(expDate.getTime() + (15 * 60 * 1000));
  $.cookie('active_setting', 'language_settigns', {path: '/', expires: expDate});
}

function showSecurity(e) {
  $('.' + e.className).removeClass('active_setting');
  $('#' + e.id).addClass('active_setting');
  $('.profile_settigns').hide();
  $('.language_settigns').hide();
  $('.security_settings').show();
  var expDate = new Date();
  expDate.setTime(expDate.getTime() + (15 * 60 * 1000));
  $.cookie('active_setting', 'security_settings', {path: '/', expires: expDate});
}

$(document).ready(function () {
  if (typeof $.cookie('active_setting') !== 'undefined') {
    var active_setting = $.cookie('active_setting');

    if (active_setting === 'profile_settigns') {
      $('button.' + active_setting).removeClass('active_setting');
      $('button#profile').addClass('active_setting');
      $('div.profile_settigns').show();
      $('div.language_settigns').hide();
      $('div.security_settings').hide();

    } else if (active_setting === 'language_settigns') {
      $('.' + active_setting).removeClass('active_setting');
      $('#language').addClass('active_setting');
      $('.profile_settigns').hide();
      $('.language_settigns').show();
      $('.security_settings').hide();

    } else if (active_setting === 'security_settings') {
      $('.' + active_setting).removeClass('active_setting');
      $('#security').addClass('active_setting');
      $('.profile_settigns').hide();
      $('.language_settigns').hide();
      $('.security_settings').show();
      $('#cpass').focus();

    }
  } else {
    $('button.' + active_setting).removeClass('active_setting');
    $('button#profile').addClass('active_setting');
    $('div.profile_settigns').show();
    $('div.language_settigns').hide();
    $('div.security_settings').hide();
  }
});

$('#upload_pic').click(function(){
  $('#profile_pic_file').click();
});
$('#profile_pic_file').change(function(){
  $('#submit_profile_pic').click();
});


