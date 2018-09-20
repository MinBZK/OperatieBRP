var convConfig = angular.module('ConvConfig', []);
var ConversieAanduidingInhoudingVermissingReisdocument = {
	titel: 'Conversie Aanduiding Inhouding/Vermissing Reisdocument',
	resourceNaam: 'ConversieAanduidingInhoudingVermissingReisdocument',
	resourceUrl: 'conversieaanduidinginhoudingvermissingreisdocument/:id',
	loaderNaam: 'ConversieAanduidingInhoudingVermissingReisdocumentLoader',
	kolommen: [
	           { name:'Id', display:'iD', readonly:true, },
	           { name:'Aanduiding Inhouding Dan Wel Vermissing Nederlands Reisdocument (LO3 35.70)', display:'rubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument',
	        	   required:true, maxlength:1, },
	           { name:'Aanduiding Inhouding/Vermissing Reisdocument (BRP)', display:'aanduidingInhoudingVermissingReisdocument',
	        	   required:true, type:'selectRef', bron:'AanduidingInhoudingVermissingReisdocument' },
	           ],
	listUri: '/conversieaanduidinginhoudingvermissingreisdocument/list',
	listTemplateUrl: 'views/generic/list.html',
	geenDetail: false
};
convConfig.value("ConversieAanduidingInhoudingVermissingReisdocumentConfig", ConversieAanduidingInhoudingVermissingReisdocument);
beheerConfiguratie.setup(ConversieAanduidingInhoudingVermissingReisdocument);

var ConversieAangifteAdreshouding = {
	titel: 'Conversie Aangifte Adreshouding',
	resourceNaam: 'ConversieAangifteAdreshouding',
	resourceUrl: 'conversieaangifteadreshouding/:id',
	loaderNaam: 'ConversieAangifteAdreshoudingLoader',
	kolommen: [
	           { name:'Id', display:'iD', readonly:true, },
	           { name:'Omschrijving van de aangifte adreshouding (LO3 72.10)', display:'rubriek7210OmschrijvingVanDeAangifteAdreshouding',
	        	   required:true, maxlength:1, },
		           { name:'Aangever (BRP)', display:'aangever', type:'selectRef', bron:'Aangever' },
		           { name:'Reden Wijziging Verblijf (BRP)', display:'redenWijzigingVerblijf', type:'selectRef', bron:'RedenWijzigingVerblijf' },
	           ],
	listUri: '/conversieaangifteadreshouding/list',
	listTemplateUrl: 'views/generic/list.html',
	geenDetail: false
};
convConfig.value("ConversieAangifteAdreshoudingConfig", ConversieAangifteAdreshouding);
beheerConfiguratie.setup(ConversieAangifteAdreshouding);

var ConversieAdellijkeTitelPredikaat = {
	titel: 'Conversie Adellijke Titel Predikaat',
	resourceNaam: 'ConversieAdellijkeTitelPredikaat',
	resourceUrl: 'conversieadellijketitelpredikaat/:id',
	loaderNaam: 'ConversieAdellijkeTitelPredikaatLoader',
	kolommen: [
	           { name:'Id', display:'iD', readonly:true, },
	           { name:'Adellijke Titel/Predikaat (LO3 02.20)', display:'rubriek0221AdellijkeTitelPredikaat', required:true, maxlength:2, },
		       { name:'Geslachtsaanduiding (BRP)', display:'geslachtsaanduiding', required:true, type:'select', bron:'Geslachtsaanduiding' },
		       { name:'Adellijke Titel (BRP)', display:'adellijkeTitel', type:'selectCodeMV', bron:'AdellijkeTitel' },
		       { name:'Predicaat (BRP)', display:'predicaat', type:'selectCodeMV', bron:'Predicaat' },
	           ],
	listUri: '/conversieadellijketitelpredikaat/list',
	listTemplateUrl: 'views/generic/list.html',
	geenDetail: false
};
convConfig.value("ConversieAdellijkeTitelPredikaatConfig", ConversieAdellijkeTitelPredikaat);
beheerConfiguratie.setup(ConversieAdellijkeTitelPredikaat);

var ConversieLo3Rubriek = {
	titel: 'Conversie LO3 Rubriek',
	resourceNaam: 'ConversieLo3Rubriek',
	resourceUrl: 'conversielo3rubriek/:id',
	loaderNaam: 'ConversieLo3RubriekLoader',
	kolommen: [
	           { name:'Id', display:'iD', },
		       { name:'Naam', display:'Naam',  },
	           ],
	listUri: '/conversielo3rubriek/list',
	listTemplateUrl: 'views/generic/list.html',
	readonly: true,
	geenNieuw: true,
	geenDetail: true
};
convConfig.value("ConversieLo3RubriekConfig", ConversieLo3Rubriek);
beheerConfiguratie.setup(ConversieLo3Rubriek);

