var autautConfig = angular.module('AutAutConfig', []);

var Attribuut = {
    titel: 'Attribuut',
    resourceNaam: 'Attribuut',
    resourceUrl: 'leveringsautorisaties/:leveringsautorisatie/dienstbundels/:dienstbundel/dienstbundelgroepen/:dienstbundelgroep/attributen/:id',
    loaderNaam: 'AttribuutLoader',
    contextParam: 'attribuut',
    vastebreedte: true,
    kolommen: [
        {name: 'Id', display: 'id', toonOpBewerken: true, readonly: true},
        {name: 'Attribuut', display: 'attribuutNaam', type: 'selectRef',  bron: 'Element', required: true, readonly: true},
        {name: 'Soort', display: 'soort', type: 'select',  bron: 'SoortElementAutorisatie', required: true, readonly: true},
        {name: 'Wordt geleverd?', display: 'actief', type: 'checkboxJaNee'},
    ],
    listUri: '/attributen',
    listTemplateUrl: 'views/generic/list.html',
    cache: false,
    geenDetail: true,
    blijfOpWijzig: false
};
autautConfig.value('AttribuutConfig', Attribuut);
beheerConfiguratie.setup(Attribuut);


var Bijhoudingsautorisatie = {
    titel: 'Bijhoudingsautorisatie',
    resourceNaam: 'Bijhoudingsautorisatie',
    resourceUrl: 'bijhoudingsautorisaties/:id',
    loaderNaam: 'BijhoudingsautorisatieLoader',
    contextParam: 'bijhoudingsautorisatie',
    vastebreedte: true,
    kolommen: [
        {name: 'Id', display: 'id', readonly: true, toonOpBewerken: true},
        {name: 'Modelautorisatie?', display: 'indicatieModelAutorisatie', filter: 'filterModelAutorisatie', type: 'checkboxJaNee', readonly: true, nieuwReadonly: false},
        {name: 'Naam', display: 'naam', sort: 'naam', filter: 'filterNaam', required: true, maxlength: 80, lijstbreedte: 4},
        {name: 'Datum ingang', display: 'datumIngang', sort: 'datumIngang', filter: 'filterDatumIngang', type:'datum', required: true },
        {name: 'Datum einde', display: 'datumEinde', sort: 'datumEinde', filter: 'filterDatumEinde', type: 'datum'},
        {name: 'Geautoriseerde', filter: 'filterGeautoriseerde',type: 'selectRef', bron: 'PartijRol', toonOpLijst: false, maxlength: 80, lijstbreedte: 4},
        {name: 'Ondertekenaar', filter: 'filterOndertekenaar',type: 'selectRef', bron: 'Partij', toonOpLijst: false, maxlength: 80, lijstbreedte: 4},
        {name: 'Transporteur', filter: 'filterTransporteur',type: 'selectRef', bron: 'Partij', toonOpLijst: false, maxlength: 80, lijstbreedte: 4},
        {
            name: 'Soorten administratieve handeling',
            display: 'bijhoudingsautorisatieSoortAdministratieveHandeling',
            type: 'inlinekruislijst',
            bron: 'BijhoudingsautorisatieSoortAdministratieveHandeling',
            toonOpBewerken: true,
            nieuwReadonly: true,
            velden: [
                {name: 'Geautoriseerd?', display: 'actief', type: 'checkboxJaNee', readonly: true},
                {name: 'Attribuut', display: 'naam', required: true},
            ],
            relatie: 'bijhoudingsautorisatie',
            geenDetail: true,
            toonOpLijst: false
        },
        {name: 'Geblokkeerd?', display: 'indicatieGeblokkeerd', sort: 'indicatieGeblokkeerd', filter: 'filterGeblokkeerd', type: 'checkboxJaNee'},
        {
            name: 'Toegangen',
            display: 'toegangbijhoudingsautorisaties',
            type: 'inlinelijst',
            bron: 'ToegangBijhoudingsautorisatie',
            toonOpBewerken: true,
            nieuwReadonly: true,
            velden: [
                {name: 'Id', display: 'id'},
                {name: 'Geautoriseerde', display: 'geautoriseerde', type: 'select', bron: 'PartijRol'},
                {name: 'Ondertekenaar', display: 'ondertekenaar', type: 'select', bron: 'Partij'},
                {name: 'Transporteur', display: 'transporteur', type: 'select', bron: 'Partij'},
                {name: 'Datum ingang', display: 'datumIngang', type: 'datum'},
                {name: 'Datum einde', display: 'datumEinde', type: 'datum'},
                {name: 'Indicatie geblokkeerd', display: 'indicatieGeblokkeerd', type: 'checkboxJaNee'},
            ],
            relatie: 'bijhoudingsautorisatie',
            toonOpLijst: false
        },
    ],
    listUri: '/bijhoudingsautorisaties',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false,
    blijfOpWijzig: true
};
autautConfig.value("BijhoudingsautorisatieConfig", Bijhoudingsautorisatie);
beheerConfiguratie.setup(Bijhoudingsautorisatie);


