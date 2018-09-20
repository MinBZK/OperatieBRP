/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.context;

import nl.bzk.brp.business.regels.logging.RegelLoggingUtil;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * De abstracte RegelContext waarin de basis wordt gelegd voor de concrete RegelContext klassen.
 */
public abstract class AbstractRegelContext implements RegelContext {

    /**
     * Geeft de situatie waarin de te valideren situatie zich bevindt, dit kan varieren per regelcontext. Soms is dit
     * de huidige situatie, maar kan ook de nieuwe situatie zijn. Afhankelijk daarvan kan het type verschillen.
     *
     * @return the situatie
     */
    public abstract BrpObject getSituatie();

    /**
     * Geeft de prefix voor de logmeldingen die een fout aangeven.
     * Override deze methode in specifieke contexten voor de gewenste prefix.
     *
     * @return de prefix voor een logmelding uit deze context
     */
    protected String getPrefixLogmeldingFout() {
        return RegelLoggingUtil.PREFIX_LOGMELDING_FOUT;
    }

    /**
     * Geeft de prefix voor de logmeldingen die een succes aangeven.
     * Override deze methode in specifieke contexten voor de gewenste prefix.
     *
     * @return de prefix voor een logmelding uit deze context
     */
    protected String getPrefixLogmeldingSucces() {
        return RegelLoggingUtil.PREFIX_LOGMELDING_SUCCES;
    }

    /**
     * Geeft een logmelding voor een geslaagde regel.
     *
     * @param regel de regel die is geslaagd
     * @return tekst voor de logmelding
     */
    public final String geefLogmeldingSucces(final Regel regel) {
        return RegelLoggingUtil.geefLogmeldingSucces(getPrefixLogmeldingSucces(), regel);
    }

    /**
     * Geeft een logmelding voor een overtreden regel.
     *
     * @param regel de regel die is overtreden
     * @return tekst voor de logmelding
     */
    public final String geefLogmeldingFout(final Regel regel) {
        return RegelLoggingUtil.geefLogmeldingFout(getPrefixLogmeldingFout(), regel, getSituatie());
    }

}
