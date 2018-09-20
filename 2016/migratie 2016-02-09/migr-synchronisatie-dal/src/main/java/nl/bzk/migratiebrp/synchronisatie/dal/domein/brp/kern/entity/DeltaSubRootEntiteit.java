/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

/**
 * Interface waarmee aangegeven kan worden welke entiteit een subroot entiteit is voor het Delta bepalen. Een subroot
 * entiteit is bv {@link PersoonAdres}. Dit is voor BMR een A-laag object, maar voor Delta niet het hoogste niveau. Dat
 * is in dit voorbeeld {@link Persoon}.
 */
public interface DeltaSubRootEntiteit extends DeltaRootEntiteit {

    /**
     * Alle subroot entiteiten hebben als root-entiteit een {@link Persoon}. Door deze methode hebben we daar toegang
     * toe.
     * 
     * @return de {@link Persoon} die hoort bij deze subroot entiteit.
     */
    Persoon getPersoon();
}
