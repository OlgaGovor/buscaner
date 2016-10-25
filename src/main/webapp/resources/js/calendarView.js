function getLastDayOfMonth(year, month) {
    return new Date(year, month + 1, 0);
}

function getFirstAllowedDayOfMonth(year, month) {
    var date = new Date(year, month, 1);
    var today = new Date();
    today.setHours(0, 0, 0, 0)
    if (date < today) {
        return today;
    }
    return date;
}

//Функция выбор кол-ва отображаемых месяцев с последующей прорисовкой календаря
function drawPriceView(requestForm, priceList, prevButtonName, nextButtonName) {

    var result = "";
    $("#calendar").find("div").remove();
    $("#calendar").addClass("calendar-container");


    var startDate = strToDate(requestForm.departureDate);
    startDate.setDate(1);
    var endDate = strToDate(requestForm.departureDateEnd);
    var count = 0;
    var rowContainer;
    var currentDate = new Date(startDate.getTime());
    while (currentDate <= endDate) {
        var currentStartDay = 0;
        var currentEndDay = 0;

        if (!(count % 2)) {
            rowContainer = $("<div/>").addClass("row calendar-month-row");
            $("#calendar").append(rowContainer);
        }


        var monthContainer = $("<div/>");
        $(monthContainer).addClass("col-md-6 calendar-month-item");
        $(rowContainer).append(monthContainer);

        if (count % 2) {
            $(monthContainer).addClass("col2");
        }
        else {
            $(monthContainer).addClass("col1");
        }

        if (count == 0) {
            var prevBtn = $("<div/>")
                .addClass("btn-sm btn-primary calendar-btn-prev")
                .text(prevButtonName);


            if (currentDate.getFullYear() <= new Date().getFullYear() && currentDate.getMonth() <= new Date().getMonth()) {
                $(prevBtn).addClass("disabled");
            }
            else {
                $(prevBtn).click(function () {
                    onPrevNextClick(requestForm, -1);
                })
            }
            $(monthContainer).append(prevBtn);
        }
        if (count == 1 || (endDate.getFullYear() == currentDate.getFullYear() && endDate.getMonth() == currentDate.getMonth())) {
            var nextBtn = $("<div/>")
                .addClass("btn-sm btn-primary calendar-btn-next")
                .text(nextButtonName)
                .click(function () {
                    onPrevNextClick(requestForm, 1);
                });
            $(monthContainer).append(nextBtn);
        }

        var pageRequestForm = getForm();
        var pageDepartureDate = strToDate(pageRequestForm.departureDate)
        var pageDepartureDateEnd = strToDate(pageRequestForm.departureDateEnd);


        if (currentDate.getFullYear() == pageDepartureDate.getFullYear()
            && currentDate.getMonth() == pageDepartureDate.getMonth()
            && currentDate.getFullYear() == pageDepartureDateEnd.getFullYear()
            && currentDate.getMonth() == pageDepartureDateEnd.getMonth()) {
            currentStartDay = strToDate(pageRequestForm.departureDate).getDate();
            currentEndDay = strToDate(pageRequestForm.departureDateEnd).getDate();
        }
        else if (currentDate.getFullYear() == pageDepartureDate.getFullYear()
            && currentDate.getMonth() == pageDepartureDate.getMonth()) {
            currentStartDay = strToDate(pageRequestForm.departureDate).getDate();
            currentEndDay = getLastDayOfMonth(currentDate.getFullYear(), currentDate.getMonth()).getDate()
        }
        else if (currentDate.getFullYear() == pageDepartureDateEnd.getFullYear()
            && currentDate.getMonth() == pageDepartureDateEnd.getMonth()) {
            currentStartDay = 1;
            currentEndDay = strToDate(pageRequestForm.departureDateEnd).getDate();
        }


        setCalendar(monthContainer, currentDate.getFullYear(), currentDate.getMonth(), currentStartDay, currentEndDay, priceList);
        currentDate.setMonth(currentDate.getMonth() + 1);
        count++;
    }

}

function onPrevNextClick(requestForm, shift) {
    var formData = getForm();

    var depDate = strToDate(requestForm.departureDate);
    var depDateEnd = strToDate(requestForm.departureDateEnd);

    /*one month currently displayed*/
    if (depDate.getFullYear() == depDateEnd.getFullYear()
        && depDate.getMonth() == depDateEnd.getMonth()) {
        if (shift > 0) {/*next*/
            var newDateEnd = new Date(depDateEnd.getFullYear(), depDateEnd.getMonth() + shift, 1);
            formData.departureDateEnd = dateToStr(newDateEnd);
        }
        else {/*prev*/
            var newDate = getLastDayOfMonth(depDate.getFullYear(), depDate.getMonth() + shift);
            formData.departureDate = dateToStr(newDate);
        }
    }
    else {
        var newDate = getLastDayOfMonth(depDate.getFullYear(), depDate.getMonth() + shift);
        var newDateEnd = new Date(depDateEnd.getFullYear(), depDateEnd.getMonth() + shift, 1);

        formData.departureDate = dateToStr(newDate);
        formData.departureDateEnd = dateToStr(newDateEnd);
    }

    searchDataByForm(formData);
}


