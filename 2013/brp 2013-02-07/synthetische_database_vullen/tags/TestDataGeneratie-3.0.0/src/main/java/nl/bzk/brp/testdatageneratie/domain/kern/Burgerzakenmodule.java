/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.domain.kern;

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
 * Generator: nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.4.8.
 * Gegenereerd op: Fri Dec 21 07:48:03 CET 2012.
 */
public enum Burgerzakenmodule {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", "Dummy"),
    /**
     * Module rondom de familierechtelijke betrekking tussen kind en ouder(s), alsmede de relaties in kader van het
     * ongeboren
     * vrucht..
     */
    AFSTAMMING(
            "Afstamming",
            "Module rondom de familierechtelijke betrekking tussen kind en ouder(s), alsmede de relaties in kader van het ongeboren vrucht."),
    /**
     * Module rondom het huwelijk en het geregistreerd partnerschap..
     */
    HUWELIJK_GEREGISTREERD_PARTNERSCHAP("HuwelijkGeregistreerd partnerschap",
                                        "Module rondom het huwelijk en het geregistreerd partnerschap."),
    /**
     * Module rondom verblijf binnen of buiten Nederland, ook wel aangeduid met de term Migratie..
     */
    VERBLIJF("Verblijf", "Module rondom verblijf binnen of buiten Nederland, ook wel aangeduid met de term Migratie."),
    /**
     * Module rondom overlijden..
     */
    OVERLIJDEN("Overlijden", "Module rondom overlijden."),
    /**
     * Is nu voor restcategorie van de berichten, c.q. vraag/antwoord berichten. Wellicht is voor deze echter Module
     * gewoon
     * niet gevuld....
     */
    ALGEMENE_BIJHOUDING(
            "Algemene bijhouding",
            "Is nu voor restcategorie van de berichten, c.q. vraag/antwoord berichten. Wellicht is voor deze echter Module gewoon niet gevuld...");

    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor BurgerzakenModule
     * @param omschrijving Omschrijving voor BurgerzakenModule
     */
    private Burgerzakenmodule(final String naam, final String omschrijving) {
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

}
