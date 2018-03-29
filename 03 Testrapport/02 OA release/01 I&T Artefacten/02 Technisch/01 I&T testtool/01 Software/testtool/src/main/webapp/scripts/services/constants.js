// context path

// sit-ap05
// assume backend is on same server
var host = location.hostname;
if (location.hostname == "") {
	host = "tit-dok01.modernodam.nl";
}

var URL_CONTEXT_PATH = "http://" + host + "/cgi-bin/testtool";
// sit-db04 (staging)
// var URL_CONTEXT_PATH = "http://sit-db04.modernodam.nl/cgi-bin/testtool";
// local
// var URL_CONTEXT_PATH = "http://localhost/cgi-bin2/testtool";

/* TIDY */
// bericht constants
var BERICHT_TOTAL_LENGTH_LENGTH = 5;
var BERICHT_GROUP_LENGTH = 2;
var BERICHT_SUB_LENGTH = 4;
var BERICHT_LENGTH_LENGTH = 3;
var BERICHT_HEADER_RANDOM_LENGTH = 8;
var BERICHT_HEADER_TYPE_LENGTH = 4
var BERICHT_HEADER_DATETIME_LENGTH = 17;
var BERICHT_HEADER_DATE_LENGTH = 8;
var BERICHT_HEADER_ANR_LENGTH = 10;
var BERICHT_HEADER_STATUS_LENGTH = 1;

// lg01
var LG01_BERICHT_HEADER = { 
					"random" : BERICHT_HEADER_RANDOM_LENGTH, 
					"type" : BERICHT_HEADER_TYPE_LENGTH,
					"datetime" : BERICHT_HEADER_DATETIME_LENGTH,
					"anr" : BERICHT_HEADER_ANR_LENGTH,
					"oldanr" : BERICHT_HEADER_ANR_LENGTH 
					};
var LG01_HEADER_LENGTH = BERICHT_HEADER_RANDOM_LENGTH +
					BERICHT_HEADER_TYPE_LENGTH +
					BERICHT_HEADER_DATETIME_LENGTH +
					BERICHT_HEADER_ANR_LENGTH +
					BERICHT_HEADER_ANR_LENGTH;

var GV01_BERICHT_HEADER = { 
					"random" : BERICHT_HEADER_RANDOM_LENGTH, 
					"type" : BERICHT_HEADER_TYPE_LENGTH,
					"anr" : BERICHT_HEADER_ANR_LENGTH
					};
var GV01_HEADER_LENGTH = BERICHT_HEADER_RANDOM_LENGTH + 
					BERICHT_HEADER_TYPE_LENGTH +
					BERICHT_HEADER_ANR_LENGTH;

var AG_BERICHT_HEADER = {
					"random" : BERICHT_HEADER_RANDOM_LENGTH, 
					"type" : BERICHT_HEADER_TYPE_LENGTH,
					"status" : BERICHT_HEADER_STATUS_LENGTH,
					"date" : BERICHT_HEADER_DATE_LENGTH
					};
var AG_HEADER_LENGTH = BERICHT_HEADER_RANDOM_LENGTH +
					BERICHT_HEADER_TYPE_LENGTH +
					BERICHT_HEADER_STATUS_LENGTH +
					BERICHT_HEADER_DATE_LENGTH;

var NG_BERICHT_HEADER = {
					"random" : BERICHT_HEADER_RANDOM_LENGTH, 
					"type" : BERICHT_HEADER_TYPE_LENGTH
					};

var NG_HEADER_LENGTH = BERICHT_HEADER_RANDOM_LENGTH +
					BERICHT_HEADER_TYPE_LENGTH;

var WA_BERICHT_HEADER = {
					"random" : BERICHT_HEADER_RANDOM_LENGTH, 
					"type" : BERICHT_HEADER_TYPE_LENGTH,
					"anr" : BERICHT_HEADER_ANR_LENGTH,
					"date" : BERICHT_HEADER_DATE_LENGTH
					};

var WA_HEADER_LENGTH = BERICHT_HEADER_RANDOM_LENGTH +
					BERICHT_HEADER_TYPE_LENGTH + 
					BERICHT_HEADER_ANR_LENGTH + 
					BERICHT_HEADER_DATE_LENGTH;

var LG01_DEFAULT_HEADER = "00000000Lg012012120115301500012345678900000000000";

// csv constants
var CSV_SKIP_LINES = 2;

