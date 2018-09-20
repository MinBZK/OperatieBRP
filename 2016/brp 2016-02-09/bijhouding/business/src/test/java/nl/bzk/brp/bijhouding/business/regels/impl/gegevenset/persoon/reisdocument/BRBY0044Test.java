/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.reisdocument;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonReisdocumentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonReisdocumentStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonReisdocumentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonReisdocumentHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class BRBY0044Test {

    private static final Integer TECHNISCHE_SLEUTEL_REIS_DOCUMENT        = 123456;
    private static final Integer ANDERE_TECHNISCHE_SLEUTEL_REIS_DOCUMENT = 654321;

    private BRBY0044             brby0044;

    @Before
    public void init() {
        brby0044 = new BRBY0044();
    }

    @Test
    public void testEenAnderVervallenReisDocumenten() {
        final List<BerichtEntiteit> berichtEntiteits =
            brby0044.voerRegelUit(maakHuidigeSituatie(1, TECHNISCHE_SLEUTEL_REIS_DOCUMENT, 20130303),
                    maakNieuwSituatie(1, ANDERE_TECHNISCHE_SLEUTEL_REIS_DOCUMENT, null), null, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testEenVervallenReisDocumenten() {
        final List<BerichtEntiteit> berichtEntiteits =
            brby0044.voerRegelUit(maakHuidigeSituatie(1, TECHNISCHE_SLEUTEL_REIS_DOCUMENT, 20130303),
                    maakNieuwSituatie(1, TECHNISCHE_SLEUTEL_REIS_DOCUMENT, null), null, null);
        Assert.assertEquals(1, berichtEntiteits.size());
    }

    @Test
    public void testEenNietVervallenReisDocumenten() {
        final List<BerichtEntiteit> berichtEntiteits =
            brby0044.voerRegelUit(maakHuidigeSituatie(1, TECHNISCHE_SLEUTEL_REIS_DOCUMENT, null),
                    maakNieuwSituatie(1, TECHNISCHE_SLEUTEL_REIS_DOCUMENT, null), null, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testGeenReisDocumenten() {
        final List<BerichtEntiteit> berichtEntiteits =
            brby0044.voerRegelUit(maakHuidigeSituatie(0, null, 0),
                    maakNieuwSituatie(0, null, 0), null, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testGeenDataOpgegeven() {
        final List<BerichtEntiteit> berichtEntiteits =
            brby0044.voerRegelUit(maakHuidigeSituatie(null, null, null),
                    maakNieuwSituatie(null, null, null), null, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0044, brby0044.getRegel());
    }

    private PersoonView maakHuidigeSituatie(final Integer aantalReisDocumenten,
            final Integer technischeSleutelReisdocument, final Integer datumOnttrekking)
    {
        final PersoonHisVolledigImplBuilder persoonBuilder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        if (aantalReisDocumenten != null) {
            for (int i = 0; i < aantalReisDocumenten; i++) {
                final PersoonReisdocumentHisVolledigImplBuilder persoonReisdocumentBuider =
                    new PersoonReisdocumentHisVolledigImplBuilder(null);
                final PersoonReisdocumentHisVolledigImpl persoonReisdocument;
                if (datumOnttrekking != null) {
                    persoonReisdocument =
                        persoonReisdocumentBuider.nieuwStandaardRecord(20120202)
                                .datumInhoudingVermissing(datumOnttrekking).eindeRecord().build();
                } else {
                    persoonReisdocument =
                        persoonReisdocumentBuider.nieuwStandaardRecord(20120202).eindeRecord().build();
                }
                ReflectionTestUtils.setField(persoonReisdocument, "iD", technischeSleutelReisdocument);
                persoonBuilder.voegPersoonReisdocumentToe(persoonReisdocument);
            }
        }
        return new PersoonView(persoonBuilder.build());
    }

    private PersoonBericht maakNieuwSituatie(final Integer aantalReisDocumenten,
            final Integer technischeSleutelReisdocument, final Integer datumInhoudingVermissing)
    {
        final PersoonBericht persoonBericht = new PersoonBericht();
        if (aantalReisDocumenten != null) {
            persoonBericht.setReisdocumenten(new ArrayList<PersoonReisdocumentBericht>());
            for (int i = 0; i < aantalReisDocumenten; i++) {
                final PersoonReisdocumentBericht persoonReisdocumentBericht = new PersoonReisdocumentBericht();
                persoonReisdocumentBericht.setObjectSleutel(technischeSleutelReisdocument.toString());
                persoonReisdocumentBericht.setStandaard(new PersoonReisdocumentStandaardGroepBericht());
                if (datumInhoudingVermissing != null) {
                    persoonReisdocumentBericht.getStandaard().setDatumInhoudingVermissing(
                            new DatumEvtDeelsOnbekendAttribuut(datumInhoudingVermissing));
                }
                persoonBericht.getReisdocumenten().add(persoonReisdocumentBericht);
            }
        }

        return persoonBericht;
    }
}