var BijhoudingsautorisatieSoortAdministratieveHandeling = {
    titel: 'BijhoudingsautorisatieSoortAdministratieveHandeling',
    resourceNaam: 'BijhoudingsautorisatieSoortAdministratieveHandeling',
    resourceUrl: 'bijhoudingsautorisaties/:bijhoudingsautorisatie/bijhoudingsautorisatieSoortAdministratieveHandelingen/:id',
    loaderNaam: 'BijhoudingsautorisatieSoortAdministratieveHandelingLoader',
    contextParam: 'bijhoudingsautorisatieSoortAdministratieveHandeling',
    vastebreedte: true,
    kolommen: [
        {name: 'Id', display: 'id', toonOpBewerken: true, readonly: true},
        {name: 'Naam', display: 'naam', type: 'selectRef',  bron: 'SoortAdministratieveHandeling', required: true, readonly: true},
        {name: 'Geautoriseerd?', display: 'actief', type: 'checkboxJaNee'},
    ],
    listUri: '/bijhoudingsautorisatieSoortAdministratieveHandelingen',
    listTemplateUrl: 'views/generic/list.html',
    cache: false,
    geenDetail: true,
    blijfOpWijzig: false
};
autautConfig.value('BijhoudingsautorisatieSoortAdministratieveHandelingConfig', BijhoudingsautorisatieSoortAdministratieveHandeling);
beheerConfiguratie.setup(BijhoudingsautorisatieSoortAdministratieveHandeling);

