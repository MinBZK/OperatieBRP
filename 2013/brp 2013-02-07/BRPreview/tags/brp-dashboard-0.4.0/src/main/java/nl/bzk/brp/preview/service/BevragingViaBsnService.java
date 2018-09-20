/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.service;

import java.util.List;

import nl.bzk.brp.model.data.kern.Pers;
import nl.bzk.brp.model.data.kern.Persnation;


/**
 * Interface voor het zoeken naar BSN
 *
 */
public interface BevragingViaBsnService {

    Pers findPersonByBsn(String bsn);

    List<Persnation> zoekNationaliteitenOpPersoon(Pers persoon);
}
