/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.ouder.ouderlijkgezag;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieConversieGBABericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderlijkGezagGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class BRBY2013Test {

    private static final boolean ZONDER_OUDERLIJK_GEZAG   = false;

    private static final boolean MET_OUDERLIJK_GEZAG      = true;
    private static final boolean ZONDER_OUDERSCHAP        = false;

    private static final boolean MET_OUDERSCHAP           = true;
    private static final Integer TECHNISCHE_SLEUTEL_OUDER = 123456789;

    private static final Integer TECHNISCHE_SLEUTEL_DERDE = 987654321;
    private final BRBY2013       brby2013                 = new BRBY2013();

    @Test
    public void testZonderBetrokkenheden() {
        final PersoonBericht nieuweSituatie = maakNieuwePersoonZonderBetrokkenheden();
        nieuweSituatie.setBetrokkenheden(null);
        final List<BerichtEntiteit> overtreders =
            brby2013.voerRegelUit(maakHuidigePersoon(), nieuweSituatie, bouwActie(), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testOuderZonderOuderlijkGezag() {
        final List<BerichtEntiteit> overtreders =
            brby2013.voerRegelUit(maakHuidigePersoon(),
                    maakNieuwePersoon(TECHNISCHE_SLEUTEL_OUDER, ZONDER_OUDERLIJK_GEZAG), bouwActie(), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testOuderMetOuderlijkGezag() {
        final List<BerichtEntiteit> overtreders =
            brby2013.voerRegelUit(maakHuidigePersoon(),
                    maakNieuwePersoon(TECHNISCHE_SLEUTEL_OUDER, MET_OUDERLIJK_GEZAG), bouwActie(), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testOuderMetOuderlijkGezagZonderHuidigOuderschap() {
        final List<BerichtEntiteit> overtreders =
            brby2013.voerRegelUit(maakHuidigePersoonZonderOuderschap(),
                    maakNieuwePersoon(TECHNISCHE_SLEUTEL_OUDER, MET_OUDERLIJK_GEZAG), bouwActie(), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testOuderMetOuderlijkGezagMetHuidigOuderschap() {
        final List<BerichtEntiteit> overtreders =
            brby2013.voerRegelUit(maakHuidigePersoonMetOuderschap(),
                    maakNieuwePersoon(TECHNISCHE_SLEUTEL_OUDER, MET_OUDERLIJK_GEZAG), bouwActie(), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testDerdeMetOuderlijkGezagMetHuidigOuderschap() {
        final List<BerichtEntiteit> overtreders =
            brby2013.voerRegelUit(maakHuidigePersoonMetOuderschap(),
                    maakNieuwePersoon(TECHNISCHE_SLEUTEL_DERDE, MET_OUDERLIJK_GEZAG), bouwActie(), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testOuderMetOuderlijkGezagMetHuidigOuderlijkGezag() {
        final List<BerichtEntiteit> overtreders =
            brby2013.voerRegelUit(maakHuidigePersoonMetOuderlijkgezag(),
                    maakNieuwePersoon(TECHNISCHE_SLEUTEL_OUDER, MET_OUDERLIJK_GEZAG), bouwActie(), null);
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY2013, brby2013.getRegel());
    }

    /**
     * Maak een nieuw PersoonBericht zonder betrokkenheden. Puur voor test coverage
     *
     * @return nieuw PersoonBericht zonder betrokkenheden.
     */
    private PersoonBericht maakNieuwePersoonZonderBetrokkenheden() {
        return maakNieuwePersoon(0, null, false);
    }

    /**
     * Maak een nieuw PersoonBericht aan voor een kind en ouder
     *
     * @param ouderMetOuderlijkGezag boolean true wanneer de ouder ouderlijkGezag heeft, anders false
     * @return nieuw PersoonBericht voor kind en ouder
     */
    private PersoonBericht maakNieuwePersoon(final Integer technischeSleutel, final boolean ouderMetOuderlijkGezag) {
        return maakNieuwePersoon(1, technischeSleutel, ouderMetOuderlijkGezag);
    }

    /**
     * Maak een nieuw PersoonBericht aan voor een kind en ouder
     *
     * @param aantalBetrokkenheden int aantal te creeeren betrokkenheden
     * @param ouderMetOuderlijkGezag boolean true wanneer de ouder ouderlijkGezag heeft, anders false
     * @return nieuw PersoonBericht voor kind en ouder
     */
    private PersoonBericht maakNieuwePersoon(final int aantalBetrokkenheden, final Integer technischeSleutel,
            final boolean ouderMetOuderlijkGezag)
    {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        for (int i = 0; i < aantalBetrokkenheden; i++) {
            final KindBericht kind = new KindBericht();
            persoon.getBetrokkenheden().add(kind);

            final OuderBericht ouder = new OuderBericht();
            ouder.setObjectSleutel(technischeSleutel.toString());
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

    private PersoonView maakHuidigePersoon() {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        return new PersoonView(persoon);
    }

    private PersoonView maakHuidigePersoonZonderOuderschap() {
        return maakHuidigePersoonMetOuderschapsEnOfOuderlijkGezag(ZONDER_OUDERSCHAP, ZONDER_OUDERLIJK_GEZAG);
    }

    private PersoonView maakHuidigePersoonMetOuderschap() {
        return maakHuidigePersoonMetOuderschapsEnOfOuderlijkGezag(MET_OUDERSCHAP, ZONDER_OUDERLIJK_GEZAG);
    }

    private PersoonView maakHuidigePersoonMetOuderlijkgezag() {
        return maakHuidigePersoonMetOuderschapsEnOfOuderlijkGezag(ZONDER_OUDERSCHAP, MET_OUDERLIJK_GEZAG);
    }

    private PersoonView maakHuidigePersoonMetOuderschapsEnOfOuderlijkGezag(final boolean ouderHeeftOuderschap,
            final boolean ouderHeeftOuderlijkGezag)
    {
        final FamilierechtelijkeBetrekkingHisVolledigImpl familie = new FamilierechtelijkeBetrekkingHisVolledigImpl();

        final PersoonHisVolledigImpl ouderPersoon =
                new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final OuderHisVolledigImplBuilder ouderBuilder = new OuderHisVolledigImplBuilder(familie, ouderPersoon);

        Integer datumEindeGeldigheidOuderschap = null;
        if (!ouderHeeftOuderschap) {
            // Geen ouderschap simuleren door einde geldigheid gelijk aan aanvang.
            datumEindeGeldigheidOuderschap = 20100101;
        }

        OuderHisVolledigImpl ouder = null;
        if (ouderHeeftOuderlijkGezag) {
            ouder = ouderBuilder.nieuwOuderlijkGezagRecord(20100101, null, 20100101).eindeRecord().build();
        } else {
            ouder =
                ouderBuilder.nieuwOuderschapRecord(20100101, datumEindeGeldigheidOuderschap, 20100101).eindeRecord()
                        .build();
        }
        familie.getBetrokkenheden().add(ouder);
        ouderPersoon.getBetrokkenheden().add(ouder);
        ReflectionTestUtils.setField(ouder, "iD", TECHNISCHE_SLEUTEL_OUDER);


        final PersoonHisVolledigImpl kindPersoon =
                new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        final KindHisVolledigImpl kind = new KindHisVolledigImpl(familie, kindPersoon);
        familie.getBetrokkenheden().add(kind);
        kindPersoon.getBetrokkenheden().add(kind);

        return new PersoonView(kindPersoon);
    }

    private ActieBericht bouwActie() {
        final ActieBericht actie = new ActieConversieGBABericht();
        actie.setTijdstipRegistratie(DatumTijdAttribuut.nu());
        actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
        return actie;
    }
}
