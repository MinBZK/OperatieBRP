/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Where;


/**
 * Schema metaelement.
 */
@Entity
@DiscriminatorValue("S")
public class Schema extends Element {

    @ManyToOne
    @JoinColumn(name = "OUDER")
    private Domein       domein;

    @OneToMany(mappedBy = "schema")
    @Where(clause = "VERSIE_TAG = 'W'")
    @Immutable
    private List<Versie> versies = new ArrayList<Versie>();

    public List<Versie> getVersies() {
        return versies;
    }

    public Versie getVersie(final String tag) {
        for (Versie versie : versies) {
            if (versie.getVersieTag().equals(tag)) {
                return versie;
            }
        }
        return null;
    }

    /**
     * Geef van alle versies diegene terug die de werkversie is.
     *
     * @return de werkversie.
     */
    public Versie getWerkVersie() {
        return getVersie(VersieTag.W.name());
    }

    public Domein getDomein() {
        return domein;
    }

}