// presentation constants
var PRESENTATION_GROUP_LENGTH = 2;
var PRESENTATION_SUB_LENGTH = 4;

/* RUN */
// run constants
var START_ISC_URI = URL_CONTEXT_PATH + "/start_isc";
var START_BEHEER_URI = URL_CONTEXT_PATH + "/start_beheer";
var RUN_LEVAUTS_URI = URL_CONTEXT_PATH + "/run_levauts";
var STOP_ISC_URI = URL_CONTEXT_PATH + "/stop_isc";
var CLEAN_ACTUALS = URL_CONTEXT_PATH + "/clean_actuals";
var CLEAN_SOA_URI = URL_CONTEXT_PATH + "/clean_soa";
var COPY_BRP_DB_URI = URL_CONTEXT_PATH + "/copy_brp_db";
var RUN_AFTERBURNER_URI = URL_CONTEXT_PATH + "/run_afterburner";
var RUN_MUTATIES_URI = URL_CONTEXT_PATH + "/run_mutaties";
var OPHALEN_RESULTATEN_URI = URL_CONTEXT_PATH + "/ophalen_resultaten_all";
var OPHALEN_VOISC_URI = URL_CONTEXT_PATH + "/fill_actual_voisc";
var OPHALEN_BRP_URI = URL_CONTEXT_PATH + "/fill_actual_brp";
var UPDATE_URI = URL_CONTEXT_PATH + "/update";

var STOP_IV_URI = URL_CONTEXT_PATH + "/stop_iv";
var START_IV_URI = URL_CONTEXT_PATH + "/start_iv";

var CREATE_IV_URI = URL_CONTEXT_PATH + "/create_initvulling";
var RUN_IV_URI = URL_CONTEXT_PATH + "/run_initvulling";
var TRUNCATE_IV_URI = URL_CONTEXT_PATH + "/truncate_gbav_initvul";
var RUN_TC_URI = URL_CONTEXT_PATH + "/run_tc";

var BACKUP_DB_URI = URL_CONTEXT_PATH + "/backup_db";
var MERGE_RESULTS_URI = URL_CONTEXT_PATH + "/merge_results";

var COPY_DATA_SET_URI = URL_CONTEXT_PATH + "/proeftuin/copy_data_set";
var CLEAN_GBAV_DB_URI = URL_CONTEXT_PATH + "/proeftuin/clean_gbav_db";
var READ_INTO_GBAV_URI = URL_CONTEXT_PATH + "/proeftuin/read_into_gbav";

var DUMMY_URL = URL_CONTEXT_PATH + "/boeh";