// Функция установки настроек календаря
function setCalendar(container, year, month, startDate, endDate, priceList) {
//console.log(year + " " + month + " " + startDate + " " + endDate);

    var text = drawCalendar(container, year, month, startDate, endDate, priceList)
    return text;
}


// Функция формирования кода календаря для одного месяца
function drawCalendar(container, year, month, startDate, endDate, priceList) {

    // Переменные

    var monthName = $.fn.datepicker.dates[language].months[month]; //values from bootstrap-datetimepicker.LANG.js
    var firstDayInstance = new Date(year, month, 1);
    var firstDay = firstDayInstance.getDay();
    if (firstDay == 0) {
        firstDay = 6
    }
    else {
        firstDay = firstDay - 1;
    }

    startDate = new Date(year, month, startDate);
    endDate = new Date(year, month, endDate);


    // Число дней в текущем месяце
    var lastDate = getLastDayOfMonth(year, month).getDate();
// Создаем массив сокращенных названий дней недели
    var weekDay = $.fn.datepicker.dates[language].daysMin; //values from bootstrap-datetimepicker.LANG.js


    // Создаем основную структуру таблицы
    var calendarTable = $("<table/>")
        .addClass("calendarTable");

    $(container).append(calendarTable);

    var header = $('<th/>')
        .addClass("calendarHeader")
        .attr("colspan", "7")
        .text(monthName + ' ' + year);
    $(calendarTable).append(header);

    var weekDayRow = $("<tr/>")
        .addClass("weekDayRow");

    $(calendarTable).append(weekDayRow);

    var firstDayOfWeek = 1; // day of the week start. 0 for Sunday - 6 for Saturday
    var dowCnt = 1;
    /*building cells with names of days*/
    while (dowCnt < firstDayOfWeek + 7) {
        var weekDayCell = $("<td/>")
            .addClass("calendarCell weekDayCell");
        $(weekDayCell).text(weekDay[(dowCnt++) % 7]);
        $(weekDayRow).append(weekDayCell);
    }

    var currentDay = 1;
    var curCell = 0;
    var inc = 0;

    for (var row = 1; row <= Math.ceil((lastDate + firstDay) / 7); ++row) {
        var calendarRow = $("<tr/>")
            .addClass("calendarRow");

        $(calendarTable).append(calendarRow);

        for (var col = 1; col <= 7; ++col) {
            var calendarCell = $("<td/>")
                .addClass("calendarCell");
            $(calendarRow).append(calendarCell);
            /*building empty cells before first day od current month*/
            if (curCell < firstDay || currentDay > lastDate) {
                $(calendarCell)
                    .addClass("beforeFirst");
                curCell++
            }
            else {
                $(calendarCell)
                    .addClass("currentMonth");
                var date = new Date(year, month, currentDay);
                if (date >= startDate && date <= endDate) {
                    $(calendarCell).addClass("selectedRange");
                }
                else {
                    $(calendarCell).addClass("notSelectedRange");
                }

                var priceFound = false;
                for (var i = 0; i < priceList.length; i++) {
                    if (date.getTime() == strToDate(priceList[i].departureDate).getTime()) {
                        var cell = buildCalendarCell(date, priceList[i]);
                        $(calendarCell).append(cell);
                        $(calendarCell).click(function () {
                            onDayClicked(this);
                        });
                        $(calendarCell).addClass("cell-with-price");
                        priceFound = true;
                        break;
                    }
                }
                if (!priceFound) {
                    $(calendarCell).append(buildCalendarCell(date));
                    var today = new Date();
                    today.setHours(0, 0, 0, 0);
                    if (date >= today) {
                        $(calendarCell).click(function () {
                            onDayClicked(this);
                        });
                    }
                }
                currentDay++;
            }

        }

    }

}

function buildCalendarCell(date, price) {
    var today = new Date();
    today.setHours(0, 0, 0, 0);
    var cell = $('<div/>')
        .addClass("dataContainer")
        .attr("date", dateToStr(date));


    var dateContainer = $("<p/>")
        .addClass("dateContainer")
        .text(date.getDate());
    $(cell).append(dateContainer);

    if (price != null) {
        var priceContainer = $("<p/>")
            .addClass("priceContainer")
            .text(price.price + ' ' + price.currency);
        $(cell).append(priceContainer);
    }
    else if (date >= today) {
        var iconContainer = $("<p/>")
            .addClass("search-icon-container");
        var icon = $("<span/>")
            .addClass("glyphicon glyphicon-refresh");
        $(iconContainer).append(icon);
        $(cell).append(iconContainer);
    }

    return cell;
}

function onDayClicked(element) {
    var dateAttr = $(element).find("div.dataContainer").attr("date");
    switchToResultList(dateAttr);
}
