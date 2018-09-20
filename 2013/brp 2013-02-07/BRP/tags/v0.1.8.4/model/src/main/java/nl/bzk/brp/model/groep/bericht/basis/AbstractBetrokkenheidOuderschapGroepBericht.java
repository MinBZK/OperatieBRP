/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.bericht.basis;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.groep.logisch.basis.BetrokkenheidOuderschapGroepBasis;

/**
 * Implementatie voor de groep ouderschap van objecttype betrokkenheid.
 */
@SuppressWarnings("serial")
public abstract class AbstractBetrokkenheidOuderschapGroepBericht extends AbstractGroepBericht
    implements BetrokkenheidOuderschapGroepBasis
{

    private Ja indOuder;
    private Ja indAdresGevend;
    @nl.bzk.brp.model.validatie.constraint.Datum
    private Datum datumAanvang;

    @Override
    public Ja getIndOuder() {
        return indOuder;
    }

    @Override
    public Ja getIndAdresGevend() {
        return indAdresGevend;
    }

    @Override
    public Datum getDatumAanvang() {
        return datumAanvang;
    }

    public void setIndOuder(final Ja indOuder) {
        this.indOuder = indOuder;
    }

    public void setIndAdresGevend(final Ja indAdresGevend) {
        this.indAdresGevend = indAdresGevend;
    }

    public void setDatumAanvang(final Datum datumAanvang) {
        this.datumAanvang = datumAanvang;
    }
}
