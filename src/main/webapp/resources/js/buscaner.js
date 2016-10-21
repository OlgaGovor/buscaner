function initForm() {
    setupEnv();
    initDatePickers();
    initSelectpickers();
    initFormOnLoad();

    $('#scanForPeriod').click(function () {
        initDateEndField();
        if ($('#scanForPeriod').prop("checked")) {
            $('#departureDateEnd').datepicker("show");
        }
    });

    $('#searchButton').click(function () {
        saveFormDataOnSearch();
        $('#dateSlider').empty();
        $('#resultTable').empty();
        searchData();
    });
    $("#swapCities").click(function () {
        swapCities();
    });
}

function initDateEndField() {
    if ($('#scanForPeriod').prop("checked")) {
        $('#departureDateEnd').removeAttr('disabled');
        setDatePickerValue("departureDateEnd", strToDate(localStorage.getItem('requestForm.departureDateEnd')));
        var date = strToDate($('#departureDate').val());
        date.setMonth(date.getMonth() +1);
        $('#departureDateEnd').datepicker('setStartDate', strToDate($('#departureDate').val()));
        $('#departureDateEnd').datepicker('setEndDate', date);
    }
    else {
        $('#departureDateEnd').attr('disabled', true);
        $('#departureDateEnd').val('');
    }
}

function initDatePickers() {
    $('#departureDate').datepicker({
        autoclose: true,
        format: 'dd-mm-yyyy',
        startDate: "today",
        language: language,
        weekStart: 1,
        todayHighlight: true
    });
    $('#departureDateEnd').datepicker({
        autoclose: true,
        format: 'dd-mm-yyyy',
        startDate: "today",
        language: language,
        weekStart: 1,
        todayHighlight: true
    });

    $("#departureDate").datepicker('setDate', new Date());
    $("#departureDateEnd").datepicker('setDate', new Date());

    $("#departureDate").on("changeDate", function (e) {

        if ($('#scanForPeriod').prop("checked")) {
            initDateEndField();
            $('#departureDateEnd').datepicker("show");
            }
    });

  /*  $("#departureDateEnd").on("changeDate", function (e) {
        $('#departureDate').datepicker('setEndDate', e.date);
    });*/
}

function initSelectpickers() {
    $('.selectpicker').selectpicker({
        size: 15,
        liveSearch: true
    });

    $('#fromCity').change(function () {
        loadRoutes($('#toCity'), $('#fromCity').val(), 'dep');
    });
    $("#langSelect").change(function () {
        changeLang($("#langSelect").val());
    });
    /*  $('#toCity').change(function () {

     loadRoutes($('#fromCity'), $('#toCity').val(), 'dst');

     });*/
}

function changeLang(newLang) {
    var url = document.URL;
    var re = /(lang=)\w\w/gi;
    if (url.indexOf('lang=') != -1) {
        url = url.replace(re, 'lang=' + newLang);
    }
    else {
        url += '?lang=' + newLang;
    }

    window.location = url;
}

function swapCities() {
    var tmp = $('#toCity').val();
    $('#toCity').selectpicker('val', $('#fromCity').val());
    $('#fromCity').selectpicker('val', tmp);
}


function initFormOnLoad() {

    if (localStorage.getItem('requestForm.fromCity') != null) {
        $('#fromCity').selectpicker('val', localStorage.getItem('requestForm.fromCity'));

        $('#toCity').selectpicker('val', localStorage.getItem('requestForm.toCity'));
        // loadRoutes($('#toCity'), $('#fromCity').val(), 'dep');

        setDatePickerValue("departureDate", strToDate(localStorage.getItem('requestForm.departureDate')));

        if (localStorage.getItem('requestForm.scanForPeriod') == 'true') {
            $('#scanForPeriod').prop("checked", true);
        }
    }
    initDateEndField();
}

function saveFormDataOnSearch() {
    if (localStorage) {
        localStorage.setItem('requestForm.fromCity', $('#fromCity').val());
        localStorage.setItem('requestForm.toCity', $('#toCity').val());
        localStorage.setItem('requestForm.departureDate', $('#departureDate').val());
        if ($('#scanForPeriod').prop("checked")) {
            localStorage.setItem('requestForm.departureDateEnd', $('#departureDateEnd').val());
        }
        localStorage.setItem('requestForm.scanForPeriod', $('#scanForPeriod').prop("checked"));
    }
}
function strToDate(dateStr) {
    if (dateStr == null || dateStr == '') {
        return null;
    }
    var parts = dateStr.split("-");
    var date = new Date(parts[2], parts[1] - 1, parseInt(parts[0]));
    return date;
}

