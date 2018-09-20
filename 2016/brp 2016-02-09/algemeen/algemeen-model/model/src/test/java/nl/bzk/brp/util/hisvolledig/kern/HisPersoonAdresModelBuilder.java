/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;


public class HisPersoonAdresModelBuilder extends AbstractMaterieleHistorieEntiteitBuilder<HisPersoonAdresModel> {

    private final HisPersoonAdresModel hisAdresModel;

    private String  naamOpenbareRuimte;
    private Integer huisnummer;
    private String  postcode;
    private String  woonplaatsNaam;

    public HisPersoonAdresModelBuilder() {
        super();
        hisAdresModel = mock(HisPersoonAdresModel.class);
    }

    public static HisPersoonAdresModelBuilder defaultValues() {
        HisPersoonAdresModelBuilder b = new HisPersoonAdresModelBuilder();
        b.woonplaats("Demodorp").postcode("1234AA").huisnummer(1).straatnaam("Teststraat");

        return b;
    }

    public static HisPersoonAdresModelBuilder kopieer(final HisPersoonAdresModel origineel) {
        HisPersoonAdresModelBuilder builder = defaultValues();

        builder.straatnaam(origineel.getNaamOpenbareRuimte().getWaarde())
                .huisnummer(origineel.getHuisnummer().getWaarde()).postcode(origineel.getPostcode().getWaarde())
                .woonplaats(origineel.getWoonplaatsnaam().getWaarde())
                .aanvangGeldigheid(origineel.getMaterieleHistorie().getDatumAanvangGeldigheid())
                .eindeGeldigheid(origineel.getMaterieleHistorie().getDatumEindeGeldigheid());

        return builder;
    }

    public HisPersoonAdresModelBuilder straatnaam(final String waarde) {
        this.naamOpenbareRuimte = waarde;
        return this;
    }

    public HisPersoonAdresModelBuilder huisnummer(final Integer nummer) {
        this.huisnummer = nummer;
        return this;
    }

    public HisPersoonAdresModelBuilder postcode(final String code) {
        this.postcode = code;
        return this;
    }

    public HisPersoonAdresModelBuilder woonplaats(final String woonplaats) {
        this.woonplaatsNaam = woonplaats;
        return this;
    }

    @Override
    public HisPersoonAdresModel build() {
        synchronized (this) {
            when(hisAdresModel.getMaterieleHistorie()).thenReturn(getHistorie());

            when(hisAdresModel.getNaamOpenbareRuimte()).thenReturn(
                    new NaamOpenbareRuimteAttribuut(this.naamOpenbareRuimte));
            when(hisAdresModel.getHuisnummer()).thenReturn(new HuisnummerAttribuut(this.huisnummer));
            when(hisAdresModel.getPostcode()).thenReturn(new PostcodeAttribuut(this.postcode));
            when(hisAdresModel.getWoonplaatsnaam()).thenReturn(new NaamEnumeratiewaardeAttribuut(this.woonplaatsNaam));
        }

        return hisAdresModel;
    }
}
