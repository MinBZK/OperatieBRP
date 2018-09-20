/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.testpersoonbouwers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AktenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentIdentificatieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RechtsgrondCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.*;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.GegevenInOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkGeregistreerdPartnerschapHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonGeslachtsnaamcomponentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieOnderCurateleHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonNationaliteitHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonReisdocumentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVoornaamHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieBronModel;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.DocumentModel;
import nl.bzk.brp.model.operationeel.kern.DocumentStandaardGroepModel;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.VerantwoordingTestUtil;
import nl.bzk.brp.util.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.GegevenInOnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.GeregistreerdPartnerschapHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.KindHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.OnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PartnerHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieOnderCurateleHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonOnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonReisdocumentHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonVoornaamHisVolledigImplBuilder;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * De klasse waarmee de testpersoon 'Johnny Donny Jordaan' gemaakt kan worden.
 */
public final class TestPersoonJohnnyJordaan extends AbstractTestPersoonBouwer {

    public static final  int    ANITA_ID     = 101;
    public static final  int    PRISCILLA_ID = 102;
    private static final String STAM_JORDAAN = "Jordaan";
    private static final String ID           = "iD";
    private static final String TEST_PARTIJ  = "testPartij";
    private static final String ROXANNE      = "Roxanne";
    private static final String HAZES        = "Hazes";
    private static final String EEN          = "1";
    private final PersoonHisVolledigImpl persoon;

    private final ActieModel actiePartnerschap =
        bouwActie(4L, SoortActie.REGISTRATIE_AANVANG_HUWELIJK_GEREGISTREERD_PARTNERSCHAP,
            SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND, new DatumAttribuut(20100831).toDate());
    public static final int LEVERINGAUTORISATIE_ID_2 = 2;
    private int betrokkenhedenIdIndex;

    private ActieModel actie20130101;

    public static final Partij AFNEMERINDICATIE_PARTIJ = StatischeObjecttypeBuilder.bouwPartij(1, TEST_PARTIJ).getWaarde();

    /**
     * Private constructor.
     */
    private TestPersoonJohnnyJordaan() {
        final ActieModel actieGeboorte =
            bouwActie(1L, SoortActie.REGISTRATIE_GEBOORTE, SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND, new DatumAttribuut(19780101).toDate());
        final ActieModel actieBijhouding =
            bouwActie(2L, SoortActie.REGISTRATIE_BIJHOUDING, SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND, new DatumAttribuut(19780102).toDate());
        final ActieModel actieIdentificatienummers =
            bouwActie(3L, SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS, SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND,
                      new DatumAttribuut(19780103).toDate());
        final ActieModel actieOnderzoek =
            bouwActie(5L, SoortActie.REGISTRATIE_ONDERZOEK, SoortAdministratieveHandeling.AANVANG_ONDERZOEK, new DatumAttribuut(19600101).toDate());

        actie20130101 =
            new ActieModel(new SoortActieAttribuut(SoortActie.CONVERSIE_G_B_A), actieGeboorte.getAdministratieveHandeling(), null,
                new DatumEvtDeelsOnbekendAttribuut(20130101), null, DatumTijdAttribuut.datumTijd(2013, 1, 1, 0, 0, 0), null);

        final PersoonVoornaamHisVolledigImpl persoonVoornaamHisVolledigImpl =
            new PersoonVoornaamHisVolledigImplBuilder(new VolgnummerAttribuut(1))
                .nieuwStandaardRecord(actieGeboorte).naam("Sjonnie").eindeRecord().build();

        final PersoonVoornaamHisVolledigImpl persoonVoornaamHisVolledigImpl2 =
            new PersoonVoornaamHisVolledigImplBuilder(new VolgnummerAttribuut(2))
                .nieuwStandaardRecord(actieGeboorte).naam("Donny").eindeRecord().build();

        final PersoonGeslachtsnaamcomponentHisVolledigImplBuilder persoonGeslachtsnaamcomponentHisVolledigImplBuilder =
            new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(new VolgnummerAttribuut(1))
                .nieuwStandaardRecord(actieGeboorte).stam("Jordan").eindeRecord()
                .nieuwStandaardRecord(actiePartnerschap).stam(STAM_JORDAAN).eindeRecord();
        final PersoonGeslachtsnaamcomponentHisVolledigImpl geslachtsnaamcomponentHisVolledig = persoonGeslachtsnaamcomponentHisVolledigImplBuilder.build();

        final PersoonAdresHisVolledigImpl persoonAdresHisVolledigImpl = new PersoonAdresHisVolledigImplBuilder()
            .nieuwStandaardRecord(bouwActie(20130101, null, 20130101, actieGeboorte.getAdministratieveHandeling())).naamOpenbareRuimte("Plein")
            .huisnummer(1).postcode("1234KJ").woonplaatsnaam(StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM.getWaarde())
            .gemeente(StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde()).eindeRecord().build();

        final PersoonIndicatieOnderCurateleHisVolledigImpl persoonIndicatieOnderCurateleHisVolledigImpl =
            new PersoonIndicatieOnderCurateleHisVolledigImplBuilder().nieuwStandaardRecord(actie20130101).waarde(Ja.J).eindeRecord().build();

        final PersoonNationaliteitHisVolledigImpl persoonNationaliteitHisVolledigImpl =
            new PersoonNationaliteitHisVolledigImplBuilder(StatischeObjecttypeBuilder.NATIONALITEIT_NEDERLANDS.getWaarde())
                .nieuwStandaardRecord(bouwActie(20130101, null, 20130101, actieGeboorte.getAdministratieveHandeling())).eindeRecord().build();

        final PersoonReisdocumentHisVolledigImpl persoonReisdocumentHisVolledigImpl =
            new PersoonReisdocumentHisVolledigImplBuilder(StatischeObjecttypeBuilder.bouwSoortNederlandsReisdocument("NL").getWaarde())
                .nieuwStandaardRecord(actieGeboorte)
                .datumUitgifte(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.gisteren())).eindeRecord().build();

