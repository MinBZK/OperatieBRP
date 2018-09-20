/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatie;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractPersoonIndicatieModel;
import org.apache.commons.lang.builder.CompareToBuilder;


/**
 * Indicaties bij een persoon.
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
@Table(schema = "Kern", name = "PersIndicatie")
public class PersoonIndicatieModel extends AbstractPersoonIndicatieModel
    implements PersoonIndicatie, Comparable<PersoonIndicatieModel>
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected PersoonIndicatieModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert.
     *
     * @param persoon persoon van Persoon \ Indicatie.
     * @param soort soort van Persoon \ Indicatie.
     */
    public PersoonIndicatieModel(final PersoonModel persoon, final SoortIndicatie soort) {
        super(persoon, soort);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonIndicatie Te kopieren object type.
     * @param persoon Bijbehorende Persoon.
     */
    public PersoonIndicatieModel(final PersoonIndicatie persoonIndicatie, final PersoonModel persoon) {
        super(persoonIndicatie, persoon);
    }

    @Override
    public int compareTo(final PersoonIndicatieModel o) {
        return new CompareToBuilder().append(this.getSoort(), o.getSoort()).toComparison();
    }
}
