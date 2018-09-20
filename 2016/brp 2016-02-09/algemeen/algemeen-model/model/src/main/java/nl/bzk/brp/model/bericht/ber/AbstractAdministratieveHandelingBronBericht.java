/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber;

import java.util.Collections;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RechtsgrondAttribuut;
import nl.bzk.brp.model.basis.AbstractBerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteitGroep;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.DocumentBericht;
import nl.bzk.brp.model.logisch.ber.AdministratieveHandelingBronBasis;

/**
 * De voor een Administratieve handeling gebruikte bronnen.
 *
 * Om een administratieve handeling te kunnen verantwoorden, kent de BRP een mechanisme waarin Acties via een
 * koppeltabel worden verantwoord door Documenten. Hierbij is het wenselijk dat eventuele nieuwe documenten kunnen
 * worden gescand. Om deze reden is er een vehikel nodig om de (technische) id's van de Documenten terug te leveren, op
 * het niveau van het bericht c.q. de Administratieve handeling, in plaats van het niveau waarin het is vastgelegd,
 * zijnde de Actie. Hierbij zijn de koppelingen tussen Administratieve handeling enerzijds, en Document anderzijds,
 * afleidbaar uit de (wel geadministreerde) koppeltabel tussen Actie en Document.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractAdministratieveHandelingBronBericht extends AbstractBerichtEntiteit implements BrpObject, BerichtEntiteit,
        MetaIdentificeerbaar, AdministratieveHandelingBronBasis
{

    private static final Integer META_ID = 9506;
    private AdministratieveHandelingBericht administratieveHandeling;
    private DocumentBericht document;
    private String rechtsgrondCode;
    private RechtsgrondAttribuut rechtsgrond;
    private OmschrijvingEnumeratiewaardeAttribuut rechtsgrondomschrijving;

    /**
     * Retourneert Administratieve handeling van Administratieve handeling \ Bron.
     *
     * @return Administratieve handeling.
     */
    public AdministratieveHandelingBericht getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Retourneert Document van Administratieve handeling \ Bron.
     *
     * @return Document.
     */
    public DocumentBericht getDocument() {
        return document;
    }

    /**
     * Retourneert Rechtsgrond van Identiteit.
     *
     * @return Rechtsgrond.
     */
    public String getRechtsgrondCode() {
        return rechtsgrondCode;
    }

    /**
     * Retourneert Rechtsgrond van Administratieve handeling \ Bron.
     *
     * @return Rechtsgrond.
     */
    public RechtsgrondAttribuut getRechtsgrond() {
        return rechtsgrond;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OmschrijvingEnumeratiewaardeAttribuut getRechtsgrondomschrijving() {
        return rechtsgrondomschrijving;
    }

    /**
     * Zet Administratieve handeling van Administratieve handeling \ Bron.
     *
     * @param administratieveHandeling Administratieve handeling.
     */
    public void setAdministratieveHandeling(final AdministratieveHandelingBericht administratieveHandeling) {
        this.administratieveHandeling = administratieveHandeling;
    }

    /**
     * Zet Document van Administratieve handeling \ Bron.
     *
     * @param document Document.
     */
    public void setDocument(final DocumentBericht document) {
        this.document = document;
    }

    /**
     * Zet Rechtsgrond van Identiteit.
     *
     * @param rechtsgrondCode Rechtsgrond.
     */
    public void setRechtsgrondCode(final String rechtsgrondCode) {
        this.rechtsgrondCode = rechtsgrondCode;
    }

    /**
     * Zet Rechtsgrond van Administratieve handeling \ Bron.
     *
     * @param rechtsgrond Rechtsgrond.
     */
    public void setRechtsgrond(final RechtsgrondAttribuut rechtsgrond) {
        this.rechtsgrond = rechtsgrond;
    }

    /**
     * Zet Rechtsgrondomschrijving van Administratieve handeling \ Bron.
     *
     * @param rechtsgrondomschrijving Rechtsgrondomschrijving.
     */
    public void setRechtsgrondomschrijving(final OmschrijvingEnumeratiewaardeAttribuut rechtsgrondomschrijving) {
        this.rechtsgrondomschrijving = rechtsgrondomschrijving;
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
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BerichtEntiteitGroep> getBerichtEntiteitGroepen() {
        return Collections.emptyList();
    }

}