function dateToStr(date) {
    function pad(s) {
        return (s < 10) ? '0' + s : s;
    }

    var month = date.getMonth() + 1;
    return pad(date.getDate()) + "-" + pad(month) + "-" + date.getFullYear();
}

function setDatePickerValue(datePicker, date) {
    if (date != null) {
        var today = new Date();
        today.setHours(0, 0, 0, 0)
        if (date < today) {
            date = today;
        }
        $('#' + datePicker).datepicker('setDate', date);
    }
}

function loadData(requestForm, url, callback) {
    $('#messageBox').empty();
    $('#messageBox').hide();

    $.post(url, requestForm).done(function (data) {
        callback(data);
    });

}

function searchDataByForm(requestForm) {
    $('#resultTable').empty();
    var url = $('#requestForm').attr("action");
    var callback = function (data) {
        $('#resultTable').html(data);
        if (!$('#scanForPeriod').prop("checked")) {
            fillDateSlider();
        }
    };
    loadData(requestForm, url, callback);


}

function searchData() {
    var requestForm = $('#requestForm').serializeArray();
    searchDataByForm(requestForm);
}

function fillDateSlider() {
    var daysRange = 6;
    $('#dateSlider').empty();

    var form = $('#requestForm');
    var url = "/loadDateSlider";
    var formData = {};
    formData["fromCity"] = $('#fromCity').val();
    formData["toCity"] = $('#toCity').val();
    formData["scanForPeriod"] = true;

    var departureDate = strToDate($('#departureDate').val());


    departureDate = getFirstDateForSlider(departureDate, daysRange);
    var departureDateEnd = new Date(departureDate.getTime());
    departureDateEnd.setDate(departureDateEnd.getDate() + daysRange);

    formData["departureDate"] = dateToStr(departureDate);
    formData["departureDateEnd"] = dateToStr(departureDateEnd);

    var callback = function (data) {
        $('#dateSlider').html(data);
        updateSliderCurrentDate($('#dateSlider'), $('#departureDate').val());
        updateSliderWeekDays($('#dateSlider'));
    };

    loadData(formData, url, callback);

}
/*calculates the first date for date slider depends on current date*/
function getFirstDateForSlider(selectedDate, daysRange) {
    var firstDate = new Date(selectedDate);
    firstDate.setDate(selectedDate.getDate() - (Math.floor(daysRange / 2)));

    var today = new Date();
    today.setHours(0, 0, 0, 0)
    while (firstDate < today) {
        firstDate.setDate(firstDate.getDate() + 1);
    }
    return firstDate;
}
/*change color of slider block that represents selected in filter date*/
function updateSliderCurrentDate(sliderContainer, selectedDate) {
    $(sliderContainer).find("div.date-slider-block[departuredate='" + selectedDate + "']").addClass("date-slider-block-selected-date");
}

/*set names of week days to ech dateSlider block*/
function updateSliderWeekDays(sliderContainer) {
    var weekDay = $.fn.datepicker.dates[language].daysMin; //values from bootstrap-datetimepicker.LANG.js
    var blocks = $(sliderContainer).find(".date-slider-block");
    for (var i = 0; i < blocks.length; i++) {
        var date = strToDate($(blocks[i]).attr("departureDate"));
        var dayName = weekDay[date.getDay()];
        $(blocks[i]).find(".week-day-name").text(dayName);
    }
}


function displayMessage(message) {
    $("#messageBox").text(message);
    $("#messageBox").show();
}
function switchToResultList(date) {
    setDatePickerValue("departureDate", strToDate(date));
    $('#scanForPeriod').prop("checked", false);
    initDateEndField();
    searchData();
}
function switchToResultCalendar(date) {
    $('#scanForPeriod').prop("checked", true);
    initDateEndField();
    var date = strToDate(date);
    var startDate = getFirstAllowedDayOfMonth(date.getFullYear(), date.getMonth());
    var endDate = getLastDayOfMonth(date.getFullYear(), date.getMonth());
    setDatePickerValue("departureDate", startDate);
    setDatePickerValue("departureDateEnd", endDate);

    $('#dateSlider').empty();
    searchData();
}

