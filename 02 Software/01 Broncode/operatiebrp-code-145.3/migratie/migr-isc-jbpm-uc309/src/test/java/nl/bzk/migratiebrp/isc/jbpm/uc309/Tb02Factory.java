/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import edu.emory.mathcs.backport.java.util.Collections;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractCategorieGebaseerdParsedLo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tb02Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegisterImpl;
import nl.bzk.migratiebrp.bericht.model.sync.register.Rol;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.isc.jbpm.common.TestPartijService;
import nl.bzk.migratiebrp.register.client.PartijService;

/**
 * Bericht factory
 */
public class Tb02Factory {

    private static Date intToDate(final int date) {
        try {
            return new SimpleDateFormat("yyyyMMdd").parse(Integer.toString(date));
        } catch (final ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public PartijService getPartijService() {
        final List<Partij> partijen = new ArrayList<>();
        partijen.add(new Partij("333301", "3333", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("042901", "0429", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("222201", "2222", intToDate(2009_01_01), Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("071701", "0717", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("199902", null, intToDate(2009_01_01), Collections.emptyList()));

        return new TestPartijService(new PartijRegisterImpl(partijen));
    }

    public Tb02Bericht maakTb02Bericht(final Soort soort) throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden;
        switch (soort) {
            case SLUITING:
                waarden = maakLijstVoorSluiting();
                bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QA1234");
                bericht.setHeader(
                        Lo3HeaderVeld.IDENTIFICERENDE_RUBRIEKEN,
                        "010110010240010310010320010330010410050240050310050320050330050410050610050620050630051510058110058120058510");
                bericht.setHeader(Lo3HeaderVeld.HERHALING, "0");
                bericht.setHeader(Lo3HeaderVeld.AANTAL, "018");
                bericht.setBronPartijCode("333301");
                bericht.setDoelPartijCode("222201");
                break;

            case ONTBINDING_INCORRECT:
                waarden = maakLijstVoorOntbindingFoutief();
                bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QB1234");
                bericht.setHeader(
                        Lo3HeaderVeld.IDENTIFICERENDE_RUBRIEKEN,
                        "010110010240010310010320010330010410050240050310050320050330050410050710050720050730050740051510058110058120058510550240550310550320550330550610550620550630551510");
                bericht.setHeader(Lo3HeaderVeld.HERHALING, "0");
                bericht.setHeader(Lo3HeaderVeld.AANTAL, "027");
                bericht.setBronPartijCode("333301");
                bericht.setDoelPartijCode("222201");
                break;

            case OVERLIJDEN:
                waarden = maakLijstVoorOverlijden();
                bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "2QA1234");
                bericht.setHeader(Lo3HeaderVeld.IDENTIFICERENDE_RUBRIEKEN, "");
                bericht.setHeader(Lo3HeaderVeld.HERHALING, "0");
                bericht.setHeader(Lo3HeaderVeld.AANTAL, "000");
                bericht.setBronPartijCode("333301");
                bericht.setDoelPartijCode("222201");
                break;
            default:
                waarden = new ArrayList<>();
        }

        final Method parseInhoud = AbstractCategorieGebaseerdParsedLo3Bericht.class.getDeclaredMethod("parseCategorieen", List.class);
        parseInhoud.setAccessible(true);
        parseInhoud.invoke(bericht, waarden);
        return bericht;
    }

    private List<Lo3CategorieWaarde> maakLijstVoorSluiting() {
        return Arrays.asList(new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
            {
                put(Lo3ElementEnum.ELEMENT_0110, "1234567890");
                put(Lo3ElementEnum.ELEMENT_0240, "Achternaam");
                put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                put(Lo3ElementEnum.ELEMENT_0320, "2222");
                put(Lo3ElementEnum.ELEMENT_0330, "1");
                put(Lo3ElementEnum.ELEMENT_0410, "M");
            }
        }), new Lo3CategorieWaarde(Lo3CategorieEnum.HUWELIJK, 0, 0, new HashMap<Lo3ElementEnum, String>() {
            {
                put(Lo3ElementEnum.ELEMENT_0240, "VrouwAchternaam");
                put(Lo3ElementEnum.ELEMENT_0310, "19751231");
                put(Lo3ElementEnum.ELEMENT_0320, "3333");
                put(Lo3ElementEnum.ELEMENT_0330, "1");
                put(Lo3ElementEnum.ELEMENT_0410, "V");
                put(Lo3ElementEnum.ELEMENT_0610, "19990101");
                put(Lo3ElementEnum.ELEMENT_0620, "5555");
                put(Lo3ElementEnum.ELEMENT_0630, "1");
                put(Lo3ElementEnum.ELEMENT_1510, "H");
                put(Lo3ElementEnum.ELEMENT_8110, "3333");
                put(Lo3ElementEnum.ELEMENT_8120, "3QA1234");
                put(Lo3ElementEnum.ELEMENT_8510, "20150917");
            }
        }));
    }

    private List<Lo3CategorieWaarde> maakLijstVoorOntbindingFoutief() {
        return Arrays.asList(new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
            {
                put(Lo3ElementEnum.ELEMENT_0110, "1234567890");
                put(Lo3ElementEnum.ELEMENT_0240, "Achternaam");
                put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                put(Lo3ElementEnum.ELEMENT_0320, "2222");
                put(Lo3ElementEnum.ELEMENT_0330, "1");
                put(Lo3ElementEnum.ELEMENT_0410, "M");
            }
        }), new Lo3CategorieWaarde(Lo3CategorieEnum.HUWELIJK, 0, 0, new HashMap<Lo3ElementEnum, String>() {
            {
                put(Lo3ElementEnum.ELEMENT_0240, "Graaf");
                put(Lo3ElementEnum.ELEMENT_0310, "19900101");
                put(Lo3ElementEnum.ELEMENT_0320, "2222");
                put(Lo3ElementEnum.ELEMENT_0330, "1");
                put(Lo3ElementEnum.ELEMENT_0410, "V");
                put(Lo3ElementEnum.ELEMENT_0710, "20120101");
                put(Lo3ElementEnum.ELEMENT_0720, "2222");
                put(Lo3ElementEnum.ELEMENT_0730, "1");
                put(Lo3ElementEnum.ELEMENT_0740, "S");
                put(Lo3ElementEnum.ELEMENT_1510, "H");
                put(Lo3ElementEnum.ELEMENT_8110, "3333");
                put(Lo3ElementEnum.ELEMENT_8120, "3QB1234");
                put(Lo3ElementEnum.ELEMENT_8510, "20150917");
            }
        }), new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_55, 0, 0, new HashMap<Lo3ElementEnum, String>() {
            {
                put(Lo3ElementEnum.ELEMENT_0240, "Gravin");
                put(Lo3ElementEnum.ELEMENT_0310, "19900202");
                put(Lo3ElementEnum.ELEMENT_0320, "2222");
                put(Lo3ElementEnum.ELEMENT_0330, "1");
                put(Lo3ElementEnum.ELEMENT_0610, "20120101");
                put(Lo3ElementEnum.ELEMENT_0620, "2222");
                put(Lo3ElementEnum.ELEMENT_0630, "1");
                put(Lo3ElementEnum.ELEMENT_1510, "P");
            }
        }));
    }

    private List<Lo3CategorieWaarde> maakLijstVoorOverlijden() {
        return Arrays.asList(new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
            {
                put(Lo3ElementEnum.ELEMENT_0110, "1234567890");
                put(Lo3ElementEnum.ELEMENT_0210, "Voornaam");
                put(Lo3ElementEnum.ELEMENT_0220, "B");
                put(Lo3ElementEnum.ELEMENT_0230, "Van");
                put(Lo3ElementEnum.ELEMENT_0240, "Achternaam");
                put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                put(Lo3ElementEnum.ELEMENT_0320, "2222");
                put(Lo3ElementEnum.ELEMENT_0330, "1");
                put(Lo3ElementEnum.ELEMENT_0410, "M");
            }
        }), new Lo3CategorieWaarde(Lo3CategorieEnum.OVERLIJDEN, 0, 0, new HashMap<Lo3ElementEnum, String>() {
            {
                put(Lo3ElementEnum.ELEMENT_0810, "19990101");
                put(Lo3ElementEnum.ELEMENT_0820, "5555");
                put(Lo3ElementEnum.ELEMENT_0830, "1");
                put(Lo3ElementEnum.ELEMENT_8110, "3333");
                put(Lo3ElementEnum.ELEMENT_8120, "2QA1234");
                put(Lo3ElementEnum.ELEMENT_8510, "20150917");
            }
        }));
    }

    public Tb02Bericht maakSluitingTb02Bericht() throws Exception {
        return maakTb02Bericht(Soort.SLUITING);
    }

    public Tb02Bericht maakOntbindingIncorrectTb02Bericht() throws Exception {
        return maakTb02Bericht(Soort.ONTBINDING_INCORRECT);
    }

    public Tb02Bericht maakOverlijdenTb02Bericht() throws Exception {
        return maakTb02Bericht(Soort.OVERLIJDEN);
    }

    private enum Soort {
        SLUITING, ONTBINDING_INCORRECT, OVERLIJDEN
    }
}
