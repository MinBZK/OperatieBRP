/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementAttribuut;
import nl.bzk.brp.model.logisch.kern.GegevenInOnderzoek;


/**
 * Gegevens waar onderzoek naar gedaan wordt/naar gedaan is.
 * <p/>
 * Bij de naam '(Gegevens)Element' is de vraag welke elementen worden bedoeld: zijn dit de LOGISCHE elementen, of de representanten hiervan in de database,
 * de DATABASE OBJECTEN. Voor een aantal objecttypen is de hypothese waaronder gewerkt wordt dat het de DATABASE OBJECTEN zijn. Om deze duidelijk te kunnen
 * scheiden van (andere) Elementen, hebben deze een aparte naam gekregen: Databaseobject. In de verwijzing van het attribuut gebruiken we echter nog de
 * naam 'Element': een Databaseobject zou immers kunnen worden beschouwd als een specialisatie van Element. Alleen is die specialisatie in het model niet
 * uitgemodelleerd. RvdP 16 november 2011.
 */
@Entity
@Table(schema = "Kern", name = "GegevenInOnderzoek")
public class GegevenInOnderzoekModel extends AbstractGegevenInOnderzoekModel implements GegevenInOnderzoek {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected GegevenInOnderzoekModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param onderzoek onderzoek van Gegeven in onderzoek.
     * @param soortGegeven soortGegeven van Gegeven in onderzoek.
     * @param objectSleutel objectSleutel van Gegeven in onderzoek.
     * @param voorkomenSleutel voorkomenSleutel van Gegeven in onderzoek.
     */
    public GegevenInOnderzoekModel(
        final OnderzoekModel onderzoek,
        final ElementAttribuut soortGegeven,
        final SleutelwaardeAttribuut objectSleutel,
        final SleutelwaardeAttribuut voorkomenSleutel)
    {
        super(onderzoek, soortGegeven, objectSleutel, voorkomenSleutel);
    }

}
