/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerblijfsrechtModel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt het verblijfsrecht.
 */
@Component
public final class VerblijfsrechtMapper extends AbstractFormeelMapper<PersoonHisVolledig, HisPersoonVerblijfsrechtModel, BrpVerblijfsrechtInhoud> {

    /**
     * Constructor.
     */
    public VerblijfsrechtMapper() {
        super(ElementEnum.PERSOON_VERBLIJFSRECHT_TIJDSTIPREGISTRATIE, ElementEnum.PERSOON_VERBLIJFSRECHT_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisPersoonVerblijfsrechtModel> getHistorieIterable(final PersoonHisVolledig persoonHisVolledig) {
        return persoonHisVolledig.getPersoonVerblijfsrechtHistorie();
    }

    @Override
    public BrpVerblijfsrechtInhoud mapInhoud(final HisPersoonVerblijfsrechtModel historie, final OnderzoekMapper onderzoekMapper) {

        final BrpVerblijfsrechtCode aanduidingVerblijfsrecht =
                BrpMapperUtil.mapBrpVerblijfsrechtCode(
                    historie.getAanduidingVerblijfsrecht(),
                    onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_VERBLIJFSRECHT_AANDUIDINGCODE, true));
        final BrpDatum datumMededeling =
                BrpMapperUtil.mapBrpDatum(
                    historie.getDatumMededelingVerblijfsrecht(),
                    onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_VERBLIJFSRECHT_DATUMMEDEDELING, true));
        final BrpDatum datumAanvang =
                BrpMapperUtil.mapBrpDatum(
                    historie.getDatumAanvangVerblijfsrecht(),
                    onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_VERBLIJFSRECHT_DATUMAANVANG, true));
        final BrpDatum datumVoorzienEinde =
                BrpMapperUtil.mapBrpDatum(
                    historie.getDatumVoorzienEindeVerblijfsrecht(),
                    onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_VERBLIJFSRECHT_DATUMVOORZIENEINDE, true));

        return new BrpVerblijfsrechtInhoud(aanduidingVerblijfsrecht, datumMededeling, datumVoorzienEinde, datumAanvang);
    }
}
