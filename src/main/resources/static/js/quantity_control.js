$(document).ready(function() {
    $(".minusButton").on ("click", function(evt) {
        evt.preventDefault();
        equipmentId = $(this).attr("eid");
        qtyInput = $("#quantity" + equipmentId);

        newQty = parseInt(qtyInput.val()) - 1;
        if (newQty > 0) qtyInput.val(newQty)
        $("#equipmentQuantity").val(newQty);

    });

     $(".plusButton").on ("click", function(evt) {
            evt.preventDefault();
            equipmentId = $(this).attr("eid");
            qtyInput = $("#quantity" + equipmentId);
            reservationqty = $("#equipmentQuantity")
            newQty = parseInt(qtyInput.val()) + 1;
            totalEquipmentInv = $("#totalEquipmentInv").val();

            if (newQty <= totalEquipmentInv) {
            qtyInput.val(newQty)
             reservationqty.val(newQty);
            } else {
            alert("There is not enough equipment to add more to your reservation");
            }
        });
        });