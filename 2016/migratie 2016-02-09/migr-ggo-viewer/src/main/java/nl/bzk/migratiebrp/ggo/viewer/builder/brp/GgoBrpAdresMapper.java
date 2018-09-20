/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonAdresHistorie;
import org.springframework.stereotype.Component;

/**
 * @see {nl.bzk.migratiebrp.ggo.viewer.mapper.GgoBrpMapper<T>}
 */
@Component
public class GgoBrpAdresMapper extends AbstractGgoBrpMapper<PersoonAdresHistorie> {
    @Override
    public final void verwerkInhoud(final GgoBrpVoorkomen voorkomen, final PersoonAdresHistorie brpInhoud, final GgoBrpGroepEnum brpGroepEnum) {
        final GgoBrpValueConvert ggoBrpValueConvert = getGgoBrpValueConvert();
        ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.SOORT_ADRES, brpInhoud.getSoortAdres());
        ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.REDEN_WIJZIGING_ADRES, brpInhoud.getRedenWijziging());
        ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.AANGEVER_ADRESHOUDING, brpInhoud.getAangeverAdreshouding());
        ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.DATUM_AANVANG_ADRESHOUDING, brpInhoud.getDatumAanvangAdreshouding());
        ggoBrpValueConvert.verwerkElement(
            voorkomen,
            brpGroepEnum,
            GgoBrpElementEnum.IDENTIFICATIECODE_ADRESSEERBAAR_OBJECT,
            brpInhoud.getIdentificatiecodeAdresseerbaarObject());
        ggoBrpValueConvert.verwerkElement(
            voorkomen,
            brpGroepEnum,
            GgoBrpElementEnum.IDENTIFICATIECODE_NUMMERAANDUIDING,
            brpInhoud.getIdentificatiecodeNummeraanduiding());
        ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.GEMEENTE, brpInhoud.getGemeente());
        ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.NAAM_OPENBARE_RUIMTE, brpInhoud.getNaamOpenbareRuimte());
        ggoBrpValueConvert.verwerkElement(
            voorkomen,
            brpGroepEnum,
            GgoBrpElementEnum.AFGEKORTE_NAAM_OPENBARE_RUIMTE,
            brpInhoud.getAfgekorteNaamOpenbareRuimte());
        ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.GEMEENTEDEEL, brpInhoud.getGemeentedeel());
        ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.HUISNUMMER, brpInhoud.getHuisnummer());
        ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.HUISLETTER, brpInhoud.getHuisletter());
        ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.HUISNUMMERTOEVOEGING, brpInhoud.getHuisnummertoevoeging());
        ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.POSTCODE, brpInhoud.getPostcode());
        ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.WOONPLAATS, brpInhoud.getWoonplaatsnaam());
        ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.LOCATIE_TOV_ADRES, brpInhoud.getLocatietovAdres());
        ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.LOCATIE_OMSCHRIJVING, brpInhoud.getLocatieOmschrijving());
        ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_1, brpInhoud.getBuitenlandsAdresRegel1());
        ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_2, brpInhoud.getBuitenlandsAdresRegel2());
        ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_3, brpInhoud.getBuitenlandsAdresRegel3());
        ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_4, brpInhoud.getBuitenlandsAdresRegel4());
        ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_5, brpInhoud.getBuitenlandsAdresRegel5());
        ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_6, brpInhoud.getBuitenlandsAdresRegel6());
        ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.LAND_OF_GEBIED, brpInhoud.getLandOfGebied());
        ggoBrpValueConvert.verwerkElement(
            voorkomen,
            brpGroepEnum,
            GgoBrpElementEnum.INDICATIE_PERSOON_AANGETROFFEN_OP_ADRES,
            brpInhoud.getIndicatiePersoonAangetroffenOpAdres());
    }
}
