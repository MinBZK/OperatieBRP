/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.logging;

/**
 * Synchronisatie beslissingen.
 */
public enum SynchronisatieBeslissing {

    /**
     * SynchroniseerNaarBrpService: verwerk als initiele vulling.
     */
    SERVICE_VERWERK_ALS_IV("a1", "Verwerken als initiele vulling."),

    /**
     * SynchroniseerNaarBrpService: verwerk als synchronisatie.
     */
    SERVICE_VERWERK_ALS_SYNC("a2", "Verwerken als synchronisatie."),

    /**
     * AbstractSynchronisatieVerwerkerImpl: bericht kan niet geparsed worden (eind status).
     */
    PARSE_BERICHT_PARSE_FOUT("b1", "Bericht kan niet geparsed worden."),

    /**
     * AbstractSynchronisatieVerwerkerImpl: bericht voldoet niet aan syntactische controles (eind status).
     */
    PARSE_BERICHT_SYNTAX_FOUT("b2", "Bericht voldoet niet aan syntactische controles."),

    /**
     * AbstractSynchronisatieVerwerkerImpl: bericht voldoet niet aan inhoudelijke controles (eind status).
     */
    PARSE_BERICHT_INHOUD_FOUT("b3", "Bericht voldoet niet aan inhoudelijke controles."),

    /**
     * InitieleVullingSynchronisatieVerwerkerImpl: Groep 80 in categorie 07 aanvullen met default waarden.
     */
    INIT_VULLING_AANVULLEN_GROEP_80("c1", "Groep 80 (Synchroniciteit) in categorie 07 (Inschrijving) aanvullen met default waarden."),

    /**
     * AbstractSynchronisatieVerwerkerImpl: persoonslijst voldoet niet aan baseline 1 controles (eind status).
     */
    CONVERSIE_PRECONDITIE_FOUT("d1", "Persoonslijst voldoet niet aan baseline 1 controles."),

    /**
     * AbstractSynchronisatieVerwerkerImpl: persoonslijst kan niet geconverteerd worden naar BRP structuur (eind
     * status).
     */
    CONVERSIE_FOUT("d2", "Persoonslijst kan niet geconverteerd worden naar BRP structuur."),

    /**
     * InitieleVullingSynchronisatieVerwerkerImpl: persoonslijst gevonden in BRP database obv actueel a-nummer (eind
     * status).
     */
    INIT_VULLING_ANUMMER_GEVONDEN("e1", "Persoonslijst gevonden in BRP database obv " + "actueel a-nummer."),

    /**
     * InitieleVullingSynchronisatieVerwerkerImpl: persoonslijst gevonden in BRP database obv actueel a-nummer (eind
     * status).
     */
    INIT_VULLING_BSN_GEVONDEN("e3", "Persoonslijst gevonden in BRP database obv " + "burger service nummer."),

    /**
     * InitieleVullingSynchronisatieVerwerkerImpl: persoonslijst toevoegen (eind status).
     */
    INIT_VULLING_TOEVOEGEN("e2", "Persoonslijst " + "toevoegen."),

    /**
     * SynchronisatieVerwerkerImpl: Verwerken als beheerderskeuze: nieuwe PL.
     */
    SYNCHRONISATIE_VERWERKER_NIEUWE_PL("f1", "Verwerken als beheerderskeuze: nieuwe PL."),

    /**
     * SynchronisatieVerwerkerImpl: Verwerken als beheerderskeuze: vervang PL.
     */
    SYNCHRONISATIE_VERWERKER_VERVANGEN_PL("f2", "Verwerken als beheerderskeuze: vervang PL."),

    /**
     * SynchronisatieVerwerkerImpl: Persoonslijst vervangen (gezaghebbend bericht).
     */
    SYNCHRONISATIE_VERWERKER_GEZAGHEBBEND_BERICHT("f3", "Persoonslijst vervangen (gezaghebbend bericht)."),

    /**
     * SynchronisatieVerwerkerImpl: Verwerken als reguliere synchronisatie.
     */
    SYNCHRONISATIE_VERWERKER_SYNCHRONISATIE("f4", "Verwerken als reguliere synchronisatie."),

    /**
     * PlVerwerkerBeheerdersKeuzeNieuw: Persoonslijst gevonden in BRP database obv actueel a-nummer (eind status).
     */
    BEHEERDERS_KEUZE_NIEUW_GEVONDEN("g1", "Persoonslijst gevonden in BRP database obv actueel a-nummer."),

