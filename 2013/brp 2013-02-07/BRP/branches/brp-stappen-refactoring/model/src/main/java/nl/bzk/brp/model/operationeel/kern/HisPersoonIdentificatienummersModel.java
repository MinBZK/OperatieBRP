/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.operationeel.kern.basis.AbstractHisPersoonIdentificatienummersModel;


/**
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.3.10.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-12-13 11:19:56.
 * Gegenereerd op: Thu Dec 13 11:20:53 CET 2012.
 */
@Entity
@Table(schema = "Kern", name = "His_PersIDs")
public class HisPersoonIdentificatienummersModel extends AbstractHisPersoonIdentificatienummersModel {

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected HisPersoonIdentificatienummersModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param persoonModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public HisPersoonIdentificatienummersModel(final PersoonModel persoonModel,
            final PersoonIdentificatienummersGroepModel groep)
    {
        super(persoonModel, groep);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public HisPersoonIdentificatienummersModel(final AbstractHisPersoonIdentificatienummersModel kopie) {
        super(kopie);
    }

}
