var vrijBerichtConfig = angular.module('VrijBerichtConfig', []);

var Zoeken = {
    titel: 'Vrije Berichten',
    resourceNaam: 'VrijBericht',
    resourceUrl: 'vrijbericht/:id',
    loaderNaam: 'VrijBerichtLoader',
    contextParam: 'vrijbericht',
    vastebreedte: true,
    sortering: 'tijdstipRegistratie,desc',
    kolommen: [
        {name: 'Id', display: 'id', toonOpLijst: false, toonOpBewerken: false},
        {name: 'Inhoud', display: 'data', toonOpLijst: false, type: 'textArea', required: true, readonly: true, nieuwReadonly: true},
        {name: 'Ingekomen/Verzonden', filter:'soortBericht', display: 'soortBericht', type: 'select', bron: 'SoortBerichtVrijBericht', ngFilter: 'richting', readonly: true, nieuwReadonly:true},
        {name: 'Ongelezen', filter:'ongelezen', display: 'ongelezen', type: 'checkboxJaNee', nieuwReadonly: true},
        {name: 'Datumtijd registratie', display: 'datumTijdRegistratie', sort: 'tijdstipRegistratie',  required: true, readonly: true},
        {name: 'Partij', filter:'partijId', display: 'partijId', type: 'selectPartij', bron: 'Partij', toonOpLijst: false, toonOpBewerken: false},
        {name: 'Soort partij', type: 'select', bron: "SoortPartij", filter: "soortPartij", lijstbreedte: 2, readonly: true, toonOpBewerken: false},
        {name: 'Soort partij', display: 'soortPartij', readonly: true, nieuwReadonly: false, toonOpBewerken: false},
        {name: 'Partij code', display: 'partijCode', readonly: true, nieuwReadonly: false, toonOpBewerken: false},
        {name: 'Partij naam', display: 'partijNaam', readonly: true, nieuwReadonly: false, toonOpBewerken: false},
        {name: 'Soort vrij bericht', display: 'soortVrijBericht', type: 'select', bron: "SoortVrijBericht", filter: "soortVrijBericht", lijstbreedte: 2, readonly: true, nieuwReadonly:false},
        {name: 'Begindatum', filter:'beginDatum', display: 'beginDatum', type: 'datum', toonOpLijst: false, readonly: true, nieuwReadonly: false, toonOpBewerken: false},
        {name: 'Einddatum', filter:'eindDatum', display: 'eindDatum', type:'datum', toonOpLijst: false, readonly: true, nieuwReadonly: false, toonOpBewerken: false},
        {
            name: 'Partijen',
            display: 'partijen',
            type: 'inlinelijst',
            bron: 'VrijBerichtPartij',
            velden: [
                {name: 'Partij code', display: 'partijCode', type: 'select', geenNieuw:false, nieuwReadonly: false},
                {name: 'Partij naam', display: 'partijNaam', type: 'select'},
                {name: 'Soort partij', display: 'soortPartij', type: 'select', required : true}
            ],
            relatie: 'vrijbericht',
            toonOpLijst: false,
            toonOpBewerken: true
        }
    ],
    listUri: '/vrijbericht/list',
    listTemplateUrl: 'views/generic/list.html',
    geenEdit: false,
    geenDetail: false,
    geenInitieleLijst: true,
    geenNieuw: true
};
vrijBerichtConfig.value('ZoekenConfig', Zoeken);
beheerConfiguratie.setup(Zoeken);

var VrijBerichtPartij = {
    titel: 'VrijberichtPartij',
    resourceNaam: 'VrijBerichtPartij',
    resourceUrl: 'vrijbericht/:vrijbericht/vrijberichtpartij/:id',
    loaderNaam: 'VrijBerichtPartijLoader',
    vastebreedte: true,
    kolommen: [
        {name: 'Id', display: 'id', readonly: true},
        {name: 'Partij code', display: 'partijCode', type: 'select' },
        {name: 'Partij naam', display: 'partijNaam', type: 'select'},
        {name: 'Soort partij', display: 'soortPartij', type: 'select'}
    ],
    listUri: '/vrijberichtpartij/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true,
    geenNieuw: true
};
kernConfig.value("VrijBerichtPartijConfig", VrijBerichtPartij);
beheerConfiguratie.setup(VrijBerichtPartij);


var SoortBerichtVrijBericht = {
    titel: 'Soort Bericht Vrij Bericht',
    resourceNaam: 'SoortBerichtVrijBericht',
    resourceUrl: 'soortbervrijber/:id',
    loaderNaam: 'SoortBerichtVrijBerichtLoader',
    kolommen: [{name: 'Id', display: 'id'},
        {name: 'Soort Bericht Vrij Bericht', display: 'naam'},
        {name: 'Soort Bericht', display: 'soortBericht', bron: 'SoortBericht'}
    ],
    listUri: '/soortbervrijber/list',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true,
    geenNieuw: true,
    geenEdit: true
};
kernConfig.value("SoortBerichtVrijBerichtConfig", SoortBerichtVrijBericht);
beheerConfiguratie.setup(SoortBerichtVrijBericht);
