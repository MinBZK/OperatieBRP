/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.autconv;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortElementAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.test.dal.data.Data;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import org.junit.Assert;
import org.junit.Test;

@Data(resources = {"classpath:dbunit-alleen-gba-autorisatie.xml", "classpath:dbunit-cleanup.xml"})
public class GbaAutConvIntegratieTest extends AbstractIntegratietest {

    private static final String AFLEVERPUNT_BRP = "mijnuri";

    static {
        System.setProperty("afleverpunt", AFLEVERPUNT_BRP);
    }

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager em;

    @Test
    public void test() throws IOException {
        final List<Leveringsautorisatie> preConversie = geefLeveringautorisaties();
        Assert.assertTrue(preConversie.stream().anyMatch(leveringsautorisatie -> leveringsautorisatie.getStelsel() == Stelsel.GBA));
        Assert.assertFalse(preConversie.stream().anyMatch(leveringsautorisatie -> leveringsautorisatie.getStelsel() == Stelsel.BRP));
        getConverteerder().converteer();
        controleerConversieLeveringsautorisatie();
        controleerConversieToegangLeveringsautorisatie();
        controleerAfnemerindicaties();
    }

    private void controleerAfnemerindicaties() {
        final List<PersoonAfnemerindicatie> persoonAfnemerindicaties = geefAfnemerindicaties();
        Assert.assertEquals(2, persoonAfnemerindicaties.stream().filter(persoonAfnemerindicatie -> persoonAfnemerindicatie
                .getLeveringsautorisatie().getStelsel() == Stelsel.BRP).count());
        Assert.assertEquals(2, persoonAfnemerindicaties.stream().filter(persoonAfnemerindicatie -> persoonAfnemerindicatie
                .getLeveringsautorisatie().getStelsel() == Stelsel.GBA).count());
    }

    private void controleerConversieToegangLeveringsautorisatie() {
        final List<ToegangLeveringsAutorisatie> toegangLeveringsAutorisaties = geefLo3ToegangLeveringsautorisaties();
        final Optional<ToegangLeveringsAutorisatie> optionalLo3Autorisatie = toegangLeveringsAutorisaties.stream().filter(t
                -> t.getLeveringsautorisatie().getStelsel() == Stelsel.GBA).findFirst();
        Assert.assertTrue(optionalLo3Autorisatie.isPresent());

        final Optional<ToegangLeveringsAutorisatie> optionalBrpAutorisatie = toegangLeveringsAutorisaties.stream().filter(t
                -> t.getLeveringsautorisatie().getStelsel() == Stelsel.BRP).findFirst();
        Assert.assertTrue(optionalBrpAutorisatie.isPresent());

        final ToegangLeveringsAutorisatie lo3toegang = optionalLo3Autorisatie.get();
        final ToegangLeveringsAutorisatie brptoegang = optionalBrpAutorisatie.get();

        Assert.assertEquals(lo3toegang.getDatumIngang(), brptoegang.getDatumIngang());
        Assert.assertEquals(20130101, brptoegang.getDatumIngang().intValue());
        Assert.assertEquals(lo3toegang.getDatumEinde(), brptoegang.getDatumEinde());
        Assert.assertEquals(20200101, brptoegang.getDatumEinde().intValue());
        Assert.assertEquals(AFLEVERPUNT_BRP, brptoegang.getAfleverpunt());
        Assert.assertEquals(lo3toegang.getIndicatieGeblokkeerd(), brptoegang.getIndicatieGeblokkeerd());
        Assert.assertNull(brptoegang.getIndicatieGeblokkeerd());
        Assert.assertEquals(lo3toegang.getNaderePopulatiebeperking(), brptoegang.getNaderePopulatiebeperking());
        Assert.assertEquals("naderepopulatiebeperking", brptoegang.getNaderePopulatiebeperking());
        Assert.assertNull(brptoegang.getTransporteur());
        Assert.assertNull(brptoegang.getOndertekenaar());

        controleerPartijRol(lo3toegang.getGeautoriseerde(), brptoegang.getGeautoriseerde());
    }

