$(document).ready(function () {
    $('#datePickerFrom, #datePickerTo, #datePicker')
        .datepicker({
            autoclose: true,
            format: 'dd.mm.yyyy'
        });

    if($('#addNewPersonFormId\\:houseNumber').val() == 0){
        $('#addNewPersonFormId\\:houseNumber').val("");
    }

});
