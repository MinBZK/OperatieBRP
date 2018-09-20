/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de signalering met betrekking tot verstrekken reisdocument.
 */
@Component
public final class SignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper extends
        AbstractFormeelMapper<PersoonHisVolledig, HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel, BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud>
{
    /**
     * Constructor.
     */
    public SignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper() {
        super(ElementEnum.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_TIJDSTIPREGISTRATIE,
              ElementEnum.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel> getHistorieIterable(
        final PersoonHisVolledig persoonHisVolledig)
    {
        if (persoonHisVolledig.getIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument() == null) {
            return null;
        } else {
            return persoonHisVolledig.getIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument().getPersoonIndicatieHistorie();
        }
    }

    /**
     * Map inhoud.
     *
     * @param historie
     *            de historie die gemapt moet worden.
     * @param onderzoekMapper
     *            onderzoek mapper
     * @return de afgeleidAdministratief.
     */
    @Override
    public BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud mapInhoud(
        final HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel historie,
        final OnderzoekMapper onderzoekMapper)
    {
        final BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud resultaat =
                new BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud(
                    BrpMapperUtil.mapBrpBoolean(
                        historie.getWaarde(),
                        onderzoekMapper.bepaalOnderzoek(
                            historie.getID(),
                            ElementEnum.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT,
                            true)),
                    BrpMapperUtil.mapBrpString(historie.getMigratieRedenOpnameNationaliteit(), null),
                    BrpMapperUtil.mapBrpString(historie.getMigratieRedenBeeindigenNationaliteit(), null));

        return resultaat;
    }
}
