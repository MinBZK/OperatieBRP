/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.gegevensfilter;

import java.util.List;
import java.util.Set;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AktenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentIdentificatieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisletterAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdresAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.basis.MaterieelVerantwoordbaar;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.AdministratieveHandelingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.ActieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.AdministratieveHandelingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.DocumentHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaat.AdministratieveHandelingDeltaPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.ActieBronHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.ActieBronModel;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.DocumentModel;
import nl.bzk.brp.model.operationeel.kern.DocumentStandaardGroepModel;
import nl.bzk.brp.model.operationeel.kern.HisDocumentModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.VerantwoordingTestUtil;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonAntwoordPersoon;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(MockitoJUnitRunner.class)
public class MutatieLeveringVerantwoordingsinformatieFilterImplTest {

    private static final String A  = "A";
    private static final String ID = "iD";

    @InjectMocks
    private final MutatieLeveringVerantwoordingsinformatieFilter filter = new MutatieLeveringVerantwoordingsinformatieFilterImpl();

    @Test
    public final void testFilter() {
        //Maak een basis persoon.
        final PersoonHisVolledigImpl johnny = TestPersoonAntwoordPersoon.maakAntwoordPersoon(false);

        // maak een administatieve handeling voor onze mutatie levering
        final AdministratieveHandelingModel administratieveHandeling = new AdministratieveHandelingModel(
            null, null, null, DatumTijdAttribuut.nu()
        );
        ReflectionTestUtils.setField(administratieveHandeling, ID, 123L);

        final ActieModel verhuisActie =
            VerantwoordingTestUtil.maakActie(administratieveHandeling, SoortActie.REGISTRATIE_ADRES,
                null, null, null, DatumTijdAttribuut.nu(), null, 1234L);
        verhuisActie.getSoort().setMagGeleverdWorden(true);
        verhuisActie.setMagGeleverdWorden(true);
        administratieveHandeling.getActies().add(verhuisActie);

        final ActieModel correctieGeboorteActie =
            VerantwoordingTestUtil.maakActie(administratieveHandeling, SoortActie.REGISTRATIE_GEBOORTE,
                null, null, null, DatumTijdAttribuut.nu(), null, 5678L);
        correctieGeboorteActie.getSoort().setMagGeleverdWorden(true);
        correctieGeboorteActie.setMagGeleverdWorden(true);
        administratieveHandeling.getActies().add(correctieGeboorteActie);

        // Verhuis de persoon
        final PersoonAdresHisVolledigImpl adres = johnny.getAdressen().iterator().next();
        final PersoonAdresStandaardGroepBericht persoonAdresStandaardGroep = new PersoonAdresStandaardGroepBericht();
        persoonAdresStandaardGroep.setHuisletter(new HuisletterAttribuut(A));
        persoonAdresStandaardGroep.setSoort(new FunctieAdresAttribuut(FunctieAdres.WOONADRES));
        persoonAdresStandaardGroep.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20140101));
        adres.getPersoonAdresHistorie().voegToe(
            new HisPersoonAdresModel(adres, persoonAdresStandaardGroep, persoonAdresStandaardGroep, verhuisActie));

        //Om ook een formele groep te raken passen we ook de geboorte van johnny aan:
        final PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();
        geboorte.setDatumGeboorte(new DatumEvtDeelsOnbekendAttribuut(19870101));
        johnny.getPersoonGeboorteHistorie().voegToe(
            new HisPersoonGeboorteModel(johnny, geboorte, correctieGeboorteActie)
        );

        // Hier en daar wat attribuutjes op maggeleverd worden, oftewel verkapt autorisaties uitdelen:
        for (final HisPersoonAdresModel hisPersoonAdresModel : adres.getPersoonAdresHistorie()) {
            hisPersoonAdresModel.getSoort().setMagGeleverdWorden(true);
        }

        for (final HisPersoonGeboorteModel hisPersoonGeboorteModel : johnny.getPersoonGeboorteHistorie()) {
            hisPersoonGeboorteModel.getDatumGeboorte().setMagGeleverdWorden(true);
        }

        //Voeg de administratieve handeling aan de verantwoordijg van johnny.
        johnny.getAdministratieveHandelingen().add(VerantwoordingTestUtil.converteer(administratieveHandeling));

        // Maak een view voor de mutatie levering van de verhuizing
        final PersoonHisVolledigView mutatieLeveringView =
            new PersoonHisVolledigView(johnny,
                new AdministratieveHandelingDeltaPredikaat(administratieveHandeling.getID()));

        //Doe de filtering
        filter.filter(mutatieLeveringView, administratieveHandeling.getID(), bouwLeveringinformatie(false));

        //Er is slechts één handeling die geleverd mag worden omdat het een mutatielevering is:
        for (final AdministratieveHandelingHisVolledigImpl administratieveHandelingHisVolledig : johnny.getAdministratieveHandelingen()) {
            if (administratieveHandelingHisVolledig.isMagGeleverdWorden()) {
                Assert.assertEquals(administratieveHandeling.getID(), administratieveHandelingHisVolledig.getID());
            }
        }

        // De handeling die we mogen leveren bevat de twee eerder aangemaakte acties, dus van alle voorkomens in de PersoonHisVolledigView is de
        // magGeleverdWorden vlag van de actie veldjes TRUE indien het om de eerder aangemaakte acties gaat. Oftewel de acties die aan de handeling
        // hangen.
        final List<HistorieEntiteit> alleHistorieRecords =
            mutatieLeveringView.getTotaleLijstVanHisElementenOpPersoonsLijst();
        //We gaan het aantal voorkomens dat we raken bijhouden met een counter.
        int hitCounter = 0;
        for (final HistorieEntiteit voorkomen : alleHistorieRecords) {
            if (voorkomen instanceof MaterieelVerantwoordbaar) {
                final MaterieelVerantwoordbaar<ActieModel> materieelVerantwoordbaar = (MaterieelVerantwoordbaar<ActieModel>) voorkomen;
                final ActieModel verantwoordingInhoud = materieelVerantwoordbaar.getVerantwoordingInhoud();
                final ActieModel verantwoordingVerval = materieelVerantwoordbaar.getVerantwoordingVerval();
                final ActieModel verantwoordingAanpassingGeldigheid =
                    materieelVerantwoordbaar.getVerantwoordingAanpassingGeldigheid();

                // Als de actieInhoud van dit voorkomen bij onze administratieve handeling hoort, dan moet het veld getoond worden:
                if (verantwoordingInhoud.getAdministratieveHandeling().getID()
                    .equals(administratieveHandeling.getID()))
                {
                    Assert.assertTrue(verantwoordingInhoud.isMagGeleverdWorden());
                    hitCounter++;
                }
                if (verantwoordingVerval != null
                    && verantwoordingVerval.getAdministratieveHandeling().getID()
                    .equals(administratieveHandeling.getID()))
                {
                    Assert.assertTrue(verantwoordingVerval.isMagGeleverdWorden());

                    hitCounter++;
                }
                if (verantwoordingAanpassingGeldigheid != null
                    && verantwoordingAanpassingGeldigheid.getAdministratieveHandeling().getID()
                    .equals(administratieveHandeling.getID()))
                {
                    Assert.assertTrue(verantwoordingAanpassingGeldigheid.isMagGeleverdWorden());
                    hitCounter++;
                }
            } else if (voorkomen instanceof FormeelVerantwoordbaar) {
                final FormeelVerantwoordbaar<ActieModel> formeelVerantwoordbaar = (FormeelVerantwoordbaar<ActieModel>) voorkomen;
                final ActieModel verantwoordingInhoud = formeelVerantwoordbaar.getVerantwoordingInhoud();
                final ActieModel verantwoordingVerval = formeelVerantwoordbaar.getVerantwoordingVerval();

                // Als de actieInhoud van dit voorkomen bij onze administratieve handeling hoort, dan moet het veld getoond worden:
                if (verantwoordingInhoud.getAdministratieveHandeling().getID()
                    .equals(administratieveHandeling.getID()))
                {
                    Assert.assertTrue(verantwoordingInhoud.isMagGeleverdWorden());
                    hitCounter++;
                }
                if (verantwoordingVerval != null
                    && verantwoordingVerval.getAdministratieveHandeling().getID()
                    .equals(administratieveHandeling.getID()))
                {
                    Assert.assertTrue(verantwoordingVerval.isMagGeleverdWorden());
                    hitCounter++;
                }
            }
        }

        // Onze administratieve handeling heeft één formele groep en één materiele groep bijgehouden. Dit betekent dat de handeling in totaal 5
        // voorkomens raakt.
        Assert.assertEquals(5, hitCounter);
    }

    /**
     * N.a.v. een bevinding met gemigreerde testdata (zie TEAMBRP-3654) is geconstateerd dat het voor kan komen dat in het verantwoordingsdeel van het
     * leverbericht bronnen (d.w.z. Document of Rechtsgrond) voorkomen waarnaar vanuit geen enkele ActieBron wordt verwezen. De betreffende bron
     * verantwoordt dan in feite geen enkele Actie in het bericht. Dat is niet wenselijk. Daarom is nieuwe regel R2015 opgesteld, om dergelijke "wees"
     * geworden Documenten en Rechtsgronden uit het verantwoordingsdeel te filteren.
     */
    @Test
    public final void testFilterNietGelinkteBronnen() {
        //Maak een basis persoon.
        final PersoonHisVolledigImpl johnny = TestPersoonAntwoordPersoon.maakAntwoordPersoon(false);
        // maak een administatieve handeling voor onze mutatie levering
        final AdministratieveHandelingModel administratieveHandeling = new AdministratieveHandelingModel(
            null, null, null, DatumTijdAttribuut.nu()
        );
        ReflectionTestUtils.setField(administratieveHandeling, ID, 123L);
        //actie zonder bron op groep
        final ActieModel verhuisActie =
            VerantwoordingTestUtil.maakActie(administratieveHandeling, SoortActie.REGISTRATIE_ADRES,
                null, null, null, DatumTijdAttribuut.nu(), null, 1234L);
        verhuisActie.getSoort().setMagGeleverdWorden(true);
        verhuisActie.setMagGeleverdWorden(true);
        administratieveHandeling.getActies().add(verhuisActie);
        final Long documentId = 444L;
        final DocumentModel document = new DocumentModel(new SoortDocumentAttribuut(
            new SoortDocument(new NaamEnumeratiewaardeAttribuut("TEST"), new OmschrijvingEnumeratiewaardeAttribuut("Een servet vol met notities"),
                new VolgnummerAttribuut(2))));
        document.setStandaard(new DocumentStandaardGroepModel(new DocumentIdentificatieAttribuut("123-BBB-9877"), new AktenummerAttribuut("1234"),
            new DocumentOmschrijvingAttribuut("Omschrijving"),
            StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM));
        ReflectionTestUtils.setField(document, ID, documentId);
        final ActieBronModel actieBron = new ActieBronModel(verhuisActie, document, null, new OmschrijvingEnumeratiewaardeAttribuut("Omschr"));
        verhuisActie.getBronnen().add(actieBron);
        //zet mag geleverd worden
        document.getSoort().setMagGeleverdWorden(true);
        //maak actie voor admin handeling zonder bron
        final ActieModel verhuisActieZonderBron =
            VerantwoordingTestUtil.maakActie(administratieveHandeling, SoortActie.REGISTRATIE_ADRES,
                null, null, null, DatumTijdAttribuut.nu(), null, 1235L);
        verhuisActieZonderBron.getSoort().setMagGeleverdWorden(true);
        // Verhuis de persoon
        final PersoonAdresHisVolledigImpl adres = johnny.getAdressen().iterator().next();
        final PersoonAdresStandaardGroepBericht persoonAdresStandaardGroep = new PersoonAdresStandaardGroepBericht();
        persoonAdresStandaardGroep.setHuisletter(new HuisletterAttribuut(A));
        persoonAdresStandaardGroep.setSoort(new FunctieAdresAttribuut(FunctieAdres.WOONADRES));
        persoonAdresStandaardGroep.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20140101));
        adres.getPersoonAdresHistorie().voegToe(
            new HisPersoonAdresModel(adres, persoonAdresStandaardGroep, persoonAdresStandaardGroep, verhuisActieZonderBron));

        //Voeg de administratieve handeling aan de verantwoordijg van johnny.
        johnny.getAdministratieveHandelingen().add(VerantwoordingTestUtil.converteer(administratieveHandeling));

        // Maak een view voor de mutatie levering van de verhuizing
        final PersoonHisVolledigView mutatieLeveringView =
            new PersoonHisVolledigView(johnny,
                new AdministratieveHandelingDeltaPredikaat(administratieveHandeling.getID()));

        //Doe de filtering
        filter.filter(mutatieLeveringView, administratieveHandeling.getID(), bouwLeveringinformatie(false));
        //controle
        final DocumentHisVolledig documentHisVolledig = geefDocumentView(documentId, mutatieLeveringView);
        final HisDocumentModel hisDocumentModel = documentHisVolledig.getDocumentHistorie().getActueleRecord();

        Assert.assertNotNull(hisDocumentModel);
        Assert.assertFalse(hisDocumentModel.getAktenummer().isMagGeleverdWorden());
    }

    @Test
    public final void testFilterVervallenDocumentenAfnemer() {
        vervallenDocumentenTest(false);
    }

    @Test
    public final void testFilterVervallenDocumentenBijhouder() {
        vervallenDocumentenTest(true);
    }

    private void vervallenDocumentenTest(final boolean bijhouder) {


        //Mockito.when(leveringAutorisatieService.heeftBijHouderRol(Mockito.any(Leveringinformatie.class))).thenReturn(bijhouder);
        //Maak een basis persoon.
        final PersoonHisVolledigImpl johnny = TestPersoonAntwoordPersoon.maakAntwoordPersoon(false);
        // maak een administatieve handeling voor onze mutatie levering
        final AdministratieveHandelingModel administratieveHandeling = new AdministratieveHandelingModel(
            null, null, null, DatumTijdAttribuut.nu()
        );
        ReflectionTestUtils.setField(administratieveHandeling, ID, 123L);
        final ActieModel verhuisActie =
            VerantwoordingTestUtil.maakActie(administratieveHandeling, SoortActie.REGISTRATIE_ADRES,
                null, null, null, DatumTijdAttribuut.nu(), null, 1234L);
        verhuisActie.getSoort().setMagGeleverdWorden(true);
        verhuisActie.setMagGeleverdWorden(true);
        administratieveHandeling.getActies().add(verhuisActie);
        final Long documentId = 444L;
        final DocumentModel document = new DocumentModel(new SoortDocumentAttribuut(
            new SoortDocument(new NaamEnumeratiewaardeAttribuut("TEST"), new OmschrijvingEnumeratiewaardeAttribuut("Een servet vol met notities"),
                new VolgnummerAttribuut(2))));
        document.setStandaard(new DocumentStandaardGroepModel(new DocumentIdentificatieAttribuut("123-BBB-9877"), new AktenummerAttribuut("1234"),
            new DocumentOmschrijvingAttribuut("Omschrijving"),
            StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM));
        ReflectionTestUtils.setField(document, ID, documentId);
        //voor test dat vlag omgezet wordt
        document.getSoort().setMagGeleverdWorden(false);
        final ActieBronModel actieBron = new ActieBronModel(verhuisActie, document, null, new OmschrijvingEnumeratiewaardeAttribuut("Omschr"));
        verhuisActie.getBronnen().add(actieBron);
        // Verhuis de persoon
        final PersoonAdresHisVolledigImpl adres = johnny.getAdressen().iterator().next();
        final PersoonAdresStandaardGroepBericht persoonAdresStandaardGroep = new PersoonAdresStandaardGroepBericht();
        persoonAdresStandaardGroep.setHuisletter(new HuisletterAttribuut(A));
        persoonAdresStandaardGroep.setSoort(new FunctieAdresAttribuut(FunctieAdres.WOONADRES));
        persoonAdresStandaardGroep.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20140101));
        adres.getPersoonAdresHistorie().voegToe(
            new HisPersoonAdresModel(adres, persoonAdresStandaardGroep, persoonAdresStandaardGroep, verhuisActie));
        //Voeg de administratieve handeling aan de verantwoordijg van johnny.
        johnny.getAdministratieveHandelingen().add(VerantwoordingTestUtil.converteer(administratieveHandeling));
        // Maak een view voor de mutatie levering van de verhuizing
        final PersoonHisVolledigView mutatieLeveringView =
            new PersoonHisVolledigView(johnny,
                new AdministratieveHandelingDeltaPredikaat(administratieveHandeling.getID()));
        //Doe de filtering
        filter.filter(mutatieLeveringView, administratieveHandeling.getID(), bouwLeveringinformatie(bijhouder));
        //controle
        final DocumentHisVolledig hisVolledigDocument = geefDocumentView(documentId, mutatieLeveringView);
        for (final HisDocumentModel documentHistorie : hisVolledigDocument.getDocumentHistorie()) {
            Assert.assertTrue(documentHistorie.getAktenummer().isMagGeleverdWorden());
        }
        //laat document vervallen
        final DocumentHisVolledig documentHisVolledigVoorVervallen = geefDocumentView(documentId, mutatieLeveringView);
        final ActieModel verantwoordingVerval = VerantwoordingTestUtil.maakActie(administratieveHandeling, SoortActie
                .VERVAL_HUWELIJK_GEREGISTREERD_PARTNERSCHAP,
            null, null, new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.gisteren()), DatumTijdAttribuut.nu(), null, 1234L);
        documentHisVolledigVoorVervallen.getDocumentHistorie().verval(verantwoordingVerval, DatumTijdAttribuut.terug24Uur());
        //zet terug in ininitele toestand, blijft false als geen bijhouder
        document.getSoort().setMagGeleverdWorden(false);
        //Doe de filtering
        filter.filter(mutatieLeveringView, administratieveHandeling.getID(), bouwLeveringinformatie(bijhouder));
        final DocumentHisVolledig hisVolledigDocument2 = geefDocumentView(documentId, mutatieLeveringView);
        for (final HisDocumentModel documentHistorie : hisVolledigDocument2.getDocumentHistorie()) {
            Assert.assertEquals(bijhouder, documentHistorie.getAktenummer().isMagGeleverdWorden());
        }
    }


    @Test
    public void testActiesIdentificerendeGroepenWordenNietGeleverdBijMutatieLevering() {
        // De actie veldjes van identificerende groepen moeten niet getoond worden in een mutatielevering van een handeling die niks met Identificerende
        // groepen doet.

        // We bouwen eerst weer de uitgangssituatie:
        // Maak een basis persoon.
        final PersoonHisVolledigImpl johnny = TestPersoonAntwoordPersoon.maakAntwoordPersoon(false);

        // maak een administatieve handeling voor onze mutatie levering
        final AdministratieveHandelingModel administratieveHandeling = new AdministratieveHandelingModel(
            null, null, null, DatumTijdAttribuut.nu()
        );
        ReflectionTestUtils.setField(administratieveHandeling, ID, 123L);

        final ActieModel verhuisActie =
            VerantwoordingTestUtil.maakActie(administratieveHandeling, SoortActie.REGISTRATIE_ADRES, null, null, null, DatumTijdAttribuut.nu(), null,
                1234L);
        verhuisActie.getSoort().setMagGeleverdWorden(true);
        verhuisActie.setMagGeleverdWorden(true);
        administratieveHandeling.getActies().add(verhuisActie);

        final ActieModel correctieGeboorteActie =
            VerantwoordingTestUtil.maakActie(administratieveHandeling, SoortActie.REGISTRATIE_GEBOORTE, null, null, null, DatumTijdAttribuut.nu(), null,
                                             5678L);
        correctieGeboorteActie.getSoort().setMagGeleverdWorden(true);
        correctieGeboorteActie.setMagGeleverdWorden(true);
        administratieveHandeling.getActies().add(correctieGeboorteActie);

        // Verhuis de persoon
        final PersoonAdresHisVolledigImpl adres = johnny.getAdressen().iterator().next();
        final PersoonAdresStandaardGroepBericht persoonAdresStandaardGroep = new PersoonAdresStandaardGroepBericht();
        persoonAdresStandaardGroep.setHuisletter(new HuisletterAttribuut(A));
        persoonAdresStandaardGroep.setSoort(new FunctieAdresAttribuut(FunctieAdres.WOONADRES));
        persoonAdresStandaardGroep.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20140101));
        adres.getPersoonAdresHistorie().voegToe(new HisPersoonAdresModel(adres, persoonAdresStandaardGroep, persoonAdresStandaardGroep, verhuisActie));

        //Om ook een formele groep te raken passen we ook de geboorte van johnny aan:
        final PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();
        geboorte.setDatumGeboorte(new DatumEvtDeelsOnbekendAttribuut(19870101));
        johnny.getPersoonGeboorteHistorie().voegToe(new HisPersoonGeboorteModel(johnny, geboorte, correctieGeboorteActie));

        // Hier en daar wat attribuutjes op maggeleverd worden, oftewel verkapt autorisaties uitdelen:
        for (final HisPersoonAdresModel hisPersoonAdresModel : adres.getPersoonAdresHistorie()) {
            hisPersoonAdresModel.getSoort().setMagGeleverdWorden(true);
        }

        for (final HisPersoonGeboorteModel hisPersoonGeboorteModel : johnny.getPersoonGeboorteHistorie()) {
            hisPersoonGeboorteModel.getDatumGeboorte().setMagGeleverdWorden(true);
        }

        //Voeg de administratieve handeling aan de verantwoordijg van johnny.
        johnny.getAdministratieveHandelingen().add(VerantwoordingTestUtil.converteer(administratieveHandeling));

        // Maak een view voor de mutatie levering van de verhuizing
        final PersoonHisVolledigView mutatieLeveringView =
            new PersoonHisVolledigView(johnny, new AdministratieveHandelingDeltaPredikaat(administratieveHandeling.getID()));

        // We zetten het magGeleverdWorden vlaggetje van een specifieke actie van een identificerende groep op true,
        // we verwachten zo dat deze op false staat.
        // De handeling heeft betrekking op adres en geboorte groepen, dus we pakken de identificatienummers groep.
        final ActieModel actieInhoud = johnny.getPersoonIdentificatienummersHistorie().getActueleRecord().getVerantwoordingInhoud();
        actieInhoud.setMagGeleverdWorden(true);
        // Er moet ook minimaal 1 attribuutje op magGeleverdWorden staan
        actieInhoud.getSoort().setMagGeleverdWorden(true);

        //Doe de filtering
        filter.filter(mutatieLeveringView, administratieveHandeling.getID(), bouwLeveringinformatie(false));

        Assert.assertFalse("Het vlaggetje op de actie is niet ge-reset!", actieInhoud.isMagGeleverdWorden());
    }

    private Leveringinformatie bouwLeveringinformatie(final boolean bijhoudingsorgaan) {

        Rol rol = Rol.AFNEMER;
        if (bijhoudingsorgaan) {
            rol = Rol.BIJHOUDINGSORGAAN_COLLEGE;
        }

        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker()
            .metGeautoriseerde(new PartijRol(null, rol, null, null)).maak();
        return new Leveringinformatie(tla, null);
    }

    private DocumentHisVolledig geefDocumentView(final Long documentId, final PersoonHisVolledigView mutatieLeveringView) {
        final List<AdministratieveHandelingHisVolledig> administratieveHandelingen = mutatieLeveringView.getAdministratieveHandelingen();
        for (final AdministratieveHandelingHisVolledig administratieveHandelingHisVolledig : administratieveHandelingen) {
            for (final ActieHisVolledig actieHisVolledig : administratieveHandelingHisVolledig.getActies()) {
                final Set<ActieBronHisVolledigView> bronnen = (Set<ActieBronHisVolledigView>) actieHisVolledig.getBronnen();
                for (final ActieBronHisVolledigView actieBronHisVolledigView : bronnen) {
                    final DocumentHisVolledig documentVoorControle = actieBronHisVolledigView.getDocument();
                    if (documentVoorControle != null && documentVoorControle.getID().equals(documentId)) {
                        return documentVoorControle;
                    }

                }
            }
        }
        return null;
    }
}
