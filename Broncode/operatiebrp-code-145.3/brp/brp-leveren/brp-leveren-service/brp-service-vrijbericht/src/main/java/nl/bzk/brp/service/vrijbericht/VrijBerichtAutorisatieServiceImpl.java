/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.vrijbericht;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.BooleanSupplier;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieException;
import nl.bzk.brp.service.cache.PartijCache;
import org.springframework.stereotype.Service;

/**
 * UCS VB.1.AV.CA implementatie.
 */
@Bedrijfsregel(Regel.R2454)
@Bedrijfsregel(Regel.R2456)
@Bedrijfsregel(Regel.R2457)
@Bedrijfsregel(Regel.R2459)
@Bedrijfsregel(Regel.R2462)
@Bedrijfsregel(Regel.R2463)
@Bedrijfsregel(Regel.R2464)
@Bedrijfsregel(Regel.R2467)
@Bedrijfsregel(Regel.R2468)
@Bedrijfsregel(Regel.R2469)
@Bedrijfsregel(Regel.R2519)
@Service
final class VrijBerichtAutorisatieServiceImpl implements VrijBerichtAutorisatieService {

    @Inject
    private PartijCache partijCache;

    private VrijBerichtAutorisatieServiceImpl() {
    }

    private static void autorisatieAanwezig(final Partij partij, final Regel regel) throws AutorisatieException {
        if (partij.getDatumIngangVrijBericht() == null) {
            throw new AutorisatieException(new Melding(regel));
        }
    }

    private static void autorisatieGeldig(final Partij partij, final Regel regel) throws AutorisatieException {
        final List<BooleanSupplier> predicateList = Lists.newArrayList(
                () -> !partij.isActueelEnGeldigVoorVrijBericht(),
                () -> partij.getDatumIngangVrijBericht() > DatumUtil.vandaag(),
                () -> partij.getDatumEindeVrijBericht() != null && partij.getDatumEindeVrijBericht() <= DatumUtil.vandaag()
        );
        if (predicateList.stream().anyMatch(BooleanSupplier::getAsBoolean)) {
            throw new AutorisatieException(new Melding(regel));
        }
    }

    private static void autorisatieNietGeblokkeerd(final Partij partij, final Regel regel) throws AutorisatieException {
        if (partij.isVrijBerichtGeblokkeerd() != null && partij.isVrijBerichtGeblokkeerd()) {
            throw new AutorisatieException(new Melding(regel));
        }
    }

    private static void partijGeldig(final Partij partij, final Regel regel) throws AutorisatieException {
        final List<BooleanSupplier> predicateList = Lists.newArrayList(
                () -> partij == null,
                () -> partij.getDatumIngang() > DatumUtil.vandaag(),
                () -> partij.getDatumEinde() != null && partij.getDatumEinde() <= DatumUtil.vandaag()
        );
        if (predicateList.stream().anyMatch(BooleanSupplier::getAsBoolean)) {
            throw new AutorisatieException(new Melding(regel));
        }
    }

    private static void ondertekenaarCorrect(final Partij partij, final String oinOndertekenaar, final Regel regel) throws AutorisatieException {
        final List<BooleanSupplier> predicateList = Lists.newArrayList(
                () -> partij.getOin() == null,
                () -> partij.getOndertekenaarVrijBericht() == null && !partij.getOin().equals(oinOndertekenaar),
                () -> partij.getOndertekenaarVrijBericht() != null && partij.getOndertekenaarVrijBericht().getOin() == null,
                () -> partij.getOndertekenaarVrijBericht() != null && !partij.getOndertekenaarVrijBericht().getOin().equals(oinOndertekenaar)
        );
        if (predicateList.stream().anyMatch(BooleanSupplier::getAsBoolean)) {
            throw new AutorisatieException(new Melding(regel));
        }
    }

