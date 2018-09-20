/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de afgeleid administratieve gegevens.
 */
@Component
public final class PersoonAfgeleidAdministratiefMapper
        extends AbstractFormeelMapper<PersoonHisVolledig, HisPersoonAfgeleidAdministratiefModel, BrpPersoonAfgeleidAdministratiefInhoud>
{
    /**
     * Constructor.
     */
    public PersoonAfgeleidAdministratiefMapper() {
        super(ElementEnum.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPREGISTRATIE, ElementEnum.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisPersoonAfgeleidAdministratiefModel> getHistorieIterable(final PersoonHisVolledig persoonHisVolledig) {
        return persoonHisVolledig.getPersoonAfgeleidAdministratiefHistorie();
    }

    /**
     * Map inhoud.
     *
     * @param historie de historie die gemapt moet worden.
     * @param onderzoekMapper onderzoek mapper
     * @return de afgeleidAdministratief.
     */
    @Override
    public BrpPersoonAfgeleidAdministratiefInhoud mapInhoud(final HisPersoonAfgeleidAdministratiefModel historie, final OnderzoekMapper onderzoekMapper) {
        final BrpBoolean indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig =
                BrpMapperUtil.mapBrpBoolean(historie.getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig(), onderzoekMapper.bepaalOnderzoek(
                    historie.getID(),
                    ElementEnum.PERSOON_AFGELEIDADMINISTRATIEF_INDICATIEONVERWERKTBIJHOUDINGSVOORSTELNIETINGEZETENEAANWEZIG,
                    true));

        return new BrpPersoonAfgeleidAdministratiefInhoud(indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig);
    }
}
