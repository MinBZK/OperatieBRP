/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import nl.bzk.brp.model.logisch.kern.GeregistreerdPartnerschap;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractGeregistreerdPartnerschapModel;


/**
 * Het (aangaan van het en be�indigen van het) geregistreerd partnerschap zoals beschreven in Titel 5A van het
 * Burgerlijk Wetboek Boek 1.
 * <p/>
 * Zie voor verdere toelichting de definitie en toelichting bij Huwelijk/Geregistreerd partnerschap, en bij Relatie.
 * <p/>
 * <p/>
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.2.3.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-12-04 09:33:11.
 * Gegenereerd op: Tue Dec 04 09:50:47 CET 2012.
 */
@Entity
@DiscriminatorValue(value = "2")
public class GeregistreerdPartnerschapModel extends AbstractGeregistreerdPartnerschapModel implements
    GeregistreerdPartnerschap
{

    /** Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft. */
    public GeregistreerdPartnerschapModel() {
        super();
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param geregistreerdPartnerschap Te kopieren object type.
     */
    public GeregistreerdPartnerschapModel(final GeregistreerdPartnerschap geregistreerdPartnerschap) {
        super(geregistreerdPartnerschap);
    }
}
