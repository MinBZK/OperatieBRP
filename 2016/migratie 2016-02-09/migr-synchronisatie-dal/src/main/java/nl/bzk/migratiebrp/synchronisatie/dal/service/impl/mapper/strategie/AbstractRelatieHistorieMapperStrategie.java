/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie;

import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Relatie;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;

/**
 * Deze abstracte class geeft invulling aan de te volgen strategie voor het mappen van historische BrpGroepen uit het
 * migratie model op de Relatie-entiteit.
 * 
 * @param <T>
 *            het type inhoud van de BrpGroep
 * @param <H>
 *            Het type van de BrpDatabaseGroep
 */
public abstract class AbstractRelatieHistorieMapperStrategie<T extends BrpGroepInhoud, H extends FormeleHistorie> extends
        AbstractHistorieMapperStrategie<T, H, Relatie>
{

    /**
     * Maakt een AbstractPersoonHistorieMapperStrategie object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public AbstractRelatieHistorieMapperStrategie(final DynamischeStamtabelRepository dynamischeStamtabelRepository, final BRPActieFactory brpActieFactory)
    {
        super(dynamischeStamtabelRepository, brpActieFactory, null);
    }

    /**
     * Voor RelatieHistorie mappers worden geen actuele gegevens uit de stapel gekopieerd. Deze implementatie doet dus
     * niets.
     * 
     * @param brpStapel
     *            de BRP stapel
     * @param entiteit
     *            de entiteit waarop de actuele gegevens gemapped moeten worden
     */
    @Override
    protected final void mapActueleGegevens(final BrpStapel<T> brpStapel, final Relatie entiteit) {
    }
}
