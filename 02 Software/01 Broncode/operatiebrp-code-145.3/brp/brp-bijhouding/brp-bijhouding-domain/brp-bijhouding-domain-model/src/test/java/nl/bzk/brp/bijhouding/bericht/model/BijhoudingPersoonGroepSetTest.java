/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNaamgebruikHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unittest voor {@link BijhoudingPersoonGroepSet}.
 */
@RunWith(MockitoJUnitRunner.class)
public class BijhoudingPersoonGroepSetTest {


    @Test
    public void testPersoonIDHistorie() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final Persoon persoon2 = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonIDHistorie historie1 = new PersoonIDHistorie(persoon);
        historie1.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        historie1.setDatumAanvangGeldigheid(2010_01_01);
        persoon.addPersoonIDHistorie(historie1);

        final Set<PersoonIDHistorie> historieSet = new BijhoudingPersoonGroepSet<>();
        final BijhoudingPersoonGroepSet<PersoonIDHistorie> castedSet = (BijhoudingPersoonGroepSet<PersoonIDHistorie>) historieSet;

        assertTrue(historieSet.isEmpty());
        assertEquals(0, historieSet.size());

        castedSet.addPersoonGroepSet(persoon, persoon.getPersoonIDHistorieSet());
        castedSet.addPersoonGroepSet(persoon2, persoon2.getPersoonIDHistorieSet());
        assertFalse(historieSet.isEmpty());
        assertEquals(1, historieSet.size());
        assertTrue(historieSet.contains(historie1));
        assertNotNull(historieSet.iterator());
        assertNotNull(historieSet.toArray());
        assertNotNull(historieSet.toArray(new PersoonIDHistorie[1]));
        assertTrue(historieSet.containsAll(Collections.singleton(historie1)));

        final PersoonIDHistorie historie2 = new PersoonIDHistorie(persoon);
        historie2.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        historie2.setActieInhoud(mock(BRPActie.class));
        historie2.setDatumAanvangGeldigheid(2010_01_01);
        castedSet.add(historie2);

