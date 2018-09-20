/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geboorte;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
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
 * Unit test voor het testen van de bedrijfsregel BRBY01032.
 */
public class BRBY01032Test {

    private static final GemeenteCodeAttribuut GELDIGE_GEMEENTE_CODE              = new GemeenteCodeAttribuut(
                                                                                          (short) 34);
    private static final GemeenteCodeAttribuut GELDIGE_GEMEENTE_CODE_ZONDER_DATUM = new GemeenteCodeAttribuut(
                                                                                          (short) 17);

    private BRBY01032                          bedrijfsregel;

    @Before
    public void init() {
        bedrijfsregel = new BRBY01032();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY01032, bedrijfsregel.getRegel());
    }

    @Test
    public void testGetLogger() {
        Assert.assertNotNull(bedrijfsregel.getLogger());
    }

    @Test
    public void testZonderNieuweSituatieGeenMeldingen() {
        final List<BerichtEntiteit> meldingen;

        meldingen = bedrijfsregel.voerRegelUit(null, bouwFamilie(null, null), null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testGeldigheidGemeenteVoorReedsOpgehaaldeGemeenteMetDatumAanvangEnDatumEinde() {
        List<BerichtEntiteit> meldingen;

        final Gemeente gemeente =
            bouwGemeente(GELDIGE_GEMEENTE_CODE, new DatumEvtDeelsOnbekendAttribuut(20120325),
                    new DatumEvtDeelsOnbekendAttribuut(20120728));

        // Test met datum voor aanvang geldigheid gemeente
        FamilierechtelijkeBetrekkingBericht familie =
            bouwFamilie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20120101));
        meldingen = bedrijfsregel.voerRegelUit(null, familie, null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(familie.getBetrokkenheden().iterator().next().getPersoon(), meldingen.get(0));

        // Test met datum 1 dag voor aanvang geldigheid gemeente
        familie = bouwFamilie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20120324));
        meldingen = bedrijfsregel.voerRegelUit(null, familie, null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(familie.getBetrokkenheden().iterator().next().getPersoon(), meldingen.get(0));

        // Test met datum gelijk aan aanvang geldigheid gemeente
        meldingen =
            bedrijfsregel.voerRegelUit(null, bouwFamilie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20120325)), null,
                    null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum tussen aanvang geldigheid gemeente en einde geledigheid gemeente
        meldingen =
            bedrijfsregel.voerRegelUit(null, bouwFamilie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20120603)), null,
                    null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum 1 dag voor einde geldigheid gemeente
        meldingen =
            bedrijfsregel.voerRegelUit(null, bouwFamilie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20120727)), null,
                    null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum gelijk aan einde geldigheid gemeente
        familie = bouwFamilie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20120728));
        meldingen = bedrijfsregel.voerRegelUit(null, familie, null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(familie.getBetrokkenheden().iterator().next().getPersoon(), meldingen.get(0));

        // Test met datum 1 dag na einde geldigheid gemeente
        familie = bouwFamilie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20120729));
        meldingen = bedrijfsregel.voerRegelUit(null, familie, null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(familie.getBetrokkenheden().iterator().next().getPersoon(), meldingen.get(0));
    }

    @Test
    public void testGeldigheidGemeenteVoorReedsOpgehaaldeGemeenteMetDatumAanvangMaarZonderDatumEinde() {
        List<BerichtEntiteit> meldingen;

        final Gemeente gemeente =
            bouwGemeente(GELDIGE_GEMEENTE_CODE, new DatumEvtDeelsOnbekendAttribuut(20120325), null);

        // Test met datum voor aanvang geldigheid gemeente
        FamilierechtelijkeBetrekkingBericht familie =
            bouwFamilie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20120101));
        meldingen = bedrijfsregel.voerRegelUit(null, familie, null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(familie.getBetrokkenheden().iterator().next().getPersoon(), meldingen.get(0));

        // Test met datum 1 dag voor aanvang geldigheid gemeente
        familie = bouwFamilie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20120324));
        meldingen = bedrijfsregel.voerRegelUit(null, familie, null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(familie.getBetrokkenheden().iterator().next().getPersoon(), meldingen.get(0));

        // Test met datum gelijk aan aanvang geldigheid gemeente
        meldingen =
            bedrijfsregel.voerRegelUit(null, bouwFamilie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20120325)), null,
                    null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum na aanvang geldigheid
        meldingen =
            bedrijfsregel.voerRegelUit(null, bouwFamilie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20120603)), null,
                    null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum in de toekomst
        meldingen =
            bedrijfsregel.voerRegelUit(null, bouwFamilie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20170101)), null,
                    null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testOngeldigheidBijOntbrekenDatumAanvangGemeente() {
        final Gemeente gemeente = bouwGemeente(GELDIGE_GEMEENTE_CODE_ZONDER_DATUM, null, null);
        final List<BerichtEntiteit> meldingen =
            bedrijfsregel.voerRegelUit(null, bouwFamilie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20120101)), null,
                    null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testGaatOokAfVoorVader() {
        List<BerichtEntiteit> meldingen;
        final Gemeente gemeente =
            bouwGemeente(GELDIGE_GEMEENTE_CODE, new DatumEvtDeelsOnbekendAttribuut(20120325),
                    new DatumEvtDeelsOnbekendAttribuut(20120728));
        final FamilierechtelijkeBetrekkingBericht familie =
            bouwFamilie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20120501));
        final OuderBericht ouder = new OuderBericht();
        PersoonBericht vader = bouwPersoon(gemeente, new DatumEvtDeelsOnbekendAttribuut(20120320));
        ouder.setPersoon(vader);
        ouder.setRelatie(familie);
        familie.getBetrokkenheden().add(ouder);
        meldingen = bedrijfsregel.voerRegelUit(null, familie, null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(familie.getBetrokkenheden().toArray(new BetrokkenheidBericht[0])[1].getPersoon(),
                meldingen.get(0));

        // Coverage conditionals
        vader = bouwPersoon(gemeente, null);
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
    public void testOngeldigheidBijOntbrekenDatumGeboorte() {
        final Gemeente gemeente =
            bouwGemeente(GELDIGE_GEMEENTE_CODE, new DatumEvtDeelsOnbekendAttribuut(20120325), null);
        final List<BerichtEntiteit> meldingen =
            bedrijfsregel.voerRegelUit(null, bouwFamilie(gemeente, null), null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testOngeldigheidBijOntbrekenGemeenteGeboorte() {
        final List<BerichtEntiteit> meldingen =
            bedrijfsregel.voerRegelUit(null, bouwFamilie(null, new DatumEvtDeelsOnbekendAttribuut(20120101)), null,
                    null);
        Assert.assertEquals(0, meldingen.size());
    }

    /**
     * Instantieert een nieuwe {@link nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente} met de opgegeven code, datum
     * van aanvang en datum van einde geldigheid.
     *
     * @param gemeentecode de gemeente code.
     * @param datumAanvang de datum van aanvang van geldigheid van de gemeente.
     * @param datumEinde de datum van einde van geldigheid van de gemeente.
     * @return een nieuwe gemeente met opgegeven waardes.
     */
    private Gemeente bouwGemeente(final GemeenteCodeAttribuut gemeentecode,
            final DatumEvtDeelsOnbekendAttribuut datumAanvang, final DatumEvtDeelsOnbekendAttribuut datumEinde)
    {
        return new Gemeente(null, gemeentecode, null, null, datumAanvang, datumEinde);
    }

    /**
     * Bouwt een familie.
     *
     * @param gemeente gemeente bij geboorte
     * @param datumGeboorte datum bij geboorte
     * @return familierechtelijke betrekking bericht
     */
    private FamilierechtelijkeBetrekkingBericht bouwFamilie(final Gemeente gemeente,
            final DatumEvtDeelsOnbekendAttribuut datumGeboorte)
    {
        final FamilierechtelijkeBetrekkingBericht familie = new FamilierechtelijkeBetrekkingBericht();
        final PersoonBericht persoon = bouwPersoon(gemeente, datumGeboorte);
        final KindBericht kind = new KindBericht();
        kind.setRelatie(familie);
        kind.setPersoon(persoon);
        familie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        familie.getBetrokkenheden().add(kind);
        return familie;
    }

    private PersoonBericht bouwPersoon(final Gemeente gemeente, final DatumEvtDeelsOnbekendAttribuut datumGeboorte) {
        final PersoonBericht persoon = new PersoonBericht();
        if (datumGeboorte != null) {
            final PersoonGeboorteGroepBericht persoonGeboorte = new PersoonGeboorteGroepBericht();
            persoon.setGeboorte(persoonGeboorte);
            if (gemeente != null) {
                persoonGeboorte.setGemeenteGeboorte(new GemeenteAttribuut(gemeente));
            }
            persoonGeboorte.setDatumGeboorte(datumGeboorte);
        }
        return persoon;
    }
}
