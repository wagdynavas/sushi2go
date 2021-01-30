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
                input.val(1);
            }
        }
    });


    $(".btn-close").on('click', function () {
        let fieldName = $(this).attr('data-field');
        let input = $("." + fieldName);

        input.val(1);
    });


    $(document).ready(function () {
        $("#sidebar").mCustomScrollbar({
            theme: "minimal"
        });

        $('.dismiss, .overlay').on('click', function () {
            $('#sidebar').removeClass('active');
            $('.overlay').removeClass('active');
        });

        $('#sidebarCollapse').on('click', function () {
            $('#sidebar').addClass('active');
            $('.overlay').addClass('active');
            $('.collapse.in').toggleClass('in');
            $('a[aria-expanded=true]').attr('aria-expanded', 'false');
        });
    });


    $(document).ready(function () {
        // Add smooth scrolling to all links
        $("a").on('click', function (event) {

            // Make sure this.hash has a value before overriding default behavior
            if (this.hash !== "") {
                // Prevent default anchor click behavior
                event.preventDefault();

                // Store hash
                var hash = this.hash;

                // Using jQuery's animate() method to add smooth page scroll
                // The optional number (800) specifies the number of milliseconds it takes to scroll to the specified area
                $('html, body').animate({
                    scrollTop: $(hash).offset().top
                }, 800, function () {

                    // Add hash (#) to URL when done scrolling (default click behavior)
                    window.location.hash = hash;
                });
            } // End if
        });
    });

    $(window).bind("scroll", function () {
        if ($(window).scrollTop() > 300) {
            $('.menu-btn').addClass('sub-menu-top');
        } else {
            $('.menu-btn').removeClass('sub-menu-top');
        }
    });
});