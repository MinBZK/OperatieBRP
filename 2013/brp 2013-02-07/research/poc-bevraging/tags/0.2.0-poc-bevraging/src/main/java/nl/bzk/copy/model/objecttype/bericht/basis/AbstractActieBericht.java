/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.bericht.basis;

import nl.bzk.copy.model.attribuuttype.Datum;
import nl.bzk.copy.model.attribuuttype.DatumTijd;
import nl.bzk.copy.model.attribuuttype.Ontleningstoelichting;
import nl.bzk.copy.model.basis.AbstractObjectTypeBericht;
import nl.bzk.copy.model.objecttype.logisch.Verdrag;
import nl.bzk.copy.model.objecttype.logisch.basis.ActieBasis;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.copy.model.objecttype.operationeel.statisch.SoortActie;

/**
 * Implementatie voor objecttype actie.
 */
@SuppressWarnings("serial")
public abstract class AbstractActieBericht extends AbstractObjectTypeBericht implements ActieBasis {

    private Partij partij;
    //    private Verdrag verdrag;
    private DatumTijd tijdstipOntlening;
    private DatumTijd tijdstipRegistratie;
    private SoortActie soort;

    @nl.bzk.copy.model.validatie.constraint.Datum
    private Datum datumAanvangGeldigheid;

    private Datum datumEindeGeldigheid;
    private Ontleningstoelichting ontleningstoelichting;

    @Override
    public Partij getPartij() {
        return partij;
    }

    @Override
    public Verdrag getVerdrag() {
        throw new UnsupportedOperationException("VerdragWeb moet gebouwd worden.");
    }

    @Override
    public DatumTijd getTijdstipOntlening() {
        return tijdstipOntlening;
    }

    @Override
    public DatumTijd getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    @Override
    public SoortActie getSoort() {
        return soort;
    }

    @Override
    public Datum getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    @Override
    public Datum getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    @Override
    public Ontleningstoelichting getOntleningstoelichting() {
        return ontleningstoelichting;
    }


    public void setPartij(final Partij partij) {
        this.partij = partij;
    }


    public void setTijdstipOntlening(final DatumTijd tijdstipOntlening) {
        this.tijdstipOntlening = tijdstipOntlening;
    }


    public void setTijdstipRegistratie(final DatumTijd tijdstipRegistratie) {
        this.tijdstipRegistratie = tijdstipRegistratie;
    }


    public void setSoort(final SoortActie soort) {
        this.soort = soort;
    }


    public void setDatumAanvangGeldigheid(final Datum datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }


    public void setDatumEindeGeldigheid(final Datum datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }


    public void setOntleningstoelichting(final Ontleningstoelichting ontleningstoelichting) {
        this.ontleningstoelichting = ontleningstoelichting;
    }
}
