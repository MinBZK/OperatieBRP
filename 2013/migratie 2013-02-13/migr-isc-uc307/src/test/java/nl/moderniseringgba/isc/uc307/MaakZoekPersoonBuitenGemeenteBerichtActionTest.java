/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc307;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtOnbekendException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3BerichtFactory;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb01Bericht;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;

import org.junit.Assert;
import org.junit.Test;

public class MaakZoekPersoonBuitenGemeenteBerichtActionTest {

    // Gebruikte gegevens moeder.
    private static final String WAARDE_ANUMMER_MOEDER = "12345678";
    private static final String WAARDE_BSN_MOEDER = "1234567890";
    private static final String WAARDE_VOORNAMEN_MOEDER = "Carla";
    private static final String WAARDE_ADELLIJKEPREDIKAATCODE_MOEDER = "K";
    private static final String WAARDE_VOORVOEGSEL_MOEDER = "van";
    private static final String WAARDE_GESLACHTSNAAM_MOEDER = "Leeuwen";
    private static final String WAARDE_GEBOORTEGEMEENTECODE_MOEDER = "1904";
    private static final String WAARDE_GEBOORTELANDCODE_MOEDER = "3010";
    private static final String WAARDE_GESLACHTSAANDUIDING_MOEDER = "V";
    private static final String WAARDE_FAMILIERECHTELIJKEBETREKKING_MOEDER = "20130104";

    /**
     * LO3-factory voor het converteren van teletext naar Lo3-bericht.
     */
    private static final Lo3BerichtFactory LO3_FACTORY = new Lo3BerichtFactory();

    /**
     * Doelgemeente voor de test.
     */
    private static final String DOELGEMEENTE = "5678";

    /**
     * Brongemeente voor de test.
     */
    private static final String BRONGEMEENTE = "1234";

    /**
     * Teletex string van het Tb01 bericht.
     */
    private static final String TB01_TELETEX =
            "00000000Tb010M1 A123400317010990210011Maarten Jan0230003van0240005Haren03100082012110103200040600033000460300410001M81200071 A1234021030210013"
                    + "Gina Jennifer0230003van0240006Lloyds03100081990100103200040599033000460300410001V621000820121101031000210011Hans Herman0230003van0240005Haren0"
                    + "3100081989010103200040599033000460300410001M621000820121101";

    private final MaakZoekPersoonBuitenGemeenteBerichtAction maakZoekPersoonBuitenGemeenteBerichtAction =
            new MaakZoekPersoonBuitenGemeenteBerichtAction();

    @Test
    public void testHappyFlow() throws BerichtSyntaxException, BerichtOnbekendException, BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", maakTb01BerichtVoorHappyFlow());

