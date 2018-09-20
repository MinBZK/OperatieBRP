/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 *
 *
 */
@Entity
@Table(schema = "AutAut", name = "His_DienstbundelLO3Rubriek")
@Access(value = AccessType.FIELD)
public class HisDienstbundelLO3Rubriek extends AbstractHisDienstbundelLO3Rubriek implements ModelIdentificeerbaar<Integer> {

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected HisDienstbundelLO3Rubriek() {
        super();
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param dienstbundelLO3Rubriek dienstbundelLO3Rubriek van HisDienstbundelLO3Rubriek
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public HisDienstbundelLO3Rubriek(final DienstbundelLO3Rubriek dienstbundelLO3Rubriek, final ActieModel actieInhoud) {
        super(dienstbundelLO3Rubriek, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public HisDienstbundelLO3Rubriek(final AbstractHisDienstbundelLO3Rubriek kopie) {
        super(kopie);
    }

}
