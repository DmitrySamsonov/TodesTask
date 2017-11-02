$(document).ready(function () {
    $('#datePickerFrom, #datePickerTo, #datePicker')
        .datepicker({
            autoclose: true,
            format: 'dd.mm.yyyy'
        });

    $('#addNewPersonFormId\\:houseNumber').val("");

});
