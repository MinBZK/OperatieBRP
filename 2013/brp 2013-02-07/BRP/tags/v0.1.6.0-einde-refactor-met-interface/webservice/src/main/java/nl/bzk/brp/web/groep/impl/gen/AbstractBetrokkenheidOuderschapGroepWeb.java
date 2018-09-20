/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.groep.impl.gen;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.groep.interfaces.gen.BetrokkenheidOuderschapGroepBasis;
import nl.bzk.brp.web.AbstractGroepWeb;

/**
 * Implementatie voor de groep ouderschap van objecttype betrokkenheid.
 */
public abstract class AbstractBetrokkenheidOuderschapGroepWeb extends AbstractGroepWeb
    implements BetrokkenheidOuderschapGroepBasis
{

    private Datum datumAanvang;
    private Ja indOuder;

    @Override
    public Datum getDatumAanvang() {
        return datumAanvang;
    }

    @Override
    public Ja getIndOuder() {
        return indOuder;
    }
}
