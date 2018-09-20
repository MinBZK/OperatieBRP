/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.KenmerkTerugmeldingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.TekstTerugmeldingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusTerugmeldingAttribuut;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.TerugmeldingHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisTerugmeldingStandaardGroep;
import nl.bzk.brp.model.logisch.kern.TerugmeldingStandaardGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_Terugmelding")
@GroepAccessor(dbObjectId = 10739)
public class HisTerugmeldingModel extends AbstractHisTerugmeldingModel implements ModelIdentificeerbaar<Integer>,
    HisTerugmeldingStandaardGroep
{

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisTerugmeldingModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param terugmeldingHisVolledig instantie van A-laag klasse.
     * @param groep                   groep
     * @param actieInhoud             Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisTerugmeldingModel(final TerugmeldingHisVolledig terugmeldingHisVolledig,
        final TerugmeldingStandaardGroep groep, final ActieModel actieInhoud)
    {
        super(terugmeldingHisVolledig, groep, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public HisTerugmeldingModel(final AbstractHisTerugmeldingModel kopie) {
        super(kopie);
    }

    /**
     * @param terugmelding          terugmelding van HisTerugmeldingModel
     * @param onderzoek             onderzoek van HisTerugmeldingModel
     * @param status                status van HisTerugmeldingModel
     * @param toelichting           toelichting van HisTerugmeldingModel
     * @param kenmerkMeldendePartij kenmerkMeldendePartij van HisTerugmeldingModel
     * @param actieInhoud           Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisTerugmeldingModel(final TerugmeldingHisVolledig terugmelding, final OnderzoekModel onderzoek,
        final StatusTerugmeldingAttribuut status, final TekstTerugmeldingAttribuut toelichting,
        final KenmerkTerugmeldingAttribuut kenmerkMeldendePartij, final ActieModel actieInhoud)
    {
        super(terugmelding, onderzoek, status, toelichting, kenmerkMeldendePartij, actieInhoud);
    }

}
