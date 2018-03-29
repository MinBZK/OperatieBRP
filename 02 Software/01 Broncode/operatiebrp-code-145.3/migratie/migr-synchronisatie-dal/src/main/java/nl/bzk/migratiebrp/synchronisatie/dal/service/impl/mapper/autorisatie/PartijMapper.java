/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import java.sql.Timestamp;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijHistorie;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpPartij;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpPartijInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.MapperUtil;

/**
 * Deze mapper mapped BrpPartijInhoud uit het migratie model op de corresponderen BRP entiteiten uit het operationele
 * datamodel van de BRP.
 */
public final class PartijMapper extends AbstractHistorieMapperStrategie<BrpPartijInhoud, PartijHistorie, Partij> {

    /**
     * Maakt een PartijMapper object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public PartijMapper(final DynamischeStamtabelRepository dynamischeStamtabelRepository, final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory, null);
    }

    /**
     * Map BRP partij naar nieuwe partij entiteit.
     * @param brpPartij BRP partij
     * @return partij entiteit
     */
    public Partij mapVanMigratie(final BrpPartij brpPartij) {
        final Partij partij = new Partij(brpPartij.getNaam(), brpPartij.getPartijCode().getWaarde());
        this.mapVanMigratie(brpPartij.getPartijStapel(), partij, null);
        partij.setIndicatieVerstrekkingsbeperkingMogelijk(brpPartij.getPartijStapel().getActueel().getInhoud().getIndicatieVerstrekkingsbeperking());
        return partij;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PartijHistorie historie, final Partij entiteit) {
        entiteit.getHisPartijen().add(historie);
    }

    @Override
    protected PartijHistorie mapHistorischeGroep(final BrpPartijInhoud groepInhoud, final Partij partij) {
        final PartijHistorie historie =
                new PartijHistorie(
                        partij,
                        new Timestamp(System.currentTimeMillis()),
                        MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumIngang()),
                        groepInhoud.getIndicatieVerstrekkingsbeperking(),
                        partij.getNaam());

        historie.setDatumEinde(MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumEinde()));
        historie.setIndicatieVerstrekkingsbeperkingMogelijk(groepInhoud.getIndicatieVerstrekkingsbeperking());

        return historie;
    }
}
