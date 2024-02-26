
$(document).ready(function () {
  $('.links_name').hide();
});
$("#sidebar-btn").on('click', function (e) {
  $('#sidebar-btn').removeClass('open');
  $('.sidebar').removeClass('open');
  $('.home_content').removeClass('open');
  $('.profile_content').removeClass('open');

  $('.fa-building-columns').hide();
  $('.home_content').css('filter', 'none');
  $('.popup_div').hide();
  $(".fa-bars").show();
  $('.links_name').hide('fast');


  e.stopPropagation();
});

$('.sidebar').on('click', function (e) {
  e.stopPropagation();

});
$(".fa-bars").on('click', function (e) {
  $('#sidebar-btn').addClass('open');
  $('.fa-building-columns').show();
  $(this).hide();
  $('.sidebar').addClass('open');
  $('.home_content').addClass('open');
  $('.home_content').css('filter', 'blur(5px)');
  $('.profile_content').addClass('open');

  $('.popup_div').show();
  $('.links_name').show('fast');

  e.stopPropagation();
});

$(document).ready(function () {
//set active link in side bar based on the context
  var CurrentUrl = document.URL;
  var CurrentUrlEnd = CurrentUrl.split('/').filter(Boolean).pop();
  $(".sidebar .nav_list li a").each(function () {
    var ThisUrl = $(this).attr('href');
    var ThisUrlEnd = ThisUrl.split('/').filter(Boolean).pop();

    if (CurrentUrlEnd.includes(ThisUrlEnd)) {
      $(this).closest('a').addClass('active');
    }
  });
});


//cancel account request if clicked outside from
/*$(document).mouseup(function (e) {
  var container = $(".popup");
  var sidebar = $(".sidebar");
  var popupdiv = $(".popup_div");

  // if the target of the click isn't the container nor a descendant of the container
  if ((!container.is(e.target) && container.has(e.target).length === 0)
          && (!sidebar.is(e.target) && sidebar.has(e.target).length === 0)
          && (!popupdiv.is(e.target) && popupdiv.has(e.target).length === 0)) {
    container.hide('fast');
    unblur();
    e.stopPropagation();
  }

});*/
