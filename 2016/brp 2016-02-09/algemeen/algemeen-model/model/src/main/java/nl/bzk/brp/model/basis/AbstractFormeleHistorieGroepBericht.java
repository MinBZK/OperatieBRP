/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;


/**
 * Basis klasse voor alle groepen in bericht met formele historie. Dit zijn per definitie ook altijd instanties van een {@link BerichtEntiteit}.
 */
@SuppressWarnings("serial")
public abstract class AbstractFormeleHistorieGroepBericht extends AbstractBerichtEntiteitGroep implements
    FormeleHistorie
{

    private DatumTijdAttribuut datumTijdRegistratie;
    private DatumTijdAttribuut datumTijdVerval;

    @Override
    public DatumTijdAttribuut getTijdstipRegistratie() {
        return this.datumTijdRegistratie;
    }

    public void setDatumTijdRegistratie(final DatumTijdAttribuut datumTijdRegistratie) {
        this.datumTijdRegistratie = datumTijdRegistratie;
    }

    @Override
    public DatumTijdAttribuut getDatumTijdVerval() {
        return this.datumTijdVerval;
    }

    public void setDatumTijdVerval(final DatumTijdAttribuut datumTijdVerval) {
        this.datumTijdVerval = datumTijdVerval;
    }

}
