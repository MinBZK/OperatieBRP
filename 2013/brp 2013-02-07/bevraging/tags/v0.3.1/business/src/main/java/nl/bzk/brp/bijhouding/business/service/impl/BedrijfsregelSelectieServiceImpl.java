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

import nl.bzk.brp.bijhouding.business.service.BedrijfsregelSelectieService;
import nl.bzk.brp.bijhouding.domein.repository.RegelGedragRepository;
import nl.bzk.brp.domein.ber.SoortBericht;
import nl.bzk.brp.domein.brm.Regelimplementatie;
import nl.bzk.brp.domein.brm.Regelimplementatiesituatie;
import nl.bzk.brp.domein.kern.RedenOpschorting;
import nl.bzk.brp.domein.kern.Verantwoordelijke;


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
    public List<Regelimplementatiesituatie> zoekMeestSpecifiekBedrijfsregelGedrag(final SoortBericht soortBericht,
            final Verantwoordelijke verantwoordelijke, final Boolean isOpschorting,
            final RedenOpschorting redenOpschorting)
    {
        List<Regelimplementatiesituatie> resultaat;

        List<Regelimplementatiesituatie> gedragingen =
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
     * @param gedragingen lijst met bedrijfsRegelimplementatiesituatie waarvan alleen de meest specifieke moeten worden
     *            verzameld
     * @return het meest specifieke gedrag
     */
    private List<Regelimplementatiesituatie> bepaalMeestSpecifiekGedrag(
            final List<Regelimplementatiesituatie> gedragingen)
    {
        List<Regelimplementatiesituatie> resultaat = new ArrayList<Regelimplementatiesituatie>();
        Set<Regelimplementatie> implementaties = bepaalRegelimplementaties(gedragingen);
        for (Regelimplementatie implementatie : implementaties) {
            resultaat.addAll(bepaalMeestSpecifiekGedrag(gedragingen, implementatie));
        }
        return resultaat;
    }

    /**
     * Filter de gegeven gedragingen zodat alleen de meest specifieke worden teruggegeven van de gegeven implementatie.
     *
     * @param gedragingen lijst met bedrijfsRegelimplementatiesituatie waarvan alleen de meest specifieke moeten worden
     *            verzameld
     * @param implementatie de implementatie waarop gefilterd moet worden
     * @return het meest specifieke gedrag met de gegeven implementatie
     */
    private List<Regelimplementatiesituatie> bepaalMeestSpecifiekGedrag(
            final List<Regelimplementatiesituatie> gedragingen, final Regelimplementatie implementatie)
    {
        List<Regelimplementatiesituatie> resultaat = new ArrayList<Regelimplementatiesituatie>();
        for (Regelimplementatiesituatie gedrag : gedragingen) {
            if (gedrag.getRegelimplementatie() != implementatie) {
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
     * @param gedragingen lijst met bedrijfsRegelimplementatiesituatie waarvan de implementaties moeten worden verzameld
     * @return alle implementaties die voorkwamen in de lijst met gedrag
     */
    private Set<Regelimplementatie> bepaalRegelimplementaties(final List<Regelimplementatiesituatie> gedragingen) {
        Set<Regelimplementatie> resultaat = new HashSet<Regelimplementatie>();
        for (Regelimplementatiesituatie gedrag : gedragingen) {
            resultaat.add(gedrag.getRegelimplementatie());
        }
        return resultaat;
    }

}
