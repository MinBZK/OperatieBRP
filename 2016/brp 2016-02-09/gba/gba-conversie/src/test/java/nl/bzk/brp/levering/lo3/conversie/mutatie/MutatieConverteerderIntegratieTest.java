/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.List;
import nl.bzk.brp.levering.lo3.mapper.OnderzoekMapper;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AktenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeLangAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NadereAanduidingVerval;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NadereAanduidingVervalAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonOnderzoek;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusOnderzoek;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.OnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieBronModel;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.DocumentModel;
import nl.bzk.brp.model.operationeel.kern.DocumentStandaardGroepModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.util.hisvolledig.kern.GegevenInOnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.OnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonOnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonVerstrekkingsbeperkingHisVolledigImplBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import support.PersoonHisVolledigUtil;

public class MutatieConverteerderIntegratieTest extends AbstractMutatieConverteerderIntegratieTest {
    // private static final Logger LOGGER = LoggerFactory.getLogger();

    @Test
    public void testGeenWijzigingen() {
        final ActieModel actie =
            PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        final PersoonVerstrekkingsbeperkingHisVolledigImplBuilder beperkingBuilder =
            new PersoonVerstrekkingsbeperkingHisVolledigImplBuilder(partij, null, null);
        builder.voegPersoonVerstrekkingsbeperkingToe(beperkingBuilder.build());

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testGroep81Akte() {
        final ActieModel actie =
            PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        final Partij documentPartij = new Partij(null, new SoortPartijAttribuut(SoortPartij.GEMEENTE), new PartijCodeAttribuut(43301), null, null, null,
            null, null, null);
        final DocumentStandaardGroepModel documentStandaard =
            new DocumentStandaardGroepModel(null, new AktenummerAttribuut("TBX0001"), null, new PartijAttribuut(documentPartij));
        final DocumentModel document = new DocumentModel(new SoortDocumentAttribuut(new SoortDocument(null, null, null)));
        document.setStandaard(documentStandaard);
        final ActieBronModel bron = new ActieBronModel(actie, document, null, null);
        actie.getBronnen().add(bron);

        builder.nieuwIdentificatienummersRecord(actie).administratienummer(1234567890L).burgerservicenummer(123123123).eindeRecord();
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_01,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "123123123",
            Lo3ElementEnum.ELEMENT_8110,
            "0433",
            Lo3ElementEnum.ELEMENT_8120,
            "TBX0001",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_51,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "123456789",
            Lo3ElementEnum.ELEMENT_8110,
            "",
            Lo3ElementEnum.ELEMENT_8120,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep82Document() {
        final ActieModel actie =
            PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        final Partij documentPartij = new Partij(null, new SoortPartijAttribuut(SoortPartij.GEMEENTE), new PartijCodeAttribuut(43301), null, null, null,
            null, null, null);
        final DocumentStandaardGroepModel documentStandaard =
            new DocumentStandaardGroepModel(null, null, new DocumentOmschrijvingAttribuut("Documentomschrijving"), new PartijAttribuut(documentPartij));
        final DocumentModel document = new DocumentModel(new SoortDocumentAttribuut(new SoortDocument(null, null, null)));
        document.setStandaard(documentStandaard);
        final ActieBronModel bron = new ActieBronModel(actie, document, null, null);
        actie.getBronnen().add(bron);

        builder.nieuwIdentificatienummersRecord(actie).administratienummer(1234567890L).burgerservicenummer(123123123).eindeRecord();
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_01,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "123123123",
            Lo3ElementEnum.ELEMENT_8210,
            "0433",
            Lo3ElementEnum.ELEMENT_8220,
            "19400101",
            Lo3ElementEnum.ELEMENT_8230,
            "Documentomschrijving",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_51,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "123456789",
            Lo3ElementEnum.ELEMENT_8210,
            "",
            Lo3ElementEnum.ELEMENT_8220,
            "",
            Lo3ElementEnum.ELEMENT_8230,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep83Onderzoek() {
        final ActieModel actie =
            PersoonHisVolledigUtil.maakActie(2L, SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL, SoortActie.CONVERSIE_G_B_A, 19400101, partij);

        // Onderzoek
        final OnderzoekHisVolledigImplBuilder onderzoekBuilder = new OnderzoekHisVolledigImplBuilder();
        onderzoekBuilder.nieuwStandaardRecord(actie)
            .datumAanvang(19340505)
            .omschrijving(OnderzoekMapper.LO3_ONDERZOEK_OMSCHRIJVING_PREFIX + "010120")
            .status(StatusOnderzoek.IN_UITVOERING)
            .eindeRecord(2001);

        final AdministratieveHandelingBericht administratieveHandelingBericht =
            new AdministratieveHandelingBericht(actie.getAdministratieveHandeling().getSoort()) {
            };

        onderzoekBuilder.nieuwAfgeleidAdministratiefRecord(actie).administratieveHandeling(administratieveHandelingBericht).eindeRecord(2002);
        final OnderzoekHisVolledigImpl onderzoek = onderzoekBuilder.build();

        // PersoonOnderzoek
        final PersoonOnderzoekHisVolledigImplBuilder persoonOnderzoekBuilder = new PersoonOnderzoekHisVolledigImplBuilder(null, onderzoek);
        persoonOnderzoekBuilder.nieuwStandaardRecord(actie).rol(SoortPersoonOnderzoek.DIRECT).eindeRecord();
        builder.voegPersoonOnderzoekToe(persoonOnderzoekBuilder.build());

        builder.nieuwIdentificatienummersRecord(actie).administratienummer(1234567890L).burgerservicenummer(123123123).eindeRecord(333455);

        // Gegeven in onderzoek
        final Element element =
            new Element(
                new NaamEnumeratiewaardeLangAttribuut("PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER"),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null)
            {
            };
        ReflectionTestUtils.setField(element, "iD", ElementEnum.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId());

        final SleutelwaardeAttribuut objectSleutel = new SleutelwaardeAttribuut(4587348L);
        final SleutelwaardeAttribuut voorkomenSleutel = new SleutelwaardeAttribuut(333455L);

        final GegevenInOnderzoekHisVolledigImplBuilder gegevensBuilder =
            new GegevenInOnderzoekHisVolledigImplBuilder(element, objectSleutel, voorkomenSleutel);
        onderzoek.getGegevensInOnderzoek().add(gegevensBuilder.build());

        // Door het opnemen van een nieuw identificatie record, vervalt het oorspronkelijke, hier wordt echter geen ID
        // voor gegenereerd, hierdoor krijg je een NPE.
        final PersoonHisVolledig persoon = builder.build();
        for (final HisPersoonIdentificatienummersModel id : persoon.getPersoonIdentificatienummersHistorie()) {
            if (id.getID() == null) {
                ReflectionTestUtils.setField(id, "iD", 5006);
            }
        }
        PersoonHisVolledigUtil.maakVerantwoording(persoon, actieGeboorte, actie);

        debugHistorieSet(persoon.getPersoonIdentificatienummersHistorie());

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(persoon, actie);
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_01,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "123123123",
            Lo3ElementEnum.ELEMENT_8310,
            "010120",
            Lo3ElementEnum.ELEMENT_8320,
            "19340505",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_51,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "123456789",
            Lo3ElementEnum.ELEMENT_8310,
            "",
            Lo3ElementEnum.ELEMENT_8320,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep84Onjuist() {
        final ActieModel actieCorrectie =
            PersoonHisVolledigUtil.maakActie(
                2L,
                SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL,
                SoortActie.CONVERSIE_G_B_A,
                19600102,
                19200101,
                null,
                partij);

        builder.nieuwIdentificatienummersRecord(actieCorrectie).administratienummer(1234567890L).burgerservicenummer(123123123).eindeRecord();

        final PersoonHisVolledigImpl persoon = builder.build();

        final MaterieleHistorieSet<HisPersoonIdentificatienummersModel> idSet = persoon.getPersoonIdentificatienummersHistorie();
        idSet.getVervallenHistorie().iterator().next().setNadereAanduidingVerval(new NadereAanduidingVervalAttribuut(NadereAanduidingVerval.O));

        PersoonHisVolledigUtil.maakVerantwoording(persoon, actieGeboorte, actieCorrectie);

        debugHistorieSet(persoon.getPersoonIdentificatienummersHistorie());

        final List<Lo3CategorieWaarde> resultaat = uitvoeren(persoon, actieCorrectie);
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_01,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "123123123",
            Lo3ElementEnum.ELEMENT_8410,
            "",
            Lo3ElementEnum.ELEMENT_8610,
            "19600102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_51,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "123456789",
            Lo3ElementEnum.ELEMENT_8410,
            "O",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testGroep88RniDeelnemer() {
        final Partij rniPartij = new Partij(null, new SoortPartijAttribuut(SoortPartij.OVERHEIDSORGAAN), new PartijCodeAttribuut(891401), null, null,
            null, null, null, null);
        final ActieModel actie =
            PersoonHisVolledigUtil.maakActie(
                2L,
                SoortAdministratieveHandeling.G_B_A_BIJHOUDING_ACTUEEL,
                SoortActie.CONVERSIE_G_B_A,
                19400101,
                rniPartij);

        final ActieBronModel bron = new ActieBronModel(actie, null, null, new OmschrijvingEnumeratiewaardeAttribuut("rechtsgrondomschrijving"));
        actie.getBronnen().add(bron);

        builder.nieuwIdentificatienummersRecord(actie).administratienummer(1234567890L).burgerservicenummer(123123123).eindeRecord();
        final List<Lo3CategorieWaarde> resultaat = uitvoeren(actie);

        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_01,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "123123123",
            Lo3ElementEnum.ELEMENT_8810,
            "0601",
            Lo3ElementEnum.ELEMENT_8820,
            "rechtsgrondomschrijving",
            Lo3ElementEnum.ELEMENT_8510,
            "19400101",
            Lo3ElementEnum.ELEMENT_8610,
            "19400102");
        assertElementen(
            resultaat,
            Lo3CategorieEnum.CATEGORIE_51,
            true,
            Lo3ElementEnum.ELEMENT_0120,
            "123456789",
            Lo3ElementEnum.ELEMENT_8810,
            "",
            Lo3ElementEnum.ELEMENT_8820,
            "",
            Lo3ElementEnum.ELEMENT_8510,
            "19200101",
            Lo3ElementEnum.ELEMENT_8610,
            "19200102");
        Assert.assertEquals(2, resultaat.size());
    }

}
