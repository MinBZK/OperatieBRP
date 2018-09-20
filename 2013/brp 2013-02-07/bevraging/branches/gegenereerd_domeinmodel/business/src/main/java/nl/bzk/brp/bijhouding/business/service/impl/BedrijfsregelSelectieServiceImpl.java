/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.domein.RedenOpschorting;
import nl.bzk.brp.bevraging.domein.Verantwoordelijke;
import nl.bzk.brp.bevraging.domein.ber.SoortBericht;
import nl.bzk.brp.bijhouding.business.service.BedrijfsregelSelectieService;
import nl.bzk.brp.bijhouding.domein.brm.RegelGedrag;
import nl.bzk.brp.bijhouding.domein.brm.RegelImplementatie;
import nl.bzk.brp.bijhouding.domein.repository.RegelGedragRepository;


/**
 * Implementatie van {@link BedrijfsregelSelectieService}.
 */
public class BedrijfsregelSelectieServiceImpl implements BedrijfsregelSelectieService {

    @Inject
    private RegelGedragRepository repository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RegelGedrag> zoekMeestSpecifiekBedrijfsregelGedrag(final SoortBericht soortBericht,
            final Verantwoordelijke verantwoordelijke, final Boolean isOpschorting,
            final RedenOpschorting redenOpschorting)
    {
        List<RegelGedrag> resultaat;

        List<RegelGedrag> gedragingen =
            repository.zoekActiefBedrijfsregelGedrag(soortBericht, verantwoordelijke, isOpschorting, redenOpschorting);

        if (gedragingen == null || gedragingen.isEmpty()) {
            resultaat = null;
        } else {
            resultaat = bepaalMeestSpecifiekGedrag(gedragingen);
        }
        return resultaat;
    }

    /**
     * Filter de gegeven gedragingen zodat alleen de voor hun implementatie meest specifieke worden teruggegeven.
     *
     * @param gedragingen lijst met bedrijfsregelgedrag waarvan alleen de meest specifieke moeten worden verzameld
     * @return het meest specifieke gedrag
     */
    private List<RegelGedrag> bepaalMeestSpecifiekGedrag(final List<RegelGedrag> gedragingen) {
        List<RegelGedrag> resultaat = new ArrayList<RegelGedrag>();
        Set<RegelImplementatie> implementaties = bepaalRegelImplementaties(gedragingen);
        for (RegelImplementatie implementatie : implementaties) {
            resultaat.addAll(bepaalMeestSpecifiekGedrag(gedragingen, implementatie));
        }
        return resultaat;
    }

    /**
     * Filter de gegeven gedragingen zodat alleen de meest specifieke worden teruggegeven van de gegeven implementatie.
     *
     * @param gedragingen lijst met bedrijfsregelgedrag waarvan alleen de meest specifieke moeten worden verzameld
     * @param implementatie de implementatie waarop gefilterd moet worden
     * @return het meest specifieke gedrag met de gegeven implementatie
     */
    private List<RegelGedrag> bepaalMeestSpecifiekGedrag(final List<RegelGedrag> gedragingen,
            final RegelImplementatie implementatie)
    {
        List<RegelGedrag> resultaat = new ArrayList<RegelGedrag>();
        for (RegelGedrag gedrag : gedragingen) {
            if (gedrag.getRegelImplementatie() != implementatie) {
                // het gedrag is niet van de implementatie die we zoeken
                continue;
            } else if (resultaat.isEmpty() || gedrag.isSpecifiekerDan(resultaat.get(0))) {
                // het gedrag is specifieker dan alles wat we tot nu toe hebben gevonden
                resultaat.clear();
                resultaat.add(gedrag);
            } else if (!resultaat.get(0).isSpecifiekerDan(gedrag)) {
                // het gedrag is even specifiek als wat we tot nu toe hebben gevonden
                resultaat.add(gedrag);
            }
        }
        return resultaat;
    }

    /**
     * Verzamelt alle implementaties die horen bij het gegeven gedrag.
     *
     * @param gedragingen lijst met bedrijfsregelgedrag waarvan de implementaties moeten worden verzameld
     * @return alle implementaties die voorkwamen in de lijst met gedrag
     */
    private Set<RegelImplementatie> bepaalRegelImplementaties(final List<RegelGedrag> gedragingen) {
        Set<RegelImplementatie> resultaat = new HashSet<RegelImplementatie>();
        for (RegelGedrag gedrag : gedragingen) {
            resultaat.add(gedrag.getRegelImplementatie());
        }
        return resultaat;
    }

}
