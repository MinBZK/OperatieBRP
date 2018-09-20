/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.vergelijk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.ggo.viewer.util.VerwerkerUtil;
import nl.gba.gbav.lo3.GegevensSet;
import nl.gba.gbav.lo3.StapelGS;
import nl.gba.gbav.spontaan.verschilanalyse.PlDiffResult;
import nl.gba.gbav.spontaan.verschilanalyse.StapelMatch;
import nl.gba.gbav.spontaan.verschilanalyse.VoorkomenMatch;

/**
 * Roept GBA-V code aan om twee Lo3 Persoonslijsten met elkaar te vergelijken en het resultaat hiervan te gebruiken om
 * te bepalen wat de lo3Herkomst is van de teruggeconverteerde Lo3Persoonslijst.
 * 
 */
public class Lo3HerkomstBuilder {
    private static final Map<Lo3Herkomst, Lo3Herkomst> HERKOMST = new HashMap<>();

    /**
     * Kopieert de Terugconversie Lo3Persoonslijst en voegt Lo3Herkomst aan toe. Lo3Herkomst wordt bepaalt door de
     * verschil/match analyse uit GBA-V (spontaan).
     * 
     * @param origineel
     *            Lo3Persoonslijst
     * @param teruggeconverteerd
     *            Lo3Persoonslijst
     * @return gekopieerde teruggeconverteerde Lo3Persoonslijst met Lo3Herkomst
     */
    public final Lo3Persoonslijst kopieerTerugconversiePlMetHerkomst(final Lo3Persoonslijst origineel, final Lo3Persoonslijst teruggeconverteerd) {
        Lo3Persoonslijst teruggeconverteerdMetHerkomst = null;

        // Bepaal de verschillen tussen beide PL-en
        final PlDiffResult diffResult = new Lo3Vergelijker().bepaalVerschillen(origineel, teruggeconverteerd);

        // Vul Lo3Persoonslijst teruggeconverteerd aan met lo3Herkomst
        teruggeconverteerdMetHerkomst = kopieerPlMetHerkomst(diffResult, teruggeconverteerd);

        return teruggeconverteerdMetHerkomst;
    }

    private Lo3Persoonslijst kopieerPlMetHerkomst(final PlDiffResult diffResult, final Lo3Persoonslijst teruggeconverteerd) {

        // bepaal herkomsten
        bepaalHerkomsten(diffResult);

        // rebuild lo3 pl
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(kopieerStapelMetHerkomst(teruggeconverteerd.getPersoonStapel(), Lo3CategorieEnum.CATEGORIE_01, 0));
        builder.ouder1Stapel(kopieerStapelMetHerkomst(teruggeconverteerd.getOuder1Stapel(), Lo3CategorieEnum.CATEGORIE_02, 0));
        builder.ouder2Stapel(kopieerStapelMetHerkomst(teruggeconverteerd.getOuder2Stapel(), Lo3CategorieEnum.CATEGORIE_03, 0));
        builder.nationaliteitStapels(kopieerStapelsMetHerkomst(teruggeconverteerd.getNationaliteitStapels(), Lo3CategorieEnum.CATEGORIE_04));
        builder.huwelijkOfGpStapels(kopieerStapelsMetHerkomst(teruggeconverteerd.getHuwelijkOfGpStapels(), Lo3CategorieEnum.CATEGORIE_05));
        builder.overlijdenStapel(kopieerStapelMetHerkomst(teruggeconverteerd.getOverlijdenStapel(), Lo3CategorieEnum.CATEGORIE_06, 0));
        builder.inschrijvingStapel(kopieerStapelMetHerkomst(teruggeconverteerd.getInschrijvingStapel(), Lo3CategorieEnum.CATEGORIE_07, 0));
        builder.verblijfplaatsStapel(kopieerStapelMetHerkomst(teruggeconverteerd.getVerblijfplaatsStapel(), Lo3CategorieEnum.CATEGORIE_08, 0));
        builder.kindStapels(kopieerStapelsMetHerkomst(teruggeconverteerd.getKindStapels(), Lo3CategorieEnum.CATEGORIE_09));
        builder.verblijfstitelStapel(kopieerStapelMetHerkomst(teruggeconverteerd.getVerblijfstitelStapel(), Lo3CategorieEnum.CATEGORIE_10, 0));
        builder.gezagsverhoudingStapel(kopieerStapelMetHerkomst(teruggeconverteerd.getGezagsverhoudingStapel(), Lo3CategorieEnum.CATEGORIE_11, 0));
        builder.reisdocumentStapels(kopieerStapelsMetHerkomst(teruggeconverteerd.getReisdocumentStapels(), Lo3CategorieEnum.CATEGORIE_12));
        builder.kiesrechtStapel(kopieerStapelMetHerkomst(teruggeconverteerd.getKiesrechtStapel(), Lo3CategorieEnum.CATEGORIE_13, 0));
        return builder.build();
    }

