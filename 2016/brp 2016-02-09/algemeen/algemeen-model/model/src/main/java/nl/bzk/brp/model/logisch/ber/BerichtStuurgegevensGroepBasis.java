/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ber;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.SysteemNaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * De naam van de groep is gebaseerd op de naam die StUF voor dit soort gegevens hanteert. Het gaat in principe om een
 * beperkte set gegevens die "op de envelop" horen c.q. zouden kunnen staan.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface BerichtStuurgegevensGroepBasis extends Groep {

    /**
     * Retourneert Zendende systeem van Stuurgegevens.
     *
     * @return Zendende systeem.
     */
    SysteemNaamAttribuut getZendendeSysteem();

    /**
     * Retourneert Ontvangende systeem van Stuurgegevens.
     *
     * @return Ontvangende systeem.
     */
    SysteemNaamAttribuut getOntvangendeSysteem();

    /**
     * Retourneert Referentienummer van Stuurgegevens.
     *
     * @return Referentienummer.
     */
    ReferentienummerAttribuut getReferentienummer();

    /**
     * Retourneert Cross referentienummer van Stuurgegevens.
     *
     * @return Cross referentienummer.
     */
    ReferentienummerAttribuut getCrossReferentienummer();

    /**
     * Retourneert Datum/tijd verzending van Stuurgegevens.
     *
     * @return Datum/tijd verzending.
     */
    DatumTijdAttribuut getDatumTijdVerzending();

    /**
     * Retourneert Datum/tijd ontvangst van Stuurgegevens.
     *
     * @return Datum/tijd ontvangst.
     */
    DatumTijdAttribuut getDatumTijdOntvangst();

}
