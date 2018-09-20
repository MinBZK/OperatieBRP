/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.injection;

/**
 * Injector factory.
 */
public final class InjectorFactory {

    /**
     * Bepaal de injector.
     * 
     * @param type
     *            type
     * @return injector, null bij een onbekend type
     */
    public Injector getInjector(final String type) {
        final Injector resultaat;
        switch (type.toLowerCase()) {
            case "selenese":
            case "selenium":
                resultaat = new InjectorSelenese();
                break;
            case "token":
                resultaat = new InjectorToken();
                break;
            default:
                resultaat = null;
        }

        return resultaat;
    }
}
