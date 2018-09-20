/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

import nl.bzk.migratiebrp.synchronisatie.dal.repository.util.PersistenceUtil;

/**
 * Bij het laden of wijzigen van een entities wordt de transient bidirectionele relatie tussen entiteiten en gegeven in
 * onderzoek bijgewerkt.
 */
public final class GegevenInOnderzoekListener {

    /**
     * Als dit een entiteit anders dan gegeven in onderzoek betreft dan moet de link naar gegegeven in onderzoek worden
     * hersteld.
     *
     * @param entity
     *            de opgeslagen entiteit
     */
    @PostPersist
    @PostUpdate
    public void postSave(final Object entity) {
        if ((entity instanceof DeltaEntiteit) && !(entity instanceof GegevenInOnderzoek)) {
            final DeltaEntiteit deltaEntiteit = (DeltaEntiteit) entity;
            for (final GegevenInOnderzoek gegevenInOnderzoek : deltaEntiteit.getGegevenInOnderzoekPerElementMap().values()) {
                if (gegevenInOnderzoek.getObjectOfVoorkomen() != entity) {
                    gegevenInOnderzoek.setObjectOfVoorkomen(deltaEntiteit);
                }
            }
        }
    }

    /**
     * Als dit een gegeven in onderzoek betreft dan moet de associatie van de entiteit naar dit gegeven in onderzoek
     * worden aangemaakt.
     *
     * @param entity
     *            de geladen entiteit
     */
    @PostLoad
    public void postLoad(final Object entity) {
        if (entity instanceof GegevenInOnderzoek) {
            final GegevenInOnderzoek gegevenInOnderzoek = (GegevenInOnderzoek) entity;
            if (gegevenInOnderzoek.getObjectOfVoorkomen() != null) {
                PersistenceUtil.getPojoFromObject(gegevenInOnderzoek.getObjectOfVoorkomen()).setGegevenInOnderzoek(gegevenInOnderzoek);
            }
        }
    }
}
