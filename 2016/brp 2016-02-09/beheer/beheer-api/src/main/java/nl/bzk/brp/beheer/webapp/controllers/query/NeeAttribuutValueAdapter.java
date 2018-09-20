/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.query;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Nee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NeeAttribuut;

/**
 * NeeAttribuut value adapter.
 */
public final class NeeAttribuutValueAdapter implements ValueAdapter<NeeAttribuut> {

    @Override
    public NeeAttribuut adaptValue(final String value) {
        return "Nee".equals(value) ? new NeeAttribuut(Nee.N) : null;
    }
}