    private void controleerPartijRol(final PartijRol lo3partijrol, final PartijRol brppartijrol) {
        Assert.assertFalse(lo3partijrol == brppartijrol);
        Assert.assertEquals(lo3partijrol.getDatumIngang(), brppartijrol.getDatumIngang());
        Assert.assertEquals(lo3partijrol.getDatumEinde(), brppartijrol.getDatumEinde());
        Assert.assertEquals(lo3partijrol.getRol(), brppartijrol.getRol());
        controleerPartij(lo3partijrol.getPartij(), brppartijrol.getPartij());
    }

    private void controleerPartij(final Partij lo3partij, final Partij brppartij) {
        Assert.assertEquals(lo3partij.getDatumIngang(), brppartij.getDatumIngang());
        Assert.assertEquals(lo3partij.getDatumEinde(), brppartij.getDatumEinde());
        Assert.assertEquals(lo3partij.getNaam() + "BRP" + lo3partij.getId().toString(), brppartij.getNaam());
        Assert.assertEquals(lo3partij.getOin(), brppartij.getOin());
        Assert.assertEquals(lo3partij.getIndicatieAutomatischFiatteren(), brppartij.getIndicatieAutomatischFiatteren());
        Assert.assertEquals(DatumUtil.vandaag(), brppartij.getDatumOvergangNaarBrp().intValue());
        Assert.assertNotEquals(lo3partij.getCode(), brppartij.getCode());
    }

    private void controleerConversieLeveringsautorisatie() {
        //assert nu een GBA + BRP autorsatie
        final List<Leveringsautorisatie> postConversie = geefLeveringautorisaties();
        Assert.assertTrue(postConversie.stream().anyMatch(leveringsautorisatie -> leveringsautorisatie.getStelsel() == Stelsel.GBA));
        Assert.assertTrue(postConversie.stream().anyMatch(leveringsautorisatie -> leveringsautorisatie.getStelsel() == Stelsel.BRP));

        //start controle conversieregels
        final Optional<Leveringsautorisatie> optionalLo3Autorisatie = postConversie.stream().filter(leveringsautorisatie
                -> leveringsautorisatie.getStelsel() == Stelsel.GBA).findFirst();
        Assert.assertTrue(optionalLo3Autorisatie.isPresent());

        final Optional<Leveringsautorisatie> optionalBrpAutorisatie = postConversie.stream().filter(leveringsautorisatie
                -> leveringsautorisatie.getStelsel() == Stelsel.BRP).findFirst();
        Assert.assertTrue(optionalBrpAutorisatie.isPresent());

        final Leveringsautorisatie lo3levAut = optionalLo3Autorisatie.get();
        final Leveringsautorisatie brpLevAut = optionalBrpAutorisatie.get();

        Assert.assertNotEquals(lo3levAut.getNaam(), brpLevAut.getNaam());
        Assert.assertEquals("Rijksinstituut voor Volksgezondheid en Milieu (RIVM)/Onderzoek & crisissitBRP" + lo3levAut.getId(), brpLevAut.getNaam());
        Assert.assertEquals(lo3levAut.getIndicatieModelautorisatie(), brpLevAut.getIndicatieModelautorisatie());
        Assert.assertEquals(false, brpLevAut.getIndicatieModelautorisatie());
        Assert.assertEquals(lo3levAut.getProtocolleringsniveau(), brpLevAut.getProtocolleringsniveau());
        Assert.assertNull(brpLevAut.getProtocolleringsniveau());
        Assert.assertEquals(lo3levAut.getIndicatieAliasSoortAdministratieveHandelingLeveren(),
                brpLevAut.getIndicatieAliasSoortAdministratieveHandelingLeveren());
        Assert.assertNull(brpLevAut.getIndicatieAliasSoortAdministratieveHandelingLeveren());
        Assert.assertEquals(lo3levAut.getDatumIngang(), brpLevAut.getDatumIngang());
        Assert.assertEquals(20130101, brpLevAut.getDatumIngang().intValue());
        Assert.assertEquals(lo3levAut.getDatumEinde(), brpLevAut.getDatumEinde());
        Assert.assertNull(brpLevAut.getDatumEinde());
        Assert.assertEquals(lo3levAut.getPopulatiebeperking(), brpLevAut.getPopulatiebeperking());
        Assert.assertEquals("WAAR", brpLevAut.getPopulatiebeperking());
        Assert.assertEquals(lo3levAut.getToelichting(), brpLevAut.getToelichting());
        Assert.assertEquals("toelichting", brpLevAut.getToelichting());
        Assert.assertEquals(lo3levAut.getIndicatieGeblokkeerd(), brpLevAut.getIndicatieGeblokkeerd());
        Assert.assertNull(brpLevAut.getIndicatieGeblokkeerd());

        controleerDienstbundel(lo3levAut, brpLevAut);
    }

