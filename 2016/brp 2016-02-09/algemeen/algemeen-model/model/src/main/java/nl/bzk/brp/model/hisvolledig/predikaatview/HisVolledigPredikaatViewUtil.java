/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;


/**
 * Utility/Helper klasse die methodes biedt ten behoeve van de predikaat views. Zo biedt deze klasse o.a. een methode om te bepalen of een Predikaat View
 * een bepaalde groep altijd moet tonen.
 */
public final class HisVolledigPredikaatViewUtil {

    /**
     * Een Identificerende groep is een voorkomen van een groep dat altijd geleverd wordt zodat de Afnemer in staat is om vast te stellen welke Persoon het
     * betreft.
     */
    @Regels(Regel.BRLV0048)
    private static final Set<Class<? extends Groep>> ALTIJD_TONEN_GROEPEN =
        new HashSet<Class<? extends Groep>>(
            Arrays.asList(
                HisPersoonGeslachtsaanduidingModel.class,
                HisPersoonGeboorteModel.class,
                HisPersoonIdentificatienummersModel.class,
                HisPersoonSamengesteldeNaamModel.class));

    /**
     * Standaard lege en private constructor om te voorkomen dat deze utility/helper klasse wordt geinstantieerd.
     */
    private HisVolledigPredikaatViewUtil() {
    }

    /**
     * Controleert of een groep een identificerende groep is voor mutatie levering.
     *
     * @param klass type van de groep om te checken
     * @return <code>true</code> als de gegeven groep altijd wordt getoond
     */
    @Regels(Regel.BRLV0048)
    public static boolean isAltijdTonenGroep(final Class<? extends Groep> klass) {
        return ALTIJD_TONEN_GROEPEN.contains(klass);
    }
}
