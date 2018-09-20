/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.algemeen.common;


/**
 * Een target platform is een technologie waarvoor generatie plaats kan vinden.
 * Deze enum biedt een veilige link tussen de naam van een platform en de code die
 * daarvoor in het BMR wordt gebruikt.
 */
public enum BmrTargetPlatform {

    /**
     * PostgreSQL database platform.
     */
    POSTGRESQL(2),

    /**
     * Java programmeertaal platform.
     */
    JAVA(3),

    /**
     * XML Schema platform.
     */
    XSD(4),

    /**
     * Expressies platform.
     */
    EXPRESSIES(5);


    private int bmrId;

    /**
     * Maak een nieuw platform aan.
     *
     * @param bmrId het id dat hiervoor in het BMR gebruikt wordt.
     */
    private BmrTargetPlatform(final int bmrId) {
        this.bmrId = bmrId;
    }

    public int getBmrId() {
        return this.bmrId;
    }

}
