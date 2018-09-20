/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.overlijden;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BRBY0902Test {

    private static final Integer DATUM_AANVANG_GELDIGHEID           = 20110101;
    private static final Integer DATUM_INSCHRIJVING                 = 20110101;
    private static final Integer DATUM_OVERLIJDEN_NA_INSCHRIJVING   = 20130101;
    private static final Integer DATUM_OVERLIJDEN_VOOR_INSCHRIJVING = 20090101;
    private static final Integer DATUM_OVERLIJDEN_IN_TOEKOMST       = DatumAttribuut.morgen().getWaarde();

    private BRBY0902 brby0902;

    @Before
    public void init() {
        brby0902 = new BRBY0902();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0902, brby0902.getRegel());
    }

    @Test
    public void testVoerRegelUitMetDatumOverlijdenNaDatumInschrijving() {
        final PersoonView huidigeSituatie = maakHuidigeSituatie();
        final PersoonBericht nieuweSituatie = maakNieuweSituatie(DATUM_OVERLIJDEN_NA_INSCHRIJVING);

        final List<BerichtEntiteit> resultaat = brby0902.voerRegelUit(huidigeSituatie, nieuweSituatie, null, null);

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testVoerRegelUitMetOverlijdenNull() {
        final PersoonView huidigeSituatie = maakHuidigeSituatie();
        final PersoonBericht nieuweSituatie = maakNieuweSituatie(DATUM_OVERLIJDEN_NA_INSCHRIJVING);
        nieuweSituatie.setOverlijden(null);

        final List<BerichtEntiteit> resultaat = brby0902.voerRegelUit(huidigeSituatie, nieuweSituatie, null, null);

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testVoerRegelUitMetDatumOverlijdenNull() {
        final PersoonView huidigeSituatie = maakHuidigeSituatie();
        final PersoonBericht nieuweSituatie = maakNieuweSituatie(DATUM_OVERLIJDEN_NA_INSCHRIJVING);
        nieuweSituatie.getOverlijden().setDatumOverlijden(null);

        final List<BerichtEntiteit> resultaat = brby0902.voerRegelUit(huidigeSituatie, nieuweSituatie, null, null);

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testVoerRegelUitMetInschrijvingNull() {
        final PersoonView huidigeSituatie = new PersoonView(
                new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE)));
        final PersoonBericht nieuweSituatie = maakNieuweSituatie(DATUM_OVERLIJDEN_NA_INSCHRIJVING);

        final List<BerichtEntiteit> resultaat = brby0902.voerRegelUit(huidigeSituatie, nieuweSituatie, null, null);

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testVoerRegelUitMetDatumInschrijvingNull() {
        final PersoonView huidigeSituatie = maakHuidigeSituatie(null);
        final PersoonBericht nieuweSituatie = maakNieuweSituatie(DATUM_OVERLIJDEN_NA_INSCHRIJVING);

        final List<BerichtEntiteit> resultaat = brby0902.voerRegelUit(huidigeSituatie, nieuweSituatie, null, null);

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testVoerRegelUitMetDatumOverlijdenVoorDatumInschrijving() {
        final PersoonView huidigeSituatie = maakHuidigeSituatie();
        final PersoonBericht nieuweSituatie = maakNieuweSituatie(DATUM_OVERLIJDEN_VOOR_INSCHRIJVING);

        final List<BerichtEntiteit> resultaat = brby0902.voerRegelUit(huidigeSituatie, nieuweSituatie, null, null);

        Assert.assertEquals(1, resultaat.size());
        Assert.assertTrue(resultaat.get(0) instanceof PersoonBericht);
    }

    @Test
    public void testVoerRegelUitMetDatumOverlijdenInToekomst() {
        final PersoonView huidigeSituatie = maakHuidigeSituatie();
        final PersoonBericht nieuweSituatie = maakNieuweSituatie(DATUM_OVERLIJDEN_IN_TOEKOMST);

        final List<BerichtEntiteit> resultaat = brby0902.voerRegelUit(huidigeSituatie, nieuweSituatie, null, null);

        Assert.assertEquals(1, resultaat.size());
        Assert.assertTrue(resultaat.get(0) instanceof PersoonBericht);
    }

    @Test
    public void testVoerRegelUitMetDatumOverlijdenOpDatumInschrijving() {
        final PersoonView huidigeSituatie = maakHuidigeSituatie();
        final PersoonBericht nieuweSituatie = maakNieuweSituatie(DATUM_INSCHRIJVING);

        final List<BerichtEntiteit> resultaat = brby0902.voerRegelUit(huidigeSituatie, nieuweSituatie, null, null);

        Assert.assertEquals(0, resultaat.size());
    }

    /**
     * Maakt de huidige situatie: een persoon met datum inschrijving.
     *
     * @return persoon his volledig view
     */
    private PersoonView maakHuidigeSituatie() {
        return maakHuidigeSituatie(new DatumEvtDeelsOnbekendAttribuut(DATUM_INSCHRIJVING));
    }

    private PersoonView maakHuidigeSituatie(final DatumEvtDeelsOnbekendAttribuut datumInschrijving) {
        final PersoonHisVolledig persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .nieuwInschrijvingRecord(DATUM_AANVANG_GELDIGHEID)
            .datumInschrijving(datumInschrijving)
            .eindeRecord()
            .build();

        return new PersoonView(persoon);
    }

    /**
     * Maakt de nieuwe situatie: een persoonbericht met datum overlijden.
     *
     * @return persoon bericht
     */
    private PersoonBericht maakNieuweSituatie(final Integer datumOverlijden) {
        final PersoonBericht nieuweSituatie = new PersoonBericht();
        final PersoonOverlijdenGroepBericht overlijden = new PersoonOverlijdenGroepBericht();
        overlijden.setDatumOverlijden(new DatumEvtDeelsOnbekendAttribuut(datumOverlijden));
        nieuweSituatie.setOverlijden(overlijden);

        return nieuweSituatie;
    }
}
