/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bevraging;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import nl.bzk.brp.business.dto.BerichtVerwerkingsResultaat;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;


/**
 * Model class voor het xsd type OpvragenPersoonAntwoord wat gebruikt wordt in
 * het antwoord van bevragingsberichten.
 */
public class OpvragenPersoonResultaat extends BerichtVerwerkingsResultaat {

    private SortedSet<PersoonModel> gevondenPersonen;

    /**
     * Private contructor om het gebruik tegen te gaan.
     */
    private OpvragenPersoonResultaat() {
        this(null);
    }

    /**
     * Constructor.
     *
     * @param meldingen Een lijst om meldingen op te kunnen slaan.
     */
    public OpvragenPersoonResultaat(final List<Melding> meldingen) {
        super(meldingen);
    }

    public Set<PersoonModel> getGevondenPersonen() {
        return gevondenPersonen;
    }

    public void setGevondenPersonen(final Set<PersoonModel> gevondenPersonen) {
        if (gevondenPersonen != null) {
            if (this.gevondenPersonen == null) {
                this.gevondenPersonen = new TreeSet<PersoonModel>(new Comparator<PersoonModel>() {

                    @Override
                    public int compare(final PersoonModel persoon1, final PersoonModel persoon2) {
                        return persoon1.getId().compareTo(persoon2.getId());
                    }
                });
            }
            for (PersoonModel persoon : gevondenPersonen) {
                this.gevondenPersonen.add(persoon);
            }
        } else {
            this.gevondenPersonen = null;
        }
    }
}