var Dienst = {
    titel: 'Dienst',
    resourceNaam: 'Dienst',
    resourceUrl: 'leveringsautorisaties/:leveringsautorisatie/dienstbundels/:dienstbundel/diensten/:id',
    loaderNaam: 'DienstLoader',
    contextParam: 'dienst',
    vastebreedte: true,
    sortering: 'indicatieGeblokkeerd,datumIngang,asc',
    kolommen: [
        {name: 'Id', display: 'id', toonOpBewerken: true, readonly: true},
        {name: 'Soort', display: 'soort', filter: 'filterStelsel', sort: 'soort', type: 'selectRef', bron: 'DienstSoortDienst', required: true, readonly: true, nieuwReadonly: false},
        {name: 'Effect afnemerindicaties', display: 'effectAfnemerindicaties', type: 'select', bron: 'EffectAfnemerindicaties', readonly: true, nieuwReadonly: false},
        {name: 'Datum ingang', display: 'datumIngang', sort: 'datumIngang', type: 'datum', required: true},
        {name: 'Datum einde', display: 'datumEinde', type: 'datum'},
        {name: 'Indicatie geblokkeerd', display: 'indicatieGeblokkeerd', sort: 'indicatieGeblokkeerd', type: 'checkboxJaNee'},
        {name: 'Attenderingscriterium', display: 'attenderingscriterium', type: 'textArea', conditioneelParam: 'soort', conditioneelWaarde: 4},
        {name: 'Soort selectie', display: 'soortSelectie', type: 'selectRef', bron: 'SoortSelectie', required: true, toonOpLijst: false, toonOpBewerken: true, nieuwReadonly: true, conditioneelParam: 'soort', conditioneelWaarde: 12},
        {name: 'Eerste selectiedatum', display: 'eersteSelectiedatum', type:'datum', toonOpLijst: false, toonOpBewerken: true, nieuwReadonly: true, conditioneelParam: 'soort', conditioneelWaarde: 12},
        {name: 'Selectieinterval', display: 'selectieInterval', type: 'nummer', maxlength: 5, toonOpLijst: false, toonOpBewerken: true, nieuwReadonly: true, conditioneelParam: 'soort', conditioneelWaarde: 12},
        {name: 'Eenheid selectieinterval', display: 'eenheidSelectieInterval', type: 'selectRef', bron: 'EenheidSelectieInterval', toonOpLijst: false, toonOpBewerken: true, nieuwReadonly: true, conditioneelParam: 'soort', conditioneelWaarde: 12},
        {name: 'Nadere selectiecriterium', display: 'nadereSelectieCriterium', toonOpLijst: false, toonOpBewerken: true, nieuwReadonly: true, conditioneelParam: 'soort', conditioneelWaarde: 12},
        {name: 'Selectie peilmoment materieel resultaat', display: 'selectiePeilmomentMaterieelResultaat', type:'datum', toonOpLijst: false, toonOpBewerken: true, nieuwReadonly: true, conditioneelParam: 'soort', conditioneelWaarde: 12},
        {name: 'Selectie peilmoment formeel resultaat', display: 'selectiePeilmomentFormeelResultaat', type:'datumtijd', toonOpLijst: false, toonOpBewerken: true, nieuwReadonly: true, conditioneelParam: 'soort', conditioneelWaarde: 12},
        {name: 'Historievorm selectie', display: 'historieVorm', type: 'selectRef', bron: 'Historievorm', required: true, toonOpLijst: false, toonOpBewerken: true, nieuwReadonly: true, conditioneelParam: 'soort', conditioneelWaarde: 12},
        {name: 'Selectieresultaat controleren?', display: 'indicatieResultaatControleren', type: 'checkboxJaNee', required: true, toonOpLijst: false, toonOpBewerken: true, nieuwReadonly: true, conditioneelParam: 'soort', conditioneelWaarde: 12},
        {name: 'Maximaal aantal personen per selectiebestand', display: 'maxPersonenPerSelectie', type: 'nummer', maxlength: 10, toonOpLijst: false, toonOpBewerken: true, nieuwReadonly: true, conditioneelParam: 'soort', conditioneelWaarde: 12},
        {name: 'Maximale grootte selectiebestand', display: 'maxGrootteSelectie', type: 'nummer', maxlength: 10, toonOpLijst: false, toonOpBewerken: true, nieuwReadonly: true, conditioneelParam: 'soort', conditioneelWaarde: 12},
        {name: 'Verzend volledig bericht bij plaatsing afnemerindicatie na selectie?', display: 'indicatieVolledigBerichtBijAfnemerindicatieNaSelectie', type: 'checkboxJaNee', toonOpLijst: false, toonOpBewerken: true, nieuwReadonly: true, conditioneelParam: 'soort', conditioneelWaarde: 12},
        {name: 'Leverwijze selectie', display: 'leverwijzeSelectie', type: 'selectRef', bron: 'LeverwijzeSelectie', toonOpLijst: false, toonOpBewerken: true, nieuwReadonly: true, conditioneelParam: 'soort', conditioneelWaarde: 12},
    ],
    listUri: '/diensten',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false,
    blijfOpWijzig: true
};
autautConfig.value('DienstConfig', Dienst);
beheerConfiguratie.setup(Dienst);

