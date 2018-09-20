/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;

/**
 * De burgerzaken module
 *
 * Bijhoudingsberichten valleen uiteen in verschillende modulen, die overeenkomen met een opdeling die gebruikelijk is
 * in burgerzaken modules.
 *
 * De module 'verblijf' wordt ook vaak 'migratie' genoemd. Vanwege de andere betekenis van migratie - deelproject ten
 * behoeve van conversie over en weer met GBA-V - is gekozen voor een term die nauw aansluit bij de Wet: namelijk
 * "Verblijf".
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum BurgerzakenModule implements ElementIdentificeerbaar {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", "Dummy"),
    /**
     * Module rondom de familierechtelijke betrekking tussen kind en ouder(s), alsmede de relaties in kader van het
     * ongeboren vrucht..
     */
    AFSTAMMING("Afstamming",
            "Module rondom de familierechtelijke betrekking tussen kind en ouder(s), alsmede de relaties in kader van het ongeboren vrucht."),
    /**
     * Module rondom het huwelijk en het geregistreerd partnerschap..
     */
    HUWELIJK_GEREGISTREERD_PARTNERSCHAP("HuwelijkGeregistreerd partnerschap", "Module rondom het huwelijk en het geregistreerd partnerschap."),
    /**
     * Module rondom verblijf binnen of buiten Nederland, ook wel aangeduid met de term Migratie..
     */
    VERBLIJF_EN_ADRES("Verblijf en adres", "Module rondom verblijf binnen of buiten Nederland, ook wel aangeduid met de term Migratie."),
    /**
     * Module rondom overlijden..
     */
    OVERLIJDEN("Overlijden", "Module rondom overlijden."),
    /**
     * Module rondom bevraging in het kader van koppelvlak Bijhouding..
     */
    BEVRAGING_BIJHOUDING("Bevraging (bijhouding)", "Module rondom bevraging in het kader van koppelvlak Bijhouding."),
    /**
     * Module rondom Nationaliteit..
     */
    NATIONALITEIT("Nationaliteit", "Module rondom Nationaliteit."),
    /**
     * Module rondom Naam en Geslacht..
     */
    NAAM_GESLACHT("Naam geslacht", "Module rondom Naam en Geslacht."),
    /**
     * Module rondom het synchroniseren van gegevens BRP-gegevens met afnemers..
     */
    SYNCHRONISATIE("Synchronisatie", "Module rondom het synchroniseren van gegevens BRP-gegevens met afnemers."),
    /**
     * Module rondom afhandeling Documenten, Verzoeken en Mededelingen..
     */
    DOCUMENT_VERZOEK_MEDEDELING("Document verzoek mededeling", "Module rondom afhandeling Documenten, Verzoeken en Mededelingen."),
    /**
     * Module rondom Reisdocumenten..
     */
    REISDOCUMENTEN("Reisdocumenten", "Module rondom Reisdocumenten."),
    /**
     * Module rondom Verkiezingen..
     */
    VERKIEZINGEN("Verkiezingen", "Module rondom Verkiezingen."),
    /**
     * Module rondom bevraging in het kader van koppelvlak Levering..
     */
    BEVRAGING("Bevraging", "Module rondom bevraging in het kader van koppelvlak Levering."),
    /**
     * Module rondom plaatsen en verwijderen van afnemerindicaties..
     */
    AFNEMERINDICATIE("Afnemerindicatie", "Module rondom plaatsen en verwijderen van afnemerindicaties."),
    /**
     * Module rondom registreren en afhandelen van onderzoeken..
     */
    ONDERZOEK("Onderzoek", "Module rondom registreren en afhandelen van onderzoeken."),
    /**
     * Module ten behoeve van selectie binnen koppelvlak Levering..
     */
    SELECTIE("Selectie", "Module ten behoeve van selectie binnen koppelvlak Levering."),
    /**
     * Module ten behoeve van migratievoorzieningen..
     */
    MIGRATIEVOORZIENINGEN("Migratievoorzieningen", "Module ten behoeve van migratievoorzieningen."),
    /**
     * Module ten behoeve van complexe bijhouding..
     */
    BIJZONDERE_BIJHOUDING("Bijzondere bijhouding", "Module ten behoeve van complexe bijhouding."),
    /**
     * Module rondom fiattering..
     */
    FIATTERING("Fiattering", "Module rondom fiattering."),
    /**
     * Module rondom attendering..
     */
    ATTENDERING("Attendering", "Module rondom attendering."),
    /**
     * Module ten behoeve van het versturen van vrije berichten..
     */
    VRIJE_BERICHTEN("Vrije berichten", "Module ten behoeve van het versturen van vrije berichten.");

    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor BurgerzakenModule
     * @param omschrijving Omschrijving voor BurgerzakenModule
     */
    private BurgerzakenModule(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Naam van Burgerzaken module.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Burgerzaken module.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.BURGERZAKENMODULE;
    }

}
