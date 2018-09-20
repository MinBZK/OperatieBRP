/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.validatie.constraint.BRAL0102;
import nl.bzk.brp.model.validatie.constraint.BRBY0032;


/**
 * Kleinste eenheid van gegevensbewerking in de BRP.
 * <p/>
 * Het bijhouden van de BRP geschiedt door het verwerken van administratieve handelingen. Deze administratieve handelingen vallen uiteen in ��n of meer
 * 'eenheden' van gegevensbewerkingen.
 */
@BRBY0032
public abstract class ActieBericht extends AbstractActieBericht implements Actie {

    @Valid
    // Ondanks dat er altijd maar één rootobject is, is dit een collectie type.
    // Dat is namelijk nodig voor JiBX, zodat die er dynamisch de juiste rootobjecten in kan stoppen.
    private List<BerichtRootObject> rootObjecten;

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     * @param soort de waarde van het discriminator attribuut
     */
    public ActieBericht(final SoortActieAttribuut soort) {
        super(soort);
        this.rootObjecten = new ArrayList<BerichtRootObject>();
    }

    @Override
    public BerichtRootObject getRootObject() {
        BerichtRootObject rootObject = null;
        if (!rootObjecten.isEmpty()) {
            // Er is altijd precies 1 rootobject.
            rootObject = this.rootObjecten.get(0);
        }
        return rootObject;
    }

    /**
     * Zet het persoon of relatie rootobject in deze actie.
     *
     * @param rootObject het rootobject.
     */
    public void setRootObject(final BerichtRootObject rootObject) {
        this.rootObjecten.clear();
        this.rootObjecten.add(rootObject);
    }

    @Override
    @BRAL0102(dbObject = DatabaseObjectKern.ACTIE)
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return super.getDatumAanvangGeldigheid();
    }

}
