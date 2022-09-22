

//var userSelectedDate;
 $("#btnSubmit").on('click',function() {


//-------------The below block of code is trying to change the text on the Add To Cart button to read
//-------------Save Changes if reservation is being edited
//  var reservationStatus = ($"{reservation.reservationStatus}").val();
//      if (reservationStatus == "Pending-Edit"){
//      btnSubmit.val("Save Changes");
//      } else if (reservationStatus =="Pending-Checkout"){
//      btnSubmit.val("Add To Cart");
//      }

    if ( !$("#dateReserved").val() ) {
       alert("Please select a reservation date.");
       event.preventDefault();
       return false;
    }
    else {
        var userSelectedDate = $("#dateReserved").val();
        var inventoryQty = $("#totalEquipmentInv").val();
        var userQty = $("#equipmentQuantity").val();
        var bookedQty = getBookedQty(userSelectedDate);
        var availableQty = parseFloat(inventoryQty) - parseFloat(bookedQty);
        var remainingQtyCheck = parseFloat(availableQty) - parseFloat(userQty);

            if (remainingQtyCheck < 0) {
                alert("Selected quantity is greater than available equipment on selected date.");
                event.preventDefault();
                return false;
            }
    }


});


    function getBookedQty(userSelectedDate) {
//        userSelectedDate = $("#dateReserved").val();
        var resDateList = createArrayObject();
        var returnQty = 0;
            $.each(resDateList, function(key, value) {
                if(key == userSelectedDate) {
                returnQty = parseFloat(returnQty) + parseFloat(value);
                }
            });
        return returnQty;
    }

    function createArrayObject() {
        reservedDatesList = $("#reservedDatesList").val();
        var res = reservedDatesList
        .trim()
        .slice(1, -1)
        .split(',')
        .reduce(function(obj, v) {
         var val = v.trim().split('=');
         // define object property
         obj[val[0]] = val[1];
         // return object reference
         return obj;
         // set initial parameter as empty object
        }, {});
        return res;
    }







//$(document).ready(function() {
//    $("#AddToCart").on("click", function(e) {
////        alert("Add to cart");
//            addToCart();
//    });
//});
//
//function addToCart() {
//quantity = $("#quantity" + equipmentId).val();
//}
//
//url = contextPath + "cart/"
//$.ajax({
//type: "POST",
////need url
////url: "http://"
//beforeSend: function(xhr) {
//xhr.setRequestHeader(crsfHeaderName, csrfValue)
//}
//}).done(function(response) {

//});
//}