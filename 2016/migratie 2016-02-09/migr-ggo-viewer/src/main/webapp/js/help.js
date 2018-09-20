
function hideAll() {
    $('.welkom_text').hide();
    $('.overzicht_gebruikersinterface_text').hide();
    $('.werken_met_de_PL_tabellen_text').hide();
    $('.werken_met_de_signaleringstabellen_text').hide();
    $('.inhoud_van_de_tabellen_text').hide();    
}

$(document).ready(
	function(e) {
	    $('.welkom').click(function (event) {
	        hideAll();
            $('.welkom_text').toggle();
        });
	    
	    $('.overzicht_gebruikersinterface').click(function (event) {
	        hideAll();
            $('.overzicht_gebruikersinterface_text').toggle();
        });
	    
	    $('.werken_met_de_PL_tabellen').click(function (event) {
	        hideAll();
            $('.werken_met_de_PL_tabellen_text').toggle();
        });
	    
	    $('.werken_met_de_signaleringstabellen').click(function (event) {
	        hideAll();
            $('.werken_met_de_signaleringstabellen_text').toggle();
        });
	    
	    $('.inhoud_van_de_tabellen').click(function (event) {
	        hideAll();
            $('.inhoud_van_de_tabellen_text').toggle();
        });
	    
	    hideAll();
	    //Toon de default eerste pagina
	    $('.welkom_text').toggle();
	}
);