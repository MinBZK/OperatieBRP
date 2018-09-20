/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.registratienationaliteit;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.model.attribuuttype.RedenVerkrijgingCode;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonNationaliteitBericht;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Nationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/** Unit test voor de {@link BRBY0165} bedrijfsregel. */
public class BRBY0165Test {

    private BRBY0165 bedrijfsregel;
    private Persoon man = null;
    private Persoon manNLZonderReden = null;
    private Persoon vreemdeMetReden = null;
    private Persoon vreemde = null;

    @Before
    public void init() {
        bedrijfsregel = new BRBY0165();

        man = bouwPersoonMetNationaliteit("987654321", StatischeObjecttypeBuilder.NATIONALITEIT_NEDERLANDS);
        manNLZonderReden = bouwPersoonMetNationaliteit("987654321", StatischeObjecttypeBuilder.NATIONALITEIT_NEDERLANDS);
        ReflectionTestUtils.setField(manNLZonderReden.getNationaliteiten().iterator().next().getGegevens(),
                "redenVerkregenNlNationaliteit", null);

        vreemde = bouwPersoonMetNationaliteit("567890123", StatischeObjecttypeBuilder.NATIONALITEIT_SLOWAAKS);
        ReflectionTestUtils.setField(vreemde.getNationaliteiten().iterator().next().getGegevens(),
                "redenVerkregenNlNationaliteit", null);

        vreemdeMetReden = bouwPersoonMetNationaliteit("567890123", StatischeObjecttypeBuilder.NATIONALITEIT_SLOWAAKS);
        RedenVerkrijgingNLNationaliteit rdn = new RedenVerkrijgingNLNationaliteit();
        ReflectionTestUtils.setField(rdn, "code", new RedenVerkrijgingCode((short) 17));
        ReflectionTestUtils.setField(vreemdeMetReden.getNationaliteiten().iterator().next().getGegevens(),
                "redenVerkregenNlNationaliteit", rdn);
    }

    @Test
    public void testGeenBericht() {
        Assert.assertTrue(bedrijfsregel.executeer(null, null, null).isEmpty());
    }

    @Test
    public void testGeenActies() {
        Assert.assertTrue(bedrijfsregel.executeer(null, null, null).isEmpty());
    }

    @Test
    public void testActiesNormaal() {
        Assert.assertTrue(bedrijfsregel.executeer(null, man, null).isEmpty());
    }

    @Test
    public void testNlZonderReden() {
        List<Melding> meldingen = bedrijfsregel.executeer(null, manNLZonderReden, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0165, meldingen.get(0).getCode());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, meldingen.get(0).getSoort());
    }

    @Test
    public void testVreemde() {
        // buitelandse nationationaliteit? redenverkrijgenNL doet er niet toe null of niet.
        Assert.assertTrue(bedrijfsregel.executeer(null, vreemde, null).isEmpty());
    }

    @Test
    public void testVreemdeMetReden() {
        // buitelandse nationationaliteit? redenverkrijgenNL doet er niet toe null of niet.
        Assert.assertTrue(bedrijfsregel.executeer(null, vreemdeMetReden, null).isEmpty());
    }



    private Persoon bouwPersoonMetNationaliteit(final String bsn, final Nationaliteit nation) {
        PersoonBericht persoon = PersoonBuilder.bouwPersoon(bsn, Geslachtsaanduiding.MAN,
                19900308,
                StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM,
                "voornaam", "voorvoegsel", "geslachtsnaam");
        persoon.setNationaliteiten(new ArrayList<PersoonNationaliteitBericht>());
        persoon.getNationaliteiten().add(
                PersoonBuilder.bouwPersoonNationaliteit(nation));
        return persoon;
    }


}
