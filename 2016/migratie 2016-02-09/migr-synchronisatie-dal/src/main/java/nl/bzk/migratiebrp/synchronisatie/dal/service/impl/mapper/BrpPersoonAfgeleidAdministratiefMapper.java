/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * BrpPersoonAfgeleidAdministratief mapper.
 */
@Component
public final class BrpPersoonAfgeleidAdministratiefMapper
        extends AbstractBrpMapper<PersoonAfgeleidAdministratiefHistorie, BrpPersoonAfgeleidAdministratiefInhoud>
{

    /**
     * Map de gegevens.
     * 
     * @param historie
     *            persoonAfgeleidAdministratiefHistorie
     * @param brpOnderzoekMapper
     *            De mapper voor onderzoeken
     * @return persoon afgeleid administratief
     */
    public BrpPersoonAfgeleidAdministratiefInhoud mapInhoud(
        final PersoonAfgeleidAdministratiefHistorie historie,
        final BrpOnderzoekMapper brpOnderzoekMapper)
    {
        final BrpBoolean onverwerktBijhoudingsvoorstelNietIngezeteneAanwezig;
        final Lo3Onderzoek onderzoekOnverwerktBijhoudingsvoorstel;
        onderzoekOnverwerktBijhoudingsvoorstel =
                brpOnderzoekMapper.bepaalOnderzoek(
                    historie,
                    Element.PERSOON_AFGELEIDADMINISTRATIEF_INDICATIEONVERWERKTBIJHOUDINGSVOORSTELNIETINGEZETENEAANWEZIG,
                    true);
        final boolean onverwerktBijhoudingsvoorstel = historie.getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig();
        onverwerktBijhoudingsvoorstelNietIngezeteneAanwezig =
                BrpMapperUtil.mapBrpBoolean(onverwerktBijhoudingsvoorstel, onderzoekOnverwerktBijhoudingsvoorstel);
        return new BrpPersoonAfgeleidAdministratiefInhoud(onverwerktBijhoudingsvoorstelNietIngezeteneAanwezig);
    }
}
