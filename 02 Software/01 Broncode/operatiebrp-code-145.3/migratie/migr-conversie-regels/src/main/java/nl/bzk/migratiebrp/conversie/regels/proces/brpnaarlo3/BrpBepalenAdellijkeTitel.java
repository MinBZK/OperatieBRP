/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

/**
 * Een adellijke titel is in de BRP niet afhankelijk van het geslacht; in LO3 wel.
 *
 * Tijdens de conversie wordt altijd de mannelijke vorm van een adellijke titel (of predikaat) gevuld bij de
 * persoonsgegevens. Dit moet worden aangepast aan de hand van het geslacht.
 */
public class BrpBepalenAdellijkeTitel {

    private final Map<Lo3AdellijkeTitelPredikaatCode, Lo3AdellijkeTitelPredikaatCode> manVrouwAdellijkTitelPredicaatMap;

    /**
     * constructor.
     */
    public BrpBepalenAdellijkeTitel() {
        manVrouwAdellijkTitelPredicaatMap = new HashMap<>();
        manVrouwAdellijkTitelPredicaatMap.put(new Lo3AdellijkeTitelPredikaatCode("B"), new Lo3AdellijkeTitelPredikaatCode("BS"));
        manVrouwAdellijkTitelPredicaatMap.put(new Lo3AdellijkeTitelPredikaatCode("G"), new Lo3AdellijkeTitelPredikaatCode("GI"));
        manVrouwAdellijkTitelPredicaatMap.put(new Lo3AdellijkeTitelPredikaatCode("H"), new Lo3AdellijkeTitelPredikaatCode("HI"));
        manVrouwAdellijkTitelPredicaatMap.put(new Lo3AdellijkeTitelPredikaatCode("JH"), new Lo3AdellijkeTitelPredikaatCode("JV"));
        manVrouwAdellijkTitelPredicaatMap.put(new Lo3AdellijkeTitelPredikaatCode("M"), new Lo3AdellijkeTitelPredikaatCode("MI"));
        manVrouwAdellijkTitelPredicaatMap.put(new Lo3AdellijkeTitelPredikaatCode("P"), new Lo3AdellijkeTitelPredikaatCode("PS"));
        manVrouwAdellijkTitelPredicaatMap.put(new Lo3AdellijkeTitelPredikaatCode("R"), new Lo3AdellijkeTitelPredikaatCode("R"));
    }

    /**
     * Voer de na-conversie uit voor adellijke titels en predikaten.
     * @param persoonslijst persoonslijst
     * @return persoonslijst
     */
    public final Lo3Persoonslijst converteer(final Lo3Persoonslijst persoonslijst) {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder(persoonslijst);

        builder.persoonStapel(verwerkPersoonStapel(persoonslijst.getPersoonStapel()));

        return builder.build();
    }

    private Lo3AdellijkeTitelPredikaatCode bepaalAdellijkeTitelPredikaat(
            final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode,
            final Lo3Geslachtsaanduiding geslachtsaanduiding) {
        Lo3AdellijkeTitelPredikaatCode resultaat = adellijkeTitelPredikaatCode;
        final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCodeZonderOnderzoek;
        adellijkeTitelPredikaatCodeZonderOnderzoek = new Lo3AdellijkeTitelPredikaatCode(adellijkeTitelPredikaatCode.getWaarde(), null);
        if (Lo3Validatie.isElementGevuld(geslachtsaanduiding)
                && Lo3GeslachtsaanduidingEnum.VROUW.getCode().equals(geslachtsaanduiding.getWaarde())
                && manVrouwAdellijkTitelPredicaatMap.get(adellijkeTitelPredikaatCodeZonderOnderzoek) != null) {
            resultaat = manVrouwAdellijkTitelPredicaatMap.get(adellijkeTitelPredikaatCodeZonderOnderzoek);
            final Lo3Onderzoek resultaatOnderzoek =
                    Lo3Onderzoek.bepaalRelevantOnderzoek(Arrays.asList(adellijkeTitelPredikaatCode.getOnderzoek(), geslachtsaanduiding.getOnderzoek()));
            resultaat = new Lo3AdellijkeTitelPredikaatCode(resultaat.getWaarde(), resultaatOnderzoek);
        }
        return resultaat;
    }

    private Lo3Stapel<Lo3PersoonInhoud> verwerkPersoonStapel(final Lo3Stapel<Lo3PersoonInhoud> persoonStapel) {
        final List<Lo3Categorie<Lo3PersoonInhoud>> result = new ArrayList<>();

        for (final Lo3Categorie<Lo3PersoonInhoud> categorie : persoonStapel) {
            result.add(verwerkPersoon(categorie));
        }

        return new Lo3Stapel<>(result);
    }

    private Lo3Categorie<Lo3PersoonInhoud> verwerkPersoon(final Lo3Categorie<Lo3PersoonInhoud> categorie) {
        if (!Lo3Validatie.isElementGevuld(categorie.getInhoud().getAdellijkeTitelPredikaatCode())) {
            return categorie;
        }
        final Lo3PersoonInhoud inhoud = categorie.getInhoud();
        final Lo3PersoonInhoud.Builder builder = new Lo3PersoonInhoud.Builder(inhoud);
        builder.setAdellijkeTitelPredikaatCode(bepaalAdellijkeTitelPredikaat(inhoud.getAdellijkeTitelPredikaatCode(), inhoud.getGeslachtsaanduiding()));

        return new Lo3Categorie<>(builder.build(), categorie.getDocumentatie(), categorie.getHistorie(), categorie.getLo3Herkomst());
    }
}
