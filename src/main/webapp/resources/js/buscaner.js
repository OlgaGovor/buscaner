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


function initFormOnLoad() {

    if (localStorage.getItem('requestForm.fromCity') != null) {
        $('#fromCity').val(localStorage.getItem('requestForm.fromCity'));
        $('#toCity').val(localStorage.getItem('requestForm.toCity'));
        setDatePickerValue("departureDate", localStorage.getItem('requestForm.departureDate'));

        if (localStorage.getItem('requestForm.scanForPeriod') == 'true') {
            $('#scanForPeriod').prop("checked", true);
            enableDateEndField();
        }

    }


}
function toDate(dateStr) {
    var parts = dateStr.split("-");
    var date = new Date(parts[2], parts[1] - 1, parseInt(parts[0]) + 1);
    return date;

}

function setDatePickerValue(datePicker, value) {
    if (value != null && value != '' && value.trim() != '') {

        $('#' + datePicker).datepicker('update', toDate(value));
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