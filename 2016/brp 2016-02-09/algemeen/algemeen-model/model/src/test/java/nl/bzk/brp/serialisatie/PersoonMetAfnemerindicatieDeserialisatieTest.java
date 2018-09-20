/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledigImplBuilder;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class PersoonMetAfnemerindicatieDeserialisatieTest extends AbstractTestPersoonBuilderDeserialisatie {

    @Test
    public void roundTripHeeftGeenAfnemerindicatiesInBlob() {
        // act
        final byte[] data = serializer.serialiseer(persoon);
        final PersoonHisVolledigImpl terugPersoon = serializer.deserialiseer(data);

        // assert
        final String json = new String(data);
        assertThat(persoon.getAfnemerindicaties().size(), is(1));
        assertThat("geen afnemerindicaties in de blob", json.contains("afnemerindicaties"), is(false));
        assertThat(terugPersoon.getAfnemerindicaties().size(), is(0));
    }

    @Override
    protected void valideerObjecten(final PersoonHisVolledigImpl persoon, final PersoonHisVolledigImpl terugPersoon) {
        // geen object validatie
    }

    protected PersoonHisVolledigImpl maakPersoon() {
        final PersoonHisVolledigImpl persoon = maakBasisPersoon(1);

        // afnemerindicatie
        final Partij partij = StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde();
        final Leveringsautorisatie abonnement = TestLeveringsautorisatieBuilder.maker().
            metNaam("Test Abo").
            metPopulatiebeperking("WAAR")
            .metProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN)
            .metDatumIngang(DatumAttribuut.gisteren())
            .maak();

        final PersoonAfnemerindicatieHisVolledigImplBuilder afnemerindicatieBuilder =
            new PersoonAfnemerindicatieHisVolledigImplBuilder(persoon, partij, abonnement).nieuwStandaardRecord(
                maakDienst()).eindeRecord(987L);
        final Set<PersoonAfnemerindicatieHisVolledigImpl> afnemerindicaties = new HashSet<>(1);
        afnemerindicaties.add(afnemerindicatieBuilder.build());

        persoon.setAfnemerindicaties(afnemerindicaties);
        return persoon;
    }
}
