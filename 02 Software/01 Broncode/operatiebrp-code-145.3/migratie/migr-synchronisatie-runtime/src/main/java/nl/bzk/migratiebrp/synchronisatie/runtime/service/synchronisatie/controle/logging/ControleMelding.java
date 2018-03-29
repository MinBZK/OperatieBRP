/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging;

/**
 * Persoonslijst controle meldingen.
 */
public enum ControleMelding {

    /* ********************************************************************* */
    /* *** CONTROLES FLOW PROCES ******************************************* */
    /* ********************************************************************* */

    /**
     * Controle bericht type.
     */
    CONTROLE_BERICHT_TYPE("controle-bericht-type", "Controle dat het berichttype LG01 is."),
    /**
     * Controle beheerderskeuze.
     */
    CONTROLE_BEHEERDERSKEUZE("controle-beheerderskeuze", "Controle dat de beheerderskeuze aanwezig is."),
    /**
     * Controle versie persoonslijsten.
     */
    CONTROLE_VERSIE_PERSOONSLIJSTEN("controle-versie-persoonslijsten", "Controle dat de versies van de persoonslijsten nog steeds overeenkomen"),

    /* ********************************************************************* */
    /* *** GROTE CONTROLES ************************************************* */
    /* ********************************************************************* */

    /**
     * Controle gezaghebbend.
     */
    CONTROLE_GEZAGHEBBEND("controle-gezaghebbend", "Controle gezaghebbend synchronisatieantwoord door de gemeente van bijhouding."),

    /**
     * Controle reguliere wijziging.
     */
    CONTROLE_REGULIERE_WIJZIGING("controle-reguliere-wijziging", "Controle reguliere wijziging door de gemeente van bijhouding."),

    /**
     * Controle reguliere verhuizing.
     */
    CONTROLE_REGULIERE_VERHUIZING("controle-reguliere-verhuizing", "Controle reguliere wijziging door verhuizing of gemeentelijke herindeling."),

    /**
     * Controle emigratie.
     */
    CONTROLE_EMIGRATIE("controle-emigratie", "Controle emigratie."),

    /**
     * Controle a-nummer wijziging.
     */
    CONTROLE_ANUMMER_WIJZIGING("controle-anummer-wijziging", "Controle a-nummer wijziging door de gemeente van bijhouding."),

    /**
     * Controle overleden persoon.
     */
    CONTROLE_WIJZIGING_OVERLEDEN_PERSOON("controle-wijziging-overleden-persoon",
            "Controle dat er een wijziging wordt gedaan op een wegens overlijden opgeschorte persoonslijst."),

    /**
     * Controle nieuwe pl.
     */
    CONTROLE_NIEUWE_PL("controle-toevoegen", "Controle nieuw opnemen van een persoonslijst."),

    /**
     * Controle aangeboden persoonslijst is ouder.
     */
    CONTROLE_PL_IS_OUDER("controle-aangeboden-ouder", "Controle aangeboden persoonslijst is ouder."),

    /**
     * Controle aangeboden persoonslijst bevat blokkeringsinformatie.
     */
    CONTROLE_PL_IS_GEBLOKKEERD("controle-aangeboden-geblokkeerd", "Controle aangeboden persoonslijst bevat blokkeringsinformatie."),

    /**
     * Controle persoonslijsten gelijk.
     */
    CONTROLE_PL_IS_GELIJK("controle-gelijk", "Controle aangeboden persoonslijst is gelijk aan de gevonden persoonslijst."),

    /* ********************************************************************* */
    /* *** LIJST CONTROLES ************************************************* */
    /* ********************************************************************* */

    /**
     * Controle 1 gevonden persoonslijst obv vorig a-nummer.
     */
    LIJST_CONTROLE_EEN_ACTUEEL_ANR("controle-zoek-niet-foutief-obv-actueel", "Controle 1 gevonden persoonslijst die niet foutief opgeschort is en een " +
            "a-nummer " +
            "heeft dat gelijk is aan het actuele a-nummer op de aangeboden persoonslijst."),

