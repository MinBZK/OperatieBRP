/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.hismodelattribuutaccess;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;


/**
 * Administratie implementatie van welke attributen van C/D laag entiteiten opgevraagd zijn.
 * <p/>
 * Let op: deze klasse is ontworpen voor eenmalig gebruik per object. Dat wil zeggen: 1 cycle van attribuut administratie voor 1 request. Dit moet extern
 * afgedwongen worden, bijvoorbeeld door Spring.
 */
public final class HisModelAttribuutAccessAdministratieImpl implements HisModelAttribuutAccessAdministratie {

    private final Map<Regel, Set<AttribuutAccess>> regelAtrribuutAccess;
    private       boolean                          actief;
    private       Regel                            huidigeRegel;

    /**
     * Constructor, begin als niet actief, zonder huidige regel.
     */
    public HisModelAttribuutAccessAdministratieImpl() {
        this.actief = false;
        this.huidigeRegel = null;
        this.regelAtrribuutAccess = new HashMap<Regel, Set<AttribuutAccess>>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activeer() {
        this.actief = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deactiveer() {
        this.actief = false;
        this.huidigeRegel = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActief() {
        return this.actief;
    }

    /**
     * Dwingt af dat deze administratie actief is.
     */
    private void checkActief() {
        if (!this.actief) {
            throw new IllegalStateException("De administratie moet actief zijn.");
        }
    }

    /**
     * Dwingt af dat deze administratie een huidige regel heeft.
     */
    private void checkHuidigeRegel() {
        if (this.huidigeRegel == null) {
            throw new IllegalStateException("Huidige regel mag niet null zijn.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHuidigeRegel(final Regel regel) {
        this.checkActief();
        this.huidigeRegel = regel;
        // Voeg een lege set toe voor de nieuwe huidige regel, zodat deze collectie nooit null zal zijn.
        this.regelAtrribuutAccess.put(this.huidigeRegel, new HashSet<AttribuutAccess>());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attribuutGeraakt(final int groepDbObjectId, final Long voorkomenId, final int attribuutDbObjectId,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid)
    {
        this.checkActief();
        this.checkHuidigeRegel();
        final Set<AttribuutAccess> huidigeRegelSet = this.regelAtrribuutAccess.get(this.huidigeRegel);
        huidigeRegelSet.add(new AttribuutAccess(groepDbObjectId, voorkomenId, attribuutDbObjectId,
            datumAanvangGeldigheid));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Regel, Set<AttribuutAccess>> getRegelAttribuutAccess() {
        return this.regelAtrribuutAccess;
    }

}
