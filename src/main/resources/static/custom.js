const url = "https://bekzodkeldiyarov-itransition6.herokuapp.com";
let dataIndex = 1;
let pageNumber = 0;

function generateData(numberOfData) {
    let locale = $('#locale').val();
    let error = $('#error').val();
    let seed = $('#seed').val();
    $.get(url + "/generateData/" + numberOfData + "/" + locale + "/" + error + "/" + (seed + pageNumber), function (response) {
        let data = response;
        for (let i = 0; i < data.length; i++) {
            console.log(data.id)
            var template = Handlebars.compile($("#dataRow").html());
            var context = {
                number: dataIndex++,
                id: data[i].id,
                name: data[i].name,
                address: data[i].address,
                phone: data[i].phoneNumber
            };
            $('#data-table-body').append(template(context));
            pageNumber++;


        }
    }).fail(function (error) {
        if (error.status === 400) {
        }
    });
    $('.tableexport-caption').remove();
}


$(window).scroll(function () {
    if ($(document).height() - $(this).height() === $(this).scrollTop()) {
        generateData(10);
    }
});

function exportTable() {
    $('.tableexport-caption').remove();
    $('#data-table').tableExport({type: 'csv'});
    $('.button-default').addClass('btn btn-primary');
}

function inputsChanged() {
    $('#data-table-body').html('');
    dataIndex = 1;
    pageNumber = 0;
    generateData(20);
}