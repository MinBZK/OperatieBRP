/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.geefmedebewoners;

import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.ModelAfdruk;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.MetaObjectFilter;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 */
public class RelatiefilterServiceImplTest {

    private RelatiefilterService relatiefilterService = new RelatiefilterServiceImpl();
    private Actie actie;


    @Before
    public void voorTest() {
        actie = TestVerantwoording.maakActie(1, DatumUtil.nuAlsZonedDateTime());
        final Actie actieInhoud = actie;
        BrpNu.set(actieInhoud.getTijdstipRegistratie());
    }

    /**
     * Voor elke gerelateerde persoon bestaat er een top-level persoon met gelijk BSN.
     * De gerelateerde personen worden dus niet verwijderd.
     */
    @Test
    public void testBetrokkenhedenBehouden() {
        final List<Persoonslijst> persoonslijstList = Lists.newArrayList();
        persoonslijstList.add(maakPersoonslijst("12345", maakKindMetOuders("11", "22"), maakOuderMetKind(), maakHuwelijkspartner(),
                maakHuwelijkGeregistreerdpartner()));
        persoonslijstList.add(maakPersoonslijst("11"));
        persoonslijstList.add(maakPersoonslijst("22"));
        persoonslijstList.add(maakPersoonslijst("33"));
        persoonslijstList.add(maakPersoonslijst("44"));
        persoonslijstList.add(maakPersoonslijst("55"));

        //sanitycheck
        assertPersoonlijstCompleet(persoonslijstList);

        final List<Persoonslijst> gefilterdePersoonslijst =
                relatiefilterService.filterRelaties(persoonslijstList, 0);
        System.out.println(ModelAfdruk.maakAfdruk(gefilterdePersoonslijst.get(0).getMetaObject()));
        Assert.assertEquals(6, gefilterdePersoonslijst.size());
        Assert.assertFalse(gefilterdePersoonslijst.get(0).getModelIndex()
                .geefObjecten(ElementHelper.getObjectElement(Element.PERSOON_KIND.getId())).isEmpty());
        Assert.assertFalse(gefilterdePersoonslijst.get(0).getModelIndex()
                .geefObjecten(ElementHelper.getObjectElement(Element.PERSOON_PARTNER.getId())).isEmpty());
        Assert.assertFalse(gefilterdePersoonslijst.get(0).getModelIndex()
                .geefObjecten(ElementHelper.getObjectElement(Element.PERSOON_OUDER.getId())).isEmpty());

    }

    /**
     * Verwijderd alle gerelateerde betrokkenheden want er zijn geen andere Personen met gelijk bsn.
     */
    @Test
    public void testVerwijderenBetrokkenheden() {
        final List<Persoonslijst> persoonslijstList = Lists.newArrayList();
        persoonslijstList.add(maakPersoonslijst("12345", maakKindMetOuders("11", "22"), maakOuderMetKind(), maakHuwelijkspartner(),
                maakHuwelijkGeregistreerdpartner()));

        //sanitycheck
        assertPersoonlijstCompleet(persoonslijstList);

        final List<Persoonslijst> gefilterdePersoonslijst = relatiefilterService.filterRelaties(persoonslijstList, 0);
        System.out.println(ModelAfdruk.maakAfdruk(gefilterdePersoonslijst.get(0).getMetaObject()));
        Assert.assertEquals(1, gefilterdePersoonslijst.size());
        Assert.assertTrue(gefilterdePersoonslijst.get(0).getModelIndex()
                .geefObjecten(ElementHelper.getObjectElement(Element.PERSOON_KIND.getId())).isEmpty());
        Assert.assertTrue(gefilterdePersoonslijst.get(0).getModelIndex()
                .geefObjecten(ElementHelper.getObjectElement(Element.PERSOON_PARTNER.getId())).isEmpty());
        Assert.assertTrue(gefilterdePersoonslijst.get(0).getModelIndex()
                .geefObjecten(ElementHelper.getObjectElement(Element.PERSOON_OUDER.getId())).isEmpty());

    }

