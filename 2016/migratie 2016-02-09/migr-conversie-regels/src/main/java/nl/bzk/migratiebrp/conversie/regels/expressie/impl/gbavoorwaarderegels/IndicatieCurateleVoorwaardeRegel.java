/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import org.springframework.stereotype.Component;

/**
 * Vertaald GBA voorwaarde regels van type indicatie onder curatele; hiervoor wordt een conversie tabel gebruikt.
 */
@Component
public final class IndicatieCurateleVoorwaardeRegel extends AbstractIndicatieVoorwaardeRegel {

    /**
     * Rubriek voor deze indicatie.
     */
    public static final String RUBRIEK = "(11|51).33.10";

    @Inject
    private ConversietabelFactory conversieTabelFactory;

    /**
     * Maakt nieuwe voorwaarderegel aan.
     */
    public IndicatieCurateleVoorwaardeRegel() {
        super(RUBRIEK);
    }

    @Override
    protected Boolean vertaalIndicatieVanWaarde(final String ruweWaarde) {
        final String zonderAanhalingstekens = ruweWaarde.replaceAll("\"", "");
        return conversieTabelFactory.createIndicatieCurateleConversietabel()
                                    .converteerNaarBrp(new Lo3IndicatieCurateleregister(zonderAanhalingstekens, null))
                                    .getWaarde();
    }
}
