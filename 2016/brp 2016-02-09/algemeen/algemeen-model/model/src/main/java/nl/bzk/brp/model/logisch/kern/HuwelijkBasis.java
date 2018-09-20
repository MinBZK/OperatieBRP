/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * Het (aangaan van het en beëindigen van het) huwelijk zoals beschreven in Titel 5 van het Burgerlijk Wetboek Boek 1.
 *
 * Zie voor verdere toelichting de definitie en toelichting bij Huwelijk/Geregistreerd partnerschap en bij Relatie.
 *
 * Het bestaan van het Huwelijk subtype komt voort uit het bericht: we willen "Huwelijk" als aparte tag opnemen in
 * bericht (dit b.v. in tegenstelling tot een "niet-ingeschrevene")
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface HuwelijkBasis extends HuwelijkGeregistreerdPartnerschap, BrpObject {

}