    private static void transporteurCorrect(final Partij partij, final String oinTransporteur, final Regel regel) throws AutorisatieException {
        final List<BooleanSupplier> predicateList = Lists.newArrayList(
                () -> partij.getOin() == null,
                () -> partij.getTransporteurVrijBericht() == null && !partij.getOin().equals(oinTransporteur),
                () -> partij.getTransporteurVrijBericht() != null && partij.getTransporteurVrijBericht().getOin() == null,
                () -> partij.getTransporteurVrijBericht() != null && !partij.getTransporteurVrijBericht().getOin().equals(oinTransporteur)
        );
        if (predicateList.stream().anyMatch(BooleanSupplier::getAsBoolean)) {
            throw new AutorisatieException(new Melding(regel));
        }
    }

    private static void ondertekenaarGeldig(final Partij partij, final String oinOndertekenaar, final Regel regel) throws AutorisatieException {
        if (!partij.getOin().equals(oinOndertekenaar)) {
            partijGeldig(partij.getOndertekenaarVrijBericht(), regel);
        }
    }

    private static void transporteurGeldig(final Partij partij, final String oinTransporteur, final Regel regel) throws AutorisatieException {
        if (!partij.getOin().equals(oinTransporteur)) {
            partijGeldig(partij.getTransporteurVrijBericht(), regel);
        }
    }

    private static void afleverpuntAanwezig(final Partij zenderVrijBericht, final Regel regel) throws AutorisatieException {
        if (zenderVrijBericht.getAfleverpuntVrijBericht() == null) {
            throw new AutorisatieException(new Melding(regel));
        }
    }

    private static void zenderIsZendendePartij(final Partij zenderVrijBericht, final String zendendePartijCode, final Regel regel)
            throws AutorisatieException {
        if (zenderVrijBericht == null || !zendendePartijCode.equals(zenderVrijBericht.getCode())) {
            throw new AutorisatieException(new Melding(regel));
        }
    }

    @Override
    public void valideerAutorisatie(final VrijBerichtVerzoek verzoek) throws AutorisatieException {
        final Partij zenderVrijBericht = partijCache.geefPartij(verzoek.getParameters().getZenderVrijBericht());
        final Partij ontvangerVrijBericht = partijCache.geefPartij(verzoek.getParameters().getOntvangerVrijBericht());
        partijGeldig(zenderVrijBericht, Regel.R2460);
        partijGeldig(ontvangerVrijBericht, Regel.R2461);
    }

    @Override
    public void valideerAutorisatieBrpZender(final VrijBerichtVerzoek verzoek) throws AutorisatieException {
        final Partij zenderVrijBericht = partijCache.geefPartij(verzoek.getParameters().getZenderVrijBericht());
        zenderIsZendendePartij(zenderVrijBericht, verzoek.getStuurgegevens().getZendendePartijCode(), Regel.R2498);
        afleverpuntAanwezig(zenderVrijBericht, Regel.R2470);
        autorisatieAanwezig(zenderVrijBericht, Regel.R2454);
        autorisatieGeldig(zenderVrijBericht, Regel.R2457);
        autorisatieNietGeblokkeerd(zenderVrijBericht, Regel.R2462);
        ondertekenaarCorrect(zenderVrijBericht, verzoek.getOin().getOinWaardeOndertekenaar(), Regel.R2464);
        transporteurCorrect(zenderVrijBericht, verzoek.getOin().getOinWaardeTransporteur(), Regel.R2467);
        ondertekenaarGeldig(zenderVrijBericht, verzoek.getOin().getOinWaardeOndertekenaar(), Regel.R2468);
        transporteurGeldig(zenderVrijBericht, verzoek.getOin().getOinWaardeTransporteur(), Regel.R2469);
    }

    @Override
    public void valideerAutorisatieBrpOntvanger(final VrijBerichtVerzoek verzoek) throws AutorisatieException {
        final Partij ontvangerVrijBericht = partijCache.geefPartij(verzoek.getParameters().getOntvangerVrijBericht());
        autorisatieAanwezig(ontvangerVrijBericht, Regel.R2456);
        autorisatieGeldig(ontvangerVrijBericht, Regel.R2459);
        autorisatieNietGeblokkeerd(ontvangerVrijBericht, Regel.R2463);
        afleverpuntAanwezig(ontvangerVrijBericht, Regel.R2519);
    }
}