var Dienstbundel = {
    titel: 'Dienstbundel',
    resourceNaam: 'Dienstbundel',
    resourceUrl: 'leveringsautorisaties/:leveringsautorisatie/dienstbundels/:id',
    loaderNaam: 'DienstbundelLoader',
    contextParam: 'dienstbundel',
    vastebreedte: true,
    resourceFilter: 'stelsel',
    sortering: 'indicatieGeblokkeerd,naam,asc',
    kolommen: [
        {name: 'Id', display: 'id', toonOpBewerken: true, readonly: true},
        {name: 'Naam', display: 'naam', sort: 'naam', required: true},
        {name: 'Datum ingang', display: 'datumIngang', type: 'datum', required: true},
        {name: 'Datum einde', display: 'datumEinde', type: 'datum'},
        {name: 'Nadere populatiebeperking', display: 'naderePopulatiebeperking', type: 'textArea'},
        {name: 'Indicatie nadere populatiebeperking volledig geconverteerd', display: 'indicatieNaderePopulatieBeperkingVolledigGeconverteerd', type: 'checkboxJaNee',toonOpBewerken: true, nieuwReadonly: true},
        {name: 'Toelichting', display: 'toelichting', maxlength: 50},
        {name: 'Indicatie geblokkeerd', display: 'indicatieGeblokkeerd', sort: 'indicatieGeblokkeerd', type: 'checkboxJaNee', toonOpBewerken: true},
        {
            name: 'Diensten',
            display: 'dienst',
            type: 'inlinelijst',
            bron: 'Dienst',
            toonOpBewerken: true,
            nieuwReadonly: true,
            velden: [
                {name: 'Id', display: 'id'},
                {name: 'Soort', display: 'soort', type: 'select',  bron: 'SoortDienst', required: true},
                {name: 'Effect afnemerindicaties', display: 'effectAfnemerindicaties', type: 'select',  bron: 'EffectAfnemerindicaties'},
                {name: 'Datum ingang', display: 'datumIngang', type: 'datum', required: true},
                {name: 'Datum einde', display: 'datumEinde', type: 'datum'},
                {name: 'Indicatie geblokkeerd', display: 'indicatieGeblokkeerd', sort: 'indicatieGeblokkeerd', type: 'checkboxJaNee'},
            ],
            relatie: 'dienstbundel',
            toonOpLijst: false
        },
        {
            name: 'Groepen',
            display: 'dienstbundelgroep',
            type: 'inlinelijst',
            bron: 'DienstbundelGroep',
            toonOpBewerken: true,
            conditioneelParam: 'stelsel',
            conditioneelWaarde: 1,
            nieuwReadonly: true,
            velden: [
                {name: 'Id', display: 'id'},
                {name: 'Groep', display: 'groepId', type: 'select',  bron: 'Element', required: true},
                {name: 'Indicatie formele historie', display: 'indicatieFormeleHistorie', type: 'checkboxJaNee'},
                {name: 'Indicatie materiele historie', display: 'indicatieMaterieleHistorie', type: 'checkboxJaNee'},
                {name: 'Indicatie verantwoording', display: 'indicatieVerantwoording', type: 'checkboxJaNee'},
            ],
            relatie: 'dienstbundel',
            toonOpLijst: false
        },
        {name: 'Lo3 Rubrieken', display: 'lo3rubrieken', type: 'inlineLijstArea', bron: 'Lo3Rubriek', relatie: 'dienstbundel', toonOpBewerken: true, conditioneelParam: 'stelsel', conditioneelWaarde: 2, nieuwReadonly: true, toonOpLijst: false },
    ],
    listUri: '/dienstbundels',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false,
    blijfOpWijzig: true
};
autautConfig.value('DienstbundelConfig', Dienstbundel);
beheerConfiguratie.setup(Dienstbundel);

var DienstbundelGroep = {
    titel: 'Groep',
    resourceNaam: 'DienstbundelGroep',
    resourceUrl: 'leveringsautorisaties/:leveringsautorisatie/dienstbundels/:dienstbundel/dienstbundelgroepen/:id',
    loaderNaam: 'DienstbundelGroepLoader',
    contextParam: 'dienstbundelgroep',
    vastebreedte: true,
    kolommen: [
        {name: 'Id', display: 'id', toonOpBewerken: true, readonly: true},
        {name: 'Groep', display: 'groepId', type: 'select',  bron: 'Groep', required: true, readonly: true, nieuwReadonly: false,  breedte: '10'},
        {name: 'Indicatie formele historie', display: 'indicatieFormeleHistorie', type: 'checkboxJaNee'},
        {name: 'Indicatie materiele historie', display: 'indicatieMaterieleHistorie', type: 'checkboxJaNee'},
        {name: 'Indicatie verantwoording', display: 'indicatieVerantwoording', type: 'checkboxJaNee'},
        {
            name: 'Attributen',
            display: 'attribuut',
            type: 'inlinekruislijst',
            bron: 'Attribuut',
            toonOpBewerken: true,
            nieuwReadonly: true,
            velden: [
                {name: 'Wordt geleverd?', display: 'actief', type: 'checkboxJaNee', readonly: true},
                {name: 'Attribuut', display: 'attribuutNaam', required: true},
                {name: 'Soort', display: 'soort', type: 'select',  bron: 'SoortElementAutorisatie', required: true},
            ],
            relatie: 'dienstbundelgroep',
            geenDetail: true,
            toonOpLijst: false
        },
    ],
    listUri: '/dienstbundelgroepen',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false,
    blijfOpWijzig: true,
    verwijderen: true
};
autautConfig.value('DienstbundelGroepConfig', DienstbundelGroep);
beheerConfiguratie.setup(DienstbundelGroep);

