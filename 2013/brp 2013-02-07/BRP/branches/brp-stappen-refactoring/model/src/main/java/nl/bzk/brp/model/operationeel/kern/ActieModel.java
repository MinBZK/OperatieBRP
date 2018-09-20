/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractActieModel;


/**
 * Eenheid van gegevensbewerking in de BRP.
 *
 * Het bijhouden van de BRP geschiedt door het uitwerken van gegevensbewerkingen op de inhoud van de BRP, c.q. het doen
 * van bijhoudingsacties. De kleinste eenheid van gegevensbewerking is de "BRP actie".
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.1.8.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-11-27 12:02:51.
 * Gegenereerd op: Tue Nov 27 14:55:35 CET 2012.
 */
@Entity
@Table(schema = "Kern", name = "Actie")
public class ActieModel extends AbstractActieModel implements Actie {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected ActieModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert.
     *
     * @param soort soort van Actie.
     * @param administratieveHandeling administratieveHandeling van Actie.
     * @param partij partij van Actie.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Actie.
     * @param datumEindeGeldigheid datumEindeGeldigheid van Actie.
     * @param tijdstipRegistratie tijdstipRegistratie van Actie.
     */
    public ActieModel(final SoortActie soort, final AdministratieveHandelingModel administratieveHandeling,
            final Partij partij, final Datum datumAanvangGeldigheid, final Datum datumEindeGeldigheid,
            final DatumTijd tijdstipRegistratie)
    {
        super(soort, administratieveHandeling, partij, datumAanvangGeldigheid, datumEindeGeldigheid,
                tijdstipRegistratie);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param actie Te kopieren object type.
     * @param administratieveHandeling Bijbehorende Administratieve handeling.
     */
    public ActieModel(final Actie actie, final AdministratieveHandelingModel administratieveHandeling) {
        super(actie, administratieveHandeling);
    }

    @Override
    @Transient
    public List<RootObject> getRootObjecten() {
        throw new UnsupportedOperationException("Java operationeel Model ondersteunt geen root objecten.");
    }
}
