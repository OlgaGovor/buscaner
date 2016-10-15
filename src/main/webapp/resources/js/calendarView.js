// Copyright (c) 2002 Yura Ladik http://www.javaportal.ru All rights reserved.
// Permission given to use the script provided that this notice remains as is.
// С изменениями KDG http://htmlweb.ru/

//Функция проверки на высокосный год
function isLeapYear(year) {
    return (year % 4 == 0)
}

//Функция возвращает колличество дней в месяце в зависимости от года
function getDays(month, year) {
    // Создаем массив, для хранения числа дней в каждом месяце
    var ar = new Array(12);
    ar[0] = 31; // Январь
    ar[1] = (isLeapYear(year)) ? 29 : 28;// Февраль
    ar[2] = 31; // Март
    ar[3] = 30; // Апрель
    ar[4] = 31; // Май
    ar[5] = 30; // Июнь
    ar[6] = 31; // Июль
    ar[7] = 31; // Август
    ar[8] = 30; // Сентябрь
    ar[9] = 31; // Остябрь
    ar[10] = 30; // Ноябрь
    ar[11] = 31; // Декабрь
    return ar[month]
}

function getLastDayOfMonth(year, month)
{
console.log("getLastDayOfMonth " + year + month + " result="+new Date(year, month+1, 0));
    return new Date(year, month+1, 0).getDate();
}

//Функция возвращает название месяца
function getMonthName(month, nameMonth) {
    // Создаем массив, для хранения названия каждого месяца
    var ar = new Array(12);
    if (nameMonth == "rus" || nameMonth == "russ" || nameMonth == "russs") {
        ar = ["Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентабрь", "Октябрь", "Ноябрь", "Декабрь"];
    } else {
        ar = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    }
    return ar[month]
}

//Функция выбор кол-ва отображаемых месяцев с последующей прорисовкой календаря
function drawPriceView(startYear, startMonth, startDay, endYear, endMonth, endDay, priceList, dayNames) {

    var result = "";
    $("#calendar").empty();
    $("#calendar").addClass("calendar-container");


    var startDate = new Date(startYear, startMonth, 1);
    var endDate = new Date(endYear, endMonth, 1);
    var count = 0;
    var rowContainer;
    while (startDate <= endDate) {
        var currentStartDay = 1;
        var currentEndDay = 1;

console.log("count="+count + " count%2="+count%2 + "!="+(!(count%2)));
        if(!(count%2)){
        rowContainer = $("<div/>").addClass("calendar-month-row");
        rowContainer.addClass("count"+count);
        $("#calendar").append(rowContainer);
        }

        var monthContainer = $("<div/>");
        $(monthContainer).addClass("calendar-month-item");
        $(rowContainer).append(monthContainer);

        if(count%2){
            $(monthContainer).addClass("col2");
        }
        else{
            $(monthContainer).addClass("col1");
        }



        if (startYear == startDate.getFullYear() && startMonth == startDate.getMonth()){
            currentStartDay = startDay;
            currentEndDay = getLastDayOfMonth(startYear, startMonth);
        }
        else if (endYear == startDate.getFullYear() && endMonth == startDate.getMonth()){
                currentStartDay = 1;
                currentEndDay = endDay;
        }
        else {
                currentStartDay = 1;
                currentEndDay = getLastDayOfMonth(startDate.getFullYear(), startDate.getMonth());
        }
        setCalendar(monthContainer, startDate.getFullYear(), startDate.getMonth(), currentStartDay, currentEndDay, priceList, dayNames);
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
function setCalendar(container, year, month, startDate, endDate, priceList, dayNames) {
//console.log(year + " " + month + " " + startDate + " " + endDate);
    var nameMonth = "rus"; // rus, russ, russs, eng, engs, engss
    var text = drawCalendar(container, nameMonth, year, month, startDate, endDate, priceList, dayNames)
    return text;
}


// Функция формирования кода календаря для одного месяца
function drawCalendar(container, nameMonth, year, month, startDate, endDate, priceList, dayNames) {

    // Переменные
    var monthName = getMonthName(month, nameMonth);
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
    var lastDate = getDays(month, year);
// Создаем массив сокращенных названий дней недели
    var weekDay = dayNames;


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
    /*building empty cells before first day od current month*/
    for (var dayNum = 0; dayNum < 7; ++dayNum) {
        var weekDayCell = $("<td/>")
            .addClass("weekDayCell");
        $(weekDayCell).text(weekDay[dayNum]);
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

                if ((inc < priceList.length) && (date.getTime() == strToDate(priceList[inc].departureDate).getTime())) {
                    var cell = buildCalendarCell(date, priceList[inc]);
                    $(calendarCell).append(cell);
                    $(calendarCell).click(function() {onDayClicked(cell);});
                    inc++;
                }
                else {
                    $(calendarCell).append(buildCalendarCell(date));
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

    return cell;
}

function onDayClicked(element) {
    var dateAttr = $(element).attr("date");
    switchToResultList(dateAttr);
}