var DienstSoortDienst = {
    titel: 'Soort Dienst',
    resourceNaam: 'DienstSoortDienst',
    resourceUrl: 'soortdienst/soorten/:id',
    loaderNaam: 'DienstSoortDienstLoader',
    contextParam: 'soortDienst',
    resourceFilter: 'stelsel',
    vastebreedte: true,
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Soort Dienst', display: 'naam'},
    ],
    listUri: '/soortdienst/soorten',
    listTemplateUrl: 'views/generic/list.html',
    cache: true,
    geenDetail: true,
    blijfOpWijzig: false
};
autautConfig.value('DienstSoortDienstConfig', DienstSoortDienst);
beheerConfiguratie.setup(DienstSoortDienst);

var EenheidSelectieInterval = {
    titel: 'Eenheid Selectie Interval',
    loaderNaam: 'EenheidSelectieIntervalLoader',
    resourceNaam: 'EenheidSelectieInterval',
    resourceUrl: 'eenheidselectieinterval/:id',
    listUri: '/eenheidselectieinterval',
    listTemplateUrl: 'views/generic/list.html',
    contextParam: 'eenheidSelectieInterval',
    vastebreedte: true,
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Eenheid selectie interval', display: 'naam'},
    ],
    cache: true,
    geenDetail: true,
    blijfOpWijzig: false
};
autautConfig.value('EenheidSelectieIntervalConfig', EenheidSelectieInterval);
beheerConfiguratie.setup(EenheidSelectieInterval);

var EffectAfnemerindicaties = {
    titel: 'Effect Afnemerindicaties',
    resourceNaam: 'EffectAfnemerindicaties',
    resourceUrl: 'effectafnemerindicaties/:id',
    loaderNaam: 'EffectAfnemerindicatiesLoader',
    listUri: '/effectafnemerindicaties/list',
    listTemplateUrl: 'views/generic/list.html',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Effect Afnemerindicaties', display: 'naam'},
        {name: 'Omschrijving', display: 'omschrijving'},
    ],
    geenDetail: true
};
autautConfig.value('EffectAfnemerindicatiesConfig', EffectAfnemerindicaties);
beheerConfiguratie.setup(EffectAfnemerindicaties);

var BijhouderFiatteringsuitzondering = {
    titel: 'Bijhouderfiatteringsuitzondering',
    resourceNaam: 'BijhouderFiatteringsuitzondering',
    resourceUrl: 'bijhouderfiatteringsuitzonderingen/:id',
    loaderNaam: 'BijhouderFiatteringsuitzonderingLoader',
    sortering: 'bijhouder.partij.naam,bijhouderBijhoudingsvoorstel.partij.naam,asc',
    kolommen: [
        {name: 'Id', display: 'id', toonOpBewerken: true, readonly: true},
        {name: 'Bijhouder', display: 'bijhouder', type: 'selectRef', bron: 'PartijRol', sort: 'bijhouder.partij.naam', filter: 'bijhouder', breedte: '10', required: true, readonly: true, nieuwReadonly: false},
        {name: 'Datum ingang', display: 'datumIngang', type: 'datum', filter: 'datumIngang', required: true},
        {name: 'Datum einde', display: 'datumEinde', type: 'datum', filter: 'datumEinde' },
        {name: 'Bijhouder bijhoudingsvoorstel', display: 'bijhouderBijhoudingsvoorstel', sort: 'bijhouderBijhoudingsvoorstel.partij.naam', filter: 'bijhouderBijhoudingsvoorstel', type: 'selectRef', bron: 'PartijRol',  breedte: '10', required: false, nieuwReadonly: false},
        {name: 'Soort document', display: 'soortDocument', type: 'selectRef', bron: 'SoortDocument', filter: 'soortDocument', breedte: '10', required: false, nieuwReadonly: false},
        {name: 'Soort administratieve handeling', display: 'soortAdministratieveHandeling', filter: 'soortAdministratieveHandelingId', type: 'selectRef', bron: 'SoortAdministratieveHandeling',  breedte: '10', required: false, nieuwReadonly: false},
        {name: 'Indicatie geblokkeerd', display: 'indicatieGeblokkeerd', type: 'checkboxJaNee', filter: 'indicatieGeblokkeerd'},
    ],
    listUri: '/bijhouderfiatteringsuitzonderingen',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false,
    blijfOpWijzig: false
};
autautConfig.value('BijhouderFiatteringsuitzonderingConfig', BijhouderFiatteringsuitzondering);
beheerConfiguratie.setup(BijhouderFiatteringsuitzondering);

