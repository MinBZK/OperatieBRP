/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl;

import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Component;

/**
 * Factory om AbstractGbaVoorwaardeRegel objecten aan te maken.
 */
@Component
public class GbaVoorwaardeRegelFactory {

    private final List<GbaVoorwaardeRegel> gbaVoorwaardeRegels;

    /**
     * Constructor.
     * @param gbaVoorwaardeRegels gba voorwaarde regel vertalers
     * @param gbaVoorwaardeRegelComparator vergelijker voor voorwaarde regel vertalers
     */
    @Inject
    public GbaVoorwaardeRegelFactory(final GbaVoorwaardeRegel[] gbaVoorwaardeRegels, final GbaVoorwaardeRegelComparator gbaVoorwaardeRegelComparator) {
        this.gbaVoorwaardeRegels = Arrays.asList(Arrays.copyOf(gbaVoorwaardeRegels, gbaVoorwaardeRegels.length));
        this.gbaVoorwaardeRegels.sort(gbaVoorwaardeRegelComparator);
    }

    /**
     * Maakt een GbaVoorwaardeRegel object aan van een <b>enkelvoudige</b> gba voorwaarde regel.
     * @param gbaVoorwaardeRegel De voorwaarderegel obv het object wordt gemaakt
     * @return een GbaVoorwaardeRegel geschikt voor het verwerken van die voorwaarde regel
     */
    public final GbaVoorwaardeRegel maakGbaVoorwaardeRegel(final RubriekWaarde gbaVoorwaardeRegel) {
        GbaVoorwaardeRegel result = null;
        for (final GbaVoorwaardeRegel regel : gbaVoorwaardeRegels) {
            if (regel.filter(gbaVoorwaardeRegel.getLo3Expressie())) {
                result = regel;
                break;
            }
        }
        return result;
    }
}
