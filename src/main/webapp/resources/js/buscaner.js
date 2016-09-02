function enableDateEndField() {
    if ($('#scanForPeriod').prop("checked")) {
        $('#departureDateEnd').removeAttr('disabled');
    }
    else {
        $('#departureDateEnd').attr('disabled', true);
        $('#departureDateEnd').val('');
    }
}

function initDatePickers() {
    $('#datepicker').datepicker({
        "setDate": new Date(),
        "autoclose": true
    });
    $('#datepickerEnd').datepicker({
        "setDate": new Date(),
        "autoclose": true
    });

}


function initFormOnLoad() {
    if (localStorage.getItem('requestForm.fromCity') != null) {
        $('#fromCity').val(localStorage.getItem('requestForm.fromCity'));
        $('#toCity').val(localStorage.getItem('requestForm.toCity'));
        $('#departureDate').val(localStorage.getItem('requestForm.departureDate'));
        $('#departureDateEnd').val(localStorage.getItem('requestForm.departureDateEnd'));
        if (localStorage.getItem('requestForm.scanForPeriod') == 'true') {
            $('#scanForPeriod').prop("checked", true);
            enableDateEndField();
        }

    }


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