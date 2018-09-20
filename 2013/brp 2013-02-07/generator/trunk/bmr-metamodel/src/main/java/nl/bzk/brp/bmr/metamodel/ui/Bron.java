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

import nl.bzk.brp.bmr.metamodel.Attribuut;
import nl.bzk.brp.bmr.metamodel.NamedModelElement;
import nl.bzk.brp.bmr.metamodel.ObjectType;

import org.hibernate.annotations.Immutable;


@Entity
@Immutable
public class Bron extends NamedModelElement {

    private String              meervoudsNaam;
    private String              identifier;
    private Integer             volgnummer;

    @ManyToOne
    @JoinColumn(name = "FORMULIER")
    private Formulier           formulier;

    @ManyToOne
    @JoinColumn(name = "OBJECTTYPE")
    private ObjectType          objectType;

    @OneToMany(mappedBy = "bron")
    @OrderBy("volgnummer")
    private List<Frame>         frames;

    @OneToMany(mappedBy = "bron")
    @OrderBy("volgnummer")
    private List<BronAttribuut> bronAttributen;

    @ManyToOne
    @JoinColumn(name = "LINK")
    private Attribuut           link;

    public Attribuut getLink() {
        return link;
    }

    public Formulier getFormulier() {
        return formulier;
    }

    public List<Frame> getFrames() {
        return frames;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getMeervoudsNaam() {
        return meervoudsNaam;
    }

    @Override
    public String getNaam() {
        if (super.getNaam() != null) {
            return super.getNaam();
        } else
            return objectType.getIdentifierDB();
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(final ObjectType objectType) {
        this.objectType = objectType;
    }

    public List<BronAttribuut> getBronAttibuten() {
        return bronAttributen;
    }

    public Integer getVolgnummer() {
        return volgnummer;
    }
}
