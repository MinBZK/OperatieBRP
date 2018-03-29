/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.migratiebrp.ggo.viewer.util.NaamUtil;
import nl.bzk.migratiebrp.ggo.viewer.util.VerwerkerUtil;
import nl.gba.gbav.lo3.GegevensSet;
import nl.gba.gbav.spontaan.verschilanalyse.VoorkomenMatch;
import org.apache.commons.lang3.StringUtils;

/**
 * Als de GBAv class StapelMatch, maar dan wat eenvoudiger te verwerken voor JSON omdat alle lists gewoon exposed zijn.
 */
public class GgoVoorkomenVergelijking {
    private static final String UNDERSCORE = "_";
    private static final String PUNT = ".";
    private static final int ELEMENT_SIZE = 4;

    private final String categorie;
    private final String type;
    private final String inhoud;
    private final List<String> oudeHerkomsten = new ArrayList<>();
    private final List<String> nieuweHerkomsten = new ArrayList<>();

    /**
     * Constructor.
     * @param voorkomenMatch de StapelMatch waarop deze StapelVergelijking zich zal baseren.
     * @param aNummer Het anummer van de PL.
     * @param type Het type van de stapelmatch.
     */
    public GgoVoorkomenVergelijking(final VoorkomenMatch voorkomenMatch, final String aNummer, final String type) {
        if (voorkomenMatch.getOldVoorkomen() != null) {
            createHerkomstClassName(aNummer, voorkomenMatch.getCategorie(), voorkomenMatch.getOldVoorkomen(), oudeHerkomsten);
        }
        if (voorkomenMatch.getNewVoorkomen() != null) {
            createHerkomstClassName(aNummer, voorkomenMatch.getCategorie(), voorkomenMatch.getNewVoorkomen(), nieuweHerkomsten);
        }
        this.type = type.toLowerCase();

        categorie = NaamUtil.createLo3CategorieStapelLabel(getCategorie(voorkomenMatch));

        // Alle elementen in de inhoud
        if (voorkomenMatch.getDifference() != null) {
            final StringBuilder joinedElements = new StringBuilder();

            createDiffElements(voorkomenMatch, joinedElements);
            inhoud = joinedElements.toString();
        } else {
            inhoud = null;
        }
    }

    private void createDiffElements(final VoorkomenMatch voorkomenMatch, final StringBuilder joinedElements) {
        final Set<Integer> elementen = new HashSet<>();
        if (voorkomenMatch.getDifference().getGegevensSet(voorkomenMatch.getCategorie(), 0, 0) != null) {
            elementen.addAll(voorkomenMatch.getDifference().getGegevensSet(voorkomenMatch.getCategorie(), 0, 0).getElements());
        }
        if (voorkomenMatch.getDifference().getGegevensSet(voorkomenMatch.getCategorie(), 0, 1) != null) {
            elementen.addAll(voorkomenMatch.getDifference().getGegevensSet(voorkomenMatch.getCategorie(), 0, 1).getElements());
        }

        for (final Integer element : elementen) {
            if (joinedElements.length() > 0) {
                joinedElements.append(", ");
            }
            final String elementStr = intElementToString(element, ELEMENT_SIZE);
            final int elementSplitIndex = 2;
            joinedElements.append(elementStr.substring(0, elementSplitIndex)).append(PUNT);
            joinedElements.append(elementStr.substring(elementSplitIndex, ELEMENT_SIZE));
        }
    }

    private String intElementToString(final Integer element, final int size) {
        String elementLabel = null;
        if (element != null) {
            elementLabel = String.valueOf(element);
            elementLabel = StringUtils.leftPad(elementLabel, size, "0");
        }
        return elementLabel;
    }

    private void createHerkomstClassName(
            final String aNummer,
            final Integer categorieNr,
            final GegevensSet voorkomen,
            final List<String> herkomsten) {
        // maak categorieNr historisch indien nodig
        final int catNr = VerwerkerUtil.maakCatNrHistorisch(categorieNr, voorkomen.getVolgNr());
        final String className =
                "" + aNummer + UNDERSCORE + catNr + UNDERSCORE + voorkomen.getStapelNr() + UNDERSCORE + voorkomen.getVolgNr();
        herkomsten.add(className);
    }

    private int getCategorie(final VoorkomenMatch voorkomenMatch) {
        int volgNr = 0;
        if (voorkomenMatch.getOldVoorkomen() != null) {
            volgNr = voorkomenMatch.getOldVoorkomen().getVolgNr();
        } else if (voorkomenMatch.getNewVoorkomen() != null) {
            volgNr = voorkomenMatch.getNewVoorkomen().getVolgNr();
        }
        return VerwerkerUtil.maakCatNrHistorisch(voorkomenMatch.getCategorie(), volgNr);
    }

    /**
     * Geef de waarde van inhoud.
     * @return the inhoud
     */
    public final String getInhoud() {
        return inhoud;
    }

    /**
     * Geef de waarde van oude herkomsten.
     * @return the oudeHerkomsten
     */
    public final List<String> getOudeHerkomsten() {
        return oudeHerkomsten;
    }

    /**
     * Geef de waarde van nieuwe herkomsten.
     * @return the nieuweHerkomsten
     */
    public final List<String> getNieuweHerkomsten() {
        return nieuweHerkomsten;
    }

    /**
     * Geef de waarde van type.
     * @return the type
     */
    public final String getType() {
        return type;
    }

    /**
     * Geef de waarde van categorie.
     * @return the categorie
     */
    public final String getCategorie() {
        return categorie;
    }

}
