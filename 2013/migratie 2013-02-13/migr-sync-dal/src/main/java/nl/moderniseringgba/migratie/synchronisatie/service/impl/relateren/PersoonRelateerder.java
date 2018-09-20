/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.relateren;

import java.math.BigDecimal;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;

/**
 * Definieert de functionaliteit die nodig is voor het relateren van personen.
 */
public interface PersoonRelateerder {

    /**
     * TODO Nu wordt adhv het administratienummer een persoon in de db opgezocht en gekoppeld aan de betrokkenheid als
     * deze wordt gevonden. Wordt er geen persoon voor dit administratienummer gevonden dan wordt de gerelateerde als
     * nieuw persoon opgeslagen zoals deze is geconverteerd vanuit LO3.
     * 
     * Als <code>aNummerIstPersoon</code> is gevuld dan wordt dit aNummer gebruikt om een bestaand persoon op te zoeken
     * en te vervangen (aNummer wijziging). Is deze parameter leeg dan wordt het aNummer van de meegegeven persoon
     * gebruikt.
     * 
     * @param persoon
     *            de persoon waarvan de SOLL situatie moet worden bepaald, mag niet null zijn
     * @param aNummerIstPersoon
     *            het a-nummer van de bestaande persoon die moet worden vervangen, mag null zijn
     * @return de persoon die de SOLL situatie is
     */
    Persoon updateRelatiesVanPersoon(final Persoon persoon, final BigDecimal aNummerIstPersoon);
}