var LO3_ELEMENTS = {
	"0110" : "A-nummer",
	"0120" : "Burgerservicenummer",
	"0210" : "Voornamen",
	"0220" : "Adellijke titel/predikaat",
	"0230" : "Voorvoegsel geslachtsnaam",
	"0240" : "Geslachtsnaam",
	"0310" : "Geboortedatum",
	"0320" : "Geboorteplaats",
	"0330" : "Geboorteland",
	"0410" : "Geslachtsaanduiding",
	"0510" : "Nationaliteit",
	"0610" : "Datum huwelijkssluiting/aangaan geregistreerd partnerschap",
	"0620" : "Plaats huwelijkssluiting/aangaan geregistreerd partnerschap",
	"0630" : "Land huwelijkssluiting/aangaan geregistreerd partnerschap",
	"0710" : "Datum ontbinding huwelijk/geregistreerd partnerschap",
	"0720" : "Plaats ontbinding huwelijk/geregistreerd partnerschap",
	"0730" : "Land ontbinding huwelijk/geregistreerd partnerschap",
	"0740" : "Reden ontbinding huwelijk/geregistreerd partnerschap",
	"0810" : "Datum overlijden",
	"0820" : "Plaats overlijden",
	"0830" : "Land overlijden",
	"0910" : "Gemeente van inschrijving",
	"0920" : "Datum inschrijving",
	"1010" : "Functie adres",
	"1020" : "Gemeentedeel",
	"1030" : "Datum aanvang adreshouding",
	"1110" : "Straatnaam",
	"1115" : "Naam openbare ruimte",
	"1120" : "Huisnummer",
	"1130" : "Huisletter",
	"1140" : "Huisnummertoevoeging",
	"1150" : "Aanduiding bij huisnummer",
	"1160" : "Postcode",
	"1170" : "Woonplaatsnaam",
	"1180" : "Identificatiecode verblijfplaats",
	"1190" : "Identificatiecode nummeraanduiding",
	"1210" : "Locatiebeschrijving",
	"1310" : "Land adres buitenland",
	"1320" : "Datum aanvang adres buitenland",
	"1330" : "Regel 1 adres buitenland",
	"1340" : "Regel 2 adres buitenland",
	"1350" : "Regel 3 adres buitenland",
	"1410" : "Land vanwaar ingeschreven",
	"1420" : "Datum vestiging in Nederland",
	"1510" : "Soort verbintenis",
	"2010" : "Vorig A-nummer",
	"2020" : "Volgend A-nummer",
	"3110" : "Aanduiding Europees kiesrecht",
	"3120" : "Datum verzoek of mededeling Europees kiesrecht",
	"3130" : "Einddatum uitsluiting Europees kiesrecht",
	"3210" : "Indicatie gezag minderjarige",
	"3310" : "Indicatie curateleregister",
	"3510" : "Soort Nederlands reisdocument",
	"3520" : "Nummer Nederlands reisdocument",
	"3530" : "Datum uitgifte Nederlands reisdocument",
	"3540" : "Autoriteit van afgifte Nederlands reisdocument",
	"3550" : "Datum einde geldigheid Nederlands reisdocument",
	"3560" : "Datum inhouding dan wel vermissing Nederlands reisdocument",
	"3570" : "Aanduiding inhouding dan wel vermissing Nederlands reisdocument",
	"3610" : "Signalering met betrekking tot verstrekken Nederlands reisdocument",
	"3810" : "Aanduiding uitgesloten kiesrecht",
	"3820" : "Einddatum uitsluiting kiesrecht",
	"3910" : "Aanduiding verblijfstitel",
	"3920" : "Datum einde verblijfstitel",
	"3930" : "Ingangsdatum verblijfstitel",
	"4010" : "Afnemersindicatie",
	"4210" : "Aantekening",
	"6110" : "Aanduiding naamgebruik",
	"6210" : "Datum ingang familierechtelijke betrekking",
	"6310" : "Reden opname nationaliteit",
	"6410" : "Reden beëindigen nationaliteit",
	"6510" : "Aanduiding bijzonder Nederlanderschap",
	"6620" : "Datum ingang blokkering PL",
	"6710" : "Datum opschorting bijhouding",
	"6720" : "Omschrijving reden opschorting bijhouding",
	"6810" : "Datum eerste inschrijving GBA/RNI",
	"6910" : "Gemeente waar de PK zich bevindt",
	"7010" : "Indicatie geheim",
	"7110" : "Datum verificatie",
	"7120" : "Omschrijving verificatie",
	"7210" : "Omschrijving van de aangifte adreshouding",
	"7510" : "Indicatie document",
	"8010" : "Versienummer",
	"8020" : "Datumtijdstempel",
	"8110" : "Registergemeente akte",
	"8120" : "Aktenummer",
	"8210" : "Gemeente document",
	"8220" : "Datum document",
	"8230" : "Beschrijving document",
	"8310" : "Aanduiding gegevens in onderzoek",
	"8320" : "Datum ingang onderzoek",
	"8330" : "Datum einde onderzoek",
	"8410" : "Indicatie onjuist, dan wel strijdigheid met de openbare orde",
	"8510" : "Ingangsdatum geldigheid",
	"8610" : "Datum van opneming",
	"8710" : "PK-gegevens volledig meegeconverteerd",
	"8810" : "RNI-deelnemer",
	"8820" : "Omschrijving verdrag",
	"3580" : "Lengte houder",
	"3710" : "Aanduiding bezit buitenlands reisdocument",
	"7310" : "EU-persoonsnummer"
};

var LO3_GROUPS = {
	"01" : "Persoon",
	"51" : "Persoon (historie)",
	"02" : "Ouder1",
	"52" : "Ouder1 (historie)",
	"03" : "Ouder2",
	"53" : "Ouder2 (historie)",
	"04" : "Nationaliteit",
	"54" : "Nationaliteit (historie)",
	"05" : "Huwelijk/geregistreerd partnerschap",
	"55" : "Huwelijk/geregistreerd partnerschap (historie)",
	"06" : "Overlijden",
	"56" : "Overlijden (historie)",
	"07" : "Inschrijving",
	"08" : "Verblijfplaats",
	"58" : "Verblijfplaats (historie)",
	"09" : "Kind",
	"59" : "Kind (historie)",
	"10" : "Verblijfstitel",
	"60" : "Verblijfstitel (historie)",
	"11" : "Gezagsverhouding",
	"61" : "Gezagsverhouding (historie)",
	"12" : "Reisdocument",
	"13" : "Kiesrecht",
	"14" : "Afnemersindicatie persoonslijst ",
	"64" : "Afnemersindicatie persoonslijst (historie)",
	"15" : "Aantekening"
};

