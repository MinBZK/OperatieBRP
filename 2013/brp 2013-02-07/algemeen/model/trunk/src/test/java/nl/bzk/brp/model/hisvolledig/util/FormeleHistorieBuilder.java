/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.basis.FormeleHistorie;

public final class FormeleHistorieBuilder {

    private FormeleHistorieBuilder() {
    }

    public static FormeleHistorie bouwFormeleHistorie(final DatumTijd datumTijdRegistratie,
                                                      final DatumTijd datumTijdVerval)
    {
        final FormeleHistorie formeleHistorie = mock(FormeleHistorie.class);
        when(formeleHistorie.getDatumTijdRegistratie()).thenReturn(datumTijdRegistratie);
        when(formeleHistorie.getDatumTijdVerval()).thenReturn(datumTijdVerval);
        return formeleHistorie;
    }

}
