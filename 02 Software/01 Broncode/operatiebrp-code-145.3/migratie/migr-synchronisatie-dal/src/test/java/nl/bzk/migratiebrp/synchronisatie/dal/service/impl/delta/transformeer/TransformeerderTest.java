/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;

import org.junit.Before;
import org.junit.Test;

public class TransformeerderTest {

    private Transformeerder transformeerder;
    private Persoon persoonOud;
    private Persoon persoonNieuw;
    private DeltaBepalingContext context;

    @Before
    public void setup() {
        SynchronisatieLogging.init();
        persoonOud = new Persoon(SoortPersoon.INGESCHREVENE);
        persoonNieuw = new Persoon(SoortPersoon.INGESCHREVENE);
        final Timestamp datumTijdRegistratie = new Timestamp(System.currentTimeMillis());
        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(new Partij("partij", "000000"), SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL, datumTijdRegistratie);
        administratieveHandeling.setDatumTijdRegistratie(datumTijdRegistratie);
        persoonNieuw.addPersoonAfgeleidAdministratiefHistorie(new PersoonAfgeleidAdministratiefHistorie(
                (short) 1,
                persoonNieuw,
                administratieveHandeling,
                datumTijdRegistratie));
        context = new DeltaBepalingContext(persoonNieuw, persoonOud, null, false);
        transformeerder = Transformeerder.maakTransformeerder();
    }

    @Test
    public void testNieuweRij() {
        final VerschilGroep verschilGroep = maakVerschilGroep(VerschilType.RIJ_TOEGEVOEGD);
        final List<VerschilGroep> result =
                transformeerder.transformeer(Collections.singletonList(verschilGroep), maakActieVervalTbvLeveringMutaties(), context);
        assertEquals(1, result.size());
        assertSame(verschilGroep, result.get(0));

        assertEquals(
                "Resultaat: null (null)\nBeslissingen: \nPersoonIDHistorie (DW-002-ACT) PersoonIDHistorie.waarde RIJ_TOEGEVOEGD",
                SynchronisatieLogging.getMelding());
    }

    @Test
    public void testVerwijderdeRij() {
        final VerschilGroep verschilGroep = maakVerschilGroep(VerschilType.RIJ_VERWIJDERD);
        final List<VerschilGroep> result =
                transformeerder.transformeer(Collections.singletonList(verschilGroep), maakActieVervalTbvLeveringMutaties(), context);
        assertEquals(1, result.size());
        assertNotSame(verschilGroep, result.get(0));
        assertEquals(4, result.get(0).size());

        assertEquals("Resultaat: null (null)\n"
                + "Beslissingen: \n"
                + "PersoonIDHistorie (DW-001) PersoonIDHistorie.actieVerval ELEMENT_NIEUW\n"
                + "PersoonIDHistorie (DW-001) PersoonIDHistorie.actieVervalTbvLeveringMutaties ELEMENT_NIEUW\n"
                + "PersoonIDHistorie (DW-001) PersoonIDHistorie.datumTijdVerval ELEMENT_NIEUW\n"
                + "PersoonIDHistorie (DW-001) PersoonIDHistorie.indicatieVoorkomenTbvLeveringMutaties ELEMENT_NIEUW", SynchronisatieLogging.getMelding());
    }

    @Test
    public void testAangepasteRij() {
        final VerschilGroep verschilGroep = maakVerschilGroep(VerschilType.ELEMENT_AANGEPAST);
        final List<VerschilGroep> result =
                transformeerder.transformeer(Collections.singletonList(verschilGroep), maakActieVervalTbvLeveringMutaties(), context);
        assertEquals(1, result.size());
        assertNotSame(verschilGroep, result.get(0));

        assertEquals(5, result.get(0).size());
        assertEquals(VerschilType.RIJ_TOEGEVOEGD, result.get(0).get(0).getVerschilType());
        assertEquals(VerschilType.ELEMENT_NIEUW, result.get(0).get(1).getVerschilType());
        assertEquals(VerschilType.ELEMENT_NIEUW, result.get(0).get(2).getVerschilType());
        assertEquals(VerschilType.ELEMENT_NIEUW, result.get(0).get(3).getVerschilType());
        assertEquals(VerschilType.ELEMENT_NIEUW, result.get(0).get(4).getVerschilType());

        assertEquals("Resultaat: null (null)\n"
                + "Beslissingen: \n"
                + "PersoonIDHistorie (DW-003) Persoon.historieSet RIJ_TOEGEVOEGD\n"
                + "PersoonIDHistorie (DW-003) PersoonIDHistorie.actieVerval ELEMENT_NIEUW\n"
                + "PersoonIDHistorie (DW-003) PersoonIDHistorie.actieVervalTbvLeveringMutaties ELEMENT_NIEUW\n"
                + "PersoonIDHistorie (DW-003) PersoonIDHistorie.datumTijdVerval ELEMENT_NIEUW\n"
                + "PersoonIDHistorie (DW-003) PersoonIDHistorie.indicatieVoorkomenTbvLeveringMutaties ELEMENT_NIEUW", SynchronisatieLogging.getMelding());
    }

    private VerschilGroep maakVerschilGroep(final VerschilType verschilType) {
        final PersoonIDHistorie oudeRij = new PersoonIDHistorie(persoonOud);
        final PersoonIDHistorie nieuweRij = new PersoonIDHistorie(persoonNieuw);

        final EntiteitSleutel eigenaarSleutel = new EntiteitSleutel(Persoon.class, "historieSet", null);
        final EntiteitSleutel sleutel = new EntiteitSleutel(PersoonIDHistorie.class, "waarde", eigenaarSleutel);
        sleutel.addSleuteldeel("tsReg", 100);
        final Verschil verschil = new Verschil(sleutel, 10, 20, verschilType, oudeRij, nieuweRij);

        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(oudeRij);
        verschilGroep.addVerschil(verschil);
        return verschilGroep;
    }

    private BRPActie maakActieVervalTbvLeveringMutaties() {
        final AdministratieveHandeling admHnd =
                new AdministratieveHandeling(new Partij("testPartij", "000606"), SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL, new Timestamp(
                        System.currentTimeMillis()));
        admHnd.setDatumTijdRegistratie(new Timestamp(System.currentTimeMillis()));
        return new BRPActie(SoortActie.CONVERSIE_GBA, admHnd, admHnd.getPartij(), admHnd.getDatumTijdRegistratie());
    }
}
