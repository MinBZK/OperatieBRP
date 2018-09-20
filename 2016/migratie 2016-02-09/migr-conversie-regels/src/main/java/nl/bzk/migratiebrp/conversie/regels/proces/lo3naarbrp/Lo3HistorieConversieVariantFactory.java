/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.util.Map;
import javax.annotation.Resource;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import org.springframework.stereotype.Component;

/**
 * Historie variant factory.
 */
@Component
public class Lo3HistorieConversieVariantFactory {

    @Inject
    @Resource(name = "historieVariantGerelateerdeMap")
    private Map<Class<? extends BrpGroepInhoud>, Lo3HistorieConversieVariant> gerelateerdeMap;

    @Inject
    @Resource(name = "historieVariantMap")
    private Map<Class<? extends BrpGroepInhoud>, Lo3HistorieConversieVariant> variantMap;

    /**
     * Factory method om de juiste conversie variant voor een bepaald inhoud type te krijgen.
     *
     * @param inhoudClass
     *            inhoud type
     * @param isGerelateerde
     *            is deze zoek actie voor een gerelateerde ipv de ik-persoon
     * @return conversie variant
     */
    public final Lo3HistorieConversieVariant getVariant(final Class<? extends BrpGroepInhoud> inhoudClass, final boolean isGerelateerde) {
        if (isGerelateerde && gerelateerdeMap.containsKey(inhoudClass)) {
            return gerelateerdeMap.get(inhoudClass);
        }

        final Lo3HistorieConversieVariant variant = variantMap.get(inhoudClass);

        if (variant == null) {
            throw new IllegalArgumentException("Geen historie conversie variant voor '" + inhoudClass.getName() + "'.");
        }

        return variant;
    }
}
