/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp.impl;

import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class GeboorteVerzoekBerichtDatumTijdOntleningTest {

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void test() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(GeboorteVerzoekBerichtTest.class
                        .getResourceAsStream("GeboorteVerzoekBericht_DatumTijdOntlening.xml"));
        final BrpBericht bericht = factory.getBericht(berichtOrigineel);
        System.out.println(bericht);

        final BrpPersoonslijst brp = ((GeboorteVerzoekBericht) bericht).getBrpPersoonslijst();
        final BrpGroep<BrpGeboorteInhoud> geboorte = brp.getGeboorteStapel().getMeestRecenteElement();
        final BrpActie actie = geboorte.getActieInhoud();
        System.out.println(actie);

        // 20121102010000
        // 20121101000

        Assert.assertEquals(BrpDatumTijd.fromDatumTijd(20121102010000L), actie.getDatumTijdOntlening());
    }

}