    /**
     * Controle 1 gevonden persoonslijst obv vorig a-nummer.
     */
    LIJST_CONTROLE_EEN_VORIG_ANR("controle-niet-foutief-obv-vorig", "Controle 1 gevonden persoonslijst die niet foutief opgeschort is en een a-nummer " +
            "heeft dat gelijk is aan het vorige a-nummer op de aangeboden persoonslijst."),

    /**
     * Controle geen gevonden persoonslijst obv actueel a-nummer.
     */
    LIJST_CONTROLE_GEEN_ACTUEEL_ANR("controle-zoek-alle-obv-actueel", "Controle geen gevonden persoonslijst (ongeacht opschorting) die een actueel of " +
            "historisch a-nummer heeft dat gelijk is aan het a-nummer op de aangeboden persoonslijst."),

    /**
     * Controle geen gevonden persoonslijst obv actueel a-nummer.
     */
    LIJST_CONTROLE_GEEN_BSN("controle-zoek-alle-obv-bsn",
            "Controle geen gevonden persoonslijst (ongeacht opschorting) die een actueel of historisch burgerservicenummer heeft dat gelijk is aan het "
                    + "burgerservicenummer op de aangeboden persoonslijst."),

    /* ********************************************************************* */
    /* *** PL CONTROLES **************************************************** */
    /* ********************************************************************* */

    PL_CONTROLE_ACTUEEL_BSN_GELIJK("controle-actueel-bsn-gelijk",
            "Controle dat de actuele BSN van de aangeboden persoonslijst overeenkomt met de actuele BSN van de gevonden persoonslijst."),

    /**
     * Controle aangeboden a-nummer historisch gelijk.
     */
    PL_CONTROLE_ANUMMER_HISTORISCH_GELIJK("controle-anummer-historisch-gelijk",
            "Controle dat de aangeboden persoonlijst geen historie van a-nummers bevat of dat alle historische a-nummer gelijk zijn aan het a-nummer."),

    /**
     * Controle aangeboden datum ingang blokkering.
     */
    PL_CONTROLE_BEVAT_DATUM_INGANG_BLOKKERING("controle-datum-ingang-blokkering",
            "Controle dat de aangeboden persoonslijst een gevulde datum ingang blokkering PL bevat."),

    /**
     * Controle actueel & historie adres aangeboden persoonslijst is een subset van gevonden persoonslijst.
     */
    PL_CONTROLE_AANGEBODEN_ADRESSEN_GELIJK("controle-aangeboden-adressen-gelijk",
            "Controle dat de aangeboden persoonslijst een adres en een historie van adressen heeft dat gelijk is aan "
                    + "het adres en de historie van adressen van de gevonden persoonslijst."),

    /**
     * Controle actueel & historie adres aangeboden persoonslijst is een subset van gevonden persoonslijst.
     */
    PL_CONTROLE_AANGEBODEN_ADRESSEN_IN_GEVONDEN("controle-aangeboden-adressen-in-gevonden-adressen",
            "Controle dat de aangeboden persoonsljist een adres en een historie van adressen heeft dat voorkomt in het adres en historie van adressen " +
                    "van de gevonden persoonslijst."),

    /**
     * Controle actueel & historie adres gevonden persoonslijst is een subset van aangeboden persoonslijst.
     */
    PL_CONTROLE_GEVONDEN_ADRESSEN_IN_HISTORIE_AANGEBODEN("controle-gevonden-adressen-in-historie-aangeboden",
            "Controle dat de gevonden persoonslijst in de BRP een adres en een historie van adressen heeft dat voorkomt in de historie van " +
                    "adressen "
                    + "op de aangeboden persoonslijst."),

