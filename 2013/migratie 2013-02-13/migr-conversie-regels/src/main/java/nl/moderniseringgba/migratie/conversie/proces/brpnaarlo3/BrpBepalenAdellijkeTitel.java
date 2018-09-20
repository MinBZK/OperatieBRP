/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;

import org.springframework.stereotype.Component;

/**
 * Een adellijke titel is in de BRP niet afhankelijk van het geslacht; in LO3 wel.
 * 
 * Tijdens de conversie wordt altijd de mannelijke vorm van een adellijke titel (of predikaat) gevuld bij de
 * persoonsgegevens. Dit moet worden aangepast aan de hand van het geslacht.
 */
@Component
public class BrpBepalenAdellijkeTitel {

    // CHECKSTYLE:OFF - Statische teksten
    @SuppressWarnings("serial")
    private static final Map<Lo3AdellijkeTitelPredikaatCode, Lo3AdellijkeTitelPredikaatCode> MAP = Collections
            .unmodifiableMap(new HashMap<Lo3AdellijkeTitelPredikaatCode, Lo3AdellijkeTitelPredikaatCode>() {
                {
                    put(new Lo3AdellijkeTitelPredikaatCode("B"), new Lo3AdellijkeTitelPredikaatCode("BS"));
                    put(new Lo3AdellijkeTitelPredikaatCode("G"), new Lo3AdellijkeTitelPredikaatCode("GI"));
                    put(new Lo3AdellijkeTitelPredikaatCode("H"), new Lo3AdellijkeTitelPredikaatCode("HI"));
                    put(new Lo3AdellijkeTitelPredikaatCode("JH"), new Lo3AdellijkeTitelPredikaatCode("JV"));
                    put(new Lo3AdellijkeTitelPredikaatCode("M"), new Lo3AdellijkeTitelPredikaatCode("MI"));
                    put(new Lo3AdellijkeTitelPredikaatCode("P"), new Lo3AdellijkeTitelPredikaatCode("PS"));
                    put(new Lo3AdellijkeTitelPredikaatCode("R"), new Lo3AdellijkeTitelPredikaatCode("R"));
                }
            });

    // CHECKSTYLE:ON

    /**
     * Voer de na-conversie uit voor adellijke titels en predikaten.
     * 
     * @param persoonslijst
     *            persoonslijst
     * @return persoonslijst
     */
    public final Lo3Persoonslijst converteer(final Lo3Persoonslijst persoonslijst) {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder(persoonslijst);

        builder.persoonStapel(verwerkPersoonStapel(persoonslijst.getPersoonStapel()));
        builder.ouder1Stapels(verwerkOuderStapels(persoonslijst.getOuder1Stapels()));
        builder.ouder2Stapels(verwerkOuderStapels(persoonslijst.getOuder2Stapels()));
        builder.huwelijkOfGpStapels(verwerkHuwelijkStapels(persoonslijst.getHuwelijkOfGpStapels()));

        return builder.build();
    }

