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
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNationaliteitHistorie;

import org.springframework.stereotype.Component;

/**
 * @see {nl.bzk.migratiebrp.ggo.viewer.mapper.GgoBrpMapper<T>}
 */
@Component
public class GgoBrpNationaliteitMapper extends AbstractGgoBrpMapper<PersoonNationaliteitHistorie> {
    @Override
    public final void verwerkInhoud(final GgoBrpVoorkomen voorkomen, final PersoonNationaliteitHistorie brpInhoud, final GgoBrpGroepEnum brpGroepEnum) {
        getGgoBrpValueConvert().verwerkElement(
            voorkomen,
            brpGroepEnum,
            GgoBrpElementEnum.NATIONALITEIT,
            brpInhoud.getPersoonNationaliteit().getNationaliteit());
        getGgoBrpValueConvert().verwerkElement(
            voorkomen,
            brpGroepEnum,
            GgoBrpElementEnum.REDEN_VERKRIJGING_NEDERLANDSCHAP,
            brpInhoud.getRedenVerkrijgingNLNationaliteit());
        getGgoBrpValueConvert().verwerkElement(
            voorkomen,
            brpGroepEnum,
            GgoBrpElementEnum.REDEN_VERLIES_NEDERLANDSCHAP,
            brpInhoud.getRedenVerliesNLNationaliteit());
        getGgoBrpValueConvert().verwerkElement(
            voorkomen,
            brpGroepEnum,
            GgoBrpElementEnum.DATUM_MIGRATIE_EINDE_BIJHOUDING,
            brpInhoud.getMigratieDatumEindeBijhouding());
        getGgoBrpValueConvert().verwerkElement(
            voorkomen,
            brpGroepEnum,
            GgoBrpElementEnum.REDEN_OPNAME_NATIONALITEIT_MIGRATIE,
            brpInhoud.getMigratieRedenOpnameNationaliteit());
        getGgoBrpValueConvert().verwerkElement(
            voorkomen,
            brpGroepEnum,
            GgoBrpElementEnum.REDEN_BEEINDIGEN_NATIONALITEIT_MIGRATIE,
            brpInhoud.getMigratieRedenBeeindigenNationaliteit());
        getGgoBrpValueConvert().verwerkElement(
            voorkomen,
            brpGroepEnum,
            GgoBrpElementEnum.INDICATIE_BIJHOUDING_BEEINDIGD,
            brpInhoud.getIndicatieBijhoudingBeeindigd());
    }

    @Override
    protected final String bepaalStapelOmschrijving(final Set<PersoonNationaliteitHistorie> brpStapel) {
        for (final PersoonNationaliteitHistorie historie : brpStapel) {
            if (historie.getPersoonNationaliteit() != null && historie.getPersoonNationaliteit().getNationaliteit() != null) {
                return getGgoBrpValueConvert().convertToViewerValue(historie.getPersoonNationaliteit().getNationaliteit(), GgoBrpElementEnum.NATIONALITEIT);
            }
        }

        return "";
    }
}
