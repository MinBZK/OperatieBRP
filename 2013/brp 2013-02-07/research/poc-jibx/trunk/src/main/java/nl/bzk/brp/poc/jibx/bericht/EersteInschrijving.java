/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.jibx.bericht;

import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.poc.jibx.model.Bron;
import nl.bzk.brp.poc.jibx.model.Persoon;

/**
 * Model class voor een eenvoudige 1e inschrijving.
 */
public class EersteInschrijving {
    
    private Set<Bron> bronnen;
    private Persoon ouder1;
    private Persoon ouder2;
    private Persoon kind;

    public EersteInschrijving() {
        this.bronnen = new HashSet<Bron>();
    }

    public Set<Bron> getBronnen() {
        return bronnen;
    }

    public void setBronnen(Set<Bron> bronnen) {
        this.bronnen = bronnen;
    }

    public Persoon getOuder1() {
        return ouder1;
    }

    public void setOuder1(Persoon ouder1) {
        this.ouder1 = ouder1;
    }

    public Persoon getOuder2() {
        return ouder2;
    }

    public void setOuder2(Persoon ouder2) {
        this.ouder2 = ouder2;
    }

    public Persoon getKind() {
        return kind;
    }

    public void setKind(Persoon kind) {
        this.kind = kind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EersteInschrijving)) {
            return false;
        }

        EersteInschrijving that = (EersteInschrijving) o;

        if (bronnen != null ? !bronnen.equals(that.bronnen) : that.bronnen != null) {
            return false;
        }
        if (kind != null ? !kind.equals(that.kind) : that.kind != null) {
            return false;
        }
        if (ouder1 != null ? !ouder1.equals(that.ouder1) : that.ouder1 != null) {
            return false;
        }
        if (ouder2 != null ? !ouder2.equals(that.ouder2) : that.ouder2 != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = bronnen != null ? bronnen.hashCode() : 0;
        result = 31 * result + (ouder1 != null ? ouder1.hashCode() : 0);
        result = 31 * result + (ouder2 != null ? ouder2.hashCode() : 0);
        result = 31 * result + (kind != null ? kind.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EersteInschrijving{" +
                "bronnen=" + bronnen +
                ", ouder1=" + ouder1 +
                ", ouder2=" + ouder2 +
                ", kind=" + kind +
                '}';
    }
}
