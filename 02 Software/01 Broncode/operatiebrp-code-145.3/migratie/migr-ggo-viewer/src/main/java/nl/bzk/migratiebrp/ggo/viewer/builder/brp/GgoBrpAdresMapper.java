/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;

import javax.inject.Inject;

/**
 * @see {nl.bzk.migratiebrp.ggo.viewer.mapper.GgoBrpMapper<T>}
 */
public class GgoBrpAdresMapper extends AbstractGgoBrpMapper<PersoonAdresHistorie> {

    /**
     * Constructor voor mapper implementatie.
     * @param ggoBrpGegevensgroepenBuilder gegevens groepen builder
     * @param ggoBrpActieBuilder actie builder
     * @param ggoBrpOnderzoekBuilder onderzoek builder
     * @param ggoBrpValueConvert value converter
     */
    @Inject
    public GgoBrpAdresMapper(final GgoBrpGegevensgroepenBuilder ggoBrpGegevensgroepenBuilder, final GgoBrpActieBuilder ggoBrpActieBuilder,
                             final GgoBrpOnderzoekBuilder ggoBrpOnderzoekBuilder, final GgoBrpValueConvert ggoBrpValueConvert) {
        super(ggoBrpGegevensgroepenBuilder, ggoBrpActieBuilder, ggoBrpOnderzoekBuilder, ggoBrpValueConvert);
    }

    @Override
    public final void verwerkInhoud(final GgoBrpVoorkomen voorkomen, final PersoonAdresHistorie brpInhoud, final GgoBrpGroepEnum brpGroepEnum) {
        final GgoBrpValueConvert ggoBrpValueConvert = getGgoBrpValueConvert();
        ggoBrpValueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.SOORT_ADRES, brpInhoud.getSoortAdres());
        ggoBrpValueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.REDEN_WIJZIGING_ADRES, brpInhoud.getRedenWijziging());
        ggoBrpValueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.AANGEVER_ADRESHOUDING, brpInhoud.getAangeverAdreshouding());
        ggoBrpValueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.DATUM_AANVANG_ADRESHOUDING, brpInhoud.getDatumAanvangAdreshouding());
        ggoBrpValueConvert.verwerkElement(
                voorkomen,
                GgoBrpElementEnum.IDENTIFICATIECODE_ADRESSEERBAAR_OBJECT,
                brpInhoud.getIdentificatiecodeAdresseerbaarObject());
        ggoBrpValueConvert.verwerkElement(
                voorkomen,
                GgoBrpElementEnum.IDENTIFICATIECODE_NUMMERAANDUIDING,
                brpInhoud.getIdentificatiecodeNummeraanduiding());
        ggoBrpValueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.GEMEENTE, brpInhoud.getGemeente());
        ggoBrpValueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.NAAM_OPENBARE_RUIMTE, brpInhoud.getNaamOpenbareRuimte());
        ggoBrpValueConvert.verwerkElement(
                voorkomen,
                GgoBrpElementEnum.AFGEKORTE_NAAM_OPENBARE_RUIMTE,
                brpInhoud.getAfgekorteNaamOpenbareRuimte());
        ggoBrpValueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.GEMEENTEDEEL, brpInhoud.getGemeentedeel());
        ggoBrpValueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.HUISNUMMER, brpInhoud.getHuisnummer());
        ggoBrpValueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.HUISLETTER, brpInhoud.getHuisletter());
        ggoBrpValueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.HUISNUMMERTOEVOEGING, brpInhoud.getHuisnummertoevoeging());
        ggoBrpValueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.POSTCODE, brpInhoud.getPostcode());
        ggoBrpValueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.WOONPLAATS, brpInhoud.getWoonplaatsnaam());
        ggoBrpValueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.LOCATIE_TOV_ADRES, brpInhoud.getLocatietovAdres());
        ggoBrpValueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.LOCATIE_OMSCHRIJVING, brpInhoud.getLocatieOmschrijving());
        ggoBrpValueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_1, brpInhoud.getBuitenlandsAdresRegel1());
        ggoBrpValueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_2, brpInhoud.getBuitenlandsAdresRegel2());
        ggoBrpValueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_3, brpInhoud.getBuitenlandsAdresRegel3());
        ggoBrpValueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_4, brpInhoud.getBuitenlandsAdresRegel4());
        ggoBrpValueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_5, brpInhoud.getBuitenlandsAdresRegel5());
        ggoBrpValueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.BUITENLANDS_ADRES_REGEL_6, brpInhoud.getBuitenlandsAdresRegel6());
        ggoBrpValueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.LAND_OF_GEBIED, brpInhoud.getLandOfGebied());
        ggoBrpValueConvert.verwerkElement(
                voorkomen,
                GgoBrpElementEnum.INDICATIE_PERSOON_AANGETROFFEN_OP_ADRES,
                brpInhoud.getIndicatiePersoonAangetroffenOpAdres());
    }
}
