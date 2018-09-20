/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.basis.AbstractBerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteitGroep;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.ActieBasis;

/**
 * Kleinste eenheid van gegevensbewerking in de BRP.
 *
 * Het bijhouden van de BRP geschiedt door het verwerken van administratieve handelingen. Deze administratieve
 * handelingen vallen uiteen in één of meer 'eenheden' van gegevensbewerkingen.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractActieBericht extends AbstractBerichtEntiteit implements BrpObject, BerichtEntiteit, MetaIdentificeerbaar, ActieBasis {

    private static final Integer META_ID = 3071;
    private SoortActieAttribuut soort;
    private AdministratieveHandelingBericht administratieveHandeling;
    private PartijAttribuut partij;
    private DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid;
    private DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid;
    private DatumTijdAttribuut tijdstipRegistratie;
    private DatumEvtDeelsOnbekendAttribuut datumOntlening;
    private List<ActieBronBericht> bronnen;

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     * @param soort de waarde van het discriminator attribuut
     */
    public AbstractActieBericht(final SoortActieAttribuut soort) {
        this.soort = soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortActieAttribuut getSoort() {
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
    public PartijAttribuut getPartij() {
        return partij;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumTijdAttribuut getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumOntlening() {
        return datumOntlening;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
    public void setPartij(final PartijAttribuut partij) {
        this.partij = partij;
    }

    /**
     * Zet Datum aanvang geldigheid van Actie.
     *
     * @param datumAanvangGeldigheid Datum aanvang geldigheid.
     */
    public void setDatumAanvangGeldigheid(final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    /**
     * Zet Datum einde geldigheid van Actie.
     *
     * @param datumEindeGeldigheid Datum einde geldigheid.
     */
    public void setDatumEindeGeldigheid(final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    /**
     * Zet Tijdstip registratie van Actie.
     *
     * @param tijdstipRegistratie Tijdstip registratie.
     */
    public void setTijdstipRegistratie(final DatumTijdAttribuut tijdstipRegistratie) {
        this.tijdstipRegistratie = tijdstipRegistratie;
    }

    /**
     * Zet Datum ontlening van Actie.
     *
     * @param datumOntlening Datum ontlening.
     */
    public void setDatumOntlening(final DatumEvtDeelsOnbekendAttribuut datumOntlening) {
        this.datumOntlening = datumOntlening;
    }

    /**
     * Zet Actie \ Bronnen van Actie.
     *
     * @param bronnen Actie \ Bronnen.
     */
    public void setBronnen(final List<ActieBronBericht> bronnen) {
        this.bronnen = bronnen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getMetaId() {
        return META_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BerichtEntiteit> getBerichtEntiteiten() {
        final List<BerichtEntiteit> berichtEntiteiten = new ArrayList<>();
        if (bronnen != null) {
            berichtEntiteiten.addAll(getBronnen());
        }
        return berichtEntiteiten;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BerichtEntiteitGroep> getBerichtEntiteitGroepen() {
        return Collections.emptyList();
    }

}
