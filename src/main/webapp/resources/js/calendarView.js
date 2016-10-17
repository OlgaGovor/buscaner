function getLastDayOfMonth(year, month){
    return new Date(year, month+1, 0);
}

function getFirstAllowedDayOfMonth(year, month){
    var date = new Date(year, month, 1);
    var today = new Date();
    today.setHours(0,0,0,0)
    if(date < today){
        return today;
    }
    return date;
}

//Функция выбор кол-ва отображаемых месяцев с последующей прорисовкой календаря
function drawPriceView(startYear, startMonth, startDay, endYear, endMonth, endDay, priceList) {

    var result = "";
    $("#calendar").find("div").remove();
    $("#calendar").addClass("calendar-container");


    var startDate = new Date(startYear, startMonth, 1);
    var endDate = new Date(endYear, endMonth, 1);
    var count = 0;
    var rowContainer;
    while (startDate <= endDate) {
        var currentStartDay = 1;
        var currentEndDay = 1;

        if(!(count%2)){
            rowContainer = $("<div/>").addClass("row calendar-month-row");
        rowContainer.addClass("count"+count);
        $("#calendar").append(rowContainer);
        }

        var monthContainer = $("<div/>");
        $(monthContainer).addClass("col-md-6 calendar-month-item");
        $(rowContainer).append(monthContainer);

        if(count%2){
            $(monthContainer).addClass("col2");
        }
        else{
            $(monthContainer).addClass("col1");
        }



        if (startYear == startDate.getFullYear() && startMonth == startDate.getMonth()){
            currentStartDay = startDay;
            currentEndDay = getLastDayOfMonth(startYear, startMonth).getDate();
        }
        else if (endYear == startDate.getFullYear() && endMonth == startDate.getMonth()){
                currentStartDay = 1;
                currentEndDay = endDay;
        }
        else {
                currentStartDay = 1;
                currentEndDay = getLastDayOfMonth(startDate.getFullYear(), startDate.getMonth()).getDate();
        }
        setCalendar(monthContainer, startDate.getFullYear(), startDate.getMonth(), currentStartDay, currentEndDay, priceList);
        startDate.setMonth(startDate.getMonth() + 1);
        count++;
    }

}

function showPrevNextButtons(startYear, startMonth, priceList) {
    var text = " <button type='button' id='prevBtn'  onclick=\"onPrevClick(" + startYear + "," + (startMonth - 1) + ")\">prev</button>" +
        " <button type='button' id='nextBtn' onclick=\"onNextClick(" + startYear + "," + (startMonth + 1) + ")\">next</button>";
    return text;
}

function onPrevClick(year, month, priceList) {
    if (month < 0) {
        year = year - 1;
        month = 11;
    }
    //setMultiMonthView(year, month, priceList);
}

function onNextClick(year, month, priceList) {
    if (month > 11) {
        year = year + 1;
        month = 0;
    }
    // setMultiMonthView(year, month, priceList);
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
    var lastDate = getLastDayOfMonth(month, year).getDate();
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
                if(date >= startDate && date<= endDate){
                    $(calendarCell).addClass("selectedRange");
                }
                else{
                    $(calendarCell).addClass("notSelectedRange");
                }

                var priceFound = false;
                for(var i =0; i < priceList.length; i++){
                    if(date.getTime() == strToDate(priceList[i].departureDate).getTime()){
                        var cell = buildCalendarCell(date, priceList[i]);
                        $(calendarCell).append(cell);
                        $(calendarCell).click(function(){onDayClicked(this);});
                        $(calendarCell).addClass("cell-with-price");
                        priceFound = true;
                        break;
                    }
                }
                if(!priceFound){
                    $(calendarCell).append(buildCalendarCell(date));
                    $(calendarCell).click(function () {
                        onDayClicked(this);
                    });
                }
                currentDay++;
            }

        }

    }

}

function buildCalendarCell(date, price) {
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
    else {
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
