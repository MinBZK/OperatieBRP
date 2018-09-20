/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonOverlijdenHistorie;
import org.springframework.stereotype.Component;

/**
 * @see {nl.bzk.migratiebrp.ggo.viewer.mapper.GgoBrpMapper<T>}
 */
@Component
public class GgoBrpOverlijdenMapper extends AbstractGgoBrpMapper<PersoonOverlijdenHistorie> {
    @Override
    public final void verwerkInhoud(final GgoBrpVoorkomen voorkomen, final PersoonOverlijdenHistorie brpInhoud, final GgoBrpGroepEnum brpGroepEnum) {
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.DATUM_OVERLIJDEN, brpInhoud.getDatumOverlijden());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.GEMEENTE, brpInhoud.getGemeente());
        getGgoBrpValueConvert().verwerkElement(
            voorkomen,
            brpGroepEnum,
            GgoBrpElementEnum.WOONPLAATSNAAM_OVERLIJDEN,
            brpInhoud.getWoonplaatsnaamOverlijden());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.BUITENLANDSE_PLAATS, brpInhoud.getBuitenlandsePlaatsOverlijden());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.BUITENLANDSE_REGIO, brpInhoud.getBuitenlandseRegioOverlijden());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.LAND_OF_GEBIED, brpInhoud.getLandOfGebied());
        getGgoBrpValueConvert().verwerkElement(
            voorkomen,
            brpGroepEnum,
            GgoBrpElementEnum.OMSCHRIJVING_LOCATIE,
            brpInhoud.getOmschrijvingLocatieOverlijden());
    }
}