var Groep = {
    titel: 'Groep',
    resourceNaam: 'Groep',
    resourceUrl: 'element/groep',
    resourceFilter: {filterSoort: "2"},
    loaderNaam: 'GroepLoader',
    contextParam: 'groep',
    vastebreedte: true,
    kolommen: [
        {name: 'Groep', display: 'naam', filter: 'filterSoort', type: 'selectRef',  bron: 'Element', required: true, readonly: true},
    ],
    listUri: '/groepen',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: true,
    blijfOpWijzig: false
};
autautConfig.value('GroepConfig', Groep);
beheerConfiguratie.setup(Groep);

var Leveringsautorisatie = {
    titel: 'Leveringsautorisatie',
    resourceNaam: 'Leveringsautorisatie',
    resourceUrl: 'leveringsautorisaties/:id',
    loaderNaam: 'LeveringsautorisatieLoader',
    contextParam: 'leveringsautorisatie',
    extraContextParams: ['stelsel'],
    vastebreedte: true,
    kolommen: [
        {name: 'Id', display: 'id', readonly: true, toonOpBewerken: true},
        {name: 'Stelsel', display: 'stelsel', sort: 'stelsel', filter: 'filterStelsel', type:'select', bron: 'Stelsel', required: true, readonly: true, nieuwReadonly: false},
        {name: 'Modelautorisatie?', display: 'indicatieModelAutorisatie', sort: 'indicatieModelautorisatie', filter: 'filterModelAutorisatie', type: 'checkboxJaNee', readonly: true, nieuwReadonly: false},
        {name: 'Naam', display: 'naam', sort: 'naam', filter: 'filterNaam', required: true, maxlength: 80, lijstbreedte: 4},
        {name: 'Protocolleringsniveau', display: 'protocolleringsniveau', type:'selectCodeOmschrijving', bron: 'Protocolleringsniveau', required: true, toonOpLijst: false},
        {name: 'Alias soort administratieve handeling leveren?', display: 'indicatieAliasLeveren', type: 'checkboxJaNee', toonOpLijst: false},
        {name: 'Datum ingang', display: 'datumIngang', sort: 'datumIngang', filter: 'filterDatumIngang', type:'datum', required: true },
        {name: 'Datum einde', display: 'datumEinde', sort: 'datumEinde', filter: 'filterDatumEinde', type: 'datum'},
        {name: 'Populatiebeperking', display: 'populatieBeperking', toonOpLijst: false, type:'textArea'},
        {name: 'Toelichting', display: 'toelichting', toonOpLijst: false},
        {name: 'Geautoriseerde', filter: 'filterGeautoriseerde',type: 'selectRef', bron: 'PartijRol', toonOpLijst: false, maxlength: 80, lijstbreedte: 4},
        {name: 'Ondertekenaar', filter: 'filterOndertekenaar',type: 'selectRef', bron: 'Partij', toonOpLijst: false, maxlength: 80, lijstbreedte: 4},
        {name: 'Transporteur', filter: 'filterTransporteur',type: 'selectRef', bron: 'Partij', toonOpLijst: false, maxlength: 80, lijstbreedte: 4},
        {name: 'Dienstbundel', filter: 'filterDienstbundel', toonOpLijst: false, maxlength: 80, lijstbreedte: 4},
        {name: 'Geblokkeerd?', display: 'indicatieGeblokkeerd', sort: 'indicatieGeblokkeerd', filter: 'filterGeblokkeerd', type: 'checkboxJaNee'},
        {name: 'Soort dienst', display: 'naam', bron:'SoortDienst', type: 'select', toonOpLijst: false, toonOpBewerken: false, filter: 'filterSoortDienst' },
        {
            name: 'Toegangen',
            display: 'toegangen',
            type: 'inlinelijst',
            bron: 'ToegangLeveringsautorisatie',
            toonOpBewerken: true,
            nieuwReadonly: true,
            velden: [
                {name: 'Id', display: 'id'},
                {name: 'Geautoriseerde', display: 'geautoriseerde', type: 'select', bron: 'PartijRol'},
                {name: 'Ondertekenaar', display: 'ondertekenaar', type: 'select', bron: 'Partij'},
                {name: 'Transporteur', display: 'transporteur', type: 'select', bron: 'Partij'},
                {name: 'Datum ingang', display: 'datumIngang', type: 'datum'},
                {name: 'Datum einde', display: 'datumEinde', type: 'datum'},
                {name: 'Indicatie geblokkeerd', display: 'indicatieGeblokkeerd', type: 'checkboxJaNee'},
            ],
            relatie: 'leveringsautorisatie',
            toonOpLijst: false
        },
        {
            name: 'Dienstbundels',
            display: 'dienstbundels',
            type: 'inlinelijst',
            bron: 'Dienstbundel',
            toonOpBewerken: true,
            nieuwReadonly: true,
            velden: [
                {name: 'Id', display: 'id', toonOpBewerken: true, readonly: true},
                {name: 'Naam', display: 'naam', required: true},
                {name: 'Datum ingang', display: 'datumIngang', type: 'datum', required: true},
                {name: 'Datum einde', display: 'datumEinde', type: 'datum'},
                {name: 'Indicatie geblokkeerd', display: 'indicatieGeblokkeerd', type: 'checkboxJaNee'},
            ],
            relatie: 'leveringsautorisatie',
            toonOpLijst: false
        },
    ],
    listUri: '/leveringsautorisaties',
    listTemplateUrl: 'views/generic/list.html',
    geenDetail: false,
    blijfOpWijzig: true
};
autautConfig.value("LeveringsautorisatieConfig", Leveringsautorisatie);
beheerConfiguratie.setup(Leveringsautorisatie);

