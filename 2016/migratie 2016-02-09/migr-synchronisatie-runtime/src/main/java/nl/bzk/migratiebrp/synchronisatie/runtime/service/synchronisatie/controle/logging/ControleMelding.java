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
    /* *** GROTE CONTROLES ************************************************* */
    /* ********************************************************************* */

    /** Controle gezaghebbend. */
    CONTROLE_GEZAGHEBBEND("controle-gezaghebbend", "Controle gezaghebbend synchronisatieantwoord door de gemeente van bijhouding."),

    /** Controle reguliere wijziging. */
    CONTROLE_REGULIERE_WIJZIGING("controle-reguliere-wijziging", "Controle reguliere wijziging door de gemeente van bijhouding."),

    /** Controle reguliere verhuizing. */
    CONTROLE_REGULIERE_VERHUIZING("controle-reguliere-verhuizing", "Controle reguliere wijziging door verhuizing of gemeentelijke herindeling."),

    /** Controle emigratie. */
    CONTROLE_EMIGRATIE("controle-emigratie", "Controle emigratie."),

    /** Controle a-nummer wijziging. */
    CONTROLE_ANUMMER_WIJZIGING("controle-anummer-wijziging", "Controle a-nummer wijziging door de gemeente van bijhouding."),

    /** Controle nieuwe pl. */
    CONTROLE_NIEUWE_PL("controle-toevoegen", "Controle nieuw opnemen van een persoonslijst."),

    /** Controle aangeboden persoonslijst is ouder. */
    CONTROLE_PL_IS_OUDER("controle-aangeboden-ouder", "Controle aangeboden persoonslijst is ouder."),

    /** Controle aangeboden persoonslijst bevat blokkeringsinformatie. */
    CONTROLE_PL_IS_GEBLOKKEERD("controle-aangeboden-geblokkeerd", "Controle aangeboden persoonslijst bevat blokkeringsinformatie."),

    /** Controle persoonslijsten gelijk. */
    CONTROLE_PL_IS_GELIJK("controle-gelijk", "Controle aangeboden persoonslijst is gelijk aan de gevonden persoonslijst."),

    /* ********************************************************************* */
    /* *** LIJST CONTROLES ************************************************* */
    /* ********************************************************************* */

    /** Controle exact 1. */
    LIJST_CONTROLE_EEN("lijst-controle-een", "Controle dat exact 1 persoonslijst voorkomt."),
    /** Controle geen. */
    LIJST_CONTROLE_GEEN("lijst-controle-geen", "Controle dat geen persoonslijsten voorkomen."),

    /* ********************************************************************* */
    /* *** PL CONTROLES **************************************************** */
    /* ********************************************************************* */

    /** Controle aangeboden a-nummer historisch gelijk. */
    PL_CONTROLE_ANUMMER_HISTORISCH_GELIJK("controle-anummer-historisch-gelijk",
            "Controle dat de aangeboden persoonlijst geen historie van a-nummers bevat of dat alle historische a-nummer gelijk zijn aan het a-nummer."),

    /** Controle aangeboden datum ingang blokkering. */
    PL_CONTROLE_BEVAT_DATUM_INGANG_BLOKKERING("controle-datum-ingang-blokkering",
            "Controle dat de aangeboden persoonslijst een gevulde datum ingang blokkering PL bevat."),

    /** Controle actueel & historie adres aangeboden persoonslijst is een subset van gevonden persoonslijst. */
    PL_CONTROLE_AANGEBODEN_ADRESSEN_GELIJK("controle-aangeboden-adressen-gelijk",
            "Controle dat de aangeboden persoonslijst een adres en een historie van adressen heeft dat gelijk is aan "
                    + "het adres en de historie van adressen van de gevonden persoonslijst."),

    /** Controle actueel & historie adres aangeboden persoonslijst is een subset van gevonden persoonslijst. */
    PL_CONTROLE_AANGEBODEN_ADRESSEN_IN_HISTORIE_GEVONDEN("controle-aangeboden-adressen-in-historie-gevonden",
            "Controle dat de aangeboden persoonsljist een adres en een historie van adressen heeft dat voorkomt "
                    + "in de historie van adressen van de gevonden persoonslijst."),

    /** Controle actueel & historie adres gevonden persoonslijst is een subset van aangeboden persoonslijst. */
    PL_CONTROLE_GEVONDEN_ADRESSEN_IN_HISTORIE_AANGEBODEN("controle-gevonden-adressen-in-historie-aangeboden",
            "Controle dat de gevonden persoonslijst in de BRP een adres en een historie van adressen heeft dat "
                    + "voorkomt in de historie van adressen op de aangeboden persoonslijst."),

    /** Controle bijhoudingspartij gelijk. */
    PL_CONTROLE_BIJHOUDINGSPARTIJ_GELIJK("controle-bijhouder-gelijk",
            "Controle dat de gevonden persoonslijst in de BRP een gemeente van bijhouding heeft dat gelijk is "
                    + "aan de gemeente van bijhouding op de aangeboden persoonslijst."),

    /** Controle bijhoudingspartij gelijk. */
    PL_CONTROLE_BIJHOUDINGSPARTIJ_ONGELIJK("controle-bijhouder-ongelijk",
            "Bijhoudingspartij van aangeboden persoonslijst komt niet overeen met gevonden persoonslijst."),

    /** Controle bijhoudingspartij gelijk. */
    PL_CONTROLE_BIJHOUDINGSPARTIJ_GELIJK_RNI("controle-bijhouder-gelijk-RNI",
            "Bijhoudingspartij van aangeboden persoonslijst komt overeen met RNI."),

    /** Controle bijhoudingspartij gelijk. */
    PL_CONTROLE_BIJHOUDINGSPARTIJ_ONGELIJK_RNI("controle-bijhouder-ongelijk-RNI",
            "Bijhoudingspartij van aangeboden persoonslijst komt niet overeen met RNI."),

    /** Controle dezelfde persoon. */
    PL_CONTROLE_DEZELFDE_PERSOON("controle-dezelfde-persoon",
            "Controle dat de persoon op de gevonden persoonslijst in de BRP dezelfde persoon is als de persoon op de aangeboden persoonslijst."),

    /** Controle versienummer gelijk. */
    PL_CONTROLE_VERSIENUMMER_GELIJK("controle-versie-gelijk",
            "Controle dat de gevonden persoonslijst in de BRP een versienummer heeft dat gelijk is aan het versienummer van de aangeboden persoonslijst."),
    /** Controle versienummer gelijk. */
    PL_CONTROLE_DATUMTIJDSTEMPEL_GELIJK("controle-datumtijdstempel-gelijk",
            "Controle dat de gevonden persoonslijst in de BRP een datumtijdstempel heeft dat gelijk is "
                    + "aan het datumtijdstempel van de aangeboden persoonslijst."),

    /** Controle gevonden versienummer gelijk of kleiner. */
    PL_CONTROLE_GEVONDEN_VERSIENUMMER_GELIJK_OF_GROTER("controle-gevonden-versie-gelijk-of-groter",
            "Controle dat de gevonden persoonslijst in de BRP een versienummer heeft dat gelijk of groter "
                    + "is dan het versienummer van de aangeboden persoonslijst."),
    /** Controle gevonden versienummer gelijk of kleiner. */
    PL_CONTROLE_GEVONDEN_VERSIENUMMER_GELIJK_OF_KLEINER("controle-gevonden-versie-gelijk-of-kleiner",
            "Controle dat de gevonden persoonslijst in de BRP een versienummer heeft dat gelijk of kleiner is "
                    + "dan het versienummer van de aangeboden persoonslijst."),
    /** Controle gevonden versienummer groter. */
    PL_CONTROLE_GEVONDEN_VERSIENUMMER_GROTER("controle-gevonden-versie-groter",
            "Controle dat de gevonden persoonslijst in de BRP een versienummer heeft dat groter is dan het versienummer van de aangeboden persoonslijst."),
    /** Controle gevonden versienummer kleiner. */
    PL_CONTROLE_GEVONDEN_VERSIENUMMER_KLEINER("controle-gevonden-versie-kleiner",
            "Controle dat de gevonden persoonslijst in de BRP een versienummer heeft dat kleiner is dan het versienummer van de aangeboden persoonslijst."),

    /** Controle gevonden datumtijdstempel gelijk of ouder. */
    PL_CONTROLE_GEVONDEN_DATUMTIJDSTEMPEL_GELIJK_OF_OUDER("controle-gevonden-datumtijdstempel-gelijk-of-ouder",
            "Controle dat de gevonden persoonslijst in de BRP een datumtijdstempel heeft dat gelijk of ouder is dan het "
                    + "datumtijdstempel van de "
                    + "aangeboden persoonslijst."),

    /** Controle gevonden datumtijdstempel ouder. */
    PL_CONTROLE_GEVONDEN_DATUMTIJDSTEMPEL_OUDER("controle-gevonden-datumtijdstempel-ouder",
            "Controle dat de gevonden persoonslijst in de BRP een datumtijdstempel heeft dat ouder is dan "
                    + "het datumtijdstempel van de aangeboden persoonslijst."),
    /** Controle gevonden datumtijdstempel nieuwer. */
    PL_CONTROLE_GEVONDEN_DATUMTIJDSTEMPEL_NIEUWER("controle-gevonden-datumtijdstempel-nieuwer",
            "Controle dat de gevonden persoonslijst in de BRP een datumtijdstempel heeft dat nieuwer is dan het "
                    + "datumtijdstempel van de aangeboden persoonslijst."),

    /** Controle historie a-nummer gelijk. */
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

    /** Controle inhoudelijk gelijk. */
    PL_CONTROLE_VOLLEDIG_GELIJK("controle-inhoudelijk-gelijk", "Controle dat de gevonden persoonslijst gelijk is aan de aangeboden persoonslijst."),
    /** Controle inhoudelijk gelijk muv adres en synchroniciteit. */
    PL_CONTROLE_VOLLEDIG_GELIJK_MUV_ADRESSEN_EN_SYNCH("controle-inhoudelijk-gelijk-zonder-adres",
            "Controle dat de aangeboden persoonslijst gelijk is aan de gevonden persoonslijst met uitzondering "
                    + "van het adres, historie van adressen, datumtijdstempel en versienummer."),

    /* ********************************************************************* */
    /* *** VERZOEK CONTROLES *********************************************** */
    /* ********************************************************************* */

    /** Controle oud a-nummer gevuld. */
    VERZOEK_CONTROLE_OUD_ANUMMER_IS_GEVULD("verzoek-oud-anummer-gevuld", "Controle dat in de kop van het bericht oud a-nummer is gevuld."),
    /** Controle oud a-nummer niet gevuld. */
    VERZOEK_CONTROLE_OUD_ANUMMER_IS_NIET_GEVULD("verzoek-oud-anummer-niet-gevuld", "Controle dat in de kop van het bericht oud a-nummer niet is gevuld."),

    /* ********************************************************************* */
    /* *** PERSOONLIJSTEN ZOEKEN ******************************************* */
    /* ********************************************************************* */

    /** Zoek niet-foutief obv actueel a-nummer. */
    ZOEKER_NIET_FOUTIEF_OBV_ACTUEEL("zoek-niet-foutief-obv-actueel",
            "Zoek persoonslijst die niet foutief opgeschort is en een a-nummer heeft dat gelijk is aan het a-nummer op de aangeboden persoonslijst."),
    /** Zoek niet-foutief obv actueel vorig a-nummer. */
    ZOEKER_NIET_FOUTIEF_OBV_VORIG("zoek-niet-foutief-obv-vorig",
            "Zoek persoonslijst die niet foutief opgeschort is en een a-nummer heeft dat gelijk is aan het vorige a-nummer op de aangeboden persoonslijst."),
    /** Zoek alle obv actueel a-nummer. */
    ZOEKER_ALLE_OBV_ACTUEEL("zoek-alle-obv-actueel",
            "Zoek persoonslijst (ongeachte opschorting) die een actueel of historisch a-nummer heeft dat gelijk is aan het "
                    + "a-nummer op de aangeboden persoonslijst.");

    private final String key;
    private final String omschrijving;

    private ControleMelding(final String key, final String omschrijving) {
        this.key = key;
        this.omschrijving = omschrijving;
    }

    /**
     * Geef de waarde van key.
     *
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * Geef de waarde van omschrijving.
     *
     * @return omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