    /**
     * Controle bijhoudingspartij gelijk.
     */
    PL_CONTROLE_BIJHOUDINGSPARTIJ_GELIJK("controle-bijhouder-gelijk",
            "Controle dat de gevonden persoonslijst in de BRP een gemeente van bijhouding heeft dat gelijk is "
                    + "aan de gemeente van bijhouding op de aangeboden persoonslijst."),

    /**
     * Controle bijhoudingspartij gelijk.
     */
    PL_CONTROLE_BIJHOUDINGSPARTIJ_ONGELIJK("controle-bijhouder-ongelijk",
            "Bijhoudingspartij van aangeboden persoonslijst komt niet overeen met gevonden persoonslijst."),

    /**
     * Controle bijhoudingspartij gelijk aan verzender.
     */
    PL_CONTROLE_BIJHOUDINGSPARTIJ_GELIJK_AAN_VERZENDER("controle-bijhouder-gelijk",
            "Controle dat de aangeboden persoonslijst een gemeente van bijhouding heeft dat gelijk is " + "aan de verzendende gemeente."),

    /**
     * Controle bijhoudingspartij ongelijk aan verzendeer.
     */
    PL_CONTROLE_BIJHOUDINGSPARTIJ_ONGELIJK_AAN_VERZENDER("controle-bijhouder-ongelijk",
            "Controle dat de aangeboden persoonslijst een gemeente van bijhouding heeft dat ongelijk is " + "aan de verzendende gemeente."),

    /**
     * Controle bijhoudingspartij gelijk.
     */
    PL_CONTROLE_BIJHOUDINGSPARTIJ_GELIJK_RNI("controle-bijhouder-gelijk-RNI", "Bijhoudingspartij van aangeboden persoonslijst komt overeen met " +
            "RNI."),

    /**
     * Controle bijhoudingspartij gelijk.
     */
    PL_CONTROLE_BIJHOUDINGSPARTIJ_ONGELIJK_RNI("controle-bijhouder-ongelijk-RNI",
            "Bijhoudingspartij van aangeboden persoonslijst komt niet overeen met RNI."),

    /**
     * Controle dezelfde persoon.
     */
    PL_CONTROLE_DEZELFDE_PERSOON("controle-dezelfde-persoon",
            "Controle dat de persoon op de gevonden persoonslijst in de BRP dezelfde persoon is als de persoon op de aangeboden persoonslijst."),

    /**
     * Controle versienummer gelijk.
     */
    PL_CONTROLE_VERSIENUMMER_GELIJK("controle-versie-gelijk",
            "Controle dat de gevonden persoonslijst in de BRP een versienummer heeft dat gelijk is aan het versienummer van de aangeboden persoonslijst."),

    /**
     * Controle versienummer gelijk.
     */
    PL_CONTROLE_DATUMTIJDSTEMPEL_GELIJK("controle-datumtijdstempel-gelijk",
            "Controle dat de gevonden persoonslijst in de BRP een datumtijdstempel heeft dat gelijk is "
                    + "aan het datumtijdstempel van de aangeboden persoonslijst."),

    /**
     * Controle gevonden versienummer gelijk of kleiner.
     */
    PL_CONTROLE_GEVONDEN_VERSIENUMMER_GELIJK_OF_GROTER(
            "controle-gevonden-versie-gelijk-of-groter", "Controle dat de gevonden persoonslijst in de BRP een versienummer heeft dat gelijk of " +
            "groter "
            + "is dan het versienummer van de aangeboden persoonslijst."),

    /**
     * Controle gevonden versienummer gelijk of kleiner.
     */
    PL_CONTROLE_GEVONDEN_VERSIENUMMER_GELIJK_OF_KLEINER("controle-gevonden-versie-gelijk-of-kleiner",
            "Controle dat de gevonden persoonslijst in de BRP een versienummer heeft dat gelijk of kleiner is dan het versienummer "
                    + "van de aangeboden persoonslijst."),

