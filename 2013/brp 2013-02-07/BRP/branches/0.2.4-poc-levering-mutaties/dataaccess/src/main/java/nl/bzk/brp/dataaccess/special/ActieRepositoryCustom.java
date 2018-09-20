/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.special;

import java.util.List;

import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;

public interface ActieRepositoryCustom {

    public List<ActieModel> findByTijdstipVerwerkingMutatieIsNull();

    public List<String> bepaalBetrokkenBsnsVanActie(final Long actieId);

    public List<String> bepaalBetrokkenBsnsVanEerdereOnverwerkteActies(final DatumTijd datumTijd);

    boolean lockTijdstipVerwerkingMutatie(final long id);
}
