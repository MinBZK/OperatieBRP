/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import nl.bzk.brp.model.logisch.kern.Instemmer;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractInstemmerModel;


/**
 * De betrokkenheid van de rol instemmer in een erkenning ongeboren vrucht of in een naamskeuze ongeboren vrucht.
 *
 * Bij de relatiesoorten Erkenning ongeboren vrucht en Naamskeuze ongeboren vrucht is er enerzijds de betrokkenheid in
 * de rol van Erkenner (in geval van Erkenning) of de Naamgever (in geval van Naamskeuze), anderzijds is er de
 * betrokkenheid van de andere (toekomstig) ouder, die hetzij met de erkenning, hetzij met de naamskeuze instemt.
 *
 *
 *
 */
@Entity
@DiscriminatorValue(value = "5")
public class InstemmerModel extends AbstractInstemmerModel implements Instemmer {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected InstemmerModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param relatie relatie van Instemmer.
     * @param persoon persoon van Instemmer.
     */
    public InstemmerModel(final RelatieModel relatie, final PersoonModel persoon) {
        super(relatie, persoon);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param instemmer Te kopieren object type.
     * @param relatie Bijbehorende Relatie.
     * @param persoon Bijbehorende Persoon.
     */
    public InstemmerModel(final Instemmer instemmer, final RelatieModel relatie, final PersoonModel persoon) {
        super(instemmer, relatie, persoon);
    }

}
