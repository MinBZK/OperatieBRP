/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaat;

import java.util.Set;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.MaterieelVerantwoordbaar;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import org.apache.commons.collections.Predicate;

/**
 * Dit predikaat filtert voorkomens die aangemaakt of gewijzigd zijn door een bepaalde set van acties.
 * Dit predikaat wordt bijvoorbeeld gebruikt om voor een bepaalde handeling X alle voorkomens die na deze handeling zijn aangepast of aangemaakt te
 * filteren. Zie hiervoor regel VR00097.
 *
 * @brp.bedrijfsregel VR00097
 */
@Regels(Regel.VR00097)
public final class NietRelevanteActiesPredikaat implements Predicate {

    private final Set<Long> uitgeslotenActieIds;

    /**
     * Constructor.
     * @param uitgeslotenActieIds de acties die uitgesloten worden van de view.
     */
    public NietRelevanteActiesPredikaat(final Set<Long> uitgeslotenActieIds) {
        this.uitgeslotenActieIds = uitgeslotenActieIds;
    }

    @Override
    public boolean evaluate(final Object object) {
        boolean resultaat = true;
        if (object instanceof FormeelVerantwoordbaar) {
            final FormeelVerantwoordbaar<ActieModel> formeelVerantwoordbaar = (FormeelVerantwoordbaar<ActieModel>) object;
            if (uitgeslotenActieIds.contains(formeelVerantwoordbaar.getVerantwoordingInhoud().getID())) {
                resultaat = false;
            }
            if (formeelVerantwoordbaar instanceof MaterieelVerantwoordbaar) {
                final MaterieelVerantwoordbaar<ActieModel> materieelVerantwoordbaar = (MaterieelVerantwoordbaar<ActieModel>) formeelVerantwoordbaar;
                if (materieelVerantwoordbaar.getVerantwoordingAanpassingGeldigheid() != null
                    && uitgeslotenActieIds.contains(materieelVerantwoordbaar.getVerantwoordingAanpassingGeldigheid().getID()))
                {
                    resultaat = false;
                }
            }
        }
        return resultaat;
    }
}
