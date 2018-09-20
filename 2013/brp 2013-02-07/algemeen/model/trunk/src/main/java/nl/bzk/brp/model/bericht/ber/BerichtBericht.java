/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber;

import java.util.Collection;

import javax.validation.Valid;

import nl.bzk.brp.model.administratie.CommunicatieIdMap;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.bericht.ber.basis.AbstractBerichtBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.logisch.ber.Bericht;
import org.jibx.runtime.IUnmarshallingContext;


/**
 * (Toekomstig) Bericht zoals verzonden door of ontvangen door de centrale voorzieningen van de BRP.
 *
 * Berichten worden door de BRP gearchiveerd. Dit betreft enerzijds ontvangen Berichten, anderzijds Berichten die
 * verzonden gaan worden.
 *
 * 1. Soort bericht (weer) verwijderd uit model als eigenschap van Bericht: reden is dat het op het moment van
 * archiveren nog niet bekend zal zijn. RvdP 8 november 2011.
 *
 *
 *
 */
public abstract class BerichtBericht extends AbstractBerichtBericht implements Bericht {
    private CommunicatieIdMap identificeerbaarObjectIndex;

    /**
     * Maakt een bericht aan van de meegegeven soort.
     *
     * @param soort de soort.
     */
    protected BerichtBericht(final SoortBericht soort) {
        setSoort(soort);
    }

    /**
     * BSN's betrokken in het Verzoek t.b.v. applicatief READ locking
     *
     * @return Collectie van BSN's waarvoor het verzoek een read lock nodig heeft.
     */
    public abstract Collection<String> getReadBsnLocks();

    /**
     * BSN's betrokken in het Verzoek t.b.v. applicatief WRITE locking
     *
     * @return Collectie van BSN's waarvoor het verzoek een write lock nodig heeft.
     */
    public abstract Collection<String> getWriteBsnLocks();

    @Override
    @Valid
    public AdministratieveHandelingBericht getAdministratieveHandeling() {
        return super.getAdministratieveHandeling();
    }

    public CommunicatieIdMap getIdentificeerbaarObjectIndex() {
        return identificeerbaarObjectIndex;
    }

    /**
     * Hook voor Jibx om dit object te registreren als IdentificeerbaarObject.
     * @param ctx de jibx context
     */
    public void jibxPostSet(final IUnmarshallingContext ctx) {
        this.identificeerbaarObjectIndex = (CommunicatieIdMap) ctx.getUserContext();
    }
}
