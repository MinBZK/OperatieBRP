/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import nl.bzk.brp.model.hisvolledig.kern.DocumentHisVolledig;
import nl.bzk.brp.model.logisch.kern.DocumentStandaardGroep;
import nl.bzk.brp.model.logisch.kern.HisDocumentStandaardGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_Doc")
public class HisDocumentModel extends AbstractHisDocumentModel implements HisDocumentStandaardGroep {

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisDocumentModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param documentHisVolledig instantie van A-laag klasse.
     * @param groep               groep
     * @param actieInhoud         actie inhoud
     */
    public HisDocumentModel(final DocumentHisVolledig documentHisVolledig, final DocumentStandaardGroep groep,
        final ActieModel actieInhoud)
    {
        super(documentHisVolledig, groep, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public HisDocumentModel(final AbstractHisDocumentModel kopie) {
        super(kopie);
    }

    /**
     * @return de objectsleutel
     */
    @Transient
    public final String getObjectSleutel() {
        return getDocument().getID().toString();
    }

}