    /**
     * Controle gevonden versienummer groter.
     */
    PL_CONTROLE_GEVONDEN_VERSIENUMMER_GROTER("controle-gevonden-versie-groter",
            "Controle dat de gevonden persoonslijst in de BRP een versienummer heeft dat groter is dan het versienummer van de aangeboden persoonslijst."),

    /**
     * Controle gevonden versienummer kleiner.
     */
    PL_CONTROLE_GEVONDEN_VERSIENUMMER_KLEINER("controle-gevonden-versie-kleiner",
            "Controle dat de gevonden persoonslijst in de BRP een versienummer heeft dat kleiner is dan het versienummer van de aangeboden persoonslijst."),

    /**
     * Controle gevonden datumtijdstempel gelijk of ouder.
     */
    PL_CONTROLE_GEVONDEN_DATUMTIJDSTEMPEL_GELIJK_OF_OUDER("controle-gevonden-datumtijdstempel-gelijk-of-ouder",
            "Controle dat de gevonden persoonslijst in de BRP een datumtijdstempel heeft dat gelijk of ouder is dan het "
                    + "datumtijdstempel van de "
                    + "aangeboden persoonslijst."),

    /**
     * Controle gevonden datumtijdstempel ouder.
     */
    PL_CONTROLE_GEVONDEN_DATUMTIJDSTEMPEL_OUDER("controle-gevonden-datumtijdstempel-ouder",
            "Controle dat de gevonden persoonslijst in de BRP een datumtijdstempel heeft dat ouder is dan "
                    + "het datumtijdstempel van de aangeboden persoonslijst."),

    /**
     * Controle gevonden datumtijdstempel nieuwer.
     */
    PL_CONTROLE_GEVONDEN_DATUMTIJDSTEMPEL_NIEUWER("controle-gevonden-datumtijdstempel-nieuwer",
            "Controle dat de gevonden persoonslijst in de BRP een datumtijdstempel heeft dat nieuwer is dan het "
                    + "datumtijdstempel van de aangeboden persoonslijst."),

    /**
     * Controle historie a-nummer gelijk.
     */
    PL_CONTROLE_HISTORIE_ANUMMER_GELIJK("controle-anummer-historie",
            "Controle dat de gevonden persoonslijst in de BRP een historie a-nummers heeft dat gelijk is aan de "
                    + "historie van a-nummers op de aangeboden persoonslijst."),

    /**
     * Controle vorig a-nummer gelijk of gevonden historie van vorig a-nummers komt voor in aangeboden historie van
     * vorig a-nummers.
     */
    PL_CONTROLE_VORIG_ANUMMER_GELIJK("controle-vorig-anummer",
            "Controle dat de gevonden persoonslijst in de BRP een vorig a-nummer heeft dat gelijk is aan het "
                    + "vorig a-nummer op de aangeboden persoonslijst of de gevonden persoonslijst in de BRP "
                    + "een historie van vorige a-nummers heeft die voorkomt in de historie van vorige a-nummers "
                    + "op de aangeboden persoonslijst."),

    /**
     * Controle inhoudelijk gelijk.
     */
    PL_CONTROLE_VOLLEDIG_GELIJK("controle-inhoudelijk-gelijk", "Controle dat de gevonden persoonslijst gelijk is aan de aangeboden persoonslijst" +
            "."),

    /**
     * Controle inhoudelijk gelijk muv adres en synchroniciteit.
     */
    PL_CONTROLE_VOLLEDIG_GELIJK_MUV_ADRESSEN_EN_SYNCH("controle-inhoudelijk-gelijk-zonder-adres",
            "Controle dat de aangeboden persoonslijst gelijk is aan de gevonden persoonslijst met uitzondering van het adres, historie van " +
                    "adressen, "
                    + "datumtijdstempel en versienummer."),

