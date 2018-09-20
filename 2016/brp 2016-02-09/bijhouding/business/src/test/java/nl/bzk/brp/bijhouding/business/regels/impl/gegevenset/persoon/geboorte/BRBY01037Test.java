/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geboorte;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Unit test voor het testen van de bedrijfsregel BRBY01037.
 */
public class BRBY01037Test {

    private static final LandGebiedCodeAttribuut GELDIGE_LAND_CODE              = new LandGebiedCodeAttribuut(
                                                                                        (short) 34);
    private static final LandGebiedCodeAttribuut GELDIGE_LAND_CODE_ZONDER_DATUM = new LandGebiedCodeAttribuut(
                                                                                        (short) 17);

    private BRBY01037                            bedrijfsregel;

    @Before
    public void init() {
        bedrijfsregel = new BRBY01037();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY01037, bedrijfsregel.getRegel());
    }

    @Test
    public void testGetLogger() {
        Assert.assertNotNull(bedrijfsregel.getLogger());
    }

    @Test
    public void testZonderNieuweSituatieGeenMeldingen() {
        final List<BerichtEntiteit> meldingen = bedrijfsregel.voerRegelUit(null, bouwFamilie(null, null), null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testGeldigheidGemeenteVoorReedsOpgehaaldeGemeenteMetDatumAanvangEnDatumEinde() {
        List<BerichtEntiteit> meldingen;

        final LandGebied land =
            bouwLand(GELDIGE_LAND_CODE, new DatumEvtDeelsOnbekendAttribuut(20120325),
                    new DatumEvtDeelsOnbekendAttribuut(20120728));

        // Test met datum voor aanvang geldigheid land
        FamilierechtelijkeBetrekkingBericht familie = bouwFamilie(land, new DatumEvtDeelsOnbekendAttribuut(20120101));
        meldingen = bedrijfsregel.voerRegelUit(null, familie, null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(familie.getBetrokkenheden().iterator().next().getPersoon(), meldingen.get(0));

        // Test met datum 1 dag voor aanvang geldigheid land
        familie = bouwFamilie(land, new DatumEvtDeelsOnbekendAttribuut(20120324));
        meldingen = bedrijfsregel.voerRegelUit(null, familie, null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(familie.getBetrokkenheden().iterator().next().getPersoon(), meldingen.get(0));

        // Test met datum gelijk aan aanvang geldigheid land
        meldingen =
            bedrijfsregel.voerRegelUit(null, bouwFamilie(land, new DatumEvtDeelsOnbekendAttribuut(20120325)), null,
                    null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum tussen aanvang geldigheid land en einde geledigheid land
        meldingen =
            bedrijfsregel.voerRegelUit(null, bouwFamilie(land, new DatumEvtDeelsOnbekendAttribuut(20120603)), null,
                    null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum 1 dag voor einde geldigheid land
        meldingen =
            bedrijfsregel.voerRegelUit(null, bouwFamilie(land, new DatumEvtDeelsOnbekendAttribuut(20120727)), null,
                    null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum gelijk aan einde geldigheid land
        familie = bouwFamilie(land, new DatumEvtDeelsOnbekendAttribuut(20120728));
        meldingen = bedrijfsregel.voerRegelUit(null, familie, null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(familie.getBetrokkenheden().iterator().next().getPersoon(), meldingen.get(0));

        // Test met datum 1 dag na einde geldigheid land
        familie = bouwFamilie(land, new DatumEvtDeelsOnbekendAttribuut(20120729));
        meldingen = bedrijfsregel.voerRegelUit(null, familie, null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(familie.getBetrokkenheden().iterator().next().getPersoon(), meldingen.get(0));
    }

    @Test
    public void testGeldigheidGemeenteVoorReedsOpgehaaldeGemeenteMetDatumAanvangMaarZonderDatumEinde() {
        List<BerichtEntiteit> meldingen;

        final LandGebied land = bouwLand(GELDIGE_LAND_CODE, new DatumEvtDeelsOnbekendAttribuut(20120325), null);

        // Test met datum voor aanvang geldigheid land
        FamilierechtelijkeBetrekkingBericht familie = bouwFamilie(land, new DatumEvtDeelsOnbekendAttribuut(20120101));
        meldingen = bedrijfsregel.voerRegelUit(null, familie, null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(familie.getBetrokkenheden().iterator().next().getPersoon(), meldingen.get(0));

        // Test met datum 1 dag voor aanvang geldigheid land
        familie = bouwFamilie(land, new DatumEvtDeelsOnbekendAttribuut(20120324));
        meldingen = bedrijfsregel.voerRegelUit(null, familie, null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(familie.getBetrokkenheden().iterator().next().getPersoon(), meldingen.get(0));

        // Test met datum gelijk aan aanvang geldigheid land
        meldingen =
            bedrijfsregel.voerRegelUit(null, bouwFamilie(land, new DatumEvtDeelsOnbekendAttribuut(20120325)), null,
                    null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum na aanvang geldigheid
        meldingen =
            bedrijfsregel.voerRegelUit(null, bouwFamilie(land, new DatumEvtDeelsOnbekendAttribuut(20120603)), null,
                    null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum in de toekomst
        meldingen =
            bedrijfsregel.voerRegelUit(null, bouwFamilie(land, new DatumEvtDeelsOnbekendAttribuut(20170101)), null,
                    null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testGaatOokAfVoorVader() {
        List<BerichtEntiteit> meldingen;
        final LandGebied land = bouwLand(GELDIGE_LAND_CODE, new DatumEvtDeelsOnbekendAttribuut(20120325), null);
        final FamilierechtelijkeBetrekkingBericht familie =
            bouwFamilie(land, new DatumEvtDeelsOnbekendAttribuut(20120501));
        final OuderBericht ouder = new OuderBericht();
        PersoonBericht vader = bouwPersoon(land, new DatumEvtDeelsOnbekendAttribuut(20120320));
        ouder.setPersoon(vader);
        ouder.setRelatie(familie);
        familie.getBetrokkenheden().add(ouder);
        meldingen = bedrijfsregel.voerRegelUit(null, familie, null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(familie.getBetrokkenheden().toArray(new BetrokkenheidBericht[0])[1].getPersoon(),
                meldingen.get(0));

        // Coverage conditionals
        vader = bouwPersoon(land, null);
        ouder.setPersoon(vader);
        ouder.setRelatie(familie);
        familie.getBetrokkenheden().add(ouder);
        meldingen = bedrijfsregel.voerRegelUit(null, familie, null, null);
        Assert.assertEquals(0, meldingen.size());

        // Coverage conditionals
        vader = bouwPersoon(null, new DatumEvtDeelsOnbekendAttribuut(20120320));
        ouder.setPersoon(vader);
        ouder.setRelatie(familie);
        familie.getBetrokkenheden().add(ouder);
        meldingen = bedrijfsregel.voerRegelUit(null, familie, null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testOngeldigheidBijOntbrekenDatumAanvangLand() {
        final LandGebied land = bouwLand(GELDIGE_LAND_CODE_ZONDER_DATUM, null, null);
        final List<BerichtEntiteit> meldingen =
            bedrijfsregel.voerRegelUit(null, bouwFamilie(land, new DatumEvtDeelsOnbekendAttribuut(20120101)), null,
                    null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testOngeldigheidBijOntbrekenLandGeboorte() {
        final List<BerichtEntiteit> meldingen =
            bedrijfsregel.voerRegelUit(null, bouwFamilie(null, new DatumEvtDeelsOnbekendAttribuut(20120101)), null,
                    null);
        Assert.assertEquals(0, meldingen.size());
    }

    /**
     * Bouwt een familie.
     *
     * @param land land van geboorte
     * @param datumGeboorte datum van geboorte
     * @return familierechtelijke betrekking bericht
     */
    private FamilierechtelijkeBetrekkingBericht bouwFamilie(final LandGebied land,
            final DatumEvtDeelsOnbekendAttribuut datumGeboorte)
    {
        final FamilierechtelijkeBetrekkingBericht familie = new FamilierechtelijkeBetrekkingBericht();
        final PersoonBericht persoon = bouwPersoon(land, datumGeboorte);
        final KindBericht kind = new KindBericht();
        kind.setRelatie(familie);
        kind.setPersoon(persoon);
        familie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        familie.getBetrokkenheden().add(kind);
        return familie;
    }

    private PersoonBericht bouwPersoon(final LandGebied land, final DatumEvtDeelsOnbekendAttribuut datumGeboorte) {
        final PersoonBericht persoon = new PersoonBericht();
        if (datumGeboorte != null) {
            final PersoonGeboorteGroepBericht persoonGeboorte = new PersoonGeboorteGroepBericht();
            persoon.setGeboorte(persoonGeboorte);
            if (land != null) {
                persoonGeboorte.setLandGebiedGeboorte(new LandGebiedAttribuut(land));
            }
            persoonGeboorte.setDatumGeboorte(datumGeboorte);
        }
        return persoon;
    }

    /**
     * Instantieert een nieuwe {@link nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied} met de opgegeven code,
     * datum
     * van aanvang en datum van einde geldigheid.
     *
     * @param code de land code.
     * @param datumAanvang de datum van aanvang van geldigheid van de land.
     * @param datumEinde de datum van einde van geldigheid van de land.
     * @return een nieuwe land met opgegeven waardes.
     */
    private LandGebied bouwLand(final LandGebiedCodeAttribuut code, final DatumEvtDeelsOnbekendAttribuut datumAanvang,
            final DatumEvtDeelsOnbekendAttribuut datumEinde)
    {
        return new LandGebied(code, null, null, datumAanvang, datumEinde);
    }
}
