/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;


public class HisRelatieModelBuilder extends AbstractFormeleHistorieEntiteitBuilder<HisRelatieModel>
{

    private final HisRelatieModel model;

    private String                         woonplaatsAanvang;
    private String                         woonplaatsEinde;
    private DatumEvtDeelsOnbekendAttribuut datumAanvang;
    private DatumEvtDeelsOnbekendAttribuut datumEinde;

    public HisRelatieModelBuilder() {
        super();
        model = mock(HisRelatieModel.class);
    }

    public static HisRelatieModelBuilder defaultValues() {
        return new HisRelatieModelBuilder();
    }

    public HisRelatieModelBuilder datumAanvang(final DatumEvtDeelsOnbekendAttribuut datum) {
        this.datumAanvang = datum;
        return this;
    }

    public HisRelatieModelBuilder datumEinde(final DatumEvtDeelsOnbekendAttribuut datum) {
        this.datumEinde = datum;
        return this;
    }

    public HisRelatieModelBuilder woonplaatsAanvang(final String naam) {
        this.woonplaatsAanvang = naam;
        return this;
    }

    public HisRelatieModelBuilder woonplaatsEinde(final String naam) {
        this.woonplaatsEinde = naam;
        return this;
    }

    public static HisRelatieModelBuilder kopieer(
            final HisRelatieModel origineel)
    {
        final HisRelatieModelBuilder builder = defaultValues();

        final DatumEvtDeelsOnbekendAttribuut aanvangOrigineel = origineel.getDatumAanvang();
        final DatumEvtDeelsOnbekendAttribuut eindeOrigineel = origineel.getDatumEinde();

        builder.woonplaatsAanvang(origineel.getWoonplaatsnaamAanvang().getWaarde()).woonplaatsEinde(
                origineel.getWoonplaatsnaamEinde().getWaarde());

        if (aanvangOrigineel != null) {
            builder.datumAanvang(new DatumEvtDeelsOnbekendAttribuut(aanvangOrigineel.getWaarde()));
        }
        if (eindeOrigineel != null) {
            builder.datumEinde(new DatumEvtDeelsOnbekendAttribuut(eindeOrigineel.getWaarde()));
        }

        return builder;
    }

    @Override
    public HisRelatieModel build() {
        synchronized (this) {
            when(model.getFormeleHistorie()).thenReturn(getHistorie());

            when(model.getDatumAanvang()).thenReturn(this.datumAanvang);
            when(model.getDatumEinde()).thenReturn(this.datumEinde);

            when(model.getWoonplaatsnaamAanvang())
                    .thenReturn(new NaamEnumeratiewaardeAttribuut(this.woonplaatsAanvang));
            when(model.getWoonplaatsnaamEinde()).thenReturn(new NaamEnumeratiewaardeAttribuut(this.woonplaatsEinde));
        }

        return model;
    }
}
