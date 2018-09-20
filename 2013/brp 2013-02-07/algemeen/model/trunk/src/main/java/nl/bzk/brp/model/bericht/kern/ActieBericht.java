/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.List;

import javax.validation.Valid;

import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.bericht.kern.basis.AbstractActieBericht;
import nl.bzk.brp.model.logisch.kern.Actie;


/**
 * Eenheid van gegevensbewerking in de BRP.
 *
 * Het bijhouden van de BRP geschiedt door het uitwerken van gegevensbewerkingen op de inhoud van de BRP, c.q. het doen
 * van bijhoudingsacties. De kleinste eenheid van gegevensbewerking is de "BRP actie".
 *
 *
 *
 */
public abstract class ActieBericht extends AbstractActieBericht implements Actie {

    @Valid
    private List<RootObject> rootObjecten;

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     * @param soort de waarde van het discriminator attribuut
     */
    public ActieBericht(final SoortActie soort) {
        super(soort);
    }

    @Override
    public List<RootObject> getRootObjecten() {
        return rootObjecten;
    }

    /**
     * Zet de persoon of relatie rootobject in deze actie naar het eerste object uit de opgegeven lijst.
     *
     * @param rootObjecten de lijst met rootobjecten.
     */
    public void setRootObjecten(final List<RootObject> rootObjecten) {
        this.rootObjecten = rootObjecten;
    }

    @Override
    @nl.bzk.brp.model.validatie.constraint.Datum
    public Datum getDatumAanvangGeldigheid() {
        return super.getDatumAanvangGeldigheid();
    }
}
