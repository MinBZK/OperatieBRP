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
public class Frame extends NamedModelElement {

    private Long            volgnummer;

    @ManyToOne
    @JoinColumn(name = "FORMULIER")
    private Formulier       formulier;

    @ManyToOne
    @JoinColumn(name = "BRON")
    private Bron            bron;

    @OneToMany(mappedBy = "frame")
    @OrderBy("volgnummer")
    private List<FrameVeld> velden;

    public Bron getBron() {
        return bron;
    }

    public void setBron(final Bron bron) {
        this.bron = bron;
    }

    public Formulier getFormulier() {
        return formulier;
    }

    public void setFormulier(final Formulier formulier) {
        this.formulier = formulier;
    }

    @Override
    public String getNaam() {
        if (super.getNaam() != null) {
            return super.getNaam();
        } else
            return bron.getNaam();
    }

    public List<FrameVeld> getVelden() {
        return velden;
    }

    public void setVelden(final List<FrameVeld> velden) {
        this.velden = velden;
    }

    public Long getVolgnummer() {
        return volgnummer;
    }
}
