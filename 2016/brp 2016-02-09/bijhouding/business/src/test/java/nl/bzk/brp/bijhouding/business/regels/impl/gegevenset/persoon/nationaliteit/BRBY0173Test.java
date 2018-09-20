/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.nationaliteit;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieConversieGBABericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.logisch.kern.Actie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Unit test voor het testen van de bedrijfsregel BRBY0173.
 */
public class BRBY0173Test {

    private static final NationaliteitcodeAttribuut GELDIGE_NATIONALITEIT_CODE              =
                                                                                                new NationaliteitcodeAttribuut(
                                                                                                        (short) 34);
    private static final NationaliteitcodeAttribuut GELDIGE_NATIONALITEIT_CODE_ZONDER_DATUM =
                                                                                                new NationaliteitcodeAttribuut(
                                                                                                        (short) 17);

    private BRBY0173                                bedrijfsregel;

    @Before
    public void init() {
        bedrijfsregel = new BRBY0173();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0173, bedrijfsregel.getRegel());
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
    public void testGeldigheidGemeenteVoorReedsOpgehaaldeGemeenteMetDatumAanvangEnDatumEinde() {
        List<BerichtEntiteit> meldingen;

        final Nationaliteit nationaliteit =
            bouwNationaliteit(GELDIGE_NATIONALITEIT_CODE, new DatumEvtDeelsOnbekendAttribuut(20120325),
                    new DatumEvtDeelsOnbekendAttribuut(20120728));

        // Test met datum voor aanvang geldigheid nationaliteit
        PersoonBericht persoon = bouwPersoon(nationaliteit);
        meldingen = bedrijfsregel.voerRegelUit(null, persoon, bouwActie(20120101), null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(persoon.getNationaliteiten().iterator().next(), meldingen.get(0));

        // Test met datum 1 dag voor aanvang geldigheid nationaliteit
        persoon = bouwPersoon(nationaliteit);
        meldingen = bedrijfsregel.voerRegelUit(null, persoon, bouwActie(20120324), null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(persoon.getNationaliteiten().iterator().next(), meldingen.get(0));

        // Test met datum gelijk aan aanvang geldigheid nationaliteit
        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(nationaliteit), bouwActie(20120325), null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum tussen aanvang geldigheid nationaliteit en einde geledigheid nationaliteit
        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(nationaliteit), bouwActie(20120603), null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum 1 dag voor einde geldigheid nationaliteit
        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(nationaliteit), bouwActie(20120727), null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum gelijk aan einde geldigheid nationaliteit
        persoon = bouwPersoon(nationaliteit);
        meldingen = bedrijfsregel.voerRegelUit(null, persoon, bouwActie(20120728), null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(persoon.getNationaliteiten().iterator().next(), meldingen.get(0));

        // Test met datum 1 dag na einde geldigheid nationaliteit
        persoon = bouwPersoon(nationaliteit);
        meldingen = bedrijfsregel.voerRegelUit(null, persoon, bouwActie(20120729), null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(persoon.getNationaliteiten().iterator().next(), meldingen.get(0));
    }

    @Test
    public void testGeldigheidGemeenteVoorReedsOpgehaaldeGemeenteMetDatumAanvangMaarZonderDatumEinde() {
        List<BerichtEntiteit> meldingen;

        final Nationaliteit nationaliteit =
            bouwNationaliteit(GELDIGE_NATIONALITEIT_CODE, new DatumEvtDeelsOnbekendAttribuut(20120325), null);

        // Test met datum voor aanvang geldigheid nationaliteit
        PersoonBericht persoon = bouwPersoon(nationaliteit);
        meldingen = bedrijfsregel.voerRegelUit(null, persoon, bouwActie(20120101), null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(persoon.getNationaliteiten().iterator().next(), meldingen.get(0));

        // Test met datum 1 dag voor aanvang geldigheid nationaliteit
        persoon = bouwPersoon(nationaliteit);
        meldingen = bedrijfsregel.voerRegelUit(null, persoon, bouwActie(20120324), null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(persoon.getNationaliteiten().iterator().next(), meldingen.get(0));

        // Test met datum gelijk aan aanvang geldigheid nationaliteit
        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(nationaliteit), bouwActie(20120325), null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum na aanvang geldigheid
        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(nationaliteit), bouwActie(20120603), null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum in de toekomst
        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(nationaliteit), bouwActie(20170101), null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testOngeldigheidBijOntbrekenDatumAanvangGemeente() {
        final Nationaliteit nationaliteit = bouwNationaliteit(GELDIGE_NATIONALITEIT_CODE_ZONDER_DATUM, null, null);
        final List<BerichtEntiteit> meldingen =
            bedrijfsregel.voerRegelUit(null, bouwPersoon(nationaliteit), bouwActie(20120101), null);
        Assert.assertEquals(0, meldingen.size());
    }

    /**
     * Maak een persoon met nationaliteit voor de test.
     *
     * @param nationaliteit de nationaliteit waar de relatie is/wordt geregistreerd.
     * @return een persoon met gevulde nationaliteit.
     */
    private PersoonBericht bouwPersoon(final Nationaliteit nationaliteit) {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setNationaliteiten(new ArrayList<PersoonNationaliteitBericht>());
        if (nationaliteit != null) {
            PersoonNationaliteitBericht persoonNationaliteit = new PersoonNationaliteitBericht();
            persoon.getNationaliteiten().add(persoonNationaliteit);
            persoonNationaliteit.setNationaliteit(new NationaliteitAttribuut(nationaliteit));
        }
        return persoon;
    }

    private Actie bouwActie(final int datumAanvang) {
        ActieBericht actieBericht = new ActieConversieGBABericht();
        actieBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumAanvang));
        return actieBericht;
    }

    private Nationaliteit bouwNationaliteit(final NationaliteitcodeAttribuut code,
            final DatumEvtDeelsOnbekendAttribuut datumAanvang, final DatumEvtDeelsOnbekendAttribuut datumEinde)
    {
        return new Nationaliteit(code, null, datumAanvang, datumEinde);
    }
}
