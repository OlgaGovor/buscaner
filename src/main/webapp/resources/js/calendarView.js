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
function drawPriceView(startYear, startMonth, startDay, endYear, endMonth, endDay, priceList) {

    if ((endMonth - startMonth) == 0) {
        var t = setCalendar(startYear, startMonth, priceList);
        document.getElementById("calendar").innerHTML = t;
    }
    if ((endMonth - startMonth) >= 1) {
        showTwoMonthes(startYear, startMonth, priceList);
    }
}

function showPrevNextButtons(startYear, startMonth) {
    var text = " <button type='button' id='prevBtn'  onclick=\"onPrevClick(" + startYear + "," + (startMonth - 1) + ")\">prev</button><button type='button' id='nextBtn' onclick=\"onNextClick(" + startYear + "," + (startMonth + 1) + ")\">next</button>";
    return text;
}

function showTwoMonthes(year, month, priceList) {
    var t1 = setCalendar(year, month);
    var year2 = year;
    var month2 = month + 1;

    if (month2 < 0) {
        year2 = year2 - 1;
        month2 = 11;
    }
    if (month2 > 11) {
        year2 = year2 + 1;
        month2 = 0;
    }
    var t2 = setCalendar(year2, month2, priceList);
    var text = "<table><tr><td>" + t1 + "</td><td>" + t2 + "</td></tr></table>";
    var buttons = showPrevNextButtons(year, month, priceList);
    document.getElementById("calendar").innerHTML = text + buttons;
}

function onPrevClick(year, month, priceList) {
    if (month < 0) {
        year = year - 1;
        month = 11;
    }
    showTwoMonthes(year, month);
}

function onNextClick(year, month, priceList) {
    if (month > 11) {
        year = year + 1;
        month = 0;
    }
    showTwoMonthes(year, month, priceList);
}


// Функция установки настроек календаря
function setCalendar(year, month, priceList) {

    // Параметры настройки таблицы
    var tableBgColor = "#ffffff"; //Цвет фона таблицы
    var headerHeight = 25; // Высота ячеки заголовка с названием месяца
    var border = 0; // Рамка
    var cellspacing = "1"; // Промежуток между ячейками
    var cellpadding = "1"; // Свободное пространство между содержимым ячейки и её границами

    var headerColor = "#ffffff"; // Цвет текста заголовка в ячейке
    var headerBgColor = "#000000"; // Цвет фона в ячейке заголовка
    var headerSize = "2"; // Размер шрифта заголовка
    var headerBold = true; // Полужирный шрифта заголовка

    var colWidth = 60; // Ширина столбцов в таблице

    var dayCellHeight = 60; // Высота ячеек содержащих дни недели
    var dayColor = "#000000"; // Цвет шрифта, представляющего дни недели
    var dayBgColor = "#ffffff"; // Цвет фона ячеек содержащих дни недели
    var dayBold = true; //Размер шрифта, представляющего дни недели
    var daySize = 2; // Полужирный шрифт представляющий дни недели

    var cellHeight = 60; // Высота ячеек, представляющих даты в календаре

    var todayColor = "red"; // Цвет, определяющий сегодняшнюю дату в календаре
    var todayBgColor = "#e0efe0"; // Цвет фона ячейки с сегодняшней датой
    var todayBold = true; // Полужирный шрифт представляющий сегодняшнюю дату в календаре
    var todaySize = 2; //Размер шрифта, представляющего сегодняшнюю дату в календаре

    var allDayColor = "#000000"; // Цвет, остальных дней в календаре
    var allDayBgColor = "#e0efe0"; //Цвет фона остальных ячеек
    var allDayBold = false; // Полужирный шрифт представляющий остальные дни
    var allDaySize = 2; //Размер шрифта, представляющего остальные дни

    var timeColor = "blue"; // Цвет выводимого времени
    var timeSize = "1"; //Размер шрифта выводимого времени
    var timeBold = false; // Полужирный шрифт выводимого времени
    var isTime = true; //Выводить время или нет
    var nameMonth = "rus"; // rus, russ, russs, eng, engs, engss
    var text = drawCalendar(tableBgColor, headerHeight, border,
        cellspacing, cellpadding,
        headerColor, headerBgColor,
        headerSize, headerBold,
        colWidth,
        dayCellHeight, dayColor, dayBgColor, dayBold, daySize,
        cellHeight,
        todayColor, todayBgColor, todayBold, todaySize,
        allDayColor, allDayBgColor, allDayBold, allDaySize,
        timeColor, timeSize, timeBold, isTime, nameMonth, year, month, priceList)
    return text;
}


