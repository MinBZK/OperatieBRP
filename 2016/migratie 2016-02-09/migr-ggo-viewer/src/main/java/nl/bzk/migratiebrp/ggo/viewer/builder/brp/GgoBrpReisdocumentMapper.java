/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import java.util.Set;

import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonReisdocumentHistorie;
import org.springframework.stereotype.Component;

/**
 * @see {nl.bzk.migratiebrp.ggo.viewer.mapper.GgoBrpMapper<T>}
 */
@Component
public class GgoBrpReisdocumentMapper extends AbstractGgoBrpMapper<PersoonReisdocumentHistorie> {
    @Override
    public final void verwerkInhoud(final GgoBrpVoorkomen voorkomen, final PersoonReisdocumentHistorie brpInhoud, final GgoBrpGroepEnum brpGroepEnum) {
        getGgoBrpValueConvert().verwerkElement(
            voorkomen,
            brpGroepEnum,
            GgoBrpElementEnum.SOORT,
            brpInhoud.getPersoonReisdocument().getSoortNederlandsReisdocument());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.NUMMER, brpInhoud.getNummer());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.DATUM_INGANG_DOCUMENT, brpInhoud.getDatumIngangDocument());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.DATUM_UITGIFTE, brpInhoud.getDatumUitgifte());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.AUTORITEIT_VAN_AFGIFTE, brpInhoud.getAutoriteitVanAfgifte());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.DATUM_EINDE_DOCUMENT, brpInhoud.getDatumEindeDocument());
        getGgoBrpValueConvert().verwerkElement(
            voorkomen,
            brpGroepEnum,
            GgoBrpElementEnum.DATUM_INHOUDING_OF_VERMISSING,
            brpInhoud.getDatumInhoudingOfVermissing());
        getGgoBrpValueConvert().verwerkElement(
            voorkomen,
            brpGroepEnum,
            GgoBrpElementEnum.AANDUIDING_INHOUDING_OF_VERMISSING,
            brpInhoud.getAanduidingInhoudingOfVermissingReisdocument());
    }

    @Override
    public final String bepaalStapelOmschrijving(final Set<PersoonReisdocumentHistorie> brpStapel) {
        for (final PersoonReisdocumentHistorie historie : brpStapel) {
            if (historie.getPersoonReisdocument() != null && historie.getPersoonReisdocument().getSoortNederlandsReisdocument() != null) {
                return historie.getPersoonReisdocument().getSoortNederlandsReisdocument().getCode();
            }
        }

        return "";
    }
}
