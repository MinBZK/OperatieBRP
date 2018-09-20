/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PartnerHisVolledigImplBuilder;


public class PersoonMetHuwelijkDeserialisatieTest extends AbstractTestPersoonBuilderDeserialisatie {

    protected PersoonHisVolledigImpl maakPersoon() {
        final PersoonHisVolledigImpl persoon = maakBasisPersoon(1);
        final PersoonHisVolledigImpl partner = maakBasisPersoon(2);

        final ActieModel actie = maakActie();
        final HuwelijkHisVolledigImpl huwelijk =
            new HuwelijkHisVolledigImplBuilder().nieuwStandaardRecord(actie).datumAanvang(20120101)
                .gemeenteAanvang((short) 123)
                .landGebiedAanvang((short) 1)
                .omschrijvingLocatieAanvang("Een oude kerk")
                .eindeRecord()
                .build();


        setField(huwelijk, "iD", 1234);
        final BetrokkenheidHisVolledigImpl huwelijkpartner1 = new PartnerHisVolledigImplBuilder(huwelijk, persoon).metVerantwoording(actie).build();
        setField(huwelijkpartner1, "iD", 3241);
        final BetrokkenheidHisVolledigImpl huwelijkpartner2 = new PartnerHisVolledigImplBuilder(huwelijk, partner).metVerantwoording(actie).build();
        setField(huwelijkpartner2, "iD", 6121);

        return persoon;
    }

    @Override
    protected void valideerObjecten(final PersoonHisVolledigImpl persoon, final PersoonHisVolledigImpl terugPersoon) {
        super.valideerObjecten(persoon, terugPersoon);

        final PartnerHisVolledigImpl partnerHisVolledig = terugPersoon.getHuwelijkGeregistreerdPartnerschappen().iterator().next()
            .geefPartnerVan(terugPersoon);

        assertTrue(partnerHisVolledig.getBetrokkenheidHistorie().heeftActueelRecord());
    }
}
