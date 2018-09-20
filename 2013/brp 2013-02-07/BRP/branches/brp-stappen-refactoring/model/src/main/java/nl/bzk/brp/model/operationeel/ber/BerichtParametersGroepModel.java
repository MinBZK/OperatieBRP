/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingswijze;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.logisch.ber.BerichtParametersGroep;
import nl.bzk.brp.model.operationeel.ber.basis.AbstractBerichtParametersGroepModel;


/**
 * 
 * 
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.4.8.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-12-20 07:32:27.
 * Gegenereerd op: Thu Dec 20 07:46:43 CET 2012.
 */
@Embeddable
public class BerichtParametersGroepModel extends AbstractBerichtParametersGroepModel implements BerichtParametersGroep {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     * 
     */
    protected BerichtParametersGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     * 
     * @param verwerkingswijze verwerkingswijze van Parameters.
     * @param peilmomentMaterieel peilmomentMaterieel van Parameters.
     * @param peilmomentFormeel peilmomentFormeel van Parameters.
     * @param aanschouwer aanschouwer van Parameters.
     */
    public BerichtParametersGroepModel(final Verwerkingswijze verwerkingswijze, final Datum peilmomentMaterieel,
            final DatumTijd peilmomentFormeel, final Burgerservicenummer aanschouwer)
    {
        super(verwerkingswijze, peilmomentMaterieel, peilmomentFormeel, aanschouwer);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     * 
     * @param berichtParametersGroep te kopieren groep.
     */
    public BerichtParametersGroepModel(final BerichtParametersGroep berichtParametersGroep) {
        super(berichtParametersGroep);
    }

}
