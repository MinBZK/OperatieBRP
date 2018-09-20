/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.BijzondereVerblijfsrechtelijkePositie;
import nl.bzk.brp.model.logisch.kern.PersoonBijzondereVerblijfsrechtelijkePositieGroep;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractPersoonBijzondereVerblijfsrechtelijkePositieGroepModel;


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
 * Metaregister versie: 1.1.15.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-11-28 16:34:59.
 * Gegenereerd op: Wed Nov 28 16:37:19 CET 2012.
 */
@Embeddable
public class PersoonBijzondereVerblijfsrechtelijkePositieGroepModel extends
        AbstractPersoonBijzondereVerblijfsrechtelijkePositieGroepModel implements
        PersoonBijzondereVerblijfsrechtelijkePositieGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected PersoonBijzondereVerblijfsrechtelijkePositieGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param bijzondereVerblijfsrechtelijkePositie bijzondereVerblijfsrechtelijkePositie van Bijzondere
     *            verblijfsrechtelijke
     *            positie.
     */
    public PersoonBijzondereVerblijfsrechtelijkePositieGroepModel(
        final BijzondereVerblijfsrechtelijkePositie bijzondereVerblijfsrechtelijkePositie)
    {
        super(bijzondereVerblijfsrechtelijkePositie);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonBijzondereVerblijfsrechtelijkePositieGroep te kopieren groep.
     */
    public PersoonBijzondereVerblijfsrechtelijkePositieGroepModel(
        final PersoonBijzondereVerblijfsrechtelijkePositieGroep persoonBijzondereVerblijfsrechtelijkePositieGroep)
    {
        super(persoonBijzondereVerblijfsrechtelijkePositieGroep);
    }

}
