/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

// import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisOnderzoekStandaardGroep;
import nl.bzk.brp.model.logisch.kern.OnderzoekStandaardGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_Onderzoek")
public class HisOnderzoekModel extends AbstractHisOnderzoekModel implements HisOnderzoekStandaardGroep, Serializable {

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisOnderzoekModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param onderzoekHisVolledig instantie van A-laag klasse.
     * @param groep                groep
     * @param actieInhoud          the actie inhoud
     */
    public HisOnderzoekModel(final OnderzoekHisVolledig onderzoekHisVolledig, final OnderzoekStandaardGroep groep,
        final ActieModel actieInhoud)
    {
        super(onderzoekHisVolledig, groep, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public HisOnderzoekModel(final AbstractHisOnderzoekModel kopie) {
        super(kopie);
    }

    /**
     * @return de objectsleutel
     */
    @Transient
    public final String getObjectSleutel() {
        return getOnderzoek().getID().toString();
    }

}
