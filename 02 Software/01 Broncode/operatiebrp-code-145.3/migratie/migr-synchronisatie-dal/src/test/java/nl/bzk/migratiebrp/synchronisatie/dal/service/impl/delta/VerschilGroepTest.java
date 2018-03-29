/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AbstractEntiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.StapelVoorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.Sleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.IstSleutel;

/**
 * Unittest voor {@link VerschilGroep}.
 */
public class VerschilGroepTest {

    private FormeleHistorie historie;
    private Persoon persoon;
    private Partij partij;

    @Before
    public void setUp() {
        persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        partij = new Partij("naam", "000001");
        historie = new PersoonBijhoudingHistorie(persoon, partij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testMaakVerschilGroepMetHistorie() {
        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(historie);
        assertTrue(verschilGroep.getVerschillen().isEmpty());
        assertTrue(verschilGroep.isEmpty());
        assertTrue(verschilGroep.size() == 0);
        assertEquals(historie, verschilGroep.getFormeleHistorie());
        assertTrue(verschilGroep.isHistorieVerschil());
        assertFalse(verschilGroep.iterator().hasNext());
        assertNull(verschilGroep.getHistorieGroepClass());
        assertNull(verschilGroep.get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testMaakVerschilGroepZonderHistorie() {
        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepZonderHistorie();
        assertTrue(verschilGroep.getVerschillen().isEmpty());
        assertTrue(verschilGroep.isEmpty());
        assertTrue(verschilGroep.size() == 0);
        assertNull(verschilGroep.getFormeleHistorie());
        assertFalse(verschilGroep.isHistorieVerschil());
        assertFalse(verschilGroep.iterator().hasNext());
        assertNull(verschilGroep.getHistorieGroepClass());
        assertNull(verschilGroep.get(0));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testMaakVerschilGroepMetHistorieMetVerschillen() {
        final Sleutel sleutel = new EntiteitSleutel(PersoonBijhoudingHistorie.class, "actieInhoud");
        final Verschil verschil = Verschil.maakVerschil(sleutel, null, historie, null, historie);

        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(historie);
        verschilGroep.addVerschil(verschil);
        assertFalse(verschilGroep.getVerschillen().isEmpty());
        assertFalse(verschilGroep.isEmpty());
        assertTrue(verschilGroep.size() == 1);
        assertEquals(historie, verschilGroep.getFormeleHistorie());
        assertTrue(verschilGroep.isHistorieVerschil());
        assertTrue(verschilGroep.iterator().hasNext());
        assertEquals(verschil, verschilGroep.get(0));
        assertEquals(historie.getClass(), verschilGroep.getHistorieGroepClass());

        verschilGroep.getVerschillen().add(verschil);
    }

    @Test
    public void testMaakKopieZonderVerschillen() {
        final Sleutel sleutel = new EntiteitSleutel(PersoonBijhoudingHistorie.class, "actieInhoud");
        final Verschil verschil = Verschil.maakVerschil(sleutel, historie, null, historie, null);

        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(historie);
        verschilGroep.addVerschillen(Collections.singletonList(verschil));
        assertFalse(verschilGroep.getVerschillen().isEmpty());
        assertFalse(verschilGroep.isEmpty());
        assertTrue(verschilGroep.size() == 1);
        assertEquals(historie, verschilGroep.getFormeleHistorie());
        assertTrue(verschilGroep.isHistorieVerschil());
        assertTrue(verschilGroep.iterator().hasNext());
        assertEquals(verschil, verschilGroep.get(0));
        assertEquals(historie.getClass(), verschilGroep.getHistorieGroepClass());

        final VerschilGroep kopieVerschilGroep = VerschilGroep.maakKopieZonderVerschillen(verschilGroep);
        assertTrue(kopieVerschilGroep.getVerschillen().isEmpty());
        assertTrue(kopieVerschilGroep.isEmpty());
        assertTrue(kopieVerschilGroep.size() == 0);
        assertEquals(historie, kopieVerschilGroep.getFormeleHistorie());
        assertTrue(kopieVerschilGroep.isHistorieVerschil());
        assertFalse(kopieVerschilGroep.iterator().hasNext());
        assertNull(kopieVerschilGroep.getHistorieGroepClass());
    }

    @Test
    public void testToStringEntiteitSleutel() {
        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(historie);
        assertEquals("", verschilGroep.toString());

        final Sleutel sleutel = new EntiteitSleutel(PersoonBijhoudingHistorie.class, "actieInhoud");
        final Verschil verschil = Verschil.maakVerschil(sleutel, historie, null, historie, null);
        verschilGroep.addVerschil(verschil);
        assertEquals("nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie, actieInhoud", verschilGroep.toString());
    }

    @Test
    public void testToStringIstSleutelVoorStapel() {
        final Stapel stapel = new Stapel(persoon, "01", 0);
        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepZonderHistorie();
        assertEquals("", verschilGroep.toString());

        final Sleutel sleutel = new IstSleutel(stapel, true);
        final Verschil verschil = Verschil.maakVerschil(sleutel, historie, null, historie, null);
        verschilGroep.addVerschil(verschil);
        assertEquals("IST-01-00", verschilGroep.toString());
    }

    @Test
    public void testToStringIstSleutelVoorStapelVoorkomen() {
        final Stapel stapel = new Stapel(persoon, "01", 0);
        final StapelVoorkomen stapelVoorkomen = new StapelVoorkomen(stapel, 0, new AdministratieveHandeling(partij,
                SoortAdministratieveHandeling.GBA_INITIELE_VULLING, new Timestamp(System.currentTimeMillis())));

        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepZonderHistorie();
        assertEquals("", verschilGroep.toString());

        final Sleutel sleutel = new IstSleutel(stapelVoorkomen, "naam", true);
        final Verschil verschil = Verschil.maakVerschil(sleutel, historie, null, historie, null);
        verschilGroep.addVerschil(verschil);
        assertEquals("IST-01-00-00", verschilGroep.toString());
    }

    @Test
    public void testFilterOnderzoekVerschillen() {
        final Sleutel sleutel = new EntiteitSleutel(PersoonBijhoudingHistorie.class, AbstractEntiteit.GEGEVEN_IN_ONDERZOEK);
        final Verschil verschil = Verschil.maakVerschil(sleutel, historie, null, historie, null);

        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(historie);
        verschilGroep.addVerschillen(Collections.singletonList(verschil));
        assertTrue(verschilGroep.size() == 1);

        final List<Verschil> verschillen = verschilGroep.filterOnderzoekVerschillen();
        assertTrue(verschilGroep.isEmpty());
        assertTrue(verschillen.size() == 1);
        assertEquals(verschil, verschillen.get(0));
    }
}
