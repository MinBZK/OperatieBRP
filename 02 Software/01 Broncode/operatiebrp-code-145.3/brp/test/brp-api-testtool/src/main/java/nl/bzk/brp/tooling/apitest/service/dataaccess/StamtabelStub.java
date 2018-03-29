/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.dataaccess;

/**
 * Stamtabel stub.
 */
@FunctionalInterface
public interface StamtabelStub {

    /**
     * Methode om stamtabellen te muteren vanuit de API test.
     * @param stamtabel de stamtabel
     * @param gegeven het gegeven
     * @param attribuut het attribuut binnen het stamgegeven
     * @param waarde de nieuwe waarde
     */
    void pasStamtabelGegevenAttribuutAanMetWaarde(String stamtabel, String gegeven, String attribuut, String waarde);
}
