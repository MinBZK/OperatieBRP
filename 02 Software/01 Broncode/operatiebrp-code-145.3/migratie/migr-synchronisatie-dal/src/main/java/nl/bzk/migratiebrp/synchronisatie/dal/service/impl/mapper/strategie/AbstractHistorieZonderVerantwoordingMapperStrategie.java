/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;

/**
 * Deze abstracte class geeft invulling aan de te volgen strategie voor het mappen van historische BrpGroepen uit het
 * migratie model op de entiteiten uit het operationele BRP gegevensmodel; maar dan zonder acties om te zetten.
 * @param <T> het type inhoud van de BrpGroep
 * @param <H> Het type van de BrpDatabaseGroep
 * @param <E> Het type van de top level entiteit (bijv. Persoon of PersoonNationaliteit)
 */
public abstract class AbstractHistorieZonderVerantwoordingMapperStrategie<T extends BrpGroepInhoud, H extends FormeleHistorie, E>
        extends AbstractMapperStrategie<T, E> {

    /**
     * Maakt een AbstractHistorieMapperStrategie object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param onderzoekMapper de mapper voor onderzoeken
     */
    public AbstractHistorieZonderVerantwoordingMapperStrategie(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final OnderzoekMapper onderzoekMapper) {
        super(dynamischeStamtabelRepository, onderzoekMapper);
    }

    @Override
    
    protected final void mapHistorischeGegevens(final BrpStapel<T> brpStapel, final E entiteit, final Element objecttype) {
        final BrpGroep<T> brpGroepActueel = brpStapel.bevatActueel() ? brpStapel.getActueel() : null;

        // map historische groepen
        for (final BrpGroep<T> brpGroep : brpStapel) {
            final H historie = mapHistorischeGroep(brpGroep.getInhoud(), entiteit);
            mapActieEnHistorie(brpGroep, historie, objecttype);
            voegHistorieToeAanEntiteit(historie, entiteit);

            // map actuele groep als deze bestaat
            if (brpGroep == brpGroepActueel) {
                kopieerActueleGroepNaarEntiteit(historie, entiteit);
            }
        }
    }

    private void mapActieEnHistorie(final BrpGroep<T> teMappenGroep, final H historie, final Element objecttype) {
        if (historie instanceof MaterieleHistorie) {
            MapperUtil.mapHistorieVanMigratie(teMappenGroep.getHistorie(), (MaterieleHistorie) historie, getOnderzoekMapper(), objecttype);
        } else {
            MapperUtil.mapFormeleHistorieVanMigratie(teMappenGroep.getHistorie(), historie, getOnderzoekMapper(), objecttype);
        }
    }

    /**
     * Voegt de historie toe aan de meegegeven persoon door de concrete methode aanroep op persoon uit te voeren.
     * @param historie de historie entiteit
     * @param entiteit de entiteit
     */
    protected abstract void voegHistorieToeAanEntiteit(H historie, E entiteit);

    /**
     * Kopieert de waarden uit de actuele historische groep naar de entiteit. Note: mogelijk wordt deze functionaliteit
     * overbodig door het gebruik van triggers in de BRP database.
     * @param historie de historie waarvan de gegevens naar de persoon moeten worden gekopieerd, mag niet null zijn
     * @param entiteit de entiteit waarvan de acutele gegevens worden gevuld, mag geen null zijn
     */
    protected abstract void kopieerActueleGroepNaarEntiteit(H historie, E entiteit);

    /**
     * Mapped de meegegeven BrpGroep inhoud uit het migratie model naar de BRP entiteit.
     * @param groepInhoud de BrpGroep-inhoud uit het migratie model, mag niet null zijn
     * @param entiteit de entiteit met actuele gegevens waaraan de historische groep wordt gekoppeld, mag geen null zijn
     * @return de BRP entiteit, mag geen null teruggeven
     */
    protected abstract H mapHistorischeGroep(T groepInhoud, E entiteit);
}
