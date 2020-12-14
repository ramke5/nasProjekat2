/**
 * Created with IntelliJ IDEA.
 * User: dev5
 * Date: 1/8/13
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */

var Lab = function () {

    var hidePasswordFields = function () {
        $("input[name='systemGeneratedPassword']").on('change', function () {
            if ($(this).is(":checked")) {
                $('#passwordHide').hide();
                $('#passwordHideEdit').hide();
                $("#welcomeEmail1").prop('checked', true);
                $("#changePassword1").prop('checked', false);
                var password =  $('#form').find('input[name="user.password"]') ;
                password.val('');
                $('#confirmPassword').val('');

            }
            else {
                $('#passwordHideEdit').hide();
                $("#welcomeEmail1").prop('checked', false);

                $('#passwordHide').show();

            }
        });
    };
    var setInputMask = function () {

        $(".zip").inputmask("99999");
        $(".decimal").autoNumeric('init', {vMin: '-999999999999.99', vMax: '999999999999.99'});
        $(".integer").autoNumeric('init', {vMin: '-999999999999', vMax: '999999999999'});

        $(".year").inputmask('y');
        $(".month").inputmask("m");
        $(".daycredit").inputmask("d");
        $(".creditcardnumber").inputmask({ "mask": "9", "repeat": 16, "greedy": false });
        $(".cvv").inputmask({ "mask": "9", "repeat": 4, "greedy": false });
        $(".accountnumber").inputmask({ "mask": "9", "repeat": 20, "greedy": false });
        $(".routingnumber").inputmask({ "mask": "9", "repeat": 20, "greedy": false });
    };

    var removeInputMask = function () {
        $(".decimal").each(function () {
            var value = $(this).val();
            value = value.split(',').join('');
            $(this).val(value);
        });
        $(".integer").each(function () {
            var value = $(this).val();
            value = value.split(',').join('');
            $(this).val(value);
        })
    };

    return{
        init: function () {
             jQuery('#same_as_above').click(function () {
                var state;
                var bFirstName = $('#form').find('input[name="billingContact.firstName"]');
                var bLastName = $('#form').find('input[name="billingContact.lastName"]');
                var bOfficePhone = $('#form').find('input[name="billingContact.officePhone"]');
                var bMobileNo = $('#form').find('input[name="billingContact.mobileNumber"]');
                var bEmail = $('#form').find('input[name="billingContact.email"]');
                var bAddress1 = $('#form').find('input[name="billingContact.address1"]');
                var bAddress2 = $('#form').find('input[name="billingContact.address2"]');
                var bCity = $('#form').find('input[name="billingContact.city"]');
                var bState = $('#form').find('select[name="billingContact.state"]');
                var bPostal = $('#form').find('input[name="billingContact.postalCode"]');

                if ($(this).is(':checked')) {
                    bFirstName.val($('#form').find('input[name="contact.firstName"]').val())
                    bFirstName.attr("readonly", "readonly")
                    bLastName.val($('#form').find('input[name="contact.lastName"]').val())
                    bLastName.attr("readonly", "readonly")
                    bOfficePhone.val($('#form').find('input[name="contact.officePhone"]').val())
                    bOfficePhone.attr("readonly", "readonly")
                    bMobileNo.val($('#form').find('input[name="contact.mobileNumber"]').val())
                    bMobileNo.attr("readonly", "readonly")
                    bEmail.val($('#form').find('input[name="contact.email"]').val())
                    bEmail.attr("readonly", "readonly")
                    bAddress1.val($('#form').find('input[name="contact.address1"]').val())
                    bAddress1.attr("readonly", "readonly")
                    bAddress2.val($('#form').find('input[name="contact.address2"]').val())
                    bAddress2.attr("readonly", "readonly")
                    bCity.val($('#form').find('input[name="contact.city"]').val())
                    bCity.attr("readonly", "readonly")
                    bState.select2('val',$('#form').find('select[name="contact.state"]').val());

                    bPostal.val($('#form').find('input[name="contact.postalCode"]').val())
                    bPostal.attr("readonly", "readonly")
                } else {
                    bFirstName.attr("readonly", false)
                    bLastName.attr("readonly", false)
                    bOfficePhone.attr("readonly", false)
                    bMobileNo.attr("readonly", false)
                    bEmail.attr("readonly", false)
                    bAddress1.attr("readonly", false)
                    bAddress2.attr("readonly", false)
                    bCity.attr("readonly", false)
                    bState.attr("readonly", false)
                    bPostal.attr("readonly", false)
                }
            });

        },
        setInputMask: function () {
            setInputMask()
        },

        hidePasswordFields: function () {

            hidePasswordFields()
        }
    }
}();