    /**
     * Beheerderskeuze: Persoonslijst toevoegen (eind status).
     */
    BEHEERDERS_KEUZE_NIEUW_TOEVOEGEN("g2", "Persoonslijst" + " toevoegen."),

    /**
     * Beheerderskeuze: Persoonslijst vervangen (eind status).
     */
    BEHEERDERS_KEUZE_VERVANGEN("g3", "Persoonslijst " + "vervangen."),

    /**
     * Beheerderskeuze: Bericht afkeuren (eind status).
     */
    BEHEERDERS_KEUZE_AFKEUREN("g4", "Bericht afkeuren."),

    /**
     * Beheerderskeuze: Bericht negeren (eind status).
     */
    BEHEERDERS_KEUZE_NEGEREN("g5", "Bericht negeren."),

    /**
     * Beheerderskeuze: Bericht negeren (eind status).
     */
    BEHEERDERS_KEUZE_ONDUIDELIJK("g6", "Beheerderskeuze onduidelijk."),

    /**
     * PlVerwerkerBeheerdersKeuzeVervang: Persoonslijst niet gevonden in BRP obv actueel a-nummer (eind status).
     */
    BEHEERDERS_KEUZE_VERVANGEN_NIET_GEVONDEN("h1", "Persoonslijst niet gevonden in BRP obv actueel a-nummer."),

    /**
     * PlVerwerkerBeheerdersKeuzeVervang: Persoonslijst vervangen (eind status).
     */
    BEHEERDERS_KEUZE_VERVANGEN_VERVANGEN("h2", "Persoonslijst vervangen."),

    /**
     * PlVerwerkerSynchronisatie: Reguliere wijziging door gemeente van bijhouding (eind status).
     */
    SYNCHRONISATIE_REGULIER_WIJZIGEN("i1", "Reguliere wijziging door gemeente van bijhouding."),

    /**
     * PlVerwerkerSynchronisatie: Reguliere wijziging door verhuizing of gemeentelijk herindeling (eind status).
     */
    SYNCHRONISATIE_REGULIER_VERHUIZEN("i2", "Reguliere wijziging door verhuizing of gemeentelijk herindeling."),

    /**
     * PlVerwerkerSynchronisatie: Reguliere wijziging door verhuizing of gemeentelijk herindeling (eind status).
     */
    SYNCHRONISATIE_EMIGRATIE("i9", "Emigratie."),

    /**
     * PlVerwerkerSynchronisatie: A-nummer wijziging doro de gemeente van bijhouding (eind status).
     */
    SYNCHRONISATIE_ANUMMER_WIJZIGING("i3", "A-nummer wijziging door de gemeente van bijhouding."),

    /**
     * PlVerwerkerSynchronisatie: Wijzigingen op pl van overleden persoon.
     */
    SYNCHRONISATIE_WIJZIGEN_OVERLEDEN_PERSOON("i10", "Wijzigingen op pl van overleden persoon"),

    /**
     * PlVerwerkerSynchronisatie: Toevoegen (eind status).
     */
    SYNCHRONISATIE_TOEVOEGEN("i4", "Persoonslijst toevoegen."),

    /**
     * PlVerwerkerSynchronisatie: De aangeboden persoonslijst is ouder (eind status).
     */
    SYNCHRONISATIE_PERSOONSLIJST_OUDER("i5", "De aangeboden persoonslijst is ouder."),

    /**
     * PlVerwerkerSynchronisatie: De aangeboden persoonslijst is geblokkeerd (eind status).
     */
    SYNCHRONISATIE_PERSOONSLIJST_GEBLOKKEERD("i6", "De aangeboden persoonslijst bevat blokkeringsinformatie."),

    /**
     * PlVerwerkerSynchronisatie: De persoonslijsten zijn gelijk (eind status).
     */
    SYNCHRONISATIE_PERSOONSLIJST_GELIJK("i7", "De persoonslijsten zijn gelijk."),

    /**
     * PlVerwerkerSynchronisatie: Onduidelijk (eind status).
     */
    SYNCHRONISATIE_ONDUIDELIJK("i8", "Onduidelijk.");

    private final String code;
    private final String omschrijving;

    /**
     * Default constructor.
     * @param code De code van de beslissing
     * @param omschrijving De omschrijving van de beslissing
     */
    SynchronisatieBeslissing(final String code, final String omschrijving) {
        this.code = code;
        this.omschrijving = omschrijving;
    }

    /**
     * Geef de waarde van code.
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * Geef de waarde van omschrijving.
     * @return omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
