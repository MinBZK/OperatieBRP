/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;

import org.hibernate.EmptyInterceptor;


/**
 * Hibernate specifieke interceptor die middels de 'persistence.xml' wordt geconfigureerd als interceptor voor alle
 * Hibernate events. Deze implementatie
 * checkt op het 'preFlush' event of een entiteit A-Laag afleidbare attributen heeft en leidt deze eventueel af als dat
 * zo is.
 */
public final class ALaagAfleidingInterceptor extends EmptyInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Methode die door Hibernate wordt aangeroepen voordat er een flush wordt uitgevoerd. In deze implementatie worden
     * alle entities die geflusht worden
     * gecontroleerd en als deze de interface {@link ALaagAfleidbaar} implementeren, dus als ze A-Laag attributen
     * bevatten die in die klasse kunnen worden
     * afgeleid, dan worden deze A-Laag attributen afgeleid.
     *
     * @param entities de entities die worden geflusht.
     */
    @Override
    public void preFlush(final Iterator entities) {
        LOGGER.debug("Hibernate PreFlush event interception t.b.v. A-Laag afleiding.");

        final Set<ALaagAfleidbaar> alaagAfleidingBenodigd = new HashSet<ALaagAfleidbaar>();
        if (entities != null) {
            while (entities.hasNext()) {
                final Object entity = entities.next();
                if (entity instanceof ALaagAfleidbaar) {
                    alaagAfleidingBenodigd.add((ALaagAfleidbaar) entity);
                }
            }
        }

        LOGGER.debug("A-Laag afleiding benodigd voor {} entities.", alaagAfleidingBenodigd.size());
        for (ALaagAfleidbaar afleidbareEntity : alaagAfleidingBenodigd) {
            afleidbareEntity.leidALaagAf();
        }
    }

}
