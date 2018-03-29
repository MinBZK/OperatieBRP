var persoonTemplate = "";
var categorieTemplate = "";
var brpPopupTemplate = "";
var trTemplate = "";
var trMeldingenHeaderTemplate = "";
var trMeldingenTemplate = "";
var trVergelijkingHeaderTemplate = "";
var trVergelijkingTemplate = "";
var thTemplate = "";
var tdTemplate = "";
var tdDatumTemplate = "";
var resultaatIndexTemplate = "";
var meerdereResultatenTemplate = "";
var trResultaatIndexTemplate = "";
var foutmeldingTemplate = "";

function empty (val) {
	return val == undefined || val == null || typeof val == 'undefined';
}

function loadTemplates() {
	$.get("templates/persoon.html", function(template) {
		persoonTemplate = template;
	});

	$.get("templates/categorie.html", function(template) {
		categorieTemplate = template;
	});

	$.get("templates/brpPopup.html", function(template) {
		brpPopupTemplate = template;
	});
	
	$.get("templates/tr.html", function(template) {
		trTemplate = template;
	});
	$.get("templates/trMeldingen.html", function(template) {
	    trMeldingenTemplate = template;
	});
	$.get("templates/trMeldingenHeader.html", function(template) {
	    trMeldingenHeaderTemplate = template;
	});
	$.get("templates/trVergelijking.html", function(template) {
	    trVergelijkingTemplate = template;
	});
	$.get("templates/trVergelijkingHeader.html", function(template) {
	    trVergelijkingHeaderTemplate = template;
	});
	$.get("templates/th.html", function(template) {
		thTemplate = template;
	});
	$.get("templates/td.html", function(template) {
		tdTemplate = template;
	});
	$.get("templates/tdDatum.html", function(template) {
		tdDatumTemplate = template;
	});
	$.get("templates/resultaatIndex.html", function(template) {
		resultaatIndexTemplate = template;
	});
	$.get("templates/meerdereResultaten.html", function(template) {
		meerdereResultatenTemplate = template;
	});
	$.get("templates/trResultaatIndex.html", function(template) {
		trResultaatIndexTemplate = template;
	});
	
	$.get("templates/foutmelding.html", function(template) {
		foutmeldingTemplate = template;
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
            $('#loadingPLDiv').hide();
            if (typeof responseJSON == 'string' || responseJSON instanceof String) {
                location.href= window.location.pathname;
            }
            else {
                Overzicht.maakOverzichten(responseJSON);
            }
        }
    });
}

$(document).ready(
	function(e) {
		$('#zoekForm').submit(function(event) {
			var aNummer = $('#aNummer').val();
			//clear
			ViewerUtils.clearFoutmeldingen();
     	   	//validate
		    if (!ViewerUtils.isValidAnummer(aNummer)) {
		    	ViewerUtils.addZoekFout('err', 'A-nummer is niet ingevuld of bevat een ongeldige waarde.', 'Een A-nummer moet numeriek zijn en 10 posities lang zijn.', false, 'aNummer');
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
			        	   var resultPL = document.getElementById(aNummer);
			        	   if (resultPL) {
			        		   resultPL.scrollIntoView(true);
			        	   }
			           },
			           error: function() {
			        	   ViewerUtils.addZoekFout('err', 'Onbekende fout bij opvragen a-nummer.', null, false, 'aNummer');
			           },
			           data: $("#aNummer").serialize(),
			           success: function(responseJSON) {
			               if (typeof responseJSON == 'string' || responseJSON instanceof String) {
			                   //De String bevat de login html page omdat de sessie is verlopen, refresh page.
			            	   location.href= window.location.pathname;
			               }
			               else {
			            	   //Maak de PL tabel overzichten
			        	       Overzicht.maakOverzichten(responseJSON);
			               }
			           }
			         });
			} else {
				ViewerUtils.addZoekFout('info', 'De PL met A-nummer ' + aNummer + ' staat al op het scherm.', null, true);
			}
		    return false;
		});

		var $importFile = $('#importFile');
		if ($importFile.length > 0) {
			$importFile.fineUploader({
				request : {
					endpoint : qq.ie() ? 'file/uploadMSIE' : 'file/upload'
				},				
				text : {
					uploadButton : 'open',
					cancelButton:"Annuleer",
					retryButton:"Opnieuw",
					failUpload:"Upload mislukt",
					dragZone:"Sleep bestand in dit vak om te importeren",
					formatProgress:"{percent}% van {total_size}",
					waitingForResponse:"Uploaden..."
				},
				messages : {
					tooManyFilesError : 'Maximaal 1 bestand toegestaan.',
					typeError: "Bestand '{file}' heeft een ongeldige extensie. Ondersteunde extensies zijn: {extensions}.",
		            sizeError: "Bestand '{file}' is te groot. Maximale bestandsgrootte is {sizeLimit}.",
		            emptyError: "Bestand '{file}' is leeg.",
		            noFilesError: "Geen bestand geselecteerd om te importeren."
				},
				showMessage: function(message) {
					// Custom implementation instead of the default alert.
					//ViewerUtils.addZoekFout('err', 'Fout bij uploaden bestand', message, true);
					// the onError event will also catch it.
				},
				multiple: false,
				forceMultipart: true,
				validation: {
					//allowedExtensions: ['txt', 'lg01', 'xls', 'GBA', 'nic'],
					sizeLimit: 102400000 // 10 kB = 10 * 1024 bytes
				}
			}).on('submit', function(event, id, filename, reason) {
				$('#defaultPLen').hide();
			}).on('error', function(event, id, filename, reason) {
				ViewerUtils.addZoekFout('err', 'Fout bij uploaden bestand', reason, true);
				$('#defaultPLen').show();
			}).on('cancel', function(event, id, filename, responseJSON) {
				if (this.timeout) clearTimeout(this.timeout);
				$('#defaultPLen').show();
			}).on('complete', function(event, id, filename, responseJSON) {
				if (this.timeout) clearTimeout(this.timeout);

				//Reset fineuploader
        	    $importFile.fineUploader('reset');
        	    $('#aNummer').val('');
				
				if (jQuery.isEmptyObject(responseJSON)) {
	                //Sessie verlopen, refresh
			    	location.href= window.location.pathname;
	            } else if (responseJSON.success) {
	            	//Maak de PL tabel overzichten
	            	Overzicht.maakOverzichten(responseJSON);
	            }
			}).on('progress', function (event, id, filename, responseJSON) {
				this.timeout = setTimeout(function() {
					var cancelButton = $('.qq-upload-cancel')[0];
					if (cancelButton) {
						cancelButton.click();
						ViewerUtils.addZoekFout('info', 'Timeout bij uploaden.', 'De server is mogelijk overbezet. Probeer het later opnieuw.', true);
					}
				}, 20000);
			});
		}

		loadTemplates();
	}
);
