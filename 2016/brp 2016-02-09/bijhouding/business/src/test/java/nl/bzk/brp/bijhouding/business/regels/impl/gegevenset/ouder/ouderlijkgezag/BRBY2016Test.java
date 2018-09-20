/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.ouder.ouderlijkgezag;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels.BRBY0003;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieConversieGBABericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderlijkGezagGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class BRBY2016Test {

    private static final boolean OUDER_ZONDER_OUDERLIJK_GEZAG = false;

    private static final boolean OUDER_MET_OUDERLIJK_GEZAG    = true;
    private final BRBY2016 brby2016 = new BRBY2016();

    @Before
    public void init() {
        ReflectionTestUtils.setField(brby2016, "brby0003", new BRBY0003());
    }

    @Test
    public void testZonderBetrokkenheden() {
        final PersoonBericht nieuweSituatie = maakNieuwePersoonZonderBetrokkenheden();
        nieuweSituatie.setBetrokkenheden(null);
        final List<BerichtEntiteit> overtreders =
            brby2016.voerRegelUit(maakHuidigePersoon(20100101), nieuweSituatie, bouwActie(), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testEenMinderjarigKindEnOuderZonderOuderlijkGezag() {
        final List<BerichtEntiteit> overtreders =
            brby2016.voerRegelUit(maakHuidigePersoon(20100101),
                    maakNieuwePersoon(OUDER_ZONDER_OUDERLIJK_GEZAG), bouwActie(), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testEenMeerderjarigKindEnOuderZonderOuderlijkGezag() {
        final List<BerichtEntiteit> overtreders =
            brby2016.voerRegelUit(maakHuidigePersoon(19750101),
                    maakNieuwePersoon(OUDER_ZONDER_OUDERLIJK_GEZAG), bouwActie(), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testEenMinderjarigKindEnOuderMetOuderlijkGezag() {
        final List<BerichtEntiteit> overtreders =
            brby2016.voerRegelUit(maakHuidigePersoon(20100101), maakNieuwePersoon(OUDER_MET_OUDERLIJK_GEZAG),
                    bouwActie(), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testEenMeerderjarigKindEnOuderMetOuderlijkGezag() {
        final List<BerichtEntiteit> overtreders =
            brby2016.voerRegelUit(maakHuidigePersoon(19750101), maakNieuwePersoon(OUDER_MET_OUDERLIJK_GEZAG),
                    bouwActie(), null);
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY2016, brby2016.getRegel());
    }

    /**
     * Maak een nieuw PersoonBericht zonder betrokkenheden. Puur voor test coverage
     *
     * @return nieuw PersoonBericht zonder betrokkenheden.
     */
    private PersoonBericht maakNieuwePersoonZonderBetrokkenheden() {
        return maakNieuwePersoon(0, false);
    }

    /**
     * Maak een nieuw PersoonBericht aan voor een kind en ouder
     *
     * @param ouderMetOuderlijkGezag boolean true wanneer de ouder ouderlijkGezag heeft, anders false
     * @return nieuw PersoonBericht voor kind en ouder
     */
    private PersoonBericht maakNieuwePersoon(final boolean ouderMetOuderlijkGezag) {
        return maakNieuwePersoon(1, ouderMetOuderlijkGezag);
    }

    /**
     * Maak een nieuw PersoonBericht aan voor een kind en ouder
     *
     * @param aantalBetrokkenheden int aantal te creeeren betrokkenheden
     * @param ouderMetOuderlijkGezag boolean true wanneer de ouder ouderlijkGezag heeft, anders false
     * @return nieuw PersoonBericht voor kind en ouder
     */
    private PersoonBericht maakNieuwePersoon(final int aantalBetrokkenheden, final boolean ouderMetOuderlijkGezag) {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        for (int i = 0; i < aantalBetrokkenheden; i++) {
            final KindBericht kind = new KindBericht();
            persoon.getBetrokkenheden().add(kind);

            final OuderBericht ouder = new OuderBericht();
            final OuderOuderlijkGezagGroepBericht ouderlijkGezag = new OuderOuderlijkGezagGroepBericht();
            if (ouderMetOuderlijkGezag) {
                ouderlijkGezag.setIndicatieOuderHeeftGezag(JaNeeAttribuut.JA);
            } else {
                ouderlijkGezag.setIndicatieOuderHeeftGezag(JaNeeAttribuut.NEE);
            }
            ouder.setOuderlijkGezag(ouderlijkGezag);

            final FamilierechtelijkeBetrekkingBericht familie = new FamilierechtelijkeBetrekkingBericht();
            familie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
            familie.getBetrokkenheden().add(ouder);
            familie.getBetrokkenheden().add(kind);
            ouder.setRelatie(familie);
            kind.setRelatie(familie);
        }
        return persoon;
    }

    private PersoonView maakHuidigePersoon(final int datumGeboorteKind) {
        final PersoonHisVolledigImpl persoon =
            new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).nieuwGeboorteRecord(datumGeboorteKind)
                    .datumGeboorte(datumGeboorteKind).landGebiedGeboorte((short) 222).eindeRecord().build();
        return new PersoonView(persoon);
    }

    private ActieBericht bouwActie() {
        final ActieBericht actie = new ActieConversieGBABericht();
        actie.setTijdstipRegistratie(DatumTijdAttribuut.nu());
        actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
        return actie;
    }
}
