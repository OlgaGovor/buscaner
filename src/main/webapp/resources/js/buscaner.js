function enableDateEndField() {
    if ($('#scanForPeriod').prop("checked")) {
        $('#departureDateEnd').removeAttr('disabled');
        setDatePickerValue("departureDateEnd", localStorage.getItem('requestForm.departureDateEnd'));
    }
    else {
        $('#departureDateEnd').attr('disabled', true);
        $('#departureDateEnd').val('');
    }
}

function initDatePickers() {
    $('#departureDate').datepicker({
        setDate: new Date(),
        autoclose: true,
        format: 'dd-mm-yyyy'
    });
    $('#departureDateEnd').datepicker({
        setDate: new Date(),
        autoclose: true,
        format: 'dd-mm-yyyy'
    });


}

function initSelectpickers(){
$('.selectpicker').selectpicker({
                // style: 'btn-outline-info',
                 size: 15,
                 liveSearch: true
               });

$('#fromCity').change(function () {

            loadRoutes($('#toCity'), $('#fromCity').val(), 'dep');

    });
  /*  $('#toCity').change(function () {

            loadRoutes($('#fromCity'), $('#toCity').val(), 'dst');

    });*/
}


function initFormOnLoad() {

    if (localStorage.getItem('requestForm.fromCity') != null) {
        $('#fromCity').selectpicker('val', localStorage.getItem('requestForm.fromCity'));
        $('#toCity').selectpicker('val',localStorage.getItem('requestForm.toCity'));
        setDatePickerValue("departureDate", localStorage.getItem('requestForm.departureDate'));

        if (localStorage.getItem('requestForm.scanForPeriod') == 'true') {
            $('#scanForPeriod').prop("checked", true);
            enableDateEndField();
        }

    }


}
function toDate(dateStr) {
    var parts = dateStr.split("-");
    var date = new Date(parts[2], parts[1] - 1, parseInt(parts[0]));
    return date;
}

function setDatePickerValue(datePicker, value) {
    if (value != null && value != '' && value.trim() != '') {
        $('#' + datePicker).datepicker('setDate', toDate(value));
    }

}

function searchData() {

    var form = $('#requestForm');
    var url = form.attr("action");
    var formData = $(form).serializeArray();
    $.post(url, formData).done(function (data) {
        $('#resultTable').html(data);
    });

}

function updateData() {
    var url = '/updateData';

    var form = $('#requestForm');
    //var url = form.attr("action");
    var formData = $(form).serializeArray();
    $.post(url, formData).done(function (data) {
        $('#resultTable').html(data);
    });

}


function sortResultList(sortBy, sortOrder) {

    var list = $("#resulList").find(".list-group-item");

if(sortBy == "priceVal" && sortOrder == "ASC"){
    list.sort(compareByPrice);
    }
else if(sortBy == "priceVal" && sortOrder == "DESC"){
        list.sort(compareByPriceDESC);
        }
else if(sortBy == "timeVal"  && sortOrder == "ASC"){
    list.sort(compareByTime);
    }
else if(sortBy == "timeVal"  && sortOrder == "DESC"){
    list.sort(compareByTimeDESC);
    }

    $("#resulList").html(list);
    }



function compareByPrice (a, b){
    a = $(a).attr("priceVal");
    b = $(b).attr("priceVal");

return (a - b);

}

function compareByPriceDESC (a, b){
    a = $(a).attr("priceVal");
    b = $(b).attr("priceVal");

return (b - a);

}


function compareByTime (a, b){
    a = $(a).attr("timeVal");
    var splA = a.split(":");
    var dtA = new Date();
    dtA.setHours(splA[0]);
    dtA.setMinutes(splA[1]);

    b = $(b).attr("timeVal");
    var splB = b.split(":");
        var dtB = new Date();
        dtB.setHours(splB[0]);
        dtB.setMinutes(splB[1]);
     return (dtA - dtB);
}

function compareByTimeDESC (a, b){
    a = $(a).attr("timeVal");
       var splA = a.split(":");
       var dtA = new Date();
       dtA.setHours(splA[0]);
       dtA.setMinutes(splA[1]);

       b = $(b).attr("timeVal");
       var splB = b.split(":");
           var dtB = new Date();
           dtB.setHours(splB[0]);
           dtB.setMinutes(splB[1]);
        return (dtB - dtA);
}

function buyTicket(link) {
    alert(link);
}

function loadRoutes(container, value, depDst) {
    var url = '/loadRoutes';

    $.post(url, {'cityId': value, 'depDst': depDst})
        .done(function (data) {
        $(container).empty();
            var i = 0;

var opt = document.createElement("option");
                opt.value = "";
                opt.style ="hidden";
                opt.innerHTML = " ";
                $(container).append(opt);

            for (; i < data.length; i++) {

                var opt = document.createElement("option");
                opt.value = data[i].cityId;
                opt.innerHTML = data[i].cityName;
                $(container).append(opt);
            }
        $(container).selectpicker('refresh');
        $(container).selectpicker('toggle');

        });

}