    private void controleerDienstbundel(final Leveringsautorisatie lo3levAut, final Leveringsautorisatie brpLevAut) {
        Assert.assertEquals(lo3levAut.getDienstbundelSet().size(), 1);
        Assert.assertEquals(brpLevAut.getDienstbundelSet().size(), 1);

        final Dienstbundel lo3bundel = lo3levAut.getDienstbundelSet().iterator().next();
        final Dienstbundel brpbundel = brpLevAut.getDienstbundelSet().iterator().next();

        Assert.assertNotEquals(lo3bundel.getNaam(), brpbundel.getNaam());
        Assert.assertEquals("Regionale Uitvoeringsdienst Drenthe (BSBm: Bestuurlijke Strafbeschikking mBRP" + lo3bundel.getId(), brpbundel.getNaam());
        Assert.assertEquals(lo3bundel.getIndicatieGeblokkeerd(), brpbundel.getIndicatieGeblokkeerd());
        Assert.assertNull(brpbundel.getIndicatieGeblokkeerd());
        Assert.assertEquals(lo3bundel.getToelichting(), brpbundel.getToelichting());
        Assert.assertNull(brpbundel.getToelichting());
        Assert.assertEquals(lo3bundel.getDatumIngang(), brpbundel.getDatumIngang());
        Assert.assertEquals(20130101, brpbundel.getDatumIngang().intValue());
        Assert.assertEquals(lo3bundel.getDatumEinde(), brpbundel.getDatumEinde());
        Assert.assertEquals(20200101, brpbundel.getDatumEinde().intValue());
        Assert.assertEquals(lo3bundel.getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd(),
                brpbundel.getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd());
        Assert.assertNull(brpbundel.getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd());

        controleerDienst(lo3bundel, brpbundel);

        controleerDienstbundelGroep(brpbundel.getDienstbundelGroepSet());
    }

