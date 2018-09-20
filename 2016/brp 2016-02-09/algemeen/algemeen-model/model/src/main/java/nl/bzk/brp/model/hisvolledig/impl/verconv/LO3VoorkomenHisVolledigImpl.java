/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.verconv;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3CategorieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.verconv.LO3VoorkomenHisVolledig;

/**
 * HisVolledig klasse voor LO3 Voorkomen.
 *
 */
@Entity
@Table(schema = "VerConv", name = "LO3Voorkomen")
public class LO3VoorkomenHisVolledigImpl extends AbstractLO3VoorkomenHisVolledigImpl implements HisVolledigImpl,
        LO3VoorkomenHisVolledig
{

    /**
     * Default contructor voor JPA.
     *
     */
    protected LO3VoorkomenHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param lO3Bericht lO3Bericht van LO3 Voorkomen.
     * @param lO3Categorie lO3Categorie van LO3 Voorkomen.
     * @param lO3Stapelvolgnummer lO3Stapelvolgnummer van LO3 Voorkomen.
     * @param lO3Voorkomenvolgnummer lO3Voorkomenvolgnummer van LO3 Voorkomen.
     */
    public LO3VoorkomenHisVolledigImpl(
        final LO3BerichtHisVolledigImpl lO3Bericht,
        final LO3CategorieAttribuut lO3Categorie,
        final VolgnummerAttribuut lO3Stapelvolgnummer,
        final VolgnummerAttribuut lO3Voorkomenvolgnummer)
    {
        super(lO3Bericht, lO3Categorie, lO3Stapelvolgnummer, lO3Voorkomenvolgnummer);
    }

}
