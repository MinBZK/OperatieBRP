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
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.SelectieperiodeInMaandenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 *
 *
 */
@Entity
@Table(schema = "AutAut", name = "His_DienstSelectie")
@Access(value = AccessType.FIELD)
public class HisDienstSelectie extends AbstractHisDienstSelectie implements ModelIdentificeerbaar<Integer> {

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected HisDienstSelectie() {
        super();
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param dienst dienst van HisDienstSelectie
     * @param eersteSelectiedatum eersteSelectiedatum van HisDienstSelectie
     * @param selectieperiodeInMaanden selectieperiodeInMaanden van HisDienstSelectie
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public HisDienstSelectie(
        final Dienst dienst,
        final DatumAttribuut eersteSelectiedatum,
        final SelectieperiodeInMaandenAttribuut selectieperiodeInMaanden,
        final ActieModel actieInhoud)
    {
        super(dienst, eersteSelectiedatum, selectieperiodeInMaanden, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public HisDienstSelectie(final AbstractHisDienstSelectie kopie) {
        super(kopie);
    }

}
