/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.bericht.request;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import nl.bzk.brp.business.regels.VoorBerichtRegel;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;

import org.apache.commons.lang.StringUtils;


/**
 * Dit is een business regel die geldt voor ELK bericht, gevalideerd moet worden dat een referentieID niet naar zichzelf
 * mag wijzen of naar een ander
 * object dat weer naar een ander object verwijst.
 * <p/>
 * Een request mag geen keten- of zelfverwijzing bevatten.
 *
 * @brp.bedrijfsregel BRBY9905
 */
@Named("BRBY9905")
public class BRBY9905 implements VoorBerichtRegel {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public final Regel getRegel() {
        return Regel.BRBY9905;
    }

    @Override
    public final List<BerichtIdentificeerbaar> voerRegelUit(final BerichtBericht bericht) {
        final List<BerichtIdentificeerbaar> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (null != bericht && null != bericht.getCommunicatieIdMap()) {
            final CommunicatieIdMap map = bericht.getCommunicatieIdMap();
            for (final String key : map.keySet()) {
                if (null != map.get(key)) {
                    for (final BerichtIdentificeerbaar ident : map.get(key)) {
                        if (ident instanceof BerichtEntiteit
                            && StringUtils.isNotBlank(((BerichtEntiteit) ident).getReferentieID()))
                        {
                            controleerReferentie(objectenDieDeRegelOvertreden, map, key, ident);
                        }
                    }
                }
            }
        }
        return objectenDieDeRegelOvertreden;
    }

    private void controleerReferentie(final List<BerichtIdentificeerbaar> objectenDieDeRegelOvertreden,
            final CommunicatieIdMap map, final String key, final BerichtIdentificeerbaar ident)
    {
        final String refId = ((BerichtEntiteit) ident).getReferentieID();
        final List<BerichtIdentificeerbaar> targets = map.get(refId);
        if (null != targets && 1 == targets.size()) {
            // Als deze null is, is dit een fout die al eerder gecontateerd,
            // we kunnen hier niks meer verifieren.
            // als deze duplicaten bevat, is dit ook al eerder gecontateerd, en kunen we OOK HIER
            // niets concluderen (want wat als 1 correct en 1 naar zichzelf refereert
            // en de laatste is een keten.
            // DUS ==> 0 or meer dan 1, NIET testen
            final BerichtIdentificeerbaar target = map.get(refId).get(0);
            if (null != target) {
                // OK, test nu of deze naar zichzelf verwijst
                if (target.equals(ident)) {
                    LOGGER.error("CommID [{}] Referentie ID [{}] refereert naar zichzelf. Object: {}", key, refId,
                            ident);
                    objectenDieDeRegelOvertreden.add(ident);
                } else if (target instanceof BerichtEntiteit
                    && StringUtils.isNotBlank(((BerichtEntiteit) target).getReferentieID()))
                {
                    // refereert dit object naar een ander object ?
                    LOGGER.error("CommID [{}] Referentie ID [{}] refereert naar Object: {} dat weer refereert naar {}",
                            key, refId, ident, ((BerichtEntiteit) target).getReferentieID());
                    objectenDieDeRegelOvertreden.add(ident);
                }
            }
        }
    }
}
