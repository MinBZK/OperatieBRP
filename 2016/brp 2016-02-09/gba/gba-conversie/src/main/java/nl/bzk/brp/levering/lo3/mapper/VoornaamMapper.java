/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVoornaamHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.springframework.stereotype.Component;

/**
 * Mapt een voornaam.
 */
@Component
public final class VoornaamMapper extends AbstractMaterieelMapper<PersoonVoornaamHisVolledig, HisPersoonVoornaamModel, BrpVoornaamInhoud> {

    /**
     * Constructor.
     */
    public VoornaamMapper() {
        super(ElementEnum.PERSOON_VOORNAAM_DATUMAANVANGGELDIGHEID,
              ElementEnum.PERSOON_VOORNAAM_DATUMEINDEGELDIGHEID,
              ElementEnum.PERSOON_VOORNAAM_TIJDSTIPREGISTRATIE,
              ElementEnum.PERSOON_VOORNAAM_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisPersoonVoornaamModel> getHistorieIterable(final PersoonVoornaamHisVolledig persoonVoornaamHisVolledig) {
        return persoonVoornaamHisVolledig.getPersoonVoornaamHistorie();
    }

    @Override
    public BrpVoornaamInhoud mapInhoud(final HisPersoonVoornaamModel historie, final OnderzoekMapper onderzoekMapper) {
        final VoornaamAttribuut voornaamAttribuut = historie.getNaam();
        final Lo3Onderzoek lo3OnderzoekVoornaam = onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_VOORNAAM_NAAM, true);

        final VolgnummerAttribuut volgnummerAttribuut = historie.getPersoonVoornaam().getVolgnummer();
        final Lo3Onderzoek lo3OnderzoekPersoonVoornaam =
                onderzoekMapper.bepaalOnderzoek(historie.getPersoonVoornaam().getID(), ElementEnum.PERSOON_VOORNAAM_VOLGNUMMER, true);

        return new BrpVoornaamInhoud(
            BrpMapperUtil.mapBrpString(voornaamAttribuut, lo3OnderzoekVoornaam),
            BrpMapperUtil.mapBrpInteger(volgnummerAttribuut, lo3OnderzoekPersoonVoornaam));
    }

}
