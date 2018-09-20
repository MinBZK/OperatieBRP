/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.app.ContextParameterNames;
import nl.bzk.brp.dataaccess.repository.PersoonHisVolledigRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.util.DatumTijdUtil;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class HaalPersonenOpStap extends AbstractAsynchroonStap {

    private static final Logger LOGGER = LoggerFactory.getLogger(HaalPersonenOpStap.class);

    @Inject
    private PersoonHisVolledigRepository hisVolledigRepository;

    @Override
    public boolean doExecute(final Context context) throws Exception {
        List<Integer> bsns = (List) context.get(ContextParameterNames.BSNLIJST);
        List<Persoon> personenLijst = getPersonenLijst(bsns);

        context.put(ContextParameterNames.PERSONENLIJST, personenLijst);

        return CONTINUE_PROCESSING;
    }

    /**
     * Haalt de personen op uit de database.
     * @param bsns de lijst met BSNs
     * @return een lijst personen
     */
    private List<Persoon> getPersonenLijst(final List<Integer> bsns) {
        List<Persoon> personenLijst = new ArrayList<Persoon>(bsns.size());

        DatumTijdAttribuut nu = DatumTijdUtil.nu();
        for (Integer bsn : bsns) {
            LOGGER.info("ophalen persoon met BSN '{}'", bsn);

            PersoonHisVolledig persoon = hisVolledigRepository.findByBurgerservicenummer(new BurgerservicenummerAttribuut(bsn.toString()));

            if (null != persoon) {
                personenLijst.add(new PersoonView(persoon, nu));
            }
        }
        return personenLijst;
    }

}
