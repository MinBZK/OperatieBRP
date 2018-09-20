/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorieTest;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijdTest;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BrpRelatieInhoudTest {

    public static BrpRelatieInhoud createInhoud(){
        return new BrpRelatieInhoud(new BrpDatum(20000101,null), new BrpGemeenteCode(new Integer("30").shortValue()),new BrpString("Pekela"),null,null, BrpLandOfGebiedCode.NEDERLAND,null,null,null,null,null,null,null,null,null);
    }

    public static BrpStapel<BrpRelatieInhoud> createStapel(){
        List<BrpGroep> groepen = new ArrayList<>();
        BrpGroep<BrpRelatieInhoud> groep = new BrpGroep<>(createInhoud(), BrpHistorieTest.createdefaultInhoud(),null,null,null);
        groepen.add(groep);
        return new BrpStapel(groepen);
    }
}