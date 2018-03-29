/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import com.google.common.collect.Lists;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class AutoriseerObjectenWaarvoorOnderliggendeAutorisatieBestaatServiceImplTest {

    private AutoriseerObjectenWaarvoorOnderliggendeAutorisatieBestaatServiceImpl service
            = new AutoriseerObjectenWaarvoorOnderliggendeAutorisatieBestaatServiceImpl();

    private Actie actieInhoud = TestVerantwoording.maakActie(1, DatumUtil.nuAlsZonedDateTime());

    @Test
    public void objectNietWegfilteren() {

        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
            .metObject()
                .metId(1)
                .metObjectElement(getObjectElement(Element.PERSOON_KIND.getId()))
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_KIND_IDENTITEIT.getId()))
                    .metRecord()
                        .metId(1)
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metId(2)
                    .metObjectElement(getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING.getId()))
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_STANDAARD.getId()))
                            .metRecord()
                                .metActieInhoud(actieInhoud)
                            .eindeRecord()
                    .eindeGroep()
                    .metObject()
                        .metId(3)
                        .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER.getId()))
                        .metObject()
                            .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER_PERSOON.getId()))
                            .metId(33)
                            .metGroep()
                                .metGroepElement(getGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS.getId()))
                                .metRecord()
                                    .metActieInhoud(actieInhoud)
                                .eindeRecord()
                            .eindeGroep()
                        .eindeObject()
                    .eindeObject()
                    //object zonder identificatienummers
                    .metObject()
                        .metId(4)
                        .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER.getId()))
                        .metObject()
                            .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER_PERSOON.getId()))
                            .metId(44)
                        .eindeObject()
                    .eindeObject()
                .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on
        final Element attribuutElement = Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER;
        final TestAutorisaties.GroepDefinitie groepDefinitie = new TestAutorisaties.GroepDefinitie();
        groepDefinitie.element = Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS;
        final Leveringsautorisatie leveringautorisatie = TestAutorisaties.maakLeveringsautorisatie(attribuutElement, SoortDienst
                .SYNCHRONISATIE_PERSOON, groepDefinitie);

        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(null,
                AutAutUtil.zoekDienst(leveringautorisatie, SoortDienst.SYNCHRONISATIE_PERSOON));
        maakBerichtParameters.setAutorisatiebundels(Lists.newArrayList(autorisatiebundel));
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT), autorisatiebundel, new StatischePersoongegevens());

        service.execute(berichtgegevens);

        // beide GerelateerdeOuder.Persoon objecten zijn geautoriseerd omdat er een autorisatie bestaat voor een onderliggend attribuut
        // de relatie is geautoriseerd omdat het onderliggende object is geautoriseerd
        assertGeautoriseerd(berichtgegevens, Element.GERELATEERDEOUDER_PERSOON, 2);

        // beide GerelateerdeOuder objecten zijn geautoriseerd omdat er een autorisatie bestaat voor een onderliggend attribuut
        // de relatie is geautoriseerd omdat het onderliggende object is geautoriseerd
        assertGeautoriseerd(berichtgegevens, Element.GERELATEERDEOUDER, 2);

        // de relatie is geautoriseerd omdat het onderliggende object is geautoriseerd
        assertGeautoriseerd(berichtgegevens, Element.FAMILIERECHTELIJKEBETREKKING, 1);

        // de betrokkenheid is geautoriseerd omdat het onderliggende object is geautoriseerd
        assertGeautoriseerd(berichtgegevens, Element.PERSOON_KIND, 1);

        // de persoon is geautoriseerd omdat het onderliggende object is geautoriseerd
        assertGeautoriseerd(berichtgegevens, Element.PERSOON, 1);
    }

    @Test
    public void objectNietWegfilteren_GeenAttrInDienstbundel() {

        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
            .metObject()
                .metId(1)
                .metObjectElement(getObjectElement(Element.PERSOON_KIND.getId()))
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_KIND_IDENTITEIT.getId()))
                    .metRecord()
                        .metId(1)
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metId(2)
                    .metObjectElement(getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING.getId()))
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_STANDAARD.getId()))
                            .metRecord()
                                .metActieInhoud(actieInhoud)
                            .eindeRecord()
                    .eindeGroep()
                    .metObject()
                        .metId(3)
                        .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER.getId()))
                        .metObject()
                            .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER_PERSOON.getId()))
                            .metId(33)
                            .metGroep()
                                .metGroepElement(getGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS.getId()))
                                .metRecord()
                                    .metActieInhoud(actieInhoud)
                                    .metAttribuut(getAttribuutElement(Element
                                        .GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER), "123456789")
                                .eindeRecord()
                            .eindeGroep()
                        .eindeObject()
                    .eindeObject()
                    //object zonder identificatienummers
                    .metObject()
                        .metId(4)
                        .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER.getId()))
                        .metObject()
                            .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER_PERSOON.getId()))
                            .metId(44)
                        .eindeObject()
                    .eindeObject()
                .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on
        final TestAutorisaties.GroepDefinitie groepDefinitie = new TestAutorisaties.GroepDefinitie();
        groepDefinitie.element = Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS;
        final Leveringsautorisatie leveringautorisatie = TestAutorisaties.maakLeveringsautorisatie(null, SoortDienst.SYNCHRONISATIE_PERSOON,
                groepDefinitie);

        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(null,
                AutAutUtil.zoekDienst(leveringautorisatie, SoortDienst.SYNCHRONISATIE_PERSOON));
        maakBerichtParameters.setAutorisatiebundels(Lists.newArrayList(autorisatiebundel));
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT), autorisatiebundel, new StatischePersoongegevens());

        service.execute(berichtgegevens);

        //niets geautoriseerd wat diensbundelgroep.attrset is leeg
        assertNietGeautoriseerd(berichtgegevens, Element.GERELATEERDEOUDER_PERSOON, 2);
        assertNietGeautoriseerd(berichtgegevens, Element.GERELATEERDEOUDER, 2);
        assertNietGeautoriseerd(berichtgegevens, Element.FAMILIERECHTELIJKEBETREKKING, 1);
        assertNietGeautoriseerd(berichtgegevens, Element.PERSOON_KIND, 1);
        assertNietGeautoriseerd(berichtgegevens, Element.PERSOON, 1);
    }


    private void assertGeautoriseerd(final Berichtgegevens berichtgegevens, final Element typeObject, final int aantal) {
        final Set<MetaObject> relatieObject = berichtgegevens.getPersoonslijst()
                .getModelIndex().geefObjecten(getObjectElement(typeObject.getId()));
        Assert.assertEquals(aantal, relatieObject.size());
        for (MetaObject metaObject : relatieObject) {
            final boolean objectGeautoriseerd = berichtgegevens.isGeautoriseerd(metaObject);
            Assert.assertTrue(objectGeautoriseerd);
        }
    }

    private void assertNietGeautoriseerd(final Berichtgegevens berichtgegevens, final Element typeObject, final int aantal) {
        final Set<MetaObject> relatieObject = berichtgegevens.getPersoonslijst()
                .getModelIndex().geefObjecten(getObjectElement(typeObject.getId()));
        Assert.assertEquals(aantal, relatieObject.size());
        for (MetaObject metaObject : relatieObject) {
            final boolean objectGeautoriseerd = berichtgegevens.isGeautoriseerd(metaObject);
            Assert.assertFalse(objectGeautoriseerd);
        }
    }

}
