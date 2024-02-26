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
    return 'Le mot de passe doit comporter entre 8 et 20 caractères.';
  } else if (!(/^(?=.*[A-Z])/.test(password))) {
    return 'Le mot de passe doit contenir au moins une majuscule.';
  } else if (!(/^(?=.*[a-z])/.test(password))) {
    return 'Le mot de passe doit contenir au moins une minuscule.';
  } else if (!(/^(?=.*[0-9])/.test(password))) {
    return 'Le mot de passe doit contenir au moins un chiffre.';
  } else if (!(/^(?=.*[.@#$%&])/.test(password))) {
    return  "Le mot de passe doit contenir des caractères spéciaux de @#$%&.";
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
      remote: "Nom d'utilisateur déjà utilisée"
    },
    email: {

      remote: "E-Mail déjà utilisée"
    },
    phonenumber: {
      remote: "Numéro déja utilisé"
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
      remote: "Mot de passe incorrect"
    }
  }
});