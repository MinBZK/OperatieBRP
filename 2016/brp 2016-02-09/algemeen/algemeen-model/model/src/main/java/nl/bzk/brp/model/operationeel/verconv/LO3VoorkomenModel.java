/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.verconv;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3CategorieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.verconv.LO3Voorkomen;


/**
 * Een voorkomen (of het ontbreken daarvan) van een
 * <p/>
 * Een LO3 bericht bevat ��n of meer categorie�n, sommige daarvan repeterend (te zien aan LO3 stapelvolgnummer); elke (repetitie van een) categori� bevat
 * ��n of meer voorkomens. In gevallen waarbij het LO3 bericht een voorkomen h�d moeten hebben, maar dit niet heeft, wordt dit ontbrekende voorkomen hier
 * alsnog vastgelegd, zodat een LO3 melding hierover kan worden vastgelegd.
 */
@Entity
@Table(schema = "VerConv", name = "LO3Voorkomen")
public class LO3VoorkomenModel extends AbstractLO3VoorkomenModel implements LO3Voorkomen, ModelIdentificeerbaar<Long> {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected LO3VoorkomenModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param lO3Bericht             lO3Bericht van LO3 Voorkomen.
     * @param lO3Categorie           lO3Categorie van LO3 Voorkomen.
     * @param lO3Stapelvolgnummer    lO3Stapelvolgnummer van LO3 Voorkomen.
     * @param lO3Voorkomenvolgnummer lO3Voorkomenvolgnummer van LO3 Voorkomen.
     */
    public LO3VoorkomenModel(final LO3BerichtModel lO3Bericht, final LO3CategorieAttribuut lO3Categorie,
        final VolgnummerAttribuut lO3Stapelvolgnummer, final VolgnummerAttribuut lO3Voorkomenvolgnummer)
    {
        super(lO3Bericht, lO3Categorie, lO3Stapelvolgnummer, lO3Voorkomenvolgnummer);
    }

}
