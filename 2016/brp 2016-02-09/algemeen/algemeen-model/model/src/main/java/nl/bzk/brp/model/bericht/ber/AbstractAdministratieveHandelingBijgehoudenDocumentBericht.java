/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.DocumentBericht;
import nl.bzk.brp.model.logisch.ber.AdministratieveHandelingBijgehoudenDocumentBasis;

/**
 * De bijhouding van een document door middel van een administratieve handeling.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractAdministratieveHandelingBijgehoudenDocumentBericht implements BrpObject, AdministratieveHandelingBijgehoudenDocumentBasis {

    private AdministratieveHandelingBericht administratieveHandeling;
    private DocumentBericht document;

    /**
     * Retourneert Administratieve handeling van Administratieve handeling \ Bijgehouden document.
     *
     * @return Administratieve handeling.
     */
    public AdministratieveHandelingBericht getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Retourneert Document van Administratieve handeling \ Bijgehouden document.
     *
     * @return Document.
     */
    public DocumentBericht getDocument() {
        return document;
    }

    /**
     * Zet Administratieve handeling van Administratieve handeling \ Bijgehouden document.
     *
     * @param administratieveHandeling Administratieve handeling.
     */
    public void setAdministratieveHandeling(final AdministratieveHandelingBericht administratieveHandeling) {
        this.administratieveHandeling = administratieveHandeling;
    }

    /**
     * Zet Document van Administratieve handeling \ Bijgehouden document.
     *
     * @param document Document.
     */
    public void setDocument(final DocumentBericht document) {
        this.document = document;
    }

}
