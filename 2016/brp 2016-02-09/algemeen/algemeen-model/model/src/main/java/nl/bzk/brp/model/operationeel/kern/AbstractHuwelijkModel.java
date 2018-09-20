/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatieAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.logisch.kern.Huwelijk;
import nl.bzk.brp.model.logisch.kern.HuwelijkBasis;

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
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHuwelijkModel extends HuwelijkGeregistreerdPartnerschapModel implements HuwelijkBasis {

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     */
    public AbstractHuwelijkModel() {
        super(new SoortRelatieAttribuut(SoortRelatie.HUWELIJK));
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param huwelijk Te kopieren object type.
     */
    public AbstractHuwelijkModel(final Huwelijk huwelijk) {
        super(huwelijk);

    }

    /**
     * Geeft alle groepen van de object met uitzondering van groepen die null zijn.
     *
     * @return Lijst met groepen.
     */
    public List<Groep> getGroepen() {
        final List<Groep> groepen = new ArrayList<>();
        return groepen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van het object.
     */
    public List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        return attributen;
    }

}
