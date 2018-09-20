/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.BerichtdataLaxXSDAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.AbstractBerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteitGroep;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.logisch.ber.BijhoudingsplanBasis;

/**
 * Het bijhoudingsplan welke opgesteld is naar aanleiding van een bijhoudingsvoorstel.
 *
 * Het bijhoudingsplan wordt niet gepersisteerd in de database. Uit de Bijhoudings POCs kwam geen businesscase om dit te
 * doen.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractBijhoudingsplanBericht extends AbstractBerichtEntiteit implements BrpObject, BerichtEntiteit, MetaIdentificeerbaar,
        BijhoudingsplanBasis
{

    private static final Integer META_ID = 21481;
    private String partijBijhoudingsvoorstelCode;
    private PartijAttribuut partijBijhoudingsvoorstel;
    private BerichtdataLaxXSDAttribuut bericht;
    private BerichtdataLaxXSDAttribuut berichtResultaat;
    private AdministratieveHandelingBericht administratieveHandeling;
    private List<BijhoudingsplanPersoonBericht> bijhoudingsplanPersonen;

    /**
     * Retourneert Partij bijhoudingsvoorstel van Identiteit.
     *
     * @return Partij bijhoudingsvoorstel.
     */
    public String getPartijBijhoudingsvoorstelCode() {
        return partijBijhoudingsvoorstelCode;
    }

    /**
     * {@inheritDoc}
     */
//handmatige wijziging
//    @Override
    public PartijAttribuut getPartijBijhoudingsvoorstel() {
        return partijBijhoudingsvoorstel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BerichtdataLaxXSDAttribuut getBericht() {
        return bericht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BerichtdataLaxXSDAttribuut getBerichtResultaat() {
        return berichtResultaat;
    }

    /**
     * {@inheritDoc}
     */
//handmatige wijziging
//    @Override
    public AdministratieveHandelingBericht getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Retourneert Bijhoudingsplan \ Personen van Bijhoudingsplan.
     *
     * @return Bijhoudingsplan \ Personen van Bijhoudingsplan.
     */
    public List<BijhoudingsplanPersoonBericht> getBijhoudingsplanPersonen() {
        return bijhoudingsplanPersonen;
    }

    /**
     * Zet Partij bijhoudingsvoorstel van Identiteit.
     *
     * @param partijBijhoudingsvoorstelCode Partij bijhoudingsvoorstel.
     */
    public void setPartijBijhoudingsvoorstelCode(final String partijBijhoudingsvoorstelCode) {
        this.partijBijhoudingsvoorstelCode = partijBijhoudingsvoorstelCode;
    }

    /**
     * Zet Partij bijhoudingsvoorstel van Bijhoudingsplan.
     *
     * @param partijBijhoudingsvoorstel Partij bijhoudingsvoorstel.
     */
    public void setPartijBijhoudingsvoorstel(final PartijAttribuut partijBijhoudingsvoorstel) {
        this.partijBijhoudingsvoorstel = partijBijhoudingsvoorstel;
    }

    /**
     * Zet Bericht van Bijhoudingsplan.
     *
     * @param bericht Bericht.
     */
    public void setBericht(final BerichtdataLaxXSDAttribuut bericht) {
        this.bericht = bericht;
    }

    /**
     * Zet Bericht resultaat van Bijhoudingsplan.
     *
     * @param berichtResultaat Bericht resultaat.
     */
    public void setBerichtResultaat(final BerichtdataLaxXSDAttribuut berichtResultaat) {
        this.berichtResultaat = berichtResultaat;
    }

    /**
     * Zet Administratieve handeling van Bijhoudingsplan.
     *
     * @param administratieveHandeling Administratieve handeling.
     */
    public void setAdministratieveHandeling(final AdministratieveHandelingBericht administratieveHandeling) {
        this.administratieveHandeling = administratieveHandeling;
    }

    /**
     * Zet Bijhoudingsplan \ Personen van Bijhoudingsplan.
     *
     * @param bijhoudingsplanPersonen Bijhoudingsplan \ Personen.
     */
    public void setBijhoudingsplanPersonen(final List<BijhoudingsplanPersoonBericht> bijhoudingsplanPersonen) {
        this.bijhoudingsplanPersonen = bijhoudingsplanPersonen;
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
        if (bijhoudingsplanPersonen != null) {
            berichtEntiteiten.addAll(getBijhoudingsplanPersonen());
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
