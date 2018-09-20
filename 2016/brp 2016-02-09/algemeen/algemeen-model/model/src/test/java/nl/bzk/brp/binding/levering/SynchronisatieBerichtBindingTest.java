/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.levering;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertNotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import nl.bzk.brp.binding.AbstractBindingUitIntegratieTest;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonOnderzoek;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusOnderzoek;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestElementBuilder;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieConversieGBABericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.GegevenInOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaat.HuidigeSituatiePredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AbstractSynchronisatieBericht;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import nl.bzk.brp.util.PersoonViewUtil;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.GegevenInOnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.KindHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.OnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonOnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonIdZetter;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.jibx.runtime.JiBXException;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class SynchronisatieBerichtBindingTest extends AbstractBindingUitIntegratieTest<AbstractSynchronisatieBericht> {
    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String ID     = "iD";

    @Test
    public void bindingPersoonMetOuder() throws JiBXException {
        final PersoonHisVolledigImpl persoon = maakPersoon();
        TestPersoonIdZetter.zetIds(persoon);

        final VolledigBericht bericht = maakBericht();
        final PersoonHisVolledigView persoonView = new PersoonHisVolledigView(persoon, new HuidigeSituatiePredikaat());
        bericht.getAdministratieveHandeling().setBijgehoudenPersonen(Collections.singletonList(persoonView));
        PersoonViewUtil.zetAlleMagGeleverdWordenVlaggen(persoonView);

        final String xml = marshalObject(bericht);

        assertNotNull(xml);
        valideerTegenSchema(xml);
    }

    @Test
    public void bindingPersoonMetOnbekendeOuder() throws JiBXException {
        final PersoonHisVolledigImpl persoon = maakPersoon();

        final RelatieHisVolledig relatie = persoon.getKindBetrokkenheid().getRelatie();
        final BetrokkenheidHisVolledig ouder = relatie.getOuderBetrokkenheden().iterator().next();

        final VolledigBericht bericht = maakBericht();
        final PersoonHisVolledigView persoonView = new PersoonHisVolledigView(persoon, new HuidigeSituatiePredikaat());
        bericht.getAdministratieveHandeling().setBijgehoudenPersonen(Collections.singletonList(persoonView));

        PersoonViewUtil.zetAlleMagGeleverdWordenVlaggen(persoonView);
        ReflectionTestUtils.setField(ouder, "persoon", null);
        TestPersoonIdZetter.zetIds(persoon);

        final String xml = marshalObject(bericht);

        assertNotNull(xml);
        valideerTegenSchema(xml);
    }

    @Test
    public void bindingPersoonJohnny() throws JiBXException, IllegalAccessException {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        final VolledigBericht bericht = maakBericht();
        final PersoonHisVolledigView persoonView = new PersoonHisVolledigView(persoon, null);

        PersoonViewUtil.zetAlleMagGeleverdWordenVlaggen(persoonView);

        bericht.getAdministratieveHandeling().setBijgehoudenPersonen(Collections.singletonList(persoonView));

        final String xml = marshalObject(bericht);

        assertNotNull(xml);
        valideerTegenSchema(xml);
    }

    @Test
    public void bindingPersoonMetOntbrekendeOuder() throws JiBXException {
        final PersoonHisVolledigImpl persoon = maakPersoon();

        final RelatieHisVolledig relatie = persoon.getKindBetrokkenheid().getRelatie();
        final OuderHisVolledig ouder = relatie.getOuderBetrokkenheden().iterator().next();
        relatie.getBetrokkenheden().remove(ouder);

        final VolledigBericht bericht = maakBericht();
        final PersoonHisVolledigView persoonView = new PersoonHisVolledigView(persoon, new HuidigeSituatiePredikaat());
        bericht.getAdministratieveHandeling().setBijgehoudenPersonen(Collections.singletonList(persoonView));

        final String xml = marshalObject(bericht);

        assertNotNull(xml);
        valideerTegenSchema(xml);
    }

    @Test
    public void bindingPersoonMetOnderzoek() throws JiBXException, NoSuchFieldException, IllegalAccessException {
        final PersoonHisVolledigImpl persoon = maakPersoon();

        final Element element = TestElementBuilder.maker().metId(10).metNaam(ElementEnum.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER).metElementNaam(
            "Persoon.Identificatie.Bsn").maak();
        final GegevenInOnderzoekHisVolledigImpl gegevenInOnderzoek = new GegevenInOnderzoekHisVolledigImplBuilder(element, new SleutelwaardeAttribuut(1L),
            new SleutelwaardeAttribuut(2L), true).build();

        final OnderzoekHisVolledigImpl onderzoek = new OnderzoekHisVolledigImplBuilder(true).nieuwStandaardRecord(maakActie(20150909)).datumAanvang(20150909)
            .omschrijving("is niet pluis").status(StatusOnderzoek.IN_UITVOERING).verwachteAfhandeldatum(20160101).eindeRecord()
            .voegGegevenInOnderzoekToe(gegevenInOnderzoek).build();
        final HisOnderzoekModel actueleRecord = onderzoek.getOnderzoekHistorie().getActueleRecord();
        ReflectionTestUtils.setField(actueleRecord, ID, 4);
        ReflectionTestUtils.setField(gegevenInOnderzoek, ID, 1);
        ReflectionTestUtils.setField(onderzoek, ID, 2);

        final Set<PersoonOnderzoekHisVolledigImpl> persoonOnderzoeken = persoon.getOnderzoeken();
        final PersoonOnderzoekHisVolledigImpl persoonOnderzoek = new PersoonOnderzoekHisVolledigImplBuilder(persoon, onderzoek, true)
            .nieuwStandaardRecord(maakActie(20150909))
            .rol(SoortPersoonOnderzoek.DIRECT).eindeRecord().build();

        ReflectionTestUtils.setField(persoonOnderzoek, ID, 3);
        persoonOnderzoeken.add(persoonOnderzoek);

        final VolledigBericht bericht = maakBericht();

        final PersoonHisVolledigView persoonView = new PersoonHisVolledigView(persoon, new HuidigeSituatiePredikaat());

        PersoonViewUtil.zetAlleMagGeleverdWordenVlaggen(persoonView);
        bericht.getAdministratieveHandeling().setBijgehoudenPersonen(Collections.singletonList(persoonView));

        final String xml = marshalObject(bericht);

        assertNotNull(xml);
        valideerTegenSchema(xml);
    }

    /**
     * Maak bericht.
     *
     * @return vul bericht
     */
    private VolledigBericht maakBericht() {
        final AdministratieveHandelingModel admhnd = new AdministratieveHandelingModel(
            new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.CORRECTIE_ADRES),
            StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM, new OntleningstoelichtingAttribuut(""), DatumTijdAttribuut.bouwDatumTijd(2014, 12, 31));
        final AdministratieveHandelingSynchronisatie synchronisatie = new AdministratieveHandelingSynchronisatie(admhnd);

        final VolledigBericht bericht = new VolledigBericht(synchronisatie);
        bericht.setStuurgegevens(maakStuurgegevensVoorSynchronisatieBericht("00000000-0000-0000-0000-000011112222"));
        bericht.setBerichtParameters(maakParametersVoorSynchronisatieBericht("Test abo"));

        return bericht;
    }

    private PersoonHisVolledigImpl maakPersoon() {

        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .nieuwGeboorteRecord(20100101)
            .datumGeboorte(20100101)
            .woonplaatsnaamGeboorte("Broek op lange dijk")
            .eindeRecord();

        final PersoonHisVolledigImpl ouderPersoon = new PersoonHisVolledigImplBuilder(SoortPersoon.DUMMY).build();
        ReflectionTestUtils.setField(ouderPersoon, ID, 123);

        final FamilierechtelijkeBetrekkingHisVolledigImpl familie = new FamilierechtelijkeBetrekkingHisVolledigImplBuilder()
            .nieuwStandaardRecord(maakActie(20100101)).eindeRecord().build();
        ReflectionTestUtils.setField(familie, ID, 3579);
        final BetrokkenheidHisVolledigImpl kind = new KindHisVolledigImplBuilder(familie, null).build();
        final BetrokkenheidHisVolledigImpl ouder = new OuderHisVolledigImplBuilder(familie, ouderPersoon)
            .nieuwOuderlijkGezagRecord(20100101, null, 20100101)
            .indicatieOuderHeeftGezag(Boolean.TRUE)
            .eindeRecord()
            .nieuwOuderschapRecord(20100101, null, 20100101)
            .indicatieOuderUitWieKindIsGeboren(true)
            .indicatieOuder(Ja.J)
            .eindeRecord()
            .build();

        ReflectionTestUtils.setField(kind, ID, 1);
        ReflectionTestUtils.setField(ouder, ID, 2);

        familie.setBetrokkenheden(new HashSet<>(asList(kind, ouder)));
        builder.voegBetrokkenheidToe(kind);

        final PersoonHisVolledigImpl persoon = builder.build();
        ReflectionTestUtils.setField(persoon, ID, 321);

        persoon.getPersoonGeboorteHistorie().getActueleRecord().getDatumGeboorte().setMagGeleverdWorden(true);
        final OuderHisVolledigImpl ouderHisVolledig = persoon.getKindBetrokkenheid().getRelatie().getOuderBetrokkenheden().iterator().next();
        ouderHisVolledig.getOuderOuderlijkGezagHistorie().getActueleRecord().getIndicatieOuderHeeftGezag().setMagGeleverdWorden(true);
        ouderHisVolledig.getOuderOuderschapHistorie().getActueleRecord().getIndicatieOuderUitWieKindIsGeboren().setMagGeleverdWorden(true);
        ouderHisVolledig.getOuderOuderschapHistorie().getActueleRecord().getIndicatieOuder().setMagGeleverdWorden(true);

        TestPersoonIdZetter.zetIds(persoon);
        return persoon;
    }

    protected ActieModel maakActie(final Integer datum) {
        final ActieBericht actieBericht = new ActieConversieGBABericht();
        actieBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datum));
        actieBericht.setDatumEindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datum));
        actieBericht.setTijdstipRegistratie(new DatumTijdAttribuut(new DatumAttribuut(datum).toDate()));
        final ActieModel actieModel = new ActieModel(actieBericht, null);
        final Integer randomActieId = new Random().nextInt();
        ReflectionTestUtils.setField(actieModel, ID, Math.abs(randomActieId.longValue()));
        return actieModel;
    }

    @Override
    protected Class<AbstractSynchronisatieBericht> getBindingClass() {
        return AbstractSynchronisatieBericht.class;
    }

    @Override
    protected String getSchemaBestand() {
        return "brp0200_lvgSynchronisatie_Berichten.xsd";
    }
}
