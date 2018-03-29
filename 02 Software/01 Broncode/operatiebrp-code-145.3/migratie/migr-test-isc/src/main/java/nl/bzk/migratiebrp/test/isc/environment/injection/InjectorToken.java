/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.injection;

import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;

/**
 * Token injector. Vervangt een gegeven sleutel in het bericht met de variabele waarde.
 */
public class InjectorToken implements Injector {

    @Override
    public final void inject(final Context context, final Bericht bericht, final String key, final String value) {
        final String inhoud = bericht.getInhoud();
        final String nieuweInhoud = inhoud.replace(key, value);
        bericht.setInhoud(nieuweInhoud);
    }

}
