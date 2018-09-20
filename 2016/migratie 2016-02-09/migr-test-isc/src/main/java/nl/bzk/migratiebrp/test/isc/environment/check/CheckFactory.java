/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.check;

/**
 * Extractor factory.
 */
public final class CheckFactory {

    /**
     * Geef een check.
     *
     * @param type
     *            type
     * @return extractor, null als type onbekend is
     */
    public Check getCheck(final String type) {
        final Check resultaat;

        switch (type.toLowerCase()) {
            case "xpath":
                resultaat = new XpathCheck();
                break;
            case "persoonslijst":
                resultaat = new PersoonslijstCheck();
                break;
            default:
                resultaat = null;
        }

        return resultaat;
    }
}
