/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import java.util.Set;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAdresInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonAdres;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonAdresHistorie;

import org.springframework.stereotype.Component;

/**
 * Map adres van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpAdresMapper {

    @Inject
    private BrpAdresInhoudMapper mapper;

    /**
     * Map het eerste adres.
     * 
     * @param persoonAdresSet
     *            de adressen
     * @return adres
     */
    public BrpStapel<BrpAdresInhoud> map(final Set<PersoonAdres> persoonAdresSet) {
        if (persoonAdresSet == null || persoonAdresSet.isEmpty()) {
            return null;
        } else {
            return mapper.map(persoonAdresSet.iterator().next().getPersoonAdresHistorieSet());
        }
    }

    /**
     * Inhoudelijke mapper.
     */
    @Component
    public static final class BrpAdresInhoudMapper extends BrpMapper<PersoonAdresHistorie, BrpAdresInhoud> {

        @Override
        protected BrpAdresInhoud mapInhoud(final PersoonAdresHistorie historie) {
            //@formatter:off
        return new BrpAdresInhoud(
                BrpMapperUtil.mapBrpFunctieAdresCode(historie.getFunctieAdres()), 
                BrpMapperUtil.mapBrpRedenWijzigingAdresCode(historie.getRedenWijzigingAdres()), 
                BrpMapperUtil.mapBrpAangeverAdreshoudingCode(historie.getAangeverAdreshouding()), 
                BrpMapperUtil.mapDatum(historie.getDatumAanvangAdreshouding()), 
                historie.getAdresseerbaarObject(), 
                historie.getIdentificatiecodeNummeraanduiding(), 
                BrpMapperUtil.mapBrpGemeenteCode(historie.getPartij()),
                historie.getNaamOpenbareRuimte(), 
                historie.getAfgekorteNaamOpenbareRuimte(), 
                historie.getGemeentedeel(), 
                BrpMapperUtil.mapInteger(historie.getHuisnummer()), 
                BrpMapperUtil.mapCharacter(historie.getHuisletter()), 
                historie.getHuisnummertoevoeging(), 
                historie.getPostcode(), 
                BrpMapperUtil.mapBrpPlaatsCode(historie.getPlaats()), 
                BrpMapperUtil.mapBrpAanduidingBijHuisnummerCode(historie.getLocatietovAdres()), 
                historie.getLocatieOmschrijving(), 
                historie.getBuitenlandsAdresRegel1(), 
                historie.getBuitenlandsAdresRegel2(), 
                historie.getBuitenlandsAdresRegel3(), 
                historie.getBuitenlandsAdresRegel4(), 
                historie.getBuitenlandsAdresRegel5(), 
                historie.getBuitenlandsAdresRegel6(), 
                BrpMapperUtil.mapBrpLandCode(historie.getLand()), 
                BrpMapperUtil.mapDatum(historie.getDatumVertrekUitNederland())
            );
        //@formatter:on
        }

    }

}
