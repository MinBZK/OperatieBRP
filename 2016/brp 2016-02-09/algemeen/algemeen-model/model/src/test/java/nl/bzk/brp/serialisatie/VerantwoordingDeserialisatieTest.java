/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie;

import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AktenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentIdentificatieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieTenOpzichteVanAdres;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SorteervolgordeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.IdComparator;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieConversieGBABericht;
import nl.bzk.brp.model.bericht.kern.DocumentBericht;
import nl.bzk.brp.model.bericht.kern.DocumentStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.AdministratieveHandelingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.RelatieHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieBronModel;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.DocumentModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;
import nl.bzk.brp.serialisatie.persoon.PersoonHisVolledigStringSerializer;
import nl.bzk.brp.util.StamgegevenBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.VerantwoordingTestUtil;
import nl.bzk.brp.util.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import org.apache.commons.lang.StringUtils;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.datumedge.hamcrest.json.SameJSONAs;


@RunWith(Parameterized.class)
public class VerantwoordingDeserialisatieTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final JacksonJsonSerializer<PersoonHisVolledigImpl> serializer = new PersoonHisVolledigStringSerializer();
    private final int id;

    public VerantwoordingDeserialisatieTest(final Integer id) {
        this.id = id;
    }

    @Parameterized.Parameters(name = "Persoon met ID:{0}")
    public static Collection<Object[]> data() {
        final Object[][] data = new Object[][]{ { 7 }, { 13 }, { 21 } };
        return Arrays.asList(data);
    }

    @Test
    public void roundtripLevertDezelfdeJson() throws IOException {
        // given
        final PersoonHisVolledigImpl persoon = maakPersoon(id);

        Assume.assumeTrue(
            persoon.getPersoonNaamgebruikHistorie().getActueleRecord().getVerantwoordingInhoud() == persoon
                .getPersoonGeboorteHistorie().getActueleRecord().getVerantwoordingInhoud());

        // when
        final byte[] data = serializer.serialiseer(persoon);
        LOGGER.info("{}", new String(data));

        final PersoonHisVolledigImpl terugPersoon = serializer.deserialiseer(data);

        Assume.assumeThat(terugPersoon.getPersoonNaamgebruikHistorie().getActueleRecord().getVerantwoordingInhoud(),
            notNullValue());
        Assume.assumeTrue(
            persoon.getPersoonNaamgebruikHistorie().getActueleRecord().getVerantwoordingInhoud() == persoon
                .getPersoonGeboorteHistorie().getActueleRecord().getVerantwoordingInhoud());

        final byte[] terugData = serializer.serialiseer(terugPersoon);
        LOGGER.info("{}", new String(terugData));

        // then
        assertThat(id + " doet JSON roundtrip", new String(data), SameJSONAs.sameJSONAs(new String(terugData))
            .allowingAnyArrayOrdering());

        assertThat(StringUtils.countMatches(new String(data), "\"actieInhoud\":{"), lessThanOrEqualTo(4));
    }

    @Test
    public void roundtripLevertDezelfdeObjecten() throws IOException {
        // given
        final PersoonHisVolledigImpl persoon = maakPersoon(id);

        Assume.assumeTrue(
            persoon.getPersoonNaamgebruikHistorie().getActueleRecord().getVerantwoordingInhoud() == persoon
                .getPersoonGeboorteHistorie().getActueleRecord().getVerantwoordingInhoud());

        // when
        final byte[] data = serializer.serialiseer(persoon);
        LOGGER.info("{}", new String(data));

        final PersoonHisVolledigImpl terugPersoon = serializer.deserialiseer(data);
        Assume.assumeThat(terugPersoon.getPersoonNaamgebruikHistorie().getActueleRecord().getVerantwoordingInhoud(),
            notNullValue());

        // then
        assertTrue(terugPersoon.getPersoonNaamgebruikHistorie().getActueleRecord().getVerantwoordingInhoud()
            == terugPersoon
            .getPersoonGeboorteHistorie().getActueleRecord().getVerantwoordingInhoud());

        final RelatieHisVolledigImpl relatie = terugPersoon.getBetrokkenheden().iterator().next().getRelatie();
        assertTrue(relatie.getBetrokkenheden().size() >= 2);

        assertNotNull(relatie.getRelatieHistorie().getActueleRecord().getVerantwoordingInhoud());
    }

    private PersoonHisVolledigImpl maakPersoon(final int persoonId) {
        PersoonHisVolledigImpl persoon = null;
        if (persoonId == 7) {
            persoon = maakPersoon7();
        } else if (persoonId == 13) {
            persoon = maakPersoon13();
        } else if (persoonId == 21) {
            persoon = maakPersoon21();
        }
        return persoon;
    }

    private PersoonHisVolledigImpl maakPersoon7() {
        final PersoonHisVolledigImpl persoon = maakBasisPersoon(1);
        final PersoonHisVolledigImpl partner = maakBasisPersoon(2);

        final ActieModel huwelijkActie = maakActie(123L, 456L);
        // gelijk id moet in json leiden tot gelijke objecten (dus slechts 1x serialiseren)
        final ActieModel kopieHuwelijkActie = maakActie(123L, 456L);
        final HuwelijkHisVolledigImpl huwelijk =
            new HuwelijkHisVolledigImplBuilder().nieuwStandaardRecord(kopieHuwelijkActie).datumAanvang(20120101)
                .eindeRecord().build();
        ReflectionTestUtils.setField(huwelijk, "iD", 1234);

        final BetrokkenheidHisVolledigImpl huwelijkpartner1 = new PartnerHisVolledigImpl(huwelijk, persoon);
        ReflectionTestUtils.setField(huwelijkpartner1, "iD", 3241);
        persoon.getBetrokkenheden().add(huwelijkpartner1);
        final BetrokkenheidHisVolledigImpl huwelijkpartner2 = new PartnerHisVolledigImpl(huwelijk, partner);
        ReflectionTestUtils.setField(huwelijkpartner2, "iD", 6121);
        partner.getBetrokkenheden().add(huwelijkpartner2);

        huwelijk.getBetrokkenheden().add(huwelijkpartner1);
        huwelijk.getBetrokkenheden().add(huwelijkpartner2);

        persoon.getPersoonAfgeleidAdministratiefHistorie().voegToe(
            new HisPersoonAfgeleidAdministratiefModel(persoon, huwelijkActie.getAdministratieveHandeling(), huwelijkActie.getTijdstipRegistratie(),
                new SorteervolgordeAttribuut((byte) 1), new JaNeeAttribuut(Boolean.FALSE), null, huwelijkActie)
        );
        partner.getPersoonAfgeleidAdministratiefHistorie().voegToe(
            new HisPersoonAfgeleidAdministratiefModel(persoon, huwelijkActie.getAdministratieveHandeling(), huwelijkActie.getTijdstipRegistratie(),
                new SorteervolgordeAttribuut((byte) 1), new JaNeeAttribuut(Boolean.FALSE), null, huwelijkActie)
        );

        persoon.getAdministratieveHandelingen().add(VerantwoordingTestUtil.converteer(huwelijkActie.getAdministratieveHandeling()));

        return persoon;
    }

    private PersoonHisVolledigImpl maakPersoon13() {
        final PersoonHisVolledigImpl persoon = maakBasisPersoon(1);
        final PersoonHisVolledigImpl moeder = maakBasisPersoon(2);
        final PersoonHisVolledigImpl vader = maakBasisPersoon(3);

        final ActieModel famActie = maakActie();
        final FamilierechtelijkeBetrekkingHisVolledigImpl fam =
            new FamilierechtelijkeBetrekkingHisVolledigImplBuilder()
                .nieuwStandaardRecord(famActie)
                .eindeRecord().build();

        ReflectionTestUtils.setField(fam, "iD", 1234);
        final BetrokkenheidHisVolledigImpl famKind = new KindHisVolledigImpl(fam, persoon);
        ReflectionTestUtils.setField(famKind, "iD", 3241);
        persoon.getBetrokkenheden().add(famKind);
        final BetrokkenheidHisVolledigImpl famMoeder = new OuderHisVolledigImpl(fam, moeder);
        ReflectionTestUtils.setField(famMoeder, "iD", 79879);
        moeder.getBetrokkenheden().add(famMoeder);
        final BetrokkenheidHisVolledigImpl famVader =
            new OuderHisVolledigImplBuilder(fam, vader).nieuwOuderschapRecord(famActie).eindeRecord(35435).build();
        ReflectionTestUtils.setField(famVader, "iD", 35435);
        vader.getBetrokkenheden().add(famVader);
        fam.getBetrokkenheden().add(famKind);
        fam.getBetrokkenheden().add(famMoeder);
        fam.getBetrokkenheden().add(famVader);

        persoon.getPersoonAfgeleidAdministratiefHistorie().voegToe(
            new HisPersoonAfgeleidAdministratiefModel(persoon, famActie.getAdministratieveHandeling(), famActie.getTijdstipRegistratie(),
                new SorteervolgordeAttribuut((byte) 1), new JaNeeAttribuut(Boolean.FALSE), null, famActie)
        );
        moeder.getPersoonAfgeleidAdministratiefHistorie().voegToe(
            new HisPersoonAfgeleidAdministratiefModel(persoon, famActie.getAdministratieveHandeling(), famActie.getTijdstipRegistratie(),
                new SorteervolgordeAttribuut((byte) 2), new JaNeeAttribuut(Boolean.FALSE), null, famActie)
        );
        vader.getPersoonAfgeleidAdministratiefHistorie().voegToe(
            new HisPersoonAfgeleidAdministratiefModel(persoon, famActie.getAdministratieveHandeling(), famActie.getTijdstipRegistratie(),
                new SorteervolgordeAttribuut((byte) 2), new JaNeeAttribuut(Boolean.FALSE), null, famActie)
        );

        // afnemerindicatie
        final Partij partij = StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde();
        final Leveringsautorisatie abonnement = TestLeveringsautorisatieBuilder.maker().metNaam("Test Abo").metPopulatiebeperking("WAAR")
            .metProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN)
            .maak();

        final PersoonAfnemerindicatieHisVolledigImplBuilder afnemerindicatieBuilder =
            new PersoonAfnemerindicatieHisVolledigImplBuilder(persoon, partij, abonnement).nieuwStandaardRecord(
                TestDienstBuilder.dummy()).eindeRecord(987L);
        final Set<PersoonAfnemerindicatieHisVolledigImpl> afnemerindicaties = new HashSet<>(1);
        afnemerindicaties.add(afnemerindicatieBuilder.build());

        persoon.setAfnemerindicaties(afnemerindicaties);

        final List<AdministratieveHandelingHisVolledigImpl> handelingen = persoon.getAdministratieveHandelingen();
        handelingen.add(VerantwoordingTestUtil.converteer(famActie.getAdministratieveHandeling()));

        return persoon;
    }

    private PersoonHisVolledigImpl maakPersoon21() {
        final PersoonHisVolledigImpl kind = maakBasisPersoon(1);
        final PersoonHisVolledigImpl persoon = maakBasisPersoon(3);

        final ActieModel famActie = maakActie();
        final FamilierechtelijkeBetrekkingHisVolledigImpl fam =
            new FamilierechtelijkeBetrekkingHisVolledigImplBuilder()
                .nieuwStandaardRecord(famActie)
                .eindeRecord().build();

        ReflectionTestUtils.setField(fam, "iD", 1234);
        final BetrokkenheidHisVolledigImpl famKind = new KindHisVolledigImpl(fam, kind);
        ReflectionTestUtils.setField(famKind, "iD", 3241);
        kind.getBetrokkenheden().add(famKind);
        final BetrokkenheidHisVolledigImpl famVader =
            new OuderHisVolledigImplBuilder(fam, persoon).nieuwOuderschapRecord(famActie).eindeRecord(35435)
                .build();
        ReflectionTestUtils.setField(famVader, "iD", 35435);
        persoon.getBetrokkenheden().add(famVader);
        fam.getBetrokkenheden().add(famKind);
        fam.getBetrokkenheden().add(famVader);

        kind.getPersoonAfgeleidAdministratiefHistorie().voegToe(
            new HisPersoonAfgeleidAdministratiefModel(persoon, famActie.getAdministratieveHandeling(), famActie.getTijdstipRegistratie(),
                new SorteervolgordeAttribuut((byte) 1), new JaNeeAttribuut(Boolean.FALSE), null, famActie)
        );
        persoon.getPersoonAfgeleidAdministratiefHistorie().voegToe(
            new HisPersoonAfgeleidAdministratiefModel(persoon, famActie.getAdministratieveHandeling(), famActie.getTijdstipRegistratie(),
                new SorteervolgordeAttribuut((byte) 2), new JaNeeAttribuut(Boolean.FALSE), null, famActie)
        );

        final List<AdministratieveHandelingHisVolledigImpl> handelingen = persoon.getAdministratieveHandelingen();
        handelingen.add(VerantwoordingTestUtil.converteer(famActie.getAdministratieveHandeling()));

        return persoon;
    }

    private PersoonHisVolledigImpl maakBasisPersoon(final int persoonId) {
        final ActieModel geboorteActie = maakActie();
        final ActieModel verstrekkingsbeperkingActie = maakActie();
        final ActieModel derdeHeeftGezagActie = maakActie();

        final PersoonHisVolledigImpl persoon =
            new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(geboorteActie)
                .datumGeboorte(20110101)
                .landGebiedGeboorte((short) 6030)
                .eindeRecord()
                .nieuwNaamgebruikRecord(geboorteActie)
                .adellijkeTitelNaamgebruik("Koning")
                .eindeRecord()
                .nieuwSamengesteldeNaamRecord(geboorteActie)
                .geslachtsnaamstam(String.format("%szoon", persoonId))
                .voornamen(String.format("Johannes de %se", persoonId))
                .eindeRecord()
                .voegPersoonAdresToe(
                    new PersoonAdresHisVolledigImplBuilder().nieuwStandaardRecord(geboorteActie)
                        .aangeverAdreshouding("P").afgekorteNaamOpenbareRuimte("kort")
                        .buitenlandsAdresRegel3("buitenlandje")
                        .locatieTenOpzichteVanAdres(LocatieTenOpzichteVanAdres.TO).eindeRecord()
                        .build())
                .voegPersoonNationaliteitToe(
                    new PersoonNationaliteitHisVolledigImplBuilder(
                        StamgegevenBuilder.bouwDynamischStamgegeven(
                            Nationaliteit.class, 6030)).nieuwStandaardRecord(geboorteActie)
                        .redenVerkrijging((short) 321).redenVerlies((short) 4763).eindeRecord().build())
                .voegPersoonIndicatieDerdeHeeftGezagToe(
                    new PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder()
                        .nieuwStandaardRecord(derdeHeeftGezagActie).waarde(Ja.J).eindeRecord().build())
                .voegPersoonIndicatieVolledigeVerstrekkingsbeperkingToe(
                    new PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder()
                        .nieuwStandaardRecord(verstrekkingsbeperkingActie).waarde(Ja.J).eindeRecord()
                        .build())
                .build();

        ReflectionTestUtils.setField(persoon, "iD", persoonId);

        persoon.getPersoonAfgeleidAdministratiefHistorie().voegToe(
            new HisPersoonAfgeleidAdministratiefModel(persoon, geboorteActie.getAdministratieveHandeling(), geboorteActie.getTijdstipRegistratie(),
                new SorteervolgordeAttribuut((byte) 1), new JaNeeAttribuut(Boolean.FALSE), null, geboorteActie)
        );

        final List<AdministratieveHandelingHisVolledigImpl> handelingen = persoon.getAdministratieveHandelingen();
        handelingen.addAll(VerantwoordingTestUtil.converteer(Arrays.asList(geboorteActie.getAdministratieveHandeling(),
            verstrekkingsbeperkingActie.getAdministratieveHandeling(),
            derdeHeeftGezagActie.getAdministratieveHandeling())));

        return persoon;
    }

    private ActieModel maakActie() {
        Long actieId = new Random().nextLong();
        Long admhndId = new Random().nextLong();
        return maakActie(actieId, admhndId);
    }

    private ActieModel maakActie(Long actieId, Long admhndId) {
        final AdministratieveHandelingModel admhnd = maakAdmhnd(admhndId);

        final ActieBericht actieBericht = new ActieConversieGBABericht();
        actieBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20110101));
        actieBericht.setDatumEindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20110101));
        actieBericht.setTijdstipRegistratie(new DatumTijdAttribuut(new DatumAttribuut(20110101).toDate()));
        final ActieModel actieModel = new ActieModel(actieBericht, admhnd);
        ReflectionTestUtils.setField(actieModel, "iD", actieId);

        final SortedSet<ActieModel> acties = new TreeSet<>(new IdComparator());
        acties.add(actieModel);
        ReflectionTestUtils.setField(admhnd, "acties", acties);


        final ActieBronModel bron = maakBron(actieModel);
        actieModel.getBronnen().add(bron);

        return actieModel;
    }

    private ActieBronModel maakBron(final ActieModel actie) {
        final DocumentModel document = maakDocument();
        final ActieBronModel bron =
            new ActieBronModel(actie, document, null, new OmschrijvingEnumeratiewaardeAttribuut("Omschr."));
        ReflectionTestUtils.setField(bron, "iD", new Random().nextLong());

        return bron;
    }

    private DocumentModel maakDocument() {
        final SoortDocument akte =
            new SoortDocument(new NaamEnumeratiewaardeAttribuut("Akte"), new OmschrijvingEnumeratiewaardeAttribuut(
                "Geboorteakte"), new VolgnummerAttribuut(1));
        final DocumentStandaardGroepBericht standaardGroepBericht = new DocumentStandaardGroepBericht();
        standaardGroepBericht.setAktenummer(new AktenummerAttribuut("123-AAA-0000"));
        standaardGroepBericht.setOmschrijving(new DocumentOmschrijvingAttribuut("Een officiele geboorteakte"));
        standaardGroepBericht.setIdentificatie(new DocumentIdentificatieAttribuut("GEB00000"));
        standaardGroepBericht.setPartij(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_SGRAVENHAGE);

        final DocumentBericht documentBericht = new DocumentBericht();
        documentBericht.setSoort(new SoortDocumentAttribuut(akte));
        documentBericht.setStandaard(standaardGroepBericht);

        final DocumentModel documentModel = new DocumentModel(documentBericht);
        ReflectionTestUtils.setField(documentModel, "iD", new Random().nextLong());

        return documentModel;
    }

    private AdministratieveHandelingModel maakAdmhnd() {
        return maakAdmhnd(new Random().nextLong());
    }

    private AdministratieveHandelingModel maakAdmhnd(Long admhndId) {
        final AdministratieveHandelingModel handelingModel =
            new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_SGRAVENHAGE, new OntleningstoelichtingAttribuut(
                "toelichting"), DatumTijdAttribuut.terug24Uur());

        ReflectionTestUtils.setField(handelingModel, "iD", admhndId);

        return handelingModel;
    }
}
