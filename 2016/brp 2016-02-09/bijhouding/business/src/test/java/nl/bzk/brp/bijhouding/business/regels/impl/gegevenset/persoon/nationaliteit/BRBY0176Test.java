/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.nationaliteit;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerkrijgingCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieConversieGBABericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitStandaardGroepBericht;
import nl.bzk.brp.model.logisch.kern.Actie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor het testen van de bedrijfsregel BRBY0176.
 */
public class BRBY0176Test {

    private static final RedenVerkrijgingCodeAttribuut GELDIGE_REDEN_VERKRIJGING_CODE              =
                                                                                                       new RedenVerkrijgingCodeAttribuut(
                                                                                                               (short) 34);
    private static final RedenVerkrijgingCodeAttribuut GELDIGE_REDEN_VERKRIJGING_CODE_ZONDER_DATUM =
                                                                                                       new RedenVerkrijgingCodeAttribuut(
                                                                                                               (short) 17);

    private BRBY0176                                   bedrijfsregel;

    @Before
    public void init() {
        bedrijfsregel = new BRBY0176();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0176, bedrijfsregel.getRegel());
    }

    @Test
    public void testGetLogger() {
        Assert.assertNotNull(bedrijfsregel.getLogger());
    }

    @Test
    public void testGeenNationaliteiten() {
        List<BerichtEntiteit> meldingen;

        meldingen = bedrijfsregel.voerRegelUit(null, new PersoonBericht(), null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testZonderNieuweSituatieGeenMeldingen() {
        List<BerichtEntiteit> meldingen;

        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(null), null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testGeenStandaardGroep() {
        List<BerichtEntiteit> meldingen;
        PersoonBericht persoon = bouwPersoon(bouwRedenVerkrijging(GELDIGE_REDEN_VERKRIJGING_CODE, null, null));
        ReflectionTestUtils.setField(persoon.getNationaliteiten().iterator().next(), "standaard", null);
        meldingen = bedrijfsregel.voerRegelUit(null, persoon, null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testGeenRedenVerkrijging() {
        List<BerichtEntiteit> meldingen;
        PersoonBericht persoon = bouwPersoon(bouwRedenVerkrijging(GELDIGE_REDEN_VERKRIJGING_CODE, null, null));
        ReflectionTestUtils.setField(persoon.getNationaliteiten().iterator().next().getStandaard(), "redenVerkrijging",
                null);
        meldingen = bedrijfsregel.voerRegelUit(null, persoon, null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testGeldigheidRedenVerkrijgingVoorReedsOpgehaaldeRedenVerkrijgingMetDatumAanvangEnDatumEinde() {
        List<BerichtEntiteit> meldingen;

        final RedenVerkrijgingNLNationaliteit redenVerkrijging =
            bouwRedenVerkrijging(GELDIGE_REDEN_VERKRIJGING_CODE, new DatumEvtDeelsOnbekendAttribuut(20120325),
                    new DatumEvtDeelsOnbekendAttribuut(20120728));

        // Test met datum voor aanvang geldigheid reden verkrijging.
        PersoonBericht persoon = bouwPersoon(redenVerkrijging);
        meldingen = bedrijfsregel.voerRegelUit(null, persoon, bouwActie(20120101), null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(persoon.getNationaliteiten().iterator().next(), meldingen.get(0));

        // Test met datum 1 dag voor aanvang geldigheid reden verkrijging.
        persoon = bouwPersoon(redenVerkrijging);
        meldingen = bedrijfsregel.voerRegelUit(null, persoon, bouwActie(20120324), null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(persoon.getNationaliteiten().iterator().next(), meldingen.get(0));

        // Test met datum gelijk aan aanvang geldigheid reden verkrijging.
        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(redenVerkrijging), bouwActie(20120325), null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum tussen aanvang geldigheid reden verkrijging. en einde geledigheid reden verkrijging.
        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(redenVerkrijging), bouwActie(20120603), null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum 1 dag voor einde geldigheid reden verkrijging.
        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(redenVerkrijging), bouwActie(20120727), null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum gelijk aan einde geldigheid reden verkrijging.
        persoon = bouwPersoon(redenVerkrijging);
        meldingen = bedrijfsregel.voerRegelUit(null, persoon, bouwActie(20120728), null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(persoon.getNationaliteiten().iterator().next(), meldingen.get(0));

        // Test met datum 1 dag na einde geldigheid reden verkrijging.
        persoon = bouwPersoon(redenVerkrijging);
        meldingen = bedrijfsregel.voerRegelUit(null, persoon, bouwActie(20120729), null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(persoon.getNationaliteiten().iterator().next(), meldingen.get(0));
    }

    @Test
    public void testGeldigheidRedenVerkrijgingVoorReedsOpgehaaldeRedenVerkrijgingMetDatumAanvangMaarZonderDatumEinde() {
        List<BerichtEntiteit> meldingen;

        final RedenVerkrijgingNLNationaliteit redenVerkrijging =
            bouwRedenVerkrijging(GELDIGE_REDEN_VERKRIJGING_CODE, new DatumEvtDeelsOnbekendAttribuut(20120325), null);

        // Test met datum voor aanvang geldigheid nationaliteit
        PersoonBericht persoon = bouwPersoon(redenVerkrijging);
        meldingen = bedrijfsregel.voerRegelUit(null, persoon, bouwActie(20120101), null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(persoon.getNationaliteiten().iterator().next(), meldingen.get(0));

        // Test met datum 1 dag voor aanvang geldigheid nationaliteit
        persoon = bouwPersoon(redenVerkrijging);
        meldingen = bedrijfsregel.voerRegelUit(null, persoon, bouwActie(20120324), null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(persoon.getNationaliteiten().iterator().next(), meldingen.get(0));

        // Test met datum gelijk aan aanvang geldigheid nationaliteit
        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(redenVerkrijging), bouwActie(20120325), null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum na aanvang geldigheid
        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(redenVerkrijging), bouwActie(20120603), null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum in de toekomst
        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(redenVerkrijging), bouwActie(20170101), null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testOngeldigheidBijOntbrekenDatumAanvangRedenVerkrijging() {
        final RedenVerkrijgingNLNationaliteit redenVerkrijging =
            bouwRedenVerkrijging(GELDIGE_REDEN_VERKRIJGING_CODE_ZONDER_DATUM, null, null);
        final List<BerichtEntiteit> meldingen =
            bedrijfsregel.voerRegelUit(null, bouwPersoon(redenVerkrijging), bouwActie(20120101), null);
        Assert.assertEquals(0, meldingen.size());
    }

    /**
     * Maak een persoon met nationaliteit voor de test.
     *
     * @return een persoon met gevulde nationaliteit.
     */
    private PersoonBericht bouwPersoon(final RedenVerkrijgingNLNationaliteit redenVerkrijging) {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setNationaliteiten(new ArrayList<PersoonNationaliteitBericht>());
        if (redenVerkrijging != null) {
            PersoonNationaliteitBericht persoonNationaliteit = new PersoonNationaliteitBericht();
            PersoonNationaliteitStandaardGroepBericht standaard = new PersoonNationaliteitStandaardGroepBericht();
            standaard.setRedenVerkrijging(new RedenVerkrijgingNLNationaliteitAttribuut(redenVerkrijging));
            persoonNationaliteit.setStandaard(standaard);
            persoon.getNationaliteiten().add(persoonNationaliteit);
        }
        return persoon;
    }

    private Actie bouwActie(final int datumAanvang) {
        ActieBericht actieBericht = new ActieConversieGBABericht();
        actieBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumAanvang));
        return actieBericht;
    }

    private RedenVerkrijgingNLNationaliteit bouwRedenVerkrijging(final RedenVerkrijgingCodeAttribuut code,
            final DatumEvtDeelsOnbekendAttribuut datumAanvang, final DatumEvtDeelsOnbekendAttribuut datumEinde)
    {
        return new RedenVerkrijgingNLNationaliteit(code, null, datumAanvang, datumEinde);
    }
}
