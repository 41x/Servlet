/**
 * Created by iskra on 06.09.2016.
 */
$(function () {
  $('.search').on('click', function (e) {
    var lastname = $('.form > input:nth-child(1)').val();
    var firstname = $('.form > input:nth-child(2)').val();
    var middlename = $('.form > input:nth-child(3)').val();
    var city = $('.form > input:nth-child(4)').val();
    var number = $('.form > input:nth-child(5)').val();
    var color = $('.form > input:nth-child(6)').val();
    var model = $('.form > input:nth-child(7)').val();
    if (firstname == '' && lastname == '' && middlename == '' && city == '' && number == '' && color == '' && model == '') {
      alert('Нет данных для поиска!');
      return;
    }
    var data = {};
    if (firstname != '') data.firstname = firstname;
    if (lastname != '') data.lastname = lastname;
    if (middlename != '') data.middlename = middlename;
    if (city != '') data.name = city;
    if (number != '') data.carnum = number;
    if (color != '') data.color = color;
    if (model != '') data.model = model;
    $.ajax({
      method: "POST",
      url: "/controller",
      data: data
    })
      .done(function (jsn) {
        var result = JSON.parse(jsn);
        if (!result.length) {
          clearResults();
          alert("Данные по запросу не найдены");
          return;
        }
        buildHTML(result);
      })
      .fail(function () {
        console.log('ajax error');
      });
  });
  $('.all').on('click', function (e) {
    $.ajax({
      method: "GET",
      url: "/controller"
    })
      .done(function (jsn) {
        var result = JSON.parse(jsn);
        if (!result.length) {
          clearResults();
          alert("Данные по запросу не найдены");
          return;
        }
        buildHTML(result);
      })
      .fail(function () {
        console.log('ajax error');
      });
  });

});
function buildHTML(jsn) {
  clearResults();
  jsn.forEach(function (el) {
    $('.t-container').append(
      "<tr>" +
      "<td>" + el.lastname + "</td>" +
      "<td>" + el.firstname + "</td>" +
      "<td>" + el.middlename + "</td>" +
      "<td>" + el.cname + "</td>" +
      "<td>" + el.carnum + "</td>" +
      "<td>" + el.color + "</td>" +
      "<td>" + el.model + "</td>" +
      "</tr>"
    );
  });
}
function clearResults() {
  $('.t-container > tr:not(:first-child)').remove();
}