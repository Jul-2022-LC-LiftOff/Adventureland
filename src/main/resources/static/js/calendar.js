var userQuantity;
var totalEquipmentInv;
var reservedDatesList;
var equipQty;
var listOfDates = [];
var newDates = [];
var originalQuantity = $("#originalReservationQuantity").val();
var originalDate = $("#originalReservationDate").val();
var currentReservationStatus = $("#reservationStatus").val();

    $(document).ready(function(){
        newDates = [];
        totalEquipmentInv = $("#totalEquipmentInv").val();
        $("#button").on('click', function () {
            newDates = [];
            userQuantity = $("#equipmentQuantity").val();
            totalEquipmentInv = $("#totalEquipmentInv").val();
            reservedDatesList = $("#reservedDatesList").val();
            var reservedDatesList = createArrayObject();
    //        $("#dateReserved").datepicker( "enabled");
    //        $("#dateReserved").datepicker("show");
        });

        //  Datepicker to show on screen.
        //    $("#dateReserved").datepicker("destroy");
        $("#dateReserved").datepicker({
            // Function disableDates is to evaluate the Dates shown on the calendar to do stuff with each date
            minDate: 0,
            maxDate: 365,
            beforeShowDay: disableDates,
            onUpdateDatepicker: afterUpdatingDatepicker,
            //onSelect: SelectedDate,
            onClose: clearCalendar
            });

        function afterUpdatingDatepicker() {
            newDates = [];
            listOfDates = [];
        };

        function clearCalendar() {
            newDates = [];
            listOfDates = [];
        };

        //    function SelectedDate() {
        //        newDates = [],
        //        listOfDates = []
        //    }

        // Disabled dates
        function disableDates(date) {

                // if statement validates whether the newDates array is empty
                // reason is because the .datepicker.formatdate method LOOPS
                // and moves the cursor to the top of the function and runs the entire
                // function again.

                    if (newDates.length <= 0 ) {

                        var reservedDatesList = createArrayObject();
                            // var dates = createDateObject();
                            newDates = createArrayofDates(reservedDatesList);
                            if (totalEquipmentInv - userQuantity < 0) {
                                alert("No Equipment available")
                                //newDates = []
                                //$("#dateReserved").datepicker("disabled");
                                return [false];
                            }
                    }

                    var string = $.datepicker.formatDate('mm/dd/yy', date);
                    return [newDates.indexOf(string) == -1];

                  }

        // the create array object function establishes an Array object as Key Value Pairs
        // ie Key: date = value: quantity
        //
        function createArrayObject() {
            newDates = []
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

        // the create date object function establishes an Array object as Key Value Pairs
        //this one creates an array as:
        // ie Key: date = value: date
        //
        function createDateObject() {

                  var dateList = reservedDatesList
                    .trim()
                    .slice(1, -1)
                    .split(',')
                    .reduce(function(obj, v) {
                      var val = v.trim().split('=');
                      // define object property
                      obj[val[0]] = val[0];
                      // return object reference
                      return obj;
                      // set initial parameter as empty object
                    }, {});

                    return dateList;
          }

        function createArrayofDates(lDates){
                userQuantity = $("#equipmentQuantity").val();
                totalEquipmentInv = $("#totalEquipmentInv").val();

                $.each(lDates, function(key, value) {
                   var currentlyReservedQty = value
                   var newReserveQty = parseFloat(currentlyReservedQty) + parseFloat(userQuantity);
                   var availableEquipment = parseFloat(totalEquipmentInv) - parseFloat(newReserveQty);

                   if (currentReservationStatus == "Pending-Edit"){
                       if (originalDate == key) {
                          availableEquipment = parseFloat(availableEquipment) + parseFloat(originalQuantity);
                      }
                   }
                   if (availableEquipment < 0) {
                   // dates added here will show as disabled in calendar.
                   listOfDates.push(key);
                   }
                });
                return listOfDates;
        }

        // Function to  If a customer selects a date first and then changes the quantity, the customer is not prevented from adding more items to the cart than what is available
        //   Researching how to hide the calendar when the “Equipment Not Available” alert is triggered

    });
