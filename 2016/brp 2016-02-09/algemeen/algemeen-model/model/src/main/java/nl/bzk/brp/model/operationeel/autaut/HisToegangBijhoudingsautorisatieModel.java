/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.autaut;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.autaut.ToegangBijhoudingsautorisatieHisVolledig;
import nl.bzk.brp.model.logisch.autaut.ToegangBijhoudingsautorisatieStandaardGroep;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 *
 *
 */
@Entity
@Table(schema = "AutAut", name = "His_ToegangBijhautorisatie")
@Access(value = AccessType.FIELD)
public class HisToegangBijhoudingsautorisatieModel extends AbstractHisToegangBijhoudingsautorisatieModel implements
        ToegangBijhoudingsautorisatieStandaardGroep, ModelIdentificeerbaar<Integer>
{

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected HisToegangBijhoudingsautorisatieModel() {
        super();
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param toegangBijhoudingsautorisatie toegangBijhoudingsautorisatie van HisToegangBijhoudingsautorisatieModel
     * @param datumIngang datumIngang van HisToegangBijhoudingsautorisatieModel
     * @param datumEinde datumEinde van HisToegangBijhoudingsautorisatieModel
     * @param indicatieGeblokkeerd indicatieGeblokkeerd van HisToegangBijhoudingsautorisatieModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public HisToegangBijhoudingsautorisatieModel(
        final ToegangBijhoudingsautorisatieHisVolledig toegangBijhoudingsautorisatie,
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde,
        final JaAttribuut indicatieGeblokkeerd,
        final ActieModel actieInhoud)
    {
        super(toegangBijhoudingsautorisatie, datumIngang, datumEinde, indicatieGeblokkeerd, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public HisToegangBijhoudingsautorisatieModel(final AbstractHisToegangBijhoudingsautorisatieModel kopie) {
        super(kopie);
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param toegangBijhoudingsautorisatieHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisToegangBijhoudingsautorisatieModel(
        final ToegangBijhoudingsautorisatieHisVolledig toegangBijhoudingsautorisatieHisVolledig,
        final ToegangBijhoudingsautorisatieStandaardGroep groep,
        final ActieModel actieInhoud)
    {
        super(toegangBijhoudingsautorisatieHisVolledig, groep, actieInhoud);
    }

}
