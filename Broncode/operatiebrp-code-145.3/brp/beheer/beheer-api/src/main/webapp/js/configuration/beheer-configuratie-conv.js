var convConfig = angular.module('ConvConfig', []);
var ConversieAanduidingInhoudingVermissingReisdocument = {
    titel: 'Conversie Aanduiding Inhouding/Vermissing Reisdocument',
    resourceNaam: 'ConversieAanduidingInhoudingVermissingReisdocument',
    resourceUrl: 'conversieaanduidinginhoudingvermissingreisdocument/:id',
    loaderNaam: 'ConversieAanduidingInhoudingVermissingReisdocumentLoader',
    kolommen: [
        {name:'Id', display: 'id', readonly:true},
        {name:'Aanduiding Inhouding Dan Wel Vermissing Nederlands Reisdocument (LO3 35.70)', display:'rubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument',
               sort: 'lo3AanduidingInhoudingOfVermissingReisdocument', required:true, maxlength:1},
        {name:'Aanduiding Inhouding/Vermissing Reisdocument (BRP)', sort: 'aanduidingInhoudingOfVermissingReisdocument.naam', display:'aanduidingInhoudingVermissingReisdocument',
               required:true, type:'select', bron:'AanduidingInhoudingVermissingReisdocument' },
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
        { name:'Id', display: 'id', readonly:true},
        { name:'Omschrijving van de aangifte adreshouding (LO3 72.10)', display:'rubr7210omsvandeaangifteadre',
               sort: 'lo3OmschrijvingAangifteAdreshouding', required:true, maxlength:1},
        { name:'Aangever (BRP)', display:'aangever', sort:'aangever.naam', type:'select', bron:'Aangever' },
        { name:'Reden Wijziging Verblijf (BRP)', sort: 'redenWijzigingVerblijf.naam', display:'redenWijzigingVerblijf', type:'select', bron:'RedenWijzigingVerblijf' },
    ],
    listUri: '/conversieaangifteadreshouding/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
convConfig.value("ConversieAangifteAdreshoudingConfig", ConversieAangifteAdreshouding);
beheerConfiguratie.setup(ConversieAangifteAdreshouding);

var ConversieAdellijkeTitelPredikaat = {
    titel: 'Conversie Adellijke Titel/Predikaat',
    resourceNaam: 'ConversieAdellijkeTitelPredikaat',
    resourceUrl: 'conversieadellijketitelpredikaat/:id',
    loaderNaam: 'ConversieAdellijkeTitelPredikaatLoader',
    kolommen: [
        { name:'Id', display: 'id', readonly:true},
        { name:'Adellijke Titel/Predikaat (LO3 02.21)', display:'rubriek0221AdellijkeTitelPredikaat', sort: 'lo3AdellijkeTitelPredikaat', required:true, maxlength:2},
        { name:'Geslachtsaanduiding (BRP)', display:'geslachtsaanduiding', sort:'geslachtsaanduidingId', required:true, type:'select', bron:'Geslachtsaanduiding' },
        { name:'Adellijke Titel (BRP)', display:'adellijkeTitel', sort:'adellijkeTitelId', type:'selectCodeMV', bron:'AdellijkeTitel' },
        { name:'Predicaat (BRP)', display:'predikaat', sort:'predikaatId', type:'selectCodeMV', bron:'Predicaat' },
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
        { name:'Id', display: 'id', readonly: true},
        { name:'Naam', display: 'naam', sort:'naam' },
    ],
    listUri: '/conversielo3rubriek/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
convConfig.value("ConversieLo3RubriekConfig", ConversieLo3Rubriek);
beheerConfiguratie.setup(ConversieLo3Rubriek);

var ConversieRedenBeeindigenNationaliteit = {
    titel: 'Conversie Reden Beëindigen Nationaliteit',
    resourceNaam: 'ConversieRedenBeeindigenNationaliteit',
    resourceUrl: 'conversieredenbeeindigennationaliteit/:id',
    loaderNaam: 'ConversieRedenBeeindigenNationaliteitLoader',
    kolommen: [
        { name:'Id', display: 'id', readonly:true},
        { name:'Reden Beëindigen Nationaliteit (LO3 64.10)', display:'rubriek6410RedenBeeindigenNationaliteit', sort: 'redenBeeindigingNationaliteit', required:true, maxlength:3},
        { name:'Reden Verlies NL Nationaliteit (BRP)', display:'redenVerlies', type:'selectCodeOmschrijving', sort: 'redenVerliesNLNationaliteit.omschrijving', bron:'RedenVerliesNLNationaliteit', required:true },
    ],
    listUri: '/conversieredenbeeindigennationaliteit/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
convConfig.value("ConversieRedenBeeindigenNationaliteitConfig", ConversieRedenBeeindigenNationaliteit);
beheerConfiguratie.setup(ConversieRedenBeeindigenNationaliteit);


var ConversieRedenOpnameNationaliteit = {
    titel: 'Conversie Reden Opname Nationaliteit',
    resourceNaam: 'ConversieRedenOpnameNationaliteit',
    resourceUrl: 'conversieredenopnamenationaliteit/:id',
    loaderNaam: 'ConversieRedenOpnameNationaliteitLoader',
    kolommen: [
        { name:'Id', display: 'id', readonly:true},
        { name:'Reden Opname Nationaliteit (LO3 63.10)', display:'rubriek6310RedenOpnameNationaliteit', sort: 'redenOpnameNationaliteit', required:true, maxlength:3},
        { name:'Reden Verkrijging NL Nationaliteit (BRP)', display:'redenVerkrijging', type:'selectCodeOmschrijving', bron:'RedenVerkrijgingNLNationaliteit', sort: 'redenVerkrijgingNLNationaliteit.omschrijving', required:true },
    ],
    listUri: '/conversieredenopnamenationaliteit/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
convConfig.value("ConversieRedenOpnameNationaliteitConfig", ConversieRedenOpnameNationaliteit);
beheerConfiguratie.setup(ConversieRedenOpnameNationaliteit);

var ConversieRedenOntbindingHuwelijkGeregistreerdePartnerschap = {
    titel: 'Conversie Reden Ontbinding Huwelijk/Geregistreerd Partnerschap',
    resourceNaam: 'ConversieRedenOntbindingHuwelijkGeregistreerdePartnerschap',
    resourceUrl: 'conversieredenontbindinghuwelijkgeregistreerdpartnerschap/:id',
    loaderNaam: 'ConversieRedenOntbindingHuwelijkGeregistreerdePartnerschapLoader',
    kolommen: [
        { name:'Id', display: 'id', readonly:true},
        { name:'Reden Ontbinding Huwelijk/Geregistreerd Partnerschap (LO3 07.40)', display:'rubriek0741RedenOntbindingHuwelijkGeregistreerdPartnerschap', sort:'lo3RedenOntbindingHuwelijkGp', required:true, maxlength:1},
        { name:'Reden Einde Relatie (BRP)', display:'redenEindeRelatie', required:true, type:'selectCodeOmschrijving', bron:'RedenEindeRelatie', sort:'redenBeeindigingRelatie.omschrijving' },
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
        { name:'Id', display: 'id', readonly:true},
        { name:'Omschrijving reden opschorting bijhouding (LO3 67.20)', display:'rubr6720omsrdnopschortingbij', required:true, maxlength:1, sort: 'lo3OmschrijvingRedenOpschorting' },
        { name:'Nadere Bijhoudingsaard (BRP)', display:'naderebijhaard', required:true, type:'selectCodeOmschrijving', bron:'NadereBijhoudingsaard', sort:'redenOpschortingId' },
    ],
    listUri: '/conversieredenopschorting/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
convConfig.value("ConversieRedenOpschortingConfig", ConversieRedenOpschorting);
beheerConfiguratie.setup(ConversieRedenOpschorting);

var ConversieRNIdeelnemer = {
    titel: 'Conversie RNI Deelnemer',
    resourceNaam: 'ConversieRNIdeelnemer',
    resourceUrl: 'conversiernideelnemer/:id',
    loaderNaam: 'ConversieRNIdeelnemerLoader',
    kolommen: [
        { name:'Id', display: 'id', readonly:true},
        { name:'Code RNI Deelnemer (LO3 88.11)', display:'rubriek8811CodeRNIDeelnemer', required:true, maxlength:5, pattern:'\\d*', sort: 'lo3CodeRNIDeelnemer'},
        { name:'Partij (BRP)', display:'partij', required:true, type:'select', bron:'Partij', sort:'partij'},
    ],
    listUri: '/conversiernideelnemer/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
convConfig.value("ConversieRNIdeelnemerConfig", ConversieRNIdeelnemer);
beheerConfiguratie.setup(ConversieRNIdeelnemer);

var ConversieSoortNLReisdocument = {
    titel: 'Conversie Soort NL Reisdocument',
    resourceNaam: 'ConversieSoortNLReisdocument',
    resourceUrl: 'conversiesoortnlreisdocument/:id',
    loaderNaam: 'ConversieSoortNLReisdocumentLoader',
    kolommen: [
        { name:'Id', display: 'id', readonly:true},
        { name:'Nederlands Reisdocument (LO3 35.11)', display:'rubriek3511NederlandsReisdocument', required: true, maxlength: 2, sort: 'lo3NederlandsReisdocument' },
        { name:'Soort Nederlands Reisdocument (BRP)', display:'soortNederlandsReisdocument', required:true, type:'selectCodeOmschrijving', bron:'SoortNederlandsReisdocument', sort:'soortNederlandsReisdocument.omschrijving'},
    ],
    listUri: '/conversiesoortnlreisdocument/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
convConfig.value("ConversieSoortNLReisdocumentConfig", ConversieSoortNLReisdocument);
beheerConfiguratie.setup(ConversieSoortNLReisdocument);

var ConversieVoorvoegsel = {
    titel: 'Conversie Voorvoegsel',
    resourceNaam: 'ConversieVoorvoegsel',
    resourceUrl: 'conversievoorvoegsel/:id',
    loaderNaam: 'ConversieVoorvoegselLoader',
    kolommen: [
        { name:'Id', display: 'id', readonly:true},
        { name:'Voorvoegsel (LO3 02.31)', display:'lo3Voorvoegsel', sort:'lo3Voorvoegsel', required:true, maxlength:10},
        { name:'Voorvoegsel (BRP)', display:'voorvoegsel', sort:'voorvoegsel', required:true, maxlength:80 },
        { name:'Scheidingsteken (BRP)', display:'scheidingsteken', sort:'scheidingsteken', required:true, maxlength:1, trim:false,  },
    ],
    listUri: '/conversievoorvoegsel/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false
};
convConfig.value("ConversieVoorvoegselConfig", ConversieVoorvoegsel);
beheerConfiguratie.setup(ConversieVoorvoegsel);