        final PersoonAfnemerindicatieHisVolledigImpl afnemerindicatieHisVolledig = new PersoonAfnemerindicatieHisVolledigImplBuilder(
            StatischeObjecttypeBuilder.bouwPartij(1, TEST_PARTIJ).getWaarde(), bouwLeveringsautorisatie("testAbonnement", 1)).nieuwStandaardRecord(maakDienst())
            .eindeRecord().build();


        final PersoonAfnemerindicatieHisVolledigImpl afnemerindicatieHisVolledigVerlopen =
            new PersoonAfnemerindicatieHisVolledigImplBuilder(AFNEMERINDICATIE_PARTIJ,
                bouwLeveringsautorisatie("testAbonnement2", LEVERINGAUTORISATIE_ID_2)).nieuwStandaardRecord(maakDienst()).datumEindeVolgen(
                DatumTijdAttribuut.datumTijd(2011, 12, 12).naarDatum()).eindeRecord().build();

        final PersoonAfnemerindicatieHisVolledigImpl afnemerindicatieHisVolledigNogNietVerlopen =
            new PersoonAfnemerindicatieHisVolledigImplBuilder(AFNEMERINDICATIE_PARTIJ,
                bouwLeveringsautorisatie("testAbonnement3", 3)).nieuwStandaardRecord(maakDienst()).datumEindeVolgen(
                DatumTijdAttribuut.datumTijd(2030, 1, 1).naarDatum()).eindeRecord().build();

        final PersoonAfnemerindicatieHisVolledigImpl afnemerindicatieHisVolledigNuVerlopen =
            new PersoonAfnemerindicatieHisVolledigImplBuilder(AFNEMERINDICATIE_PARTIJ,
                bouwLeveringsautorisatie("testAbonnement3", 4)).nieuwStandaardRecord(maakDienst()).datumEindeVolgen(
                DatumTijdAttribuut.nu().naarDatum()).eindeRecord().build();



        final DocumentModel document = new DocumentModel(new SoortDocumentAttribuut(
            new SoortDocument(new NaamEnumeratiewaardeAttribuut("Kladje"), new OmschrijvingEnumeratiewaardeAttribuut("Een servet vol met notities"),
                new VolgnummerAttribuut(1))));
        document.setStandaard(new DocumentStandaardGroepModel(new DocumentIdentificatieAttribuut("123-BBB-9876"), new AktenummerAttribuut("1234"),
            new DocumentOmschrijvingAttribuut("Omschrijving"),
            StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM));
        final ActieBronModel actieBron = new ActieBronModel(actieGeboorte, document, null, new OmschrijvingEnumeratiewaardeAttribuut("Omschr"));
        actieGeboorte.getBronnen().add(actieBron);
        ReflectionTestUtils.setField(actieBron, ID, 1L);
        ReflectionTestUtils.setField(document, ID, 1L);

