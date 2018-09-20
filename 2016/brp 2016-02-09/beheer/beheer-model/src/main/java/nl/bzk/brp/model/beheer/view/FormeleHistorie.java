/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.view;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Historie embeddable.
 *
 * @param <T> his record type
 */
@Embeddable
@SuppressWarnings("checkstyle:designforextension")
public class FormeleHistorie<T> implements Historie<T> {

    /**
     * Relatie naar actie inhoud.
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "actieinh")
    @OrderBy
    @Fetch(value = FetchMode.SELECT)
    private Set<T> inhoud = new HashSet<>();

    @Transient
    private final Set<T> geldigheid = new HashSet<>();

    /**
     * Relatie als actie verval.
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "actieverval")
    @OrderBy
    @Fetch(value = FetchMode.SELECT)
    private Set<T> verval = new HashSet<>();

    /**
     * Relatie als actie verval tbv levering mutaties.
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "actievervaltbvlevmuts")
    @OrderBy
    @Fetch(value = FetchMode.SELECT)
    private Set<T> vervalTbvMutatie = new HashSet<>();

    /**
     * Verzameling met formele historische inhoud.
     *
     * @return de formele historische inhoud.
     */
    @Override
    public Set<T> getInhoud() {
        return inhoud;
    }

    /**
     * Zet de verzameling met formele historische inhoud.
     *
     * @param inhoud de formele historische inhoud.
     */
    public void setInhoud(final Set<T> inhoud) {
        this.inhoud = inhoud;
    }

    /**
     * Verzameling met verval waarden.
     *
     * @return De verval waarden.
     */
    @Override
    public Set<T> getVerval() {
        return verval;
    }

    /**
     * Zet de verzameling met verval waarden.
     *
     * @param verval De verval waarden.
     */
    public void setVerval(final Set<T> verval) {
        this.verval = verval;
    }

    /**
     * Verzameling met verval waarden t.b.v. mutatie (MUTS).
     *
     * @return De verval waarden t.b.v. mutatie.
     */
    @Override
    public Set<T> getVervalTbvMutatie() {
        return vervalTbvMutatie;
    }

    /**
     * Zet de verzameling met verval waarden t.b.v. mutatie (MUTS).
     *
     * @param vervalTbvMutatie De verval waarden t.b.v. mutatie.
     */
    public void setVervalTbvMutatie(final Set<T> vervalTbvMutatie) {
        this.vervalTbvMutatie = vervalTbvMutatie;
    }

    /**
     * Verzameling met de geldigheden.
     *
     * @return De geldigheden.
     */
    @Override
    public Set<T> getGeldigheid() {
        return geldigheid;
    }
}
