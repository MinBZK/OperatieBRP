/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import org.junit.Test;

/**
 * Unittest voor de {@link NationaliteitHelper}.
 */
public class NationaliteitHelperTest {

    @Test
    public void testNationaliteitBestaatAl() {
        final ElementBuilder builder = new ElementBuilder();

        final NationaliteitElement nationaliteit1 = builder.maakNationaliteitElement("nat1", "0001", "001");
        nationaliteit1.setDatumAanvangGeldigheidRegistratieNationaliteitActie(2016_01_01);
        final NationaliteitElement nationaliteit2 = builder.maakNationaliteitElement("nat2", "0001", "001");
        nationaliteit2.setDatumAanvangGeldigheidRegistratieNationaliteitActie(2017_01_01);

        final ElementBuilder.PersoonParameters params1 = new ElementBuilder.PersoonParameters();
        params1.nationaliteiten(Collections.singletonList(nationaliteit1));

        final ElementBuilder.PersoonParameters params2 = new ElementBuilder.PersoonParameters();
        params2.nationaliteiten(Collections.singletonList(nationaliteit2));

        final PersoonElement registratieNationaliteit1 = builder.maakPersoonGegevensElement("pers1", null, null, params1);
        final PersoonElement registratieNationaliteit2 = builder.maakPersoonGegevensElement("pers2", null, null, params2);
        final BijhoudingPersoon persoon = new BijhoudingPersoon();
        persoon.registreerPersoonElement(registratieNationaliteit1);
        persoon.registreerPersoonElement(registratieNationaliteit2);

        NationaliteitHelper helper = new NationaliteitHelper(persoon);
        assertFalse(helper.heeftNationaliteitAl("0001", 2015_01_01));
        assertFalse(helper.heeftNationaliteitAl("0001", 2016_01_01));
        assertFalse(helper.heeftNationaliteitAl("0002", 2016_01_01));
        assertTrue(helper.heeftNationaliteitAl("0001", 2017_01_01));
    }

    @Test
    public void testNationaliteitBestaatAl_nieuwInBericht() {
        final ElementBuilder builder = new ElementBuilder();

        final NationaliteitElement nationaliteit1 = builder.maakNationaliteitElement("nat1", "0001", "001");
        nationaliteit1.setDatumAanvangGeldigheidRegistratieNationaliteitActie(2016_01_01);

        final ElementBuilder.PersoonParameters params1 = new ElementBuilder.PersoonParameters();
        params1.nationaliteiten(Collections.singletonList(nationaliteit1));

        final PersoonElement registratieNationaliteit1 = builder.maakPersoonGegevensElement("pers1", null, null, params1);
        final BijhoudingPersoon persoon = new BijhoudingPersoon();
        persoon.registreerPersoonElement(registratieNationaliteit1);

        NationaliteitHelper helper = new NationaliteitHelper(persoon);
        assertFalse(helper.heeftNationaliteitAl("0001", 2016_01_01));
    }

    @Test
    public void testNationaliteitBestaatAl_zelfdeDatum() {
        final ElementBuilder builder = new ElementBuilder();

        final int datum = 2016_01_01;
        final NationaliteitElement nationaliteit1 = builder.maakNationaliteitElement("nat1", "0001", "001");
        nationaliteit1.setDatumAanvangGeldigheidRegistratieNationaliteitActie(datum);
        final NationaliteitElement nationaliteit2 = builder.maakNationaliteitElement("nat2", "0001", "001");
        nationaliteit2.setDatumAanvangGeldigheidRegistratieNationaliteitActie(datum);

        final ElementBuilder.PersoonParameters params1 = new ElementBuilder.PersoonParameters();
        params1.nationaliteiten(Collections.singletonList(nationaliteit1));

        final ElementBuilder.PersoonParameters params2 = new ElementBuilder.PersoonParameters();
        params2.nationaliteiten(Collections.singletonList(nationaliteit2));

        final PersoonElement registratieNationaliteit1 = builder.maakPersoonGegevensElement("pers1", null, null, params1);
        final PersoonElement registratieNationaliteit2 = builder.maakPersoonGegevensElement("pers2", null, null, params2);
        final BijhoudingPersoon persoon = new BijhoudingPersoon();
        persoon.registreerPersoonElement(registratieNationaliteit1);
        persoon.registreerPersoonElement(registratieNationaliteit2);

        NationaliteitHelper helper = new NationaliteitHelper(persoon);
        assertFalse(helper.heeftNationaliteitAl("0002", datum));
        assertTrue(helper.heeftNationaliteitAl("0001", datum));
        assertTrue(helper.heeftNationaliteitAl("0001", datum));
    }

