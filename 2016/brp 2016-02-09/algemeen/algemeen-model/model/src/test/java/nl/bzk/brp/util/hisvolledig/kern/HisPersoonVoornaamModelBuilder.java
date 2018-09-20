/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;


public class HisPersoonVoornaamModelBuilder extends AbstractMaterieleHistorieEntiteitBuilder<HisPersoonVoornaamModel> {

    private HisPersoonVoornaamModel hisVoornaamModel;

    private String naam;

    public HisPersoonVoornaamModelBuilder() {
        super();
        hisVoornaamModel = mock(HisPersoonVoornaamModel.class);

        this.naam = "";
    }

    public static HisPersoonVoornaamModelBuilder defaultValues() {
        return new HisPersoonVoornaamModelBuilder();
    }

    public static HisPersoonVoornaamModelBuilder kopieer(final HisPersoonVoornaamModel origineel) {
        HisPersoonVoornaamModelBuilder builder = defaultValues();

        builder.naam(origineel.getNaam().getWaarde())
                .aanvangGeldigheid(origineel.getMaterieleHistorie().getDatumAanvangGeldigheid())
                .eindeGeldigheid(origineel.getMaterieleHistorie().getDatumEindeGeldigheid());

        return builder;
    }

    public HisPersoonVoornaamModelBuilder naam(final String waarde) {
        this.naam = waarde;
        return this;
    }

    @Override
    public HisPersoonVoornaamModel build() {
        synchronized (this) {
            when(hisVoornaamModel.getNaam()).thenReturn(new VoornaamAttribuut(this.naam));
            when(hisVoornaamModel.getMaterieleHistorie()).thenReturn(getHistorie());
            when(hisVoornaamModel.getDatumAanvangGeldigheid()).thenReturn(getHistorie().getDatumAanvangGeldigheid());
            when(hisVoornaamModel.getDatumEindeGeldigheid()).thenReturn(getHistorie().getDatumEindeGeldigheid());
            when(hisVoornaamModel.getTijdstipRegistratie()).thenReturn(getHistorie().getTijdstipRegistratie());
            when(hisVoornaamModel.getDatumTijdVerval()).thenReturn(getHistorie().getDatumTijdVerval());
        }

        return hisVoornaamModel;
    }
}
