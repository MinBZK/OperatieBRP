/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging;

import java.util.Date;
import java.util.Random;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardetekstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.ber.BerichtZoekcriteriaPersoonGroepBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefDetailsPersoonBericht;
import nl.bzk.brp.model.bevraging.bijhouding.ZoekPersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.RelatieTestUtil;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.springframework.test.util.ReflectionTestUtils;

public final class TestDataOpvragenPersoonBerichtUitvoerStap {

    public static final  int    ID_PERSOON_PERSOON = 123;
    public static final  int    ID_PERSOON_PARTNER = 321;
    public static final  int    ID_PERSOON_OUDER1  = 456;
    public static final  int    ID_PERSOON_OUDER2  = 789;
    private static final String I_D                = "iD";

    private TestDataOpvragenPersoonBerichtUitvoerStap() {
    }

    public static GeefDetailsPersoonBericht maakGeefDetailsPersoonBerichtMetBsn() {
        return maakGeefDetailsPersoonBericht(true, false, false);
    }

    public static GeefDetailsPersoonBericht maakGeefDetailsPersoonBerichtMetAnr() {
        return maakGeefDetailsPersoonBericht(false, true, false);
    }

    public static GeefDetailsPersoonBericht maakGeefDetailsPersoonBericht(final boolean bsnInRequest, final boolean anrInRequest,
        final boolean objectSleutelInRequest)
    {
        final GeefDetailsPersoonBericht bericht = new GeefDetailsPersoonBericht();
        bericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        if (bsnInRequest) {
            bericht.getZoekcriteriaPersoon().setBurgerservicenummer(new BurgerservicenummerAttribuut("1234"));
        }
        if (anrInRequest) {
            bericht.getZoekcriteriaPersoon().setAdministratienummer(new AdministratienummerAttribuut("1234"));
        }
        if (objectSleutelInRequest) {
            bericht.getZoekcriteriaPersoon().setObjectSleutel(new SleutelwaardetekstAttribuut("1234"));
        }

        return bericht;
    }

    public static ZoekPersoonBericht maakZoekPersoonBerichtVoorBijhoudingMetBsn() {
        final ZoekPersoonBericht bericht = new ZoekPersoonBericht();
        bericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        bericht.getZoekcriteriaPersoon().setBurgerservicenummer(new BurgerservicenummerAttribuut("1234"));
        return bericht;
    }

    public static nl.bzk.brp.model.bevraging.levering.ZoekPersoonBericht maakZoekPersoonBerichtVoorLeveringMetBsn() {
        final nl.bzk.brp.model.bevraging.levering.ZoekPersoonBericht bericht = new nl.bzk.brp.model.bevraging.levering.ZoekPersoonBericht();
        bericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        bericht.getZoekcriteriaPersoon().setBurgerservicenummer(new BurgerservicenummerAttribuut("1234"));
        return bericht;
    }

    public static ZoekPersoonBericht maakZoekPersoonBerichtVoorBijhoudingMetAnummer() {
        final ZoekPersoonBericht bericht = new ZoekPersoonBericht();
        bericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        bericht.getZoekcriteriaPersoon().setAdministratienummer(new AdministratienummerAttribuut("5678"));
        return bericht;
    }

    public static nl.bzk.brp.model.bevraging.levering.ZoekPersoonBericht maakZoekPersoonBerichtVoorLeveringMetAnummer() {
        final nl.bzk.brp.model.bevraging.levering.ZoekPersoonBericht bericht = new nl.bzk.brp.model.bevraging.levering.ZoekPersoonBericht();
        bericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        bericht.getZoekcriteriaPersoon().setAdministratienummer(new AdministratienummerAttribuut("5678"));
        return bericht;
    }

    public static ZoekPersoonBericht maakZoekPersoonBerichtVoorBijhoudingMetPostcode() {
        final ZoekPersoonBericht bericht = new ZoekPersoonBericht();
        bericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        bericht.getZoekcriteriaPersoon().setPostcode(new PostcodeAttribuut("1234AB"));
        return bericht;
    }

    public static PersoonHisVolledigImpl maakTestPersoon() {
        final PersoonHisVolledigImpl persoon = maakPersoonHisVolledig(ID_PERSOON_PERSOON);

        //Voeg een kind betrokkenheid toe
        final PersoonHisVolledigImpl ouder1 = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        ReflectionTestUtils.setField(ouder1, I_D, ID_PERSOON_OUDER1);
        final PersoonHisVolledigImpl ouder2 = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        ReflectionTestUtils.setField(ouder2, I_D, ID_PERSOON_OUDER2);

        final ActieModel actieModel =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                new DatumEvtDeelsOnbekendAttribuut(
                    20120101), null, new DatumTijdAttribuut(new Date()), null);

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, persoon, actieModel);

        // Geef de betrokkenheden id's, nodig voor de BetrokkenheidComparator.
        for (final BetrokkenheidHisVolledigImpl betrokkenheidHisVolledig : persoon.getBetrokkenheden().iterator().next()
            .getRelatie().getBetrokkenheden()) {
            ReflectionTestUtils.setField(betrokkenheidHisVolledig, I_D, Math.abs(new Random().nextInt()));
        }

        // Voeg een partner betrokkenheid toe
        // (die op hetzelfde adres woont en toevallig ook nog eens verder exact dezelfde data heeft)
        final PersoonHisVolledigImpl partner = maakPersoonHisVolledig(ID_PERSOON_PARTNER);
        final HuwelijkHisVolledigImpl huwelijk =
            new HuwelijkHisVolledigImplBuilder().nieuwStandaardRecord(20120101).eindeRecord().build();
        final PartnerHisVolledigImpl partner1Betr = new PartnerHisVolledigImpl(huwelijk, persoon);
        ReflectionTestUtils.setField(partner1Betr, I_D, Math.abs(new Random().nextInt()));
        final PartnerHisVolledigImpl partner2Betr = new PartnerHisVolledigImpl(huwelijk, partner);
        ReflectionTestUtils.setField(partner2Betr, I_D, Math.abs(new Random().nextInt()));

        huwelijk.getBetrokkenheden().add(partner1Betr);
        huwelijk.getBetrokkenheden().add(partner2Betr);
        persoon.getBetrokkenheden().add(partner1Betr);
        partner.getBetrokkenheden().add(partner2Betr);


        return persoon;
    }

    private static PersoonHisVolledigImpl maakPersoonHisVolledig(final Integer id) {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .nieuwGeboorteRecord(19800101)
            .datumGeboorte(19800101)
            .eindeRecord()
            .nieuwIdentificatienummersRecord(19800101, null, 19800101)
            .burgerservicenummer(1234)
            .administratienummer(1235L)
            .eindeRecord()
            .voegPersoonAdresToe(new PersoonAdresHisVolledigImplBuilder()
                .nieuwStandaardRecord(19800101, null, 19800101)
                .postcode("1234AB")
                .huisnummer(12)
                .huisletter("A")
                .identificatiecodeNummeraanduiding("abcd")
                .eindeRecord()
                .build())
            .build();
        ReflectionTestUtils.setField(persoon, I_D, id);
        return persoon;
    }

}
