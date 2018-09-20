/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Sleutelwaarde;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObject;
import nl.bzk.brp.model.logisch.kern.GegevenInOnderzoek;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractGegevenInOnderzoekModel;


/**
 * Gegevens waar onderzoek naar gedaan wordt/naar gedaan is.
 *
 * Bij de naam '(Gegevens)Element' is de vraag welke elementen worden bedoeld: zijn dit de LOGISCHE elementen, of de
 * representanten hiervan in de database, de DATABASE OBJECTEN.
 * Voor een aantal objecttypen is de hypothese waaronder gewerkt wordt dat het de DATABASE OBJECTEN zijn. Om deze
 * duidelijk te kunnen scheiden van (andere) Elementen, hebben deze een aparte naam gekregen: Databaseobject.
 * In de verwijzing van het attribuut gebruiken we echter nog de naam 'Element': een Databaseobject zou immers kunnen
 * worden beschouwd als een specialisatie van Element. Alleen is die specialisatie in het model niet uitgemodelleerd.
 * RvdP 16 november 2011.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.1.8.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-11-27 12:02:51.
 * Gegenereerd op: Tue Nov 27 14:55:35 CET 2012.
 */
@Entity
@Table(schema = "Kern", name = "GegevenInOnderzoek")
public class GegevenInOnderzoekModel extends AbstractGegevenInOnderzoekModel implements GegevenInOnderzoek {

    //FIXME: TEMP HACK voor een id veld.
    @Id
    @SequenceGenerator(name = "GEGEVEN_IN_ONDERZOEK", sequenceName = "Kern.seq_GegevenInOnderzoek")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEGEVEN_IN_ONDERZOEK")
    private Long iD;

    
    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected GegevenInOnderzoekModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert.
     *
     * @param onderzoek onderzoek van Gegeven in onderzoek.
     * @param soortGegeven soortGegeven van Gegeven in onderzoek.
     * @param identificatie identificatie van Gegeven in onderzoek.
     */
    public GegevenInOnderzoekModel(final OnderzoekModel onderzoek, final DatabaseObject soortGegeven,
            final Sleutelwaarde identificatie)
    {
        super(onderzoek, soortGegeven, identificatie);
    }

}
