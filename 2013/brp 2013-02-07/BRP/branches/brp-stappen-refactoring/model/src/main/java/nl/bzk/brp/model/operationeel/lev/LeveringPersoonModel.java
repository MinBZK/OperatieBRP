/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.lev;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.logisch.lev.LeveringPersoon;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.lev.basis.AbstractLeveringPersoonModel;


/**
 * Het betrokken zijn van een Persoon in een Levering.
 *
 * Bij een Levering van Persoonsgegevens, zijn ��n of meer Personen het "onderwerp" van de Levering. Indien een Persoon
 * onderwerp is van een Levering, dan wordt de koppeling tussen deze Levering en de Persoon vastgelegd.
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
@Table(schema = "Lev", name = "LevPers")
public class LeveringPersoonModel extends AbstractLeveringPersoonModel implements LeveringPersoon {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected LeveringPersoonModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert.
     *
     * @param levering levering van Levering \ Persoon.
     * @param persoon persoon van Levering \ Persoon.
     */
    public LeveringPersoonModel(final LeveringModel levering, final PersoonModel persoon) {
        super(levering, persoon);
    }

}
