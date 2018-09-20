/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie;

import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Betrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Relatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortBetrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;

/**
 * Deze abstracte class geeft invulling aan de te volgen strategie voor het mappen van historische BrpGroepen uit het
 * migratie model op de Persoon-entiteit voor groepen die ook op niet-ingeschreven personen worden gebruikt.
 *
 * @param <T>
 *            het type inhoud van de BrpGroep
 * @param <H>
 *            Het type van de BrpDatabaseGroep
 */
public abstract class AbstractNietIngeschrevenPersoonHistorieMapperStrategie<T extends BrpGroepInhoud, H extends FormeleHistorie> extends
        AbstractPersoonHistorieMapperStrategie<T, H>
{
    /**
     * Maakt een AbstractNietIngeschrevenPersoonHistorieMapperStrategie object.
     *
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper
     *            de mapper voor onderzoeken
     */
    public AbstractNietIngeschrevenPersoonHistorieMapperStrategie(final DynamischeStamtabelRepository dynamischeStamtabelRepository,
                                                                  final BRPActieFactory brpActieFactory,
                                                                  final OnderzoekMapper onderzoekMapper)
    {
        super(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
    }

    /**
     * Map onderzoek voor een ingeschreven of niet-ingeschreven persoon door afhankelijk van de soort persoon, soort
     * relatie en soort betrokkenheid de juist detail methode aan te roepen.
     * 
     * @param persoon
     *            de persoon waarvoor onderzoek gemapped moet worden
     * @param groepInhoud
     *            de migratie groep met de bron gegevens
     * @param historie
     *            de historie entiteit waaraan onderzoek gegevens gekoppeld moeten worden
     */
    protected final void mapOnderzoek(final Persoon persoon, final T groepInhoud, final H historie) {
        if (persoon.getSoortPersoon().equals(SoortPersoon.INGESCHREVENE)) {
            mapOnderzoekPersoon(groepInhoud, historie);
        } else if (persoon.getSoortPersoon().equals(SoortPersoon.ONBEKEND)) {
            // Een niet-ingeschrevene heeft per definitie 1 relatie
            final Relatie relatie = persoon.getRelaties().iterator().next();
            switch (relatie.getSoortRelatie()) {
                case FAMILIERECHTELIJKE_BETREKKING:
                    final Betrokkenheid betrokkenheid = persoon.getBetrokkenheidSet().iterator().next();
                    if (betrokkenheid.getSoortBetrokkenheid().equals(SoortBetrokkenheid.KIND)) {
                        mapOnderzoekKind(groepInhoud, historie);
                    } else if (betrokkenheid.getSoortBetrokkenheid().equals(SoortBetrokkenheid.OUDER)) {
                        mapOnderzoekOuder(groepInhoud, historie);
                    } else {
                        throw new IllegalStateException("Ongeldige soort betrokkenheid bij familierechtelijke betrekking");
                    }
                    break;
                case HUWELIJK:
                    mapOnderzoekHuwelijkspartner(groepInhoud, historie);
                    break;
                case GEREGISTREERD_PARTNERSCHAP:
                    mapOnderzoekGeregistreerdPartner(groepInhoud, historie);
                    break;
                default:
                    break;
            }
        } else {
            throw new IllegalStateException("Onbekende soort persoon");
        }
    }

    /**
     * De detail methode voor het mappen van onderzoek op de primaire (ingeschreven) persoon.
     *
     * @param groepInhoud
     *            de migratie groep met de bron gegevens
     * @param historie
     *            de historie entiteit waaraan onderzoek gegevens gekoppeld moeten worden
     */
    protected abstract void mapOnderzoekPersoon(T groepInhoud, H historie);

    /**
     * De detail methode voor het mappen van onderzoek op een gerelateerd niet-ingeschreven kind.
     *
     * @param groepInhoud
     *            de migratie groep met de bron gegevens
     * @param historie
     *            de historie entiteit waaraan onderzoek gegevens gekoppeld moeten worden
     */
    protected abstract void mapOnderzoekKind(T groepInhoud, H historie);

    /**
     * De detail methode voor het mappen van onderzoek op een gerelateerde niet-ingeschreven ouder.
     *
     * @param groepInhoud
     *            de migratie groep met de bron gegevens
     * @param historie
     *            de historie entiteit waaraan onderzoek gegevens gekoppeld moeten worden
     */
    protected abstract void mapOnderzoekOuder(T groepInhoud, H historie);

    /**
     * De detail methode voor het mappen van onderzoek op een gerelateerde niet-ingeschreven huwelijkspartner.
     *
     * @param groepInhoud
     *            de migratie groep met de bron gegevens
     * @param historie
     *            de historie entiteit waaraan onderzoek gegevens gekoppeld moeten worden
     */
    protected abstract void mapOnderzoekHuwelijkspartner(T groepInhoud, H historie);

    /**
     * De detail methode voor het mappen van onderzoek op een gerelateerde niet-ingeschreven geregistreerde partner.
     *
     * @param groepInhoud
     *            de migratie groep met de bron gegevens
     * @param historie
     *            de historie entiteit waaraan onderzoek gegevens gekoppeld moeten worden
     */
    protected abstract void mapOnderzoekGeregistreerdPartner(T groepInhoud, H historie);
}
