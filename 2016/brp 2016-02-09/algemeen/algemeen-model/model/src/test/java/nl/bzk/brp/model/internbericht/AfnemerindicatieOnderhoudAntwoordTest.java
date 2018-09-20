/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.internbericht;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMeldingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RegelAttribuut;
import nl.bzk.brp.serialisatie.JsonStringSerializer;
import org.junit.Before;
import org.junit.Test;


public class AfnemerindicatieOnderhoudAntwoordTest {

    private AfnemerindicatieOnderhoudAntwoord afnemerindicatieOnderhoudAntwoord =
        new AfnemerindicatieOnderhoudAntwoord();

    private JsonStringSerializer<AfnemerindicatieOnderhoudAntwoord> jsonStringSerialiseerder =
        new JsonStringSerializer<>(
            AfnemerindicatieOnderhoudAntwoord.class);

    private String testJson =
        "{\"meldingen\":[{\"melding\":\"Een melding\",\"regel\":"
            + "{\"waarde\":\"ACT0001\"},\"soort\":{\"waarde\":\"INFORMATIE\"}},"
            + "{\"melding\":\"Nog een melding\",\"regel\":{\"waarde\":\"ACT0002\"},\"soort\":"
            + "{\"waarde\":\"FOUT\"}}],\"referentienummer\":\"kljwar234\",\"succesvol\":true}";

    @Before
    public void setup() {
        RegelMelding melding1 =
            new RegelMelding(new RegelAttribuut(Regel.ACT0001), new SoortMeldingAttribuut(SoortMelding.INFORMATIE),
                new MeldingtekstAttribuut("Een melding"));
        RegelMelding melding2 =
            new RegelMelding(new RegelAttribuut(Regel.ACT0002), new SoortMeldingAttribuut(SoortMelding.FOUT),
                new MeldingtekstAttribuut("Nog een melding"));

        afnemerindicatieOnderhoudAntwoord.setSuccesvol(true);
        afnemerindicatieOnderhoudAntwoord.setMeldingen(Arrays.asList(new RegelMelding[]{ melding1, melding2 }));
        afnemerindicatieOnderhoudAntwoord.setReferentienummer(new ReferentienummerAttribuut("kljwar234"));
    }

    @Test
    public void testVanObjectNaarJson() {
        String jsonString = jsonStringSerialiseerder.serialiseerNaarString(afnemerindicatieOnderhoudAntwoord);

        assertEquals(testJson, jsonString);
    }

    @Test
    public void testVanJsonNaarObject() {
        AfnemerindicatieOnderhoudAntwoord resultaat = jsonStringSerialiseerder.deserialiseerVanuitString(testJson);

        assertEquals(afnemerindicatieOnderhoudAntwoord.getSuccesvol(), true);
        assertEquals(afnemerindicatieOnderhoudAntwoord.getMeldingen().size(), resultaat.getMeldingen().size());
        assertEquals(afnemerindicatieOnderhoudAntwoord.getMeldingen().get(0).getMelding(), resultaat.getMeldingen()
                .get(0).getMelding());
        assertEquals(afnemerindicatieOnderhoudAntwoord.getMeldingen().get(0).getRegel(), resultaat.getMeldingen()
                .get(0).getRegel());
        assertEquals(afnemerindicatieOnderhoudAntwoord.getMeldingen().get(0).getSoort(), resultaat.getMeldingen()
                .get(0).getSoort());
        assertEquals(afnemerindicatieOnderhoudAntwoord.getReferentienummer(), resultaat.getReferentienummer());
    }

}