var LeverwijzeSelectie = {
    titel: 'LeverwijzeSelectie',
    loaderNaam: 'LeverwijzeSelectieLoader',
    resourceNaam: 'LeverwijzeSelectie',
    resourceUrl: 'leverwijzeselectie/:id',
    listUri: '/leverwijzeselectie',
    listTemplateUrl: 'views/generic/list.html',
    contextParam: 'leverwijzeSelectie',
    vastebreedte: true,
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Leverwijze selectie', display: 'naam'},
    ],
    cache: true,
    geenDetail: true,
    blijfOpWijzig: false
};
autautConfig.value('LeverwijzeSelectieConfig', LeverwijzeSelectie);
beheerConfiguratie.setup(LeverwijzeSelectie);

var Lo3Rubriek = {
    titel: 'Lo3 Rubrieken',
    resourceNaam: 'Lo3Rubriek',
    resourceUrl: 'leveringsautorisaties/:leveringsautorisatie/dienstbundels/:dienstbundel/lo3rubrieken/:id',
    loaderNaam: 'Lo3RubriekLoader',
    kolommen: [],
    geenDetail: false
};
autautConfig.value('Lo3RubriekConfig', Lo3Rubriek);
beheerConfiguratie.setup(Lo3Rubriek);

var Protocolleringsniveau = {
    titel: 'Protocolleringsniveau',
    resourceNaam: 'Protocolleringsniveau',
    resourceUrl: 'protocolleringsniveau/:id',
    loaderNaam: 'ProtocolleringsniveauLoader',
    listUri: '/protocolleringsniveau/list',
    listTemplateUrl: 'views/generic/list.html',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Code', display: 'code'},
        {name: 'Protocolleringsniveau', display: 'naam'},
        {name: 'Omschrijving', display: 'omschrijving'},
        {name: 'Datum aanvang geldigheid', display: 'datumAanvangGeldigheid', type:'datum' },
        {name: 'Datum einde geldigheid', display: 'datumEindeGeldigheid', type:'datum' },
    ],
    geenDetail: true
};
autautConfig.value('ProtocolleringsniveauConfig', Protocolleringsniveau);
beheerConfiguratie.setup(Protocolleringsniveau);

var SoortDienst = {
    titel: 'Soort Dienst',
    resourceNaam: 'SoortDienst',
    resourceUrl: 'soortdienst/:id',
    loaderNaam: 'SoortDienstLoader',
    listUri: '/soortdienst/list',
    listTemplateUrl: 'views/generic/list.html',
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Soort Dienst', display: 'naam'},
    ],
    geenDetail: true
};
autautConfig.value('SoortDienstConfig', SoortDienst);
beheerConfiguratie.setup(SoortDienst);

