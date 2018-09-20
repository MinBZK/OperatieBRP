/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.BurgerzakenModule;


/**
 * De mogelijke soort administratieve handeling
 *
 *
 *
 */
public enum SoortAdministratieveHandeling {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("-1", "Dummy", null),
    /**
     * Erkenning ongeboren vrucht.
     */
    ERKENNING_ONGEBOREN_VRUCHT("01001", "Erkenning ongeboren vrucht", BurgerzakenModule.AFSTAMMING),
    /**
     * Inschrijving door geboorte.
     */
    INSCHRIJVING_DOOR_GEBOORTE("01002", "Inschrijving door geboorte", BurgerzakenModule.AFSTAMMING),
    /**
     * Inschrijving door geboorte met erkenning.
     */
    INSCHRIJVING_DOOR_GEBOORTE_MET_ERKENNING("01003", "Inschrijving door geboorte met erkenning",
            BurgerzakenModule.AFSTAMMING),
    /**
     * Erkenning na geboorte.
     */
    ERKENNING_NA_GEBOORTE("01004", "Erkenning na geboorte", BurgerzakenModule.AFSTAMMING),
    /**
     * Vernietiging erkenning.
     */
    VERNIETIGING_ERKENNING("01005", "Vernietiging erkenning", BurgerzakenModule.AFSTAMMING),
    /**
     * Registratie adoptie.
     */
    REGISTRATIE_ADOPTIE("01006", "Registratie adoptie", BurgerzakenModule.AFSTAMMING),
    /**
     * Omzetting adoptie Nederlands recht.
     */
    OMZETTING_ADOPTIE_NEDERLANDS_RECHT("01007", "Omzetting adoptie Nederlands recht", BurgerzakenModule.AFSTAMMING),
    /**
     * Herroeping adoptie.
     */
    HERROEPING_ADOPTIE("01008", "Herroeping adoptie", BurgerzakenModule.AFSTAMMING),
    /**
     * Vaststelling vaderschap door rechter.
     */
    VASTSTELLING_VADERSCHAP_DOOR_RECHTER("01009", "Vaststelling vaderschap door rechter", BurgerzakenModule.AFSTAMMING),
    /**
     * Registratie ontbrekende geboorteakte.
     */
    REGISTRATIE_ONTBREKENDE_GEBOORTEAKTE("01010", "Registratie ontbrekende geboorteakte", BurgerzakenModule.AFSTAMMING),
    /**
     * Registratie verbeterde geboorteakte.
     */
    REGISTRATIE_VERBETERDE_GEBOORTEAKTE("01011", "Registratie verbeterde geboorteakte", BurgerzakenModule.AFSTAMMING),
    /**
     * Registratie niet ingeschreven kind.
     */
    REGISTRATIE_NIET_INGESCHREVEN_KIND("01012", "Registratie niet ingeschreven kind", BurgerzakenModule.AFSTAMMING),
    /**
     * Ontkenning vaderschap.
     */
    ONTKENNING_VADERSCHAP("01013", "Ontkenning vaderschap", BurgerzakenModule.AFSTAMMING),
    /**
     * Inroeping van staat.
     */
    INROEPING_VAN_STAAT("01014", "Inroeping van staat", BurgerzakenModule.AFSTAMMING),
    /**
     * Betwisting van staat.
     */
    BETWISTING_VAN_STAAT("01015", "Betwisting van staat", BurgerzakenModule.AFSTAMMING),
    /**
     * Correctie afstamming.
     */
    CORRECTIE_AFSTAMMING("01016", "Correctie afstamming", BurgerzakenModule.AFSTAMMING),
    /**
     * Sluiting huwelijk Nederland.
     */
    SLUITING_HUWELIJK_NEDERLAND("02001", "Sluiting huwelijk Nederland",
            BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Ontbinding huwelijk Nederland.
     */
    ONTBINDING_HUWELIJK_NEDERLAND("02002", "Ontbinding huwelijk Nederland",
            BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Sluiting geregistreerd partnerschap Nederland.
     */
    SLUITING_GEREGISTREERD_PARTNERSCHAP_NEDERLAND("02003", "Sluiting geregistreerd partnerschap Nederland",
            BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Ontbinding geregistreerd partnerschap Nederland.
     */
    ONTBINDING_GEREGISTREERD_PARTNERSCHAP_NEDERLAND("02004", "Ontbinding geregistreerd partnerschap Nederland",
            BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Sluiting huwelijk buitenland.
     */
    SLUITING_HUWELIJK_BUITENLAND("02005", "Sluiting huwelijk buitenland",
            BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Ontbinding huwelijk buitenland.
     */
    ONTBINDING_HUWELIJK_BUITENLAND("02006", "Ontbinding huwelijk buitenland",
            BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Correctie huwelijk.
     */
    CORRECTIE_HUWELIJK("02007", "Correctie huwelijk", BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Correctie geregistreerd partnerschap.
     */
    CORRECTIE_GEREGISTREERD_PARTNERSCHAP("02008", "Correctie geregistreerd partnerschap",
            BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * Registratie binnengemeentelijke verhuizing.
     */
    REGISTRATIE_BINNENGEMEENTELIJKE_VERHUIZING("03001", "Registratie binnengemeentelijke verhuizing",
            BurgerzakenModule.VERBLIJF),
    /**
     * Registratie intergemeentelijke verhuizing.
     */
    REGISTRATIE_INTERGEMEENTELIJKE_VERHUIZING("03002", "Registratie intergemeentelijke verhuizing",
            BurgerzakenModule.VERBLIJF),
    /**
     * Correctie adres Nederland.
     */
    CORRECTIE_ADRES_NEDERLAND("03003", "Correctie adres Nederland", BurgerzakenModule.VERBLIJF),
    /**
     * Correctie adres buitenland.
     */
    CORRECTIE_ADRES_BUITENLAND("03004", "Correctie adres buitenland", BurgerzakenModule.VERBLIJF),
    /**
     * Registratie overlijden Nederland.
     */
    REGISTRATIE_OVERLIJDEN_NEDERLAND("04001", "Registratie overlijden Nederland", BurgerzakenModule.OVERLIJDEN),
    /**
     * Registratie overlijden buitenland.
     */
    REGISTRATIE_OVERLIJDEN_BUITENLAND("04002", "Registratie overlijden buitenland", BurgerzakenModule.OVERLIJDEN);

    private final String            code;
    private final String            naam;
    private final BurgerzakenModule module;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param code Code voor SoortAdministratieveHandeling
     * @param naam Naam voor SoortAdministratieveHandeling
     * @param module Module voor SoortAdministratieveHandeling
     */
    private SoortAdministratieveHandeling(final String code, final String naam, final BurgerzakenModule module) {
        this.code = code;
        this.naam = naam;
        this.module = module;
    }

    /**
     * Retourneert Code van Soort administratieve handeling.
     *
     * @return Code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Soort administratieve handeling.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Module van Soort administratieve handeling.
     *
     * @return Module.
     */
    public BurgerzakenModule getModule() {
        return module;
    }

}
