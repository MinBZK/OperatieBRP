/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.bepaling;

import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.google.common.collect.Iterables;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingssoort;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaModel;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ModelIndex;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Test;

/**
 * Unit test voor {@link MutatieleveringVerwerkingssoortServiceImpl}.
 */
public class MutatieleveringVerwerkingssoortServiceImplTest {

    private final Actie actieLaatste = TestVerantwoording.maakActie(1, DatumUtil.nuAlsZonedDateTime());
    private final Actie actieVorigeHandeling = TestVerantwoording.maakActie(2, DatumUtil.nuAlsZonedDateTime().minusYears(1));
    private final Actie actieNietInDelta = TestVerantwoording.maakActie(3, DatumUtil.nuAlsZonedDateTime().minusYears(3));

    private final MutatieleveringVerwerkingssoortServiceImpl service = new MutatieleveringVerwerkingssoortServiceImpl();

    @Test
    public void bepaaltWijzigingCorrect() throws Exception {
        // @formatter:off
        final MetaObject object = MetaObject.maakBuilder()
            .metId(1)
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_MIGRATIE.getId()))
                .metRecord()
                    .metId(1)
                    .metActieInhoud(actieVorigeHandeling)
                    .metActieAanpassingGeldigheid(actieLaatste)
                .eindeRecord()
                .metRecord()
                    .metId(2)
                    .metActieInhoud(actieVorigeHandeling)
                    .metActieVerval(actieLaatste)
                .eindeRecord()
            .eindeGroep()
        .build();
        // @formatter:on

        final Persoonslijst persoonslijst = new Persoonslijst(object, 0L);

        final Map<MetaModel, Verwerkingssoort> verwerkingssoortMap = service.execute(persoonslijst);

        final Set<MetaRecord> records = Iterables.getOnlyElement(object.getGroepen()).getRecords();
        for (final MetaRecord record : records) {
            if (record.getVoorkomensleutel() == 1L) {
                assertThat(verwerkingssoortMap.get(record), is(Verwerkingssoort.WIJZIGING));
            }
            if (record.getVoorkomensleutel() == 2L) {
                assertThat(verwerkingssoortMap.get(record), is(Verwerkingssoort.VERVAL));
            }
        }
        assertThat(verwerkingssoortMap.get(object), is(Verwerkingssoort.WIJZIGING));
    }

    @Test
    public void bepaaltToevoegingCorrect() throws Exception {
        // @formatter:off
        final MetaObject object = MetaObject.maakBuilder()
            .metId(1)
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE.getId()))
                .metRecord()
                    .metId(1)
                    .metActieInhoud(actieLaatste)
                .eindeRecord()
            .eindeGroep()
        .build();
        // @formatter:on

        final Persoonslijst persoonslijst = new Persoonslijst(object, 0L);

        final Map<MetaModel, Verwerkingssoort> verwerkingssoortMap = service.execute(persoonslijst);

        final Set<MetaRecord> records = Iterables.getOnlyElement(object.getGroepen()).getRecords();
        for (final MetaRecord record : records) {
            if (record.getVoorkomensleutel() == 1L) {
                assertThat(verwerkingssoortMap.get(record), is(Verwerkingssoort.TOEVOEGING));
            }
        }
        assertThat(verwerkingssoortMap.get(object), is(Verwerkingssoort.TOEVOEGING));
    }

    @Test
    public void bepaaltWijzigingObjectCorrectBijToevoegingEnWijziging() throws Exception {
        // @formatter:off
        final MetaObject object = TestBuilders.maakLeegPersoon()
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_MIGRATIE.getId()))
                .metRecord()
                    .metId(1)
                    .metActieInhoud(actieLaatste)
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE.getId()))
                .metRecord()
                    .metId(2)
                    .metActieInhoud(actieLaatste)
                    .metActieInhoud(actieVorigeHandeling)
                .eindeRecord()
            .eindeGroep()
        .build();
        // @formatter:on

        final Persoonslijst persoonslijst = new Persoonslijst(object, 0L);

        final Map<MetaModel, Verwerkingssoort> verwerkingssoortMap = service.execute(persoonslijst);

        Set<MetaRecord> records = Iterables.getFirst(object.getGroepen(), null).getRecords();
        for (final MetaRecord record : records) {
            if (record.getVoorkomensleutel() == 1L) {
                assertThat(verwerkingssoortMap.get(record), is(Verwerkingssoort.TOEVOEGING));
            }
        }
        records = Iterables.getLast(object.getGroepen()).getRecords();
        for (final MetaRecord record : records) {
            if (record.getVoorkomensleutel() == 2L) {
                assertThat(verwerkingssoortMap.get(record), is(Verwerkingssoort.REFERENTIE));
            }
        }
        assertThat(verwerkingssoortMap.get(object), is(Verwerkingssoort.WIJZIGING));
    }

    //    @Test