        final Map<String, Object> result = maakZoekPersoonBuitenGemeenteBerichtAction.execute(parameters);
        Assert.assertNotNull("Bij HappyFlow hoort het zoekPersoonBuitenGemeenteBericht niet 'null' te zijn.",
                result.get("zoekPersoonBuitenGemeenteBericht"));
    }

    @Test
    public void testHappyFlowZonderGeboorteDatumMoeder() throws BerichtSyntaxException, BerichtOnbekendException,
            BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", maakTb01BerichtZonderGeboorteDatumMoeder());

        final Map<String, Object> result = maakZoekPersoonBuitenGemeenteBerichtAction.execute(parameters);
        Assert.assertNotNull("Bij HappyFlow hoort het zoekPersoonBuitenGemeenteBericht niet 'null' te zijn.",
                result.get("zoekPersoonBuitenGemeenteBericht"));
    }

    private Object maakTb01BerichtVoorHappyFlow() throws BerichtSyntaxException, BerichtOnbekendException,
            BerichtInhoudException {

        // Tb01 bericht als teletex string.
        System.out.println("TB01 text: " + TB01_TELETEX);

        // Stel het Tb01 bericht op basis van de teletext string op.
        final Tb01Bericht tb01_bericht = (Tb01Bericht) LO3_FACTORY.getBericht(TB01_TELETEX);
        tb01_bericht.setDoelGemeente(DOELGEMEENTE);
        tb01_bericht.setBronGemeente(BRONGEMEENTE);
        return tb01_bericht;
    }

    private Object maakTb01BerichtZonderGeboorteDatumMoeder() {

        final Lo3Persoonslijst lo3Persoonslijst = maakLo3Persoonslijst();

        // Stel het Tb01 bericht op basis van de teletext string op.
        final Tb01Bericht tb01_bericht = new Tb01Bericht();
        tb01_bericht.setLo3Persoonslijst(lo3Persoonslijst);
        tb01_bericht.setDoelGemeente(DOELGEMEENTE);
        tb01_bericht.setBronGemeente(BRONGEMEENTE);
        return tb01_bericht;
    }

    private Lo3Persoonslijst maakLo3Persoonslijst() {

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();

        final Lo3Historie historie =
                new Lo3Historie(null, new Lo3Datum(Integer.valueOf(19500101)),
                        new Lo3Datum(Integer.valueOf(19500101)));

        final Lo3Herkomst herkomstOuder1Inhoud = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 1, 1);

        final Lo3Herkomst herkomstOuder2Inhoud = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_03, 1, 1);

        Lo3OuderInhoud moederInhoud = null;
        Lo3OuderInhoud vaderInhoud = null;

        // Vul ouder1Inhoud met de moedergegevens.
        moederInhoud =
                new Lo3OuderInhoud(Long.valueOf(WAARDE_ANUMMER_MOEDER), Long.valueOf(WAARDE_BSN_MOEDER),
                        WAARDE_VOORNAMEN_MOEDER, new Lo3AdellijkeTitelPredikaatCode(
                                WAARDE_ADELLIJKEPREDIKAATCODE_MOEDER), WAARDE_VOORVOEGSEL_MOEDER,
                        WAARDE_GESLACHTSNAAM_MOEDER, null, new Lo3GemeenteCode(WAARDE_GEBOORTEGEMEENTECODE_MOEDER),
                        new Lo3LandCode(WAARDE_GEBOORTELANDCODE_MOEDER), new Lo3Geslachtsaanduiding(
                                WAARDE_GESLACHTSAANDUIDING_MOEDER), new Lo3Datum(
                                Integer.valueOf(WAARDE_FAMILIERECHTELIJKEBETREKKING_MOEDER)));

        // Vul ouder2Inhoud met de vadergegevens.
        vaderInhoud = new Lo3OuderInhoud(null, null, null, null, null, null, null, null, null, null, null);

        Lo3Categorie<Lo3OuderInhoud> categorieOuder1Inhoud;
        Lo3Categorie<Lo3OuderInhoud> categorieOuder2Inhoud;

        categorieOuder1Inhoud = new Lo3Categorie<Lo3OuderInhoud>(moederInhoud, null, historie, herkomstOuder1Inhoud);

        categorieOuder2Inhoud = new Lo3Categorie<Lo3OuderInhoud>(vaderInhoud, null, historie, herkomstOuder2Inhoud);

        final List<Lo3Categorie<Lo3OuderInhoud>> lijstOuder1Inhoud = new ArrayList<Lo3Categorie<Lo3OuderInhoud>>();
        lijstOuder1Inhoud.add(categorieOuder1Inhoud);

        final List<Lo3Categorie<Lo3OuderInhoud>> lijstOuder2Inhoud = new ArrayList<Lo3Categorie<Lo3OuderInhoud>>();
        lijstOuder2Inhoud.add(categorieOuder2Inhoud);

        final Lo3Stapel<Lo3OuderInhoud> ouder1Stapel = new Lo3Stapel<Lo3OuderInhoud>(lijstOuder1Inhoud);
        final Lo3Stapel<Lo3OuderInhoud> ouder2Stapel = new Lo3Stapel<Lo3OuderInhoud>(lijstOuder2Inhoud);

        builder.ouder1Stapel(ouder1Stapel);
        builder.ouder2Stapel(ouder2Stapel);

        return builder.build();
    }

}
