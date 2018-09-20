/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.impl.gen;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.attribuuttype.OntleningsToelichting;
import nl.bzk.brp.model.attribuuttype.TechnischIdGroot;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.objecttype.interfaces.gen.ActieBasis;
import nl.bzk.brp.model.objecttype.interfaces.usr.Verdrag;
import nl.bzk.brp.model.objecttype.statisch.Partij;
import nl.bzk.brp.model.objecttype.statisch.SoortActie;

/**
 * Implementatie voor objecttype actie.
 */
public abstract class AbstractActieMdl extends AbstractDynamischObjectType implements ActieBasis {

    private TechnischIdGroot iD;
    private Partij partij;
    private Verdrag verdrag;
    private DatumTijd tijdstipOntlening;
    private DatumTijd tijdstipRegistratie;
    private SoortActie soort;
    private Datum datumAanvangGeldigheid;
    private Datum datumEindeGeldigheid;
    private OntleningsToelichting ontleningsToelichting;

    public TechnischIdGroot getID() {
        return iD;
    }

    public Partij getPartij() {
        return partij;
    }

    public Verdrag getVerdrag() {
        return verdrag;
    }

    public DatumTijd getTijdstipOntlening() {
        return tijdstipOntlening;
    }

    public DatumTijd getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    public SoortActie getSoort() {
        return soort;
    }

    public Datum getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    public Datum getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    public OntleningsToelichting getOntleningsToelichting() {
        return ontleningsToelichting;
    }
}
