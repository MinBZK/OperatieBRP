/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

/**
 * Interface waarmee aangegeven kan worden welke entiteit een subroot entiteit is voor het Delta
 * bepalen. Een subroot entiteit is bv {@link PersoonAdres}. Dit is voor BMR een A-laag object, maar
 * voor Delta niet het hoogste niveau. Dat is in dit voorbeeld {@link Persoon}.
 */
public interface SubRootEntiteit extends Afleidbaar, RootEntiteit {

    /**
     * Geef de waarde van persoon van DeltaSubRootEntiteit.
     *
     * @return de waarde van persoon van DeltaSubRootEntiteit
     */
    Persoon getPersoon();
}