        assertEquals(2, persoon.getPersoonIDHistorieSet().size());
        assertNull(historie1.getDatumTijdVerval());
        assertNull(historie1.getActieVerval());
        for (final PersoonIDHistorie historie : persoon.getPersoonIDHistorieSet()) {
            assertEquals(persoon, historie.getPersoon());
        }
        assertEquals(1, persoon2.getPersoonIDHistorieSet().size());
        for (final PersoonIDHistorie historie : persoon2.getPersoonIDHistorieSet()) {
            assertEquals(persoon2, historie.getPersoon());
        }
    }

    @Test
    public void testPersoonGeboorteHistorie() {
        final LandOfGebied landOfGebied = new LandOfGebied("0001", "Land");
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final Persoon persoon2 = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonGeboorteHistorie historie1 = new PersoonGeboorteHistorie(persoon, 2010_01_01, landOfGebied);
        historie1.setDatumTijdRegistratie(Timestamp.from(Instant.now().minus(1, ChronoUnit.HOURS)));
        persoon.addPersoonGeboorteHistorie(historie1);

        final BijhoudingPersoonGroepSet historieSet = new BijhoudingPersoonGroepSet<>();

        historieSet.addPersoonGroepSet(persoon, persoon.getPersoonGeboorteHistorieSet());
        historieSet.addPersoonGroepSet(persoon2, persoon2.getPersoonGeboorteHistorieSet());

        assertFalse(historieSet.isEmpty());
        assertEquals(1, historieSet.size());

        final PersoonGeboorteHistorie historie2 = new PersoonGeboorteHistorie(persoon, 2016_01_01, landOfGebied);
        historie2.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        historie2.setActieInhoud(mock(BRPActie.class));
        historieSet.add(historie2);

        assertEquals(2, persoon.getPersoonGeboorteHistorieSet().size());
        assertNotNull(historie1.getDatumTijdVerval());
        assertNotNull(historie1.getActieVerval());
        for (final PersoonGeboorteHistorie historie : persoon.getPersoonGeboorteHistorieSet()) {
            assertEquals(persoon, historie.getPersoon());
        }
        assertEquals(1, persoon2.getPersoonGeboorteHistorieSet().size());
        for (final PersoonGeboorteHistorie historie : persoon2.getPersoonGeboorteHistorieSet()) {
            assertEquals(persoon2, historie.getPersoon());
        }
    }

    @Test
    public void testPersoonSamengesteldeNaamHistorie() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final Persoon persoon2 = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonSamengesteldeNaamHistorie historie1 = new PersoonSamengesteldeNaamHistorie(persoon, "Stam", true, false);
        historie1.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        historie1.setDatumAanvangGeldigheid(2010_01_01);
        persoon.addPersoonSamengesteldeNaamHistorie(historie1);

        final BijhoudingPersoonGroepSet historieSet = new BijhoudingPersoonGroepSet<>();

        historieSet.addPersoonGroepSet(persoon, persoon.getPersoonSamengesteldeNaamHistorieSet());
        historieSet.addPersoonGroepSet(persoon2, persoon2.getPersoonSamengesteldeNaamHistorieSet());

        assertFalse(historieSet.isEmpty());
        assertEquals(1, historieSet.size());

        final PersoonSamengesteldeNaamHistorie historie2 = new PersoonSamengesteldeNaamHistorie(persoon, "Mast", true, false);
        historie2.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        historie2.setActieInhoud(mock(BRPActie.class));
        historie2.setDatumAanvangGeldigheid(2010_01_01);
        historieSet.add(historie2);

        assertEquals(2, persoon.getPersoonSamengesteldeNaamHistorieSet().size());
        assertNull(historie1.getDatumTijdVerval());
        assertNull(historie1.getActieVerval());
        for (final PersoonSamengesteldeNaamHistorie historie : persoon.getPersoonSamengesteldeNaamHistorieSet()) {
            assertEquals(persoon, historie.getPersoon());
        }
        assertEquals(1, persoon2.getPersoonSamengesteldeNaamHistorieSet().size());
        for (final PersoonSamengesteldeNaamHistorie historie : persoon2.getPersoonSamengesteldeNaamHistorieSet()) {
            assertEquals(persoon2, historie.getPersoon());
        }
    }

    @Test
    public void testPersoonGeslachtsaanduidingHistorie() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final Persoon persoon2 = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonGeslachtsaanduidingHistorie historie1 = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.MAN);
        historie1.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        historie1.setDatumAanvangGeldigheid(2010_01_01);
        persoon.addPersoonGeslachtsaanduidingHistorie(historie1);

        final BijhoudingPersoonGroepSet historieSet = new BijhoudingPersoonGroepSet<>();

        historieSet.addPersoonGroepSet(persoon, persoon.getPersoonGeslachtsaanduidingHistorieSet());
        historieSet.addPersoonGroepSet(persoon2, persoon2.getPersoonGeslachtsaanduidingHistorieSet());

        assertFalse(historieSet.isEmpty());
        assertEquals(1, historieSet.size());

        final PersoonGeslachtsaanduidingHistorie historie2 = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.VROUW);
        historie2.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        historie2.setActieInhoud(mock(BRPActie.class));
        historie2.setDatumAanvangGeldigheid(2010_01_01);
        historieSet.add(historie2);

        assertEquals(2, persoon.getPersoonGeslachtsaanduidingHistorieSet().size());
        assertNull(historie1.getDatumTijdVerval());
        assertNull(historie1.getActieVerval());
        for (final PersoonGeslachtsaanduidingHistorie historie : persoon.getPersoonGeslachtsaanduidingHistorieSet()) {
            assertEquals(persoon, historie.getPersoon());
        }
        assertEquals(1, persoon2.getPersoonGeslachtsaanduidingHistorieSet().size());
        for (final PersoonGeslachtsaanduidingHistorie historie : persoon2.getPersoonGeslachtsaanduidingHistorieSet()) {
            assertEquals(persoon2, historie.getPersoon());
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testPersoonNaamgebruikHistorie() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final Persoon persoon2 = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonNaamgebruikHistorie historie1 = new PersoonNaamgebruikHistorie(persoon, "Stam", true, Naamgebruik.EIGEN);
        historie1.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        persoon.addPersoonNaamgebruikHistorie(historie1);

        final BijhoudingPersoonGroepSet historieSet = new BijhoudingPersoonGroepSet<>();

        historieSet.addPersoonGroepSet(persoon, persoon.getPersoonNaamgebruikHistorieSet());
        historieSet.addPersoonGroepSet(persoon2, persoon2.getPersoonNaamgebruikHistorieSet());

        assertFalse(historieSet.isEmpty());
        assertEquals(1, historieSet.size());

        final PersoonNaamgebruikHistorie historie2 = new PersoonNaamgebruikHistorie(persoon, "Stam", true, Naamgebruik.PARTNER);
        historie2.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        historie2.setActieInhoud(mock(BRPActie.class));
        historieSet.add(historie2);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() {
        new BijhoudingPersoonGroepSet<PersoonIDHistorie>().remove(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveAll() {
        new BijhoudingPersoonGroepSet<PersoonIDHistorie>().removeAll(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAll() {
        new BijhoudingPersoonGroepSet<PersoonIDHistorie>().addAll(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRetainAll() {
        new BijhoudingPersoonGroepSet<PersoonIDHistorie>().retainAll(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testClear() {
        new BijhoudingPersoonGroepSet<PersoonIDHistorie>().clear();
    }
}