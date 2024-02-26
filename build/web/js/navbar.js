/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

$(document).ready(function () {
//set active link in side bar based on the context
  var CurrentUrl = document.URL;
  var CurrentUrlEnd = CurrentUrl.split('/').filter(Boolean).pop();
  $(".navbar .navbar-links li a").each(function () {
    var ThisUrl = $(this).attr('href');
    var ThisUrlEnd = ThisUrl.split('/').filter(Boolean).pop();
    if (CurrentUrlEnd.includes(ThisUrlEnd)) {
      $(this).closest('a').addClass('active');
    }
  });
});