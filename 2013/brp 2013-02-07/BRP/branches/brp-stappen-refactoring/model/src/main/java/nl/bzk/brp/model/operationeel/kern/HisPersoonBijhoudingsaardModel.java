/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.operationeel.kern.basis.AbstractHisPersoonBijhoudingsaardModel;


/**
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.5.6.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-11 09:16:24.
 * Gegenereerd op: Fri Jan 11 09:30:23 CET 2013.
 */
@Entity
@Table(schema = "Kern", name = "His_PersBijhaard")
public class HisPersoonBijhoudingsaardModel extends AbstractHisPersoonBijhoudingsaardModel {

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected HisPersoonBijhoudingsaardModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param persoonModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public HisPersoonBijhoudingsaardModel(final PersoonModel persoonModel, final PersoonBijhoudingsaardGroepModel groep)
    {
        super(persoonModel, groep);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public HisPersoonBijhoudingsaardModel(final AbstractHisPersoonBijhoudingsaardModel kopie) {
        super(kopie);
    }

}
