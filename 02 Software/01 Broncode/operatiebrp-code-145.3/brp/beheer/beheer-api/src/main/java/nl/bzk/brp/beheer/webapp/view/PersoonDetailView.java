/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.view;

import java.util.List;
import nl.bzk.brp.beheer.webapp.view.blob.BlobViewObject;

/**
 * Detail view voor een persoon.
 */
public final class PersoonDetailView implements PersoonView {

    private final BlobViewObject persoon;
    private final List<BlobViewObject> verantwoording;
    private final List<BlobViewObject> afnemerindicaties;

    /**
     * Constructor.
     *
     * @param persoon
     *            persoon
     * @param verantwoording
     *            verantwoording
     * @param afnemerindicaties
     *            afnemerindicaties
     */
    public PersoonDetailView(final BlobViewObject persoon, final List<BlobViewObject> verantwoording, final List<BlobViewObject> afnemerindicaties) {
        this.persoon = persoon;
        this.verantwoording = verantwoording;
        this.afnemerindicaties = afnemerindicaties;
    }

    /**
     * Geeft persoon.
     * @return BlobViewObject van persoon
     */
    public BlobViewObject getPersoon() {
        return persoon;
    }

    /**
     * Geef lijst van verantwoordingen terug
     * @return lijst van BlobViewObjecten van varantwoordingen
     */
    public List<BlobViewObject> getVerantwoording() {
        return verantwoording;
    }

    /**
     * Geef lijst van afnementsindicaties terug
     * @return lijst van afnemerindicaties van relaties
     */
    public List<BlobViewObject> getAfnemerindicaties() {
        return afnemerindicaties;
    }

}
