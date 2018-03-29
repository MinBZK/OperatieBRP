/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;

import java.util.Set;

import javax.inject.Inject;

/**
 * @see {nl.bzk.migratiebrp.ggo.viewer.mapper.GgoBrpMapper<T>}
 */
public class GgoBrpNationaliteitMapper extends AbstractGgoBrpMapper<PersoonNationaliteitHistorie> {

    /**
     * Constructor voor mapper implementatie.
     * @param ggoBrpGegevensgroepenBuilder gegevens groepen builder
     * @param ggoBrpActieBuilder actie builder
     * @param ggoBrpOnderzoekBuilder onderzoek builder
     * @param ggoBrpValueConvert value converter
     */
    @Inject
    public GgoBrpNationaliteitMapper(final GgoBrpGegevensgroepenBuilder ggoBrpGegevensgroepenBuilder, final GgoBrpActieBuilder ggoBrpActieBuilder,
                                     final GgoBrpOnderzoekBuilder ggoBrpOnderzoekBuilder, final GgoBrpValueConvert ggoBrpValueConvert) {
        super(ggoBrpGegevensgroepenBuilder, ggoBrpActieBuilder, ggoBrpOnderzoekBuilder, ggoBrpValueConvert);
    }

    @Override
    public final void verwerkInhoud(final GgoBrpVoorkomen voorkomen, final PersoonNationaliteitHistorie brpInhoud, final GgoBrpGroepEnum brpGroepEnum) {
        getGgoBrpValueConvert().verwerkElement(
                voorkomen,
                GgoBrpElementEnum.NATIONALITEIT,
                brpInhoud.getPersoonNationaliteit().getNationaliteit());
        getGgoBrpValueConvert().verwerkElement(
                voorkomen,
                GgoBrpElementEnum.REDEN_VERKRIJGING_NEDERLANDSCHAP,
                brpInhoud.getRedenVerkrijgingNLNationaliteit());
        getGgoBrpValueConvert().verwerkElement(
                voorkomen,
                GgoBrpElementEnum.REDEN_VERLIES_NEDERLANDSCHAP,
                brpInhoud.getRedenVerliesNLNationaliteit());
        getGgoBrpValueConvert().verwerkElement(
                voorkomen,
                GgoBrpElementEnum.DATUM_MIGRATIE_EINDE_BIJHOUDING,
                brpInhoud.getMigratieDatumEindeBijhouding());
        getGgoBrpValueConvert().verwerkElement(
                voorkomen,
                GgoBrpElementEnum.REDEN_OPNAME_NATIONALITEIT_MIGRATIE,
                brpInhoud.getMigratieRedenOpnameNationaliteit());
        getGgoBrpValueConvert().verwerkElement(
                voorkomen,
                GgoBrpElementEnum.REDEN_BEEINDIGEN_NATIONALITEIT_MIGRATIE,
                brpInhoud.getMigratieRedenBeeindigenNationaliteit());
        getGgoBrpValueConvert().verwerkElement(
                voorkomen,
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