var SoortSelectie = {
    titel: 'Soort Selectie',
    loaderNaam: 'SoortSelectieLoader',
    resourceNaam: 'SoortSelectie',
    resourceUrl: 'soortselectie/:id',
    listUri: '/soortselectie',
    listTemplateUrl: 'views/generic/list.html',
    contextParam: 'soortSelectie',
    vastebreedte: true,
    kolommen: [
        {name: 'Id', display: 'id'},
        {name: 'Soort Selectie', display: 'naam'},
    ],
    cache: true,
    geenDetail: true,
    blijfOpWijzig: false
};
autautConfig.value('SoortSelectieConfig', SoortSelectie);
beheerConfiguratie.setup(SoortSelectie);

var ToegangBijhoudingsautorisatie = {
    titel: 'ToegangBijhoudingsautorisatie',
    resourceNaam: 'ToegangBijhoudingsautorisatie',
    resourceUrl: 'bijhoudingsautorisaties/:bijhoudingsautorisatie/toegangbijhoudingsautorisaties/:id',
    contextParam: 'toegangbijhoudingsautorisatie',
    sortering: 'datumIngang,datumEinde,geautoriseerde,desc',
    kolommen: [
        {name: 'Id', display: 'id', toonOpBewerken: true, readonly: true},
        {name: 'Geautoriseerde', display: 'geautoriseerde', type: 'selectRef', bron: 'PartijRol', required: true, toonOpBewerken: true, toonOpLijst: false, breedte: '10', readonly: true, nieuwReadonly: false},
        {name: 'Ondertekenaar', display: 'ondertekenaar', type: 'selectRef', bron: 'Partij', breedte: '10', required: false, readonly: true, nieuwReadonly: false},
        {name: 'Transporteur', display: 'transporteur', type: 'selectRef', bron: 'Partij',  breedte: '10', required: false, readonly: true, nieuwReadonly: false},
        {name: 'Datum ingang', display: 'datumIngang', type: 'datum', required: true},
        {name: 'Datum einde', display: 'datumEinde', type: 'datum'},
        {name: 'Afleverpunt', display: 'afleverpunt'},
        {name: 'Indicatie geblokkeerd', display: 'indicatieGeblokkeerd', type: 'checkboxJaNee'},
    ],
    geenDetail: false,
    blijfOpWijzig: false
};
autautConfig.value('ToegangBijhoudingsautorisatieConfig', ToegangBijhoudingsautorisatie);
beheerConfiguratie.setup(ToegangBijhoudingsautorisatie);

var ToegangLeveringsautorisatie = {
    titel: 'Toegang',
    resourceNaam: 'ToegangLeveringsautorisatie',
    resourceUrl: 'leveringsautorisaties/:leveringsautorisatie/toegangen/:id',
    contextParam: 'toegang',
    sortering: 'datumIngang,datumEinde,geautoriseerde,desc',
    kolommen: [
        {name: 'Id', display: 'id', toonOpBewerken: true, readonly: true},
        {name: 'Geautoriseerde', display: 'geautoriseerde', type: 'selectRef', bron: 'PartijRol', required: true, toonOpBewerken: true, toonOpLijst: false, breedte: '10', readonly: true, nieuwReadonly: false},
        {name: 'Ondertekenaar', display: 'ondertekenaar', type: 'selectRef', bron: 'Partij', breedte: '10', required: false, readonly: true, nieuwReadonly: false},
        {name: 'Transporteur', display: 'transporteur', type: 'selectRef', bron: 'Partij',  breedte: '10', required: false, readonly: true, nieuwReadonly: false},
        {name: 'Datum ingang', display: 'datumIngang', type: 'datum', required: true},
        {name: 'Datum einde', display: 'datumEinde', type: 'datum'},
        {name: 'Nadere populatiebeperking', display: 'naderePopulatiebeperking', type: 'textArea' },
        {name: 'Afleverpunt', display: 'afleverpunt'},
        {name: 'Indicatie geblokkeerd', display: 'indicatieGeblokkeerd', type: 'checkboxJaNee'},
    ],
    geenDetail: false,
    blijfOpWijzig: false
};
autautConfig.value('ToegangLeveringsautorisatieConfig', ToegangLeveringsautorisatie);
beheerConfiguratie.setup(ToegangLeveringsautorisatie);
