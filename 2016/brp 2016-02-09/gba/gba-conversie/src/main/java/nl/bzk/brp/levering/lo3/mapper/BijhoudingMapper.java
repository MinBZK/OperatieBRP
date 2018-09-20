/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;

import org.springframework.stereotype.Component;

/**
 * Mapt de bijhouding.
 */
@Component
public final class BijhoudingMapper extends AbstractMaterieelMapper<PersoonHisVolledig, HisPersoonBijhoudingModel, BrpBijhoudingInhoud> {
    /**
     * Constructor.
     */
    public BijhoudingMapper() {
        super(ElementEnum.PERSOON_BIJHOUDING_DATUMAANVANGGELDIGHEID,
              ElementEnum.PERSOON_BIJHOUDING_DATUMEINDEGELDIGHEID,
              ElementEnum.PERSOON_BIJHOUDING_TIJDSTIPREGISTRATIE,
              ElementEnum.PERSOON_BIJHOUDING_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisPersoonBijhoudingModel> getHistorieIterable(final PersoonHisVolledig persoonHisVolledig) {
        return persoonHisVolledig.getPersoonBijhoudingHistorie();
    }

    /**
     * Map inhoud.
     *
     * @param historie de historie die gemapt moet worden.
     * @param onderzoekMapper onderzoek mapper
     * @return de brpBijhoudingsaardInhoud.
     */
    @Override
    public BrpBijhoudingInhoud mapInhoud(final HisPersoonBijhoudingModel historie, final OnderzoekMapper onderzoekMapper) {
        return new BrpBijhoudingInhoud(
            BrpMapperUtil.mapBrpPartijCode(
                historie.getBijhoudingspartij(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_BIJHOUDING_PARTIJCODE, true)),
            BrpMapperUtil.mapBrpBijhoudingsaardCode(
                historie.getBijhoudingsaard(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_BIJHOUDING_BIJHOUDINGSAARDCODE, true)),
            BrpMapperUtil.mapBrpNadereBijhoudingsaardCode(
                historie.getNadereBijhoudingsaard(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE, true)),
            BrpMapperUtil.mapBrpBoolean(
                historie.getIndicatieOnverwerktDocumentAanwezig(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_BIJHOUDING_INDICATIEONVERWERKTDOCUMENTAANWEZIG, true)));
    }

}
