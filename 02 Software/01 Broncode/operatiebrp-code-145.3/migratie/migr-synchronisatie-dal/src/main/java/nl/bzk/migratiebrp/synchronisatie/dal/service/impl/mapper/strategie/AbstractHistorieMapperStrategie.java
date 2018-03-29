/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;

/**
 * Deze abstracte class geeft invulling aan de te volgen strategie voor het mappen van historische BrpGroepen uit het
 * migratie model op de entiteiten uit het operationele BRP gegevensmodel.
 * @param <T> het type inhoud van de BrpGroep
 * @param <H> Het type van de BrpDatabaseGroep
 * @param <E> Het type van de top level entiteit (bijv. Persoon of PersoonNationaliteit)
 */
// @formatter:off
public abstract class AbstractHistorieMapperStrategie<T extends BrpGroepInhoud, H extends FormeleHistorieZonderVerantwoording, E>
        extends AbstractMapperStrategie<T, E> {
    // @formatter:on

    private final BRPActieFactory brpActieFactory;

    /**
     * Maakt een AbstractHistorieMapperStrategie object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper de mapper voor onderzoeken
     */
    public AbstractHistorieMapperStrategie(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory,
            final OnderzoekMapper onderzoekMapper) {
        super(dynamischeStamtabelRepository, onderzoekMapper);
        this.brpActieFactory = brpActieFactory;
    }

    @Override
    
    protected final void mapHistorischeGegevens(final BrpStapel<T> brpStapel, final E entiteit, final Element objecttype) {
        // map historische groepen
        for (final BrpGroep<T> brpGroep : brpStapel) {
            final H historie = mapHistorischeGroep(brpGroep.getInhoud(), entiteit);
            mapActieEnHistorie(brpGroep, historie, objecttype);
            voegHistorieToeAanEntiteit(historie, entiteit);
        }
    }

    private void mapActieEnHistorie(final BrpGroep<T> teMappenGroep, final H historie, final Element objecttype) {
        if (historie instanceof MaterieleHistorie) {
            mapActieEnHistorieVanMigratie(teMappenGroep, (MaterieleHistorie) historie, objecttype);
        } else {
            mapActieEnFormeleHistorieVanMigratie(teMappenGroep, historie, objecttype);
        }
    }

    /**
     * Mapped de actie en materiële historie attributen van het BRP migratie model op de BRP entiteiten.
     * @param brpGroep de BrpGroep uit het migratie model
     * @param historie de materiële historie als BRP entiteit
     * @param objecttype het objecttype van de gerelateerde persoon. Null als het om de 'eigen' persoon gaat.
     */
    private void mapActieEnHistorieVanMigratie(final BrpGroep<?> brpGroep, final MaterieleHistorie historie, final Element objecttype) {
        historie.setActieAanpassingGeldigheid(brpActieFactory.getBRPActie(brpGroep.getActieGeldigheid()));
        historie.setActieInhoud(brpActieFactory.getBRPActie(brpGroep.getActieInhoud()));
        historie.setActieVerval(brpActieFactory.getBRPActie(brpGroep.getActieVerval()));

        MapperUtil.mapHistorieVanMigratie(brpGroep.getHistorie(), historie, getOnderzoekMapper(), objecttype);
    }

    /**
     * Mapped de actie en materiële historie attributen van het BRP migratie model op de BRP entiteiten.
     * @param brpGroep de BrpGroep uit het migratie model
     * @param historie de formele historie als BRP entiteit
     * @param objecttype het objecttype van de gerelateerde persoon. Null als het om de 'eigen' persoon gaat.
     */
    private void mapActieEnFormeleHistorieVanMigratie(final BrpGroep<?> brpGroep, final FormeleHistorieZonderVerantwoording historie,
                                                      final Element objecttype) {
        if (historie instanceof FormeleHistorie) {
            ((FormeleHistorie) historie).setActieInhoud(brpActieFactory.getBRPActie(brpGroep.getActieInhoud()));
            ((FormeleHistorie) historie).setActieVerval(brpActieFactory.getBRPActie(brpGroep.getActieVerval()));
        }

        MapperUtil.mapFormeleHistorieVanMigratie(brpGroep.getHistorie(), historie, getOnderzoekMapper(), objecttype);
    }

    /**
     * Voegt de historie toe aan de meegegeven persoon door de concrete methode aanroep op persoon uit te voeren.
     * @param historie de historie entiteit
     * @param entiteit de entiteit
     */
    protected abstract void voegHistorieToeAanEntiteit(H historie, E entiteit);

    /**
     * Mapped de meegegeven BrpGroep inhoud uit het migratie model naar de BRP entiteit.
     * @param groepInhoud de BrpGroep-inhoud uit het migratie model, mag niet null zijn
     * @param entiteit de entiteit met actuele gegevens waaraan de historische groep wordt gekoppeld, mag geen null zijn
     * @return de BRP entiteit, mag geen null teruggeven
     */
    protected abstract H mapHistorischeGroep(T groepInhoud, E entiteit);
}