    private void controleerDienstbundelGroep(final Set<DienstbundelGroep> dienstbundelGroepSet) {

        final Map<Element, DienstbundelGroep> groepMap = Maps.newHashMap();
        dienstbundelGroepSet.forEach(dienstbundelGroep -> groepMap.put(dienstbundelGroep.getGroep(), dienstbundelGroep));

        final Map<Element, Element> attribuutMap = Maps.newHashMap();
        dienstbundelGroepSet.forEach(dienstbundelGroep -> dienstbundelGroep.getDienstbundelGroepAttribuutSet()
                .forEach(dienstbundelGroepAttribuut -> attribuutMap.put(dienstbundelGroepAttribuut.getAttribuut(), dienstbundelGroep.getGroep())));

        final Consumer<Element> assertAlleenVerantwoording = (e) -> {
            final DienstbundelGroep g = groepMap.get(e);
            Assert.assertNotNull(g);
            Assert.assertFalse(g.getIndicatieFormeleHistorie());
            Assert.assertFalse(g.getIndicatieMaterieleHistorie());
            Assert.assertTrue(g.getIndicatieVerantwoording());
        };
        final Consumer<Element> assertAlles = (e) -> {
            final DienstbundelGroep g = groepMap.get(e);
            Assert.assertNotNull(g);
            Assert.assertTrue(g.getIndicatieFormeleHistorie());
            Assert.assertTrue(g.getIndicatieMaterieleHistorie());
            Assert.assertTrue(g.getIndicatieVerantwoording());
        };
        final Consumer<Element> assertMaterieelEnVerantwoording = (e) -> {
            final DienstbundelGroep g = groepMap.get(e);
            Assert.assertNotNull(g);
            Assert.assertFalse(g.getIndicatieFormeleHistorie());
            Assert.assertTrue(g.getIndicatieMaterieleHistorie());
            Assert.assertTrue(g.getIndicatieVerantwoording());
        };
        final Consumer<Element> assertMaterieel = (e) -> {
            final DienstbundelGroep g = groepMap.get(e);
            Assert.assertNotNull(g);
            Assert.assertFalse(g.getIndicatieFormeleHistorie());
            Assert.assertTrue(g.getIndicatieMaterieleHistorie());
            Assert.assertFalse(g.getIndicatieVerantwoording());
        };

        //01.01.10
        Assert.assertTrue(attribuutMap.containsKey(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER));

        //02.81.20
        assertAlles.accept(Element.GERELATEERDEOUDER_PERSOON_IDENTITEIT);
        assertAlleenVerantwoording.accept(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE);
        assertAlleenVerantwoording.accept(Element.GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING);
        assertMaterieelEnVerantwoording.accept(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS);
        assertAlleenVerantwoording.accept(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM);
        assertAlleenVerantwoording.accept(Element.GERELATEERDEOUDER_OUDERSCHAP);
        assertAlles.accept(Element.GERELATEERDEOUDER_IDENTITEIT);
        assertAlleenVerantwoording.accept(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG);

        //58.12.10
        Assert.assertTrue(attribuutMap.containsKey(Element.PERSOON_ADRES_LOCATIEOMSCHRIJVING));

        assertMaterieel.accept(Element.PERSOON_ADRES_LOCATIEOMSCHRIJVING.getGroep());

        assertMaterieelEnVerantwoording.accept(Element.GERELATEERDEKIND_PERSOON_GEBOORTE);
        assertMaterieelEnVerantwoording.accept(Element.GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS);
        assertMaterieelEnVerantwoording.accept(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM);
        assertAlles.accept(Element.GERELATEERDEKIND_PERSOON_IDENTITEIT);
        assertMaterieelEnVerantwoording.accept(Element.PERSOON_OUDER_OUDERSCHAP);
        assertAlles.accept(Element.PERSOON_OUDER_IDENTITEIT);

        //52.01.10
        assertMaterieelEnVerantwoording.accept(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getGroep());

        //controleer uitzonderingen
        Assert.assertTrue(attribuutMap.containsKey(Element.PERSOON_SOORTCODE));
        Assert.assertTrue(attribuutMap.containsKey(Element.PERSOON_BIJHOUDING_BIJHOUDINGSAARDCODE));
        Assert.assertTrue(attribuutMap.containsKey(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE));

        //controleer conditionele uitzonderingen
        Assert.assertTrue(attribuutMap.containsKey(Element.PERSOON_ADRES_INDICATIEPERSOONAANGETROFFENOPADRES));
        Assert.assertTrue(attribuutMap.containsKey(Element.GERELATEERDEKIND_PERSOON_SOORTCODE));
        Assert.assertTrue(attribuutMap.containsKey(Element.GERELATEERDEOUDER_PERSOON_SOORTCODE));
        Assert.assertFalse(attribuutMap.containsKey(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SOORTCODE));
        Assert.assertFalse(attribuutMap.containsKey(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SOORTCODE));

        //controleer uitzonderingen mbt onderzoek
        final Consumer<Element> assertGeenVlaggen = (e) -> {
            final DienstbundelGroep g = groepMap.get(e);
            Assert.assertNotNull(g);
            Assert.assertFalse(g.getIndicatieFormeleHistorie());
            Assert.assertFalse(g.getIndicatieMaterieleHistorie());
            Assert.assertFalse(g.getIndicatieVerantwoording());
        };
        assertGeenVlaggen.accept(Element.ONDERZOEK_IDENTITEIT);
        assertGroepBevatOptioneleVerplichtAanbevolenAttributen(Element.ONDERZOEK_IDENTITEIT);
        assertGeenVlaggen.accept(Element.ONDERZOEK_STANDAARD);
        assertGroepBevatOptioneleVerplichtAanbevolenAttributen(Element.ONDERZOEK_STANDAARD);
        assertGeenVlaggen.accept(Element.GEGEVENINONDERZOEK_IDENTITEIT);
        assertGroepBevatOptioneleVerplichtAanbevolenAttributen(Element.GEGEVENINONDERZOEK_IDENTITEIT);
    }

