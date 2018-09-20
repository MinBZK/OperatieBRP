/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.bzk.brp.bevraging.domein.repository;

import nl.bzk.brp.bevraging.domein.Persoon;

/**
 *
 * @author emalotau
 */
public interface PersoonRepository {
    Persoon zoekOpBSN(Integer bsn);
    void create(Persoon persoon);
}
