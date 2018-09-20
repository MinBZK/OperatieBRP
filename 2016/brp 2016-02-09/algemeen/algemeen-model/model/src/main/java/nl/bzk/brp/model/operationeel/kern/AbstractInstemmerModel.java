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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheidAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.logisch.kern.Instemmer;
import nl.bzk.brp.model.logisch.kern.InstemmerBasis;

/**
 * De OT:Betrokkenheid van een OT:Persoon in de _rol_ van "_Instemmer_" in een OT:"Erkenning ongeboren vrucht" of in een
 * OT:"Naamskeuze ongeboren vrucht".
 *
 * Zowel bij een erkenning ongeboren vrucht als bij een naamskeuze ongeboren vrucht is er naast de betrokkenheid van een
 * persoon als erkenner of naamgever ook een (toekomstige) ouder die hiermee instemt: in dat geval is er sprake van een
 * betrokkenheid in de rol van instemmer.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractInstemmerModel extends BetrokkenheidModel implements InstemmerBasis {

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractInstemmerModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param relatie relatie van Instemmer.
     * @param persoon persoon van Instemmer.
     */
    public AbstractInstemmerModel(final RelatieModel relatie, final PersoonModel persoon) {
        super(relatie, new SoortBetrokkenheidAttribuut(SoortBetrokkenheid.INSTEMMER), persoon);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param instemmer Te kopieren object type.
     * @param relatie Bijbehorende Relatie.
     * @param persoon Bijbehorende Persoon.
     */
    public AbstractInstemmerModel(final Instemmer instemmer, final RelatieModel relatie, final PersoonModel persoon) {
        super(instemmer, relatie, persoon);

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
