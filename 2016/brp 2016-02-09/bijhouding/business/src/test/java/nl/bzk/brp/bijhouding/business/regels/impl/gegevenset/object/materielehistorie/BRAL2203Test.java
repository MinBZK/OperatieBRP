/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.object.materielehistorie;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNaamgebruikBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.RelatieTestUtil;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

public class BRAL2203Test {

    private final ActieModel actieModel = new ActieModel(null, null, null, null, null, DatumTijdAttribuut.nu(), null);

    @Test
    public void testGetRegel() throws Exception {
        Assert.assertEquals(Regel.BRAL2203, new BRAL2203().getRegel());
    }

    @Test
    public void testDatumAanvangGeldigheidActieNaGeboorteDatumPersoonHuidigeSituatie() {
        final List<BerichtEntiteit> berichtEntiteits =
            new BRAL2203().voerRegelUit(maakPersoonView(19830404), null, maakActie(20120101), null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testDatumAanvangGeldigheidActieVoorGeboorteDatumPersoonHuidigeSituatie() {
        final Actie actie = maakActie(19830404);
        final List<BerichtEntiteit> berichtEntiteits =
            new BRAL2203().voerRegelUit(maakPersoonView(20120101), null, actie, null);
        Assert.assertFalse(berichtEntiteits.isEmpty());
        Assert.assertEquals(actie, berichtEntiteits.get(0));
    }

    @Test
    public void testDatumAanvangGeldigheidActieOpGeboorteDatumPersoonHuidigeSituatie() {
        final Actie actie = maakActie(20120101);
        final List<BerichtEntiteit> berichtEntiteits =
            new BRAL2203().voerRegelUit(maakPersoonView(20120101), null, actie, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testDatumAanvangGeldigheidActieNaGeboorteDatumKindInFamilieRechtelijkeBetrekkingHuidigeSituatie() {
        final ActieModel actieModel =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               20120101), null, new DatumTijdAttribuut(new Date()), null);

        final PersoonHisVolledigImpl kind = maakPersoon(20120101);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(maakPersoon(19830101), maakPersoon(19850205), kind, actieModel);
        final FamilierechtelijkeBetrekkingHisVolledigImpl familierechtelijkeBetrekking =
                RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind);
        final Actie actie = maakActie(20130101);
        final List<BerichtEntiteit> berichtEntiteits =
                new BRAL2203().voerRegelUit(new FamilierechtelijkeBetrekkingView(
                        familierechtelijkeBetrekking, DatumTijdAttribuut.nu(), DatumAttribuut.vandaag()),
                                            null, actie, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testDatumAanvangGeldigheidActieOpGeboorteDatumOuderInFamilieRechtelijkeBetrekkingHuidigeSituatie() {
        final ActieModel actieModel =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               20120101), null, new DatumTijdAttribuut(new Date()), null);
        final PersoonHisVolledigImpl kind = maakPersoon(20110101);
        final PersoonHisVolledigImpl ouder = maakPersoon(20120101);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(maakPersoon(19830101), ouder, kind, actieModel);
        final FamilierechtelijkeBetrekkingHisVolledigImpl familierechtelijkeBetrekking =
                RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind);
        final Actie actie = maakActie(20120101);
        final List<BerichtEntiteit> berichtEntiteits =
                new BRAL2203().voerRegelUit(
                        new FamilierechtelijkeBetrekkingView(familierechtelijkeBetrekking, DatumTijdAttribuut.nu(),
                                                             DatumAttribuut.vandaag()),
                        null, actie, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testDatumAanvangGeldigheidActieOpGeboorteDatumKindInFamilieRechtelijkeBetrekkingNieuweSituatie() {
        final Actie actie = maakActie(20120101);
        final RelatieBericht relatie = new FamilierechtelijkeBetrekkingBericht();
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());

        final PersoonBericht kind = maakPersoonBericht(20120101);
        final KindBericht kindBericht = new KindBericht();
        kindBericht.setPersoon(kind);

        final PersoonBericht ouder1 = maakPersoonBericht(19830101);
        final OuderBericht ouder1Bericht = new OuderBericht();
        ouder1Bericht.setPersoon(ouder1);

        final PersoonBericht ouder2 = maakPersoonBericht(19840505);
        final OuderBericht ouder2Bericht = new OuderBericht();
        ouder2Bericht.setPersoon(ouder2);

        relatie.getBetrokkenheden().add(kindBericht);
        relatie.getBetrokkenheden().add(ouder1Bericht);
        relatie.getBetrokkenheden().add(ouder2Bericht);

        final List<BerichtEntiteit> berichtEntiteits =
                new BRAL2203().voerRegelUit(null, relatie, actie, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testDatumAanvangGeldigheidActieVoorGeboorteDatumKindInFamilieRechtelijkeBetrekkingNieuweSituatie() {
        final Actie actie = maakActie(20110101);
        final RelatieBericht relatie = new FamilierechtelijkeBetrekkingBericht();
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());

        final PersoonBericht kind = maakPersoonBericht(20120101);
        final KindBericht kindBericht = new KindBericht();
        kindBericht.setPersoon(kind);

        final PersoonBericht ouder1 = maakPersoonBericht(19830101);
        final OuderBericht ouder1Bericht = new OuderBericht();
        ouder1Bericht.setPersoon(ouder1);

        final PersoonBericht ouder2 = maakPersoonBericht(19840505);
        final OuderBericht ouder2Bericht = new OuderBericht();
        ouder2Bericht.setPersoon(ouder2);

        relatie.getBetrokkenheden().add(kindBericht);
        relatie.getBetrokkenheden().add(ouder1Bericht);
        relatie.getBetrokkenheden().add(ouder2Bericht);

        final List<BerichtEntiteit> berichtEntiteits =
                new BRAL2203().voerRegelUit(null, relatie, actie, null);
        Assert.assertTrue(berichtEntiteits.size() == 1);
    }

    /**
     * Maakt een actie.
     *
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @return actie
     */
    private Actie maakActie(final Integer datumAanvangGeldigheid) {
        final ActieBericht actie = new ActieRegistratieNaamgebruikBericht();
        actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumAanvangGeldigheid));
        return actie;
    }

    /**
     * Maakt een persoon view.
     *
     * @param datumGeboorte datum geboorte
     * @return persoon view
     */
    private PersoonView maakPersoonView(final Integer datumGeboorte) {
        return new PersoonView(
            new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(actieModel)
                .datumGeboorte(datumGeboorte).eindeRecord().build());
    }

    /**
     * Maak een persoon bericht.
     *
     * @param datumGeboorte geboorte datum van persoon
     * @return een persoonbericht met de geboorte groep en datum
     */
    private PersoonBericht maakPersoonBericht(final Integer datumGeboorte) {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setGeboorte(new PersoonGeboorteGroepBericht());
        persoon.getGeboorte().setDatumGeboorte(new DatumEvtDeelsOnbekendAttribuut(datumGeboorte));
        return persoon;
    }

    /**
     * Maakt een persoon.
     *
     * @param datumGeboorte datum geboorte
     * @return persoon his volledig impl
     */
    private PersoonHisVolledigImpl maakPersoon(final Integer datumGeboorte) {
        return new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .nieuwGeboorteRecord(actieModel)
            .datumGeboorte(datumGeboorte).eindeRecord().build();
    }

}
