
// functions for form individu verification
$.validator.addMethod("strong_password", function (value, element) {
  let password = value;
  if (!(/^(?=.*[0-9])(?=.*[.@#$%&])(.{8,20}$)/
          .test(password))) {
    return false;
  }
  return true;
}, function (value, element) {
  let password = $(element).val();
  if (!(/^(.{8,20}$)/.test(password))) {
    return 'يجب أن يكون طول كلمة المرور ما بين 8 إلى 20 حرفًا.';
  } else if (!(/^(?=.*[0-9])/.test(password))) {
    return 'يجب أن تحتوي كلمة المرور على رقم واحد على الأقل.';
  } else if (!(/^(?=.*[.@#$%&])/.test(password))) {
    return "يجب أن تحتوي كلمة المرور على أحرف خاصة من @ # $٪ &.";
  }
  return false;
});

$('#pass').on('keyup', function () {
  $(".individual-form").validate().element('#pass');
});

$('#repass').on('keyup', function () {
  if ($('#pass').val() === $('#repass').val()) {
    $('#repass').css("border", "2px solid green");
  } else
    $('#repass').css("border", "2px solid red");
});

$('#opass').on('keyup', function () {
  $(".organisation-form").validate().element('#opass');
});

$('#orepass').on('keyup', function () {
  if ($('#opass').val() === $('#orepass').val()) {
    $('#orepass').css("border", "2px solid green");
  } else
    $('#orepass').css("border", "2px solid red");
});

//
$(".individual-form").validate({
  errorClass: "error fail-alert",
  validClass: "valid success-alert",
  rules: {
    pass: {
      required: true,
      strong_password: true

    },
    repass: {
      required: true,
      equalTo: '#pass'
    },
    userName: {
      remote: {
        url: "Validation/UserNameExists.jsp",
        type: "post",
        data: {
          userName: function () {
            return $('#userName').val();
          }
        }
      }
    },
    email: {

      remote: {
        url: "Validation/EmailExists.jsp",
        type: "post",
        data: {
          email: function () {
            return $('#email').val();
          }
        }
      }
    },
    phone: {

      remote: {
        url: "Validation/PhoneExists.jsp",
        type: "post",
        data: {
          phone: function () {
            return $('#full_phone').val();
          }
        }
      }
    }
  },
  messages: {
    userName: {

      remote: "اسم المستخدم قيد الاستخدام بالفعل"
    },
    email: {

      remote: "البريد الإلكتروني مستخدم بالفعل"
    },
    phone: {
      remote: "الرقم مستخدم بالفعل"
    }
  }
});
//validate 2 nd form
$(".organisation-form").validate({
  errorClass: "error fail-alert",
  validClass: "valid success-alert",
  rules: {
    opass: {
      required: true,
      strong_password: true
    },
    orepass: {
      required: true,
      equalTo: '#opass'
    },
    ouserName: {
      remote: {
        url: "Validation/oUserNameExists.jsp",
        type: "post",
        data: {
          ouserName: function () {
            return $('#ouserName').val();
          }
        }
      }
    },
    oemail: {

      remote: {
        url: "Validation/oEmailExists.jsp",
        type: "post",
        data: {
          oemail: function () {
            return $('#oemail').val();
          }
        }
      }
    },
    ophone: {

      remote: {
        url: "Validation/oPhoneExists.jsp",
        type: "post",
        data: {
          ophone: function () {
            return $('#ofull_phone').val();
          }
        }
      }
    }
  },
  messages: {
    ouserName: {

      remote: "اسم المستخدم قيد الاستخدام بالفعل"
    },
    oemail: {

      remote: "البريد الإلكتروني مستخدم بالفعل"
    },
    ophone: {
      remote: "الرقم مستخدم بالفعل"
    }
  }

});




