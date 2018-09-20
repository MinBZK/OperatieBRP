/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import nl.bzk.brp.preview.model.Melding;

/**
 * Deze comparator zorgt ervoor dat de meldingen op volgorde van belangrijkheid worden getoond. Er wordt gekeken
 * naar het soort van de melding.
 */
public class MeldingComparator implements Comparator<Melding> {

    /**
     * De soort codes van meldingen, gesorteerd op belangrijkheid. De code 'F' is het belangrijkste en zal altijd
     * bovenaan in de gesorteerde lijst komen.
     */
    private static final List<String> MELDING_CODES_GESORTEERD = Arrays.asList(new String[]{"F", "W", "O", "I"});

    /**
     * @see Comparator#compare(Object, Object)
     * @param m1 Melding 1
     * @param m2 Melding 2
     * @return De vergelijking tussen melding 1 en 2 als int.
     */
    @Override
    public int compare(final Melding m1, final Melding m2) {
        final String soortCode1 = m1.getSoort().getCode();
        final String soortCode2 = m2.getSoort().getCode();

        final Integer indexM1 = getIndexVoorMeldingSoortCode(soortCode1);
        final Integer indexM2 = getIndexVoorMeldingSoortCode(soortCode2);

        return indexM1.compareTo(indexM2);
    }

    /**
     * Haalt een index voor een melding soort code op.
     *
     * @param meldingSoortCode de melding soort code
     * @return index voor melding soort code
     */
    private Integer getIndexVoorMeldingSoortCode(final String meldingSoortCode) {
        final Integer resultaat;
        if (MELDING_CODES_GESORTEERD.contains(meldingSoortCode)) {
            resultaat = MELDING_CODES_GESORTEERD.indexOf(meldingSoortCode);
        } else {
            //Onbekende melding soortcode, ga er vanuit dat deze onbelangrijk is
            resultaat = MELDING_CODES_GESORTEERD.size();
        }
        return resultaat;
    }
}
