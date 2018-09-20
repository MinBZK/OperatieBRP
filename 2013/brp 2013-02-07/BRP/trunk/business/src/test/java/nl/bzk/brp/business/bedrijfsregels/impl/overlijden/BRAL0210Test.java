/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.overlijden;

import java.util.List;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaats;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegio;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieOmschrijving;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

/*
 * De volgende combinaties van locatiegegevens zijn toegestaan, bij geboorte, overlijden, relatie
 * van de soort "huwelijk/ geregistreerd partnerschap" (zowel aanvang als einde),
 * of als onderdeel van de geboortegegevens van een niet-ingeschrevene:
 *
 *          Gemeente + Land (mits land is Nederland)    of
 *          Gemeente + Woonplaats + Land (mits land is Nederland) of
 *          Buitenlandse plaats + Buitenlandse regio + Land  (mits land is niet Nederland) of
 *          Buitenlandse plaats + Land  (mits land is niet Nederland)
 *          Omschrijving locatie + Land  (mits land is niet Nederland)
 *
 * Niveau: Fout
 *
 * @brp.bedrijfsregel BRAL0210
 *
*/

public class BRAL0210Test {

    private BRAL0210 bral0210;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        bral0210 = new BRAL0210();
    }

//    //@Test
//    public void testDrieNullen() throws Exception {
//        List<Melding> meldingen = bral0210.executeer(null, null, null);
//        Assert.assertEquals(0, meldingen.size());
//    }
//
//    //@Test
//    public void testPersoonOverledenMetModelNull() throws Exception {
//        PersoonBericht persoonBericht = new PersoonBericht();
//        PersoonOverlijdenGroepBericht overlijdenGroep = new PersoonOverlijdenGroepBericht();
//        persoonBericht.setOverlijden(overlijdenGroep);
//
//        List<Melding> meldingen = bral0210.executeer(null, persoonBericht, null);
//        // gaat goed, omdat de persoonModel niet bestaat.
//        Assert.assertEquals(0, meldingen.size());
//    }
//
//    //@Test
//    public void testLeegBerichtNullModel() throws Exception {
//        PersoonBericht bericht = new PersoonBericht();
//        List<Melding> meldingen = bral0210.executeer(null, bericht, null);
//        // gaat ook goed, omdat bericht.persoon.getOverlijden groep is null.
//        Assert.assertEquals(0, meldingen.size());
//    }
//
//    //@Test
//    public void testLeegBericht() throws Exception {
//        // beide datas hebben geen laatsteWijziging en overlijdenDatum.
//        List<Melding> meldingen = bral0210.executeer(
//                maakOverLedenPersoon(null, null),
//                maakOverLedenPersoon(null, null),
//                null);
//        Assert.assertEquals(0, meldingen.size());
//    }
//
//    //@Test
//    public void testOverlijdenZonderHuidigAdministratie() throws Exception {
//        // huidig persoon heeft geen afgeleide afministratie
//        List<Melding> meldingen = bral0210.executeer(
//            maakOverLedenPersoon(null, null),
//            maakOverLedenPersoon(20110831, null),
//            null);
//        Assert.assertEquals(0, meldingen.size());
//    }

    @Test
    public void testOverlijdenCorrect() throws Exception {
        List<Melding> meldingen = bral0210.executeer(
            maakOverLedenPersoon(null, null, null, null, null, null, null),
            maakOverLedenPersoon(20110831,
                StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM,
                StatischeObjecttypeBuilder.LAND_NEDERLAND,
                StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM,
                null,
                null,
                null
            ),
            null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testOverlijdenBuitenlandCorrect1() throws Exception {
        List<Melding> meldingen = bral0210.executeer(
            maakOverLedenPersoon(null, null, null, null, null, null, null),
            maakOverLedenPersoon(20110831,
                null,
                StatischeObjecttypeBuilder.LAND_BELGIE,
                null,
                "BuitPlaats",
                "BuitRegio",
                null
            ),
            null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testOverlijdenBuitenlandCorrect2() throws Exception {
        List<Melding> meldingen = bral0210.executeer(
            maakOverLedenPersoon(null, null, null, null, null, null, null),
            maakOverLedenPersoon(20110831,
                null,
                StatischeObjecttypeBuilder.LAND_BELGIE,
                null,
                "BuitPlaats",
                null,
                null
            ),
            null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testOverlijdenBuitenlandCorrect3() throws Exception {
        List<Melding> meldingen = bral0210.executeer(
            maakOverLedenPersoon(null, null, null, null, null, null, null),
            maakOverLedenPersoon(20110831,
                null,
                StatischeObjecttypeBuilder.LAND_BELGIE,
                null,
                null,
                null,
                "Omschrij"
            ),
            null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testOverlijdenBuitenland() throws Exception {
        // huidig persoon heeft ouder afgeleide afministratie
        List<Melding> meldingen = bral0210.executeer(
            maakOverLedenPersoon(null, null, null, null, null, null, null),
            maakOverLedenPersoon(20110831,
                null,
                StatischeObjecttypeBuilder.LAND_BELGIE,
                null,
                null,
                null,
                null
            ),
            null);
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testOverlijdenBuitenlandGemeentePlaats() throws Exception {
        // huidig persoon heeft ouder afgeleide afministratie
        List<Melding> meldingen = bral0210.executeer(
            maakOverLedenPersoon(null, null, null, null, null, null, null),
            maakOverLedenPersoon(20110831,
                StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM,
                StatischeObjecttypeBuilder.LAND_BELGIE,
                StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM,
                null,
                null,
                null
            ),
            null);
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testOverlijdenBuitenlandGemeente() throws Exception {
        // huidig persoon heeft ouder afgeleide afministratie
        List<Melding> meldingen = bral0210.executeer(
            maakOverLedenPersoon(null, null, null, null, null, null, null),
            maakOverLedenPersoon(20110831,
                StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM,
                StatischeObjecttypeBuilder.LAND_BELGIE,
                null,
                null,
                null,
                null
            ),
            null);
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testOverlijdenNLEnBuitenPlaats() throws Exception {
        // huidig persoon heeft ouder afgeleide afministratie
        List<Melding> meldingen = bral0210.executeer(
            maakOverLedenPersoon(null, null, null, null, null, null, null),
            maakOverLedenPersoon(20110831,
                StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM,
                StatischeObjecttypeBuilder.LAND_NEDERLAND,
                null,
                "BuitenPlaats",
                null,
                null
            ),
            null);
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testOverlijdenNLEnBuitenOmsch() throws Exception {
        // huidig persoon heeft ouder afgeleide afministratie
        List<Melding> meldingen = bral0210.executeer(
            maakOverLedenPersoon(null, null, null, null, null, null, null),
            maakOverLedenPersoon(20110831,
                StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM,
                StatischeObjecttypeBuilder.LAND_NEDERLAND,
                null,
                null,
                null,
                "Omsch"
            ),
            null);
        Assert.assertEquals(1, meldingen.size());
    }

    private Persoon maakOverLedenPersoon(final Integer datumOverlijden,
        final Partij gemeente,
        final Land land,
        final Plaats plaats,
        final String buitenlandseplaats,
        final String buitenlandseRegio,
        final String locatie)
    {
        PersoonBericht persoon = new PersoonBericht();
        PersoonOverlijdenGroepBericht overlijdenGroep = new PersoonOverlijdenGroepBericht();
        persoon.setOverlijden(overlijdenGroep);
        if (null != datumOverlijden) {
            overlijdenGroep.setCommunicatieID("id.pers.overlijden");
            overlijdenGroep.setDatumOverlijden(new Datum(datumOverlijden));
        }
        overlijdenGroep.setGemeenteOverlijden(gemeente);
        overlijdenGroep.setLandOverlijden(land);
        overlijdenGroep.setWoonplaatsOverlijden(plaats);
        if (null != buitenlandseplaats) {
            overlijdenGroep.setBuitenlandsePlaatsOverlijden(new BuitenlandsePlaats(buitenlandseplaats));
        }
        if (null != buitenlandseRegio) {
            overlijdenGroep.setBuitenlandseRegioOverlijden(new BuitenlandseRegio(buitenlandseRegio));
        }
        if (null != locatie) {
            overlijdenGroep.setOmschrijvingLocatieOverlijden(new LocatieOmschrijving(locatie));
        }
        return persoon;
    }

    @Test
    public void dummy() {
    }
}
