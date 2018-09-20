/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;

import nl.bzk.brp.model.logisch.kern.Erkenner;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractErkennerModel;


/**
 * De betrokkenheid in de rol van erkenner in een erkenning ongeboren vrucht.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.2.7.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-12-06 11:53:47.
 * Gegenereerd op: Thu Dec 06 11:54:36 CET 2012.
 */
@Entity
public class ErkennerModel extends AbstractErkennerModel implements Erkenner {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected ErkennerModel() {
        super();
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param erkenner Te kopieren object type.
     * @param relatie Bijbehorende Relatie.
     * @param persoon Bijbehorende Persoon.
     */
    public ErkennerModel(final Erkenner erkenner, final RelatieModel relatie, final PersoonModel persoon) {
        super(erkenner, relatie, persoon);
    }

}
