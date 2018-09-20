/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.prot;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.prot.LeveringsaantekeningHisVolledig;

/**
 * HisVolledig klasse voor Leveringsaantekening.
 *
 */
@Entity
@Table(schema = "Prot", name = "Levsaantek")
@Access(value = AccessType.FIELD)
public class LeveringsaantekeningHisVolledigImpl extends AbstractLeveringsaantekeningHisVolledigImpl implements HisVolledigImpl,
        LeveringsaantekeningHisVolledig
{

    /**
     * Default contructor voor JPA.
     *
     */
    protected LeveringsaantekeningHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft. CHECKSTYLE-BRP:OFF -
     * MAX PARAMS
     *
     * @param toegangLeveringsautorisatieId toegangLeveringsautorisatieId van Leveringsaantekening.
     * @param dienstId dienstId van Leveringsaantekening.
     * @param datumTijdKlaarzettenLevering datumTijdKlaarzettenLevering van Leveringsaantekening.
     * @param datumMaterieelSelectie datumMaterieelSelectie van Leveringsaantekening.
     * @param datumAanvangMaterielePeriodeResultaat datumAanvangMaterielePeriodeResultaat van Leveringsaantekening.
     * @param datumEindeMaterielePeriodeResultaat datumEindeMaterielePeriodeResultaat van Leveringsaantekening.
     * @param datumTijdAanvangFormelePeriodeResultaat datumTijdAanvangFormelePeriodeResultaat van Leveringsaantekening.
     * @param datumTijdEindeFormelePeriodeResultaat datumTijdEindeFormelePeriodeResultaat van Leveringsaantekening.
     * @param administratieveHandelingId administratieveHandelingId van Leveringsaantekening.
     * @param soortSynchronisatie soortSynchronisatie van Leveringsaantekening.
     */
    public LeveringsaantekeningHisVolledigImpl(
        final Integer toegangLeveringsautorisatieId,
        final Integer dienstId,
        final DatumTijdAttribuut datumTijdKlaarzettenLevering,
        final DatumAttribuut datumMaterieelSelectie,
        final DatumAttribuut datumAanvangMaterielePeriodeResultaat,
        final DatumAttribuut datumEindeMaterielePeriodeResultaat,
        final DatumTijdAttribuut datumTijdAanvangFormelePeriodeResultaat,
        final DatumTijdAttribuut datumTijdEindeFormelePeriodeResultaat,
        final Long administratieveHandelingId,
        final SoortSynchronisatieAttribuut soortSynchronisatie)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(toegangLeveringsautorisatieId,
              dienstId,
              datumTijdKlaarzettenLevering,
              datumMaterieelSelectie,
              datumAanvangMaterielePeriodeResultaat,
              datumEindeMaterielePeriodeResultaat,
              datumTijdAanvangFormelePeriodeResultaat,
              datumTijdEindeFormelePeriodeResultaat,
              administratieveHandelingId,
              soortSynchronisatie);
    }

}
