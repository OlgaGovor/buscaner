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
    alert('sortResultList ' + sortBy + ' ' + sortOrder);
}