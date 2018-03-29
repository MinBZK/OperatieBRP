/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;


import static nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie.MUTATIE_BERICHT;
import static nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie.VOLLEDIG_BERICHT;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;

import com.google.common.collect.Lists;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
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
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class MetaRecordAutorisatiePredicateTest {

    private Actie actieInhoud = TestVerantwoording.maakActie(1, DatumUtil.nuAlsZonedDateTime());

    @Test
    public void actueelFormeelRecord() {

        final Element typeGroep = Element.PERSOON_GEBOORTE;
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_IDENTITEIT.getId()))
                .metRecord()
                    .metId(1)
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE.getId()))
                .metRecord()
                    .metId(1)
                    .metActieInhoud(actieInhoud)
                .eindeRecord()
            .eindeGroep()
        .build();
        //@formatter:on

        //geen groep
        final Dienst geen = geefDienstZonderGroepen();
        assertGeautoriseerd(geen, persoon, typeGroep, MUTATIE_BERICHT);
        assertGeautoriseerd(geen, persoon, typeGroep, VOLLEDIG_BERICHT);

        //formeel + materieel
        final Dienst beide = geefDienstMetGroep(typeGroep, true, true);
        assertGeautoriseerd(beide, persoon, typeGroep, MUTATIE_BERICHT);
        assertGeautoriseerd(beide, persoon, typeGroep, VOLLEDIG_BERICHT);

        //alleen formeel
        final Dienst formeel = geefDienstMetGroep(typeGroep, true, false);
        assertGeautoriseerd(formeel, persoon, typeGroep, MUTATIE_BERICHT);
        assertGeautoriseerd(formeel, persoon, typeGroep, VOLLEDIG_BERICHT);

        //alleen materieel
        final Dienst materieel = geefDienstMetGroep(typeGroep, false, true);
        assertGeautoriseerd(materieel, persoon, typeGroep, MUTATIE_BERICHT);
        assertGeautoriseerd(materieel, persoon, typeGroep, VOLLEDIG_BERICHT);
    }

    @Test
    public void actueelFormeelRecord_ElementNietInDienstbundel() {

        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE.getId()))
                .metRecord()
                    .metId(1)
                    .metActieInhoud(actieInhoud)
                .eindeRecord()
            .eindeGroep()
        .build();
        //@formatter:on

        //formeel + materieel
        final Dienst beideAnderElement = geefDienstMetGroep(Element.PERSOON_ADRES_STANDAARD, true, true);
        assertGeautoriseerd(beideAnderElement, persoon, Element.PERSOON_GEBOORTE, MUTATIE_BERICHT);
        assertGeautoriseerd(beideAnderElement, persoon, Element.PERSOON_GEBOORTE, VOLLEDIG_BERICHT);
    }


    @Test
    public void vervallenFormeelRecord() {

        final Element typeGroep = Element.PERSOON_GEBOORTE;
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(getGroepElement(typeGroep.getId()))
                .metRecord()
                    .metId(1)
                    .metActieInhoud(actieInhoud)
                    .metActieVerval(TestVerantwoording.maakActie(1))
                .eindeRecord()
            .eindeGroep()
        .build();
        //@formatter:on

        //geen groep
        final Dienst geen = geefDienstZonderGroepen();
        assertGeautoriseerd(geen, persoon, typeGroep, MUTATIE_BERICHT);
        assertNietGeautoriseerd(geen, persoon, typeGroep, VOLLEDIG_BERICHT);

        //formeel + materieel
        final Dienst beide = geefDienstMetGroep(typeGroep, true, true);
        assertGeautoriseerd(beide, persoon, typeGroep, MUTATIE_BERICHT);
        assertGeautoriseerd(beide, persoon, typeGroep, VOLLEDIG_BERICHT);

        //alleen formeel
        final Dienst formeel = geefDienstMetGroep(typeGroep, true, false);
        assertGeautoriseerd(formeel, persoon, typeGroep, MUTATIE_BERICHT);
        assertGeautoriseerd(formeel, persoon, typeGroep, VOLLEDIG_BERICHT);

        //alleen materieel
        final Dienst materieel = geefDienstMetGroep(typeGroep, false, true);
        assertGeautoriseerd(materieel, persoon, typeGroep, MUTATIE_BERICHT);
        assertNietGeautoriseerd(materieel, persoon, typeGroep, VOLLEDIG_BERICHT);
    }


    @Test
    public void actueelMaterieelRecord() {

        final Element typeGroep = Element.PERSOON_IDENTIFICATIENUMMERS;
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(getGroepElement(typeGroep.getId()))
                .metRecord()
                    .metId(1)
                    .metActieInhoud(actieInhoud)
                .eindeRecord()
            .eindeGroep()
        .build();
        //@formatter:on

        //geen groep
        final Dienst dienstZonderGroepen = geefDienstZonderGroepen();
        assertGeautoriseerd(dienstZonderGroepen, persoon, typeGroep, MUTATIE_BERICHT);
        assertGeautoriseerd(dienstZonderGroepen, persoon, typeGroep, VOLLEDIG_BERICHT);

        //groep formele vlag true
        final Dienst dienstFormeelMaterieel = geefDienstMetGroep(typeGroep, true, true);
        assertGeautoriseerd(dienstFormeelMaterieel, persoon, typeGroep, MUTATIE_BERICHT);
        assertGeautoriseerd(dienstFormeelMaterieel, persoon, typeGroep, VOLLEDIG_BERICHT);

        //groep formeel vlag false
        final Dienst formeel = geefDienstMetGroep(typeGroep, true, false);
        assertGeautoriseerd(formeel, persoon, typeGroep, MUTATIE_BERICHT);
        assertGeautoriseerd(formeel, persoon, typeGroep, VOLLEDIG_BERICHT);

        //groep formeel vlag false
        final Dienst dienstMateriel = geefDienstMetGroep(typeGroep, false, true);
        assertGeautoriseerd(dienstMateriel, persoon, typeGroep, MUTATIE_BERICHT);
        assertGeautoriseerd(dienstMateriel, persoon, typeGroep, VOLLEDIG_BERICHT);

        //groep materiele vlag false
        final Dienst geenHistorie = geefDienstMetGroep(typeGroep, false, false);
        assertGeautoriseerd(geenHistorie, persoon, typeGroep, MUTATIE_BERICHT);
        assertGeautoriseerd(geenHistorie, persoon, typeGroep, VOLLEDIG_BERICHT);
    }

    @Test
    public void vervallenMaterieelRecord() {

        final Element typeGroep = Element.PERSOON_IDENTIFICATIENUMMERS;
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(getGroepElement(typeGroep.getId()))
                .metRecord()
                    .metId(1)
                    .metActieInhoud(actieInhoud)
                    .metActieVerval(TestVerantwoording.maakActie(1))
                .eindeRecord()
            .eindeGroep()
        .build();
        //@formatter:on

        //groep formele vlag true
        final Dienst dienstFormeelMaterieel = geefDienstMetGroep(typeGroep, true, true);
        assertGeautoriseerd(dienstFormeelMaterieel, persoon, typeGroep, MUTATIE_BERICHT);
        assertGeautoriseerd(dienstFormeelMaterieel, persoon, typeGroep, VOLLEDIG_BERICHT);

        //groep formeel vlag false
        final Dienst formeel = geefDienstMetGroep(typeGroep, true, false);
        assertGeautoriseerd(formeel, persoon, typeGroep, MUTATIE_BERICHT);
        assertGeautoriseerd(formeel, persoon, typeGroep, VOLLEDIG_BERICHT);

        //groep formeel vlag false
        final Dienst dienstMateriel = geefDienstMetGroep(typeGroep, false, true);
        assertGeautoriseerd(dienstMateriel, persoon, typeGroep, MUTATIE_BERICHT);
        assertNietGeautoriseerd(dienstMateriel, persoon, typeGroep, VOLLEDIG_BERICHT);

        //groep materiele vlag false
        final Dienst geenHistorie = geefDienstMetGroep(typeGroep, false, false);
        assertGeautoriseerd(geenHistorie, persoon, typeGroep, MUTATIE_BERICHT);
        assertNietGeautoriseerd(geenHistorie, persoon, typeGroep, VOLLEDIG_BERICHT);

    }

    @Test
    public void beeindigdMaterieelRecord() {
        final Element typeGroep = Element.PERSOON_IDENTIFICATIENUMMERS;
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(getGroepElement(typeGroep.getId()))
                .metRecord()
                    .metId(1)
                    .metActieInhoud(actieInhoud)
                    .metDatumEindeGeldigheid(20100101)
                .eindeRecord()
            .eindeGroep()
        .build();
        //@formatter:on

        //groep formele vlag true
        final Dienst beide = geefDienstMetGroep(typeGroep, true, true);
        assertGeautoriseerd(beide, persoon, typeGroep, MUTATIE_BERICHT);
        assertGeautoriseerd(beide, persoon, typeGroep, VOLLEDIG_BERICHT);

        //groep formeel vlag false
        final Dienst formeel = geefDienstMetGroep(typeGroep, true, false);
        assertNietGeautoriseerd(formeel, persoon, typeGroep, MUTATIE_BERICHT);
        assertNietGeautoriseerd(formeel, persoon, typeGroep, VOLLEDIG_BERICHT);

        //groep formeel vlag false
        final Dienst materieel = geefDienstMetGroep(typeGroep, false, true);
        assertGeautoriseerd(materieel, persoon, typeGroep, MUTATIE_BERICHT);
        assertGeautoriseerd(materieel, persoon, typeGroep, VOLLEDIG_BERICHT);

        //groep materiele vlag false
        final Dienst geen = geefDienstMetGroep(typeGroep, false, false);
        assertNietGeautoriseerd(geen, persoon, typeGroep, MUTATIE_BERICHT);
        assertNietGeautoriseerd(geen, persoon, typeGroep, VOLLEDIG_BERICHT);
    }

    @Test
    public void nietBeeindigdMaterieelRecord() {
        final Element typeGroep = Element.PERSOON_IDENTIFICATIENUMMERS;
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(getGroepElement(typeGroep.getId()))
                .metRecord()
                    .metId(1)
                    .metActieInhoud(actieInhoud)
                .eindeRecord()
            .eindeGroep()
        .build();
        //@formatter:on

        //groep formele vlag true
        final Dienst beide = geefDienstMetGroep(typeGroep, true, true);
        assertGeautoriseerd(beide, persoon, typeGroep, MUTATIE_BERICHT);
        assertGeautoriseerd(beide, persoon, typeGroep, VOLLEDIG_BERICHT);

        //groep formeel vlag false
        final Dienst formeel = geefDienstMetGroep(typeGroep, true, false);
        assertGeautoriseerd(formeel, persoon, typeGroep, MUTATIE_BERICHT);
        assertGeautoriseerd(formeel, persoon, typeGroep, VOLLEDIG_BERICHT);

        //groep formeel vlag false
        final Dienst materieel = geefDienstMetGroep(typeGroep, false, true);
        assertGeautoriseerd(materieel, persoon, typeGroep, MUTATIE_BERICHT);
        assertGeautoriseerd(materieel, persoon, typeGroep, VOLLEDIG_BERICHT);

        //groep materiele vlag false
        final Dienst geen = geefDienstMetGroep(typeGroep, false, false);
        assertGeautoriseerd(geen, persoon, typeGroep, MUTATIE_BERICHT);
        assertGeautoriseerd(geen, persoon, typeGroep, VOLLEDIG_BERICHT);
    }

    @Test
    public void geenHistorieRecord() {

        final Element typeGroep = Element.PERSOON_IDENTITEIT;
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(getGroepElement(typeGroep.getId()))
                .metRecord()
                    .metId(1)
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId())
                .metRecord().metActieInhoud(TestVerantwoording.maakActie(1)).eindeRecord()
            .eindeGroep()
        .build();
        //@formatter:on

        //geen groep
        final Dienst geen = geefDienstZonderGroepen();
        assertGeautoriseerd(geen, persoon, Element.PERSOON_IDENTITEIT, MUTATIE_BERICHT);
        assertGeautoriseerd(geen, persoon, Element.PERSOON_IDENTITEIT, VOLLEDIG_BERICHT);

        //formeel + materieel
        final Dienst beide = geefDienstMetGroep(typeGroep, true, true);
        assertGeautoriseerd(geen, persoon, Element.PERSOON_IDENTITEIT, MUTATIE_BERICHT);
        assertGeautoriseerd(geen, persoon, Element.PERSOON_IDENTITEIT, VOLLEDIG_BERICHT);

        //alleen formeel
        final Dienst formeel = geefDienstMetGroep(typeGroep, true, false);
        assertGeautoriseerd(geen, persoon, Element.PERSOON_IDENTITEIT, MUTATIE_BERICHT);
        assertGeautoriseerd(geen, persoon, Element.PERSOON_IDENTITEIT, VOLLEDIG_BERICHT);

        //alleen materieel
        final Dienst materieel = geefDienstMetGroep(typeGroep, false, true);
        assertGeautoriseerd(geen, persoon, Element.PERSOON_IDENTITEIT, MUTATIE_BERICHT);
        assertGeautoriseerd(geen, persoon, Element.PERSOON_IDENTITEIT, VOLLEDIG_BERICHT);
    }

    @Test(expected = IllegalStateException.class)
    public void testRecordIsNull() {
        final MetaObject persoon = TestBuilders.maakLeegPersoon(1).build();
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(null, geefDienstZonderGroepen());
        maakBerichtParameters.setAutorisatiebundels(Lists.newArrayList(autorisatiebundel));
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT), autorisatiebundel, new StatischePersoongegevens());

        new MetaRecordAutorisatiePredicate(berichtgegevens).test(null);
    }


    private Dienst geefDienstZonderGroepen() {
        return AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.ATTENDERING), SoortDienst.ATTENDERING);
    }


    private Dienst geefDienstMetGroep(Element element, boolean formeel, boolean materieel) {

        final TestAutorisaties.GroepDefinitie groepDefinitie = new TestAutorisaties.GroepDefinitie();
        groepDefinitie.indicatieFormeleHistorie = formeel;
        groepDefinitie.indicatieMaterieleHistorie = materieel;
        groepDefinitie.element = element;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.maakLeveringsautorisatie(null, SoortDienst.ATTENDERING, groepDefinitie);
        return AutAutUtil.zoekDienst(leveringsautorisatie, SoortDienst.ATTENDERING);
    }

    private void assertGeautoriseerd(final Dienst dienst, final MetaObject persoon, final Element element, final SoortSynchronisatie
            soortSynchronisatie) {

        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(null, dienst);
        maakBerichtParameters.setAutorisatiebundels(Lists.newArrayList(autorisatiebundel));
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(soortSynchronisatie), autorisatiebundel, new StatischePersoongegevens());

        final MetaRecord record = persoon.getGroep(getGroepElement(element.getId())).getRecords().iterator().next();
        Assert.assertTrue(new MetaRecordAutorisatiePredicate(berichtgegevens).test(record));
    }

    private void assertNietGeautoriseerd(final Dienst dienst, final MetaObject persoon, final Element element, final SoortSynchronisatie
            soortSynchronisatie) {
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(null, dienst);
        maakBerichtParameters.setAutorisatiebundels(Lists.newArrayList(autorisatiebundel));
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(soortSynchronisatie), autorisatiebundel, new StatischePersoongegevens());

        final MetaRecord record = persoon.getGroep(getGroepElement(element.getId())).getRecords().iterator().next();
        Assert.assertFalse(new MetaRecordAutorisatiePredicate(berichtgegevens).test(record));
    }
}
