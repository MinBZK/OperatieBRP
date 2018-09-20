/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Component;

/**
 * Factory om AbstractGbaVoorwaardeRegel objecten aan te maken.
 *
 */
@Component
public class GbaVoorwaardeRegelFactory {

    @Inject
    private GbaVoorwaardeRegel[] gbaVoorwaardeRegels;

    @Inject
    private GbaVoorwaardeRegelComparator gbaVoorwaardeRegelComparator;

    /**
     * Maakt een GbaVoorwaardeRegel object aan van een <b>enkelvoudige</b> gba voorwaarde regel.
     *
     * @param gbaVoorwaardeRegel
     *            De voorwaarderegel obv het object wordt gemaakt
     * @return een GbaVoorwaardeRegel geschikt voor het verwerken van die voorwaarde regel
     */
    public final GbaVoorwaardeRegel maakGbaVoorwaardeRegel(final String gbaVoorwaardeRegel) {
        GbaVoorwaardeRegel result = null;
        final List<GbaVoorwaardeRegel> regels = Arrays.asList(gbaVoorwaardeRegels);
        Collections.sort(regels, gbaVoorwaardeRegelComparator);
        for (final GbaVoorwaardeRegel regel : regels) {
            if (regel.filter(gbaVoorwaardeRegel)) {
                result = regel;
                break;
            }
        }
        return result;
    }
}