    /**
     * Gerelateerde betrokkenheden zonder bsn worden weggefilterd
     */
    @Test
    public void testVerwijderenBetrokkenhedenZonderBsn() {
        final List<Persoonslijst> persoonslijstList = Lists.newArrayList();
        persoonslijstList.add(maakPersoonslijst("12345", maakOuderZonderBsnOpKind()));

        //sanitycheck
        assertPersoonlijstCompleet(persoonslijstList);

        final List<Persoonslijst> gefilterdePersoonslijst = relatiefilterService.filterRelaties(persoonslijstList, 0);
        System.out.println(ModelAfdruk.maakAfdruk(gefilterdePersoonslijst.get(0).getMetaObject()));
        Assert.assertEquals(1, gefilterdePersoonslijst.size());
        Assert.assertTrue(gefilterdePersoonslijst.get(0).getModelIndex()
                .geefObjecten(ElementHelper.getObjectElement(Element.PERSOON_OUDER.getId())).isEmpty());

    }

    /**
     * Gerelateerde betrokkenheden zonder bsn worden weggefilterd
     */
    @Test
    public void testVerwijderenEnkelGerelateerdePersoon() {
        final List<Persoonslijst> persoonslijstList = Lists.newArrayList();
        persoonslijstList.add(maakPersoonslijst("12345", maakKindMetOuders("2222", "3333")));
        persoonslijstList.add(maakPersoonslijst("2222"));

        //sanitycheck
        assertPersoonlijstCompleet(persoonslijstList);

        final List<Persoonslijst> gefilterdePersoonslijst = relatiefilterService.filterRelaties(persoonslijstList, 0);
        System.out.println(ModelAfdruk.maakAfdruk(gefilterdePersoonslijst.get(0).getMetaObject()));
        Assert.assertEquals(2, gefilterdePersoonslijst.size());
        Assert.assertFalse(gefilterdePersoonslijst.get(0).getModelIndex()
                .geefObjecten(ElementHelper.getObjectElement(Element.PERSOON_KIND.getId())).isEmpty());
        final Set<MetaObject> ouderObjecten = gefilterdePersoonslijst.get(0).getModelIndex()
                .geefObjecten(ElementHelper.getObjectElement(Element.GERELATEERDEOUDER.getId()));
        Assert.assertEquals(1, ouderObjecten.size());
        final MetaAttribuut ouderBsn = gefilterdePersoonslijst.get(0).getModelIndex().geefAttributen(ElementHelper
                .getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId())).iterator().next();
        Assert.assertEquals("2222", ouderBsn.<String>getWaarde());
        Assert.assertTrue(ouderObjecten.iterator().next() == ouderBsn.getParentRecord().getParentGroep().getParentObject().getParentObject());
    }

    private Persoonslijst maakPersoonslijst(String bsn, MetaObject.Builder... gerelateerdeBuilder) {

        //@formatter:off
        final MetaObject.Builder persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId())
                .metRecord()
                    .metId(0)
                    .metActieInhoud(actie)
                    .metAttribuut(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId(), bsn)
                .eindeRecord()
            .eindeGroep();

        for (MetaObject.Builder builder : gerelateerdeBuilder) {
            persoon.metObject(builder);
        }

        return new Persoonslijst(persoon.build(), 0L);
    }

    private MetaObject.Builder maakHuwelijkGeregistreerdpartner() {
        //@formatter:off
        return MetaObject.maakBuilder()
                .metObjectElement(getObjectElement(Element.PERSOON_PARTNER))
                .metId(123)
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_PARTNER_IDENTITEIT))
                    .metRecord()
                        .metActieInhoud(actie)
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metId(123)
                    .metObjectElement(getObjectElement(Element.GEREGISTREERDPARTNERSCHAP))
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.GEREGISTREERDPARTNERSCHAP_STANDAARD))
                        .metRecord()
                        .metActieInhoud(actie)
                        .eindeRecord()
                    .eindeGroep()
                    .metObject()
                        .metId(123)
                        .metObjectElement(getObjectElement(Element.GERELATEERDEGEREGISTREERDEPARTNER))
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_IDENTITEIT))
                            .metRecord()
                                .metActieInhoud(actie)
                            .eindeRecord()
                        .eindeGroep()
                        .metObject()
                            .metId(123)
                            .metObjectElement(getObjectElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON))
                            .metGroep()
                                .metGroepElement(getGroepElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS))
                                .metRecord()
                                    .metId(123)
                                    .metAttribuut(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId(), "55")
                                    .metActieInhoud(actie)
                                .eindeRecord()
                            .eindeGroep()
                        .eindeObject()
                    .eindeObject()
                .eindeObject()
            .eindeObject()
        .eindeObject();
        //@formatter:on
    }

    private MetaObject.Builder maakHuwelijkspartner() {
        //@formatter:off
        return MetaObject.maakBuilder()
                .metObjectElement(getObjectElement(Element.PERSOON_PARTNER))
                .metId(124)
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_PARTNER_IDENTITEIT))
                    .metRecord()
                        .metActieInhoud(actie)
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metId(124)
                    .metObjectElement(getObjectElement(Element.HUWELIJK))
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.HUWELIJK_STANDAARD))
                        .metRecord()
                            .metActieInhoud(actie)
                        .eindeRecord()
                    .eindeGroep()
                    .metObject()
                        .metId(124)
                        .metObjectElement(getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER))
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_IDENTITEIT))
                            .metRecord()
                                .metActieInhoud(actie)
                            .eindeRecord()
                        .eindeGroep()
                        .metObject()
                            .metId(124)
                            .metObjectElement(getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON))
                            .metGroep()
                                .metGroepElement(getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS))
                                .metRecord()
                                    .metId(124)
                                    .metAttribuut(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId(), "44")
                                    .metActieInhoud(actie)
                                .eindeRecord()
                            .eindeGroep()
                        .eindeObject()
                    .eindeObject()
                .eindeObject()
            .eindeObject()
        .eindeObject();
        //@formatter:on
    }

    private MetaObject.Builder maakOuderMetKind() {
        //@formatter:off
        return MetaObject.maakBuilder()
                .metObjectElement(getObjectElement(Element.PERSOON_OUDER))
                .metId(125)
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_OUDER_IDENTITEIT))
                    .metRecord()
                        .metActieInhoud(actie)
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metId(125)
                    .metObjectElement(getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING))
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_STANDAARD))
                        .metRecord()
                        .metActieInhoud(actie)
                        .eindeRecord()
                    .eindeGroep()
                    .metObject()
                        .metId(125)
                        .metObjectElement(getObjectElement(Element.GERELATEERDEKIND))
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.GERELATEERDEKIND_IDENTITEIT))
                            .metRecord()
                                .metActieInhoud(actie)
                            .eindeRecord()
                        .eindeGroep()
                        .metObject()
                            .metId(125)
                            .metObjectElement(getObjectElement(Element.GERELATEERDEKIND_PERSOON))
                            .metGroep()
                                .metGroepElement(getGroepElement(Element.GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS))
                                .metRecord()
                                    .metId(125)
                                    .metAttribuut(Element.GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId(), "33")
                                    .metActieInhoud(actie)
                                .eindeRecord()
                            .eindeGroep()
                        .eindeObject()
                    .eindeObject()
                .eindeObject()
            .eindeObject()
        .eindeObject();
        //@formatter:on
    }

    private MetaObject.Builder maakOuderZonderBsnOpKind() {
        //@formatter:off
        return MetaObject.maakBuilder()
                .metObjectElement(getObjectElement(Element.PERSOON_OUDER))
                .metId(126)
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_OUDER_IDENTITEIT))
                    .metRecord()
                        .metActieInhoud(actie)
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metId(126)
                    .metObjectElement(getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING))
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_STANDAARD))
                        .metRecord()
                        .metActieInhoud(actie)
                        .eindeRecord()
                    .eindeGroep()
                    .metObject()
                        .metId(126)
                        .metObjectElement(getObjectElement(Element.GERELATEERDEKIND))
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.GERELATEERDEKIND_IDENTITEIT))
                            .metRecord()
                                .metActieInhoud(actie)
                            .eindeRecord()
                        .eindeGroep()
                        .metObject()
                            .metId(126)
                            .metObjectElement(getObjectElement(Element.GERELATEERDEKIND_PERSOON))
                            .metGroep()
                                .metGroepElement(getGroepElement(Element.GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS))
                                .metRecord()
                                    .metId(126)
                                    .metActieInhoud(actie)
                                .eindeRecord()
                            .eindeGroep()
                        .eindeObject()
                    .eindeObject()
                .eindeObject()
            .eindeObject()
        .eindeObject();
        //@formatter:on
    }

    private MetaObject.Builder maakKindMetOuders(String bsnOuder1, String bsnOuder2) {
        //@formatter:off
        return MetaObject.maakBuilder()
                .metObjectElement(getObjectElement(Element.PERSOON_KIND))
                .metId(127)
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_KIND_IDENTITEIT))
                    .metRecord()
                        .metActieInhoud(actie)
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metId(127)
                    .metObjectElement(getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING))
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_STANDAARD))
                        .metRecord()
                        .metActieInhoud(actie)
                        .eindeRecord()
                    .eindeGroep()
                    .metObject()
                        .metId(127)
                        .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER))
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.GERELATEERDEOUDER_IDENTITEIT))
                            .metRecord()
                                .metActieInhoud(actie)
                            .eindeRecord()
                        .eindeGroep()
                        .metObject()
                            .metId(127)
                            .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER_PERSOON))
                            .metGroep()
                                .metGroepElement(getGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS))
                                .metRecord()
                                    .metId(127)
                                    .metAttribuut(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId(), bsnOuder1)
                                    .metActieInhoud(actie)
                                .eindeRecord()
                            .eindeGroep()
                        .eindeObject()
                    .eindeObject()
                    .metObject()
                        .metId(1272)
                        .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER))
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.GERELATEERDEOUDER_IDENTITEIT))
                            .metRecord()
                                .metActieInhoud(actie)
                            .eindeRecord()
                        .eindeGroep()
                        .metObject()
                            .metId(1272)
                            .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER_PERSOON))
                            .metGroep()
                                .metGroepElement(getGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS))
                                .metRecord()
                                    .metId(1272)
                                    .metAttribuut(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId(), bsnOuder2)
                                    .metActieInhoud(actie)
                                .eindeRecord()
                            .eindeGroep()
                        .eindeObject()
                    .eindeObject()
                .eindeObject()
            .eindeObject()
        .eindeObject();
        //@formatter:on
    }

    /**
     * Als de MetaObject structuur niet compleet is, omdat bijvoorbeeld groepen ontbreken,
     * dan zal het {@link MetaObjectFilter} deze verwijderen. Dit geeft mogelijk false positives in
     * de test. Deze methode
     */
    private void assertPersoonlijstCompleet(final List<Persoonslijst> persoonslijstList) {
        for (Persoonslijst persoonslijst : persoonslijstList) {
            final MetaObject object1 = persoonslijst.getMetaObject();
            final MetaObject object2 = new MetaObjectFilter(object1).filter();
            final boolean gelijk = object1.deepEquals(object2);

            if (!gelijk) {
                System.out.println();
                System.out.printf("ongefilterd:\n");
                System.out.println(ModelAfdruk.maakAfdruk(object1));
                System.out.println();
                System.out.printf("gefilterd:\n");
                System.out.println(ModelAfdruk.maakAfdruk(object2));
            }
            Assert.assertTrue(gelijk);
        }
    }
}
