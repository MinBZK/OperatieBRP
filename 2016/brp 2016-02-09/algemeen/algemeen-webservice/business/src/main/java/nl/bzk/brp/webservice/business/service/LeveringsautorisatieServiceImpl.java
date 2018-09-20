/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.dataaccess.repository.ToegangLeveringsautorisatieRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.util.AutorisatieOffloadGegevens;
import org.springframework.stereotype.Service;

/**
 * Implementatie van de {@link LeveringsautorisatieService}.
 */
@Service
public class LeveringsautorisatieServiceImpl implements LeveringsautorisatieService {

    @Inject
    private ToegangLeveringsautorisatieRepository repository;

    @Inject
    private ReferentieDataRepository referentieDataRepository;

    @Override
    public final void controleerAutorisatie(final int leveringautorisatieId, final String partijcode, final AutorisatieOffloadGegevens offloadGegevens)
        throws AutorisatieException
    {
        if (partijcode == null || offloadGegevens == null) {
            throw new AutorisatieException();
        }

        final Partij partij = referentieDataRepository.vindPartijOpCode(new PartijCodeAttribuut(Integer.parseInt(partijcode)));
        // TODO: hier wordt op afnemer gecheckt, klopt dat wel, of moeten alle rollen geautoriseerd zijn?
        if (isAfnemer(partij)) {
            controleerAuthenticatie(leveringautorisatieId, offloadGegevens, partij);
        } else {
            throw new AutorisatieException(Regel.R2120);
        }
    }

    private void controleerAuthenticatie(final int leveringAutorisatieId, final AutorisatieOffloadGegevens offloadGegevens, final Partij partij) throws
        AutorisatieException
    {
        final List<ToegangLeveringsautorisatie> autorisaties =
            new ArrayList<>(repository.geefToegangLeveringsautorisaties(partij.getCode().getWaarde()));
        if (autorisaties.size() == 0) {
            throw new AutorisatieException(Regel.R2120);
        }
        // verwijder alle toegangleveringsautorisatie die de verkeerde leveringAutorisatieId hebben
        Iterator<ToegangLeveringsautorisatie> tlaIterator = autorisaties.iterator();
        while (tlaIterator.hasNext()) {
            final ToegangLeveringsautorisatie tla = tlaIterator.next();
            if (tla.getLeveringsautorisatie().getID() != leveringAutorisatieId) {
                tlaIterator.remove();
            }
        }
        if (autorisaties.isEmpty()) {
            throw new AutorisatieException(Regel.R2053);//De opgegeven leveringsautorisatie bestaat niet
        }

        final boolean isZelfOndertekenaar = partij.getOIN().getWaarde().equals(offloadGegevens.getOndertekenaar().getOIN().getWaarde());
        final boolean isZelfTransporteur = partij.getOIN().getWaarde().equals(offloadGegevens.getTransporteur().getOIN().getWaarde());

        boolean isOndertekenaarGevonden = false;
        boolean isTransporteurGevonden = false;

        ToegangLeveringsautorisatie gevondenAutorisatie = null;
        for (final ToegangLeveringsautorisatie autorisatie : autorisaties) {
            final AutorisatieUtil.AutorisatieResultaat autorisatieResultaat = AutorisatieUtil
                .bepaalAutorisatieResultaat(autorisatie.getOndertekenaar(), autorisatie.getTransporteur(), offloadGegevens,
                    isZelfOndertekenaar,
                    isZelfTransporteur);
            isOndertekenaarGevonden |= autorisatieResultaat.isOndertekenaarGeautoriseerd();
            isTransporteurGevonden |= autorisatieResultaat.isTransporteurGeautoriseerd();
            if (autorisatieResultaat.isGeautoriseerd()) {
                gevondenAutorisatie = autorisatie;
            }
        }

        final DatumAttribuut systeemDatum = new DatumAttribuut(new Date());

        controleerToegangleveringsAutorisatie(autorisaties, isOndertekenaarGevonden, isTransporteurGevonden, gevondenAutorisatie, systeemDatum);
        controleerLeveringsautorisatie(gevondenAutorisatie, systeemDatum);
    }

    private static void controleerToegangleveringsAutorisatie(final List<ToegangLeveringsautorisatie> autorisaties, final boolean isOndertekenaarGevonden,
        final boolean isTransporteurGevonden, final ToegangLeveringsautorisatie gevondenAutorisatie, final DatumAttribuut systeemDatum)
        throws AutorisatieException
    {
        AutorisatieUtil.verifieerAutorisatie(gevondenAutorisatie, isOndertekenaarGevonden, isTransporteurGevonden);

        if (!gevondenAutorisatie.isGeldigOp(systeemDatum)) {
            throw new AutorisatieException(Regel.R1258);
        }
        if (gevondenAutorisatie.isGeblokkeerd()) {
            throw new AutorisatieException(Regel.R2052);
        }
    }

    private static void controleerLeveringsautorisatie(final ToegangLeveringsautorisatie geselecteerdeAutorisatie, final DatumAttribuut systeemDatum)
        throws AutorisatieException
    {
        final Leveringsautorisatie leveringsautorisatie = geselecteerdeAutorisatie.getLeveringsautorisatie();
        if (!leveringsautorisatie.isGeldigOp(systeemDatum)) {
            throw new AutorisatieException(Regel.R1261);
        }
        if (leveringsautorisatie.isGeblokkeerd()) {
            throw new AutorisatieException(Regel.R1263);
        }
    }

    private static boolean isAfnemer(final Partij partij) {
        if (partij == null) {
            return false;
        }
        final Set<Rol> rollen = partij.getRollen();
        return rollen.contains(Rol.AFNEMER);
    }
}
