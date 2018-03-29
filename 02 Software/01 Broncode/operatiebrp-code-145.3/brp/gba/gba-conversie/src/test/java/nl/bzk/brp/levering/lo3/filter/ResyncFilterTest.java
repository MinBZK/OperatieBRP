/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.filter;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Assert;
import org.junit.Test;

public class ResyncFilterTest {

    private static final String RUBRIEK_REISDOCUMENT = "12.35.10";

    private final Actie         actieInitieleVulling;
    private final Actie         actieSynchronisatie;
    private final AdministratieveHandeling administratieveHandelingInitieleVulling;
    private final AdministratieveHandeling administratieveHandelinSynchronisatie;

    {
        final ZonedDateTime tsReg = ZonedDateTime.of(1920, 1, 2, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID);
        administratieveHandelingInitieleVulling = AdministratieveHandeling.converter().converteer(TestVerantwoording
            .maakAdministratieveHandeling(-21L, "000034", tsReg, SoortAdministratieveHandeling.GBA_INITIELE_VULLING)
            .metObject(TestVerantwoording.maakActieBuilder(1, SoortActie.CONVERSIE_GBA, tsReg, "000001", 19620101)
        ).build());
        actieInitieleVulling = administratieveHandelingInitieleVulling.getActies().iterator().next();

        final ZonedDateTime tsReg2 = ZonedDateTime.of(2014, 7, 11, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID);
        administratieveHandelinSynchronisatie = AdministratieveHandeling.converter().converteer(TestVerantwoording
            .maakAdministratieveHandeling(-22l, "000034", tsReg2, SoortAdministratieveHandeling
                .GBA_INITIELE_VULLING)
            .metObject(TestVerantwoording.maakActieBuilder(1, SoortActie.CONVERSIE_GBA, tsReg2, "000001", 19630101)
            ).build());
        actieSynchronisatie = administratieveHandelinSynchronisatie.getActies().iterator().next();

    }

    @Test
    public void test() {
        final MetaObject.Builder basispersoon = MetaObject.maakBuilder();
        basispersoon.metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId())).metId(4645);

        voegGeboorteToe(basispersoon, actieInitieleVulling);
        voegReisdocumentToe(basispersoon, actieSynchronisatie);

        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(administratieveHandelingInitieleVulling);
        administratieveHandelingen.add(administratieveHandelinSynchronisatie);
        final Persoonslijst persoonslijst = new Persoonslijst(basispersoon.build(), 0L);

        // Debug output
        System.out.println("-GROEPEN-");
        persoonslijst.getMetaObject().getGroepen().stream().forEach(metaGroep -> System.out.println("GROEP: " + metaGroep));
        System.out.println("-OBJECTEN-");
        persoonslijst.getMetaObject().getObjecten().stream().forEach(metaObject -> {
            System.out.println("OBJECT: " + metaObject);
            metaObject.getGroepen().stream().forEach(metaGroep -> System.out.println(" - GROEP: " + metaGroep));
        });

        final List<String> resyncRubrieken = ResyncBerichtFilter.bepaalRubrieken(persoonslijst, null, administratieveHandelinSynchronisatie);
        System.out.println(resyncRubrieken);

        // Zie RubriekenMap
        Assert.assertTrue(resyncRubrieken.contains("12.35."));
        Assert.assertTrue(resyncRubrieken.contains("12.8"));

        final List<String> filterRubriekenMetReisdocument = Arrays.asList(RUBRIEK_REISDOCUMENT);
        final List<String> filterRubriekenZonderReisdocument = Arrays.asList("01.01.10");

        Assert.assertTrue(ResyncBerichtFilter.bevatRubrieken(filterRubriekenMetReisdocument, resyncRubrieken));
        Assert.assertFalse(ResyncBerichtFilter.bevatRubrieken(filterRubriekenZonderReisdocument, resyncRubrieken));
    }

    @Test
    public void testFallback() {
        final MetaObject.Builder basispersoon = MetaObject.maakBuilder();
        basispersoon.metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId())).metId(4645);

        voegGeboorteToe(basispersoon, actieInitieleVulling);
        voegProbasToe(basispersoon, actieSynchronisatie);

        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(administratieveHandelingInitieleVulling);
        administratieveHandelingen.add(administratieveHandelinSynchronisatie);
        final Persoonslijst persoonslijst = new Persoonslijst(basispersoon.build(), 0L);

        final List<String> resyncRubrieken = ResyncBerichtFilter.bepaalRubrieken(persoonslijst, null, administratieveHandelinSynchronisatie);
        // Zie RubriekenMap
        Assert.assertEquals(0, resyncRubrieken.size());

        final List<String> filterRubrieken = Arrays.asList(RUBRIEK_REISDOCUMENT);
        Assert.assertTrue(ResyncBerichtFilter.bevatRubrieken(filterRubrieken, resyncRubrieken));
    }

    private void voegGeboorteToe(final MetaObject.Builder builder, final Actie actieGeboorte) {
        // Geboorte
        builder.metGroep()
            .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_GEBOORTE.getId()))
            .metRecord()
            .metId(54654)
            .metActieInhoud(actieGeboorte)
            .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM.getId()), 20040101)
            .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_GEBOORTE_GEMEENTECODE.getId()), 0626)
            .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_GEBOORTE_LANDGEBIEDCODE.getId()), 6030)
            .eindeRecord()
            .eindeGroep();
    }

    private void voegReisdocumentToe(final MetaObject.Builder builder, final Actie actieReisdocument) {
        // Reisdocument
        final MetaObject.Builder reisdocument = builder.metObject().metId(2344).metObjectElement(Element.PERSOON_REISDOCUMENT.getId());
        final MetaGroep.Builder identGroep = reisdocument.metGroep().metGroepElement(Element.PERSOON_REISDOCUMENT_IDENTITEIT.getId());
        identGroep.metRecord().metId(2344).metAttribuut(Element.PERSOON_REISDOCUMENT_SOORTCODE.getId(), "PP");
        final MetaGroep.Builder standaardGroep = reisdocument.metGroep().metGroepElement(Element.PERSOON_REISDOCUMENT_STANDAARD.getId());
        standaardGroep.metRecord().metId(23441).metActieInhoud(actieReisdocument).metAttribuut(Element.PERSOON_REISDOCUMENT_NUMMER.getId(), "3454");
    }

    private void voegProbasToe(final MetaObject.Builder builder, final Actie actieProbas) {
        builder.metObject(
            MetaObject.maakBuilder()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE.getId()))
                .metId(2344)
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_IDENTITEIT.getId()))
                .metRecord()
                .metId(23551)
                .metAttribuut(
                    ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_SOORTNAAM.getId()),
                    SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE.toString())
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_STANDAARD.getId()))
                .metRecord()
                .metId(23552)
                .metActieInhoud(actieProbas)
                .metAttribuut(
                    ElementHelper.getAttribuutElement(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_WAARDE.getId()),
                    true)
                .eindeRecord()
                .eindeGroep()
                .eindeObject());
        // builder.metObjectElement(MetaObjectUtil.maakPersoonIndicatieBijzondereVerblijfsrechtelijkePositie(true));

        // final MetaGroep.Builder probasGroep =
        // builder.metGroep().metGroepElement(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE.getId());
        //
        // probasGroep.metRecord()
        // .metId(23551)
        // .metActieInhoud(actieProbas)
        // .metAttribuut(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE.getId(), true);
    }
}
