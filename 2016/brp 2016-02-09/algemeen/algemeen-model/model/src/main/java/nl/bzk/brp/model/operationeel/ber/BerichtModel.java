/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.RichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBerichtAttribuut;
import nl.bzk.brp.model.logisch.ber.Bericht;


/**
 * (Toekomstig) Bericht zoals verzonden door of ontvangen door de centrale voorzieningen van de BRP.
 * <p/>
 * Berichten worden door de BRP gearchiveerd. Dit betreft enerzijds ontvangen Berichten, anderzijds Berichten die verzonden gaan worden.
 * <p/>
 * 1. Soort bericht (weer) verwijderd uit model als eigenschap van Bericht: reden is dat het op het moment van archiveren nog niet bekend zal zijn. RvdP 8
 * november 2011.
 */
@Entity
@Table(schema = "Ber", name = "Ber")
public class BerichtModel extends AbstractBerichtModel implements Bericht {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     * <p/>
     * Handmatige wijziging: public constructor tbv berichtarchivering in webservice business.
     */
    public BerichtModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort    soort van Bericht.
     * @param richting richting van Bericht.
     */
    public BerichtModel(final SoortBerichtAttribuut soort, final RichtingAttribuut richting) {
        super(soort, richting);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param bericht Te kopieren object type.
     */
    public BerichtModel(final Bericht bericht, final Long administratieveHandelingId, final BerichtModel antwoordOp) {
        super(bericht);
    }
}
