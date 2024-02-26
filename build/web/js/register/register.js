/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

//nav bar script
const toggleButton = document.getElementsByClassName('toggle-button')[0];
const navbarLinks = document.getElementsByClassName('navbar-links')[0];

toggleButton.addEventListener('click', () => {
  navbarLinks.classList.toggle('active');
});

//toggle forms
$(document).ready(function () {

  $('.individual-btn').addClass('active');
  $('.organisation-btn').removeClass('active');
  $('.organisation-form').hide();
  $('.individual-form').show();
  $('#firstname').focus();


  if ($.cookie('state') !== null) {

    if ($.cookie('state') === 'organisation-form') {
      $('.organisation-btn').addClass('active');
      $('.individual-btn').removeClass('active');
      $('.organisation-form').show();
      $('.individual-form').hide();
      $('#name').focus();
    } else if (($.cookie('state') === 'individual-form')) {
      $('.individual-btn').addClass('active');
      $('.organisation-btn').removeClass('active');
      $('.organisation-form').hide();
      $('.individual-form').show();
      $('#firstname').focus();
    }
  }


//toogle buttons
  $('.organisation-btn').on('click',
          function () {
            $('.organisation-form').show();
            $('.individual-form').hide();
            $('.organisation-btn').addClass('active');
            $('.individual-btn').removeClass('active');
            $('#name').focus();
            var expDate = new Date();
            expDate.setTime(expDate.getTime() + (15 * 60 * 1000));
            $.cookie('state', 'organisation-form', {path: '/', expires: expDate});
          }
  );
  $('.individual-btn').on('click',
          function () {
            $('.individual-form').show();
            $('.organisation-form').hide();
            $('.individual-btn').addClass('active');
            $('.organisation-btn').removeClass('active');
            $('#firstname').focus();
            var expDate = new Date();
            expDate.setTime(expDate.getTime() + (15 * 60 * 1000));
            $.cookie('state', 'individual-form', {path: '/', expires: expDate});
          }
  );

});

const phoneInputField = document.querySelector("#phone");
const phoneInput = window.intlTelInput(phoneInputField, {
  initialCountry: "dz",
  utilsScript:
          "intel-tel-input/js/utils.js"
});

const ophoneInputField = document.querySelector("#ophone");
const ophoneInput = window.intlTelInput(ophoneInputField, {
  initialCountry: "dz",
  utilsScript:
          "intel-tel-input/js/utils.js"
});

function setPhoneIndividual() {
  const full_phone = document.querySelector("#full_phone");
  const phone = document.querySelector("#phone");
  full_phone.value = phoneInput.getNumber();
}


function setPhoneOrganization() {
  const ofull_phone = document.querySelector("#ofull_phone");
  const ophone = document.querySelector("#ophone");
  ofull_phone.value = ophoneInput.getNumber();
  console.log(ophoneInput.getNumber());
}