    @Test
    public void testHeeftNederlandseNationaliteit_nieuwInBericht() {
        final ElementBuilder builder = new ElementBuilder();

        final int datum = 2016_01_01;
        final NationaliteitElement nationaliteit1 = builder.maakNationaliteitElement("nat1", "0001", "001");
        nationaliteit1.setDatumAanvangGeldigheidRegistratieNationaliteitActie(datum);

        final ElementBuilder.PersoonParameters params1 = new ElementBuilder.PersoonParameters();
        params1.nationaliteiten(Collections.singletonList(nationaliteit1));

        final PersoonElement registratieNationaliteit1 = builder.maakPersoonGegevensElement("pers1", null, null, params1);
        final BijhoudingPersoon persoon = new BijhoudingPersoon();
        persoon.registreerPersoonElement(registratieNationaliteit1);

        NationaliteitHelper helper = new NationaliteitHelper(persoon);
        assertTrue(helper.heeftNederlandseNationaliteit(datum));
        assertTrue(helper.heeftNederlandseNationaliteit(2017_01_01));
        assertTrue(helper.heeftNederlandseNationaliteit(2099_01_01));
        assertFalse(helper.heeftNederlandseNationaliteit(2015_01_01));
    }

    @Test
    public void testHeeftNederlandseNationaliteit_beeindigdInBericht() {
        final ElementBuilder builder = new ElementBuilder();

        final NationaliteitElement nationaliteit1 = builder.maakNationaliteitElement("nat1", "0001", "001");
        nationaliteit1.setDatumAanvangGeldigheidRegistratieNationaliteitActie(2016_01_01);
        final NationaliteitElement nationaliteit2 = builder.maakNationaliteitElementVerlies("nat2", null, "nat1", "034");
        nationaliteit2.setDatumEindeGeldigheidRegistratieNationaliteitActie(2017_01_01);

        final Map<String, BmrGroep> refMap = new HashMap<>();
        refMap.put("nat1", nationaliteit1);
        nationaliteit2.initialiseer(refMap);

        final ElementBuilder.PersoonParameters params1 = new ElementBuilder.PersoonParameters();
        params1.nationaliteiten(Collections.singletonList(nationaliteit1));

        final ElementBuilder.PersoonParameters params2 = new ElementBuilder.PersoonParameters();
        params2.nationaliteiten(Collections.singletonList(nationaliteit2));

        final PersoonElement registratieNationaliteit = builder.maakPersoonGegevensElement("pers1", null, null, params1);
        final PersoonElement beeindigingNationaliteit = builder.maakPersoonGegevensElement("pers2", null, null, params2);
        final BijhoudingPersoon persoon = new BijhoudingPersoon();
        persoon.registreerPersoonElement(registratieNationaliteit);
        persoon.registreerPersoonElement(beeindigingNationaliteit);

        final NationaliteitHelper helper = new NationaliteitHelper(persoon);
        assertTrue(helper.heeftNederlandseNationaliteit(2016_01_01));
        assertTrue(helper.heeftGeldigeNationaliteit(2016_01_01));
        assertTrue(helper.heeftNederlandseNationaliteit(2016_12_31));

        assertFalse(helper.heeftNederlandseNationaliteit(2017_01_01));
        assertFalse(helper.heeftGeldigeNationaliteit(2017_01_01));
        assertFalse(helper.isNationaliteitMeerdereKerenBeeindigd(nationaliteit2));

        assertFalse(helper.verliestNederlandseNationaliteit(2016_12_31));
        assertTrue(helper.verliestNederlandseNationaliteit(2017_01_01));
        assertFalse(helper.verliestNederlandseNationaliteit(2017_01_03));
    }

    @Test
    public void testHeeftNederlandseNationaliteit_inDatabase() {
        final BijhoudingPersoon persoon = new BijhoudingPersoon();

        final PersoonNationaliteit persoonNationaliteit = new PersoonNationaliteit(persoon, new Nationaliteit("Nederlandse", "0001"));
        final PersoonNationaliteitHistorie historie = new PersoonNationaliteitHistorie(persoonNationaliteit);
        historie.setDatumAanvangGeldigheid(2016_01_01);
        historie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        persoonNationaliteit.addPersoonNationaliteitHistorie(historie);
        persoon.addPersoonNationaliteit(persoonNationaliteit);

        NationaliteitHelper helper = new NationaliteitHelper(persoon);
        assertTrue(helper.heeftNederlandseNationaliteit(2016_01_01));
        assertTrue(helper.heeftNederlandseNationaliteit(2099_12_31));
    }


