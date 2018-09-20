/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.verstrekkingsbeperkingspecifiek;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieConversieGBABericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVerstrekkingsbeperkingBericht;
import nl.bzk.brp.model.logisch.kern.Actie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor het testen van de bedrijfsregel BRBY0193.
 */
public class BRBY0193Test {

    private static final PartijCodeAttribuut GELDIGE_PARTIJ_CODE              = new PartijCodeAttribuut(34);
    private static final PartijCodeAttribuut GELDIGE_PARTIJ_CODE_ZONDER_DATUM = new PartijCodeAttribuut(17);

    private BRBY0193                         bedrijfsregel;

    @Before
    public void init() {
        bedrijfsregel = new BRBY0193();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0193, bedrijfsregel.getRegel());
    }

    @Test
    public void testGetLogger() {
        Assert.assertNotNull(bedrijfsregel.getLogger());
    }

    @Test
    public void testGeenVerstrekkingsbeperkingen() {
        final List<BerichtEntiteit> meldingen;

        meldingen = bedrijfsregel.voerRegelUit(null, new PersoonBericht(), null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testZonderNieuweSituatieGeenMeldingen() {
        final List<BerichtEntiteit> meldingen;

        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(null), null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testGeenPartij() {
        final List<BerichtEntiteit> meldingen;
        final PersoonBericht persoon = bouwPersoon(bouwPartij(GELDIGE_PARTIJ_CODE, null, null));
        ReflectionTestUtils.setField(persoon.getVerstrekkingsbeperkingen().iterator().next(), "gemeenteVerordening",
                null);
        meldingen = bedrijfsregel.voerRegelUit(null, persoon, null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testGeldigheidMetDatumAanvangEnDatumEinde() {
        List<BerichtEntiteit> meldingen;

        final Partij partij =
            bouwPartij(GELDIGE_PARTIJ_CODE, new DatumEvtDeelsOnbekendAttribuut(20120325),
                    new DatumEvtDeelsOnbekendAttribuut(20120728));

        // Test met datum voor aanvang geldigheid partij.
        PersoonBericht persoon = bouwPersoon(partij);
        meldingen = bedrijfsregel.voerRegelUit(null, persoon, bouwActie(20120101), null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(persoon.getVerstrekkingsbeperkingen().iterator().next(), meldingen.get(0));

        // Test met datum 1 dag voor aanvang geldigheid partij.
        persoon = bouwPersoon(partij);
        meldingen = bedrijfsregel.voerRegelUit(null, persoon, bouwActie(20120324), null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(persoon.getVerstrekkingsbeperkingen().iterator().next(), meldingen.get(0));

        // Test met datum gelijk aan aanvang geldigheid partij.
        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(partij), bouwActie(20120325), null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum tussen aanvang geldigheid partij. en einde geledigheid partij.
        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(partij), bouwActie(20120603), null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum 1 dag voor einde geldigheid partij.
        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(partij), bouwActie(20120727), null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum gelijk aan einde geldigheid partij.
        persoon = bouwPersoon(partij);
        meldingen = bedrijfsregel.voerRegelUit(null, persoon, bouwActie(20120728), null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(persoon.getVerstrekkingsbeperkingen().iterator().next(), meldingen.get(0));

        // Test met datum 1 dag na einde geldigheid partij.
        persoon = bouwPersoon(partij);
        meldingen = bedrijfsregel.voerRegelUit(null, persoon, bouwActie(20120729), null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(persoon.getVerstrekkingsbeperkingen().iterator().next(), meldingen.get(0));
    }

    @Test
    public void testGeldigheidMetDatumAanvangMaarZonderDatumEinde() {
        List<BerichtEntiteit> meldingen;

        final Partij partij = bouwPartij(GELDIGE_PARTIJ_CODE, new DatumEvtDeelsOnbekendAttribuut(20120325), null);

        // Test met datum voor aanvang geldigheid partij
        PersoonBericht persoon = bouwPersoon(partij);
        meldingen = bedrijfsregel.voerRegelUit(null, persoon, bouwActie(20120101), null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(persoon.getVerstrekkingsbeperkingen().iterator().next(), meldingen.get(0));

        // Test met datum 1 dag voor aanvang geldigheid partij
        persoon = bouwPersoon(partij);
        meldingen = bedrijfsregel.voerRegelUit(null, persoon, bouwActie(20120324), null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(persoon.getVerstrekkingsbeperkingen().iterator().next(), meldingen.get(0));

        // Test met datum gelijk aan aanvang geldigheid partij
        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(partij), bouwActie(20120325), null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum na aanvang geldigheid
        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(partij), bouwActie(20120603), null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum in de toekomst
        meldingen = bedrijfsregel.voerRegelUit(null, bouwPersoon(partij), bouwActie(20170101), null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testOngeldigheidBijOntbrekenDatumAanvangRedenVerkrijging() {
        final Partij partij = bouwPartij(GELDIGE_PARTIJ_CODE_ZONDER_DATUM, null, null);
        final List<BerichtEntiteit> meldingen =
            bedrijfsregel.voerRegelUit(null, bouwPersoon(partij), bouwActie(20120101), null);
        Assert.assertEquals(0, meldingen.size());
    }

    private PersoonBericht bouwPersoon(final Partij partij) {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setVerstrekkingsbeperkingen(new ArrayList<PersoonVerstrekkingsbeperkingBericht>());
        if (partij != null) {
            final PersoonVerstrekkingsbeperkingBericht verstrekkingsbeperking =
                new PersoonVerstrekkingsbeperkingBericht();
            verstrekkingsbeperking.setGemeenteVerordening(new PartijAttribuut(partij));
            persoon.getVerstrekkingsbeperkingen().add(verstrekkingsbeperking);
        }
        return persoon;
    }

    private Actie bouwActie(final int datumAanvang) {
        final ActieBericht actieBericht = new ActieConversieGBABericht();
        actieBericht.setTijdstipRegistratie(new DatumTijdAttribuut(new DatumAttribuut(datumAanvang).toDate()));
        return actieBericht;
    }

    private Partij bouwPartij(final PartijCodeAttribuut code, final DatumEvtDeelsOnbekendAttribuut datumAanvang,
            final DatumEvtDeelsOnbekendAttribuut datumEinde)
    {
        return TestPartijBuilder.maker().metCode(code).metDatumAanvang(datumAanvang).metDatumEinde(datumEinde).maak();
    }
}
