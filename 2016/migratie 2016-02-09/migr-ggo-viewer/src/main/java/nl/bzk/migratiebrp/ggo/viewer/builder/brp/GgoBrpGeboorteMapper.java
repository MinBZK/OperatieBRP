/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import java.util.Map;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeboorteHistorie;
import org.springframework.stereotype.Component;

/**
 * @see {nl.bzk.migratiebrp.ggo.viewer.mapper.GgoBrpMapper<T>}
 */
@Component
public class GgoBrpGeboorteMapper extends AbstractGgoBrpMapper<PersoonGeboorteHistorie> {
    @Override
    public final void verwerkInhoud(final GgoBrpVoorkomen voorkomen, final PersoonGeboorteHistorie brpInhoud, final GgoBrpGroepEnum brpGroepEnum) {
        verwerkInhoud(voorkomen.getInhoud(), brpInhoud, brpGroepEnum);
    }

    /**
     * Uitgebreidere aanroep tbv het verwerken van 'meer' en 'samenvatting'.
     *
     * @param ggoInhoud
     *            De Map<String,String> waaraan de inhoud wordt toegevoegd.
     * @param brpInhoud
     *            De inhoud van de bron-BRP-groep.
     * @param brpGroepEnum
     *            Het label van de groep.
     */
    public final void verwerkInhoud(final Map<String, String> ggoInhoud, final PersoonGeboorteHistorie brpInhoud, final GgoBrpGroepEnum brpGroepEnum) {
        getGgoBrpValueConvert().verwerkElement(ggoInhoud, brpGroepEnum, GgoBrpElementEnum.DATUM_GEBOORTE, brpInhoud.getDatumGeboorte());
        getGgoBrpValueConvert().verwerkElement(ggoInhoud, brpGroepEnum, GgoBrpElementEnum.GEMEENTE_GEBOORTE, brpInhoud.getGemeente());
        getGgoBrpValueConvert().verwerkElement(ggoInhoud, brpGroepEnum, GgoBrpElementEnum.WOONPLAATSNAAM_GEBOORTE, brpInhoud.getWoonplaatsnaamGeboorte());
        getGgoBrpValueConvert().verwerkElement(
            ggoInhoud,
            brpGroepEnum,
            GgoBrpElementEnum.BUITENLANDSE_PLAATS_GEBOORTE,
            brpInhoud.getBuitenlandsePlaatsGeboorte());
        getGgoBrpValueConvert().verwerkElement(
            ggoInhoud,
            brpGroepEnum,
            GgoBrpElementEnum.BUITENLANDSE_REGIO_GEBOORTE,
            brpInhoud.getBuitenlandseRegioGeboorte());
        getGgoBrpValueConvert().verwerkElement(ggoInhoud, brpGroepEnum, GgoBrpElementEnum.LAND_OF_GEBIED_GEBOORTE, brpInhoud.getLandOfGebied());
        getGgoBrpValueConvert().verwerkElement(
            ggoInhoud,
            brpGroepEnum,
            GgoBrpElementEnum.OMSCHRIJVING_GEBOORTELOCATIE,
            brpInhoud.getOmschrijvingGeboortelocatie());
    }
}
