/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import java.util.List;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.basis.AbstractObjectTypeBericht;
import nl.bzk.brp.model.bericht.kern.ActieBronBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.logisch.kern.basis.ActieBasis;


/**
 * Eenheid van gegevensbewerking in de BRP.
 *
 * Het bijhouden van de BRP geschiedt door het uitwerken van gegevensbewerkingen op de inhoud van de BRP, c.q. het doen
 * van bijhoudingsacties. De kleinste eenheid van gegevensbewerking is de "BRP actie".
 *
 *
 *
 */
public abstract class AbstractActieBericht extends AbstractObjectTypeBericht implements ActieBasis {

    private SoortActie                      soort;
    private AdministratieveHandelingBericht administratieveHandeling;
    private Partij                          partij;
    private Datum                           datumAanvangGeldigheid;
    private Datum                           datumEindeGeldigheid;
    private DatumTijd                       tijdstipRegistratie;
    private List<ActieBronBericht>          bronnen;

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     * @param soort de waarde van het discriminator attribuut
     */
    public AbstractActieBericht(final SoortActie soort) {
        this.soort = soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortActie getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdministratieveHandelingBericht getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij getPartij() {
        return partij;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumTijd getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    /**
     * Retourneert Actie/Bronnen van Actie.
     *
     * @return Actie/Bronnen van Actie.
     */
    public List<ActieBronBericht> getBronnen() {
        return bronnen;
    }

    /**
     * Zet Administratieve handeling van Actie.
     *
     * @param administratieveHandeling Administratieve handeling.
     */
    public void setAdministratieveHandeling(final AdministratieveHandelingBericht administratieveHandeling) {
        this.administratieveHandeling = administratieveHandeling;
    }

    /**
     * Zet Partij van Actie.
     *
     * @param partij Partij.
     */
    public void setPartij(final Partij partij) {
        this.partij = partij;
    }

    /**
     * Zet Datum aanvang geldigheid van Actie.
     *
     * @param datumAanvangGeldigheid Datum aanvang geldigheid.
     */
    public void setDatumAanvangGeldigheid(final Datum datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    /**
     * Zet Datum einde geldigheid van Actie.
     *
     * @param datumEindeGeldigheid Datum einde geldigheid.
     */
    public void setDatumEindeGeldigheid(final Datum datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    /**
     * Zet Tijdstip registratie van Actie.
     *
     * @param tijdstipRegistratie Tijdstip registratie.
     */
    public void setTijdstipRegistratie(final DatumTijd tijdstipRegistratie) {
        this.tijdstipRegistratie = tijdstipRegistratie;
    }

    /**
     * Zet Actie/Bronnen van Actie.
     *
     * @param bronnen Actie/Bronnen.
     */
    public void setBronnen(final List<ActieBronBericht> bronnen) {
        this.bronnen = bronnen;
    }

}
