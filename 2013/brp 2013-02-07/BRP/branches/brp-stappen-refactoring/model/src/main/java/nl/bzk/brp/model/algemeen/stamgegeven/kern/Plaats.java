/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LangeNaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Woonplaatscode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.basis.AbstractPlaats;
import nl.bzk.brp.model.basis.GeldigheidsPeriode;
import nl.bzk.brp.util.DatumUtil;


/**
 * De woonplaatsen, zoals onderhouden vanuit de BAG.
 *
 * De inhoud van de woonplaatsentabel wordt overgenomen vanuit de BAG. Qua vorm wijkt deze wel af, zo wordt er apart
 * bijgehouden welke gemeenten er zijn, terwijl de BAG deze in ��n en dezelfde tabel heeft gestopt.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.DynamischStamgegevensModelGenerator.
 * Metaregister versie: 1.1.8.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-11-27 10:41:47.
 * Gegenereerd op: Tue Nov 27 10:43:33 CET 2012.
 */
@Entity
@Table(schema = "Kern", name = "Plaats")
public class Plaats extends AbstractPlaats implements GeldigheidsPeriode {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected Plaats() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van Plaats.
     * @param naam naam van Plaats.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Plaats.
     * @param datumEindeGeldigheid datumEindeGeldigheid van Plaats.
     */
    public Plaats(final Woonplaatscode code, final LangeNaamEnumeratiewaarde naam, final Datum datumAanvangGeldigheid,
            final Datum datumEindeGeldigheid)
    {
        super(code, naam, datumAanvangGeldigheid, datumEindeGeldigheid);
    }

    @Override
    public boolean isGeldigOp(final Datum peilDatum) {
        if (peilDatum == null) {
            throw new IllegalArgumentException("Peildatum kan niet leeg zijn voor geldigheid op plaats.");
        }
        return DatumUtil.isGeldigOp(getDatumAanvangGeldigheid(), getDatumEindeGeldigheid(), peilDatum);
    }

    @Override
    public boolean isGeldigPeriode(final Datum beginDatum, final Datum eindDatum) {
        if (beginDatum != null && eindDatum != null && beginDatum.equals(eindDatum)) {
            throw new IllegalArgumentException("Begin datum en eind datum kunnen niet dezelfde zijn.");
        }
        return DatumUtil.isDatumsGeldigOpPeriode(getDatumAanvangGeldigheid(), getDatumEindeGeldigheid(),
            beginDatum, eindDatum);
    }
}
