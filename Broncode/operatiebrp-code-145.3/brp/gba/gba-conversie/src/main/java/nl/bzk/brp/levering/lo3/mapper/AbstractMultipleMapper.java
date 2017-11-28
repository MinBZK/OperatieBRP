/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.levering.lo3.util.MetaModelUtil;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;

/**
 * Basis mapper voor BRP naar Conversie model (voor meerdere stapels).
 * @param <G> Conversie model groep inhoud type
 */
public abstract class AbstractMultipleMapper<G extends BrpGroepInhoud> {
    private final ObjectElement objectElement;

    /**
     * Constructor.
     * @param objectElement object element waarvan er meerdere gemapped worden.
     */
    protected AbstractMultipleMapper(final ObjectElement objectElement) {
        this.objectElement = objectElement;
    }

    /**
     * Map.
     * @param object het parent object waarin de te mappen objecten zich bevinden
     * @param onderzoekMapper onderzoek mapper
     * @return lijst van stapels
     */
    public final List<BrpStapel<G>> map(final MetaObject object, final OnderzoekMapper onderzoekMapper) {
        final List<BrpStapel<G>> result = new ArrayList<>();

        for (final MetaObject single : MetaModelUtil.getObjecten(object, objectElement)) {
            final BrpStapel<G> stapel = getSingleMapper().map(single, onderzoekMapper);
            if (stapel != null) {
                result.add(stapel);
            }
        }

        return result;
    }

    /**
     * Geef de mapper die voor een enkele stapel gebruikt wordt.
     * @return mapper
     */
    protected abstract AbstractMapper<G> getSingleMapper();

}
