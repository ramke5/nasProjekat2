var Sample = function () {

    var setHelpText = function (path,tooltip) {
        $(path).popover({trigger:'hover', placement:'right', content:tooltip, html:true });
    }
    return{
        setSampleValues: function (sampleType, analysisType, gender, method, eGenResult, nGenResult, overallResult) {
            $('#sampleType').val(sampleType).attr("selected", "selected");
            $('#analysisType').val(analysisType).attr("selected", "selected");
            $('#gender').val(gender).attr("selected", "selected");
            $('#method').val(method).attr("selected", "selected");
            $('#eGenResult').val(eGenResult).attr("selected", "selected");
            $('#nGenResult').val(nGenResult).attr("selected", "selected");
            $('#overallResult').val(overallResult).attr("selected", "selected");

        },
        setHelpText: function () {
            setHelpText('label[for="patientName"]',"Unesite ime pacijenta.");
            setHelpText('label[for="patientSurname"]',"Unesite prezime pacijenta.");
            setHelpText('label[for="birthDate"]',"Unesite datum rođenja pacijenta.");
            setHelpText('label[for="gender"]',"Unesite spol pacijenta.");
            setHelpText('label[for="address"]',"Unesite adresu pacijenta.");
            setHelpText('label[for="city"]',"Unesti grad pacijenta.");
            setHelpText('label[for="phoneNumber"]',"Unesite broj telefona pacijenta.");
            setHelpText('label[for="email"]',"Unesite email pacijenta.");
            setHelpText('label[for="sampleType"]',"Unesite tip uzorka.");
            setHelpText('label[for="analysisType"]',"Unesite vrstu analizu.");
            setHelpText('label[for="method"]',"Unesite metod rada.");
            setHelpText('label[for="analysisReason"]',"Unesite razlog testiranja.");
            setHelpText('label[for="note"]',"Unesite dodatnu napomenu.");
            setHelpText('label[for="eGenResult"]',"Unesite E gen rezultat.");
            setHelpText('label[for="nGenResult"]',"Unesite N gen rezultat.");
            setHelpText('label[for="overallResult"]',"Unesite rezultat analize.");
            setHelpText('label[for="analysisPurpose"]',"Unesite cilj analize.");



        }
    }
}();
