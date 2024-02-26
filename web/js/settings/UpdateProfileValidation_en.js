/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
$.validator.addMethod("strong_password", function (value, element) {
  let password = value;
  if (!(/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[.@#$%&])(.{8,20}$)/
          .test(password))) {
    return false;
  }
  return true;
}, function (value, element) {
  let password = $(element).val();
  if (!(/^(.{8,20}$)/.test(password))) {
    return 'Password must be between 8 to 20 characters long.';
  } else if (!(/^(?=.*[A-Z])/.test(password))) {
    return 'Password must contain at least one uppercase.';
  } else if (!(/^(?=.*[a-z])/.test(password))) {
    return 'Password must contain at least one lowercase.';
  } else if (!(/^(?=.*[0-9])/.test(password))) {
    return 'Password must contain at least one digit.';
  } else if (!(/^(?=.*[.@#$%&])/.test(password))) {
    return "Password must contain special characters from @#$%&.";
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
            return $('#phonenumber').val();
          }
        }
      }
    }
  },
  messages: {
    username: {

      remote: "Username already taken"
    },
    email: {

      remote: "Email already used"
    },
    phonenumber: {
      remote: "Phone already used"
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
      remote: "Incorrect Password"
    }
  }
});