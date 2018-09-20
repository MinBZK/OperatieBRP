/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

/**
 * Een in de Database voorkomend object, waarvan kennis noodzakelijk is voor het kunnen uitvoeren van de
 * functionaliteit.
 *
 * In een database is er geen sprake van objecttypen, groepen en attributen, maar van tabellen, kolommen, indexen,
 * constraints e.d. Deze worden hier opgesomd, voor zover het nodig is voor de functionaliteit, bijvoorbeeld doordat er
 * een onderzoek kan zijn gestart naar een gegeven dat daarin staat, of omdat er verantwoording over is vastgelegd.
 *
 * De populatie wordt beperkt tot de historie tabellen en de kolommen daarbinnen; over indexen, constraint e.d. is nog
 * geen informatiebehoefte. Deze worden derhalve in deze tabel 'niet gekend'.
 * Ook andere tabellen (zoals de actual-tabel in de A-laag) wordt niet opgenomen.
 * De reden hiervoor is dat het vullen van de database-object tabel weliswaar gebeurd door gegenereerde code; voor elk
 * soort database-object dan wel elke extra populatie (naast historie tabellen ook actual tabellen? Extra code!)
 * inspanning kost.
 * Nu is het criterium eenvoudig: pas als er een behoefte is, wordt het erin gestopt.
 * RvdP 15 november 2011.
 *
 *
 *
 */
public enum DatabaseObject {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", null, null, null);

    private final String              naam;
    private final SoortDatabaseObject soort;
    private final DatabaseObject      ouder;
    private final String              javaIdentifier;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor DatabaseObject
     * @param soort Soort voor DatabaseObject
     * @param ouder Ouder voor DatabaseObject
     * @param javaIdentifier JavaIdentifier voor DatabaseObject
     */
    private DatabaseObject(final String naam, final SoortDatabaseObject soort, final DatabaseObject ouder,
            final String javaIdentifier)
    {
        this.naam = naam;
        this.soort = soort;
        this.ouder = ouder;
        this.javaIdentifier = javaIdentifier;
    }

    /**
     * Retourneert Naam van Database object.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Soort van Database object.
     *
     * @return Soort.
     */
    public SoortDatabaseObject getSoort() {
        return soort;
    }

    /**
     * Retourneert Ouder van Database object.
     *
     * @return Ouder.
     */
    public DatabaseObject getOuder() {
        return ouder;
    }

    /**
     * Retourneert Java identifier van Database object.
     *
     * @return Java identifier.
     */
    public String getJavaIdentifier() {
        return javaIdentifier;
    }

}
