/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.bericht.basis;

import nl.bzk.copy.model.attribuuttype.Datum;
import nl.bzk.copy.model.attribuuttype.JaNee;
import nl.bzk.copy.model.basis.AbstractGroepBericht;
import nl.bzk.copy.model.groep.logisch.basis.PersoonUitsluitingNLKiesrechtGroepBasis;


/**
 * .
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonUitsluitingNLKiesrechtGroepBericht extends AbstractGroepBericht
        implements PersoonUitsluitingNLKiesrechtGroepBasis
{
    private JaNee indicatieUitsluitingNLKiesrecht;
    private Datum datumEindeUitsluitingNLKiesrecht;

    @Override
    public JaNee getIndicatieUitsluitingNLKiesrecht() {
        return indicatieUitsluitingNLKiesrecht;
    }

    @Override
    public Datum getDatumEindeUitsluitingNLKiesrecht() {
        return datumEindeUitsluitingNLKiesrecht;
    }

    public void setIndicatieUitsluitingNLKiesrecht(final JaNee indicatieUitsluitingNLKiesrecht) {
        this.indicatieUitsluitingNLKiesrecht = indicatieUitsluitingNLKiesrecht;
    }

    public void setDatumEindeUitsluitingNLKiesrecht(final Datum datumEindeUitsluitingNLKiesrecht) {
        this.datumEindeUitsluitingNLKiesrecht = datumEindeUitsluitingNLKiesrecht;
    }
}
