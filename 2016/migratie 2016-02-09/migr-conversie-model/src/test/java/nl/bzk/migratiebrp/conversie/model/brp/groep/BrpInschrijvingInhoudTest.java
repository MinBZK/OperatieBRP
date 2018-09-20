/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorieTest;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;

public class BrpInschrijvingInhoudTest {
    public static BrpInschrijvingInhoud createInhoud() {
        return new BrpInschrijvingInhoud(new BrpDatum(new Integer(20050101), null), new BrpLong(1L), new BrpDatumTijd(Calendar.getInstance().getTime()));
    }

    public static BrpInschrijvingInhoud createInhoud(int datumInschrijving, int versienummer, Date datumTijd) {
        return new BrpInschrijvingInhoud(new BrpDatum(Integer.valueOf(datumInschrijving),null),new BrpLong(Long.valueOf(versienummer)),new BrpDatumTijd(datumTijd, null));
    }

    public static BrpStapel<BrpInschrijvingInhoud> createStapel() {
        List<BrpGroep<BrpInschrijvingInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpInschrijvingInhoud> groep = new BrpGroep<>(createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }

}