var ConversieRedenBeeindigenNationaliteit = {
    titel: 'Conversie Reden Beëindigen Nationaliteit',
    resourceNaam: 'ConversieRedenBeeindigenNationaliteit',
    resourceUrl: 'conversieredenbeeindigennationaliteit/:id',
    loaderNaam: 'ConversieRedenBeeindigenNationaliteitLoader',
    kolommen: [
        { name:'Id', display:'iD', readonly:true, },
        { name:'Reden Beëindigen Nationaliteit (LO3 64.10)', display:'rubriek6410RedenBeeindigenNationaliteit', required:true, maxlength:3, },
        { name:'Reden Verlies NL Nationaliteit (BRP)', display:'redenVerlies', type:'selectCodeOmschrijving', bron:'RedenVerliesNLNationaliteit' },
    ],
    listUri: '/conversieredenbeeindigennationaliteit/list',
    listTemplateUrl: 'views/generic/list.html',
   	geenDetail: false
};
convConfig.value("ConversieRedenBeeindigenNationaliteitConfig", ConversieRedenBeeindigenNationaliteit);
beheerConfiguratie.setup(ConversieRedenBeeindigenNationaliteit);


var ConversieRedenOpnameNationaliteit = {
    titel: 'Conversie Reden Opname Nationalieteit',
    resourceNaam: 'ConversieRedenOpnameNationaliteit',
    resourceUrl: 'conversieredenopnamenationaliteit/:id',
    loaderNaam: 'ConversieRedenOpnameNationaliteitLoader',
    kolommen: [
        { name:'Id', display:'iD', readonly:true, },
        { name:'Reden Opname Nationaliteit (LO3 63.10)', display:'rubriek6310RedenOpnameNationaliteit', required:true, maxlength:3, },
        { name:'Reden Verkrijging NL Nationaliteit (BRP)', display:'redenVerkrijging', type:'selectCodeOmschrijving', bron:'RedenVerkrijgingNLNationaliteit' },
    ],
    listUri: '/conversieredenopnamenationaliteit/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
convConfig.value("ConversieRedenOpnameNationaliteitConfig", ConversieRedenOpnameNationaliteit);
beheerConfiguratie.setup(ConversieRedenOpnameNationaliteit);

var ConversieRedenOntbindingHuwelijkGeregistreerdePartnerschap = {
	titel: 'Conversie Reden Ontbinding Huwelijk Geregistreerde Partnerschap',
	resourceNaam: 'ConversieRedenOntbindingHuwelijkGeregistreerdePartnerschap',
	resourceUrl: 'conversieredenontbindinghuwelijkgeregistreerdpartnerschap/:id',
	loaderNaam: 'ConversieRedenOntbindingHuwelijkGeregistreerdePartnerschapLoader',
	kolommen: [
	           { name:'Id', display:'iD', readonly:true, },
	           { name:'Reden Ontbinding Huwelijk/Geregistreerd Partnerschap (LO3 07.40)', display:'rubriek0741RedenOntbindingHuwelijkGeregistreerdPartnerschap', required:true, maxlength:1, },
		       { name:'Reden Einde Relatie (BRP)', display:'redenEindeRelatie', required:true, type:'selectCodeOmschrijving', bron:'RedenEindeRelatie' },
	           ],
	listUri: '/conversieredenontbindinghuwelijkgeregistreerdpartnerschap/list',
	listTemplateUrl: 'views/generic/list.html',
	geenDetail: false
};
convConfig.value("ConversieRedenOntbindingHuwelijkGeregistreerdePartnerschapConfig", ConversieRedenOntbindingHuwelijkGeregistreerdePartnerschap);
beheerConfiguratie.setup(ConversieRedenOntbindingHuwelijkGeregistreerdePartnerschap);

var ConversieRedenOpschorting = {
	titel: 'Conversie Reden Opschorting',
	resourceNaam: 'ConversieRedenOpschorting',
	resourceUrl: 'conversieredenopschorting/:id',
	loaderNaam: 'ConversieRedenOpschortingLoader',
	kolommen: [
	           { name:'Id', display:'iD', readonly:true, },
	           { name:'Omschrijving reden opschorting bijhouding (LO3 67.20)', display:'rubriek6720OmschrijvingRedenOpschortingBijhouding', required:true, maxlength:1, },
		       { name:'Nadere Bijhoudingsaard (BRP)', display:'nadereBijhoudingsaard', required:true, type:'select', bron:'NadereBijhoudingsaard' },
	           ],
	listUri: '/conversieredenopschorting/list',
	listTemplateUrl: 'views/generic/list.html',
	geenDetail: false
};
convConfig.value("ConversieRedenOpschortingConfig", ConversieRedenOpschorting);
beheerConfiguratie.setup(ConversieRedenOpschorting);

var ConversieRNIDeelnemer = {
	titel: 'Conversie RNI Deelnemer',
	resourceNaam: 'ConversieRNIDeelnemer',
	resourceUrl: 'conversiernideelnemer/:id',
	loaderNaam: 'ConversieRNIDeelnemerLoader',
	kolommen: [
	           { name:'Id', display:'iD', readonly:true, },
	           { name:'Code RNI Deelnemer (LO3 88.10)', display:'rubriek8811CodeRNIDeelnemer',
	        	   required:true, maxlength:5, pattern:'\\d*', },
		           { name:'Partij (BRP)', display:'partij', required:true, type:'selectRef', bron:'Partij' },
	           ],
	listUri: '/conversiernideelnemer/list',
	listTemplateUrl: 'views/generic/list.html',
	geenDetail: false
};
convConfig.value("ConversieRNIDeelnemerConfig", ConversieRNIDeelnemer);
beheerConfiguratie.setup(ConversieRNIDeelnemer);

var ConversieSoortNLReisdocument = {
	titel: 'Conversie Soort NL Reisdocument',
	resourceNaam: 'ConversieSoortNLReisdocument',
	resourceUrl: 'conversiesoortnlreisdocument/:id',
	loaderNaam: 'ConversieSoortNLReisdocumentLoader',
	kolommen: [
	           { name:'Id', display:'iD', readonly:true, },
	           { name:'Nederlands Reisdocument (LO3 35.10)', display:'rubriek3511NederlandsReisdocument', required: true, maxlength: 2, },
		       { name:'Soort Nederlands Reisdocument (BRP)', display:'soortNederlandsReisdocument', required:true, type:'selectCodeOmschrijving', bron:'SoortNederlandsReisdocument' },
	           ],
	listUri: '/conversiesoortnlreisdocument/list',
	listTemplateUrl: 'views/generic/list.html',
	geenDetail: false
};
convConfig.value("ConversieSoortNLReisdocumentConfig", ConversieSoortNLReisdocument);
beheerConfiguratie.setup(ConversieSoortNLReisdocument);

var ConversieSoortRegisterSoortDocument = {
	titel: 'Conversie Soort Register Soort Document',
	resourceNaam: 'ConversieSoortRegisterSoortDocument',
	resourceUrl: 'conversiesoortregistersoortdocument/:id',
	loaderNaam: 'ConversieSoortRegisterSoortDocumentLoader',
	kolommen: [
	           { name:'Id', display:'iD', readonly:true, },
	           { name:'Soort Register (LO3 81.20)', display:'rubriek812101SoortRegister', required:true, maxlength:1, },
		       { name:'Soort Document (BRP)', display:'soortDocument', required:true, type:'selectRef', bron:'SoortDocument' },
	           ],
	listUri: '/conversiesoortregistersoortdocument/list',
	listTemplateUrl: 'views/generic/list.html',
	geenDetail: false
};
convConfig.value("ConversieSoortRegisterSoortDocumentConfig", ConversieSoortRegisterSoortDocument);
beheerConfiguratie.setup(ConversieSoortRegisterSoortDocument);

var ConversieVoorvoegsel = {
	titel: 'Conversie Voorvoegsel',
	resourceNaam: 'ConversieVoorvoegsel',
	resourceUrl: 'conversievoorvoegsel/:id',
	loaderNaam: 'ConversieVoorvoegselLoader',
	kolommen: [
	           { name:'Id', display:'iD', readonly:true, },
	           { name:'Voorvoegsel (LO3 02.30)', display:'rubriek0231Voorvoegsel', sort:'rubriek0231Voorvoegsel', required:true, maxlength:10, },
	           { name:'Voorvoegsel (BRP)', display:'voorvoegsel', sort:'voorvoegsel', required:true, maxlength:80 },
	           { name:'Scheidingsteken (BRP)', display:'scheidingsteken', sort:'scheidingsteken', required:true, maxlength:1, trim:false,  },
	           ],
	listUri: '/conversievoorvoegsel/list',
	listTemplateUrl: 'views/generic/list.html',
	geenDetail: false
};
convConfig.value("ConversieVoorvoegselConfig", ConversieVoorvoegsel);
beheerConfiguratie.setup(ConversieVoorvoegsel);
