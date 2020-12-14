var User = function () {

    var hidePasswordFields = function () {

        $("input[name='systemGeneratedPassword']").on('change', function () {
            if ($(this).is(":checked")) {
                $('#passwordHide').hide();
                $('#passwordHideEdit').hide();
                $("#changePassword1").prop('checked', false);
                $("#welcomeEmail1").prop('checked', true);
                var password =  $('#form').find('input[name="user.password"]') ;
                password.val('');
                $('#confirmPassword').val('');
            }
            else {
                $('#passwordHide').show();
                $("#welcomeEmail1").prop('checked', false);
                $('#passwordHideEdit').hide();
            }
        });
    };

    var disableFields = function () {
        $("input[name='adminUser']").on('click', function () {
            if ($(this).is(":checked")) {


                $('#labUser1').attr('disabled', 'disabled');
            }

        });
        $("input[name='labUser']").on('click', function () {
            if ($(this).is(":checked")) {


                $('#adminUser1').attr('disabled', 'disabled');
            }

        });
    };

    return{
        init: function () {
            $('#msg-hide').hide();
            
            $("#checkAvailable").click(function (e)  {
                var email = $('#form').find('input[name="user.email"]').val();
                var username = $('#form').find('input[name="user.username"]').val();
                 if(email !== "" || username !== "")   {
                $.ajax({
                    type: "GET",
                    async: "false",

                    url: "/user/checkEmail",
                    data:{
                        email:email,
                        username:username
                    } ,
                    success: function (data) {
                        if (data === 0) {
                            $.msgBox({
                                title: "Ooops",

                                content: "Email i korisničko ime već postoje!",
                                type: "error",
                                opacity: 0.1,
                                buttons: [{ value: "Ok" }]
                            });
                        }
                        else if(data  === 1)   {
                              if(email !== "" && username !== "")
                              {
                            $.msgBox({
                                title: "Ooops",
                                content: "Email i korisničko ime su dostupni.",
                                type: "info",
                                opacity: 0.1,
                                buttons: [{ value: "Ok" }]
                            });
                              }
                            else if(email !== "")
                              {
                                  $.msgBox({
                                      title: "Ooops",
                                      content: "Email je dostupan.",
                                      type: "info",
                                      opacity: 0.1,
                                      buttons: [{ value: "Ok" }]
                                  });
                              }
                            else
                              {
                                  $.msgBox({
                                      title: "Ooops",
                                      content: "Korisničko ime je dostupno.",
                                      type: "info",
                                      opacity: 0.1,
                                      buttons: [{ value: "Ok" }]
                                  });
                              }
                        }
                        if (data  === 2) {
                            $.msgBox({
                                title: "Ooops",
                                content: "Email već postoji!",
                                type: "error",
                                opacity: 0.1,
                                buttons: [{ value: "Ok" }]
                            });
                        }
                        else if(data  === 3)   {
                            $.msgBox({
                                title: "Ooops",
                                content: "Korisničko ime već postoji!",
                                type: "error",
                                opacity: 0.1,
                                buttons: [{ value: "Ok" }]
                            });
                        }
                    }
                });
                 }
                else  if(email === "" && username === "")
                 {
                     $.msgBox({
                         title: "Ooops",
                         content: "Unesi email i korisničko ime.",
                         type: "info",
                         opacity: 0.1,
                         buttons: [{ value: "Ok" }]
                     });
                 }
            });
        },
        hidePasswordFields: function () {

            hidePasswordFields()
        } ,
        disableFields: function () {
            disableFields()
        }
    }
    var setHelpText = function (path,tooltip) {
        $(path).popover({trigger:'hover', placement:'right', content:tooltip, html:true });
    }
    return{
        setUserValues: function (subLab) {
            $('#user.subLab').val(subLab).attr("selected", "selected");

        },
        setHelpText: function () {
            setHelpText('label[for="user.subLab"]',"Unesite ambulantu korisnika.");
        }
    }
}();