    private void bepaalHerkomsten(final PlDiffResult diffResult) {
        for (int i = 0; i < diffResult.countStapelMatches(); i++) {
            final StapelMatch stapelMatch = diffResult.getStapelMatch(i);
            if (stapelMatch.getType() == StapelMatch.IDENTICAL) {
                final List<StapelGS> stapels = stapelMatch.getNewStapels() != null ? stapelMatch.getNewStapels() : stapelMatch.getOldStapels();
                for (final StapelGS stapel : stapels) {
                    for (int j = 0; j < stapel.getAantal(); j++) {
                        final Lo3Herkomst gbavPlTerugconversieHerkomst = maakHerkomst(stapel.getGegevensSet(j));
                        HERKOMST.put(gbavPlTerugconversieHerkomst, gbavPlTerugconversieHerkomst);
                    }
                }
            } else {
                for (int j = 0; j < stapelMatch.countVoorkomenMatches() + 1; j++) {
                    if (stapelMatch.getVoorkomenMatch(j) != null) {
                        final VoorkomenMatch voorkomenMatch = stapelMatch.getVoorkomenMatch(j);
                        if (voorkomenMatch.getOldVoorkomen() != null && voorkomenMatch.getNewVoorkomen() != null) {
                            final Lo3Herkomst gbavPlHerkomst = maakHerkomst(voorkomenMatch.getOldVoorkomen());
                            final Lo3Herkomst gbavPlTerugconversieHerkomst = maakHerkomst(voorkomenMatch.getNewVoorkomen());
                            HERKOMST.put(gbavPlTerugconversieHerkomst, gbavPlHerkomst);
                        }
                    }
                }
            }
        }
    }

    private <T extends Lo3CategorieInhoud> List<Lo3Stapel<T>> kopieerStapelsMetHerkomst(final List<Lo3Stapel<T>> lo3Stapels, final Lo3CategorieEnum catNr)
    {
        final List<Lo3Stapel<T>> stapels = new ArrayList<>();
        int stapelNr = 0;
        for (final Lo3Stapel<T> lo3Stapel : lo3Stapels) {
            stapels.add(kopieerStapelMetHerkomst(lo3Stapel, catNr, stapelNr));
            stapelNr++;
        }
        return stapels;
    }

    private <T extends Lo3CategorieInhoud> Lo3Stapel<T> kopieerStapelMetHerkomst(
        final Lo3Stapel<T> lo3Stapel,
        final Lo3CategorieEnum catNr,
        final int stapelNr)
    {
        if (lo3Stapel == null) {
            return null;
        }

        final List<Lo3Categorie<T>> categorieen = new ArrayList<>();
        int volgNr = lo3Stapel.getCategorieen().size() - 1;
        for (final Lo3Categorie<T> categorie : lo3Stapel.getCategorieen()) {
            final Lo3Herkomst lo3Herkomst = HERKOMST.get(new Lo3Herkomst(VerwerkerUtil.maakCatNrHistorisch(catNr, volgNr), stapelNr, volgNr));
            categorieen.add(new Lo3Categorie<>(categorie.getInhoud(), categorie.getDocumentatie(), categorie.getHistorie(), lo3Herkomst));
            volgNr--;
        }
        return new Lo3Stapel<>(categorieen);
    }

    private Lo3Herkomst maakHerkomst(final GegevensSet voorkomen) {
        final int catNr = VerwerkerUtil.maakCatNrHistorisch(voorkomen.getCategorieNr(), voorkomen.getVolgNr());
        return new Lo3Herkomst(Lo3CategorieEnum.getLO3Categorie(catNr), voorkomen.getStapelNr(), voorkomen.getVolgNr());
    }
}
