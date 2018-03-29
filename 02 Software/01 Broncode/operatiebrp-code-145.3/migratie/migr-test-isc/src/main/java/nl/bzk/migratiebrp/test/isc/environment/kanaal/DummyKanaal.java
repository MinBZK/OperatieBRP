/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal;


/**
 * Dummy kanaal. Doet niets, kan gebruikt worden om losse extractors te runnen.
 */
public final class DummyKanaal extends AbstractKanaal {

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.Kanaal#getKanaal()
     */
    @Override
    public String getKanaal() {
        return "dummy";
    }

    @Override
    public void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
    }

    @Override
    public Bericht verwerkInkomend(final TestCasusContext testCasus, final Bericht verwachtBericht) throws KanaalException {
        return verwachtBericht;
    }

}
