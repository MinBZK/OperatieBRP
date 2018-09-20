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
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HaalPersonenOpStap extends AbstractAsynchroonStap {

    private static final Logger LOGGER = LoggerFactory.getLogger(HaalPersonenOpStap.class);

    @Inject
    private PersoonRepository persoonRepository;

    @Override
    public boolean doExecute(final Context context) throws Exception {
        List<Integer> bsns = (List) context.get(ContextParameterNames.BSNLIJST);
        List<PersoonModel> personenLijst = getPersonenLijst(bsns);

        haalGegevensLuiOp(personenLijst);

        context.put(ContextParameterNames.PERSONENLIJST, personenLijst);

        return false;
    }

    private void haalGegevensLuiOp(final List<PersoonModel> personenLijst) {
        for (PersoonModel persoon : personenLijst) {
            if (persoon.getVoornamen() != null) {
                persoon.getVoornamen().size();
            }
            if(persoon.getAdressen() != null){
                persoon.getAdressen().size();
            }
            if(persoon.getGeslachtsnaamcomponenten() != null){
                persoon.getGeslachtsnaamcomponenten().size();
            }
        }
    }

    private List<PersoonModel> getPersonenLijst(final List<Integer> bsns) {
        List<PersoonModel> personenLijst = new ArrayList<PersoonModel>(bsns.size());

        for (Integer bsn : bsns) {
            LOGGER.info("ophalen persoon met BSN '{}'", bsn);
            PersoonModel persoon = persoonRepository.findByBurgerservicenummer(new Burgerservicenummer(bsn.toString()));
            personenLijst.add(persoon);
        }
        return personenLijst;
    }

}
