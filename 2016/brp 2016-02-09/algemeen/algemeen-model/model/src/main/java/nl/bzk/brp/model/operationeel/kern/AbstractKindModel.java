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
import nl.bzk.brp.model.logisch.kern.Kind;
import nl.bzk.brp.model.logisch.kern.KindBasis;

/**
 * De OT:Betrokkenheid van een OT:Persoon in de _rol_ van "_Kind_" in een OT:"Familierechtelijke Betrekking".
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractKindModel extends BetrokkenheidModel implements KindBasis {

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractKindModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param relatie relatie van Kind.
     * @param persoon persoon van Kind.
     */
    public AbstractKindModel(final RelatieModel relatie, final PersoonModel persoon) {
        super(relatie, new SoortBetrokkenheidAttribuut(SoortBetrokkenheid.KIND), persoon);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param kind Te kopieren object type.
     * @param relatie Bijbehorende Relatie.
     * @param persoon Bijbehorende Persoon.
     */
    public AbstractKindModel(final Kind kind, final RelatieModel relatie, final PersoonModel persoon) {
        super(kind, relatie, persoon);

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
