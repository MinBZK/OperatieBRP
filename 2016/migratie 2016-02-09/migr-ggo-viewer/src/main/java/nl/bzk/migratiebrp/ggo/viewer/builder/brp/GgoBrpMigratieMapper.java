/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonMigratieHistorie;
import org.springframework.stereotype.Component;

/**
 * @see {nl.bzk.migratiebrp.ggo.viewer.mapper.GgoBrpMapper<T>}
 */
@Component
public class GgoBrpMigratieMapper extends AbstractGgoBrpMapper<PersoonMigratieHistorie> {
    @Override
    public final void verwerkInhoud(final GgoBrpVoorkomen voorkomen, final PersoonMigratieHistorie brpInhoud, final GgoBrpGroepEnum brpGroepEnum) {
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.SOORT_MIGRATIE, brpInhoud.getSoortMigratie());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.REDEN_WIJZIGING_ADRES, brpInhoud.getRedenWijzigingMigratie());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.AANGEVER_ADRESHOUDING, brpInhoud.getAangeverMigratie());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.LAND_OF_GEBIED_MIGRATIE, brpInhoud.getLandOfGebied());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_1, brpInhoud.getBuitenlandsAdresRegel1());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_2, brpInhoud.getBuitenlandsAdresRegel2());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_3, brpInhoud.getBuitenlandsAdresRegel3());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_4, brpInhoud.getBuitenlandsAdresRegel4());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_5, brpInhoud.getBuitenlandsAdresRegel5());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_6, brpInhoud.getBuitenlandsAdresRegel6());
    }
}
