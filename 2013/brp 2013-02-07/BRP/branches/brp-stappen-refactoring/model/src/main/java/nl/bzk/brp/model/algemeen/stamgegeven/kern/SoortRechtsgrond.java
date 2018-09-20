/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

/**
 * Categorisatie van rechtsgrond.
 *
 * De verschillende rechtsgronden laten zich categoriseren naar (o.a.) het doel waarvoor de rechtsgrond wordt gebruikt:
 * zo hebben sommige rechtsgronden betrekking op het verkrijgen van de (Nederlandse) Nationaliteit, andere hebben
 * betrekking op het kunnen/mogen vastleggen van gegevens over niet-ingezetenen.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:51 CET 2013.
 */
public enum SoortRechtsgrond {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy"),
    /**
     * Verkrijging Nederlandse Nationaliteit.
     */
    VERKRIJGING_NEDERLANDSE_NATIONALITEIT("Verkrijging Nederlandse Nationaliteit"),
    /**
     * Verlies Nederlandse Nationaliteit.
     */
    VERLIES_NEDERLANDSE_NATIONALITEIT("Verlies Nederlandse Nationaliteit"),
    /**
     * Internationaal verdrag RNI.
     */
    INTERNATIONAAL_VERDRAG_R_N_I("Internationaal verdrag RNI");

    private final String naam;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor SoortRechtsgrond
     */
    private SoortRechtsgrond(final String naam) {
        this.naam = naam;
    }

    /**
     * Retourneert Naam van Soort rechtsgrond.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

}
