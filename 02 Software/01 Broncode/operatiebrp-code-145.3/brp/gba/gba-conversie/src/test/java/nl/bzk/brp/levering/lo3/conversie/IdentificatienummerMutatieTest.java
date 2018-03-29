/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie;

import com.google.common.collect.Lists;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereAanduidingVerval;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.element.ElementConstants;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.levering.lo3.mapper.PersoonIdentificatienummersMapper;
import nl.bzk.brp.levering.lo3.util.MetaModelUtil;
import org.junit.Assert;
import org.junit.Test;

public class IdentificatienummerMutatieTest {

    private static final Logger LOGGER        = LoggerFactory.getLogger();
    private static final String OUD_ANUMMER   = "9539040545";
    private static final String NIEUW_ANUMMER = "3450924321";
    private static final String OUD_BSN       = "134134355";
    private static final String NIEUW_BSN     = "765463454";

    private IdentificatienummerMutatie subject;

    @Test
    public void testNetteAnummerWijziging() {

        final ZonedDateTime tsReg = ZonedDateTime.of(1977, 1, 1, 0, 0, 0, 0, ZoneId.of(DatumUtil.UTC));
        final AdministratieveHandeling administratievehandeling = AdministratieveHandeling.converter()
            .converteer(TestVerantwoording.maakAdministratieveHandeling(1, "000001", tsReg,
                SoortAdministratieveHandeling
                    .GBA_INITIELE_VULLING)
                .metObjecten(Lists.newArrayList(
                    TestVerantwoording.maakActieBuilder(1L, SoortActie.REGISTRATIE_ADRES, tsReg, "000001", null),
                    TestVerantwoording.maakActieBuilder(2L, SoortActie.REGISTRATIE_ADRES, tsReg, "000001", null)
                ))
                .build());
        final Actie actieInhoudOud = administratievehandeling.getActie(1);
        final Actie actieInhoud = administratievehandeling.getActie(2);


        final MetaObject persoon =
            MetaObject.maakBuilder()
                .metId(25252354)
                .metGroep()
                .metGroepElement(ElementConstants.PERSOON_IDENTIFICATIENUMMERS)
                .metRecord()
                .metId(234524)
                .metAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT, NIEUW_ANUMMER)
                .metActieInhoud(actieInhoud)
                .eindeRecord()
                .metRecord()
                .metId(34134)
                .metAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT, OUD_ANUMMER)
                .metActieInhoud(actieInhoudOud)
                .metActieAanpassingGeldigheid(actieInhoud)
                .metActieVervalTbvLeveringMutaties(actieInhoud)
                .eindeRecord()
                .eindeGroep()
                .build();

        // Print vanuit meta model
        LOGGER.info("---[META - Nette Anummer wijziging]-------------------------------------------------");
        LOGGER.info("id: {}", persoon.getObjectsleutel());
        for (final MetaRecord historie : MetaModelUtil.getRecords(persoon, PersoonIdentificatienummersMapper.GROEP_ELEMENT)) {
            LOGGER.info("his: {}", historie.getVoorkomensleutel());
            LOGGER.info(
                "\tAnummer: {}",
                historie.getAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT) == null ? ""
                    : historie.getAttribuut(
                    PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT)
                    .getWaarde());
            LOGGER.info(
                "\tActie inhoud (id={}): {}",
                historie.getActieInhoud() == null ? "" : historie.getActieInhoud().getId(),
                historie.getActieInhoud() == null ? "" : historie.getActieInhoud().getTijdstipRegistratie());
            LOGGER.info(
                "\tActie aanpassing geldigheid (id={}): {}",

                historie.getActieAanpassingGeldigheid() == null ? "" : historie.getActieAanpassingGeldigheid().getId(),
                historie.getActieAanpassingGeldigheid() == null ? "" : historie.getActieAanpassingGeldigheid().getTijdstipRegistratie());
            LOGGER.info(
                "\tActie verval tbv muts (id={}): {}",
                historie.getActieVervalTbvLeveringMutaties() == null ? "" : historie.getActieVervalTbvLeveringMutaties().getId(),
                historie.getActieVervalTbvLeveringMutaties() == null ? "" : historie.getActieVervalTbvLeveringMutaties().getTijdstipRegistratie());
            LOGGER.info("\tNadere aanduiding verval: {}", historie.getNadereAanduidingVerval() == null ? "" : historie.getNadereAanduidingVerval());
        }

        subject = new IdentificatienummerMutatie(persoon, administratievehandeling);
        Assert.assertTrue(subject.isAnummerWijziging());
        Assert.assertEquals(
            NIEUW_ANUMMER,
            subject.getNieuwIdentificatienummersRecord().getAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT).getWaarde());
        Assert.assertEquals(
            OUD_ANUMMER,
            subject.getVervallenIdentificatienummersRecord().getAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT).getWaarde());
    }

    @Test
    public void testNetteCorrectieAnummer() {

        final ZonedDateTime tsReg = ZonedDateTime.of(1977, 1, 1, 0, 0, 0, 0, ZoneId.of(DatumUtil.UTC));
        final AdministratieveHandeling administratievehandeling = AdministratieveHandeling.converter()
            .converteer(TestVerantwoording.maakAdministratieveHandeling(1, "000001", tsReg,
                SoortAdministratieveHandeling
                    .GBA_INITIELE_VULLING)
                .metObjecten(Lists.newArrayList(
                    TestVerantwoording.maakActieBuilder(1L, SoortActie.REGISTRATIE_ADRES, tsReg, "000001", null),
                    TestVerantwoording.maakActieBuilder(2L, SoortActie.REGISTRATIE_ADRES, tsReg, "000001", null)
                ))
                .build());
        final Actie actieInhoudOud = administratievehandeling.getActie(1);
        final Actie actieInhoud = administratievehandeling.getActie(2);


        final MetaObject persoon =
            MetaObject.maakBuilder()
                .metId(25252354)
                .metGroep()
                .metGroepElement(ElementConstants.PERSOON_IDENTIFICATIENUMMERS)
                .metRecord()
                .metId(234524)
                .metAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT, NIEUW_ANUMMER)
                .metActieInhoud(actieInhoud)
                .eindeRecord()
                .metRecord()
                .metId(34134)
                .metAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT, OUD_ANUMMER)
                .metActieInhoud(actieInhoudOud)
                .metActieVerval(actieInhoudOud)
                .metActieVervalTbvLeveringMutaties(actieInhoud)
                .metNadereAanduidingVerval(NadereAanduidingVerval.O.getCode())
                .eindeRecord()
                .eindeGroep()
                .build();

        // Print vanuit meta model
        LOGGER.info("---[META - Nette correctie Anummer]-------------------------------------------------");
        LOGGER.info("id: {}", persoon.getObjectsleutel());
        for (final MetaRecord historie : MetaModelUtil.getRecords(persoon, PersoonIdentificatienummersMapper.GROEP_ELEMENT)) {
            LOGGER.info("his: {}", historie.getVoorkomensleutel());
            LOGGER.info(
                "\tAnummer: {}",
                historie.getAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT) == null ? ""
                    : historie.getAttribuut(
                    PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT)
                    .getWaarde());
            LOGGER.info(
                "\tActie inhoud (id={}): {}",
                historie.getActieInhoud() == null ? "" : historie.getActieInhoud().getId(),
                historie.getActieInhoud() == null ? "" : historie.getActieInhoud().getTijdstipRegistratie());
            LOGGER.info(
                "\tActie aanpassing geldigheid (id={}): {}",

                historie.getActieAanpassingGeldigheid() == null ? "" : historie.getActieAanpassingGeldigheid().getId(),
                historie.getActieAanpassingGeldigheid() == null ? "" : historie.getActieAanpassingGeldigheid().getTijdstipRegistratie());
            LOGGER.info(
                "\tActie verval tbv muts (id={}): {}",
                historie.getActieVervalTbvLeveringMutaties() == null ? "" : historie.getActieVervalTbvLeveringMutaties().getId(),
                historie.getActieVervalTbvLeveringMutaties() == null ? "" : historie.getActieVervalTbvLeveringMutaties().getTijdstipRegistratie());
            LOGGER.info("\tNadere aanduiding verval: {}", historie.getNadereAanduidingVerval() == null ? "" : historie.getNadereAanduidingVerval());
        }

        subject = new IdentificatienummerMutatie(persoon, administratievehandeling);
        Assert.assertTrue(subject.isAnummerWijziging());
        Assert.assertEquals(
            NIEUW_ANUMMER,
            subject.getNieuwIdentificatienummersRecord().getAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT).getWaarde());
        Assert.assertEquals(
            OUD_ANUMMER,
            subject.getVervallenIdentificatienummersRecord().getAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT).getWaarde());
    }

    @Test
    public void testGeenAnummerWijziging() {

        final ZonedDateTime tsReg = ZonedDateTime.of(1977, 1, 1, 0, 0, 0, 0, ZoneId.of(DatumUtil.UTC));
        final AdministratieveHandeling administratievehandeling = AdministratieveHandeling.converter()
            .converteer(TestVerantwoording.maakAdministratieveHandeling(1, "000001", tsReg,
                SoortAdministratieveHandeling
                    .GBA_INITIELE_VULLING)
                .metObjecten(Lists.newArrayList(
                    TestVerantwoording.maakActieBuilder(1L, SoortActie.REGISTRATIE_ADRES, tsReg, "000001", null),
                    TestVerantwoording.maakActieBuilder(2L, SoortActie.REGISTRATIE_ADRES, tsReg, "000001", null)
                ))
                .build());
        final Actie actieInhoudOud = administratievehandeling.getActie(1);
        final Actie actieInhoud = administratievehandeling.getActie(2);

        final MetaObject persoon =
            MetaObject.maakBuilder()
                .metId(25252354)
                .metGroep()
                .metGroepElement(ElementConstants.PERSOON_IDENTIFICATIENUMMERS)
                .metRecord()
                .metId(234524)
                .metAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT, NIEUW_ANUMMER)
                .metActieInhoud(actieInhoud)
                .eindeRecord()
                .metRecord()
                .metId(34134)
                .metAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT, NIEUW_ANUMMER)
                .metActieInhoud(actieInhoudOud)
                .metActieVerval(actieInhoudOud)
                .metActieVervalTbvLeveringMutaties(actieInhoud)
                .metNadereAanduidingVerval(NadereAanduidingVerval.O.getCode())
                .eindeRecord()
                .eindeGroep()
                .build();

        // Print vanuit meta model
        LOGGER.info("---[META - Geen Anummer wijziging]-------------------------------------------------");
        LOGGER.info("id: {}", persoon.getObjectsleutel());
        for (final MetaRecord historie : MetaModelUtil.getRecords(persoon, PersoonIdentificatienummersMapper.GROEP_ELEMENT)) {
            LOGGER.info("his: {}", historie.getVoorkomensleutel());
            LOGGER.info(
                "\tAnummer: {}",
                historie.getAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT) == null ? ""
                    : historie.getAttribuut(
                    PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT)
                    .getWaarde());
            LOGGER.info(
                "\tActie inhoud (id={}): {}",
                historie.getActieInhoud() == null ? "" : historie.getActieInhoud().getId(),
                historie.getActieInhoud() == null ? "" : historie.getActieInhoud().getTijdstipRegistratie());
            LOGGER.info(
                "\tActie aanpassing geldigheid (id={}): {}",

                historie.getActieAanpassingGeldigheid() == null ? "" : historie.getActieAanpassingGeldigheid().getId(),
                historie.getActieAanpassingGeldigheid() == null ? "" : historie.getActieAanpassingGeldigheid().getTijdstipRegistratie());
            LOGGER.info(
                "\tActie verval tbv muts (id={}): {}",
                historie.getActieVervalTbvLeveringMutaties() == null ? "" : historie.getActieVervalTbvLeveringMutaties().getId(),
                historie.getActieVervalTbvLeveringMutaties() == null ? "" : historie.getActieVervalTbvLeveringMutaties().getTijdstipRegistratie());
            LOGGER.info("\tNadere aanduiding verval: {}", historie.getNadereAanduidingVerval() == null ? "" : historie.getNadereAanduidingVerval());
        }

        subject = new IdentificatienummerMutatie(persoon, administratievehandeling);
        Assert.assertFalse(subject.isAnummerWijziging());
        Assert.assertEquals(
            NIEUW_ANUMMER,
            subject.getNieuwIdentificatienummersRecord().getAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT).getWaarde());
        Assert.assertEquals(
            NIEUW_ANUMMER,
            subject.getVervallenIdentificatienummersRecord().getAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT).getWaarde());
    }

    @Test
    public void testOngeldigeActieVervalAnummerWijziging() {

        final AdministratieveHandeling administratievehandeling = AdministratieveHandeling.converter()
            .converteer(TestVerantwoording.maakAdministratieveHandeling(1, "000001", null,
                SoortAdministratieveHandeling
                    .GBA_INITIELE_VULLING)
                .metObjecten(Lists.newArrayList(
                    TestVerantwoording.maakActieBuilder(2L, SoortActie.REGISTRATIE_ADRES, ZonedDateTime.of(1977, 1, 1, 0, 0, 0, 0, DatumUtil
                        .BRP_ZONE_ID), "000001", null)

                ))
        .build());

        final Actie actieInhoud = administratievehandeling.getActie(2);
        final Actie actieInhoudOud = TestVerantwoording.maakActie(1L, ZonedDateTime.of(1970, 1, 1, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID));
        final Actie actieInhoudOngeldig = TestVerantwoording.maakActie(3L, ZonedDateTime.of(2012, 1, 1, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID));

        final MetaObject persoon =
            MetaObject.maakBuilder()
                .metId(25252354)
                .metGroep()
                .metGroepElement(ElementConstants.PERSOON_IDENTIFICATIENUMMERS)
                .metRecord()
                .metId(234524)
                .metAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT, NIEUW_ANUMMER)
                .metActieInhoud(actieInhoud)
                .eindeRecord()
                .metRecord()
                .metId(34134)
                .metAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT, OUD_ANUMMER)
                .metActieInhoud(actieInhoudOud)
                .metActieVerval(actieInhoudOngeldig)
                .eindeRecord()
                .eindeGroep()
                .build();

        // Print vanuit meta model
        LOGGER.info("---[META - Ongeldige ActieVerval Anummer]-------------------------------------------------");
        LOGGER.info("id: {}", persoon.getObjectsleutel());
        for (final MetaRecord historie : MetaModelUtil.getRecords(persoon, PersoonIdentificatienummersMapper.GROEP_ELEMENT)) {
            LOGGER.info("his: {}", historie.getVoorkomensleutel());
            LOGGER.info(
                "\tAnummer: {}",
                historie.getAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT) == null ? ""
                    : historie.getAttribuut(
                    PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT)
                    .getWaarde());
            LOGGER.info(
                "\tActie inhoud (id={}): {}",
                historie.getActieInhoud() == null ? "" : historie.getActieInhoud().getId(),
                historie.getActieInhoud() == null ? "" : historie.getActieInhoud().getTijdstipRegistratie());
            LOGGER.info(
                "\tActie aanpassing geldigheid (id={}): {}",

                historie.getActieAanpassingGeldigheid() == null ? "" : historie.getActieAanpassingGeldigheid().getId(),
                historie.getActieAanpassingGeldigheid() == null ? "" : historie.getActieAanpassingGeldigheid().getTijdstipRegistratie());
            LOGGER.info(
                "\tActie verval tbv muts (id={}): {}",
                historie.getActieVervalTbvLeveringMutaties() == null ? "" : historie.getActieVervalTbvLeveringMutaties().getId(),
                historie.getActieVervalTbvLeveringMutaties() == null ? "" : historie.getActieVervalTbvLeveringMutaties().getTijdstipRegistratie());
            LOGGER.info("\tNadere aanduiding verval: {}", historie.getNadereAanduidingVerval() == null ? "" : historie.getNadereAanduidingVerval());
        }

        subject = new IdentificatienummerMutatie(persoon, administratievehandeling);
        Assert.assertFalse(subject.isAnummerWijziging());
        Assert.assertEquals(
            NIEUW_ANUMMER,
            subject.getNieuwIdentificatienummersRecord().getAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT).getWaarde());
        Assert.assertNull(subject.getVervallenIdentificatienummersRecord());
    }

    @Test
    public void testOngeldigeActieInhoudAnummerWijziging() {

        final AdministratieveHandeling administratievehandeling = AdministratieveHandeling.converter()
            .converteer(TestVerantwoording.maakAdministratieveHandeling(2, "000001",
                null, SoortAdministratieveHandeling.GBA_INITIELE_VULLING)
                .metObjecten(Lists.newArrayList(
                    TestVerantwoording.maakActieBuilder(2L, SoortActie.REGISTRATIE_ADRES,
                        ZonedDateTime.of(1977, 1, 1, 0, 0, 0, 0, ZoneId.of(DatumUtil.UTC)), "000001", null)
                ))
            .build());
        final Actie actieInhoud = administratievehandeling.getActie(2);
        final Actie actieInhoudOngeldig = TestVerantwoording.maakActie(3, ZonedDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneId.of(DatumUtil.UTC)));

        final MetaObject persoon =
            MetaObject.maakBuilder()
                .metId(25252354)
                .metGroep()
                .metGroepElement(ElementConstants.PERSOON_IDENTIFICATIENUMMERS)
                .metRecord()
                .metId(234524)
                .metAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT, NIEUW_ANUMMER)
                .metActieInhoud(actieInhoudOngeldig)
                .eindeRecord()
                .eindeGroep()
                .build();

        // Print vanuit meta model
        LOGGER.info("---[META - Ongeldige ActieInhoud Anummer]-------------------------------------------------");
        LOGGER.info("id: {}", persoon.getObjectsleutel());
        for (final MetaRecord historie : MetaModelUtil.getRecords(persoon, PersoonIdentificatienummersMapper.GROEP_ELEMENT)) {
            LOGGER.info("his: {}", historie.getVoorkomensleutel());
            LOGGER.info(
                "\tAnummer: {}",
                historie.getAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT) == null ? ""
                    : historie.getAttribuut(
                    PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT)
                    .getWaarde());
            LOGGER.info(
                "\tActie inhoud (id={}): {}",
                historie.getActieInhoud() == null ? "" : historie.getActieInhoud().getId(),
                historie.getActieInhoud() == null ? "" : historie.getActieInhoud().getTijdstipRegistratie());
            LOGGER.info(
                "\tActie aanpassing geldigheid (id={}): {}",

                historie.getActieAanpassingGeldigheid() == null ? "" : historie.getActieAanpassingGeldigheid().getId(),
                historie.getActieAanpassingGeldigheid() == null ? "" : historie.getActieAanpassingGeldigheid().getTijdstipRegistratie());
            LOGGER.info(
                "\tActie verval tbv muts (id={}): {}",
                historie.getActieVervalTbvLeveringMutaties() == null ? "" : historie.getActieVervalTbvLeveringMutaties().getId(),
                historie.getActieVervalTbvLeveringMutaties() == null ? "" : historie.getActieVervalTbvLeveringMutaties().getTijdstipRegistratie());
            LOGGER.info("\tNadere aanduiding verval: {}", historie.getNadereAanduidingVerval() == null ? "" : historie.getNadereAanduidingVerval());
        }

        subject = new IdentificatienummerMutatie(persoon, administratievehandeling);
        Assert.assertFalse(subject.isAnummerWijziging());
        Assert.assertNull(subject.getNieuwIdentificatienummersRecord());
    }

    @Test
    public void testLegeHistorieAnummer() {

        final ZonedDateTime tsReg = ZonedDateTime.of(1977, 1, 1, 0, 0, 0, 0, ZoneId.of(DatumUtil.UTC));
        final AdministratieveHandeling administratievehandeling = AdministratieveHandeling.converter()
            .converteer(TestVerantwoording.maakAdministratieveHandeling(1, "000001", tsReg,
                SoortAdministratieveHandeling
                    .GBA_INITIELE_VULLING)
                .metObjecten(Lists.newArrayList(
                    TestVerantwoording.maakActieBuilder(1L, SoortActie.REGISTRATIE_ADRES, tsReg, "000001", null)
                ))
                .build());
        final Actie actieInhoud = administratievehandeling.getActie(1);

        final MetaObject persoon =
            MetaObject.maakBuilder()
                .metId(25252354)
                .metGroep()
                .metGroepElement(ElementConstants.PERSOON_IDENTIFICATIENUMMERS)
                .metRecord()
                .metId(234524)
                .metAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT, NIEUW_ANUMMER)
                .metActieInhoud(actieInhoud)
                .eindeRecord()
                .eindeGroep()
                .build();

        // Print vanuit meta model
        LOGGER.info("---[META - Lege historie Anummer]-------------------------------------------------");
        LOGGER.info("id: {}", persoon.getObjectsleutel());
        for (final MetaRecord historie : MetaModelUtil.getRecords(persoon, PersoonIdentificatienummersMapper.GROEP_ELEMENT)) {
            LOGGER.info("his: {}", historie.getVoorkomensleutel());
            LOGGER.info(
                "\tAnummer: {}",
                historie.getAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT) == null ? ""
                    : historie.getAttribuut(
                    PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT)
                    .getWaarde());
            LOGGER.info(
                "\tActie inhoud (id={}): {}",
                historie.getActieInhoud() == null ? "" : historie.getActieInhoud().getId(),
                historie.getActieInhoud() == null ? "" : historie.getActieInhoud().getTijdstipRegistratie());
            LOGGER.info(
                "\tActie aanpassing geldigheid (id={}): {}",

                historie.getActieAanpassingGeldigheid() == null ? "" : historie.getActieAanpassingGeldigheid().getId(),
                historie.getActieAanpassingGeldigheid() == null ? "" : historie.getActieAanpassingGeldigheid().getTijdstipRegistratie());
            LOGGER.info(
                "\tActie verval tbv muts (id={}): {}",
                historie.getActieVervalTbvLeveringMutaties() == null ? "" : historie.getActieVervalTbvLeveringMutaties().getId(),
                historie.getActieVervalTbvLeveringMutaties() == null ? "" : historie.getActieVervalTbvLeveringMutaties().getTijdstipRegistratie());
            LOGGER.info("\tNadere aanduiding verval: {}", historie.getNadereAanduidingVerval() == null ? "" : historie.getNadereAanduidingVerval());
        }

        subject = new IdentificatienummerMutatie(persoon, administratievehandeling);
        Assert.assertFalse(subject.isAnummerWijziging());
        Assert.assertEquals(
            NIEUW_ANUMMER,
            subject.getNieuwIdentificatienummersRecord().getAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT).getWaarde());
        Assert.assertNull(subject.getVervallenIdentificatienummersRecord());
    }

    @Test
    public void testLeegActueelAnummer() {

        final AdministratieveHandeling administratievehandeling = AdministratieveHandeling.converter()
            .converteer(TestVerantwoording.maakAdministratieveHandeling(1, "000001", ZonedDateTime.of(1977, 1, 1, 0, 0, 0, 0, ZoneId.of(DatumUtil.UTC)),
                SoortAdministratieveHandeling
                    .GBA_INITIELE_VULLING)
                .metObjecten(Lists.newArrayList(
                    TestVerantwoording.maakActieBuilder(1L, SoortActie.REGISTRATIE_ADRES, ZonedDateTime.of(1977, 1, 1, 0, 0, 0, 0, ZoneId.of(DatumUtil
                        .UTC)), "000001", null),
                    TestVerantwoording.maakActieBuilder(2L, SoortActie.REGISTRATIE_ADRES, ZonedDateTime.of(1977, 1, 1, 0, 0, 0, 0, ZoneId.of(DatumUtil
                        .UTC)), "000001", null)
                ))
                .build());
        final Actie actieInhoudOud = administratievehandeling.getActie(1);
        final Actie actieInhoud = administratievehandeling.getActie(2);

        final MetaObject persoon =
            MetaObject.maakBuilder()
                .metId(25252354)
                .metGroep()
                .metGroepElement(ElementConstants.PERSOON_IDENTIFICATIENUMMERS)
                .metRecord()
                .metId(234524)
                .metActieInhoud(actieInhoud)
                .eindeRecord()
                .metRecord()
                .metId(34134)
                .metAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT, OUD_ANUMMER)
                .metActieInhoud(actieInhoudOud)
                .metActieVerval(actieInhoudOud)
                .metActieVervalTbvLeveringMutaties(actieInhoud)
                .eindeRecord()
                .eindeGroep()
                .build();

        // Print vanuit meta model
        LOGGER.info("---[META - Leeg actueel Anummer]-------------------------------------------------");
        LOGGER.info("id: {}", persoon.getObjectsleutel());
        for (final MetaRecord historie : MetaModelUtil.getRecords(persoon, PersoonIdentificatienummersMapper.GROEP_ELEMENT)) {
            LOGGER.info("his: {}", historie.getVoorkomensleutel());
            LOGGER.info(
                "\tAnummer: {}",
                historie.getAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT) == null ? ""
                    : historie.getAttribuut(
                    PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT)
                    .getWaarde());
            LOGGER.info(
                "\tActie inhoud (id={}): {}",
                historie.getActieInhoud() == null ? "" : historie.getActieInhoud().getId(),
                historie.getActieInhoud() == null ? "" : historie.getActieInhoud().getTijdstipRegistratie());
            LOGGER.info(
                "\tActie aanpassing geldigheid (id={}): {}",

                historie.getActieAanpassingGeldigheid() == null ? "" : historie.getActieAanpassingGeldigheid().getId(),
                historie.getActieAanpassingGeldigheid() == null ? "" : historie.getActieAanpassingGeldigheid().getTijdstipRegistratie());
            LOGGER.info(
                "\tActie verval tbv muts (id={}): {}",
                historie.getActieVervalTbvLeveringMutaties() == null ? "" : historie.getActieVervalTbvLeveringMutaties().getId(),
                historie.getActieVervalTbvLeveringMutaties() == null ? "" : historie.getActieVervalTbvLeveringMutaties().getTijdstipRegistratie());
            LOGGER.info("\tNadere aanduiding verval: {}", historie.getNadereAanduidingVerval() == null ? "" : historie.getNadereAanduidingVerval());
        }

        subject = new IdentificatienummerMutatie(persoon, administratievehandeling);
        Assert.assertFalse(subject.isAnummerWijziging());
        Assert.assertNull(subject.getNieuwIdentificatienummersRecord().getAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT));
        Assert.assertEquals(
            OUD_ANUMMER,
            subject.getVervallenIdentificatienummersRecord().getAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT).getWaarde());
    }

    @Test
    public void testNetteBsnWijziging() {

        final AdministratieveHandeling administratievehandeling = AdministratieveHandeling.converter()
            .converteer(TestVerantwoording.maakAdministratieveHandeling(1, "000001", ZonedDateTime.of(1977, 1, 1, 0, 0, 0, 0, ZoneId.of(DatumUtil.UTC)),
                SoortAdministratieveHandeling
                    .GBA_INITIELE_VULLING)
                .metObjecten(Lists.newArrayList(
                    TestVerantwoording.maakActieBuilder(1L, SoortActie.REGISTRATIE_ADRES,
                        ZonedDateTime.of(1977, 1, 1, 0, 0, 0, 0, ZoneId.of(DatumUtil.UTC)), "000001", null),
                    TestVerantwoording.maakActieBuilder(2L, SoortActie.REGISTRATIE_ADRES,
                        ZonedDateTime.of(1977, 1, 1, 0, 0, 0, 0, ZoneId.of(DatumUtil.UTC)), "000001", null)
                ))
                .build());
        final Actie actieInhoudOud = administratievehandeling.getActie(1);
        final Actie actieInhoud = administratievehandeling.getActie(2);

        final MetaObject persoon =
            MetaObject.maakBuilder()
                .metId(25252354)
                .metGroep()
                .metGroepElement(ElementConstants.PERSOON_IDENTIFICATIENUMMERS)
                .metRecord()
                .metId(234524)
                .metAttribuut(PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT, NIEUW_BSN)
                .metActieInhoud(actieInhoud)
                .eindeRecord()
                .metRecord()
                .metId(34134)
                .metAttribuut(PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT, OUD_BSN)
                .metActieInhoud(actieInhoudOud)
                .metActieAanpassingGeldigheid(actieInhoud)
                .metActieVervalTbvLeveringMutaties(actieInhoud)
                .eindeRecord()
                .eindeGroep()
                .build();

        // Print vanuit meta model
        LOGGER.info("---[META - Nette BSN wijziging]-------------------------------------------------");
        LOGGER.info("id: {}", persoon.getObjectsleutel());
        for (final MetaRecord historie : MetaModelUtil.getRecords(persoon, PersoonIdentificatienummersMapper.GROEP_ELEMENT)) {
            LOGGER.info("his: {}", historie.getVoorkomensleutel());
            LOGGER.info(
                "\tBSN: {}",
                historie.getAttribuut(PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT) == null ? ""
                    : historie.getAttribuut(
                    PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT)
                    .getWaarde());
            LOGGER.info(
                "\tActie inhoud (id={}): {}",
                historie.getActieInhoud() == null ? "" : historie.getActieInhoud().getId(),
                historie.getActieInhoud() == null ? "" : historie.getActieInhoud().getTijdstipRegistratie());
            LOGGER.info(
                "\tActie aanpassing geldigheid (id={}): {}",

                historie.getActieAanpassingGeldigheid() == null ? "" : historie.getActieAanpassingGeldigheid().getId(),
                historie.getActieAanpassingGeldigheid() == null ? "" : historie.getActieAanpassingGeldigheid().getTijdstipRegistratie());
            LOGGER.info(
                "\tActie verval tbv muts (id={}): {}",
                historie.getActieVervalTbvLeveringMutaties() == null ? "" : historie.getActieVervalTbvLeveringMutaties().getId(),
                historie.getActieVervalTbvLeveringMutaties() == null ? "" : historie.getActieVervalTbvLeveringMutaties().getTijdstipRegistratie());
            LOGGER.info("\tNadere aanduiding verval: {}", historie.getNadereAanduidingVerval() == null ? "" : historie.getNadereAanduidingVerval());
        }

        subject = new IdentificatienummerMutatie(persoon, administratievehandeling);
        Assert.assertTrue(subject.isBsnWijziging());
        Assert.assertEquals(
            NIEUW_BSN,
            subject.getNieuwIdentificatienummersRecord().getAttribuut(PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT).getWaarde());
        Assert.assertEquals(
            OUD_BSN,
            subject.getVervallenIdentificatienummersRecord().getAttribuut(PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT).getWaarde());
    }

    @Test
    public void testNetteCorrectieBsn() {

        final ZonedDateTime tsReg = ZonedDateTime.of(1977, 1, 1, 0, 0, 0, 0, ZoneId.of(DatumUtil.UTC));
        final AdministratieveHandeling administratievehandeling = AdministratieveHandeling.converter()
            .converteer(TestVerantwoording.maakAdministratieveHandeling(1, "000001", tsReg,
                SoortAdministratieveHandeling
                    .GBA_INITIELE_VULLING)
                .metObjecten(Lists.newArrayList(
                    TestVerantwoording.maakActieBuilder(1L, SoortActie.REGISTRATIE_ADRES, tsReg, "000001", null),
                    TestVerantwoording.maakActieBuilder(2L, SoortActie.REGISTRATIE_ADRES, tsReg, "000001", null)
                ))
                .build());
        final Actie actieInhoudOud = administratievehandeling.getActie(1);
        final Actie actieInhoud = administratievehandeling.getActie(2);

        final MetaObject persoon =
            MetaObject.maakBuilder()
                .metId(25252354)
                .metGroep()
                .metGroepElement(ElementConstants.PERSOON_IDENTIFICATIENUMMERS)
                .metRecord()
                .metId(234524)
                .metAttribuut(PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT, NIEUW_BSN)
                .metActieInhoud(actieInhoud)
                .eindeRecord()
                .metRecord()
                .metId(34134)
                .metAttribuut(PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT, OUD_BSN)
                .metActieInhoud(actieInhoudOud)
                .metActieVerval(actieInhoudOud)
                .metActieVervalTbvLeveringMutaties(actieInhoud)
                .metNadereAanduidingVerval(NadereAanduidingVerval.O.getCode())
                .eindeRecord()
                .eindeGroep()
                .build();

        // Print vanuit meta model
        LOGGER.info("---[META - Nette correctie BSN]-------------------------------------------------");
        LOGGER.info("id: {}", persoon.getObjectsleutel());
        for (final MetaRecord historie : MetaModelUtil.getRecords(persoon, PersoonIdentificatienummersMapper.GROEP_ELEMENT)) {
            LOGGER.info("his: {}", historie.getVoorkomensleutel());
            LOGGER.info(
                "\tBSN: {}",
                historie.getAttribuut(PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT) == null ? ""
                    : historie.getAttribuut(
                    PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT)
                    .getWaarde());
            LOGGER.info(
                "\tActie inhoud (id={}): {}",
                historie.getActieInhoud() == null ? "" : historie.getActieInhoud().getId(),
                historie.getActieInhoud() == null ? "" : historie.getActieInhoud().getTijdstipRegistratie());
            LOGGER.info(
                "\tActie aanpassing geldigheid (id={}): {}",

                historie.getActieAanpassingGeldigheid() == null ? "" : historie.getActieAanpassingGeldigheid().getId(),
                historie.getActieAanpassingGeldigheid() == null ? "" : historie.getActieAanpassingGeldigheid().getTijdstipRegistratie());
            LOGGER.info(
                "\tActie verval tbv muts (id={}): {}",
                historie.getActieVervalTbvLeveringMutaties() == null ? "" : historie.getActieVervalTbvLeveringMutaties().getId(),
                historie.getActieVervalTbvLeveringMutaties() == null ? "" : historie.getActieVervalTbvLeveringMutaties().getTijdstipRegistratie());
            LOGGER.info("\tNadere aanduiding verval: {}", historie.getNadereAanduidingVerval() == null ? "" : historie.getNadereAanduidingVerval());
        }

        subject = new IdentificatienummerMutatie(persoon, administratievehandeling);
        Assert.assertTrue(subject.isBsnWijziging());
        Assert.assertEquals(
            NIEUW_BSN,
            subject.getNieuwIdentificatienummersRecord().getAttribuut(PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT).getWaarde());
        Assert.assertEquals(
            OUD_BSN,
            subject.getVervallenIdentificatienummersRecord().getAttribuut(PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT).getWaarde());
    }

    @Test
    public void testGeenBsnWijziging() {

        final ZonedDateTime tsReg = ZonedDateTime.of(1977, 1, 1, 0, 0, 0, 0, ZoneId.of(DatumUtil.UTC));
        final AdministratieveHandeling administratievehandeling = AdministratieveHandeling.converter()
            .converteer(TestVerantwoording.maakAdministratieveHandeling(1, "000001", tsReg,
                SoortAdministratieveHandeling
                    .GBA_INITIELE_VULLING)
                .metObjecten(Lists.newArrayList(
                    TestVerantwoording.maakActieBuilder(1L, SoortActie.REGISTRATIE_ADRES, tsReg, "000001", null),
                    TestVerantwoording.maakActieBuilder(2L, SoortActie.REGISTRATIE_ADRES, tsReg, "000001", null)
                ))
                .build());
        final Actie actieInhoudOud = administratievehandeling.getActie(1);
        final Actie actieInhoud = administratievehandeling.getActie(2);

        final MetaObject persoon =
            MetaObject.maakBuilder()
                .metId(25252354)
                .metGroep()
                .metGroepElement(ElementConstants.PERSOON_IDENTIFICATIENUMMERS)
                .metRecord()
                .metId(234524)
                .metAttribuut(PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT, NIEUW_BSN)
                .metActieInhoud(actieInhoud)
                .eindeRecord()
                .metRecord()
                .metId(34134)
                .metAttribuut(PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT, NIEUW_BSN)
                .metActieInhoud(actieInhoudOud)
                .metActieVerval(actieInhoudOud)
                .metActieVervalTbvLeveringMutaties(actieInhoud)
                .metNadereAanduidingVerval(NadereAanduidingVerval.O.getCode())
                .eindeRecord()
                .eindeGroep()
                .build();

        // Print vanuit meta model
        LOGGER.info("---[META - Geen BSN wijziging]-------------------------------------------------");
        LOGGER.info("id: {}", persoon.getObjectsleutel());
        for (final MetaRecord historie : MetaModelUtil.getRecords(persoon, PersoonIdentificatienummersMapper.GROEP_ELEMENT)) {
            LOGGER.info("his: {}", historie.getVoorkomensleutel());
            LOGGER.info(
                "\tBSN: {}",
                historie.getAttribuut(PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT) == null ? ""
                    : historie.getAttribuut(
                    PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT)
                    .getWaarde());
            LOGGER.info(
                "\tActie inhoud (id={}): {}",
                historie.getActieInhoud() == null ? "" : historie.getActieInhoud().getId(),
                historie.getActieInhoud() == null ? "" : historie.getActieInhoud().getTijdstipRegistratie());
            LOGGER.info(
                "\tActie aanpassing geldigheid (id={}): {}",

                historie.getActieAanpassingGeldigheid() == null ? "" : historie.getActieAanpassingGeldigheid().getId(),
                historie.getActieAanpassingGeldigheid() == null ? "" : historie.getActieAanpassingGeldigheid().getTijdstipRegistratie());
            LOGGER.info(
                "\tActie verval tbv muts (id={}): {}",
                historie.getActieVervalTbvLeveringMutaties() == null ? "" : historie.getActieVervalTbvLeveringMutaties().getId(),
                historie.getActieVervalTbvLeveringMutaties() == null ? "" : historie.getActieVervalTbvLeveringMutaties().getTijdstipRegistratie());
            LOGGER.info("\tNadere aanduiding verval: {}", historie.getNadereAanduidingVerval() == null ? "" : historie.getNadereAanduidingVerval());
        }

        subject = new IdentificatienummerMutatie(persoon, administratievehandeling);
        Assert.assertFalse(subject.isBsnWijziging());
        Assert.assertEquals(
            NIEUW_BSN,
            subject.getNieuwIdentificatienummersRecord().getAttribuut(PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT).getWaarde());
        Assert.assertEquals(
            NIEUW_BSN,
            subject.getVervallenIdentificatienummersRecord().getAttribuut(PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT).getWaarde());
    }

    @Test
    public void testLegeHistorieBsn() {

        final ZonedDateTime tsReg = ZonedDateTime.of(1977, 1, 1, 0, 0, 0, 0, ZoneId.of(DatumUtil.UTC));
        final AdministratieveHandeling administratievehandeling = AdministratieveHandeling.converter()
            .converteer(TestVerantwoording.maakAdministratieveHandeling(1, "000001", tsReg,
                SoortAdministratieveHandeling
                    .GBA_INITIELE_VULLING)
                .metObjecten(Lists.newArrayList(
                    TestVerantwoording.maakActieBuilder(1L, SoortActie.REGISTRATIE_ADRES, tsReg, "000001", null)
                ))
                .build());
        final Actie actieInhoud = administratievehandeling.getActie(1);

        final MetaObject persoon =
            MetaObject.maakBuilder()
                .metId(25252354)
                .metGroep()
                .metGroepElement(ElementConstants.PERSOON_IDENTIFICATIENUMMERS)
                .metRecord()
                .metId(234524)
                .metAttribuut(PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT, NIEUW_BSN)
                .metActieInhoud(actieInhoud)
                .eindeRecord()
                .eindeGroep()
                .build();

        // Print vanuit meta model
        LOGGER.info("---[META - Lege historie BSN]-------------------------------------------------");
        LOGGER.info("id: {}", persoon.getObjectsleutel());
        for (final MetaRecord historie : MetaModelUtil.getRecords(persoon, PersoonIdentificatienummersMapper.GROEP_ELEMENT)) {
            LOGGER.info("his: {}", historie.getVoorkomensleutel());
            LOGGER.info(
                "\tBSN: {}",
                historie.getAttribuut(PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT) == null ? ""
                    : historie.getAttribuut(
                    PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT)
                    .getWaarde());
            LOGGER.info(
                "\tActie inhoud (id={}): {}",
                historie.getActieInhoud() == null ? "" : historie.getActieInhoud().getId(),
                historie.getActieInhoud() == null ? "" : historie.getActieInhoud().getTijdstipRegistratie());
            LOGGER.info(
                "\tActie aanpassing geldigheid (id={}): {}",

                historie.getActieAanpassingGeldigheid() == null ? "" : historie.getActieAanpassingGeldigheid().getId(),
                historie.getActieAanpassingGeldigheid() == null ? "" : historie.getActieAanpassingGeldigheid().getTijdstipRegistratie());
            LOGGER.info(
                "\tActie verval tbv muts (id={}): {}",
                historie.getActieVervalTbvLeveringMutaties() == null ? "" : historie.getActieVervalTbvLeveringMutaties().getId(),
                historie.getActieVervalTbvLeveringMutaties() == null ? "" : historie.getActieVervalTbvLeveringMutaties().getTijdstipRegistratie());
            LOGGER.info("\tNadere aanduiding verval: {}", historie.getNadereAanduidingVerval() == null ? "" : historie.getNadereAanduidingVerval());
        }

        subject = new IdentificatienummerMutatie(persoon, administratievehandeling);
        Assert.assertFalse(subject.isBsnWijziging());
        Assert.assertEquals(
            NIEUW_BSN,
            subject.getNieuwIdentificatienummersRecord().getAttribuut(PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT).getWaarde());
        Assert.assertNull(subject.getVervallenIdentificatienummersRecord());
    }

    @Test
    public void testLeegActueelBsn() {

        final ZonedDateTime tsReg = ZonedDateTime.of(1977, 1, 1, 0, 0, 0, 0, ZoneId.of(DatumUtil.UTC));
        final AdministratieveHandeling administratievehandeling = AdministratieveHandeling.converter()
            .converteer(TestVerantwoording.maakAdministratieveHandeling(1, "000001", tsReg,
                SoortAdministratieveHandeling
                    .GBA_INITIELE_VULLING)
                .metObjecten(Lists.newArrayList(
                    TestVerantwoording.maakActieBuilder(1L, SoortActie.REGISTRATIE_ADRES, tsReg, "000001", null),
                    TestVerantwoording.maakActieBuilder(2L, SoortActie.REGISTRATIE_ADRES, tsReg, "000001", null)
                ))
                .build());
        final Actie actieInhoudOud = administratievehandeling.getActie(1);
        final Actie actieInhoud = administratievehandeling.getActie(2);

        final MetaObject persoon =
            MetaObject.maakBuilder()
                .metId(25252354)
                .metGroep()
                .metGroepElement(ElementConstants.PERSOON_IDENTIFICATIENUMMERS)
                .metRecord()
                .metId(234524)
                .metActieInhoud(actieInhoud)
                .eindeRecord()
                .metRecord()
                .metId(34134)
                .metAttribuut(PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT, OUD_BSN)
                .metActieInhoud(actieInhoudOud)
                .metActieVerval(actieInhoudOud)
                .metActieVervalTbvLeveringMutaties(actieInhoud)
                .eindeRecord()
                .eindeGroep()
                .build();

        // Print vanuit meta model
        LOGGER.info("---[META - Leeg actueel BSN]-------------------------------------------------");
        LOGGER.info("id: {}", persoon.getObjectsleutel());
        for (final MetaRecord historie : MetaModelUtil.getRecords(persoon, PersoonIdentificatienummersMapper.GROEP_ELEMENT)) {
            LOGGER.info("his: {}", historie.getVoorkomensleutel());
            LOGGER.info(
                "\tBSN: {}",
                historie.getAttribuut(PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT) == null ? ""
                    : historie.getAttribuut(
                    PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT)
                    .getWaarde());
            LOGGER.info(
                "\tActie inhoud (id={}): {}",
                historie.getActieInhoud() == null ? "" : historie.getActieInhoud().getId(),
                historie.getActieInhoud() == null ? "" : historie.getActieInhoud().getTijdstipRegistratie());
            LOGGER.info(
                "\tActie aanpassing geldigheid (id={}): {}",

                historie.getActieAanpassingGeldigheid() == null ? "" : historie.getActieAanpassingGeldigheid().getId(),
                historie.getActieAanpassingGeldigheid() == null ? "" : historie.getActieAanpassingGeldigheid().getTijdstipRegistratie());
            LOGGER.info(
                "\tActie verval tbv muts (id={}): {}",
                historie.getActieVervalTbvLeveringMutaties() == null ? "" : historie.getActieVervalTbvLeveringMutaties().getId(),
                historie.getActieVervalTbvLeveringMutaties() == null ? "" : historie.getActieVervalTbvLeveringMutaties().getTijdstipRegistratie());
            LOGGER.info("\tNadere aanduiding verval: {}", historie.getNadereAanduidingVerval() == null ? "" : historie.getNadereAanduidingVerval());
        }

        subject = new IdentificatienummerMutatie(persoon, administratievehandeling);
        Assert.assertFalse(subject.isBsnWijziging());
        Assert.assertNull(subject.getNieuwIdentificatienummersRecord().getAttribuut(PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT));
        Assert.assertEquals(
            OUD_BSN,
            subject.getVervallenIdentificatienummersRecord().getAttribuut(PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT).getWaarde());
    }
}
