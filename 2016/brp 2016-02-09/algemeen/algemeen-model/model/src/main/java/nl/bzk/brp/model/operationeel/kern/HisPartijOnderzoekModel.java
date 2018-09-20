/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartijOnderzoekAttribuut;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.PartijOnderzoekHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisPartijOnderzoekStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PartijOnderzoekStandaardGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_PartijOnderzoek")
@GroepAccessor(dbObjectId = 10785)
public class HisPartijOnderzoekModel extends AbstractHisPartijOnderzoekModel implements ModelIdentificeerbaar<Integer>,
    HisPartijOnderzoekStandaardGroep
{

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisPartijOnderzoekModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param partijOnderzoekHisVolledig instantie van A-laag klasse.
     * @param groep                      groep
     * @param actieInhoud                Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisPartijOnderzoekModel(final PartijOnderzoekHisVolledig partijOnderzoekHisVolledig,
        final PartijOnderzoekStandaardGroep groep, final ActieModel actieInhoud)
    {
        super(partijOnderzoekHisVolledig, groep, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public HisPartijOnderzoekModel(final AbstractHisPartijOnderzoekModel kopie) {
        super(kopie);
    }

    /**
     * @param partijOnderzoek partijOnderzoek van HisPartijOnderzoekModel
     * @param rol             rol van HisPartijOnderzoekModel
     * @param actieInhoud     Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisPartijOnderzoekModel(final PartijOnderzoekHisVolledig partijOnderzoek,
        final SoortPartijOnderzoekAttribuut rol, final ActieModel actieInhoud)
    {
        super(partijOnderzoek, rol, actieInhoud);
    }

    /**
     * @return de objectsleutel
     */
    @Transient
    public final String getObjectSleutel() {
        return getPartijOnderzoek().getID().toString();
    }

}
