/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.bzk.migratiebrp.conversie.regels.expressie.Lo3VoorwaardeRegelOnvertaalbaarExceptie;

/**
 * Split een gegeven expressie op ENVWD (en voorwaarde) en OFVWD (of voorwaarde). De voorwaarde wordt vertaald naar een
 * voorwaarde met variabelen die overeenkomen met de map waarin de voorwaarde per variabelen zijn genoemd
 *
 * de voorwaarde: ((01.04.10 GA1 \"V\") ENVWD (01.03.10 KDOG1 19.89.20 - 0029)) OFVWD ((01.04.10 GA1 \"M\") ENVWD
 * (01.03.10 KDOG1 19.89.20 - 0054))
 *
 * wordt vertaald naar: ((K0) EN (K1)) OF ((K2) EN (K3))
 *
 * In de bijbehorende map komen de voorwaarden: sleutel: voorwaarde K0: 01.04.10 GA1 \"V\" K1: 01.03.10 KDOG1 19.89.20 -
 * 0029 K2: 01.04.10 GA1 \"M\" K3: 01.03.10 KDOG1 19.89.20 - 0054
 *
 */
class VoorwaardeRegelNaarEnkelvoudigeVoorwaarde {

    private static final DecimalFormat TELLER_FORMAT = new DecimalFormat("00");

    private static final String ENVWD = "ENVWD";
    private static final String OFVWD = "OFVWD";
    private final String gbaVoorwaardeRegel;
    private String voorwaardeRegel;
    private final Map<String, String> gbaVoorwaardeMap = new HashMap<>();

    /**
     * Instantieert een nieuw VoorwaardeRegelNaarEnkelvoudigeVoorwaarde object met de te vertalen GBA voorwaarde
     * meegegeven.
     *
     * @param gbaVoorwaardeRegel
     *            De GBA voorwaarderegel welke vertaald moet worden
     */
    public VoorwaardeRegelNaarEnkelvoudigeVoorwaarde(final String gbaVoorwaardeRegel) {
        this.gbaVoorwaardeRegel = gbaVoorwaardeRegel;
        process();
    }

    /**
     * Geeft de voorwaarderegel terug met placeholders voor de gebruikte voorwaarden. Na vertaling van deze voorwaarden
     * kunnen deze worden ingevoegd op de plaats van de placeholder
     *
     * @return de voorwaarderegel geschikt voor verdere verwerking
     */
    public String getVoorwaardeRegel() {
        return voorwaardeRegel;
    }

    /**
     * Geeft de losse GBA voorwaarden in een map terug, waarvan de sleutel overeenkomt met voorwaarde regel uit
     * getVoorwaardeRegel.
     *
     * @return map met de losse GBA voorwaarden
     */
    public Map<String, String> getGbaVoorwaardeMap() {
        return gbaVoorwaardeMap;
    }

    private void process() {
        // Tel dat het aantal haakjes klopt
        final TekstHelper tekstHelper = new TekstHelper(gbaVoorwaardeRegel);
        final int aantalHaakjesOpenen = count(tekstHelper.getVeiligeRegel(), '(');
        final int aantalHaakjesSluiten = count(tekstHelper.getVeiligeRegel(), ')');
        if (aantalHaakjesOpenen != aantalHaakjesSluiten) {
            throw new Lo3VoorwaardeRegelOnvertaalbaarExceptie(gbaVoorwaardeRegel, null, new IllegalArgumentException(
                    "Onsamenhangend aantal haakjes openen en sluiten."));
        }

        final List<String> ruweVoorwaarden = splitsGBAVoorwaardenRegelInLosseRuweRegels();

        int teller = 0;
        for (final String ruweVoorwaarde : ruweVoorwaarden) {
            // Hier al spaties weghalen (en 'dangling haakjes')
            gbaVoorwaardeMap.put("K" + formatTeller(teller), verwijderVoorwaardeRelevanteHaakjes(ruweVoorwaarde.trim()));
            teller++;
        }

        voorwaardeRegel = gbaVoorwaardeRegel.replaceAll(OFVWD, "OF").replaceAll(ENVWD, "EN");
        for (final Entry<String, String> item : gbaVoorwaardeMap.entrySet()) {
            final Pattern pattern = Pattern.compile(item.getValue(), Pattern.LITERAL);
            final Matcher matcher = pattern.matcher(voorwaardeRegel);
            voorwaardeRegel = matcher.replaceAll(item.getKey());

            // Hier de rest van de haakjes weghalen
            item.setValue(verwijderAlleHaakjes(item.getValue()));
        }
    }

    private String verwijderAlleHaakjes(final String value) {
        final TekstHelper tekstHelper = new TekstHelper(value);
        tekstHelper.setVeiligeRegel(tekstHelper.getVeiligeRegel().replaceAll("\\(", "").replaceAll("\\)", ""));
        return tekstHelper.getGbaVoorwaardeRegel();
    }

    private String verwijderVoorwaardeRelevanteHaakjes(final String voorwaardeDeel) {
        final TekstHelper tekstHelper = new TekstHelper(voorwaardeDeel);

        final int aantalHaakjesOpenen = count(tekstHelper.getVeiligeRegel(), '(');
        final int aantalHaakjesSluiten = count(tekstHelper.getVeiligeRegel(), ')');

        final StringBuilder result = new StringBuilder(tekstHelper.getVeiligeRegel());
        if (aantalHaakjesOpenen > aantalHaakjesSluiten) {
            for (int i = 0; i < aantalHaakjesOpenen - aantalHaakjesSluiten; i++) {
                result.deleteCharAt(result.indexOf("("));
            }
        } else if (aantalHaakjesSluiten > aantalHaakjesOpenen) {
            for (int i = 0; i < aantalHaakjesSluiten - aantalHaakjesOpenen; i++) {
                result.deleteCharAt(result.lastIndexOf(")"));
            }
        }
        tekstHelper.setVeiligeRegel(result.toString());
        return tekstHelper.getGbaVoorwaardeRegel();
    }

    private int count(final String value, final char c) {
        int result = 0;
        int index = -1;

        while ((index = value.indexOf(c, index + 1)) > -1) {
            result++;
        }

        return result;
    }

    private String formatTeller(final int teller) {
        return TELLER_FORMAT.format(teller);
    }

    private List<String> splitsGBAVoorwaardenRegelInLosseRuweRegels() {
        final String[] individueleVoorwaardeOfDelen = gbaVoorwaardeRegel.split(OFVWD);
        final List<String> ruweVoorwaarden = new ArrayList<>();
        for (final String individueleVoorwaardeOfDeel : individueleVoorwaardeOfDelen) {
            final String[] ruweIndividueleVoorwaarden = individueleVoorwaardeOfDeel.split(ENVWD);
            ruweVoorwaarden.addAll(Arrays.asList(ruweIndividueleVoorwaarden));
        }
        return ruweVoorwaarden;
    }
}
