/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import org.junit.Test;

/**
 * Unittest voor {@link FormeleHistorieZonderVerantwoording}.
 */
public class FormeleHistorieZonderVerantwoordingTest {
    @Test
    public void getActueelHistorieVoorkomen() {
        final Set<BetrokkenheidHistorie> historie = new HashSet<>();
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Betrokkenheid betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, relatie);
        final BetrokkenheidHistorie historie1 = new BetrokkenheidHistorie(betrokkenheid);
        final BetrokkenheidHistorie historie2 = new BetrokkenheidHistorie(betrokkenheid);

        final Timestamp nu = new Timestamp(System.currentTimeMillis());
        historie1.setDatumTijdRegistratie(nu);
        historie2.setDatumTijdRegistratie(nu);
        historie2.setDatumTijdVerval(nu);

        assertFalse(FormeleHistorieZonderVerantwoording.heeftActueelVoorkomen(historie));
        assertNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(historie));

        historie.add(historie2);
        assertFalse(FormeleHistorieZonderVerantwoording.heeftActueelVoorkomen(historie));
        assertNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(historie));

        historie.add(historie1);
        assertTrue(FormeleHistorieZonderVerantwoording.heeftActueelVoorkomen(historie));
        assertSame(historie1, FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(historie));
    }

    @Test
    public void getNietVervallenVoorkomens() {
        final Set<BetrokkenheidOuderHistorie> voorkomens = new LinkedHashSet<>();
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Betrokkenheid betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, relatie);
        final BetrokkenheidOuderHistorie voorkomen1 = new BetrokkenheidOuderHistorie(betrokkenheid);
        final BetrokkenheidOuderHistorie voorkomen2 = new BetrokkenheidOuderHistorie(betrokkenheid);
        final BetrokkenheidOuderHistorie voorkomen3 = new BetrokkenheidOuderHistorie(betrokkenheid);

        final Timestamp nu = new Timestamp(System.currentTimeMillis());

        voorkomen1.setDatumTijdRegistratie(nu);
        voorkomen1.setDatumAanvangGeldigheid(20010101);
        voorkomen1.setDatumTijdVerval(nu);
        voorkomen2.setDatumTijdRegistratie(nu);
        voorkomen2.setDatumAanvangGeldigheid(voorkomen1.getDatumAanvangGeldigheid());
        voorkomen2.setDatumEindeGeldigheid(voorkomen1.getDatumAanvangGeldigheid() + 1);
        voorkomen3.setDatumTijdRegistratie(nu);
        voorkomen3.setDatumAanvangGeldigheid(voorkomen2.getDatumEindeGeldigheid());
        voorkomens.add(voorkomen1);
        voorkomens.add(voorkomen2);
        voorkomens.add(voorkomen3);

        assertSame(voorkomen3, FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(voorkomens));
        final List<BetrokkenheidOuderHistorie> nietVervallenVoorkomens = new ArrayList<>(FormeleHistorieZonderVerantwoording.getNietVervallenVoorkomens(voorkomens));
        assertEquals(2, nietVervallenVoorkomens.size());
        assertSame(voorkomen2, nietVervallenVoorkomens.get(0));
        assertSame(voorkomen3, nietVervallenVoorkomens.get(1));
    }

    @Test
    public void testGetDeclaredEntityHistoryFields() {
        assertEquals(5, FormeleHistorieZonderVerantwoording.getDeclaredEntityHistoryFields(PersoonGeboorteHistorie.class, AbstractFormeleHistorie.class)
            .size());
    }
}
