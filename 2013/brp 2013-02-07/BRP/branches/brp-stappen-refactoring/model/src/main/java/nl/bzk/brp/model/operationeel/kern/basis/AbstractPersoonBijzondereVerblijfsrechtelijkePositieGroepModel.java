/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.BijzondereVerblijfsrechtelijkePositie;
import nl.bzk.brp.model.logisch.kern.PersoonBijzondereVerblijfsrechtelijkePositieGroep;
import nl.bzk.brp.model.logisch.kern.basis.PersoonBijzondereVerblijfsrechtelijkePositieGroepBasis;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * Gegevens over verblijf op basis van een bijzondere verblijfsrechtelijke positie.vreemdelingenwet.
 *
 * Er zijn personen vrijgesteld van de toetsing aan de vreemdelingenwet, o.a. op basis van het grond van het
 * Diplomatenverdrag en het Consulaire verdrag, of omdat het om aan de NAVO krijgsmacht gelieerde personen zijn, waarvan
 * verblijf in Nederland een wezenlijk Nederlands belang wordt geacht.
 *
 * 1. Hernoemd tot uitzondering vreemdelingenwet, en tot echte groep gemaakt: naast geprivilegieerden in toekomst ook
 * militair personeel. Wordt dus geen Ja/Nee tje c.q. indicatie, maar een verwijzing naar een reden.
 * RvdP 27 november 2012.
 * 2. Vorm van historie: alleen formeel.
 * Motivatie: je bent vrijgesteld, of niet, en niet 'over' een (materi�le) periode.
 * Verder is het voor toepassen van het gegeven alleen relevant of je NU vrijgesteld bent. Alleen een formele tijdslijn
 * dus.
 * RvdP 10 jan 2012, aangepast 27 november 2012.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-21 13:38:20.
 * Gegenereerd op: Mon Jan 21 13:42:15 CET 2013.
 */
@MappedSuperclass
public abstract class AbstractPersoonBijzondereVerblijfsrechtelijkePositieGroepModel implements
        PersoonBijzondereVerblijfsrechtelijkePositieGroepBasis
{

    @ManyToOne
    @JoinColumn(name = "BVP")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private BijzondereVerblijfsrechtelijkePositie bijzondereVerblijfsrechtelijkePositie;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractPersoonBijzondereVerblijfsrechtelijkePositieGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param bijzondereVerblijfsrechtelijkePositie bijzondereVerblijfsrechtelijkePositie van Bijzondere
     *            verblijfsrechtelijke
     *            positie.
     */
    public AbstractPersoonBijzondereVerblijfsrechtelijkePositieGroepModel(
            final BijzondereVerblijfsrechtelijkePositie bijzondereVerblijfsrechtelijkePositie)
    {
        this.bijzondereVerblijfsrechtelijkePositie = bijzondereVerblijfsrechtelijkePositie;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonBijzondereVerblijfsrechtelijkePositieGroep te kopieren groep.
     */
    public AbstractPersoonBijzondereVerblijfsrechtelijkePositieGroepModel(
            final PersoonBijzondereVerblijfsrechtelijkePositieGroep persoonBijzondereVerblijfsrechtelijkePositieGroep)
    {
        this.bijzondereVerblijfsrechtelijkePositie =
            persoonBijzondereVerblijfsrechtelijkePositieGroep.getBijzondereVerblijfsrechtelijkePositie();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BijzondereVerblijfsrechtelijkePositie getBijzondereVerblijfsrechtelijkePositie() {
        return bijzondereVerblijfsrechtelijkePositie;
    }

}
