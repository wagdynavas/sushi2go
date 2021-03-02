$(document).ready(function () {
    $(".btn-location").change(function () {
        let restaurantBranch = $('input[name="locations"]:checked').val();
        let queensway = $('#queensway').addClass('hidden');
        let dundas = $('#dundas').addClass('hidden');
        let royal_york =$('#royal-york').addClass('hidden');

        switch (restaurantBranch) {
            case "QUEENSWAY" : {
                queensway.removeClass("hidden");3
                break;
            } case "DUNDAS" : {
                dundas.removeClass("hidden");
                break;
            } case "ROYAL_YORK" : {
                royal_york.removeClass("hidden");
                break;
            } default :{

            }
        }
    });


    $("#btn-delivery").on("click", function () {
        $("#btn-pickup").removeClass("active");
        $("#btn-delivery").addClass("active");
        setDeliveryType("delivery");
    });

    $("#btn-pickup").on("click", function () {
        $("#btn-pickup").addClass("active");
        $("#btn-delivery").removeClass("active");
        setDeliveryType("pickup");
    });

    function setDeliveryType(deliveryType) {
        const delivery = "delivery";
        let delivery_wrapper = $('#delivery-wrapper').addClass('hidden');
        let pickup_wrapper = $('#pickup-wrapper').addClass('hidden');
        if (deliveryType == delivery) {
            delivery_wrapper.removeClass("hidden");
        } else {
            pickup_wrapper.removeClass("hidden");
        }
    }
});