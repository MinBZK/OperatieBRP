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
import nl.bzk.brp.model.logisch.kern.FamilierechtelijkeBetrekking;
import nl.bzk.brp.model.logisch.kern.FamilierechtelijkeBetrekkingBasis;

/**
 * De familierechtelijke betrekking tussen het Kind enerzijds en zijn/haar ouders anderzijds.
 *
 * De familierechtelijke betrekking "is" van het Kind. Adoptie, erkenning, dan wel het terugdraaien van een adoptie of
 * erkenning heeft géén invloed op de familierechtelijke betrekking zelf: het blijft één en dezelfde Relatie.
 *
 * De Standaard groep van de Familierechtelijke Betrekking kan in principe maar 1 keer voor komen. Er zijn immers geen
 * attributen. Het historiepatroon van deze groep is dan ook 'Formele historie bestaansperiode'. Dit is in BMR niet vast
 * te leggen aangzien de groep vanuit de Relatie overerfd wordt. In het berichmodel is dit wel zo gerealiseerd middels
 * een constraint.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractFamilierechtelijkeBetrekkingModel extends RelatieModel implements FamilierechtelijkeBetrekkingBasis {

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     */
    public AbstractFamilierechtelijkeBetrekkingModel() {
        super(new SoortRelatieAttribuut(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING));
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param familierechtelijkeBetrekking Te kopieren object type.
     */
    public AbstractFamilierechtelijkeBetrekkingModel(final FamilierechtelijkeBetrekking familierechtelijkeBetrekking) {
        super(familierechtelijkeBetrekking);

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