    private void controleerDienst(final Dienstbundel lo3bundel, final Dienstbundel brpbundel) {
        Assert.assertEquals(lo3bundel.getDienstSet().size(), 1);
        Assert.assertEquals(brpbundel.getDienstSet().size(), 1);

        final Dienst lo3dienst = lo3bundel.getDienstSet().iterator().next();
        final Dienst brpdienst = brpbundel.getDienstSet().iterator().next();

        Assert.assertEquals(lo3dienst.getSoortDienst(), brpdienst.getSoortDienst());
        Assert.assertEquals(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING, brpdienst.getSoortDienst());
        Assert.assertEquals(lo3dienst.getIndicatieGeblokkeerd(), brpdienst.getIndicatieGeblokkeerd());
        Assert.assertNull(brpdienst.getIndicatieGeblokkeerd());
        Assert.assertEquals(lo3dienst.getDatumIngang(), brpdienst.getDatumIngang());
        Assert.assertEquals(20130101, brpdienst.getDatumIngang().intValue());
        Assert.assertEquals(lo3dienst.getDatumEinde(), brpdienst.getDatumEinde());
        Assert.assertEquals(20200101, brpdienst.getDatumEinde().intValue());
        Assert.assertEquals(lo3dienst.getAttenderingscriterium(), brpdienst.getAttenderingscriterium());
        Assert.assertEquals("WAAR", brpdienst.getAttenderingscriterium());
        Assert.assertEquals(lo3dienst.getEersteSelectieDatum(), brpdienst.getEersteSelectieDatum());
        Assert.assertEquals(lo3dienst.getEffectAfnemerindicaties(), brpdienst.getEffectAfnemerindicaties());
        Assert.assertEquals(lo3dienst.getMaximumAantalZoekresultaten(), brpdienst.getMaximumAantalZoekresultaten());
    }

    private List<Leveringsautorisatie> geefLeveringautorisaties() {
        final TypedQuery<Leveringsautorisatie> query =
                em.createQuery("select la from Leveringsautorisatie la ", Leveringsautorisatie.class);
        return query.getResultList();
    }

    private List<ToegangLeveringsAutorisatie> geefLo3ToegangLeveringsautorisaties() {
        final TypedQuery<ToegangLeveringsAutorisatie> query =
                em.createQuery("select tla from ToegangLeveringsAutorisatie tla "
                        , ToegangLeveringsAutorisatie.class);
        return query.getResultList();
    }

    private void assertBevatGroep(Element element, boolean formeel, boolean materieel, boolean verantwoording) {
        Assert.assertEquals("Groep niet aanwezig: " + element.getNaam(),
                queryBuilder().queryForObject(
                        String.format("select count(*) from autaut.dienstbundelgroep where groep = %d", element.getId()), Integer.class).intValue(), 1);
    }

    private List<PersoonAfnemerindicatie> geefAfnemerindicaties() {
        final TypedQuery<PersoonAfnemerindicatie> query =
                em.createQuery("select p from PersoonAfnemerindicatie p"
                        , PersoonAfnemerindicatie.class);
        return query.getResultList();
    }

    private void assertGroepBevatOptioneleVerplichtAanbevolenAttributen(Element element) {

        for (AttribuutElement attribuutElement : ElementHelper.getGroepElement(element).getAttributenInGroep()) {
            boolean moetBevatten = attribuutElement.getAutorisatie() == SoortElementAutorisatie.AANBEVOLEN
                    || attribuutElement.getAutorisatie() == SoortElementAutorisatie.VERPLICHT
                    || attribuutElement.getAutorisatie() == SoortElementAutorisatie.OPTIONEEL;
            assertBevatAttribuut(attribuutElement.getElement(), moetBevatten);
        }
    }

    private void assertBevatAttribuut(Element element, boolean bevat) {
        Assert.assertEquals("Groep niet aanwezig: " + element.getNaam(), queryBuilder().queryForObject(String.format("select count(*) from "
                + "autaut.dienstbundelgroepattr where attr = %d", element.getId()), Integer.class).intValue(), bevat ? 1 : 0);
    }
}
