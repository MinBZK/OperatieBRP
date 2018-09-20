var persoonTemplate = "";
var overzichtTemplate = "";
var brpActieTemplate = "";
var trTemplate = "";
var trPreconditiesTemplate = "";
var thTemplate = "";
var tdTemplate = "";

function empty (val) {
	return val == undefined || val == null || typeof val == 'undefined';
}

function loadTemplates() {
	$.get("templates/persoon.html", function(template) {
		persoonTemplate = template;
	});

	$.get("templates/overzicht.html", function(template) {
		overzichtTemplate = template;
	});

	$.get("templates/brpActie.html", function(template) {
		brpActieTemplate = template;
	});
	$.get("templates/tr.html", function(template) {
		trTemplate = template;
	});
	$.get("templates/trPrecondities.html", function(template) {
		trPreconditiesTemplate = template;
	});
	$.get("templates/th.html", function(template) {
		thTemplate = template;
	});
	$.get("templates/td.html", function(template) {
		tdTemplate = template;
	});
}

function toggleClass(id) {
	var $id = $('#' + id);

	if ($id.hasClass(id)) {
		$id.removeClass(id);
	} else {
		$id.addClass(id);
	}
}

function laadBestand(name) {
	$('#content').empty();
    $.ajax({
        type : "POST",
        url : "file/uploadFileName",
        beforeSend: function() {
            $('#loadingPLDiv').css('display', 'inline-block');
            $('#loadingPLDiv').show();
        },
        complete: function() { 
            $('#loadingPLDiv').hide();
        },
        data : "qqfile=" + name,
        success : function(responseJSON) {
            Overzicht.maakOverzichten(responseJSON);
        }
    });
}

$(document).ready(
	function(e) {
		$('#vertical').click(function (event) {
			$('#vertical').toggleClass('vertical');
			Overzicht.verwerkFilters();
		});
		$('#ongeldig').click(function (event) {
			if ($('#historisch').hasClass('historisch')) {
			    $('#ongeldig').toggleClass('ongeldig');
            }
			Overzicht.verwerkFilters();
		});
		$('#historisch').click(function (event) {
			$('#historisch').toggleClass('historisch');
			if (!($('#historisch').hasClass('historisch'))) {
                $('#ongeldig').removeClass('ongeldig');
            }
			else {
			    $('#ongeldig').addClass('ongeldig');
			}
			Overzicht.verwerkFilters();
		});

		$('#zoekForm').submit(function(event) {
			var aNummer = $('#aNummer').val();
			//clear
			$("#foutmeldingen").empty();
     	   	//validate
		    var validateExpr = /\d+/; 
		    if (!validateExpr.test(aNummer)) {
		    	ViewerUtils.addLineToTable($("#foutmeldingen"), {thClass: 'error', th : 'Zoeken op a-nummer', td: 'A-nummer is niet ingevuld of bevat een ongeldige waarde'});
		    	return false;
		    }
		    //call
			var aNummerId = '#' + aNummer;			
			if ($(aNummerId).size() == 0) {
			    $.ajax({
			           type: "GET",
			           url: "zoek",
			           beforeSend: function() {
			        	   $('#loadingSubmitDiv').css('display', 'inline-block');
			        	   $('#loadingSubmitDiv').show();
			           },
			           complete: function() { 
			        	   $('#loadingSubmitDiv').hide();
			        	   document.getElementById(aNummer).scrollIntoView(true);
			           },
			           error: function() {
			        	   ViewerUtils.addLineToTable($("#foutmeldingen"), {thClass: 'error', th : 'Zoeken op a-nummer', td: 'A-nummer is niet ingevuld of bevat een ongeldige waarde'});				       		
			           },
			           data: $("#aNummer").serialize(),
			           success: function(responseJSON) {
			        	   Overzicht.maakOverzichten(responseJSON);
			           }
			         });
			}
		    return false;
		});

		var $lg01File = $('#lg01File');
		if ($lg01File.length > 0) {
			$('#lg01File').fineUploader({
				request : {
					endpoint : qq.ie() ? 'file/uploadMSIE' : 'file/upload'
				},
				text : {
					uploadButton : 'Bestand toevoegen'
				},
				//debug: true,
				multiple: true,
				forceMultipart: true,
				validation: {
					allowedExtensions: ['txt', 'xls', 'GBA'],
					sizeLimit: 102400000 // 50 kB = 50 * 1024 bytes
				}
			}).on('error', function(event, id, filename, reason) {
				console.log(reason);
				ViewerUtils.addLineToTable($("#foutmeldingen"), {thClass: 'error', th : 'Bestand toevoegen', td: 'Fouten bij het uploaden: ' + reason});
			}).on('complete', function(event, id, filename, responseJSON) {
				$('#lg01File').fineUploader('reset');
				Overzicht.maakOverzichten(responseJSON);
			});

			$('#toggleDefaultPL').click(function (event) {
	            $('#defaultPLen').toggleClass('hide');
	        });
		}

		loadTemplates();
	}
);