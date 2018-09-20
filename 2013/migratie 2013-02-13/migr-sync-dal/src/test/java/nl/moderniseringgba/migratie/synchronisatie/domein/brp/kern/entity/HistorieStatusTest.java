/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;

import org.junit.Test;

/**
 * Test cases voor het testen van het bepalen van de HistorieStatus van een BrpStapel.
 * 
 * @see BrpStapel
 */
public class HistorieStatusTest {

    @Test
    public void testBepaalHistorieStatus() {
        assertEquals(HistorieStatus.A, HistorieStatus.bepaalHistorieStatus(createTestStapelA()));

        assertEquals(HistorieStatus.M, HistorieStatus.bepaalHistorieStatus(createTestStapelM()));

        assertEquals(HistorieStatus.F, HistorieStatus.bepaalHistorieStatus(createTestStapelF()));

        assertEquals(HistorieStatus.X, HistorieStatus.bepaalHistorieStatus(null));
    }

    private BrpStapel<?> createTestStapelA() {
        final List<BrpGroep<BrpIdentificatienummersInhoud>> groepen =
                new ArrayList<BrpGroep<BrpIdentificatienummersInhoud>>();
        final BrpIdentificatienummersInhoud inhoud1 = new BrpIdentificatienummersInhoud(123456789L, 987654321L);
        final BrpIdentificatienummersInhoud inhoud2 = new BrpIdentificatienummersInhoud(223456789L, 987654322L);
        final BrpIdentificatienummersInhoud inhoud3 = new BrpIdentificatienummersInhoud(323456789L, 987654323L);

        final BrpHistorie historie1 =
                new BrpHistorie(new BrpDatum(20110101), null, BrpDatumTijd.fromDatum(20110101),
                        BrpDatumTijd.fromDatum(20110201));
        final BrpHistorie historie2 =
                new BrpHistorie(new BrpDatum(20110201), new BrpDatum(20110301), BrpDatumTijd.fromDatum(20110201),
                        null);
        final BrpHistorie historie3 =
                new BrpHistorie(new BrpDatum(20110301), null, BrpDatumTijd.fromDatum(20110201), null);

        groepen.add(new BrpGroep<BrpIdentificatienummersInhoud>(inhoud1, historie1, null, null, null));
        groepen.add(new BrpGroep<BrpIdentificatienummersInhoud>(inhoud2, historie2, null, null, null));
        groepen.add(new BrpGroep<BrpIdentificatienummersInhoud>(inhoud3, historie3, null, null, null));

        return new BrpStapel<BrpIdentificatienummersInhoud>(groepen);
    }

    private BrpStapel<?> createTestStapelM() {
        final List<BrpGroep<BrpIdentificatienummersInhoud>> groepen =
                new ArrayList<BrpGroep<BrpIdentificatienummersInhoud>>();
        final BrpIdentificatienummersInhoud inhoud1 = new BrpIdentificatienummersInhoud(123456789L, 987654321L);
        final BrpIdentificatienummersInhoud inhoud2 = new BrpIdentificatienummersInhoud(223456789L, 987654322L);

        final BrpHistorie historie1 =
                new BrpHistorie(new BrpDatum(20110101), null, BrpDatumTijd.fromDatum(20110101),
                        BrpDatumTijd.fromDatum(20110201));
        final BrpHistorie historie2 =
                new BrpHistorie(new BrpDatum(20110201), new BrpDatum(20110301), BrpDatumTijd.fromDatum(20110201),
                        null);

        groepen.add(new BrpGroep<BrpIdentificatienummersInhoud>(inhoud1, historie1, null, null, null));
        groepen.add(new BrpGroep<BrpIdentificatienummersInhoud>(inhoud2, historie2, null, null, null));

        return new BrpStapel<BrpIdentificatienummersInhoud>(groepen);
    }

    private BrpStapel<?> createTestStapelF() {
        final List<BrpGroep<BrpIdentificatienummersInhoud>> groepen =
                new ArrayList<BrpGroep<BrpIdentificatienummersInhoud>>();
        final BrpIdentificatienummersInhoud inhoud1 = new BrpIdentificatienummersInhoud(123456789L, 987654321L);

        final BrpHistorie historie1 =
                new BrpHistorie(new BrpDatum(20110101), null, BrpDatumTijd.fromDatum(20110101),
                        BrpDatumTijd.fromDatum(20110201));

        groepen.add(new BrpGroep<BrpIdentificatienummersInhoud>(inhoud1, historie1, null, null, null));

        return new BrpStapel<BrpIdentificatienummersInhoud>(groepen);
    }
}
