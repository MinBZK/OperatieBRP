/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;
import static org.junit.Assert.assertTrue;

import java.time.ZonedDateTime;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.AutorisatieAlles;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import org.junit.Before;
import org.junit.Test;


/**
 */
public class MutatieleveringBehoudIdentificerendeGegevensServiceImplTest {

    private MutatieleveringBehoudIdentificerendeGegevensServiceImpl service = new MutatieleveringBehoudIdentificerendeGegevensServiceImpl();

    private ZonedDateTime nu;

    @Before
    public void beforeTest() {
        this.nu = DatumUtil.nuAlsZonedDateTime();
        BrpNu.set(nu);
    }

    @Test
    public void doeNietsBijGeenMutatieBericht() {
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.maakIngeschrevenPersoon().build(), 0L);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst, new MaakBerichtPersoonInformatie
                (SoortSynchronisatie.VOLLEDIG_BERICHT), null, new StatischePersoongegevens());
        service.execute(berichtgegevens);
    }

    @Test
    public void testBehoudIdentiteitOpBetrokkenheidBijLeveringObject() {
        final Actie actueleActie = TestVerantwoording.maakActie(500, nu);
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
                .metObject()
                    .metObjectElement(Element.PERSOON_PARTNER.getId())
                        .metGroep()
                            .metGroepElement(Element.PERSOON_PARTNER_IDENTITEIT)
                             .metRecord()
                                .metId(1)
                                .metActieInhoud(actueleActie)
                            .eindeRecord()
                        .eindeGroep()
                        .metObject()
                            .metObjectElement(Element.HUWELIJK.getId())
                                .metGroep()
                                    .metGroepElement(getGroepElement(Element.HUWELIJK_STANDAARD))
                                    .metRecord()
                                        .metId(1)
                                        .metActieInhoud(actueleActie)
                                    .eindeRecord()
                                .eindeGroep()
                                .metObject()
                                .metObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER.getId())
                                    .metGroep()
                                    .metGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_IDENTITEIT.getId())
                                         .metRecord()
                                            .metId(1)
                                            .metActieInhoud(actueleActie)
                                        .eindeRecord()
                                    .eindeGroep()
                                    .metObject()
                                        .metObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON.getId())
                                        .metGroep()
                                            .metGroepElement(getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE))
                                            .metRecord()
                                                .metId(1)
                                                .metActieInhoud(actueleActie)
                                            .eindeRecord()
                                        .eindeGroep()
                                    .eindeObject()
                                .eindeObject()
                        .eindeObject()
                .eindeObject()
        .eindeObject().build();
        //@formatter:on

        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.MUTATIE_BERICHT), null, new StatischePersoongegevens());

        new AutorisatieAlles(berichtgegevens);

        //zet ouderschap record in delta
        for (final MetaGroep metaGroep : persoonslijst.getModelIndex()
                .geefGroepenVanElement(getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE))) {
            for (final MetaRecord metaRecord : metaGroep.getRecords()) {
                berichtgegevens.addDeltaRecord(metaRecord);
            }
        }

        service.execute(berichtgegevens);

        final Set<MetaRecord> geautoriseerdeRecords = berichtgegevens.getGeautoriseerdeRecords();

        assertTrue(bevatGroep(geautoriseerdeRecords, Element.PERSOON_PARTNER_IDENTITEIT));
        assertTrue(bevatGroep(geautoriseerdeRecords, Element.GERELATEERDEHUWELIJKSPARTNER_IDENTITEIT));
    }

    @Test
    public void testBehoudIdentificerendeGroepOpHoofdpersoon() {

        final Actie actueleActie = TestVerantwoording.maakActie(500, nu);
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_IDENTITEIT))
                    .metRecord()
                        .metId(1)
                        .metAttribuut(getAttribuutElement(Element.PERSOON_SOORTCODE), SoortPersoon.INGESCHREVENE.getCode())
                    .eindeRecord()
                .eindeGroep()
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS))
                    .metRecord()
                        .metId(1)
                        .metActieInhoud(actueleActie)
                    .eindeRecord()
                     .metRecord()
                        .metId(2)
                        .metActieInhoud(actueleActie)
                    .eindeRecord()
                .eindeGroep()
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE))
                    .metRecord()
                        .metId(1)
                        .metActieInhoud(actueleActie)
                    .eindeRecord()
                    .metRecord()
                        .metId(2)
                        .metActieInhoud(actueleActie)
                    .eindeRecord()
                .eindeGroep()
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_GESLACHTSAANDUIDING))
                    .metRecord()
                        .metId(1)
                        .metActieInhoud(actueleActie)
                    .eindeRecord()
                    .metRecord()
                        .metId(2)
                        .metActieInhoud(actueleActie)
                    .eindeRecord()
                .eindeGroep()
               .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_SAMENGESTELDENAAM))
                    .metRecord()
                        .metId(1)
                        .metActieInhoud(actueleActie)
                    .eindeRecord()
                    .metRecord()
                        .metId(2)
                        .metActieInhoud(actueleActie)
                    .eindeRecord()
                .eindeGroep()
        .eindeObject().build();
        //@formatter:on

        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.MUTATIE_BERICHT), null, new StatischePersoongegevens());

        new AutorisatieAlles(berichtgegevens);

        service.execute(berichtgegevens);

        //niks zit in delta, maar identificerende groepen blijven behouden. Dus nog steeds geautoriseerd
        final Set<MetaRecord> geautoriseerdeRecords = berichtgegevens.getGeautoriseerdeRecords();

        assertTrue(bevatGroep(geautoriseerdeRecords, Element.PERSOON_IDENTITEIT));
        assertTrue(bevatGroep(geautoriseerdeRecords, Element.PERSOON_GEBOORTE));
        assertTrue(bevatGroep(geautoriseerdeRecords, Element.PERSOON_GESLACHTSAANDUIDING));
        assertTrue(bevatGroep(geautoriseerdeRecords, Element.PERSOON_IDENTIFICATIENUMMERS));
        assertTrue(bevatGroep(geautoriseerdeRecords, Element.PERSOON_SAMENGESTELDENAAM));
    }

    private boolean bevatGroep(final Set<MetaRecord> geautoriseerdeRecords, final Element groepElement) {
        for (MetaRecord geautoriseerdeRecord : geautoriseerdeRecords) {
            if (geautoriseerdeRecord.getParentGroep().getGroepElement().getId() == groepElement.getId()) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void testBehoudIdentificerendeGroepOpBetrokkenPersoonDoorRecordInDelta() {

        final Actie actueleActie = TestVerantwoording.maakActie(500, nu);
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
                .metGroep()
                .metGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId())
                    .metRecord().metActieInhoud(actueleActie).eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(getObjectElement(Element.PERSOON_KIND))
                    .metObject()
                        .metObjectElement(getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING))
                        .metObject()
                            .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER))
                            .metGroep()
                                .metGroepElement(getGroepElement(Element.GERELATEERDEOUDER_OUDERSCHAP))
                                .metRecord()
                                    .metId(1)
                                    .metActieInhoud(actueleActie)
                                .eindeRecord()
                            .eindeGroep()
                            .metObject()
                                .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER_PERSOON))
                                .metGroep()
                                    .metGroepElement(getGroepElement(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE))
                                        .metRecord()
                                            .metId(999)
                                             .metActieInhoud(actueleActie)
                                        .eindeRecord()
                                .eindeGroep()
                                .metGroep()
                                    .metGroepElement(getGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS))
                                        .metRecord()
                                            .metId(999)
                                             .metActieInhoud(actueleActie)
                                        .eindeRecord()
                                .eindeGroep()
                                .metGroep()
                                    .metGroepElement(getGroepElement(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM))
                                        .metRecord()
                                            .metId(999)
                                             .metActieInhoud(actueleActie)
                                        .eindeRecord()
                                .eindeGroep()
                                .metGroep()
                                    .metGroepElement(getGroepElement(Element.GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING))
                                        .metRecord()
                                            .metId(999)
                                             .metActieInhoud(actueleActie)
                                        .eindeRecord()
                                .eindeGroep()
                            .eindeObject()
                        .eindeObject()
                    .eindeObject()
                .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on

        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.MUTATIE_BERICHT), null, new StatischePersoongegevens());

        new AutorisatieAlles(berichtgegevens);

        //zet ouderschap record in delta
        for (final MetaGroep metaGroep : persoonslijst.getModelIndex().geefGroepenVanElement(getGroepElement(Element.GERELATEERDEOUDER_OUDERSCHAP))) {
            for (final MetaRecord metaRecord : metaGroep.getRecords()) {
                berichtgegevens.addDeltaRecord(metaRecord);
            }
        }

        service.execute(berichtgegevens);

        for (final MetaGroep metaGroep : persoonslijst.getModelIndex().geefGroepen()) {
            for (final MetaRecord metaRecord : metaGroep.getRecords()) {
                if (999 == metaRecord.getVoorkomensleutel()) {
                    assertTrue(berichtgegevens.isGeautoriseerd(metaRecord));
                }
            }
        }
    }
}
