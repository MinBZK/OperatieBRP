/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.util.HashSet;
import java.util.Random;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkGeregistreerdPartnerschapHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.KindHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder;
import org.springframework.test.util.ReflectionTestUtils;


/**
 *
 */
public final class RelatieTestUtil {

    private RelatieTestUtil() {
    }

    public static void bouwFamilieRechtelijkeBetrekking(final PersoonHisVolledigImpl ouder1,
            final PersoonHisVolledigImpl ouder2, final PersoonHisVolledigImpl kind,
            final boolean defaultMagGeleverdWordenVoorAttributen, final ActieModel actieModel)
    {
        bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind, DatumAttribuut.vandaag().getWaarde(),
                defaultMagGeleverdWordenVoorAttributen, actieModel);
    }

    public static void bouwFamilieRechtelijkeBetrekking(final PersoonHisVolledigImpl ouder1,
            final PersoonHisVolledigImpl ouder2, final PersoonHisVolledigImpl kind, final ActieModel actieModel)
    {
        bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind, false, actieModel);
    }

    public static void bouwFamilieRechtelijkeBetrekking(final PersoonHisVolledigImpl ouder1,
            final PersoonHisVolledigImpl ouder2, final PersoonHisVolledigImpl kind, final int ouderschapVanaf, final ActieModel actieModel)
    {
        bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind, ouderschapVanaf, false, actieModel);
    }

    public static void bouwFamilieRechtelijkeBetrekking(final PersoonHisVolledigImpl ouder1,
            final PersoonHisVolledigImpl ouder2, final PersoonHisVolledigImpl kind, final int ouderschapVanaf,
            final boolean defaultMagGeleverdWordenVoorAttributen, final ActieModel actieModel)
    {
        if (ouder1 == null && ouder2 == null) {
            throw new IllegalArgumentException("Minstens één ouder is verplicht in een familierechtelijke betrekking");
        }

        final FamilierechtelijkeBetrekkingHisVolledigImpl familierechtelijkeBetrekkingHisVolledigKind =
                new FamilierechtelijkeBetrekkingHisVolledigImplBuilder(defaultMagGeleverdWordenVoorAttributen).build();
        ReflectionTestUtils.setField(familierechtelijkeBetrekkingHisVolledigKind, "iD",
                Math.abs(new Random().nextInt()));

        familierechtelijkeBetrekkingHisVolledigKind.setBetrokkenheden(new HashSet<BetrokkenheidHisVolledigImpl>());
        final BetrokkenheidHisVolledigImpl kind1Betr =
                new KindHisVolledigImplBuilder(familierechtelijkeBetrekkingHisVolledigKind, kind).metVerantwoording(actieModel).build();
        ReflectionTestUtils.setField(kind1Betr, "iD", Math.abs(new Random().nextInt()));
        familierechtelijkeBetrekkingHisVolledigKind.getBetrokkenheden().add(kind1Betr);
        kind.getBetrokkenheden().add(kind1Betr);

        if (ouder1 != null) {
            final OuderHisVolledigImpl ouder1BetrKind =
                    new OuderHisVolledigImplBuilder(familierechtelijkeBetrekkingHisVolledigKind, ouder1,
                            defaultMagGeleverdWordenVoorAttributen)
                            .nieuwOuderschapRecord(ouderschapVanaf, null, ouderschapVanaf).indicatieOuder(Ja.J)
                            .indicatieOuderUitWieKindIsGeboren(true)
                            .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel()).metVerantwoording(actieModel).build();
            familierechtelijkeBetrekkingHisVolledigKind.getBetrokkenheden().add(ouder1BetrKind);
            ouder1BetrKind.getOuderOuderschapHistorie().getActueleRecord().getTijdstipRegistratie()
                    .setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            ouder1BetrKind.getOuderOuderschapHistorie().getActueleRecord().getDatumAanvangGeldigheid()
                    .setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            ouder1BetrKind.getOuderOuderschapHistorie().getActueleRecord().getVerantwoordingInhoud()
                    .setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            ouder1.getBetrokkenheden().add(ouder1BetrKind);
            ReflectionTestUtils.setField(ouder1BetrKind, "iD", Math.abs(new Random().nextInt()));
            ReflectionTestUtils.setField(ouder1BetrKind.getOuderOuderschapHistorie().getActueleRecord()
                    .getVerantwoordingInhoud(), "iD", 200L);
        }

        if (ouder2 != null) {
            final OuderHisVolledigImpl ouder2BetrKind =
                    new OuderHisVolledigImplBuilder(familierechtelijkeBetrekkingHisVolledigKind, ouder2,
                            defaultMagGeleverdWordenVoorAttributen)
                            .nieuwOuderschapRecord(ouderschapVanaf, null, ouderschapVanaf).indicatieOuder(Ja.J)
                            .indicatieOuderUitWieKindIsGeboren(true)
                            .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel()).metVerantwoording(actieModel).build();
            familierechtelijkeBetrekkingHisVolledigKind.getBetrokkenheden().add(ouder2BetrKind);
            ouder2.getBetrokkenheden().add(ouder2BetrKind);
            ouder2BetrKind.getOuderOuderschapHistorie().getActueleRecord().getTijdstipRegistratie()
                    .setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            ouder2BetrKind.getOuderOuderschapHistorie().getActueleRecord().getDatumAanvangGeldigheid()
                    .setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            ouder2BetrKind.getOuderOuderschapHistorie().getActueleRecord().getVerantwoordingInhoud()
                    .setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);

            ReflectionTestUtils.setField(ouder2BetrKind, "iD", Math.abs(new Random().nextInt()));
            ReflectionTestUtils.setField(ouder2BetrKind.getOuderOuderschapHistorie().getActueleRecord()
                    .getVerantwoordingInhoud(), "iD", 200L);
        }
    }

    public static FamilierechtelijkeBetrekkingHisVolledigImpl haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonRolInHeeft(
            final PersoonHisVolledigImpl persoon, final SoortBetrokkenheid rol)
    {
        for (final BetrokkenheidHisVolledigImpl betrokkenheidHisVolledig : persoon.getBetrokkenheden()) {
            if (rol == betrokkenheidHisVolledig.getRol().getWaarde()) {
                return (FamilierechtelijkeBetrekkingHisVolledigImpl) betrokkenheidHisVolledig.getRelatie();
            }
        }
        return null;
    }

    public static HuwelijkGeregistreerdPartnerschapHisVolledigImpl haalHuwelijkUitPersoonBetrokkenheden(
            final PersoonHisVolledigImpl persoon)
    {
        for (final BetrokkenheidHisVolledigImpl betrokkenheidHisVolledig : persoon.getBetrokkenheden()) {
            if (SoortBetrokkenheid.PARTNER == betrokkenheidHisVolledig.getRol().getWaarde() &&
                    betrokkenheidHisVolledig.getRelatie() instanceof HuwelijkGeregistreerdPartnerschapHisVolledigImpl) {
                return (HuwelijkGeregistreerdPartnerschapHisVolledigImpl) betrokkenheidHisVolledig.getRelatie();
            }
        }
        return null;
    }

    public static FamilierechtelijkeBetrekkingHisVolledigImpl haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(
            final PersoonHisVolledigImpl persoon)
    {
        return haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonRolInHeeft(persoon,
                SoortBetrokkenheid.KIND);
    }

    public static void bouwFamilieRechtelijkeBetrekkingMetNullWaarden(final PersoonHisVolledigImpl ouder1,
            final PersoonHisVolledigImpl ouder2, final PersoonHisVolledigImpl kind, final int ouderschapVanaf)
    {
        bouwFamilieRechtelijkeBetrekkingMetNullWaarden(ouder1, ouder2, kind, ouderschapVanaf, false);
    }

    /**
     * Bouwt een familie rechtelijke betrekking met null waarden. Deze methode kan met name gebruikt worden om
     * conditionals te testen. Voor 'standaard'-gebruik kan beter gebruik gemaakt worden van
     *
     * @param ouder1 ouder 1
     * @param ouder2 ouder 2
     * @param kind   kind
     */
    public static void bouwFamilieRechtelijkeBetrekkingMetNullWaarden(final PersoonHisVolledigImpl ouder1,
            final PersoonHisVolledigImpl ouder2, final PersoonHisVolledigImpl kind, final int ouderschapVanaf,
            final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        final FamilierechtelijkeBetrekkingHisVolledigImpl familierechtelijkeBetrekkingHisVolledig =
                new FamilierechtelijkeBetrekkingHisVolledigImplBuilder(defaultMagGeleverdWordenVoorAttributen).build();
        familierechtelijkeBetrekkingHisVolledig.setBetrokkenheden(new HashSet<BetrokkenheidHisVolledigImpl>());

        if (kind != null) {
            final BetrokkenheidHisVolledigImpl kind1Betr =
                    new KindHisVolledigImpl(familierechtelijkeBetrekkingHisVolledig, kind);
            familierechtelijkeBetrekkingHisVolledig.getBetrokkenheden().add(kind1Betr);
            kind.getBetrokkenheden().add(kind1Betr);
        }

        if (ouder1 != null) {
            final OuderHisVolledigImpl ouder1BetrKind =
                    new OuderHisVolledigImplBuilder(familierechtelijkeBetrekkingHisVolledig, ouder1,
                            defaultMagGeleverdWordenVoorAttributen)
                            .nieuwOuderschapRecord(ouderschapVanaf, null, ouderschapVanaf)
                            .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel()).build();
            familierechtelijkeBetrekkingHisVolledig.getBetrokkenheden().add(ouder1BetrKind);
            ouder1.getBetrokkenheden().add(ouder1BetrKind);
        }

        if (ouder2 != null) {
            final OuderHisVolledigImpl ouder2BetrKind =
                    new OuderHisVolledigImplBuilder(familierechtelijkeBetrekkingHisVolledig, ouder2,
                            defaultMagGeleverdWordenVoorAttributen)
                            .nieuwOuderschapRecord(ouderschapVanaf, null, ouderschapVanaf)
                            .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel()).build();
            familierechtelijkeBetrekkingHisVolledig.getBetrokkenheden().add(ouder2BetrKind);
            ouder2.getBetrokkenheden().add(ouder2BetrKind);
        }
    }
}
