/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.KindHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class PersoonMetGezinDeserialisatieTest extends AbstractTestPersoonBuilderDeserialisatie {

    private static final String ID = "iD";
    private static final String PERSOON = "persoon";
    private static final String BETROKKENHEDEN = "betrokkenheden";

    protected PersoonHisVolledigImpl maakPersoon() {
        final PersoonHisVolledigImpl persoon = maakBasisPersoon(1);
        final PersoonHisVolledigImpl moeder = maakBasisPersoon(2);
        final PersoonHisVolledigImpl vader = maakBasisPersoon(3);

        final ActieModel actie = maakActie();

        final FamilierechtelijkeBetrekkingHisVolledigImpl familie =
            new FamilierechtelijkeBetrekkingHisVolledigImplBuilder().nieuwStandaardRecord(actie).eindeRecord().build();
        ReflectionTestUtils.setField(familie, ID, 1234);

        final BetrokkenheidHisVolledigImpl kind = new KindHisVolledigImplBuilder(familie, persoon).metVerantwoording(actie).build();
        ReflectionTestUtils.setField(kind, ID, 5678);

        final BetrokkenheidHisVolledigImpl ouderBetr1 = new OuderHisVolledigImplBuilder(familie, moeder)
            .metVerantwoording(actie)
            .nieuwOuderlijkGezagRecord(actie)
            .indicatieOuderHeeftGezag(true)
            .eindeRecord(9)
            .nieuwOuderschapRecord(actie)
            .indicatieOuderUitWieKindIsGeboren(true)
            .indicatieOuder(Ja.J)
            .eindeRecord(10)
            .build();
        ReflectionTestUtils.setField(ouderBetr1, ID, 3456);

        final BetrokkenheidHisVolledigImpl ouderBetr2 = new OuderHisVolledigImplBuilder(familie, vader)
            .metVerantwoording(actie)
            .nieuwOuderlijkGezagRecord(actie)
            .indicatieOuderHeeftGezag(true)
            .eindeRecord(11)
            .nieuwOuderschapRecord(actie)
            .indicatieOuder(Ja.J)
            .eindeRecord(12)
            .build();
        ReflectionTestUtils.setField(ouderBetr2, ID, 6789);

        return persoon;
    }

    @Override
    protected void valideerObjecten(final PersoonHisVolledigImpl persoon, final PersoonHisVolledigImpl terugPersoon) {
        super.valideerObjecten(persoon, terugPersoon);

        for (final OuderHisVolledigImpl ouderHisVolledig : terugPersoon.getKindBetrokkenheid().getRelatie().getOuderBetrokkenheden()) {
            assertNotNull(ouderHisVolledig.getOuderOuderlijkGezagHistorie().getHistorie());
            assertNotNull(ouderHisVolledig.getOuderOuderschapHistorie().getHistorie());
            assertTrue(ouderHisVolledig.getBetrokkenheidHistorie().heeftActueelRecord());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testJson() throws IOException {
        // given
        final byte[] heenJson = serializer.serialiseer(persoon);
        final String heenJsonString = new String(heenJson);
        final HashMap<String, Object> wrapper = new ObjectMapper().readValue(heenJsonString, HashMap.class);

        // de persoon is gevuld, maar heeft geen betrokkenheden
        final Map<String, Object> persoon = (Map) (wrapper.get(PERSOON));
        assertEquals(1, persoon.get(ID));
        assertNotNull(persoon.get("soort"));
        assertNull(persoon.get(BETROKKENHEDEN));

        // er zijn 3 betrokkenheden
        final List<Object> betrokkenheden = (List) wrapper.get(BETROKKENHEDEN);
        assertEquals(3, betrokkenheden.size());
        // een betrokkenheid kent geen relatie, maar wel een persoon
        final List<Object> wrappedBetrokkenheid = (List) (betrokkenheden.get(0));
        assertEquals("nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl", wrappedBetrokkenheid.get(0));
        final Map<String, Object> betrokkenheid = (Map) (wrappedBetrokkenheid.get(1));
        assertNull(betrokkenheid.get("relatie"));

        // van de betrokkenheidpersoon is alleen de id geserialiseerd
        final Map<String, Object> betrokkenheidPersoon = (Map) (betrokkenheid.get(PERSOON));
        assertEquals("van een betrokken persoon mag alleen de id geserialiseerd worden", 1, betrokkenheidPersoon.size());
        assertEquals(2, betrokkenheidPersoon.get(ID));

        // er is 1 relatie
        final List<Object> relaties = (List) wrapper.get("relaties");
        assertEquals(1, relaties.size());
        final List<Object> wrappedRelatie = (List) (relaties.get(0));
        assertEquals("nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl", wrappedRelatie.get(0));
        final Map<String, Object> relatie = (Map) (wrappedRelatie.get(1));
        final List<Object> relatieBetrokkenheden = (List) relatie.get(BETROKKENHEDEN);
        assertEquals(3, relatieBetrokkenheden.size());
    }
}
