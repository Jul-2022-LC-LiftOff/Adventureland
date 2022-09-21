var Total = $("#reservedTotal").val()
 $(document).ready(function(){
     $("#btnCompleteReservation").on('click', function () {
//        Total = $("#reservedTotal").val().toString();
        if (Total == 0) {
           alert("There is nothing to Reserve. Add Equipment to the cart to make a reservation.");
           event.preventDefault();
           return false;
        };
    });
 });