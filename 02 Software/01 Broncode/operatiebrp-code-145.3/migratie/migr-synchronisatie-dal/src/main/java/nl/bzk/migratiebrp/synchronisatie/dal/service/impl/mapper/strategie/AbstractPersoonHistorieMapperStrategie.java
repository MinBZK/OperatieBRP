/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;

/**
 * Deze abstracte class geeft invulling aan de te volgen strategie voor het mappen van historische BrpGroepen uit het
 * migratie model op de Persoon-entiteit.
 * @param <T> het type inhoud van de BrpGroep
 * @param <H> Het type van de BrpDatabaseGroep
 */
public abstract class AbstractPersoonHistorieMapperStrategie<T extends BrpGroepInhoud, H extends FormeleHistorie> extends
        AbstractHistorieMapperStrategie<T, H, Persoon> {

    /**
     * Maakt een AbstractPersoonHistorieMapperStrategie object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper de mapper voor onderzoeken
     */
    public AbstractPersoonHistorieMapperStrategie(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory,
            final OnderzoekMapper onderzoekMapper) {
        super(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
    }

}
