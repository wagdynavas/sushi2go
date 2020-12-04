$(document).ready(function(){
    $(document).on('click','.add-remove-btn',function(){
        let fieldName = $(this).attr('data-field');
        let type      = $(this).attr('data-type');
        let input = $("." + fieldName);
        let currentVal = parseInt(input.val());
        if (!isNaN(currentVal)) {
            if (type == "minus") {
                if (currentVal > input.attr("min")) {
                    input.val(currentVal - 1).change();
                }

                if(parseInt(input.val()) == input.attr('min')) {
                    $(this).attr('disabled', true);
                }
            } else if (type == "plus") {
                if(currentVal < input.attr('max')) {
                    input.val(currentVal + 1).change();
                    $(this).attr('disabled', false);
                }
                if(parseInt(input.val()) == input.attr('max')) {
                    $(this).attr('disabled', true);
                }
            } else {
                input.val(0);
            }
        }
    });


    $(".btn-close").on('click', function () {
        let fieldName = $(this).attr('data-field');
        let input = $("." + fieldName);

        input.val(0);
    })



});