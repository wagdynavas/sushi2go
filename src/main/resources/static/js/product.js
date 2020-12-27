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

            } else if (type == "plus") {
                if(currentVal < input.attr('max')) {
                    input.val(currentVal + 1).change();
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


    $(window).bind("scroll", function () {
        if ($(window).scrollTop() > 300) {
            $('.sub-menu').addClass('sub-menu-top');
        } else {
            $('.sub-menu').removeClass('sub-menu-top');
        }
    });


    $(".btn-lunch").on("click", function () {
        $(".menu-lunch").removeClass("hide-menu");
        $(".menu-dinner").addClass("hide-menu");
        $(".btn-dinner").removeClass("active");
        $(".btn-lunch").addClass("active");
    })

    $(".btn-dinner").on("click", function () {
        $(".menu-dinner").removeClass("hide-menu");
        $(".menu-lunch").addClass("hide-menu");
        $(".btn-dinner").addClass("active");
        $(".btn-lunch").removeClass("active")
    });


});