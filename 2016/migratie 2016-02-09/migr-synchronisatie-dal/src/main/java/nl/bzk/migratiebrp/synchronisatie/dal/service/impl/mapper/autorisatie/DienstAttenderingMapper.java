/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import java.util.HashSet;
import java.util.Set;

import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstAttenderingInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Dienst;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.DienstAttenderingHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;

/**
 * Mapper verantwoordelijk voor het mappen van het BRP model naar een Dienst Attendering entiteit.
 */
// @formatter:off
public final class DienstAttenderingMapper extends
    AbstractHistorieMapperStrategie<BrpDienstAttenderingInhoud, DienstAttenderingHistorie, Dienst>
{
    // @formatter:on

    /**
     * Constructor.
     *
     * @param dynamischeStamtabelRepository
     *            dynamische stamtabel repository om oa partijen te bevragen
     * @param brpActieFactory
     *            actie factory
     */
    public DienstAttenderingMapper(final DynamischeStamtabelRepository dynamischeStamtabelRepository, final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory, null);
    }

    @Override
    protected void mapActueleGegevens(final BrpStapel<BrpDienstAttenderingInhoud> brpStapel, final Dienst entiteit) {
        // Niet nodig voor dit object.
    }

    @Override
    protected void voegHistorieToeAanEntiteit(final DienstAttenderingHistorie historie, final Dienst entiteit) {
        Set<DienstAttenderingHistorie> historieSet = entiteit.getDienstAttenderingHistorieSet();
        if (historieSet == null) {
            historieSet = new HashSet<>();
            entiteit.setDienstAttenderingHistorieSet(historieSet);
        }
        historieSet.add(historie);
    }

    @Override
    protected void kopieerActueleGroepNaarEntiteit(final DienstAttenderingHistorie historie, final Dienst entiteit) {
        if (historie != null) {
            entiteit.setAttenderingscriterium(historie.getAttenderingscriterium());
        }
    }

    @Override
    protected DienstAttenderingHistorie mapHistorischeGroep(final BrpDienstAttenderingInhoud groepInhoud, final Dienst entiteit) {
        final DienstAttenderingHistorie result = new DienstAttenderingHistorie(entiteit);
        result.setAttenderingscriterium(groepInhoud.getAttenderingscriterium());
        return result;
    }

}
