/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.nationaliteit.acties.nationaliteit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenEindeRelatieCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.BijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestNationaliteitBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestRedenEindeRelatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestSoortDocumentBuilder;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieBronBericht;
import nl.bzk.brp.model.bericht.kern.ActieConversieGBABericht;
import nl.bzk.brp.model.bericht.kern.DocumentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.GeregistreerdPartnerschapHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkGeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.util.hisvolledig.kern.GeregistreerdPartnerschapHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test voor de {@link BRBY0158} bedrijfsregel.
 */
public class BRBY0158Test {

    private final BRBY0158 brby0158 = new BRBY0158();

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0158, brby0158.getRegel());
    }

    @Test
    public void testNationaliteitenNull() {
        final List<BerichtEntiteit> overtreders = brby0158.voerRegelUit(null, bouwNieuweSituatie(null), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNationaliteitenLeeg() {
        final PersoonBericht persoon = bouwNieuweSituatie(null);
        persoon.setNationaliteiten(new ArrayList<PersoonNationaliteitBericht>());
        final List<BerichtEntiteit> overtreders = brby0158.voerRegelUit(null, persoon, null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testVreemdeNationaliteit() {
        final List<BerichtEntiteit> overtreders = brby0158.voerRegelUit(null, bouwNieuweSituatie(new NationaliteitcodeAttribuut((short) 1234)), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitBronnenNull() {
        final ActieBericht actie = bouwActie();
        actie.setBronnen(null);
        final List<BerichtEntiteit> overtreders = brby0158.voerRegelUit(null, bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), actie, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitBronnenLeeg() {
        final ActieBericht actie = bouwActie();
        actie.setBronnen(new ArrayList<ActieBronBericht>());
        final List<BerichtEntiteit> overtreders = brby0158.voerRegelUit(null, bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), actie, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitDocumentNull() {
        final ActieBericht actie = bouwActie();
        ReflectionTestUtils.setField(actie.getBronnen().iterator().next(), "document", null);
        final List<BerichtEntiteit> overtreders = brby0158.voerRegelUit(null, bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), actie, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitBronAnders() {
        final ActieBericht actie = bouwActie();
        ReflectionTestUtils.setField(actie.getBronnen().iterator().next().getDocument().getSoort().getWaarde(), "naam", new NaamEnumeratiewaardeAttribuut("Ander document"));
        final List<BerichtEntiteit> overtreders = brby0158.voerRegelUit(null, bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), actie, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitBronKoninklijkBesluitHuidigeSituatieNull() {
        final List<BerichtEntiteit> overtreders = brby0158.voerRegelUit(null, bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), bouwActie(), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitBronKoninklijkBesluitTeKortIngezetene() {
        final List<BerichtEntiteit> overtreders = brby0158.voerRegelUit(
                bouwHuidigeSituatieIngezetene(dagDeg(2010, 2012)),
                bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), bouwActie(), null);
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitBronKoninklijkBesluitTeKortIngezeteneMeederePeriodes() {
        final List<BerichtEntiteit> overtreders = brby0158.voerRegelUit(
                bouwHuidigeSituatieIngezetene(dagDeg(2008, 2009, 2010, 2012)),
                bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), bouwActie(), null);
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitBronKoninklijkBesluitLangGenoegIngezetene() {
        final List<BerichtEntiteit> overtreders = brby0158.voerRegelUit(
                bouwHuidigeSituatieIngezetene(dagDeg(2005, null)),
                bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), bouwActie(), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitBronKoninklijkBesluitLangGenoegMaarGeenIngezetene() {
        final PersoonHisVolledig persoon = bouwHuidigeSituatieIngezetene(dagDeg(2005, null));
        ReflectionTestUtils.setField(persoon.getPersoonBijhoudingHistorie().iterator().next(), "bijhoudingsaard",
                                     new BijhoudingsaardAttribuut(Bijhoudingsaard.NIET_INGEZETENE));
        final List<BerichtEntiteit> overtreders = brby0158.voerRegelUit(
                persoon,
                bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), bouwActie(), null);
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitBronKoninklijkBesluitLangGenoegIngezeteneMeerderePeriodes() {
        final List<BerichtEntiteit> overtreders = brby0158.voerRegelUit(
                bouwHuidigeSituatieIngezetene(dagDeg(2005, 2008, 2010, 2012)),
                bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), bouwActie(), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitBronKoninklijkBesluitHuwelijkTeKort() {
        final DatumEvtDeelsOnbekendAttribuut datumEvtDeelsOnbekendAttribuut = new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag());
        datumEvtDeelsOnbekendAttribuut.voegJaarToe(-1);
        final List<BerichtEntiteit> overtreders = brby0158.voerRegelUit(bouwHuidigeSituatieHuwelijk(datumEvtDeelsOnbekendAttribuut, dagDeg()),
                bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), bouwActie(), null);
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitBronKoninklijkBesluitPartnerschapTeKort() {
        final DatumEvtDeelsOnbekendAttribuut datumEvtDeelsOnbekendAttribuut = new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag());
        datumEvtDeelsOnbekendAttribuut.voegJaarToe(-1);
        final PersoonHisVolledig persoon = bouwHuidigeSituatieHuwelijk(datumEvtDeelsOnbekendAttribuut, dagDeg());
        ReflectionTestUtils.setField(persoon.getBetrokkenheden().iterator().next().getRelatie(), "soort",
                                     new SoortRelatieAttribuut(SoortRelatie.GEREGISTREERD_PARTNERSCHAP));
        final List<BerichtEntiteit> overtreders = brby0158.voerRegelUit(persoon, bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), bouwActie(),
                                                                        null);
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitBronKoninklijkBesluitHuwelijkGeenNederlander() {
        final DatumEvtDeelsOnbekendAttribuut datumEvtDeelsOnbekendAttribuut = new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag());
        datumEvtDeelsOnbekendAttribuut.voegJaarToe(-3);
        final PersoonHisVolledig persoon = bouwHuidigeSituatieHuwelijk(datumEvtDeelsOnbekendAttribuut, dagDeg(2005, null));
        final HuwelijkGeregistreerdPartnerschapHisVolledig huwelijk =
            (HuwelijkGeregistreerdPartnerschapHisVolledig) persoon.getBetrokkenheden().iterator().next().getRelatie();
        ReflectionTestUtils.setField(huwelijk.geefPartnerVan(persoon).getPersoon()
                .getNationaliteiten().iterator().next().getNationaliteit().getWaarde(), "code", new NationaliteitcodeAttribuut((short) 1234));
        final List<BerichtEntiteit> overtreders = brby0158.voerRegelUit(
                persoon,
                bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), bouwActie(), null);
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitBronKoninklijkBesluitHuwelijkNietMeerNederlander() {
        final DatumEvtDeelsOnbekendAttribuut datumEvtDeelsOnbekendAttribuut = new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag());
        datumEvtDeelsOnbekendAttribuut.voegJaarToe(-3);
        final List<BerichtEntiteit> overtreders =
            brby0158.voerRegelUit(bouwHuidigeSituatieHuwelijk(datumEvtDeelsOnbekendAttribuut, dagDeg(jaarGeleden(5), jaarGeleden(1))),
                                  bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), bouwActie(), null);
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitBronKoninklijkBesluitHuwelijkWelNederlander() {
        final DatumEvtDeelsOnbekendAttribuut datumEvtDeelsOnbekendAttribuut = new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag());
        datumEvtDeelsOnbekendAttribuut.voegJaarToe(-3);
        final List<BerichtEntiteit> overtreders =
            brby0158.voerRegelUit(bouwHuidigeSituatieHuwelijk(datumEvtDeelsOnbekendAttribuut, dagDeg(jaarGeleden(1), null)),
                                  bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), bouwActie(), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitBronKoninklijkBesluitOmgezetHuwelijkTeKort() {
        final DatumEvtDeelsOnbekendAttribuut datumEvtDeelsOnbekendAttribuutAanvang = new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag());
        datumEvtDeelsOnbekendAttribuutAanvang.voegJaarToe(-1);
        final DatumEvtDeelsOnbekendAttribuut datumEvtDeelsOnbekendAttribuutEind = new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag());
        datumEvtDeelsOnbekendAttribuutEind.voegJaarToe(-2);
        final List<BerichtEntiteit> overtreders = brby0158.voerRegelUit(
                bouwOmgezetPartnerschap(bouwHuidigeSituatieHuwelijk(datumEvtDeelsOnbekendAttribuutAanvang, dagDeg(jaarGeleden(3), null)),
                                        datumEvtDeelsOnbekendAttribuutEind),
                bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), bouwActie(), null);
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitBronKoninklijkBesluitOmgezetHuwelijkLangGenoeg() {
        final DatumEvtDeelsOnbekendAttribuut datumEvtDeelsOnbekendAttribuutAanvang = new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag());
        datumEvtDeelsOnbekendAttribuutAanvang.voegJaarToe(-1);
        final DatumEvtDeelsOnbekendAttribuut datumEvtDeelsOnbekendAttribuutEind = new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag());
        datumEvtDeelsOnbekendAttribuutEind.voegJaarToe(-3);
        final List<BerichtEntiteit> overtreders = brby0158.voerRegelUit(
                bouwOmgezetPartnerschap(bouwHuidigeSituatieHuwelijk(datumEvtDeelsOnbekendAttribuutAanvang, dagDeg(jaarGeleden(3), null)),
                                        datumEvtDeelsOnbekendAttribuutEind), bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), bouwActie(), null);
        Assert.assertEquals(0, overtreders.size());
    }

    private PersoonHisVolledig bouwHuidigeSituatieIngezetene(final Integer[] datumAanvangEindeIngezetene) {
        final PersoonHisVolledigImplBuilder persoonBuilder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        for (int i = 0; i < datumAanvangEindeIngezetene.length; i += 2) {
            persoonBuilder
                    .nieuwBijhoudingRecord(datumAanvangEindeIngezetene[i], datumAanvangEindeIngezetene[i + 1], datumAanvangEindeIngezetene[i])
                        .bijhoudingsaard(Bijhoudingsaard.INGEZETENE)
                    .eindeRecord();
        }
        return persoonBuilder.build();
    }

    private PersoonHisVolledig bouwHuidigeSituatieHuwelijk(final DatumEvtDeelsOnbekendAttribuut datumAanvangHuwelijk, final Integer[] datumAanvangEindePartnerNederlander) {
        final PersoonHisVolledigImpl persoon1 = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        ReflectionTestUtils.setField(persoon1, "iD", 1);
        final PersoonHisVolledigImpl persoon2 = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        ReflectionTestUtils.setField(persoon2, "iD", 2);
        final PersoonNationaliteitHisVolledigImplBuilder nationaliteitBuilder = new PersoonNationaliteitHisVolledigImplBuilder(
            TestNationaliteitBuilder.maker().metCode(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE).maak());
        for (int i = 0; i < datumAanvangEindePartnerNederlander.length; i += 2) {
            nationaliteitBuilder
                    .nieuwStandaardRecord(datumAanvangEindePartnerNederlander[i], datumAanvangEindePartnerNederlander[i + 1], datumAanvangEindePartnerNederlander[i])
                    .eindeRecord();
        }
        persoon2.getNationaliteiten().add(nationaliteitBuilder.build());

        final HuwelijkHisVolledigImpl huwelijk = new HuwelijkHisVolledigImplBuilder()
                .nieuwStandaardRecord(datumAanvangHuwelijk.getWaarde())
                    .datumAanvang(datumAanvangHuwelijk)
                .eindeRecord()
                .build();

        final PartnerHisVolledigImpl partner1 = new PartnerHisVolledigImpl(huwelijk, persoon1);
        final PartnerHisVolledigImpl partner2 = new PartnerHisVolledigImpl(huwelijk, persoon2);
        persoon1.getBetrokkenheden().add(partner1);
        persoon2.getBetrokkenheden().add(partner2);
        huwelijk.getBetrokkenheden().add(partner1);
        huwelijk.getBetrokkenheden().add(partner2);

        return persoon1;
    }

    private PersoonHisVolledig bouwOmgezetPartnerschap(final PersoonHisVolledig persoonMetHuwelijk, final DatumEvtDeelsOnbekendAttribuut datumAanvangPartnerschap) {
        final HuwelijkHisVolledigImpl huwelijk = (HuwelijkHisVolledigImpl) persoonMetHuwelijk.getBetrokkenheden().iterator().next().getRelatie();
        final PersoonHisVolledigImpl persoon1 = (PersoonHisVolledigImpl) persoonMetHuwelijk;
        ReflectionTestUtils.setField(persoon1, "iD", 1);
        final PersoonHisVolledigImpl persoon2 = huwelijk.geefPartnerVan(persoonMetHuwelijk).getPersoon();
        ReflectionTestUtils.setField(persoon2, "iD", 2);

        final GeregistreerdPartnerschapHisVolledigImpl partnerschap = new GeregistreerdPartnerschapHisVolledigImplBuilder()
            .nieuwStandaardRecord(datumAanvangPartnerschap.getWaarde())
            .datumAanvang(datumAanvangPartnerschap)
            .datumEinde(huwelijk.getRelatieHistorie().iterator().next().getDatumAanvang())
            .redenEinde(TestRedenEindeRelatieBuilder.maker()
                    .metCode(RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_OMZETTING_SOORT_VERBINTENIS_CODE).maak())
            .eindeRecord()
            .build();

        final PartnerHisVolledigImpl partner1 = new PartnerHisVolledigImpl(partnerschap, persoon1);
        final PartnerHisVolledigImpl partner2 = new PartnerHisVolledigImpl(partnerschap, persoon2);
        persoon1.getBetrokkenheden().add(partner1);
        persoon2.getBetrokkenheden().add(partner2);
        partnerschap.getBetrokkenheden().add(partner1);
        partnerschap.getBetrokkenheden().add(partner2);

        return persoon1;
    }

    private Integer[] dagDeg(final Integer ... jaarAanvangEinde) {
        final Integer[] dagDeg = new Integer[jaarAanvangEinde.length];
        for (int i = 0; i < jaarAanvangEinde.length; i++) {
            if (jaarAanvangEinde[i] != null) {
                dagDeg[i] = 10000 * jaarAanvangEinde[i] + 101;
            }
        }
        return dagDeg;
    }

    @SuppressWarnings("deprecation")
    private Integer jaarGeleden(final int aantal) {
        return new Date().getYear() + 1900 - aantal;
    }

    private PersoonBericht bouwNieuweSituatie(final NationaliteitcodeAttribuut nationaliteit) {
        final PersoonBericht persoon = new PersoonBericht();
        if (nationaliteit != null) {
            persoon.setNationaliteiten(new ArrayList<PersoonNationaliteitBericht>());
            final PersoonNationaliteitBericht persoonNationaliteit = new PersoonNationaliteitBericht();
            persoonNationaliteit.setNationaliteit(new NationaliteitAttribuut(
                TestNationaliteitBuilder.maker().metCode(nationaliteit).maak()));
            persoon.getNationaliteiten().add(persoonNationaliteit);
        }
        return persoon;
    }

    private ActieBericht bouwActie() {
        final ActieBericht actie = new ActieConversieGBABericht();
        actie.setTijdstipRegistratie(DatumTijdAttribuut.nu());
        actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
        actie.setBronnen(new ArrayList<ActieBronBericht>());
        final DocumentBericht document = new DocumentBericht();
        document.setSoort(new SoortDocumentAttribuut(
            TestSoortDocumentBuilder.maker().metNaam(NaamEnumeratiewaardeAttribuut.DOCUMENT_NAAM_KONINKLIJK_BESLUIT).maak()));
        final ActieBronBericht bron = new ActieBronBericht();
        bron.setDocument(document);
        actie.getBronnen().add(bron);
        return actie;
    }
}