        final Rechtsgrond rechtsgrond = new Rechtsgrond(new RechtsgrondCodeAttribuut((short) 1), SoortRechtsgrond.DUMMY,
                                                        new OmschrijvingEnumeratiewaardeAttribuut("Omschrijving rechtsgrond"), null,
                                                        actieGeboorte.getDatumAanvangGeldigheid(), actieGeboorte.getDatumEindeGeldigheid());
        final RechtsgrondAttribuut rechtsgrondAttr = new RechtsgrondAttribuut(rechtsgrond);
        final ActieBronModel actieBron2 = new ActieBronModel(actieGeboorte, null, rechtsgrondAttr, null);
        actieGeboorte.getBronnen().add(actieBron2);
        ReflectionTestUtils.setField(actieBron2, ID, 2L);
        ReflectionTestUtils.setField(rechtsgrond, ID, (short) 2);

        final PersoonHisVolledigImplBuilder persoonHisVolledigImplBuilder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);

        persoon =
            persoonHisVolledigImplBuilder
                .voegPersoonVoornaamToe(persoonVoornaamHisVolledigImpl)
                .voegPersoonVoornaamToe(persoonVoornaamHisVolledigImpl2)
                .voegPersoonGeslachtsnaamcomponentToe(geslachtsnaamcomponentHisVolledig)
                .nieuwGeboorteRecord(actieGeboorte).datumGeboorte(19780101)
                .gemeenteGeboorte(Short.valueOf("123"))
                .landGebiedGeboorte(Short.valueOf("255"))
                .eindeRecord()
                .nieuwBijhoudingRecord(actieBijhouding)
                .bijhoudingspartij(12345)
                .eindeRecord()
                .nieuwIdentificatienummersRecord(bouwActie(20120101, 20130101, 20120101, actieIdentificatienummers.getAdministratieveHandeling()))
                .burgerservicenummer(123456789)
                .eindeRecord()
                .nieuwIdentificatienummersRecord(actieIdentificatienummers)
                .burgerservicenummer(987654321).administratienummer(4253217569L)
                .eindeRecord()
                .voegPersoonAfnemerindicatieToe(afnemerindicatieHisVolledig)
                .voegPersoonAfnemerindicatieToe(afnemerindicatieHisVolledigVerlopen)
                .voegPersoonAfnemerindicatieToe(afnemerindicatieHisVolledigNogNietVerlopen)
                .voegPersoonAfnemerindicatieToe(afnemerindicatieHisVolledigNuVerlopen)
                    .nieuwAfgeleidAdministratiefRecord(actieGeboorte)
                .indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig(false).sorteervolgorde(Byte.valueOf(EEN))
                .eindeRecord()
                .nieuwAfgeleidAdministratiefRecord(actieBijhouding)
                .indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig(false).sorteervolgorde(Byte.valueOf(EEN))
                .eindeRecord()
                .nieuwAfgeleidAdministratiefRecord(actieIdentificatienummers)
                .indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig(false).sorteervolgorde(Byte.valueOf(EEN))
                .eindeRecord()
                .nieuwAfgeleidAdministratiefRecord(actiePartnerschap)
                .indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig(false).sorteervolgorde(Byte.valueOf(EEN))
                .eindeRecord()
                .nieuwAfgeleidAdministratiefRecord(actieOnderzoek)
                .indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig(false).sorteervolgorde(Byte.valueOf(EEN))
                .eindeRecord()
                .voegPersoonAdresToe(persoonAdresHisVolledigImpl)
                .voegPersoonIndicatieOnderCurateleToe(persoonIndicatieOnderCurateleHisVolledigImpl)
                .voegPersoonNationaliteitToe(persoonNationaliteitHisVolledigImpl)
                .voegPersoonReisdocumentToe(persoonReisdocumentHisVolledigImpl)
                .build();

        bouwOuderschap("Anita", persoon, ANITA_ID);
        bouwOuderschap("Priscilla", persoon, PRISCILLA_ID);
        bouwKind(persoon, "Pa");
        bouwPartnerschapVanJohnnyDonnyJordaan(persoon);
        // Bouw handmatig de verantwoordingsinformatie op
        persoon.getAdministratieveHandelingen().add(VerantwoordingTestUtil.converteer(actieGeboorte.getAdministratieveHandeling()));
        persoon.getAdministratieveHandelingen().add(VerantwoordingTestUtil.converteer(actieBijhouding.getAdministratieveHandeling()));
        persoon.getAdministratieveHandelingen().add(VerantwoordingTestUtil.converteer(actieIdentificatienummers.getAdministratieveHandeling()));
        persoon.getAdministratieveHandelingen().add(VerantwoordingTestUtil.converteer(actiePartnerschap.getAdministratieveHandeling()));
        persoon.getAdministratieveHandelingen().add(VerantwoordingTestUtil.converteer(actieOnderzoek.getAdministratieveHandeling()));

        //voeg onderzoek toe
        final Element element = TestElementBuilder.maker().metId(10).metNaam(ElementEnum.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER).metElementNaam(
            "Persoon.Identificatie.Bsn").metExpressie("$identificatienummers.burgerservicenummer").maak();
        final GegevenInOnderzoekHisVolledigImpl gegevenInOnderzoek = new GegevenInOnderzoekHisVolledigImplBuilder(element, new SleutelwaardeAttribuut(1L),
            new SleutelwaardeAttribuut(2L), false).build();

        final OnderzoekHisVolledigImpl onderzoek = new OnderzoekHisVolledigImplBuilder(true).nieuwStandaardRecord(actieOnderzoek).datumAanvang(20150909)
            .omschrijving("is niet pluis").status(StatusOnderzoek.IN_UITVOERING).verwachteAfhandeldatum(20160101).eindeRecord()
            .voegGegevenInOnderzoekToe(gegevenInOnderzoek).build();
        final HisOnderzoekModel actueleRecord = onderzoek.getOnderzoekHistorie().getActueleRecord();
        ReflectionTestUtils.setField(actueleRecord, ID, 4);
        ReflectionTestUtils.setField(gegevenInOnderzoek, ID, 1);
        ReflectionTestUtils.setField(onderzoek, ID, 2);

        final Set<PersoonOnderzoekHisVolledigImpl> persoonOnderzoeken = persoon.getOnderzoeken();
        final PersoonOnderzoekHisVolledigImpl persoonOnderzoek = new PersoonOnderzoekHisVolledigImplBuilder(persoon, onderzoek, true)
            .nieuwStandaardRecord(20150909)
            .rol(SoortPersoonOnderzoek.DIRECT).eindeRecord().build();

        ReflectionTestUtils.setField(persoonOnderzoek, ID, 3);
        persoonOnderzoeken.add(persoonOnderzoek);

        TestPersoonIdZetter.zetIds(persoon);

        ReflectionTestUtils.setField(persoon, ID, 1);
    }

    /**
     * Maak een testpersoon: de zogenaamde "Johnny Donny Jordaan".
     *
     * @return persoon his volledig impl
     */
    public static PersoonHisVolledigImpl maak() {
        return new TestPersoonJohnnyJordaan().persoon;
    }

    /**
     * Bouwt een ouder betrokkenheid met ouderlijk gezag en ouderschap via een familierechtelijke betrekking voor de testpersoon.
     *
     * @param voornaamKind voornaam kind
     * @param ouder        ouder
     */
    private void bouwOuderschap(final String voornaamKind, final PersoonHisVolledigImpl ouder, final Integer kindId) {
        final FamilierechtelijkeBetrekkingHisVolledigImpl relatie = new FamilierechtelijkeBetrekkingHisVolledigImplBuilder().build();
        final ActieModel verantwoordingInhoud = ouder.getPersoonAfgeleidAdministratiefHistorie().getActueleRecord().getVerantwoordingInhoud();
        final KindHisVolledigImpl kindBetrokkenheid = new KindHisVolledigImplBuilder(relatie, bouwKindVanJohnnyDonnyJordaan(voornaamKind, kindId))
            .metVerantwoording(verantwoordingInhoud).build();
        final OuderHisVolledigImpl ouderBetrokkenheid = new OuderHisVolledigImplBuilder(relatie, ouder)
            .nieuwOuderschapRecord(verantwoordingInhoud).indicatieOuder(Ja.J).eindeRecord().metVerantwoording(verantwoordingInhoud)
            .nieuwOuderlijkGezagRecord(verantwoordingInhoud).indicatieOuderHeeftGezag(true).eindeRecord().metVerantwoording(verantwoordingInhoud)
            .build();

        relatie.setBetrokkenheden(new HashSet<>(Arrays.asList(ouderBetrokkenheid, kindBetrokkenheid)));
        ReflectionTestUtils.setField(relatie, ID, 1);

        // Voorkomt nullpointers in de betrokkenheid comparator
        ReflectionTestUtils.setField(kindBetrokkenheid, ID, getUniekBetrokkenheidId());
        ReflectionTestUtils.setField(ouderBetrokkenheid, ID, getUniekBetrokkenheidId());
    }

    /**
     * Bouw de kind betrokkenheid via een familierechtelijke betrekking van de testpersoon.
     *
     * @param kind      kind persoon
     * @param naamVader naam van vader
     */
    private void bouwKind(final PersoonHisVolledigImpl kind, final String naamVader) {
        final FamilierechtelijkeBetrekkingHisVolledigImpl relatie = new FamilierechtelijkeBetrekkingHisVolledigImplBuilder().build();
        final ActieModel verantwoordingInhoud = kind.getPersoonAfgeleidAdministratiefHistorie().getActueleRecord().getVerantwoordingInhoud();
        final KindHisVolledigImpl kindBetrokkenheid = new KindHisVolledigImplBuilder(relatie, kind).metVerantwoording(verantwoordingInhoud).build();

        final OuderHisVolledigImpl ouderBetrokkenheid =
            new OuderHisVolledigImplBuilder(relatie, bouwOuderVanJohnnyDonnyJordaan(naamVader))
                .nieuwOuderschapRecord(verantwoordingInhoud).indicatieOuder(Ja.J).eindeRecord().metVerantwoording(verantwoordingInhoud)
                .nieuwOuderlijkGezagRecord(verantwoordingInhoud).indicatieOuderHeeftGezag(JaNeeAttribuut.JA).eindeRecord()
                .metVerantwoording(verantwoordingInhoud).build();

        ReflectionTestUtils.setField(relatie, ID, 2);
        relatie.setBetrokkenheden(new HashSet<>(Arrays.asList(kindBetrokkenheid, ouderBetrokkenheid)));

        ReflectionTestUtils.setField(kindBetrokkenheid, ID, 123);
        ReflectionTestUtils.setField(ouderBetrokkenheid, ID, 234);
    }

    /**
     * Bouw het kind van Johnny Donny Jordaan.
     *
     * @param voornaam voornaam van kind
     * @param kindId   id van kind
     * @return kind als persoon his volledig impl
     */
    private PersoonHisVolledigImpl bouwKindVanJohnnyDonnyJordaan(final String voornaam, final Integer kindId) {
        final PersoonVoornaamHisVolledigImplBuilder persoonVoornaamHisVolledigImplBuilder =
            new PersoonVoornaamHisVolledigImplBuilder(new VolgnummerAttribuut(1)).nieuwStandaardRecord(actie20130101).naam(voornaam).eindeRecord();

        final PersoonGeslachtsnaamcomponentHisVolledigImplBuilder persoonGeslachtsnaamcomponentHisVolledigImplBuilder =
            new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(new VolgnummerAttribuut(1)).nieuwStandaardRecord(actie20130101).stam(STAM_JORDAAN)
                .eindeRecord();
        final PersoonGeslachtsnaamcomponentHisVolledigImpl geslachtsnaamcomponentHisVolledig = persoonGeslachtsnaamcomponentHisVolledigImplBuilder.build();

        final PersoonHisVolledigImplBuilder persoonHisVolledigImplBuilder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);


        final PersoonHisVolledigImpl kind =
            persoonHisVolledigImplBuilder
                .voegPersoonVoornaamToe(persoonVoornaamHisVolledigImplBuilder.build())
                .voegPersoonGeslachtsnaamcomponentToe(geslachtsnaamcomponentHisVolledig)
                .nieuwBijhoudingRecord(actie20130101).bijhoudingspartij(12345).eindeRecord()
                .nieuwIdentificatienummersRecord(actie20130101).burgerservicenummer(100000).eindeRecord()
                .nieuwSamengesteldeNaamRecord(actie20130101).voornamen(voornaam).geslachtsnaamstam(STAM_JORDAAN).eindeRecord()
                .build();

        ReflectionTestUtils.setField(kind, ID, kindId);
        TestPersoonIdZetter.zetIds(kind);

        return kind;
    }

    /**
     * Bouw de partnerschap van Johnny Donny Jordaan.
     *
     * @param johnny johnny
     */
    private void bouwPartnerschapVanJohnnyDonnyJordaan(final PersoonHisVolledigImpl johnny) {
        final HuwelijkGeregistreerdPartnerschapHisVolledigImpl relatie =
            new GeregistreerdPartnerschapHisVolledigImplBuilder().nieuwStandaardRecord(actiePartnerschap)
                .datumAanvang(actiePartnerschap.getDatumAanvangGeldigheid()).eindeRecord().build();

        final ActieModel verantwoordingInhoud = johnny.getPersoonAfgeleidAdministratiefHistorie().getActueleRecord().getVerantwoordingInhoud();
        final PartnerHisVolledigImpl bruid =
            new PartnerHisVolledigImplBuilder(relatie, bouwPartnerVanJohnnyDonnyJordaan()).metVerantwoording(verantwoordingInhoud).build();
        final PartnerHisVolledigImpl bruidegom = new PartnerHisVolledigImplBuilder(relatie, johnny).metVerantwoording(verantwoordingInhoud).build();

        ReflectionTestUtils.setField(relatie, ID, 3);
        relatie.setBetrokkenheden(new HashSet<BetrokkenheidHisVolledigImpl>(Arrays.asList(bruid, bruidegom)));

        ReflectionTestUtils.setField(bruid, ID, getUniekBetrokkenheidId());
        ReflectionTestUtils.setField(bruidegom, ID, getUniekBetrokkenheidId());
    }

    /**
     * Bouw de ouder van johnny donny jordaan.
     *
     * @param voornaam voornaam
     * @return ouder als persoon his volledig impl
     */
    private PersoonHisVolledigImpl bouwOuderVanJohnnyDonnyJordaan(final String voornaam) {
        final PersoonVoornaamHisVolledigImpl voornaamHisVolledig = new PersoonVoornaamHisVolledigImplBuilder(new VolgnummerAttribuut(1))
            .nieuwStandaardRecord(actie20130101).naam(voornaam).eindeRecord().build();
        TestPersoonIdZetter.zetVoorkomenIntegerIds(voornaamHisVolledig.getPersoonVoornaamHistorie());

        final PersoonGeslachtsnaamcomponentHisVolledigImpl geslachtsnaamcomponentHisVolledig = new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(
            new VolgnummerAttribuut(1)).nieuwStandaardRecord(actie20130101).stam(STAM_JORDAAN).eindeRecord().build();

        final PersoonHisVolledigImpl ouder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .voegPersoonVoornaamToe(voornaamHisVolledig)
            .voegPersoonGeslachtsnaamcomponentToe(geslachtsnaamcomponentHisVolledig)
            .nieuwBijhoudingRecord(actie20130101).bijhoudingspartij(12345).eindeRecord()
            .nieuwIdentificatienummersRecord(actie20130101).burgerservicenummer(100000).eindeRecord()
            .nieuwSamengesteldeNaamRecord(actie20130101).voornamen(voornaam)
            .geslachtsnaamstam(STAM_JORDAAN).eindeRecord().build();

        ReflectionTestUtils.setField(ouder.getPersoonSamengesteldeNaamHistorie().getActueleRecord().getVerantwoordingInhoud(), ID, 153L);
        ReflectionTestUtils.setField(ouder, ID, 3);
        TestPersoonIdZetter.zetIds(ouder);

        return ouder;
    }

    /**
     * Bouw de partner van johnny donny jordaan.
     *
     * @return partner als persoon his volledig impl
     */
    private PersoonHisVolledigImpl bouwPartnerVanJohnnyDonnyJordaan() {
        final PersoonVoornaamHisVolledigImpl voornaamHisVolledig = new PersoonVoornaamHisVolledigImplBuilder(new VolgnummerAttribuut(1))
            .nieuwStandaardRecord(actie20130101).naam(ROXANNE).eindeRecord().build();
        TestPersoonIdZetter.zetVoorkomenIntegerIds(voornaamHisVolledig.getPersoonVoornaamHistorie());

        final PersoonGeslachtsnaamcomponentHisVolledigImpl geslachtsnaamcomponent = new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(
            new VolgnummerAttribuut(1)).nieuwStandaardRecord(actie20130101).stam(HAZES).eindeRecord().build();

        final PersoonHisVolledigImpl partner = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .voegPersoonVoornaamToe(voornaamHisVolledig)
            .voegPersoonGeslachtsnaamcomponentToe(geslachtsnaamcomponent)
            .nieuwBijhoudingRecord(actie20130101).bijhoudingspartij(12345).eindeRecord()
            .nieuwIdentificatienummersRecord(actie20130101).burgerservicenummer(100080).eindeRecord()
            .nieuwSamengesteldeNaamRecord(actie20130101).voornamen(ROXANNE).geslachtsnaamstam(HAZES).eindeRecord()
            .build();

        ReflectionTestUtils.setField(partner, ID, 666);
        TestPersoonIdZetter.zetIds(partner);

        return partner;
    }

    private int getUniekBetrokkenheidId() {
        return betrokkenhedenIdIndex++;
    }


}
