/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voornaam;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.basis.MaterieleHistorieImpl;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;

public class HisPersoonVoornaamModelBuilder {
    private HisPersoonVoornaamModel hisVoornaamModel;
    private MaterieleHistorie       historie;

    private String naam;

    public HisPersoonVoornaamModelBuilder() {
        hisVoornaamModel = mock(HisPersoonVoornaamModel.class);
        historie = new MaterieleHistorieImpl();

        this.naam = "";
    }

    public static HisPersoonVoornaamModelBuilder defaultValues() {
        return new HisPersoonVoornaamModelBuilder();
    }

    public static HisPersoonVoornaamModelBuilder kopieer(final HisPersoonVoornaamModel origineel) {
        HisPersoonVoornaamModelBuilder builder = defaultValues();
        builder.naam = origineel.getNaam().getWaarde();

        builder.historie = origineel.getMaterieleHistorie();
        return builder;
    }

    public HisPersoonVoornaamModelBuilder naam(final String waarde) {
        this.naam = waarde;
        return this;
    }

    public HisPersoonVoornaamModelBuilder aanvangGeldigheid(final Datum datum) {
        historie.setDatumAanvangGeldigheid(datum);
        return this;
    }

    public HisPersoonVoornaamModelBuilder eindeGeldigheid(final Datum datum) {
        historie.setDatumEindeGeldigheid(datum);
        return this;
    }

    public HisPersoonVoornaamModelBuilder datumTijdRegistratie(final DatumTijd datumTijd) {
        historie.setDatumTijdRegistratie(datumTijd);
        return this;
    }

    public HisPersoonVoornaamModelBuilder datumTijdVerval(final DatumTijd datumTijd) {
        historie.setDatumTijdVerval(datumTijd);
        return this;
    }

    public HisPersoonVoornaamModel build() {
        synchronized (this) {
            when(hisVoornaamModel.getMaterieleHistorie()).thenReturn(historie);

            when(hisVoornaamModel.getNaam()).thenReturn(new Voornaam(this.naam));

            when(hisVoornaamModel.getMaterieleHistorie()).thenReturn(historie);
        }

        return hisVoornaamModel;
    }
}
