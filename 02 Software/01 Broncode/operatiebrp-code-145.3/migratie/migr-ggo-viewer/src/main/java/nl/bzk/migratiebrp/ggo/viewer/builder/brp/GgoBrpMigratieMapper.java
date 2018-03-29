/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonMigratieHistorie;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;

import javax.inject.Inject;

/**
 * @see {nl.bzk.migratiebrp.ggo.viewer.mapper.GgoBrpMapper<T>}
 */
public class GgoBrpMigratieMapper extends AbstractGgoBrpMapper<PersoonMigratieHistorie> {

    /**
     * Constructor voor mapper implementatie.
     * @param ggoBrpGegevensgroepenBuilder gegevens groepen builder
     * @param ggoBrpActieBuilder actie builder
     * @param ggoBrpOnderzoekBuilder onderzoek builder
     * @param ggoBrpValueConvert value converter
     */
    @Inject
    public GgoBrpMigratieMapper(final GgoBrpGegevensgroepenBuilder ggoBrpGegevensgroepenBuilder, final GgoBrpActieBuilder ggoBrpActieBuilder,
                                final GgoBrpOnderzoekBuilder ggoBrpOnderzoekBuilder, final GgoBrpValueConvert ggoBrpValueConvert) {
        super(ggoBrpGegevensgroepenBuilder, ggoBrpActieBuilder, ggoBrpOnderzoekBuilder, ggoBrpValueConvert);
    }

    @Override
    public final void verwerkInhoud(final GgoBrpVoorkomen voorkomen, final PersoonMigratieHistorie brpInhoud, final GgoBrpGroepEnum brpGroepEnum) {
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.SOORT_MIGRATIE, brpInhoud.getSoortMigratie());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.REDEN_WIJZIGING_ADRES, brpInhoud.getRedenWijzigingMigratie());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.AANGEVER_ADRESHOUDING, brpInhoud.getAangeverMigratie());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.LAND_OF_GEBIED_MIGRATIE, brpInhoud.getLandOfGebied());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_1, brpInhoud.getBuitenlandsAdresRegel1());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_2, brpInhoud.getBuitenlandsAdresRegel2());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_3, brpInhoud.getBuitenlandsAdresRegel3());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_4, brpInhoud.getBuitenlandsAdresRegel4());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_5, brpInhoud.getBuitenlandsAdresRegel5());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_6, brpInhoud.getBuitenlandsAdresRegel6());
    }
}
