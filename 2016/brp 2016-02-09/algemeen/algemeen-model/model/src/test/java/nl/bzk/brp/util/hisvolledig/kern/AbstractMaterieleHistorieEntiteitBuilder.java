/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.basis.MaterieleHistorieImpl;
import nl.bzk.brp.model.basis.MaterieleHistorieModel;


/**
 * Abstracte builder met basis methodes voor het zetten van waardes voor {@link MaterieleHistorieModel}.
 */
public abstract class AbstractMaterieleHistorieEntiteitBuilder<T extends MaterieelHistorisch> {

    private MaterieleHistorieModel historie;

    protected AbstractMaterieleHistorieEntiteitBuilder() {
        this.historie = new MaterieleHistorieImpl();
    }

    protected MaterieleHistorieModel getHistorie() {
        return this.historie;
    }

    public AbstractMaterieleHistorieEntiteitBuilder<T> aanvangGeldigheid(final DatumEvtDeelsOnbekendAttribuut datum) {
        historie.setDatumAanvangGeldigheid(datum);
        return this;
    }

    public AbstractMaterieleHistorieEntiteitBuilder<T> eindeGeldigheid(final DatumEvtDeelsOnbekendAttribuut datum) {
        historie.setDatumEindeGeldigheid(datum);
        return this;
    }

    public AbstractMaterieleHistorieEntiteitBuilder<T> datumTijdRegistratie(final DatumTijdAttribuut datumTijd) {
        historie.setDatumTijdRegistratie(datumTijd);
        return this;
    }

    public AbstractMaterieleHistorieEntiteitBuilder<T> datumTijdVerval(final DatumTijdAttribuut datumTijd) {
        historie.setDatumTijdVerval(datumTijd);
        return this;
    }

    public abstract T build();
}
