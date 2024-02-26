/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

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
  $("#changePass").validate().element('#pass');
});
$('#repass').on('keyup', function () {
  if ($('#pass').val() === $('#repass').val()) {
    $('#repass').css("border", "2px solid green");
  } else
    $('#repass').css("border", "2px solid red");
});

$("#profile_info").validate({
  errorClass: "error fail-alert",
  validClass: "valid success-alert",
  rules: {

    username: {
      remote: {
        url: "Validation/UserNameExists.jsp",
        type: "post",
        data: {
          userName: function () {
            return $('#username').val();
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
    phonenumber: {

      remote: {
        url: "Validation/PhoneExists.jsp",
        type: "post",
        data: {
          phonenumber: function () {
            return $('#full_phone').val();
          }
        }
      }
    }
  },
  messages: {
    username: {

      remote: "اسم المستخدم قيد الاستخدام بالفعل"
    },
    email: {

      remote: "البريد الإلكتروني مستخدم بالفعل"
    },
    phonenumber: {
      remote: "الرقم مستخدم بالفعل"
    }
  }
});


$('#changePass').validate({
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
    cpass: {

      remote: {
        url: "Validation/PasswordCorrect.jsp",
        type: "post",
        data: {
          cpass: function () {
            return $('#cpass').val();
          },
          cid: function () {
            return $('#cid').val();
          }

        }
      }
    }

  },
  messages: {
    cpass: {
      remote: "كلمة السر غير صحيحة"
    }
  }
});