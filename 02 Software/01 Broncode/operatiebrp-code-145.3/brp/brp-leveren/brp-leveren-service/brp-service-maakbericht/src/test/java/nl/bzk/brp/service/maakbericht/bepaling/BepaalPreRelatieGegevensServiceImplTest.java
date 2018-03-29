/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.bepaling;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import com.google.common.collect.Lists;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ParentFirstModelVisitor;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestDatumUtil;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * FilterPreRelatiesServiceTest.
 */
public class BepaalPreRelatieGegevensServiceImplTest {

    private BepaalPreRelatieGegevensService service = new BepaalPreRelatieGegevensServiceImpl();

    @Test
    public void testPrerelatieNietTonenOuderschapHoofdpersoon() {
        final GroepElement groepElementSamenGesteldeNaam = getGroepElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM);
        final GroepElement groepElementGeboorte = getGroepElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE);

        final Integer datumAanvangRelatie = DatumUtil.vandaag();
        final Integer datumEindeGeldigheidRecord = DatumUtil.gisteren();
        final ZonedDateTime datumTijdvervalRecord = null;

        final MetaObject metaObject = maakPersoonMetOuderBetrokkenHeidMetGerelateerdKind(datumAanvangRelatie, datumEindeGeldigheidRecord,
                datumTijdvervalRecord, TestDatumUtil.gisteren(), TestDatumUtil.gisteren(), false);

        final List<MetaRecord> records = new ArrayList<>();
        new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(final MetaRecord record) {
                if (record.getParentGroep().getGroepElement().equals(groepElementSamenGesteldeNaam) || record.getParentGroep().getGroepElement().equals
                        (groepElementGeboorte)) {
                    records.add(record);
                }
            }
        }.visit(metaObject);

        final Berichtgegevens berichtgegevens = maakBerichtGegevens(metaObject, Rol.AFNEMER);
        final Set<MetaRecord> prerelatieRecords = service.bepaal(berichtgegevens.getPersoonslijst());

        Assert.assertTrue(prerelatieRecords.size() > 0);
        for (MetaRecord record : records) {
            Assert.assertTrue(prerelatieRecords.contains(record));
        }
    }

    @Test
    public void testPrerelatieWelTonenGeenDatumEindeGeldigheidVervallenOuderschapMaarVervalNietNaTsRegBetrokkenheidIndMutLev() {
        //indicatie mut lev records zijn uitgesloten van prerelatie filtering bij tsverval vergelijking
        final GroepElement groepElementSamenGesteldeNaam = getGroepElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM);
        final GroepElement groepElementGeboorte = getGroepElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE);

        final Integer datumAanvangRelatie = DatumUtil.vandaag();
        final Integer datumEindeGeldigheidRecord = null;
        final ZonedDateTime eergisteren = DatumUtil.nuAlsZonedDateTime().minus(2, ChronoUnit.DAYS);
        final ZonedDateTime eereergisteren = DatumUtil.nuAlsZonedDateTime().minus(3, ChronoUnit.DAYS);
        final ZonedDateTime datumTijdvervalRecord = eergisteren;

        final MetaObject metaObject = maakPersoonMetOuderBetrokkenHeidMetGerelateerdKind(datumAanvangRelatie, datumEindeGeldigheidRecord,
                datumTijdvervalRecord, datumTijdvervalRecord, eereergisteren, true);

        final List<MetaRecord> records = new ArrayList<>();
        new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(final MetaRecord record) {
                if (record.getParentGroep().getGroepElement().equals(groepElementSamenGesteldeNaam) || record.getParentGroep().getGroepElement().equals
                        (groepElementGeboorte)) {
                    records.add(record);
                }
            }
        }.visit(metaObject);

        final Berichtgegevens berichtgegevens = maakBerichtGegevens(metaObject, Rol.AFNEMER);
        final Set<MetaRecord> prerelatieRecords = service.bepaal(berichtgegevens.getPersoonslijst());
        Assert.assertTrue(prerelatieRecords.isEmpty());
        for (MetaRecord record : records) {
            Assert.assertFalse(prerelatieRecords.contains(record));
        }
    }

    @Test
    public void testPrerelatieWelTonenGeenDatumEindeGeldigheidVervallenOuderschap() {
        final GroepElement groepElementSamenGesteldeNaam = getGroepElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM);
        final GroepElement groepElementGeboorte = getGroepElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE);

        final Integer datumAanvangRelatie = DatumUtil.vandaag();
        final Integer datumEindeGeldigheidRecord = null;
        final ZonedDateTime datumTijdvervalRecord = TestDatumUtil.morgen();

        final MetaObject metaObject = maakPersoonMetOuderBetrokkenHeidMetGerelateerdKind(datumAanvangRelatie, datumEindeGeldigheidRecord,
                datumTijdvervalRecord, TestDatumUtil.gisteren(), TestDatumUtil.gisteren(), false);

        final List<MetaRecord> records = new ArrayList<>();
        new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(final MetaRecord record) {
                if (record.getParentGroep().getGroepElement().equals(groepElementSamenGesteldeNaam) || record.getParentGroep().getGroepElement().equals
                        (groepElementGeboorte)) {
                    records.add(record);
                }
            }
        }.visit(metaObject);

        final Berichtgegevens berichtgegevens = maakBerichtGegevens(metaObject, Rol.AFNEMER);
        final Set<MetaRecord> prerelatieRecords = service.bepaal(berichtgegevens.getPersoonslijst());
        for (MetaRecord record : records) {
            Assert.assertFalse(prerelatieRecords.contains(record));
        }
    }


    @Test
    public void testPrerelatieNietTonenGeenDatumEindeGeldigheidVervallenOuderschapMaarVervalNietNaTsRegBetrokkenheid() {
        final GroepElement groepElementSamenGesteldeNaam = getGroepElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM);
        final GroepElement groepElementGeboorte = getGroepElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE);

        final Integer datumAanvangRelatie = DatumUtil.vandaag();
        final Integer datumEindeGeldigheidRecord = null;
        final ZonedDateTime eergisteren = DatumUtil.nuAlsZonedDateTime().minus(2, ChronoUnit.DAYS);
        final ZonedDateTime eereergisteren = DatumUtil.nuAlsZonedDateTime().minus(3, ChronoUnit.DAYS);

        final MetaObject metaObject = maakPersoonMetOuderBetrokkenHeidMetGerelateerdKind(datumAanvangRelatie, datumEindeGeldigheidRecord,
                eergisteren, eergisteren, eereergisteren, false);

        final List<MetaRecord> records = new ArrayList<>();
        new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(final MetaRecord record) {
                if (record.getParentGroep().getGroepElement().equals(groepElementSamenGesteldeNaam) || record.getParentGroep().getGroepElement().equals
                        (groepElementGeboorte)) {
                    records.add(record);
                }
            }
        }.visit(metaObject);

        final Berichtgegevens berichtgegevens = maakBerichtGegevens(metaObject, Rol.AFNEMER);
        final Set<MetaRecord> prerelatieRecords = service.bepaal(berichtgegevens.getPersoonslijst());
        for (MetaRecord record : records) {
            Assert.assertTrue(prerelatieRecords.contains(record));
        }
    }

    @Test
    public void testPrerelatieNietTonenGeenDatumEindeGeldigheidVervallenOuderschapMaarVervalNietNaTsRegGerelateerdeBetrokkenheid() {
        final GroepElement groepElementSamenGesteldeNaam = getGroepElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM);
        final GroepElement groepElementGeboorte = getGroepElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE);

        final Integer datumAanvangRelatie = DatumUtil.vandaag();
        final Integer datumEindeGeldigheidRecord = null;
        final ZonedDateTime eergisteren = DatumUtil.nuAlsZonedDateTime().minus(2, ChronoUnit.DAYS);
        final ZonedDateTime eereergisteren = DatumUtil.nuAlsZonedDateTime().minus(3, ChronoUnit.DAYS);
        final ZonedDateTime datumTijdvervalRecord = eergisteren;

        final MetaObject metaObject = maakPersoonMetOuderBetrokkenHeidMetGerelateerdKind(datumAanvangRelatie, datumEindeGeldigheidRecord,
                datumTijdvervalRecord, eereergisteren, datumTijdvervalRecord, false);

        final List<MetaRecord> records = new ArrayList<>();
        new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(final MetaRecord record) {
                if (record.getParentGroep().getGroepElement().equals(groepElementSamenGesteldeNaam) || record.getParentGroep().getGroepElement().equals
                        (groepElementGeboorte)) {
                    records.add(record);
                }
            }
        }.visit(metaObject);

        final Berichtgegevens berichtgegevens = maakBerichtGegevens(metaObject, Rol.AFNEMER);
        final Set<MetaRecord> prerelatieRecords = service.bepaal(berichtgegevens.getPersoonslijst());
        for (MetaRecord record : records) {
            Assert.assertTrue(prerelatieRecords.contains(record));
        }
    }

    @Test
    public void testPrerelatieNietTonenGeenDatumEindeGeldigheidVervallenOuderschapEnVervalNaTsRegBetrokkenheden() {
        final GroepElement groepElementSamenGesteldeNaam = getGroepElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM);
        final GroepElement groepElementGeboorte = getGroepElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE);

        final Integer datumAanvangRelatie = DatumUtil.vandaag();
        final Integer datumEindeGeldigheidRecord = null;
        final ZonedDateTime eergisteren = DatumUtil.nuAlsZonedDateTime().minus(2, ChronoUnit.DAYS);
        final ZonedDateTime eereergisteren = DatumUtil.nuAlsZonedDateTime().minus(3, ChronoUnit.DAYS);
        final ZonedDateTime datumTijdvervalRecord = eergisteren;

        final MetaObject metaObject = maakPersoonMetOuderBetrokkenHeidMetGerelateerdKind(datumAanvangRelatie, datumEindeGeldigheidRecord,
                datumTijdvervalRecord, eereergisteren, eereergisteren, false);

        final List<MetaRecord> records = new ArrayList<>();
        new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(final MetaRecord record) {
                if (record.getParentGroep().getGroepElement().equals(groepElementSamenGesteldeNaam) || record.getParentGroep().getGroepElement().equals
                        (groepElementGeboorte)) {
                    records.add(record);
                }
            }
        }.visit(metaObject);

        final Berichtgegevens berichtgegevens = maakBerichtGegevens(metaObject, Rol.AFNEMER);
        final Set<MetaRecord> prerelatieRecords = service.bepaal(berichtgegevens.getPersoonslijst());
        for (MetaRecord record : records) {
            Assert.assertFalse(prerelatieRecords.contains(record));
        }
    }


    @Test
    public void testPrerelatieWelTonenDatumAanvangVoorDatumEindeGeldigheidOuderschap() {
        final GroepElement groepElementSamenGesteldeNaam = getGroepElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM);
        final GroepElement groepElementGeboorte = getGroepElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE);

        final Integer datumEindeGeldigheidRecord = DatumUtil.datumRondVandaag(1);
        final Integer datumAanvangRelatie = DatumUtil.datumRondVandaag(2);
        final ZonedDateTime datumTijdvervalRecord = null;

        final MetaObject metaObject = maakPersoonMetOuderBetrokkenHeidMetGerelateerdKind(datumAanvangRelatie, datumEindeGeldigheidRecord,
                datumTijdvervalRecord, TestDatumUtil.gisteren(), TestDatumUtil.gisteren(), false);

        final List<MetaRecord> records = new ArrayList<>();
        new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(final MetaRecord record) {
                if (record.getParentGroep().getGroepElement().equals(groepElementSamenGesteldeNaam) || record.getParentGroep().getGroepElement().equals
                        (groepElementGeboorte)) {
                    records.add(record);
                }
            }
        }.visit(metaObject);

        final Berichtgegevens berichtgegevens = maakBerichtGegevens(metaObject, Rol.AFNEMER);
        final Set<MetaRecord> prerelatieRecords = service.bepaal(berichtgegevens.getPersoonslijst());

        for (MetaRecord record : records) {
            Assert.assertFalse(prerelatieRecords.contains(record));
        }
    }

    @Test
    public void testPrerelatieNietTonenHuwelijk() {
        final GroepElement groepElementSamenGesteldeNaam = getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM);
        final GroepElement groepElementGeboorte = getGroepElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE);

        final Integer datumAanvangRelatie = DatumUtil.vandaag();
        final Integer datumEindeGeldigheidRecord = DatumUtil.gisteren();
        final ZonedDateTime datumTijdvervalRecord = null;

        final MetaObject metaObject = maakPersoonMetHuwelijkBetrokkenHeid(datumAanvangRelatie, datumEindeGeldigheidRecord,
                datumTijdvervalRecord, TestDatumUtil.gisteren(), TestDatumUtil.gisteren());

        final List<MetaRecord> records = new ArrayList<>();
        new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(final MetaRecord record) {
                if (record.getParentGroep().getGroepElement().equals(groepElementSamenGesteldeNaam) || record.getParentGroep().getGroepElement().equals
                        (groepElementGeboorte)) {
                    records.add(record);
                }
            }
        }.visit(metaObject);

        final Berichtgegevens berichtgegevens = maakBerichtGegevens(metaObject, Rol.AFNEMER);
        final Set<MetaRecord> prerelatieRecords = service.bepaal(berichtgegevens.getPersoonslijst());

        Assert.assertTrue(prerelatieRecords.size() > 0);
        for (MetaRecord record : records) {
            Assert.assertTrue(prerelatieRecords.contains(record));
        }
    }


    @Test
    public void testPrerelatieWelTonenGeenDatumEindeGeldigheidHuwelijk() {
        final GroepElement groepElementSamenGesteldeNaam = getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM);
        final GroepElement groepElementGeboorte = getGroepElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE);

        final Integer datumAanvangRelatie = DatumUtil.vandaag();
        final Integer datumEindeGeldigheidRecord = null;
        final ZonedDateTime datumTijdvervalRecord = null;

        final MetaObject metaObject = maakPersoonMetHuwelijkBetrokkenHeid(datumAanvangRelatie, datumEindeGeldigheidRecord,
                datumTijdvervalRecord, TestDatumUtil.gisteren(), TestDatumUtil.gisteren());

        final List<MetaRecord> records = new ArrayList<>();
        new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(final MetaRecord record) {
                if (record.getParentGroep().getGroepElement().equals(groepElementSamenGesteldeNaam) || record.getParentGroep().getGroepElement().equals
                        (groepElementGeboorte)) {
                    records.add(record);
                }
            }
        }.visit(metaObject);

        final Berichtgegevens berichtgegevens = maakBerichtGegevens(metaObject, Rol.AFNEMER);
        final Set<MetaRecord> prerelatieRecords = service.bepaal(berichtgegevens.getPersoonslijst());

        for (MetaRecord record : records) {
            Assert.assertFalse(prerelatieRecords.contains(record));
        }
    }

    @Test
    public void testPrerelatieWelTonenDatumAanvangVoorDatumEindeGeldigheidHuwelijk() {
        final GroepElement groepElementSamenGesteldeNaam = getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM);
        final GroepElement groepElementGeboorte = getGroepElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE);

        final Integer datumEindeGeldigheidRecord = DatumUtil.datumRondVandaag(1);
        final Integer datumAanvangRelatie = DatumUtil.datumRondVandaag(2);
        final ZonedDateTime datumTijdvervalRecord = null;

        final MetaObject metaObject = maakPersoonMetHuwelijkBetrokkenHeid(datumAanvangRelatie, datumEindeGeldigheidRecord,
                datumTijdvervalRecord, TestDatumUtil.gisteren(), TestDatumUtil.gisteren());

        final List<MetaRecord> records = new ArrayList<>();
        new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(final MetaRecord record) {
                if (record.getParentGroep().getGroepElement().equals(groepElementSamenGesteldeNaam)
                        || record.getParentGroep().getGroepElement().equals(groepElementGeboorte)) {
                    records.add(record);
                }
            }
        }.visit(metaObject);

        final Berichtgegevens berichtgegevens = maakBerichtGegevens(metaObject, Rol.AFNEMER);
        final Set<MetaRecord> prerelatieRecords = service.bepaal(berichtgegevens.getPersoonslijst());

        for (MetaRecord record : records) {
            Assert.assertFalse(prerelatieRecords.contains(record));
        }
    }

    @Test
    public void testAlleenActueleAttributenGebruikenBijPreRelatieFiltering() {
        final GroepElement groepElementSamenGesteldeNaam = getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM);

        final Integer datumEindeGeldigheidRecord = DatumUtil.datumRondVandaag(1);
        final Integer datumAanvangRelatie = DatumUtil.datumRondVandaag(2);
        final ZonedDateTime datumTijdvervalRecord = null;

        final MetaObject metaObject = maakPersoonMetHuwelijkBetrokkenHeid(datumAanvangRelatie, datumEindeGeldigheidRecord,
                datumTijdvervalRecord, TestDatumUtil.gisteren(), TestDatumUtil.gisteren());

        final List<MetaRecord> records = new ArrayList<>();
        new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(final MetaRecord record) {
                if (record.getParentGroep().getGroepElement().equals(groepElementSamenGesteldeNaam)) {
                    records.add(record);
                }
                if (record.getParentGroep().getGroepElement().getNaam().equals("Huwelijk.Standaard")) {
                    for (MetaRecord r : record.getParentGroep().getRecords()) {
                        ReflectionTestUtils.setField(r, "actieVerval", TestVerantwoording
                                .maakActie(1, ZonedDateTime.of(2010, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"))));
                    }
                }
            }
        }.visit(metaObject);

        final Berichtgegevens berichtgegevens = maakBerichtGegevens(metaObject, Rol.AFNEMER);
        final Set<MetaRecord> prerelatieRecords = service.bepaal(berichtgegevens.getPersoonslijst());

        for (MetaRecord record : records) {
            Assert.assertFalse(prerelatieRecords.contains(record));
        }
    }

    private MetaObject maakPersoonMetOuderBetrokkenHeidMetGerelateerdKind(final Integer datumAanvangRelatie,
                                                                          final Integer datumEindeGeldigheidRecord,
                                                                          final ZonedDateTime datumTijdvervalRecord,
                                                                          final ZonedDateTime tsregBetrokkenheid,
                                                                          final ZonedDateTime tsRegGerelateerdeBetrokkenheid,
                                                                          final boolean indMutLevRecord) {

        final Actie actieInhoud = TestVerantwoording.maakActie(1, TestDatumUtil.gisteren());
        final Actie actieInhoudBetrokkenheid = TestVerantwoording.maakActie(1, tsregBetrokkenheid);
        final Actie actieInhoudGerelateerdeBetrokkenheid = TestVerantwoording.maakActie(1, tsRegGerelateerdeBetrokkenheid);

        final MetaGroep.Builder groepBuilder = new MetaGroep.Builder();
        final GroepElement groepElementSamenGesteldeNaam = getGroepElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM);
        groepBuilder.metGroepElement(groepElementSamenGesteldeNaam);

        final List<MetaRecord.Builder> recordBuilders = new ArrayList<>();
        final MetaRecord.Builder record = new MetaRecord.Builder(groepBuilder)
                .metActieInhoud(actieInhoudGerelateerdeBetrokkenheid)
                .metIndicatieTbvLeveringMutaties(indMutLevRecord)
                .metAttribuut(getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM
                        .getId()), "X");
        if (datumEindeGeldigheidRecord != null) {
            record.metDatumEindeGeldigheid(datumEindeGeldigheidRecord);
        }
        if (datumTijdvervalRecord != null) {
            final Actie actieVerval = TestVerantwoording.maakActie(2, datumTijdvervalRecord);
            record.metActieVerval(actieVerval);
        }

        recordBuilders.add(record);

        groepBuilder.metRecords(recordBuilders);

        int index = 1;
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metId(index++)
            .metObjectElement(Element.PERSOON)
            .metObject()
                .metObjectElement(getObjectElement(Element.PERSOON_OUDER))
                .metId(index++)
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_OUDER_IDENTITEIT))
                    .metRecord().metId(1).metActieInhoud(actieInhoudBetrokkenheid).eindeRecord()
                .eindeGroep()
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_OUDER_OUDERSCHAP))
                    .metRecord().metId(1)
                            .metActieInhoud(actieInhoudBetrokkenheid)
                         .metDatumAanvangGeldigheid(datumAanvangRelatie)
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING))
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_STANDAARD))
                            .metRecord().metActieInhoud(actieInhoud)
                                .metAttribuut(getAttribuutElement(Element.FAMILIERECHTELIJKEBETREKKING_DATUMAANVANG), datumAanvangRelatie)
                            .eindeRecord()
                    .eindeGroep()
                    .metGroep()
                    .metGroepElement(getGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_IDENTITEIT))
                            .metRecord()
                                .metAttribuut(getAttribuutElement(Element.FAMILIERECHTELIJKEBETREKKING_SOORTCODE), SoortRelatie
                                    .FAMILIERECHTELIJKE_BETREKKING.getCode())
                            .eindeRecord()
                    .eindeGroep()
                    .metObject()
                        .metObjectElement(getObjectElement(Element.GERELATEERDEKIND))
                        .metId(index++)
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.GERELATEERDEKIND_IDENTITEIT))
                            .metRecord().metActieInhoud(actieInhoudGerelateerdeBetrokkenheid).eindeRecord()
                        .eindeGroep()
                        .metObject()
                            .metObjectElement(getObjectElement(Element.GERELATEERDEKIND_PERSOON))
                            .metId(index++)
                            .metGroepen(Collections.singletonList(groepBuilder))
                            .eindeObject()
                        .eindeObject()
                .eindeObject()
            .eindeObject()
        .build();
      return persoon;
    }


    private MetaObject maakPersoonMetHuwelijkBetrokkenHeid(final Integer datumAanvangRelatie,
        final Integer datumEindeGeldigheidRecord,
        final ZonedDateTime datumTijdvervalRecord,
        final ZonedDateTime tsregBetrokkenheid, final ZonedDateTime tsRegGerelateerdeBetrokkenheid)
    {
        final Actie actieInhoud = TestVerantwoording.maakActie(1, TestDatumUtil.gisteren());

        final Actie actieInhoudBetrokkenheid = TestVerantwoording.maakActie(1, tsregBetrokkenheid);
        final Actie actieInhoudGerelateerdeBetrokkenheid = TestVerantwoording.maakActie(1, tsRegGerelateerdeBetrokkenheid);

        final MetaGroep.Builder groepBuilderSamenGesteldeNaam = new MetaGroep.Builder();
        final GroepElement groepElementSamenGesteldeNaam = getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM);
        groepBuilderSamenGesteldeNaam.metGroepElement(groepElementSamenGesteldeNaam);


        final List<MetaRecord.Builder> recordBuilders = new ArrayList<>();
        final MetaRecord.Builder record = new MetaRecord.Builder(groepBuilderSamenGesteldeNaam)
            .metActieInhoud(actieInhoud)
            .metAttribuut(getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM
                .getId()), "X");
        if (datumEindeGeldigheidRecord != null) {
            record.metDatumEindeGeldigheid(datumEindeGeldigheidRecord);
        }
        if (datumTijdvervalRecord != null) {
            final Actie actieVerval = TestVerantwoording.maakActie(2, datumTijdvervalRecord);
            record.metActieVerval(actieVerval);
        }

        recordBuilders.add(record);

        groepBuilderSamenGesteldeNaam.metRecords(recordBuilders);

        final MetaGroep.Builder groepBuilderGeboorte = new MetaGroep.Builder();
        groepBuilderGeboorte.metGroepElement(groepElementSamenGesteldeNaam);

        final MetaRecord.Builder recordGeboorte = new MetaRecord.Builder(groepBuilderSamenGesteldeNaam)
            .metActieInhoud(actieInhoud)
            .metAttribuut(getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM
                .getId()), "X");
        if (datumTijdvervalRecord != null) {
            final Actie actieVerval = TestVerantwoording.maakActie(3, datumTijdvervalRecord);
            recordGeboorte.metActieVerval(actieVerval);
        }

        groepBuilderGeboorte.metRecords(recordBuilders);

        int index = 1;
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metId(index++)
            .metObjectElement(Element.PERSOON)
            .metObject()
                .metObjectElement(getObjectElement(Element.PERSOON_PARTNER))
                .metId(index++)
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_PARTNER_IDENTITEIT))
                    .metRecord().metId(1).metActieInhoud(actieInhoudBetrokkenheid).eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(getObjectElement(Element.HUWELIJK))
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.HUWELIJK_STANDAARD))
                            .metRecord()
                                .metActieInhoud(actieInhoud)
                                .metAttribuut(getAttribuutElement(Element.HUWELIJK_DATUMAANVANG), datumAanvangRelatie)
                            .eindeRecord()
                    .eindeGroep()
                    .metGroep()
                    .metGroepElement(getGroepElement(Element.HUWELIJK_IDENTITEIT))
                            .metRecord()
                                .metAttribuut(getAttribuutElement(Element.HUWELIJK_SOORTCODE), SoortRelatie.HUWELIJK.getCode())
                            .eindeRecord()
                    .eindeGroep()
                    .metObject()
                        .metObjectElement(getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER))
                        .metId(index++)
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_IDENTITEIT))
                            .metRecord().metActieInhoud(actieInhoudGerelateerdeBetrokkenheid).eindeRecord()
                        .eindeGroep()
                        .metObject()
                            .metObjectElement(getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON))
                            .metId(index++)
                            .metGroepen(Lists.newArrayList(groepBuilderSamenGesteldeNaam, groepBuilderGeboorte))
                            .eindeObject()
                        .eindeObject()
                .eindeObject()
            .eindeObject()
        .build();
      return persoon;
    }


    private Berichtgegevens maakBerichtGegevens(final MetaObject metaObject, final Rol rol) {
        final Persoonslijst persoonslijst = new Persoonslijst(metaObject, 0L);

        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();

        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(SoortDienst.ATTENDERING);
        final Dienst dienst = leveringsautorisatie.getDienstbundelSet().iterator().next().getDienstSet().iterator().next();
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(TestAutorisaties.maak(rol, dienst), dienst);
        maakBerichtParameters.setAutorisatiebundels(Lists.newArrayList(autorisatiebundel));

        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
            new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT), autorisatiebundel, new StatischePersoongegevens());

        berichtgegevens.getPersoonslijst().getMetaObject().accept(new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(final MetaRecord record) {
                berichtgegevens.autoriseer(record);
            }
        });
        return berichtgegevens;
    }
}
