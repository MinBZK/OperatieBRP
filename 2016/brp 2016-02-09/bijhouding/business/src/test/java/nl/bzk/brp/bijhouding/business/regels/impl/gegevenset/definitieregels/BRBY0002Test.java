/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenEindeRelatieCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkGeregistreerdPartnerschapHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonNationaliteitHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.GeregistreerdPartnerschapHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PartnerHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;


public class BRBY0002Test {

    private final BRBY0002 brby0002 = new BRBY0002();

    @Test
    public void testVraagOpKandidaatVaderNietGevonden() {
        final List<PersoonView> kandidaten =
                brby0002.bepaalKandidatenVader(
                        new PersoonView(new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE))),
                        new DatumEvtDeelsOnbekendAttribuut(20120101));

        Assert.assertEquals(0, kandidaten.size());
    }

    @Test
    public void testVraagOpKandidaatVaderZonderOverledenVaderNietNL() {
        final PersoonView moeder = bouwMoeder(20101231, null, "2", null, null, null);

        final List<PersoonView> kandidaten =
                brby0002.bepaalKandidatenVader(moeder, new DatumEvtDeelsOnbekendAttribuut(20120101));

        Assert.assertEquals(1, kandidaten.size());
    }

    @Test
    public void testVraagOpKandidaatVaderZonderOverledenVaderKindGeborenBuitenPeriodeHuwelijk() {
        final PersoonView moeder = bouwMoeder(20101231, null, "2", null, null, null);

        final List<PersoonView> kandidaten =
                brby0002.bepaalKandidatenVader(moeder, new DatumEvtDeelsOnbekendAttribuut(20090101));

        Assert.assertEquals(0, kandidaten.size());
    }

    @Test
    public void testVraagOpKandidaatVaderMetOverledenVaderNietNL() {
        final PersoonView moeder = bouwMoeder(20101231, 20110101, "2", null, null, null);

        final List<PersoonView> kandidaten =
                brby0002.bepaalKandidatenVader(moeder, new DatumEvtDeelsOnbekendAttribuut(20120101));

        Assert.assertEquals(1, kandidaten.size());
    }


    @Test
    public void testVraagOpKandidaatVaderMetOverledenVaderNietNLBuitenPeriode() {
        final PersoonView moeder = bouwMoeder(20101231, 20101231, "2", null, null, null);

        final List<PersoonView> kandidaten =
                brby0002.bepaalKandidatenVader(moeder, new DatumEvtDeelsOnbekendAttribuut(20120101));

        Assert.assertEquals(0, kandidaten.size());
    }

    @Test
    public void testVraagOpKandidaatVaderMetOverledenVaderNL() {
        final PersoonView moeder = bouwMoeder(20101231, 20110301, NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE_STRING, null,
                null, null);

        final List<PersoonView> kandidaten =
                brby0002.bepaalKandidatenVader(moeder, new DatumEvtDeelsOnbekendAttribuut(20120101));

        Assert.assertEquals(1, kandidaten.size());
    }

    @Test
    public void testVraagOpKandidaatVaderMetOverledenVaderNLBuitenPeriode() {
        final PersoonView moeder = bouwMoeder(20101231, 20110228, NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE_STRING, null,
                null, null);

        final List<PersoonView> kandidaten =
                brby0002.bepaalKandidatenVader(moeder, new DatumEvtDeelsOnbekendAttribuut(20120101));

        Assert.assertEquals(0, kandidaten.size());
    }

    @Test
    public void testVraagOpKandidaatVaderMetHuwelijkNaGeboorte() {
        final PersoonView moeder =
                bouwMoeder(20101231, 20110228, NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE_STRING, null, null, null);

        final List<PersoonView> kandidaten =
                brby0002.bepaalKandidatenVader(moeder, new DatumEvtDeelsOnbekendAttribuut(20100101));

        Assert.assertEquals(0, kandidaten.size());
    }

    @Test
    public void testVraagOpKandidaatVaderMetHuwelijkNaGeboorteEnVoorgGeboorte() {
        final PersoonView moeder =
                bouwMoeder(20050505, 20111111, NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE_STRING, 20120505, 20140611,
                        NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE_STRING);

        final List<PersoonView> kandidaten =
                brby0002.bepaalKandidatenVader(moeder, new DatumEvtDeelsOnbekendAttribuut(20110505));

        Assert.assertEquals(1, kandidaten.size());
    }

    @Test
    public void testVraagOpKandidaatVaderMoederHeeftGeregistreerdPartnerschap() {
        final PersoonHisVolledigImpl moeder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeslachtsaanduidingRecord(19800101, null, 19800101).geslachtsaanduiding(
                        Geslachtsaanduiding.VROUW).eindeRecord().build();

        final PersoonHisVolledigImpl vader = bouwVader(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE_STRING);
        bouwHuwelijkGeregistreerdPartnerschap(SoortRelatie.GEREGISTREERD_PARTNERSCHAP, 20101231, 20110101,
                                              moeder, vader);

        final List<PersoonView> kandidaten =
                brby0002.bepaalKandidatenVader(new PersoonView(moeder), new DatumEvtDeelsOnbekendAttribuut(20110505));

        Assert.assertEquals(0, kandidaten.size());
    }

    private PersoonView bouwMoeder(final Integer huwelijkDatumAanvang1, final Integer overlijdingsDatum1,
                                              final String nationaliteitCodeVader1, final Integer huwelijkDatumAanvang2,
                                              final Integer overlijdingsDatum2,
                                              final String nationaliteitCodeVader2)
    {
        final PersoonHisVolledigImpl vader = bouwVader(nationaliteitCodeVader1);

        final PersoonHisVolledigImpl moeder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeslachtsaanduidingRecord(19800101, null, 19800101).geslachtsaanduiding(
                        Geslachtsaanduiding.VROUW).eindeRecord().build();

        bouwHuwelijkGeregistreerdPartnerschap(SoortRelatie.HUWELIJK,
                                              huwelijkDatumAanvang1, overlijdingsDatum1, moeder, vader);

        voegKindBetrokkenheidToe(moeder);

        return new PersoonView(moeder);
    }

    private void voegKindBetrokkenheidToe(final PersoonHisVolledigImpl moeder) {
        final FamilierechtelijkeBetrekkingHisVolledigImpl fam =
                new FamilierechtelijkeBetrekkingHisVolledigImplBuilder().build();
        moeder.getBetrokkenheden().add(new KindHisVolledigImpl(fam, moeder));
    }

    private PersoonHisVolledigImpl bouwVader(final String nationaliteitCodeVader) {
        final PersoonHisVolledigImpl vader =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                        .nieuwGeslachtsaanduidingRecord(19800101, null, 19800101).geslachtsaanduiding(
                        Geslachtsaanduiding.MAN).eindeRecord().build();

        final Nationaliteit nationaliteitWillekeurig = new Nationaliteit(
                new NationaliteitcodeAttribuut(Short.valueOf(nationaliteitCodeVader)),
                new NaamEnumeratiewaardeAttribuut("nationaliteit"),
                new DatumEvtDeelsOnbekendAttribuut(19601231),
                null);
        final PersoonNationaliteitHisVolledigImpl nationaliteit = new PersoonNationaliteitHisVolledigImplBuilder(vader,
                nationaliteitWillekeurig)
                .nieuwStandaardRecord(DatumAttribuut.vandaag().getWaarde(), null, DatumAttribuut.vandaag().getWaarde())
                .eindeRecord().build();

        vader.getNationaliteiten().add(nationaliteit);

        return vader;
    }

    private void bouwHuwelijkGeregistreerdPartnerschap(final SoortRelatie soortRelatie,
                                                       final Integer huwelijkDatumAanvang,
                                                       final Integer overlijdingsDatum,
                                                       final PersoonHisVolledigImpl moeder,
                                                       final PersoonHisVolledigImpl vader)
    {
        final HuwelijkGeregistreerdPartnerschapHisVolledigImpl relatie;

        if (SoortRelatie.HUWELIJK == soortRelatie) {
            relatie = new HuwelijkHisVolledigImplBuilder().build();
        } else if (SoortRelatie.GEREGISTREERD_PARTNERSCHAP == soortRelatie) {
            relatie = new GeregistreerdPartnerschapHisVolledigImplBuilder().build();
        } else {
            throw new IllegalArgumentException("Soort relatie moet huwelijk of GP zijn.");
        }

        final RelatieStandaardGroepBericht standaard = new RelatieStandaardGroepBericht();
        standaard.setDatumAanvang(new DatumEvtDeelsOnbekendAttribuut(huwelijkDatumAanvang));
        if (overlijdingsDatum != null) {
            standaard.setDatumEinde(new DatumEvtDeelsOnbekendAttribuut(overlijdingsDatum));
            final RedenEindeRelatieAttribuut redenBeeindigingRelatie = StatischeObjecttypeBuilder.bouwRedenEindeRelatie(
                    RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_OVERLIJDEN_CODE_STRING, "omschrijving reden.");

            standaard.setRedenEinde(redenBeeindigingRelatie);
        }

        final HisRelatieModel hisHuwelijkGeregistreerdPartnerschapModel = new HisRelatieModel(relatie, standaard, maakActieHuwelijk(20101231));

        relatie.getRelatieHistorie().voegToe(hisHuwelijkGeregistreerdPartnerschapModel);

        final PartnerHisVolledigImpl partnerVrouw = new PartnerHisVolledigImplBuilder(relatie, moeder).build();
        final PartnerHisVolledigImpl partnerMan = new PartnerHisVolledigImplBuilder(relatie, vader).build();

        relatie.getBetrokkenheden().add(partnerVrouw);
        relatie.getBetrokkenheden().add(partnerMan);


        moeder.getBetrokkenheden().add(partnerVrouw);
    }


    /**
     * Creeert een standaard actie voor registratie geboorte.
     *
     * @return het actie model
     */
    private ActieModel maakActieHuwelijk(final Integer datumAanvang) {
        return new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
                new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                        SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND), null,
                        null, null), null, new DatumEvtDeelsOnbekendAttribuut(datumAanvang), null, DatumTijdAttribuut.nu(), null);
    }
}