    private Lo3AdellijkeTitelPredikaatCode bepaalAdellijkeTitelPredikaat(
            final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode,
            final Lo3Geslachtsaanduiding geslachtsaanduiding) {
        if (geslachtsaanduiding != null
                && Lo3GeslachtsaanduidingEnum.VROUW.getCode().equals(geslachtsaanduiding.getCode())) {
            return MAP.get(adellijkeTitelPredikaatCode);
        } else {
            return adellijkeTitelPredikaatCode;
        }

    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private Lo3Stapel<Lo3PersoonInhoud> verwerkPersoonStapel(final Lo3Stapel<Lo3PersoonInhoud> persoonStapel) {
        final List<Lo3Categorie<Lo3PersoonInhoud>> result = new ArrayList<Lo3Categorie<Lo3PersoonInhoud>>();

        for (final Lo3Categorie<Lo3PersoonInhoud> categorie : persoonStapel) {
            result.add(verwerkPersoon(categorie));
        }

        return new Lo3Stapel<Lo3PersoonInhoud>(result);
    }

    private Lo3Categorie<Lo3PersoonInhoud> verwerkPersoon(final Lo3Categorie<Lo3PersoonInhoud> categorie) {
        if (categorie.getInhoud().getAdellijkeTitelPredikaatCode() == null) {
            return categorie;
        }
        final Lo3PersoonInhoud inhoud = categorie.getInhoud();
        final Lo3PersoonInhoud.Builder builder = new Lo3PersoonInhoud.Builder(inhoud);
        builder.setAdellijkeTitelPredikaatCode(bepaalAdellijkeTitelPredikaat(inhoud.getAdellijkeTitelPredikaatCode(),
                inhoud.getGeslachtsaanduiding()));

        return new Lo3Categorie<Lo3PersoonInhoud>(builder.build(), categorie.getDocumentatie(),
                categorie.getHistorie(), categorie.getLo3Herkomst());
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private List<Lo3Stapel<Lo3OuderInhoud>> verwerkOuderStapels(final List<Lo3Stapel<Lo3OuderInhoud>> stapels) {
        final List<Lo3Stapel<Lo3OuderInhoud>> result = new ArrayList<Lo3Stapel<Lo3OuderInhoud>>();

        for (final Lo3Stapel<Lo3OuderInhoud> stapel : stapels) {
            result.add(verwerkOuderStapel(stapel));
        }

        return result;
    }

    private Lo3Stapel<Lo3OuderInhoud> verwerkOuderStapel(final Lo3Stapel<Lo3OuderInhoud> stapel) {
        final List<Lo3Categorie<Lo3OuderInhoud>> result = new ArrayList<Lo3Categorie<Lo3OuderInhoud>>();

        for (final Lo3Categorie<Lo3OuderInhoud> categorie : stapel) {
            result.add(verwerkOuder(categorie));
        }

        return new Lo3Stapel<Lo3OuderInhoud>(result);
    }

    private Lo3Categorie<Lo3OuderInhoud> verwerkOuder(final Lo3Categorie<Lo3OuderInhoud> categorie) {
        if (categorie.getInhoud().getAdellijkeTitelPredikaatCode() == null) {
            return categorie;
        }
        final Lo3OuderInhoud inhoud = categorie.getInhoud();
        final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder(inhoud);
        builder.setAdellijkeTitelPredikaatCode(bepaalAdellijkeTitelPredikaat(inhoud.getAdellijkeTitelPredikaatCode(),
                inhoud.getGeslachtsaanduiding()));

        return new Lo3Categorie<Lo3OuderInhoud>(builder.build(), categorie.getDocumentatie(),
                categorie.getHistorie(), categorie.getLo3Herkomst());
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> verwerkHuwelijkStapels(
            final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> stapels) {
        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> result = new ArrayList<Lo3Stapel<Lo3HuwelijkOfGpInhoud>>();

        for (final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel : stapels) {
            result.add(verwerkHuwelijkStapel(stapel));
        }

        return result;
    }

    private Lo3Stapel<Lo3HuwelijkOfGpInhoud> verwerkHuwelijkStapel(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel) {
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> result = new ArrayList<Lo3Categorie<Lo3HuwelijkOfGpInhoud>>();

        for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie : stapel) {
            result.add(verwerkHuwelijk(categorie));
        }

        return new Lo3Stapel<Lo3HuwelijkOfGpInhoud>(result);
    }

    private Lo3Categorie<Lo3HuwelijkOfGpInhoud> verwerkHuwelijk(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie) {
        if (categorie.getInhoud().getAdellijkeTitelPredikaatCode() == null) {
            return categorie;
        }
        final Lo3HuwelijkOfGpInhoud inhoud = categorie.getInhoud();
        final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder(inhoud);
        builder.setAdellijkeTitelPredikaatCode(bepaalAdellijkeTitelPredikaat(inhoud.getAdellijkeTitelPredikaatCode(),
                inhoud.getGeslachtsaanduiding()));

        return new Lo3Categorie<Lo3HuwelijkOfGpInhoud>(builder.build(), categorie.getDocumentatie(),
                categorie.getHistorie(), categorie.getLo3Herkomst());
    }
}
