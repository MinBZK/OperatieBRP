/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.FormeleHistorieImpl;
import nl.bzk.brp.model.basis.FormeleHistorieModel;


public final class FormeleHistorieBuilder {

    private FormeleHistorieBuilder() {
    }

    public static FormeleHistorieModel bouwFormeleHistorie(final DatumTijdAttribuut datumTijdRegistratie,
            final DatumTijdAttribuut datumTijdVerval)
    {
        final FormeleHistorieModel formeleHistorie = new FormeleHistorieImpl();
        formeleHistorie.setDatumTijdRegistratie(datumTijdRegistratie);
        formeleHistorie.setDatumTijdVerval(datumTijdVerval);
        return formeleHistorie;
    }

}
