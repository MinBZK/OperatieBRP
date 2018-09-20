/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bevraging;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaat;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaatImpl;
import org.apache.commons.lang.builder.CompareToBuilder;


/**
 * Model klasse voor een antwoord op een bevraging.
 * Bevat een (evt lege) lijst met de gevonden personen.
 */
public class BevragingResultaat extends BerichtVerwerkingsResultaatImpl implements BerichtVerwerkingsResultaat {

    private SortedSet<PersoonHisVolledigView> gevondenPersonen;
    private Set<Integer> teArchiverenPersonenIngaandBericht = new HashSet<>();
    private Set<Integer> teArchiverenPersonenUitgaandBericht = new HashSet<>();

    /**
     * Constructor.
     *
     * @param meldingen Een lijst om meldingen op te kunnen slaan.
     */
    public BevragingResultaat(final List<Melding> meldingen) {
        super(meldingen);
        // Initialiseer de gevonden personen met de gewenste sortering.
        this.gevondenPersonen = new TreeSet<>(new Comparator<PersoonHisVolledigView>() {
            @Override
            public final int compare(final PersoonHisVolledigView persoon1, final PersoonHisVolledigView persoon2) {
                //TODO: Functionele manier van sorteren, nog geen specs voor (evt. afhankelijk van de soort vraag)
                //Tijdelijke oplossing: sorteren op id.
                return new CompareToBuilder().append(persoon1.getID(), persoon2.getID()).toComparison();
            }
        });
    }

    public Set<PersoonHisVolledigView> getGevondenPersonen() {
        return gevondenPersonen;
    }

    /**
     * Voeg een persoon toe aan de gevonden personen.
     *
     * @param persoon persoon
     */
    public final void voegGevondenPersoonToe(final PersoonHisVolledigView persoon) {
        this.gevondenPersonen.add(persoon);
    }

    /**
     * Voeg meerdere personen toe aan de gevonden personen.
     *
     * @param personen personen
     */
    public final void voegGevondenPersonenToe(final Collection<PersoonHisVolledigView> personen) {
        this.gevondenPersonen.addAll(personen);
    }

    @Override
    public Set<Integer> getTeArchiverenPersonenIngaandBericht() {
        return teArchiverenPersonenIngaandBericht;
    }

    @Override
    public Set<Integer> getTeArchiverenPersonenUitgaandBericht() {
        return teArchiverenPersonenUitgaandBericht;
    }

}
