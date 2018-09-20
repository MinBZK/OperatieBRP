/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeleHistorieImpl;
import nl.bzk.brp.model.basis.FormeleHistorieModel;


/**
 * Abstracte builder met basis methodes voor het zetten van waardes voor
 * {@link nl.bzk.brp.model.basis.MaterieleHistorieModel}.
 */
public abstract class AbstractFormeleHistorieEntiteitBuilder<T extends FormeelHistorisch> {

    private FormeleHistorieModel historie;

    protected AbstractFormeleHistorieEntiteitBuilder() {
        this.historie = new FormeleHistorieImpl();
    }

    protected FormeleHistorieModel getHistorie() {
        return this.historie;
    }

    public AbstractFormeleHistorieEntiteitBuilder<T> datumTijdRegistratie(final DatumTijdAttribuut datumTijd) {
        historie.setDatumTijdRegistratie(datumTijd);
        return this;
    }

    public AbstractFormeleHistorieEntiteitBuilder<T> datumTijdVerval(final DatumTijdAttribuut datumTijd) {
        historie.setDatumTijdVerval(datumTijd);
        return this;
    }

    public abstract T build();
}
