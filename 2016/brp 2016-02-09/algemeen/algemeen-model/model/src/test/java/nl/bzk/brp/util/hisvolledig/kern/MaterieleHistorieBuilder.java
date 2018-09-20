/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.MaterieleHistorieImpl;
import nl.bzk.brp.model.basis.MaterieleHistorieModel;


public final class MaterieleHistorieBuilder {

    private MaterieleHistorieBuilder() {
    }

    public static MaterieleHistorieModel bouwMaterieleHistorie(final DatumTijdAttribuut datumTijdRegistratie,
            final DatumTijdAttribuut datumTijdVerval, final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
            final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid)
    {
        final MaterieleHistorieModel materieleHistorieModel = new MaterieleHistorieImpl();
        materieleHistorieModel.setDatumTijdRegistratie(datumTijdRegistratie);
        materieleHistorieModel.setDatumTijdVerval(datumTijdVerval);
        materieleHistorieModel.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        materieleHistorieModel.setDatumEindeGeldigheid(datumEindeGeldigheid);

        return materieleHistorieModel;
    }

}
