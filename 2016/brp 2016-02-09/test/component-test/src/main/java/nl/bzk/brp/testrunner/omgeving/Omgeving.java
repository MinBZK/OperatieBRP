/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.omgeving;

import java.util.List;

/**
 * Interface om een omgeving
 */
public interface Omgeving {

    /**
     * Start de omgeving
     */
    void start();

    /**
     * Stop de omgeving
     */
    void stop();

    /**
     * @param logischeNaam logische naam van het component
     * @return indicatie of de omgeving een component met de gegeven naam bevat
     */
    boolean bevat(String logischeNaam);

    /**
     *
     * @param logischeNaam
     * @return
     */
    Component geefComponent(String logischeNaam);

    /**
     * Geeft componenten welke voldoen aan het opgegeven type
     * @param type
     * @param <T>
     * @return
     */
    <T> List<T> geefComponenten(final Class<T> type);

    /**
     * Geeft het component welke voldoet aan het gegeven type
     * @param type
     * @param <T>
     * @return
     * @throws IllegalStateException als er meerdere componenten van dit type bestaan.
     */
    <T> T geefComponent(Class<T> type);


    /**
     * @return het IP-adres waar de omgeving op draait
     */
    String geefOmgevingHost();

    /**
     * @return indicatie of de omgeving is gestart
     */
    boolean isGestart();

    /**
     * @return indicatie of de omgeving is gestopt
     */
    boolean isGestopt();


    Iterable<Component> geefComponenten();
}
