/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.Iterables;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import org.junit.Before;
import org.junit.Test;

public class ActieTest {

    private static final Long ACTIE_ID = 1L;
    private static final Integer ACTIEBRON_ID = 2;
    private static final String ACTIEBRON_RECHTSGROND = "Rechtsgrond";
    private static final String ACTIEBRON_RECHTSGRONDOMSCHRIJVING = "Rechtsgrondomschrijving";
    private static final SoortActie SOORT_ACTIE = SoortActie.BEEINDIGING_VOORNAAM;
    private static final ZonedDateTime ZDT_NU = ZonedDateTime.now();
    private static final Integer DATUM_ONTLENING = 20140101;
    private static final String PARTIJCODE = "123456";
    private static final SoortAdministratieveHandeling SOORT_ADMINISTRATIEVE_HANDELING = SoortAdministratieveHandeling.AANVANG_ONDERZOEK;

    private AdministratieveHandeling administratieveHandeling;
    private Actie actie;
    private Actiebron actiebron;
    private MetaObject actieMetaObject;

    @Before
    public void voorTest() {
        final MetaObject.Builder actieMetaObjectBuilder = TestVerantwoording.maakActieBuilder(ACTIE_ID, SOORT_ACTIE, ZDT_NU, PARTIJCODE, DATUM_ONTLENING);
        final MetaObject.Builder actiebronMetaObjectBuilder =
                TestVerantwoording.maakActiebronBuilder(ACTIEBRON_ID, ACTIEBRON_RECHTSGROND, ACTIEBRON_RECHTSGRONDOMSCHRIJVING);
        actieMetaObjectBuilder.metObject(actiebronMetaObjectBuilder);
        final MetaObject actiebronMetaObject = actiebronMetaObjectBuilder.build();

        actieMetaObject = actieMetaObjectBuilder.build();
        administratieveHandeling = TestVerantwoording.maakAdministratieveHandeling(SOORT_ADMINISTRATIEVE_HANDELING);
        actie = Actie.converter().converteer(actieMetaObject, administratieveHandeling);
        actiebron = Actiebron.converter().converteer(actiebronMetaObject, actie);
    }

    @Test
    public void testConverter() {
        assertEquals(actieMetaObject, actie.getMetaObject());
        assertEquals(administratieveHandeling, actie.getAdministratieveHandeling());
        assertEquals(ACTIE_ID, actie.getId());
        assertEquals(SOORT_ACTIE, actie.getSoort());
        assertEquals(PARTIJCODE, actie.getPartijCode());
        assertEquals(ZDT_NU, actie.getTijdstipRegistratie());
        assertEquals(DATUM_ONTLENING, actie.getDatumOntlening());
        assertEquals(1, actie.getBronnen().size());
        assertEquals(actiebron, Iterables.getOnlyElement(actie.getBronnen()));
    }

    @Test
    public void testToString() {
        assertEquals("1", actie.toString());
    }

    @Test
    public void testEquals() {
        assertTrue(actie.equals(actie));
        //gelijk id, dus gelijk
        assertTrue(actie.equals(TestVerantwoording.maakActie(ACTIE_ID)));
        assertFalse(actie.equals(null));
        assertFalse(actie.equals(actiebron));
    }
}
