/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.bericht.basis;

import nl.bzk.brp.model.basis.AbstractObjectTypeBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.DocumentBericht;

/**
 *
 * .
 *
 */
public abstract class AbstractBronBericht extends AbstractObjectTypeBericht {

    private ActieBericht actie;

    private DocumentBericht document;

    /**
     * Retourneert Document van Bron.
     *
     * @return Document.
     */
    public DocumentBericht getDocument() {
        return document;
    }

    /**
     * Zet Document van Bron.
     *
     * @param document Document.
     */
    public void setDocument(final DocumentBericht document) {
        this.document = document;
    }

    public ActieBericht getActie() {
        return actie;
    }

    /**
     * .
     * @param actie .
     */
    public void setActie(final ActieBericht actie) {
        this.actie = actie;
    }

}
