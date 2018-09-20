/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel.ui;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import nl.bzk.brp.bmr.metamodel.Attribuut;
import nl.bzk.brp.bmr.metamodel.ModelElement;

import org.hibernate.annotations.Immutable;


@Entity
@Immutable
public class FrameVeld extends ModelElement {

    private Integer   volgnummer;

    @ManyToOne
    @JoinColumn(name = "FRAME")
    private Frame     frame;

    @ManyToOne
    @JoinColumn(name = "ATTRIBUUT")
    private Attribuut attribuut;

    public Attribuut getAttribuut() {
        return attribuut;
    }

    public void setAttribuut(final Attribuut attribuut) {
        this.attribuut = attribuut;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(final Frame frame) {
        this.frame = frame;
    }

    public Integer getVolgnummer() {
        return volgnummer;
    }

    public String getNaam() {
        Bron bron = frame.getBron();
        for (BronAttribuut bronAttribuut : bron.getBronAttibuten()) {
            if (bronAttribuut.getAttribuut() == attribuut) {
                return bronAttribuut.getNaam();
            }
        }
        return attribuut.getNaam();
    }
}