    /**
     * Gevonden persoonslijst bevat juiste blokkeringssituatie.
     */
    PL_CONTROLE_GEVONDEN_PERSOONSLIJST_BEVAT_JUISTE_BLOKKERINGSSITUATIE("controle-juiste-blokkeringssituatie",
            "Controle dat de gevonden persoonslijst een juiste blokkeringsituatie bevat"),

    /**
     * Gevonden persoonslijst is opgeschort met reden O.
     */
    PL_CONTROLE_GEVONDEN_PERSOONSLIJST_IS_OPGESCHORT_MET_REDEN_O("controle-opschorting-reden-o", "Controle dat persoonslijst is opgeschort met " +
            "reden O"),

    /**
     * Aangeboden persoonslijst is niet opgeschort met reden F.
     */
    PL_CONTROLE_PERSOONSLIJST_IS_NIET_OPGESCHORT_MET_REDEN_F("controle-opschorting-reden-f", "Controle dat persoonslijst niet is opgeschort met " +
            "reden F"),

    /* ********************************************************************* */
    /* *** VERZOEK CONTROLES *********************************************** */
    /* ********************************************************************* */

    /**
     * Controle of bericht van soort Lg01 is.
     */
    VERZOEK_CONTROLE_BERICHT_VAN_SOORT_LG01("verzoek-soort-bericht-lg01", "Controle dat bericht van soort Lg01 is"),

    /**
     * Controle oud a-nummer gevuld.
     */
    VERZOEK_CONTROLE_OUD_ANUMMER_IS_GEVULD("verzoek-oud-anummer-gevuld", "Controle dat in de kop van het bericht oud a-nummer is gevuld."),

    /**
     * Controle oud a-nummer niet gevuld.
     */
    VERZOEK_CONTROLE_OUD_ANUMMER_IS_NIET_GEVULD("verzoek-oud-anummer-niet-gevuld", "Controle dat in de kop van het bericht oud a-nummer niet is " +
            "gevuld."),

    /* ********************************************************************* */
    /* *** PERSOONLIJSTEN ZOEKEN ******************************************* */
    /* ********************************************************************* */

    /**
     * Zoek niet-foutief obv actueel a-nummer.
     */
    ZOEKER_NIET_FOUTIEF_OBV_ACTUEEL("zoek-niet-foutief-obv-actueel",
            "Zoek persoonslijst die niet foutief opgeschort is en een a-nummer heeft dat gelijk is aan het a-nummer op de aangeboden persoonslijst."),

    /**
     * Zoek niet-foutief obv actueel bsn.
     */
    ZOEKER_NIET_FOUTIEF_OBV_BSN("zoek-niet-foutief-obv-bsn",
            "Zoek persoonslijst die niet foutief opgeschort is en een actueel of historisch burgerservicenummer heeft dat gelijk is aan het "
                    + "burgerservicenummer op de aangeboden persoonslijst."),

    /**
     * Zoek niet-foutief obv actueel vorig a-nummer.
     */
    ZOEKER_NIET_FOUTIEF_OBV_VORIG("zoek-niet-foutief-obv-vorig",
            "Zoek persoonslijst die niet foutief opgeschort is en een a-nummer heeft dat gelijk is aan het vorige a-nummer op de aangeboden " +
                    "persoonslijst."),

    /**
     * Zoek alle obv actueel a-nummer.
     */
    ZOEKER_ALLE_OBV_ACTUEEL("zoek-alle-obv-actueel",
            "Zoek persoonslijst (ongeacht opschorting) die een actueel of historisch a-nummer heeft dat gelijk is aan het "
                    + "a-nummer op de aangeboden persoonslijst."),;

    private final String key;
    private final String omschrijving;

    ControleMelding(final String key, final String omschrijving) {
        this.key = key;
        this.omschrijving = omschrijving;
    }

    /**
     * Geef de waarde van key.
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * Geef de waarde van omschrijving.
     * @return omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
