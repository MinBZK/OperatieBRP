/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel.ui;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import nl.bzk.brp.bmr.metamodel.NamedModelElement;

import org.hibernate.annotations.Immutable;


@Entity
@Immutable
public class Applicatie extends NamedModelElement {

    @OneToMany(mappedBy = "applicatie")
    private List<Formulier> formulieren;

    public List<Formulier> getFormulieren() {
        return formulieren;
    }

    public void setFormulieren(final List<Formulier> formulieren) {
        this.formulieren = formulieren;
    }

}
