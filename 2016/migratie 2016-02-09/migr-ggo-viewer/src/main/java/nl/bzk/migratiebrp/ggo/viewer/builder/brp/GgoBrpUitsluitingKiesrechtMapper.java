/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonUitsluitingKiesrechtHistorie;
import org.springframework.stereotype.Component;

/**
 * @see {nl.bzk.migratiebrp.ggo.viewer.mapper.GgoBrpMapper<T>}
 */
@Component
public class GgoBrpUitsluitingKiesrechtMapper extends AbstractGgoBrpMapper<PersoonUitsluitingKiesrechtHistorie> {
    @Override
    public final void verwerkInhoud(
        final GgoBrpVoorkomen voorkomen,
        final PersoonUitsluitingKiesrechtHistorie brpInhoud,
        final GgoBrpGroepEnum brpGroepEnum)
    {
        getGgoBrpValueConvert().verwerkElement(
            voorkomen,
            brpGroepEnum,
            GgoBrpElementEnum.INDICATIE_UITSLUITING_KIESRECHT,
            brpInhoud.getIndicatieUitsluitingKiesrecht());
        getGgoBrpValueConvert().verwerkElement(
            voorkomen,
            brpGroepEnum,
            GgoBrpElementEnum.DATUM_EINDE_UITSLUITING_KIESRECHT,
            brpInhoud.getDatumVoorzienEindeUitsluitingKiesrecht());
    }
}
