/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.dataaccess.repository.ToegangBijhoudingsautorisatieRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.BijhoudingsautorisatieSoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangBijhoudingsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.util.AutorisatieOffloadGegevens;
import org.springframework.stereotype.Service;

/**
 * Implementatie van de {@link BijhoudingsautorisatieService}.
 *
 * @brp.bedrijfsregel R2120, R1257, R2052, R1258, R2106, R2121, R2122
 */
@Service
@Regels({ Regel.R2120, Regel.R1257, Regel.R2052, Regel.R1258, Regel.R2106, Regel.R2121, Regel.R2122 })
class BijhoudingsautorisatieServiceImpl implements BijhoudingsautorisatieService {

    @Inject
    private ToegangBijhoudingsautorisatieRepository toegangBijhoudingsautorisatieRepository;
    @Inject
    private ReferentieDataRepository                referentieDataRepository;

    @Override
    public void controleerAutorisatie(final SoortAdministratieveHandeling soortAdministratieveHandeling, final String partijcode,
        final AutorisatieOffloadGegevens offloadGegevens) throws AutorisatieException
    {
        if (partijcode == null || offloadGegevens == null) {
            throw new AutorisatieException();
        }

        final Partij partij = referentieDataRepository.vindPartijOpCode(new PartijCodeAttribuut(Integer.parseInt(partijcode)));
        if (isBijhouder(partij)) {
            final ToegangBijhoudingsautorisatie autorisatie = geefGeautoriseerdeToegangBijhoudingsautorisatie(partij, offloadGegevens);
            final List<BijhoudingsautorisatieSoortAdministratieveHandeling> geautoriseerdeHandelingen = autorisatie.getGeautoriseerdeHandelingen();
            if (geautoriseerdeHandelingen.size() > 0 && !autorisatieBevatAdministratieveHandeling(geautoriseerdeHandelingen,
                soortAdministratieveHandeling))
            {
                throw new AutorisatieException(Regel.R2106);
            }
        } else {
            throw new AutorisatieException(Regel.R2120);
        }
    }

    private static boolean autorisatieBevatAdministratieveHandeling(final List<BijhoudingsautorisatieSoortAdministratieveHandeling>
        geautoriseerdeHandelingen,
        final SoortAdministratieveHandeling administratieveHandeling)
    {
        for (final BijhoudingsautorisatieSoortAdministratieveHandeling bijhoudingsautorisatieSoortAdministratieveHandeling : geautoriseerdeHandelingen) {
            if (bijhoudingsautorisatieSoortAdministratieveHandeling.getSoortAdministratieveHandeling().equals(administratieveHandeling)) {
                return true;
            }
        }
        return false;
    }

    private ToegangBijhoudingsautorisatie geefGeautoriseerdeToegangBijhoudingsautorisatie(final Partij partij,
        final AutorisatieOffloadGegevens offloadGegevens) throws AutorisatieException
    {
        final List<ToegangBijhoudingsautorisatie> autorisaties = toegangBijhoudingsautorisatieRepository
            .geefToegangBijhoudingsautorisatie(partij.getCode().getWaarde());
        if (autorisaties.size() == 0) {
            throw new AutorisatieException(Regel.R2120);
        }

        final boolean isZelfOndertekenaar = partij.getOIN().getWaarde().equals(offloadGegevens.getOndertekenaar().getOIN().getWaarde());
        final boolean isZelfTransporteur = partij.getOIN().getWaarde().equals(offloadGegevens.getTransporteur().getOIN().getWaarde());

        boolean isOndertekenaarGevonden = false;
        boolean isTransporteurGevonden = false;

        ToegangBijhoudingsautorisatie gevondenAutorisatie = null;

        for (final ToegangBijhoudingsautorisatie autorisatie : autorisaties) {
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

        verifieerAutorisatie(gevondenAutorisatie, isOndertekenaarGevonden, isTransporteurGevonden);

        return gevondenAutorisatie;
    }

    private void verifieerAutorisatie(final ToegangBijhoudingsautorisatie gevondenAutorisatie, final boolean isOndertekenaarGevonden,
        final boolean isTransporteurGevonden) throws AutorisatieException
    {
        AutorisatieUtil.verifieerAutorisatie(gevondenAutorisatie, isOndertekenaarGevonden, isTransporteurGevonden);

        if (!isAuthenticatieGeldig(gevondenAutorisatie)) {
            throw new AutorisatieException(Regel.R1258);
        }
        final JaAttribuut indicatieGeblokkeerd = gevondenAutorisatie.getIndicatieGeblokkeerd();
        if (indicatieGeblokkeerd != null && indicatieGeblokkeerd.getWaarde().getVasteWaarde()) {
            throw new AutorisatieException(Regel.R2052);
        }
    }

    private static boolean isAuthenticatieGeldig(final ToegangBijhoudingsautorisatie autorisatie) {
        final Date systeemDatum = new Date();
        final boolean datumIngangVoorSysteemDatum = DatumAttribuut
            .testDatum1voorDatum2(autorisatie.getDatumIngang(), new DatumAttribuut(systeemDatum), true);
        final boolean systeemDatumVoorDatumEinde = autorisatie.getDatumEinde() == null || DatumAttribuut
            .testDatum1voorDatum2(new DatumAttribuut(systeemDatum), autorisatie.getDatumEinde(), false);
        return datumIngangVoorSysteemDatum && systeemDatumVoorDatumEinde;
    }

    private static boolean isBijhouder(final Partij partij) {
        if (partij == null) {
            return false;
        }
        final Set<Rol> rollen = partij.getRollen();
        return rollen.contains(Rol.BIJHOUDINGSORGAAN_COLLEGE) || rollen.contains(Rol.BIJHOUDINGSORGAAN_MINISTER);
    }

}
