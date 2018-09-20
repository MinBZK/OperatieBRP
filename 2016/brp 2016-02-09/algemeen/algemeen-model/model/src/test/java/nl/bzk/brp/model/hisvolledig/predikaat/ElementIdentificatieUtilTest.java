/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaat;

import static nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import org.junit.Test;

public class ElementIdentificatieUtilTest {

    @Test
    public void testGeefElementEnumVoorRelatieStandaardGroep() throws Exception {
        assertEquals(HUWELIJK_STANDAARD, ElementIdentificatieUtil.geefElementEnumVoorRelatieStandaardGroep(SoortRelatie.HUWELIJK));
        assertEquals(GEREGISTREERDPARTNERSCHAP_STANDAARD, ElementIdentificatieUtil.geefElementEnumVoorRelatieStandaardGroep(SoortRelatie.GEREGISTREERD_PARTNERSCHAP));
        assertEquals(FAMILIERECHTELIJKEBETREKKING_STANDAARD, ElementIdentificatieUtil.geefElementEnumVoorRelatieStandaardGroep(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING));
        assertEquals(NAAMSKEUZEONGEBORENVRUCHT_STANDAARD, ElementIdentificatieUtil.geefElementEnumVoorRelatieStandaardGroep(SoortRelatie.NAAMSKEUZE_ONGEBOREN_VRUCHT));
        assertEquals(ERKENNINGONGEBORENVRUCHT_STANDAARD, ElementIdentificatieUtil.geefElementEnumVoorRelatieStandaardGroep(SoortRelatie.ERKENNING_ONGEBOREN_VRUCHT));
        assertNull(ElementIdentificatieUtil.geefElementEnumVoorRelatieStandaardGroep(SoortRelatie.DUMMY));
    }

    @Test
    public void testGeefElementEnumVoorRelatieIdentiteitGroep() throws Exception {
        assertEquals(HUWELIJK_IDENTITEIT, ElementIdentificatieUtil.geefElementEnumVoorRelatieIdentiteitGroep(SoortRelatie.HUWELIJK));
        assertEquals(GEREGISTREERDPARTNERSCHAP_IDENTITEIT, ElementIdentificatieUtil.geefElementEnumVoorRelatieIdentiteitGroep(SoortRelatie.GEREGISTREERD_PARTNERSCHAP));
        assertEquals(FAMILIERECHTELIJKEBETREKKING_IDENTITEIT, ElementIdentificatieUtil.geefElementEnumVoorRelatieIdentiteitGroep(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING));
        assertEquals(NAAMSKEUZEONGEBORENVRUCHT_IDENTITEIT, ElementIdentificatieUtil.geefElementEnumVoorRelatieIdentiteitGroep(SoortRelatie.NAAMSKEUZE_ONGEBOREN_VRUCHT));
        assertEquals(ERKENNINGONGEBORENVRUCHT_IDENTITEIT, ElementIdentificatieUtil.geefElementEnumVoorRelatieIdentiteitGroep(SoortRelatie.ERKENNING_ONGEBOREN_VRUCHT));
        assertNull(ElementIdentificatieUtil.geefElementEnumVoorRelatieIdentiteitGroep(SoortRelatie.DUMMY));
    }
}
