/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.FormeleHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.MaterieleHistorie;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;

/**
 * Deze abstracte class geeft invulling aan de te volgen strategie voor het mappen van historische BrpGroepen uit het
 * migratie model op de entiteiten uit het operationele BRP gegevensmodel.
 * 
 * @param <T>
 *            het type inhoud van de BrpGroep
 * @param <H>
 *            Het type van de BrpDatabaseGroep
 * @param <E>
 *            Het type van de top level entiteit (bijv. Persoon of PersoonNationaliteit)
 */
public abstract class AbstractHistorieMapperStrategie<T extends BrpGroepInhoud, H extends FormeleHistorie, E extends Object>
        implements HistorieMapperStrategie<T, E> {

    private final StamtabelMapping stamtabelMapping;
    private final BRPActieFactory brpActieFactory;

    /**
     * Maakt een AbstractHistorieMapperStrategie object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public AbstractHistorieMapperStrategie(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        this.stamtabelMapping = new DynamischeStamtabelRepositoryAdapter(dynamischeStamtabelRepository);
        this.brpActieFactory = brpActieFactory;
    }

    /**
     * @return de stamtabel mapping die gebruikt kan worden om stamtabellen te bevragen
     */
    public final StamtabelMapping getStamtabelMapping() {
        return stamtabelMapping;
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

    private void mapHistorischeGegevens(final BrpStapel<T> brpStapel, final E entiteit) {
        BrpGroep<T> teMappenGroep = brpStapel.getMeestRecenteElement();
        boolean isTeMappenGroepActueel = true;

        while (teMappenGroep != null) {
            final H historie = mapHistorischeGroep(teMappenGroep.getInhoud());
            mapActieEnHistorie(teMappenGroep, historie);
            voegHistorieToeAanEntiteit(historie, entiteit);

            if (isTeMappenGroepActueel) {
                kopieerActueleGroepNaarEntiteit(historie, entiteit);
                isTeMappenGroepActueel = false;
            }
            teMappenGroep = brpStapel.getVorigElement(teMappenGroep);
        }
    }

    private void mapActieEnHistorie(final BrpGroep<T> teMappenGroep, final H historie) {
        if (historie instanceof MaterieleHistorie) {
            mapActieEnHistorieVanMigratie(teMappenGroep, (MaterieleHistorie) historie);
        } else {
            mapActieEnFormeleHistorieVanMigratie(teMappenGroep, historie);
        }
    }

    /**
     * Mapped de actie en materiële historie attributen van het BRP migratie model op de BRP entiteiten.
     * 
     * @param brpGroep
     *            de BrpGroep uit het migratie model
     * @param historie
     *            de materiële historie als BRP entiteit
     */
    private void mapActieEnHistorieVanMigratie(final BrpGroep<?> brpGroep, final MaterieleHistorie historie) {
        historie.setActieAanpassingGeldigheid(brpActieFactory.getBRPActie(brpGroep.getActieGeldigheid()));
        historie.setActieInhoud(brpActieFactory.getBRPActie(brpGroep.getActieInhoud()));
        historie.setActieVerval(brpActieFactory.getBRPActie(brpGroep.getActieVerval()));

        MapperUtil.mapHistorieVanMigratie(brpGroep.getHistorie(), historie);
    }

    /**
     * Mapped de actie en materiële historie attributen van het BRP migratie model op de BRP entiteiten.
     * 
     * @param brpGroep
     *            de BrpGroep uit het migratie model
     * @param historie
     *            de formele historie als BRP entiteit
     */
    private void mapActieEnFormeleHistorieVanMigratie(final BrpGroep<?> brpGroep, final FormeleHistorie historie) {
        historie.setActieInhoud(brpActieFactory.getBRPActie(brpGroep.getActieInhoud()));
        historie.setActieVerval(brpActieFactory.getBRPActie(brpGroep.getActieVerval()));

        MapperUtil.mapFormeleHistorieVanMigratie(brpGroep.getHistorie(), historie);
    }

    /**
     * Mapped de actuele gegevens uit de stapel op de entiteit.
     * 
     * @param brpStapel
     *            de BRP stapel
     * @param entiteit
     *            de entiteit waarop de actuele gegevens gemapped moeten worden
     */
    protected abstract void mapActueleGegevens(final BrpStapel<T> brpStapel, final E entiteit);

    /**
     * Voegt de historie toe aan de meegegeven persoon door de concrete methode aanroep op persoon uit te voeren.
     * 
     * @param historie
     *            de historie entiteit
     * @param entiteit
     *            de entiteit
     */
    protected abstract void voegHistorieToeAanEntiteit(final H historie, final E entiteit);

    /**
     * Kopieert de waarden uit de actuele historische groep naar de entiteit. Note: mogelijk wordt deze functionaliteit
     * overbodig door het gebruik van triggers in de BRP database.
     * 
     * @param historie
     *            de historie waarvan de gegevens naar de persoon moeten worden gekopieerd, mag niet null zijn
     * @param entiteit
     *            de entiteit waarvan de acutele gegevens worden gevuld, mag geen null zijn
     */
    protected abstract void kopieerActueleGroepNaarEntiteit(final H historie, final E entiteit);

    /**
     * Mapped de meegegeven BrpGroep inhoud uit het migratie model naar de BRP entiteit.
     * 
     * @param groepInhoud
     *            de BrpGroep-inhoud uit het migratie model, mag niet null zijn
     * @return de BRP entiteit, mag geen null teruggeven
     */
    protected abstract H mapHistorischeGroep(final T groepInhoud);
}