//    public void bepaalIdentificatieObjectCorrectBijIdentificatieEnReferentie() throws Exception {
//        // @formatter:off
//        final MetaObject object = TestBuilders.maakLeegPersoon()
//            .metGroep()
//                .metGroepElement(getGroepElement(Element.PERSOON_MIGRATIE.getId()))
//                .metRecord()
//                    .metId(1)
//                    .metActieInhoud(ACTIE_INHOUD_1)
//                    .metActieAanpassingGeldigheid(ACTIE_AANPASSING_GELDIGHEID_1)
//                .eindeRecord()
//            .eindeGroep()
//            .metGroep()
//                .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE.getId()))
//                .metRecord()
//                    .metId(2)
//                    .metActieInhoud(ACTIE_INHOUD_1)
//                .eindeRecord()
//            .eindeGroep()
//        .build();
//        // @formatter:on
//
//        final Persoonslijst persoonslijst = new Persoonslijst(object);
//        final Map<MetaModel, Verwerkingssoort> verwerkingssoortMap = service.execute(persoonslijst, ACTIE_VERVAL_1.getAdministratieveHandeling());
//
//        assertThat(verwerkingssoortMap.get(object), is(Verwerkingssoort.IDENTIFICATIE));
//    }
//
    @Test
    public void bepaaltVervalCorrect() {
        // @formatter:off
        final MetaObject object = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON.getId())
            .metId(1)
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId()))
                .metRecord().metActieInhoud(actieLaatste).eindeRecord()
                .metRecord().metActieInhoud(actieVorigeHandeling).metActieInhoud(actieLaatste).eindeRecord()
            .eindeGroep()
            .metObject()
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))
                    .metRecord()
                        .metId(1)
                        .metActieInhoud(actieNietInDelta)
                        .metActieVerval(actieLaatste)
                    .eindeRecord()
                    .metRecord()
                        .metId(2)
                        .metActieInhoud(actieNietInDelta)
                        .metActieVerval(actieNietInDelta)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        // @formatter:on

        final Persoonslijst persoonslijst = new Persoonslijst(object, 0L);

        final Map<MetaModel, Verwerkingssoort> verwerkingssoortMap = service.execute(persoonslijst);

        final ModelIndex modelIndex = persoonslijst.getModelIndex();
        final Set<MetaRecord> records = Iterables.getOnlyElement(modelIndex.
                geefGroepenVanElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD))).getRecords();
        for (final MetaRecord record : records) {
            if (record.getVoorkomensleutel() == 1L) {
                assertThat(verwerkingssoortMap.get(record), is(Verwerkingssoort.VERVAL));
            }
            if (record.getVoorkomensleutel() == 2L) {
                assertThat(verwerkingssoortMap.get(record), is(Verwerkingssoort.REFERENTIE));
            }
        }

        assertThat(verwerkingssoortMap.get(records.iterator().next().getParentGroep().getParentObject()), is(Verwerkingssoort.VERVAL));

        MetaObject adres = Iterables.getOnlyElement(modelIndex.geefObjecten(getObjectElement(Element.PERSOON_ADRES)));
        assertThat(verwerkingssoortMap.get(adres), is(Verwerkingssoort.VERVAL));
    }

    @Test
    public void bepaaltWijzigingCorrectMetActieVervalTbvLevMut() throws Exception {
        // @formatter:off
        final MetaObject object = MetaObject.maakBuilder()
            .metId(1)
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId()))
                .metRecord().metActieInhoud(actieLaatste).eindeRecord()
                .metRecord().metActieInhoud(actieVorigeHandeling).metActieInhoud(actieLaatste).eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_MIGRATIE.getId()))
                .metRecord()
                    .metId(1)
                    .metActieInhoud(actieNietInDelta)
                    .metActieVervalTbvLeveringMutaties(actieLaatste)
                .eindeRecord()
            .eindeGroep()
        .build();
        // @formatter:on

        final Persoonslijst persoonslijst = new Persoonslijst(object, 0L);

        final Map<MetaModel, Verwerkingssoort> verwerkingssoortMap = service.execute(persoonslijst);

        final Set<MetaRecord> records = object.getGroep(getGroepElement(Element.PERSOON_MIGRATIE)).getRecords();
        for (final MetaRecord record : records) {
            if (record.getVoorkomensleutel() == 1L) {
                assertThat(verwerkingssoortMap.get(record), is(Verwerkingssoort.VERVAL));
            }
            if (record.getVoorkomensleutel() == 2L) {
                assertThat(verwerkingssoortMap.get(record), is(Verwerkingssoort.REFERENTIE));
            }
        }
        assertThat(verwerkingssoortMap.get(object), is(Verwerkingssoort.WIJZIGING));
    }

    @Test
    public void bepaaltReferentieCorrect() {

        // @formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON.getId())
            .metId(1)
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId()))
                .metRecord().metActieAanpassingGeldigheid(actieLaatste).eindeRecord()
                .metRecord().metActieAanpassingGeldigheid(actieVorigeHandeling).eindeRecord()
            .eindeGroep()
            .metObject()
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))
                    .metRecord()
                        .metId(1)
                        .metActieInhoud(actieNietInDelta)
                        .metActieVerval(actieNietInDelta)
                    .eindeRecord()
                    .metRecord()
                        .metId(2)
                        .metActieInhoud(actieNietInDelta)
                        .metActieVerval(actieNietInDelta)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        // @formatter:on

        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        final Map<MetaModel, Verwerkingssoort> verwerkingssoortMap = service.execute(persoonslijst);
        final MetaGroep adresGroep = persoonslijst.getModelIndex().
                geefGroepenVanElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD)).iterator().next();
        for (final MetaRecord record : adresGroep.getRecords()) {
            if (record.getVoorkomensleutel() == 1L) {
                assertThat(verwerkingssoortMap.get(record), is(Verwerkingssoort.REFERENTIE));
            }
            if (record.getVoorkomensleutel() == 2L) {
                assertThat(verwerkingssoortMap.get(record), is(Verwerkingssoort.REFERENTIE));
            }
        }
        assertThat(verwerkingssoortMap.get(adresGroep.getParentObject()), is(Verwerkingssoort.REFERENTIE));
        assertThat(verwerkingssoortMap.get(persoon), is(Verwerkingssoort.WIJZIGING));
    }

    @Test
    public void bepaaltIdentificatieCorrect() {

        // @formatter:off
        final MetaObject object = MetaObject.maakBuilder()
            .metId(1)
            .metObjectElement(Element.PERSOON)
            .metObject()
                //dit object is puur voor het linken van de delta handelingen
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                    .metRecord().metActieInhoud(actieLaatste).eindeRecord()
                    .metRecord().metActieInhoud(actieVorigeHandeling).metActieVerval(actieLaatste).eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId()))
                .metRecord()
                    .metId(1)
                    .metActieInhoud(actieNietInDelta)
                .eindeRecord()
                .metRecord()
                    .metId(2)
                    .metActieInhoud(actieNietInDelta)
                .eindeRecord()
                .metRecord()
                    .metId(3)
                    .metActieInhoud(actieNietInDelta)
                    .metDatumEindeGeldigheid(DatumUtil.vandaag())
                .eindeRecord()
            .eindeGroep()
        .build();
        // @formatter:on

        final Persoonslijst persoonslijst = new Persoonslijst(object, 0L);
        final Map<MetaModel, Verwerkingssoort> verwerkingssoortMap = service.execute(persoonslijst);

        final Set<MetaGroep> metaGroeps = persoonslijst.getModelIndex().geefGroepenVanElement(getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId()));
        final Set<MetaRecord> records = Iterables.getOnlyElement(metaGroeps).getRecords();

        for (final MetaRecord record : records) {
            if (record.getVoorkomensleutel() == 1L) {
                assertThat(verwerkingssoortMap.get(record), is(Verwerkingssoort.IDENTIFICATIE));
            }
            if (record.getVoorkomensleutel() == 2L) {
                assertThat(verwerkingssoortMap.get(record), is(Verwerkingssoort.IDENTIFICATIE));
            }
            if (record.getVoorkomensleutel() == 3L) {
                assertThat(verwerkingssoortMap.get(record), is(Verwerkingssoort.REFERENTIE));
            }
        }
        assertThat(verwerkingssoortMap.get(object), is(Verwerkingssoort.IDENTIFICATIE));
    }


    @Test
    public void bepaaltWijzigingVoorObjectCorrectDrieVerschillendeGroepVerwerkingssoorten() {
        // @formatter:off
        final MetaObject object = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON.getId())
            .metId(1)
            .metObject()
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))
                    .metRecord()
                        .metId(1)
                        .metActieInhoud(actieLaatste)
                        .metActieAanpassingGeldigheid(actieLaatste)
                    .eindeRecord()
                    .metRecord()
                        .metId(2)
                        .metActieInhoud(actieVorigeHandeling)
                        .metActieVerval(actieLaatste)
                    .eindeRecord()
                .eindeGroep()
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_ADRES_IDENTITEIT.getId()))
                    .metRecord()
                        .metId(3)
                    .eindeRecord()
                    .metRecord()
                        .metId(4)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        // @formatter:on

        final Persoonslijst persoonslijst = new Persoonslijst(object, 0L);

        final Map<MetaModel, Verwerkingssoort> verwerkingssoortMap = service.execute(persoonslijst);

        assertThat(verwerkingssoortMap.get(object), is(Verwerkingssoort.WIJZIGING));
    }

    @Test
    public void bepaaltObjectInObjectVerwerkingssoortCorrect() {
        // @formatter:off
        final MetaObject persoonObject = MetaObject.maakBuilder()
            .metId(1)
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId()))
                .metRecord().metActieInhoud(actieLaatste).eindeRecord()
                .metRecord().metActieInhoud(actieVorigeHandeling).metActieInhoud(actieLaatste).eindeRecord()
            .eindeGroep()
            .metObject()
                .metId(2)
                .metObjectElement(getObjectElement(Element.PERSOON_ADRES.getId()))
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))
                    .metRecord()
                        .metId(3)
                        .metActieVerval(actieLaatste)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        // @formatter:on

        final Persoonslijst persoonslijst = new Persoonslijst(persoonObject, 0L);
        final Map<MetaModel, Verwerkingssoort> verwerkingssoortMap = service.execute(persoonslijst);

        final MetaObject adres = Iterables.getOnlyElement(persoonslijst.getModelIndex()
                .geefObjecten(getObjectElement(Element.PERSOON_ADRES.getId())));
        assertThat(verwerkingssoortMap.get(adres), is(Verwerkingssoort.VERVAL));

        assertThat(verwerkingssoortMap.get(persoonObject), is(Verwerkingssoort.TOEVOEGING));
    }

}
