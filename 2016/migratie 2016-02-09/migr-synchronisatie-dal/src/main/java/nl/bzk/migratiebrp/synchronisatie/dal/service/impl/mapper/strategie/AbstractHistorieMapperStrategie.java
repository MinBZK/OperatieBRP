/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie;

import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.MaterieleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;

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
// @formatter:off
public abstract class AbstractHistorieMapperStrategie<T extends BrpGroepInhoud, H extends FormeleHistorie, E>
    extends AbstractMapperStrategie<T, E>
{
    // @formatter:on

    private final BRPActieFactory brpActieFactory;

    /**
     * Maakt een AbstractHistorieMapperStrategie object.
     *
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper
     *            de mapper voor onderzoeken
     */
    public AbstractHistorieMapperStrategie(
        final DynamischeStamtabelRepository dynamischeStamtabelRepository,
        final BRPActieFactory brpActieFactory,
        final OnderzoekMapper onderzoekMapper)
    {
        super(dynamischeStamtabelRepository, onderzoekMapper);
        this.brpActieFactory = brpActieFactory;
    }

    @Override
    @SuppressWarnings("PMD.CompareObjectsWithEquals" /* Vergelijking op specifiek object voorkomen. */)
    protected final void mapHistorischeGegevens(final BrpStapel<T> brpStapel, final E entiteit) {
        final BrpGroep<T> brpGroepActueel = brpStapel.bevatActueel() ? brpStapel.getActueel() : null;

        // map historische groepen
        for (final BrpGroep<T> brpGroep : brpStapel) {
            final H historie = mapHistorischeGroep(brpGroep.getInhoud(), entiteit);
            mapActieEnHistorie(brpGroep, historie);
            voegHistorieToeAanEntiteit(historie, entiteit);

            // map actuele groep als deze bestaat
            if (brpGroep == brpGroepActueel) {
                kopieerActueleGroepNaarEntiteit(historie, entiteit);
            }
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

        MapperUtil.mapHistorieVanMigratie(brpGroep.getHistorie(), historie, getOnderzoekMapper());
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

        MapperUtil.mapFormeleHistorieVanMigratie(brpGroep.getHistorie(), historie, getOnderzoekMapper());
    }

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
     * @param entiteit
     *            de entiteit met actuele gegevens waaraan de historische groep wordt gekoppeld, mag geen null zijn
     * @return de BRP entiteit, mag geen null teruggeven
     */
    protected abstract H mapHistorischeGroep(final T groepInhoud, final E entiteit);
}
