/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstHistorie;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.MapperUtil;

/**
 * Mapper verantwoordelijk voor het mappen van het BRP model naar een Dienst entiteit.
 */
// @formatter:off
public final class DienstMapper extends
        AbstractHistorieMapperStrategie<BrpDienstInhoud, DienstHistorie, Dienst> {
    // @formatter:on

    /**
     * Constructor.
     * @param dynamischeStamtabelRepository dynamische stamtabel repository om oa partijen te bevragen
     * @param brpActieFactory actie factory
     */
    public DienstMapper(final DynamischeStamtabelRepository dynamischeStamtabelRepository, final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory, null);
    }

    @Override
    protected void voegHistorieToeAanEntiteit(final DienstHistorie historie, final Dienst entiteit) {
        Set<DienstHistorie> historieSet = entiteit.getDienstHistorieSet();
        if (historieSet == null) {
            historieSet = new HashSet<>();
            entiteit.setDienstHistorieSet(historieSet);
        }
        historieSet.add(historie);
    }

    @Override
    protected DienstHistorie mapHistorischeGroep(final BrpDienstInhoud groepInhoud, final Dienst entiteit) {
        final DienstHistorie result =
                new DienstHistorie(entiteit, new Timestamp(System.currentTimeMillis()), MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumIngang()));
        result.setDatumEinde(MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumEinde()));
        result.setIndicatieGeblokkeerd(groepInhoud.getGeblokkeerd());
        return result;
    }

}