var DEFAULT_PASSWORD = "Blup123";

var IV_TYPES = ['pl', 'aut', 'ai', 'proto']; 

var LABEL_ENABLED = "label label-info";
var LABEL_IN_PROGRESS = "label label-warning";
var LABEL_SUCCESS = "label label-success";
var LABEL_DISABLED = "label label-default";				
var LABEL_ERROR = "label label-danger";				

var STATUS_RUN = "run";
var STATUS_QUERY = "run";

var BRP_LEVERING_HEADERS = {
	"lvg_synVerwerkPersoon.stuurgegevens" : "Bericht gegevens",
	"lvg_synVerwerkPersoon.parameters" : "Bericht gegevens",
	"lvg_synVerwerkPersoon.meldingen" : "Bericht gegevens",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.afgeleidAdministratief" : "Afgeleid Administratief",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.identificatienummers" : "Identificatienummers",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.samengesteldeNaam" : "Samengestelde naam",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.geboorte" : "Geboorte",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.geslachtsaanduiding" : "Geslachtsaanduiding",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.inschrijving" : "Inschrijving",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.nummerverwijzing" : "Nummerverwijzing",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.bijhouding" : "Bijhouding",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.overlijden" : "Overlijden",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.voornamen.voornaam" : "Voornamen",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.geslachtsnaamcomponenten.geslachtsnaamcomponent" : "Geslachtsnaamcomponenten",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.naamgebruik" : "Naamgebruik",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.adressen.adres" : "Adressen",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.nationaliteiten.nationaliteit" : "Nationaliteiten",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.buitenlandsPersoonsnummers" : "Buitenlands persoonsnummer",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.indicaties" : "Indicaties",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.verstrekkingsbeperkingen.verstrekkingsbeperking" : "Verstrekkingsbeperkingen",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.onderzoeken.onderzoek.gegevensInOnderzoek.gegevenInOnderzoek" : "Gegeven in onderzoek",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.onderzoeken.onderzoek.gegevensInOnderzoek" : "Gegevens in onderzoek",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.onderzoeken.onderzoek.onderzoek" : "Onderzoek",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.onderzoeken.onderzoek" : "Onderzoek",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.administratieveHandelingen.administratieveHandeling.gedeblokkeerdeMeldingen.gedeblokkeerdeMelding" : "Gedeblokkeerde meldingen",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.administratieveHandelingen.administratieveHandeling.bronnen.bron.document" : "Bron",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.administratieveHandelingen.administratieveHandeling.bronnen.bron" : "Bron",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.administratieveHandelingen.administratieveHandeling.bronnen" : "Bronnen",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.administratieveHandelingen.administratieveHandeling.bijgehoudenActies.actie.bronnen.bron" : "Actie",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.administratieveHandelingen.administratieveHandeling.bijgehoudenActies.actie.bronnen" : "Actie",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.administratieveHandelingen.administratieveHandeling.bijgehoudenActies.actie" : "Actie",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.administratieveHandelingen.administratieveHandeling.bijgehoudenActies" : "Acties",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.administratieveHandelingen.administratieveHandeling" : "Administratieve handeling",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.migratie" : "Migratie",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.verblijfsrecht" : "Verblijfsrecht",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.reisdocumenten.reisdocument" : "Reisdocumenten",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.uitsluitingKiesrecht" : "Uitsluiting kiesrecht",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.deelnameEUVerkiezingen" : "Deelname EU verkiezingen",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.persoonskaart" : "Persoonskaart",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.afnemerindicaties.afnemerindicatie" : "Afnemerindicaties",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.verificaties.verificatie" : "Verificaties",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.partner.huwelijk.betrokkenheden.partner.persoon.identificatienummers" : "Gegevens huwelijkspartner",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.partner.huwelijk.betrokkenheden.partner.persoon.samengesteldeNaam" : "Gegevens huwelijkspartner",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.partner.huwelijk.betrokkenheden.partner.persoon.geboorte" : "Gegevens huwelijkspartner",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.partner.huwelijk.betrokkenheden.partner.persoon.geslachtsaanduiding" : "Gegevens huwelijkspartner",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.partner.huwelijk.betrokkenheden.partner.persoon" : "Gegevens huwelijkspartner",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.partner.huwelijk.betrokkenheden.partner" : "Gegevens huwelijkspartner",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.partner.huwelijk.relatie" : "Betrokkenheid huwelijk",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.partner.huwelijk" : "Betrokkenheid huwelijk",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.partner.geregistreerdPartnerschap.betrokkenheden.partner.persoon.identificatienummers" : "Gegevens geregistreerd partner",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.partner.geregistreerdPartnerschap.betrokkenheden.partner.persoon.samengesteldeNaam" : "Gegevens geregistreerd partner",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.partner.geregistreerdPartnerschap.betrokkenheden.partner.persoon.geboorte" : "Gegevens geregistreerd partner",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.partner.geregistreerdPartnerschap.betrokkenheden.partner.persoon.geslachtsaanduiding" : "Gegevens geregistreerd partner",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.partner.geregistreerdPartnerschap.betrokkenheden.partner.persoon" : "Gegevens geregistreerd partner",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.partner.geregistreerdPartnerschap.betrokkenheden.partner" : "Gegevens geregistreerd partner",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.partner.geregistreerdPartnerschap.relatie" : "Betrokkenheid geregistreerd partnerschap",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.partner.geregistreerdPartnerschap" : "Betrokkenheid geregistreerd partnerschap",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.kind.familierechtelijkeBetrekking.betrokkenheden.ouder.ouderschap" : "Gegevens ouder",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.kind.familierechtelijkeBetrekking.betrokkenheden.ouder.ouderlijkGezag" : "Gegevens ouder",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.kind.familierechtelijkeBetrekking.betrokkenheden.ouder.persoon.geslachtsaanduiding" : "Gegevens ouder",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.kind.familierechtelijkeBetrekking.betrokkenheden.ouder.persoon.geboorte" : "Gegevens ouder",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.kind.familierechtelijkeBetrekking.betrokkenheden.ouder.persoon.samengesteldeNaam" : "Gegevens ouder",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.kind.familierechtelijkeBetrekking.betrokkenheden.ouder.persoon.identificatienummers" : "Gegevens ouder",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.kind.familierechtelijkeBetrekking.betrokkenheden.ouder.persoon" : "Gegevens ouder",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.kind.familierechtelijkeBetrekking.betrokkenheden.ouder" : "Gegevens ouder",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.kind.familierechtelijkeBetrekking.relatie" : "Betrokkenheid kind",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.kind.familierechtelijkeBetrekking" : "Betrokkenheid kind",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.kind" : "Betrokkenheid kind",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.ouder.familierechtelijkeBetrekking.betrokkenheden.kind.persoon.identificatienummers" : "Gegevens kind",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.ouder.familierechtelijkeBetrekking.betrokkenheden.kind.persoon.samengesteldeNaam" : "Gegevens kind",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.ouder.familierechtelijkeBetrekking.betrokkenheden.kind.persoon.geboorte" : "Gegevens kind",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.ouder.familierechtelijkeBetrekking.betrokkenheden.kind.persoon" : "Gegevens kind",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.ouder.familierechtelijkeBetrekking.betrokkenheden.kind" : "Gegevens kind",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.ouder.familierechtelijkeBetrekking.relatie" : "Betrokkenheid ouder",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.ouder.familierechtelijkeBetrekking" : "Betrokkenheid ouder",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.ouder.ouderschap" : "Betrokkenheid ouder",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon.betrokkenheden.ouder" : "Betrokkenheid ouder",
	"lvg_synVerwerkPersoon.synchronisatie.bijgehoudenPersonen.persoon" : "Hoofdpersoon",
	"lvg_synVerwerkPersoon.synchronisatie" : "Bericht gegevens"
};

var BRP_LEVERING_KEYS = {
	"gegevensInOnderzoek.gegevenInOnderzoek" : "ggvond",
	"familierechtelijkeBetrekking.betrokkenheden.kind" : "kind",
	"familierechtelijkeBetrekking.betrokkenheden.ouder" : "ouder",
	"geregistreerdPartnerschap.relatie" : "relatie",
	"geregistreerdPartnerschap.betrokkenheden.partner" : "partner",
	"huwelijk.betrokkenheden.partner" : "partner"
};