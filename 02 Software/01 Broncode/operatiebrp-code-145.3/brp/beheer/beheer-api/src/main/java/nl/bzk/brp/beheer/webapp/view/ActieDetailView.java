/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.view;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.brp.beheer.webapp.view.blob.BlobViewObject;

/**
 * Detail view voor een actie.
 */
public final class ActieDetailView implements ActieView {

    private final BRPActie actie;

    private final List<BlobViewObject> personen;
    private final List<BlobViewObject> relaties;
    private final List<BlobViewObject> onderzoeken;

    /**
     * Constructor.
     * @param actie actie
     * @param personen gerelateerde personen
     * @param relaties gerelateerde relaties
     * @param onderzoeken gerelateerde onderzoeken
     */
    public ActieDetailView(
        final BRPActie actie,
        final List<BlobViewObject> personen,
        final List<BlobViewObject> relaties,
        final List<BlobViewObject> onderzoeken) {
        this.actie = actie;
        this.personen = personen;
        this.relaties = relaties;
        this.onderzoeken = onderzoeken;
    }

    /**
     * geef actie terug.
     * @return brp actie
     */
    public BRPActie getActie() {
        return actie;
    }

    /**
     * Geef lijst van personen terug.
     * @return lijst van BlobViewObjecten van personen
     */
    public List<BlobViewObject> getPersonen() {
        return personen;
    }

    /**
     * Geef lijst van relaties terug
     * @return lijst van BlobViewObjecten van relaties
     */
    public List<BlobViewObject> getRelaties() {
        return relaties;
    }

    /**
     * Geef lijst van onderzoeken terug
     * @return lijst van onderzoeken van relaties
     */
    public List<BlobViewObject> getOnderzoeken() {
        return onderzoeken;
    }
}