    @Test
    public void testHeeftNederlandseNationaliteit_inDatabase_beeindigdInBericht() {
        final ElementBuilder builder = new ElementBuilder();

        final NationaliteitElement nationaliteitElement = builder.maakNationaliteitElementVerlies("nat2", "1", null, "034");
        nationaliteitElement.setDatumEindeGeldigheidRegistratieNationaliteitActie(2017_01_01);

        final ElementBuilder.PersoonParameters params = new ElementBuilder.PersoonParameters();
        params.nationaliteiten(Collections.singletonList(nationaliteitElement));

        final PersoonElement beeindigingNationaliteit = builder.maakPersoonGegevensElement("pers1", null, null, params);
        final BijhoudingPersoon persoon = new BijhoudingPersoon();
        persoon.registreerPersoonElement(beeindigingNationaliteit);

        final PersoonNationaliteit persoonNationaliteit = new PersoonNationaliteit(persoon, new Nationaliteit("Nederlandse", "0001"));
        final PersoonNationaliteitHistorie historie = new PersoonNationaliteitHistorie(persoonNationaliteit);
        historie.setDatumAanvangGeldigheid(2016_01_01);
        historie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        persoonNationaliteit.addPersoonNationaliteitHistorie(historie);
        persoonNationaliteit.setId(1L);
        persoon.addPersoonNationaliteit(persoonNationaliteit);

        nationaliteitElement.bepaalBijhoudingPersoonNationaliteitEntiteit(persoon);

        NationaliteitHelper helper = new NationaliteitHelper(persoon);
        assertTrue(helper.heeftNederlandseNationaliteit(2016_01_01));
        assertTrue(helper.heeftNederlandseNationaliteit(2016_12_31));
        assertFalse(helper.heeftNederlandseNationaliteit(2017_01_01));
    }

    @Test
    public void testHeeftNederlandseNationaliteit_dubbelBeeindigdInBericht() {
        final ElementBuilder builder = new ElementBuilder();

        final NationaliteitElement nationaliteit1 = builder.maakNationaliteitElement("nat1", "0001", "001");
        nationaliteit1.setDatumAanvangGeldigheidRegistratieNationaliteitActie(2016_01_01);
        final NationaliteitElement nationaliteit2 = builder.maakNationaliteitElementVerlies("nat2", null, "nat1", "034");
        nationaliteit2.setDatumEindeGeldigheidRegistratieNationaliteitActie(2017_01_01);
        final NationaliteitElement nationaliteit3 = builder.maakNationaliteitElementVerlies("nat3", null, "nat1", "034");
        nationaliteit3.setDatumEindeGeldigheidRegistratieNationaliteitActie(2017_01_01);

        final Map<String, BmrGroep> refMap = new HashMap<>();
        refMap.put("nat1", nationaliteit1);
        nationaliteit2.initialiseer(refMap);
        nationaliteit3.initialiseer(refMap);

        final ElementBuilder.PersoonParameters params1 = new ElementBuilder.PersoonParameters();
        params1.nationaliteiten(Collections.singletonList(nationaliteit1));
        final ElementBuilder.PersoonParameters params2 = new ElementBuilder.PersoonParameters();
        params2.nationaliteiten(Collections.singletonList(nationaliteit2));
        final ElementBuilder.PersoonParameters params3 = new ElementBuilder.PersoonParameters();
        params3.nationaliteiten(Collections.singletonList(nationaliteit3));

        final PersoonElement registratieNationaliteit = builder.maakPersoonGegevensElement("pers1", null, null, params1);
        final PersoonElement beeindigingNationaliteit = builder.maakPersoonGegevensElement("pers2", null, null, params2);
        final PersoonElement beeindigingNationaliteit2 = builder.maakPersoonGegevensElement("pers3", null, null, params3);
        final BijhoudingPersoon persoon = new BijhoudingPersoon();
        persoon.registreerPersoonElement(registratieNationaliteit);
        persoon.registreerPersoonElement(beeindigingNationaliteit);
        persoon.registreerPersoonElement(beeindigingNationaliteit2);

        NationaliteitHelper helper = new NationaliteitHelper(persoon);
        assertTrue(helper.isNationaliteitMeerdereKerenBeeindigd(nationaliteit2));
        assertTrue(helper.isNationaliteitMeerdereKerenBeeindigd(nationaliteit3));
    }
}
