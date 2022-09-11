var userQuantity;
var totalEquipmentInv;
var reservedDatesList;
var equipQty;
var listOfDates = [];
var newDates = [];

  $(document).ready(function(){

        totalEquipmentInv = $("#totalEquipmentInv").val();
    $("button").on('click', function () {
    newDates = [];
        userQuantity = $("#equipmentQuantity").val();
        totalEquipmentInv = $("#totalEquipmentInv").val();

//      ArrayString conversion
        reservedDatesList = $("#reservedDatesList").val();
//        Example: var str = "{one=Print, two=Email, three=Download, four=Send to Cloud}";


// the below statement is a function created where a string array is converted to a jquery array object
    var reservedDatesList = createArrayObject();

//  Calls the DatePicker Function, and shows the calendar
        $("#dateReserved").datepicker("show");

    });

//  Datepicker to show on screen.
    $("#dateReserved").datepicker({
// Function disableDates is to evaluate the Dates shown on the calendar to do stuff with each date
       minDate:0, maxDate:365,
        beforeShowDay: disableDates
      });

// Disabled dates
      function disableDates(date) {
            // if statement validates whether the newDates array is empty
            // reason is because the .datepicker.formatdate method LOOPS
            // and moves the cursor to the top of the function and runs the entire
            // function again.
                if (newDates.length <= 0) {
                var reservedDatesList = createArrayObject();
//                var dates = createDateObject();
                    newDates = createArrayofDates(reservedDatesList);

                }
                var string = $.datepicker.formatDate('mm/dd/yy', date);
//                for(var key in dates) {
//                            alert('key: ' + key + '\n' + 'value: ' + dates[key]);
//                        }
                return [newDates.indexOf(string) == -1];

              }

// the create array object function establishes an Array object as Key Value Pairs
// ie Key: date = value: quantity
//
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
                       if (availableEquipment < 0) {
                       listOfDates.push(key);
                       }
                    });
                    return listOfDates;
            }
  });
//
////  ------------------------------------------------------------------------------------------------
////  ------------------------------------------------------------------------------------------------
//
//
//// Alert
////$(document).ready(function($){
////      alert("js is working");
////      });
////  });





////  ------------------------------------------------------------------------------------------------
//Sample Code:
////  ------------------------------------------------------------------------------------------------


//testing .each to loop through the array to capture the elements in an array
////        JQuery.each(dateres1, function(key, element) {
////            alert('key: ' + key + '\n' + 'value: ' + element);
////        });
////  ------------------------------------------------------------------------------------------------
////  ------------------------------------------------------------------------------------------------
// testing for to loop through and capture the Key value of the array
////        for(var key in realArray) {
////            alert('key: ' + key + '\n' + 'value: ' + realArray[key]);
////        }
////  ------------------------------------------------------------------------------------------------
////  ------------------------------------------------------------------------------------------------
//below extracts the array information from string based on Key and Value format
//// remove the space at start and end
//reservedDatesList
//  .trim()
//  // get string without `{` and `}`
//  .slice(1, -1)
//  // split by `,`
//  .split(',')
//  // iterate over the array
//  .forEach(function(v) {
//    // split by `=`
//    var val = v.trim().split('=');
//    alert('key : ' + val[0] + ", value : " + val[1]);
//  })
////  ------------------------------------------------------------------------------------------------
////  ------------------------------------------------------------------------------------------------
//  $("#dateReserved").datepicker({
//            beforeShowDay: function(date)
//            {
//               // this is a look in jquery structure that goes through each date and
//               // with the if logic disables that date in the calendar form being selected.
//               var day = date.getDay();
//               if (equipQty == 1 && day == 6)
//               {return [false];
//               }
//               else
//               {return [true];
//               }
//});
