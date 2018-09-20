/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.bevraging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Named;
import nl.bzk.brp.business.regels.BerichtBedrijfsregel;
import nl.bzk.brp.business.regels.context.BerichtRegelContext;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.ber.BerichtZoekcriteriaPersoonGroepBericht;

/**
 * Implementatie van bedrijfsregel BRBV0001
 * <p/>
 * Van de zoekcriteria Burgerservicenummer, Administratienummer en Technische sleutel  moet er precies één zijn gevuld.
 * <p/>
 *
 * @brp.bedrijfsregel BRBV0001
 */
@Named("BRBV0001")
public class BRBV0001 implements BerichtBedrijfsregel<BerichtRegelContext> {

    @Override
    public final Regel getRegel() {
        return Regel.BRBV0001;
    }

    @Override
    public List<BerichtIdentificeerbaar> valideer(final BerichtRegelContext regelContext) {
        final List<BerichtIdentificeerbaar> objectenDieDeRegelOvertreden = new ArrayList<>();

        final BerichtZoekcriteriaPersoonGroepBericht zoekcriteria = regelContext.getBericht().getZoekcriteriaPersoon();
        if (!preciesEenKeerNotNull(zoekcriteria.getBurgerservicenummer(),
                zoekcriteria.getAdministratienummer(),
                zoekcriteria.getObjectSleutel()))
        {
            objectenDieDeRegelOvertreden.add(zoekcriteria);
        }

        return objectenDieDeRegelOvertreden;
    }

    @Override
    public Class<BerichtRegelContext> getContextType() {
        return BerichtRegelContext.class;
    }

    /**
     * Controleer of precies één van de parameters niet null is.
     *
     * @param param1 waarde die gecontroleerd moet worden
     * @param param2 waarde die gecontroleerd moet worden
     * @param param3 waarde die gecontroleerd moet worden
     * @return true wanneer er precies één parameter not null is, anders false
     */
    public final boolean preciesEenKeerNotNull(final Object param1, final Object param2, final Object param3) {
        int count = 0;
        for (Object object : Arrays.asList(param1, param2, param3)) {
            if (object != null) {
                count++;
            }
        }
        return count == 1;
    }

}
