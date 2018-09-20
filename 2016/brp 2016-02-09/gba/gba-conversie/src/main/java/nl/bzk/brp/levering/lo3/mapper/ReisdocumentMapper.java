/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonReisdocumentHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonReisdocumentModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt een reisdocument.
 */
@Component
public final class ReisdocumentMapper extends AbstractFormeelMapper<PersoonReisdocumentHisVolledig, HisPersoonReisdocumentModel, BrpReisdocumentInhoud> {

    /**
     * Constructor.
     */
    public ReisdocumentMapper() {
        super(ElementEnum.PERSOON_REISDOCUMENT_TIJDSTIPREGISTRATIE, ElementEnum.PERSOON_REISDOCUMENT_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisPersoonReisdocumentModel> getHistorieIterable(final PersoonReisdocumentHisVolledig persoonReisdocumentHisVolledig) {
        return persoonReisdocumentHisVolledig.getPersoonReisdocumentHistorie();
    }

    @Override
    public BrpReisdocumentInhoud mapInhoud(final HisPersoonReisdocumentModel historie, final OnderzoekMapper onderzoekMapper) {
        return new BrpReisdocumentInhoud(
            BrpMapperUtil.mapBrpSoortNederlandsReisdocumentCode(
                historie.getPersoonReisdocument().getSoort(),
                onderzoekMapper.bepaalOnderzoek(historie.getPersoonReisdocument().getID(), ElementEnum.PERSOON_REISDOCUMENT_SOORTCODE, true)),
            BrpMapperUtil.mapBrpString(
                historie.getNummer(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_REISDOCUMENT_NUMMER, true)),
            BrpMapperUtil.mapBrpDatum(
                historie.getDatumIngangDocument(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_REISDOCUMENT_DATUMINGANGDOCUMENT, true)),
            BrpMapperUtil.mapBrpDatum(
                historie.getDatumUitgifte(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_REISDOCUMENT_DATUMUITGIFTE, true)),
            BrpMapperUtil.mapBrpReisdocumentAutoriteitVanAfgifteCode(
                historie.getAutoriteitVanAfgifte(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_REISDOCUMENT_AUTORITEITVANAFGIFTE, true)),
            BrpMapperUtil.mapBrpDatum(
                historie.getDatumEindeDocument(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_REISDOCUMENT_DATUMEINDEDOCUMENT, true)),
            BrpMapperUtil.mapBrpDatum(
                historie.getDatumInhoudingVermissing(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_REISDOCUMENT_DATUMINHOUDINGVERMISSING, true)),
            BrpMapperUtil.mapBrpAanduidingInhoudingOfVermissingReisdocumentCode(
                historie.getAanduidingInhoudingVermissing(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_REISDOCUMENT_AANDUIDINGINHOUDINGVERMISSINGCODE, true)));
    }

    @Override
    protected Integer getStapelNummer(final HisPersoonReisdocumentModel historie) {
        return historie.getPersoonReisdocument().getID();
    }
}