// Функция формирования кода календаря для одного месяца
function drawCalendar(tableBgColor, headerHeight, border,
                      cellspacing, cellpadding,
                      headerColor, headerBgColor, headerSize, headerBold,
                      colWidth,
                      dayCellHeight, dayColor, dayBgColor, dayBold, daySize,
                      cellHeight,
                      todayColor, todayBgColor, todayBold, todaySize,
                      allDayColor, allDayBgColor, allDayBold, allDaySize,
                      timeColor, timeSize, timeBold, isTime, nameMonth, year, month, priceList) {
    // Переменные
    var monthName = getMonthName(month, nameMonth);
    var firstDayInstance = new Date(year, month, 1);
    var firstDay = firstDayInstance.getDay() + 8;
    firstDayInstance = null;

    // Число дней в текущем месяце
    var lastDate = getDays(month, year);
    // Создаем основную структуру таблицы
    var text = "";
    text += '<table border=' + border + ' cellspacing=' + cellspacing +
        ' cellpadding=' + cellpadding + ' bgcolor=' + tableBgColor + '>' +
        '<th colspan=7 height=' + headerHeight + ' bgcolor=' + headerBgColor + '>' +
        '<font color="' + headerColor + '" size=' + headerSize + '>';
    if (headerBold)text += '<tb>';
    text += monthName + ' ' + year;
    if (headerBold)text += '</b>';
    text += '</font>';
    text += '</th>';
    var openCol = '<td width=' + colWidth + ' height=' + dayCellHeight + ' bgcolor=' +
        dayBgColor + '>';
    openCol += '<font color="' + dayColor + '" size=' + daySize + '>';
    if (dayBold)openCol += '<tb>';
    var closeCol = '</font></td>';
    if (dayBold)closeCol = '</b>' + closeCol;
    // Создаем массив сокращенных названий дней недели
    var weekDay = new Array(7);
    if (nameMonth == "rus") {
        weekDay = ["Пн", "ВТ", "Ср", "Чт", "Пт", "Сб", "Вс"];
    }
    else if (nameMonth == "russ") {
        weekDay = ["пн", "вт", "ср", "чт", "пт", "сб", "вс"];
    }
    else if (nameMonth == "russs") {
        weekDay = ["п", "в", "с", "ч", "п", "с", "в"];
    }
    else if (nameMonth == "eng") {
        weekDay = ["Mon", "Tues", "Wed", "Thu", "Fri", "Sat", "Sun"]
    }
    else if (nameMonth == "engs") {
        weekDay = ["Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"];
    }
    else if (nameMonth == "engss") {
        weekDay = ["m", "t", "w", "t", "f", "s", "s"];
    }
    text += '<tr align="center" valign="center">';
    for (var dayNum = 0; dayNum < 7; ++dayNum) {
        text += openCol + weekDay[dayNum] + closeCol
    }
    text += '</tr>';
    var digit = 1;
    var curCell = 2;
    var inc = 0;
    for (var row = 1; row <= Math.ceil((lastDate + firstDay - 1) / 7); ++row) {
        text += '<tr align="right" valign="top">';
        for (var col = 1; col <= 7; ++col) {
            if (digit > lastDate) break;
            if (curCell < firstDay) {
                text += '<td><font size=' + allDaySize + ' color=' + allDayColor +
                    '> </font></td>';
                curCell++
            }
            else {
                text += '<td height=' + cellHeight + ' bgcolor=' + allDayBgColor +
                    '><font size=' + allDaySize + ' color=' + allDayColor + '>';
                if (allDayBold)text += '<b>';

                text += '<div><p>';
                text += digit;
                text += '</p><p>';


                var date = new Date(year, month, digit);

                if ((inc < priceList.length) && (date.getTime() == toDate(priceList[inc].departureDate).getTime())) {
                    text += priceList[inc].price + priceList[inc].currency;
                    inc++;
                }
                else {

                }
                ;
                text += '</p></div>'

                if (allDayBold)text += '</b>';
                text += '</font></td>';

                digit++;
            }
        }
        text += '</tr>';
    }
    text += '</table>';
    //возвращаем сформированную таблицу для месяца
    return text;
}