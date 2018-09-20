/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie;

import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;

/**
 * Deze abstracte class geeft invulling aan de te volgen strategie voor het mappen van BrpGroepen uit het migratie model
 * op de entiteiten uit het operationele BRP gegevensmodel.
 * 
 * @param <T>
 *            het type inhoud van de BrpGroep
 * @param <E>
 *            Het type van de top level entiteit (bijv. Persoon of PersoonNationaliteit)
 */
public abstract class AbstractMapperStrategie<T extends BrpGroepInhoud, E extends Object> implements MapperStrategie<T, E> {

    private final StamtabelMapping stamtabelMapping;
    private final OnderzoekMapper onderzoekMapper;

    /**
     * Maakt een AbstractMapperStrategie object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param onderzoekMapper
     *            de mapper voor onderzoeken
     */
    public AbstractMapperStrategie(final DynamischeStamtabelRepository dynamischeStamtabelRepository, final OnderzoekMapper onderzoekMapper) {
        super();
        stamtabelMapping = new DynamischeStamtabelRepositoryAdapter(dynamischeStamtabelRepository);
        this.onderzoekMapper = onderzoekMapper;
    }

    /**
     * Geef de waarde van stamtabel mapping.
     *
     * @return de stamtabel mapping die gebruikt kan worden om stamtabellen te bevragen
     */
    public final StamtabelMapping getStamtabelMapping() {
        return stamtabelMapping;
    }

    /**
     * Geef de waarde van onderzoek mapper.
     *
     * @return de onderzoek mapper
     */
    public final OnderzoekMapper getOnderzoekMapper() {
        return onderzoekMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void mapVanMigratie(final BrpStapel<T> brpStapel, final E entiteit) {
        if (entiteit == null) {
            throw new NullPointerException();
        }
        if (brpStapel == null) {
            return;
        }
        mapActueleGegevens(brpStapel, entiteit);
        mapHistorischeGegevens(brpStapel, entiteit);
    }

    /**
     * Mapped de historische gegevens uit de stapel op de entiteit.
     * 
     * @param brpStapel
     *            de BRP stapel
     * @param entiteit
     *            de entiteit waarop de actuele gegevens gemapped moeten worden
     */
    protected abstract void mapHistorischeGegevens(BrpStapel<T> brpStapel, E entiteit);

    /**
     * Mapped de actuele gegevens uit de stapel op de entiteit.
     * 
     * @param brpStapel
     *            de BRP stapel
     * @param entiteit
     *            de entiteit waarop de actuele gegevens gemapped moeten worden
     */
    protected abstract void mapActueleGegevens(final BrpStapel<T> brpStapel, final E entiteit);

}
