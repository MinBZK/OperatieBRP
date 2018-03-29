/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3;

import java.util.List;
import java.util.Optional;
import nl.bzk.algemeenbrp.util.xml.annotation.ElementList;
import nl.bzk.migratiebrp.conversie.model.Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Deze class representeert een LO3 Categorie Stapel. Dit is een lijst met LO3 categorieen die samen de actuele en
 * historische gegevens van deze categorie representeren.
 *
 * De volgorde is van oud naar nieuw. Dus bij een Lo3 Stapel van n elementen is Lo3Stapel.get(0) de oudste en
 * Lo3Stapel.get(n-1) die nieuwste (laatste) wijziging.
 *
 * Deze class is immutable en threadsafe.
 * @param <T> een Lo3Categorie
 */
public final class Lo3Stapel<T extends Lo3CategorieInhoud> extends Stapel<Lo3Categorie<T>> {

    private Lo3Categorie<T> actueelVoorkomen;

    /**
     * Maakt een LO3-categorie stapel aan.
     * @param categorieen de lijst met LO3 categorieen. Deze mag niet null of leeg zijn.
     * @throws IllegalArgumentException als categorieen een lege lijst is
     * @throws NullPointerException als categorieen null is
     */
    public Lo3Stapel(@ElementList(name = "categorieen", inline = true, type = Lo3Categorie.class) final List<Lo3Categorie<T>> categorieen) {
        super(categorieen);
        bepaalLo3ActueelVoorkomen();
    }

    /**
     * Geef de waarde van categorieen.
     * @return de lijst met LO3 categorieen (is niet null of leeg).
     */
    @ElementList(name = "categorieen", inline = true, type = Lo3Categorie.class)
    public List<Lo3Categorie<T>> getCategorieen() {
        return getElementen();
    }

    private void bepaalLo3ActueelVoorkomen() {
        for (final Lo3Categorie<T> voorkomen : getCategorieen()) {
            if (!voorkomen.isAfsluitendVoorkomen()) {
                final Lo3Herkomst lo3Herkomst = voorkomen.getLo3Herkomst();
                if (lo3Herkomst != null && lo3Herkomst.isLo3ActueelVoorkomen()) {
                    actueelVoorkomen = voorkomen;
                }
            }
        }
    }

    /**
     * Geeft het actuele voorkomen volgens LO3 terug (voorkomen=0) als deze in de stapel zit. null als dit niet zo is.
     * @return het actuele voorkomen volgens LO3 mits deze in de stapel voorkomt, anders null
     */
    public Lo3Categorie<T> getLo3ActueelVoorkomen() {
        return actueelVoorkomen;
    }

    /**
     * Geeft true terug als het actuele voorkomen in deze stapel voorkomt.
     * @return true als het actuele voorkomen in deze stapel voorkomt.
     */
    public boolean bevatLo3ActueelVoorkomen() {
        return actueelVoorkomen != null;
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof Lo3Stapel && new EqualsBuilder().appendSuper(super.equals(other)).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).toHashCode();
    }

    /**
     * Zoekt het meest recente voorkomen. Dit wordt bepaalt adhv de datum ingang geldigheid. LET OP: Deze methode kan
     * een voorkomen terug geven dat niet gelijk is aan het actuele voorkomen.
     * @return het meest recente voorkomen
     */
    public Optional<Lo3Categorie<T>> zoekMeestRecentVoorkomen() {
        return getCategorieen().stream()
                .sorted((o1, o2) -> o2.getHistorie().getIngangsdatumGeldigheid().compareTo(o1.getHistorie().getIngangsdatumGeldigheid()))
                .findFirst();
    }
}
