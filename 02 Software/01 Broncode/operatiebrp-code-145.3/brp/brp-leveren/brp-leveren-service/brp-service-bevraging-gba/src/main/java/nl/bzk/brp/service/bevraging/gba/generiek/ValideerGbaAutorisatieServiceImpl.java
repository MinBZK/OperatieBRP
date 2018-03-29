/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.generiek;

import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelLo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import org.springframework.stereotype.Component;

/**
 * Implementatie van de ValideerGbaAutorisatieService.
 */
@Component
public class ValideerGbaAutorisatieServiceImpl implements ValideerGbaAutorisatieService {

    private static final String BSN = "01.01.20";

    @Override
    public Set<Melding> valideer(final ZoekPersoonGeneriekGbaVerzoek verzoek, final Autorisatiebundel autorisatiebundel) {
        List<String> rubrieken = autorisatiebundel.getDienst().getDienstbundel().getDienstbundelLo3RubriekSet()
                .stream()
                .map(DienstbundelLo3Rubriek::getLo3Rubriek)
                .map(Lo3Rubriek::getNaam)
                .collect(Collectors.toList());

        // Bij Plaatsen en Verwijderen afnemersindicatie wordt BSN door BRP/Migratievoorziening gebruikt.
        // Deze dus altijd toestaan
        if (autorisatiebundel.getSoortDienst() == SoortDienst.PLAATSING_AFNEMERINDICATIE
                || autorisatiebundel.getSoortDienst() == SoortDienst.VERWIJDERING_AFNEMERINDICATIE) {
            rubrieken.add(BSN);
        }

        Set<String> alleRubrieken = new HashSet<>();
        if (verzoek.getGevraagdeRubrieken() != null) {
            alleRubrieken.addAll(verzoek.getGevraagdeRubrieken());
        }
        if (verzoek.getZoekRubrieken() != null) {
            alleRubrieken.addAll(verzoek.getZoekRubrieken());
        }

        if (!rubrieken.containsAll(alleRubrieken)) {
            return Collections.singleton(new Melding(Regel.R2343));
        }

        return Sets.newHashSet();
    }
}