function onDateSliderClick(div) {

    console.log($(div).attr("departureDate"));
    $('#departureDate').datepicker('setDate', strToDate($(div).attr("departureDate")));
    searchData();

}
/*

 function fillDateSliderAjax() {
 var daysRange = 5;

 var form = $('#requestForm');
 var url = form.attr("action");
 var formData = {};
 formData["fromCity"] = $('#fromCity').val();
 formData["toCity"] = $('#toCity').val();
 formData["scanForPeriod"] = true;

 var departureDate = strToDate($('#departureDate').val());
 var departureDateEnd = new Date(departureDate.getTime());
 departureDate.setDate(departureDate.getDate() - daysRange);
 departureDateEnd.setDate(departureDateEnd.getDate() + daysRange);

 formData["departureDate"] = dateToStr(departureDate);
 formData["departureDateEnd"] = dateToStr(departureDateEnd);

 jQuery.ajax({
 url: url,
 type: "POST",
 data: JSON.stringify(formData),
 contentType: "application/json",
 dataType : 'json',
 success: function (data) {
 buildDateSlider(data);
 }
 });
 }

 function buildDateSlider(data){

 for (var el, i = 0; i < data.length; i++) {
 var dayBlock= document.createElement('div');
 dayBlock.className='col-lg-1';
 dayBlock.innerHTML = data[i].departureDate + " "+data[i].price;
 $("#dateslider").append(dayBlock);

 }



 }
 */


function updateData() {
    var url = '/updateData';

    var form = $('#requestForm');
    //var url = form.attr("action");
    var formData = $(form).serializeArray();
    $.post(url, formData).done(function (data) {
        $('#resultTable').html(data);
        fillDateSlider();
    });

}

function getForm() {

    var formData = {
        fromCity: $('#fromCity').val(),
        toCity: $('#toCity').val(),
        departureDate: $('#departureDate').val(),
        departureDateEnd: $('#departureDateEnd').val(),
        scanForPeriod: $('#scanForPeriod').prop("checked")
    }

    return formData;

}

function setForm(formData) {
    console.log(formData);
    $('#fromCity').selectpicker('val', formData.fromCity);
    $('#toCity').selectpicker('val', formData.toCity);
    setDatePickerValue("departureDate", strToDate(formData.departureDate));
    if (formData.scanForPeriod == 'true') {
        $('#scanForPeriod').prop("checked", true);
        setDatePickerValue("departureDateEnd", strToDate(formData.departureDateEnd));
    }
}


function sortResultList(sortBy, sortOrder) {

    var list = $("#resulList").find(".list-group-item");

    if (sortBy == "priceVal" && sortOrder == "ASC") {
        list.sort(compareByPrice);
    }
    else if (sortBy == "priceVal" && sortOrder == "DESC") {
        list.sort(compareByPriceDESC);
    }
    else if (sortBy == "timeVal" && sortOrder == "ASC") {
        list.sort(compareByTime);
    }
    else if (sortBy == "timeVal" && sortOrder == "DESC") {
        list.sort(compareByTimeDESC);
    }

    $("#resulList").html(list);
}


function compareByPrice(a, b) {
    a = $(a).attr("priceVal");
    b = $(b).attr("priceVal");

    return (a - b);

}

function compareByPriceDESC(a, b) {
    a = $(a).attr("priceVal");
    b = $(b).attr("priceVal");

    return (b - a);

}


function compareByTime(a, b) {
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

function compareByTimeDESC(a, b) {
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
    window.open(link, '_blank');
}

function loadRoutes(container, value, depDst) {
    var url = '/loadRoutes';

    $.post(url, {'cityId': value, 'depDst': depDst})
        .done(function (data) {
            $(container).empty();
            var i = 0;

            var opt = document.createElement("option");
            opt.value = "";
            opt.style = "hidden";
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


function setupEnv() {
    $.ajaxSetup({
        beforeSend: function () {
            //$("#messageBox").html("loading...");
            //$("#messageBox").show();
            $("#loadingIndicator").show();
        },
        complete: function () {
            //$("#messageBox").hide();
            $("#loadingIndicator").hide();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            $("#loadingIndicator").hide();
            if (jqXHR.status == 404) {
                $("#messageBox").html("server not found");
            } else {
                $("#messageBox").html(textStatus + ": " + errorThrown + ": " + jqXHR.responseJSON.exception + ": " + jqXHR.responseJSON.message);
            }
            $('#messageBox').show();
        }
    });
}