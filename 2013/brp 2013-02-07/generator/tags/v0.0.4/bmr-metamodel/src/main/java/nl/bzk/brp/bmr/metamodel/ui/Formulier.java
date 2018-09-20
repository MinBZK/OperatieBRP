/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel.ui;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import nl.bzk.brp.bmr.metamodel.NamedModelElement;

import org.hibernate.annotations.Immutable;


@Entity
@Immutable
public class Formulier extends NamedModelElement {

    @ManyToOne
    @JoinColumn(name = "APPLICATIE")
    private Applicatie  applicatie;

    @OneToMany(mappedBy = "formulier")
    @OrderBy("volgnummer")
    private List<Frame> frames;

    @OneToMany(mappedBy = "formulier")
    @OrderBy("volgnummer")
    private List<Bron>  bronnen;

    public Applicatie getApplicatie() {
        return applicatie;
    }

    public List<Bron> getBronnen() {
        return bronnen;
    }

    public List<Frame> getFrames() {
        return frames;
    }

    public void setFrames(final List<Frame> frames) {
        this.frames = frames;
    }

}
