/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.lev;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.logisch.lev.LeveringCommunicatie;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;
import nl.bzk.brp.model.operationeel.lev.basis.AbstractLeveringCommunicatieModel;


/**
 * De communicatie over een Levering, door middel van een bericht.
 *
 * Een Levering is (vaak) aanleiding tot ��n of meer uitgaande berichten: een bericht met de inhoud, en/of een bericht
 * met de melding dat de Levering klaar staat om opgehaald te worden. Al deze situaties worden gegeneraliseerd tot
 * 'communicatie over de leverring', of te wel Levering \ Communicatie.
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
@Table(schema = "Lev", name = "LevCommunicatie")
public class LeveringCommunicatieModel extends AbstractLeveringCommunicatieModel implements LeveringCommunicatie {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected LeveringCommunicatieModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert.
     *
     * @param levering levering van Levering \ Communicatie.
     * @param uitgaandBericht uitgaandBericht van Levering \ Communicatie.
     */
    public LeveringCommunicatieModel(final LeveringModel levering, final BerichtModel uitgaandBericht) {
        super(levering, uitgaandBericht);
    }

}
