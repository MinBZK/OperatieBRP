/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.notificator.service.impl;

import java.util.List;
import net.sf.cglib.core.CollectionUtils;
import nl.bzk.brp.serialisatie.notificator.service.IdentifierService;
import nl.bzk.brp.serialisatie.notificator.transformers.IntegerTransformer;
import org.springframework.stereotype.Service;

/**
 * Implementatie van de IdentifierService interface.
 */
@Service
public class IdentifierServiceImpl implements IdentifierService {

    @Override
    public final List<Integer> converteerLijstString(final List<String> cliLijst) {
        return (List<Integer>) CollectionUtils.transform(cliLijst, new IntegerTransformer());
    }
}
