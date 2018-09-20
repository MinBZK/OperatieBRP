/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tb02Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Gemeente;
import nl.bzk.migratiebrp.bericht.model.sync.register.GemeenteRegisterImpl;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.isc.jbpm.common.TestGemeenteService;
import nl.bzk.migratiebrp.register.client.GemeenteService;

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

    public GemeenteService getGemeenteRegister() {
        final List<Gemeente> gemeenten = new ArrayList<>();
        gemeenten.add(new Gemeente("3333", "582222", null));
        gemeenten.add(new Gemeente("0429", "580429", null));
        gemeenten.add(new Gemeente("2222", "583333", intToDate(19900101)));
        gemeenten.add(new Gemeente("0717", "580717", null));
        return new TestGemeenteService(new GemeenteRegisterImpl(gemeenten));
    }

    public Tb02Bericht maakTb02Bericht(final Soort soort) throws Exception {
        final Tb02Bericht bericht = new Tb02Bericht();
        final List<Lo3CategorieWaarde> waarden;
        switch (soort) {
            case SLUITING:
                waarden = maakLijstVoorSluiting();
                bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QA1234");
                bericht.setHeader(Lo3HeaderVeld.IDENTIFICERENDE_RUBRIEKEN, "010110010240010310010320010330010410050240050310050320050330050410050610050620050630051510058110058120058510");
                bericht.setHeader(Lo3HeaderVeld.HERHALING, "0");
                bericht.setHeader(Lo3HeaderVeld.AANTAL, "018");
                bericht.setBronGemeente("3333");
                break;
            case ONTBINDING:
                waarden = maakLijstVoorOntbinding();
                bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QB1234");
                bericht.setHeader(Lo3HeaderVeld.HERHALING, "0");
                bericht.setHeader(Lo3HeaderVeld.AANTAL, "027");
                bericht.setBronGemeente("3333");
                bericht.setHeader(Lo3HeaderVeld.IDENTIFICERENDE_RUBRIEKEN, "010110010240010310010320010330010410050240050310050320050330050410050710050720050730050740051510058110058120058510550240550310550320550330550610550620550630551510");
                break;
            case ONTBINDING_INCORRECT:
                waarden = maakLijstVoorOntbindingFoutief();
                bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QB1234");
                bericht.setHeader(Lo3HeaderVeld.IDENTIFICERENDE_RUBRIEKEN, "010110010240010310010320010330010410050240050310050320050330050410050710050720050730050740051510058110058120058510550240550310550320550330550610550620550630551510");
                bericht.setHeader(Lo3HeaderVeld.HERHALING, "0");
                bericht.setHeader(Lo3HeaderVeld.AANTAL, "027");
                bericht.setBronGemeente("3333");
                break;
            case OMZETTING:
                waarden = maakLijstVoorOmzetting();
                bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QH1234");
                bericht.setHeader(Lo3HeaderVeld.IDENTIFICERENDE_RUBRIEKEN, "010110010240010310010320010330010410050240050310050320050330050410050610050620050630051510058110058120058510550240550310550320550330550610550620550630551510");
                bericht.setHeader(Lo3HeaderVeld.HERHALING, "0");
                bericht.setHeader(Lo3HeaderVeld.AANTAL, "026");
                bericht.setBronGemeente("3333");
                break;
            case OMZETTING_INCORRECT:
                waarden = maakLijstVoorOmzettingFoutief();
                bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QH1234");
                bericht.setHeader(Lo3HeaderVeld.IDENTIFICERENDE_RUBRIEKEN, "010110010240010310010320010330010410050240050310050320050330050410050610050620050630051510058110058120058510550240550310550320550330550610550620550630551510");
                bericht.setHeader(Lo3HeaderVeld.HERHALING, "0");
                bericht.setHeader(Lo3HeaderVeld.AANTAL, "026");
                bericht.setBronGemeente("3333");
                break;
            case NAAMWIJZING:
                waarden = maakLijstNaamwijziging();
                bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "1QH1234");
                bericht.setHeader(Lo3HeaderVeld.IDENTIFICERENDE_RUBRIEKEN, "");
                bericht.setHeader(Lo3HeaderVeld.HERHALING, "0");
                bericht.setHeader(Lo3HeaderVeld.AANTAL, "000");
                bericht.setBronGemeente("3333");
                break;
            case VOORNAAMWIJZIGING:
                waarden = maakLijstVoornaamwijziging();
                bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "1QM1234");
                bericht.setHeader(Lo3HeaderVeld.IDENTIFICERENDE_RUBRIEKEN, "");
                bericht.setHeader(Lo3HeaderVeld.HERHALING, "0");
                bericht.setHeader(Lo3HeaderVeld.AANTAL, "000");
                bericht.setBronGemeente("3333");
                break;
            case GESLACHTSWIJZIGING:
                waarden = maakLijstGeslachtswijziging();
                bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "1QS1234");
                bericht.setHeader(Lo3HeaderVeld.IDENTIFICERENDE_RUBRIEKEN, "");
                bericht.setHeader(Lo3HeaderVeld.HERHALING, "0");
                bericht.setHeader(Lo3HeaderVeld.AANTAL, "000");
                bericht.setBronGemeente("3333");
                break;
            case OVERLIJDEN:
                waarden = maakLijstVoorOverlijden();
                bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "2QA1234");
                bericht.setHeader(Lo3HeaderVeld.IDENTIFICERENDE_RUBRIEKEN, "");
                bericht.setHeader(Lo3HeaderVeld.HERHALING, "0");
                bericht.setHeader(Lo3HeaderVeld.AANTAL, "000");
                bericht.setBronGemeente("3333");
                break;
            default:
                waarden = new ArrayList<>();
        }

        final Method parseInhoud = bericht.getClass().getDeclaredMethod("parseInhoud", List.class);
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

    private List<Lo3CategorieWaarde> maakLijstVoorOntbinding() {
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
                        put(Lo3ElementEnum.ELEMENT_0240, "Graaf");
                        put(Lo3ElementEnum.ELEMENT_0310, "19900101");
                        put(Lo3ElementEnum.ELEMENT_0320, "2222");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0610, "20120101");
                        put(Lo3ElementEnum.ELEMENT_0620, "2222");
                        put(Lo3ElementEnum.ELEMENT_0630, "1");
                        put(Lo3ElementEnum.ELEMENT_1510, "H");
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

    private List<Lo3CategorieWaarde> maakLijstVoorOmzetting() {
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
                        put(Lo3ElementEnum.ELEMENT_0610, "20150101");
                        put(Lo3ElementEnum.ELEMENT_0620, "5555");
                        put(Lo3ElementEnum.ELEMENT_0630, "1");
                        put(Lo3ElementEnum.ELEMENT_1510, "P");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "3QH1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }), new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_55, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0240, "Graaf");
                        put(Lo3ElementEnum.ELEMENT_0310, "19900101");
                        put(Lo3ElementEnum.ELEMENT_0320, "2222");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0610, "20120101");
                        put(Lo3ElementEnum.ELEMENT_0620, "2222");
                        put(Lo3ElementEnum.ELEMENT_0630, "1");
                        put(Lo3ElementEnum.ELEMENT_1510, "H");
                    }
                }));
    }

    private List<Lo3CategorieWaarde> maakLijstVoorOmzettingFoutief() {
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
                        put(Lo3ElementEnum.ELEMENT_0610, "20150101");
                        put(Lo3ElementEnum.ELEMENT_0620, "5555");
                        put(Lo3ElementEnum.ELEMENT_0630, "1");
                        put(Lo3ElementEnum.ELEMENT_1510, "P");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "3QH1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }), new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_55, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0240, "Graaf");
                        put(Lo3ElementEnum.ELEMENT_0310, "19900101");
                        put(Lo3ElementEnum.ELEMENT_0320, "2222");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0610, "20120101");
                        put(Lo3ElementEnum.ELEMENT_0620, "2222");
                        put(Lo3ElementEnum.ELEMENT_0630, "1");
                        put(Lo3ElementEnum.ELEMENT_1510, "P");
                    }
                }));
    }

    private List<Lo3CategorieWaarde> maakLijstNaamwijziging() {
        return Arrays.asList(
                new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "123456789");
                        put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
                        put(Lo3ElementEnum.ELEMENT_0230, "van");
                        put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
                        put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                        put(Lo3ElementEnum.ELEMENT_0320, "3333");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "1QH1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
                        put(Lo3ElementEnum.ELEMENT_0230, "in het");
                        put(Lo3ElementEnum.ELEMENT_0240, "Veld");
                        put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                        put(Lo3ElementEnum.ELEMENT_0320, "3333");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                    }
                }));
    }

    private List<Lo3CategorieWaarde> maakLijstVoornaamwijziging() {
        return Arrays.asList(
                new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "");
                        put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
                        put(Lo3ElementEnum.ELEMENT_0230, "van");
                        put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
                        put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                        put(Lo3ElementEnum.ELEMENT_0320, "3333");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "1QM1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0210, "Truus");
                        put(Lo3ElementEnum.ELEMENT_0230, "van");
                        put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
                        put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                        put(Lo3ElementEnum.ELEMENT_0320, "3333");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                    }
                }));
    }

    private List<Lo3CategorieWaarde> maakLijstGeslachtswijziging() {
        return Arrays.asList(
                new Lo3CategorieWaarde(Lo3CategorieEnum.PERSOON, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0110, "123456789");
                        put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
                        put(Lo3ElementEnum.ELEMENT_0230, "van");
                        put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
                        put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                        put(Lo3ElementEnum.ELEMENT_0320, "3333");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "V");
                        put(Lo3ElementEnum.ELEMENT_8110, "3333");
                        put(Lo3ElementEnum.ELEMENT_8120, "1QS1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }),
                new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 0, new HashMap<Lo3ElementEnum, String>() {
                    {
                        put(Lo3ElementEnum.ELEMENT_0210, "Sarah");
                        put(Lo3ElementEnum.ELEMENT_0230, "van");
                        put(Lo3ElementEnum.ELEMENT_0240, "Dijk");
                        put(Lo3ElementEnum.ELEMENT_0310, "19700101");
                        put(Lo3ElementEnum.ELEMENT_0320, "3333");
                        put(Lo3ElementEnum.ELEMENT_0330, "1");
                        put(Lo3ElementEnum.ELEMENT_0410, "M");
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
                        put(Lo3ElementEnum.ELEMENT_8120, "3QA1234");
                        put(Lo3ElementEnum.ELEMENT_8510, "20150917");
                    }
                }));
    }
    enum Soort {
        SLUITING, ONTBINDING, ONTBINDING_INCORRECT, OMZETTING, OMZETTING_INCORRECT, NAAMWIJZING, VOORNAAMWIJZIGING, GESLACHTSWIJZIGING, OVERLIJDEN
    }
}
