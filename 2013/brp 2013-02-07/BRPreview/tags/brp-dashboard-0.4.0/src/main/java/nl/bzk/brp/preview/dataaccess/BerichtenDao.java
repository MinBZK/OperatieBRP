/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess;

import java.util.Calendar;
import java.util.List;

import nl.bzk.brp.preview.model.Bericht;


public interface BerichtenDao {

    public List<Bericht> getAlleBerichten();

    public List<Bericht> getBerichtenOpBsn(Integer bsn);

    public List<Bericht> getBerichtenVanaf(Calendar vanaf);

    public void opslaan(Bericht bericht);

}
