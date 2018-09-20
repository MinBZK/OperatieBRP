/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Geslachtsaanduiding;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Naamgebruik;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNaamgebruikHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import org.junit.Before;
import org.junit.Test;

public class AbstractEntityHistorieConverterTest {

    private Naamgebruik naamgebruik;
    private String geslachtsnaamstamNaamgebruik;
    private boolean indicatieNaamgebruikAfgeleid;
    private Persoon persoon;

    @Before
    public void setUp() {
        naamgebruik = Naamgebruik.EIGEN;
        geslachtsnaamstamNaamgebruik = "Geslachtsnaamstam";
        indicatieNaamgebruikAfgeleid = true;
        persoon = new Persoon(SoortPersoon.INGESCHREVENE);
    }

    @Test
    public void testFormeleHistorieActueel() {
        final PersoonNaamgebruikHistorie historie =
                new PersoonNaamgebruikHistorie(persoon, geslachtsnaamstamNaamgebruik, indicatieNaamgebruikAfgeleid, naamgebruik);
        final AbstractEntityHistorieConverter<PersoonNaamgebruikHistorie> converter = new PersoonNaamgebruikHistorieConverter();

        final Set<PersoonNaamgebruikHistorie> historieSet = new HashSet<>();
        historieSet.add(historie);
        final Object result = converter.getActueel(historieSet);
        assertNotNull(result);
        assertEquals(historie, result);
    }

    @Test
    public void testFormeleHistorieGeenActueel() {
        final PersoonNaamgebruikHistorie historie =
                new PersoonNaamgebruikHistorie(persoon, geslachtsnaamstamNaamgebruik, indicatieNaamgebruikAfgeleid, naamgebruik);
        historie.setDatumTijdVerval(new Timestamp(1l));

        final AbstractEntityHistorieConverter<PersoonNaamgebruikHistorie> converter = new PersoonNaamgebruikHistorieConverter();
        final Set<PersoonNaamgebruikHistorie> historieSet = new HashSet<>();
        historieSet.add(historie);
        final Object result = converter.getActueel(historieSet);
        assertNull(result);
    }

    @Test
    public void testFormeleHistorieMeerdereActueel() {
        final PersoonNaamgebruikHistorie historie =
                new PersoonNaamgebruikHistorie(persoon, geslachtsnaamstamNaamgebruik, indicatieNaamgebruikAfgeleid, naamgebruik);
        final PersoonNaamgebruikHistorie historie2 =
                new PersoonNaamgebruikHistorie(persoon, "Jansen", indicatieNaamgebruikAfgeleid, naamgebruik);
        final AbstractEntityHistorieConverter<PersoonNaamgebruikHistorie> converter = new PersoonNaamgebruikHistorieConverter();

        final Set<PersoonNaamgebruikHistorie> historieSet = new HashSet<>();
        historieSet.add(historie);
        historieSet.add(historie2);
        try {
            converter.getActueel(historieSet);
            fail("Verwacht een IllegalStateException");
        } catch (final IllegalStateException ise) {
            assertNotNull(ise);
        }
    }

    @Test
    public void testMaterieleHistorieActueel() {
        final PersoonGeslachtsaanduidingHistorie historie = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.MAN);
        final AbstractEntityHistorieConverter<PersoonGeslachtsaanduidingHistorie> converter =
                new PersoonGeslachtsaanduidingHistorieConverter();

        final Set<PersoonGeslachtsaanduidingHistorie> historieSet = new HashSet<>();
        historieSet.add(historie);
        final Object result = converter.getActueel(historieSet);
        assertNotNull(result);
        assertEquals(historie, result);
    }

    @Test
    public void testMaterieleHistorieGeenActueelDoorGeldigheid() {
        final PersoonGeslachtsaanduidingHistorie historie = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.MAN);
        historie.setDatumEindeGeldigheid(20120101);
        final AbstractEntityHistorieConverter<PersoonGeslachtsaanduidingHistorie> converter =
                new PersoonGeslachtsaanduidingHistorieConverter();

        final Set<PersoonGeslachtsaanduidingHistorie> historieSet = new HashSet<>();
        historieSet.add(historie);
        final Object result = converter.getActueel(historieSet);
        assertNull(result);
    }

    @Test
    public void testMaterieleHistorieGeenActueelDoorVerval() {
        final PersoonGeslachtsaanduidingHistorie historie = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.MAN);
        historie.setDatumTijdVerval(new Timestamp(1l));
        final AbstractEntityHistorieConverter<PersoonGeslachtsaanduidingHistorie> converter =
                new PersoonGeslachtsaanduidingHistorieConverter();

        final Set<PersoonGeslachtsaanduidingHistorie> historieSet = new HashSet<>();
        historieSet.add(historie);
        final Object result = converter.getActueel(historieSet);
        assertNull(result);
    }
}
