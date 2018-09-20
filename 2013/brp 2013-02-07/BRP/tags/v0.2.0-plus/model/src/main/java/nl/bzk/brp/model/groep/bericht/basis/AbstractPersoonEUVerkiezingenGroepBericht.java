/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.bericht.basis;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.groep.logisch.basis.PersoonEUVerkiezingenGroepBasis;


/**
 * .
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonEUVerkiezingenGroepBericht extends AbstractGroepBericht implements PersoonEUVerkiezingenGroepBasis {
    private JaNee indicatieDeelnameEUVerkiezingen;
    private Datum datumAanleidingAanpassingDeelnameEUVerkiezing;
    private Datum datumEindeUitsluitingEUKiesrecht;

    @Override
    public JaNee getIndicatieDeelnameEUVerkiezingen() {
        return indicatieDeelnameEUVerkiezingen;
    }

    @Override
    public Datum getDatumAanleidingAanpassingDeelnameEUVerkiezing() {
        return datumAanleidingAanpassingDeelnameEUVerkiezing;
    }

    @Override
    public Datum getDatumEindeUitsluitingEUKiesrecht() {
        return datumEindeUitsluitingEUKiesrecht;
    }

    public void setIndicatieDeelnameEUVerkiezingen(final JaNee indicatieDeelnameEUVerkiezingen) {
        this.indicatieDeelnameEUVerkiezingen = indicatieDeelnameEUVerkiezingen;
    }

    public void setDatumAanleidingAanpassingDeelnameEUVerkiezing(
            final Datum datumAanleidingAanpassingDeelnameEUVerkiezing)
    {
        this.datumAanleidingAanpassingDeelnameEUVerkiezing = datumAanleidingAanpassingDeelnameEUVerkiezing;
    }

    public void setDatumEindeUitsluitingEUKiesrecht(final Datum datumEindeUitsluitingEUKiesrecht) {
        this.datumEindeUitsluitingEUKiesrecht = datumEindeUitsluitingEUKiesrecht;
    }
}
