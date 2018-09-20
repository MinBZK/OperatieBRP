/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.migratie.acties.adres;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import nl.bzk.brp.business.regels.RegelInterface;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.hisvolledig.kern.PersoonAdresHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;


/**
 * Na verwerking van alle adrescorrecties uit het bericht moet gelden: bij een adres dient de datum aanvang
 * adreshouding gelijk te zijn aan datum aanvang geldigheid (bij het vastleggen van een startadres ten gevolge van
 * een adrescorrectie).
 * <p/>
 * Merkop: Update, deze regel is dus NIET geldig voor buitenlandse adressen. (Nog in te bouwen hier).
 *
 * @brp.bedrijfsregel BRBY0525
 */
@Named("BRBY0525")
public class BRBY0525 implements RegelInterface {

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0525;
    }

    /**
     * Voert de regel uit.
     * @param bericht bericht
     * @param persoonHisVolledig persoonHisVolledig
     * @param tijdstipVerwerking tijdstipVerwerking
     * @return objectenDieDeRegelOvertreden
     */
    public List<BerichtIdentificeerbaar> voerRegelUit(final BijhoudingsBericht bericht, final PersoonHisVolledig persoonHisVolledig,
        final Date tijdstipVerwerking)
    {
        final List<BerichtIdentificeerbaar> objectenDieDeRegelOvertreden = new ArrayList<>();

        final List<HisPersoonAdresModel> historie =
            haalAdresHistorieGewijzigdeRecordsOp(persoonHisVolledig, tijdstipVerwerking);

        if (isDatumInconsistent(historie)) {
            objectenDieDeRegelOvertreden.add(bericht.getAdministratieveHandeling());
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Controleert of er in de historie ergens de datumAanvangAdreshouding ongelijk is aan de datumAanvangGeldigheid.
     *
     * @param historie de historie records die aangepast zijn door het bericht
     * @return true als er inconsitentie zijn
     */
    private boolean isDatumInconsistent(final List<HisPersoonAdresModel> historie) {
        boolean inconsistent = false;
        for (final HisPersoonAdresModel his : historie) {
            if (LandGebiedCodeAttribuut.NEDERLAND.equals(his.getLandGebied().getWaarde().getCode())
                && !his.getMaterieleHistorie().getDatumAanvangGeldigheid().getWaarde()
                        .equals(his.getDatumAanvangAdreshouding().getWaarde()))
            {
                inconsistent = true;
                break;
            }
        }

        return inconsistent;
    }

    /**
     * Haalt de historie die gewijzigd is met de huidige bericht.
     *
     * @param persoonHisVolledig de adres historie voor wie het opgehaald moet worden
     * @param tijdstipVerwerking tijdstip van vewerking
     * @return gewijzigde adres his records.
     */
    private List<HisPersoonAdresModel> haalAdresHistorieGewijzigdeRecordsOp(
            final PersoonHisVolledig persoonHisVolledig, final Date tijdstipVerwerking)
    {
        final List<HisPersoonAdresModel> historie = new ArrayList<>();
        for (final PersoonAdresHisVolledig persoonAdresHisVolledig : persoonHisVolledig.getAdressen()) {
            final MaterieleHistorieSet<HisPersoonAdresModel> historieSet =
                persoonAdresHisVolledig.getPersoonAdresHistorie();

            for (final HisPersoonAdresModel his : historieSet) {
                if (his.getDatumTijdVerval() == null
                    && his.getTijdstipRegistratie().getWaarde().equals(tijdstipVerwerking))
                {
                    historie.add(his);
                }
            }
        }

        return historie;
    }
}
