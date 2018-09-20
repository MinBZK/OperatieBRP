/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIDHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;

import org.junit.Before;
import org.junit.Test;

public class TransformatieDw003Test extends AbstractTransformatieTest {
    private Transformatie transformatie;

    @Before
    public void setup() {
        transformatie = new TransformatieDw003();
    }

    @Test
    public void testAccept() {
        final VerschilGroep verschilGroep = maakPersoonIDVerschilGroep(VerschilType.ELEMENT_AANGEPAST);
        assertTrue(transformatie.accept(verschilGroep));

        final VerschilGroep verschilGroep2 = maakPersoonIDVerschilGroep(VerschilType.RIJ_VERWIJDERD, false, 2);
        assertTrue(transformatie.accept(verschilGroep2));
    }

    @Test
    public void testNotAccept() {
        final VerschilGroep verschilGroep = maakPersoonIDVerschilGroep(VerschilType.RIJ_VERWIJDERD);
        assertFalse(transformatie.accept(verschilGroep));

        final VerschilGroep verschilGroep2 = maakPersoonIDVerschilGroep(VerschilType.RIJ_TOEGEVOEGD);
        assertFalse(transformatie.accept(verschilGroep2));
    }

    @Test
    public void testExecute() {
        final PersoonIDHistorie oudeRij = new PersoonIDHistorie(persoonOud);
        final PersoonIDHistorie nieuweRij = new PersoonIDHistorie(persoonNieuw);

        final EntiteitSleutel eigenaarSleutel = new EntiteitSleutel(Persoon.class, "historieSet", null);
        final EntiteitSleutel sleutel = new EntiteitSleutel(PersoonIDHistorie.class, "waarde", eigenaarSleutel);
        sleutel.addSleuteldeel("tsReg", 100);
        final Verschil verschil = new Verschil(sleutel, 10, 20, VerschilType.ELEMENT_AANGEPAST, oudeRij, nieuweRij);

        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(oudeRij);
        verschilGroep.addVerschil(verschil);

        final VerschilGroep result = transformatie.execute(verschilGroep, maakDummyActie(), context);

        assertEquals(5, result.size());

        assertEquals(VerschilType.RIJ_TOEGEVOEGD, result.get(0).getVerschilType());
        assertSame(nieuweRij, result.get(0).getNieuweWaarde());

        assertEquals(VerschilType.ELEMENT_NIEUW, result.get(1).getVerschilType());
        assertSame(oudeRij, result.get(1).getBestaandeHistorieRij());
        assertEquals(VerschilType.ELEMENT_NIEUW, result.get(2).getVerschilType());
        assertSame(oudeRij, result.get(1).getBestaandeHistorieRij());
        assertEquals(VerschilType.ELEMENT_NIEUW, result.get(3).getVerschilType());
        assertSame(oudeRij, result.get(1).getBestaandeHistorieRij());
        assertEquals(VerschilType.ELEMENT_NIEUW, result.get(4).getVerschilType());
        assertSame(oudeRij, result.get(1).getBestaandeHistorieRij());

        assertEquals("historieSet", result.get(0).getSleutel().getVeld());
        assertEquals(1, result.get(0).getSleutel().getDelen().size());
        assertEquals("tsReg", result.get(0).getSleutel().getDelen().entrySet().iterator().next().getKey());
        assertEquals(100, result.get(0).getSleutel().getDelen().entrySet().iterator().next().getValue());
    }

    @Test
    public void testGetCode() {
        assertEquals(DeltaWijziging.DW_003, transformatie.getDeltaWijziging());
    }
}
