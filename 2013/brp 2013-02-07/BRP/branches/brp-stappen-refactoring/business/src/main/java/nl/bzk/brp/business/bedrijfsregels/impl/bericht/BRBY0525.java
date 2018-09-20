/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.bericht;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.BerichtContextBedrijfsRegel;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.historie.HistoriePersoonAdresRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;


/**
 * Na verwerking van alle adrescorrecties uit het bericht moet gelden: bij een adres dient de datum aanvang
 * adreshouding gelijk te zijn aan datum aanvang geldigheid (bij het vastleggen van een staartadres ten gevolge van
 * een adrescorrectie).
 *
 * @brp.bedrijfsregel BRBY0525
 */
public class BRBY0525 implements BerichtContextBedrijfsRegel {

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private HistoriePersoonAdresRepository historiePersoonAdresRepository;

    @Override
    public String getCode() {
        return "BRBY0525";
    }

    @Override
    public List<Melding> executeer(final BerichtContext context) {
        // Bedrijfs regel gaat ervanuit dat alle personen in het bericht over dezelfde persoon gaat.
        Persoon persoon = context.getHoofdPersonen().get(0);

        PersoonModel persoonModel = persoonRepository.findByBurgerservicenummer(
            persoon.getIdentificatienummers().getBurgerservicenummer());

        List<HisPersoonAdresModel> historie = historiePersoonAdresRepository.haalHistorieGewijzigdeRecordsOp(
            persoonModel, new DatumTijd(context.getTijdstipVerwerking()));

        List<Melding> meldingen;
        if (isDatumIncosistent(historie)) {
            meldingen = Arrays.asList(new Melding(SoortMelding.DEBLOKKEERBAAR, MeldingCode.BRBY0525,
                (Identificeerbaar) persoon, "adres"));
        } else {
            meldingen = Collections.EMPTY_LIST;
        }
        return meldingen;
    }

    /**
     * Controlleert of er in de historie ergens de datumAanvangAdreshouding ongelijk is aan de datumAanvangGeldigheid.
     *
     * @param historie de historie records die aangepast zijn door het bericht
     * @return true als er inconsitentie zijn
     */
    private boolean isDatumIncosistent(final List<HisPersoonAdresModel> historie) {
        boolean incosistent = false;
        for (HisPersoonAdresModel his : historie) {
            if (!his.getMaterieleHistorie().getDatumAanvangGeldigheid().getWaarde()
                    .equals(his.getDatumAanvangAdreshouding().getWaarde()))
            {
                incosistent = true;
                break;
            }
        }

        return incosistent;
    }
}
