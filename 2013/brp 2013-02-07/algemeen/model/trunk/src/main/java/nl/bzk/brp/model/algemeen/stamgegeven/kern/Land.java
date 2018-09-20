/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ISO31661Alpha2;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Landcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.basis.AbstractLand;


/**
 * Onafhankelijke staat, zoals door Nederland erkend.
 *
 * De erkenning van de ene staat door de andere, is een eenzijdige rechtshandeling van die andere staat, die daarmee te
 * kennen geeft dat hij de nieuw erkende staat als lid van het internationale statensysteem aanvaardt en alle gevolgen
 * van die erkenning wil accepteren. De voorkomens van "Land" zijn hier beperkt tot die staten die door de Staat der
 * Nederlanden erkend zijn, dan wel (in geval van historische gegevens) erkend zijn geweest.
 *
 * Bij de uitbreiding met ISO codes is een cruciale keuze: voegen we de tweeletterige code ('ISO 3166-1 alpha 2') toe,
 * of gaan we voor de drieletterige code.
 * Omdat de tweeletterige iets vaker wordt gebruikt, en omdat deze ook (gratis) kan worden gebruikt zonder dat daarvoor
 * iets hoeft te worden aangeschaft, is de keus op de tweeletterige code gevallen.
 * RvdP 5 september 2011.
 *
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "Land")
public class Land extends AbstractLand {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected Land() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van Land.
     * @param naam naam van Land.
     * @param iSO31661Alpha2 iSO31661Alpha2 van Land.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Land.
     * @param datumEindeGeldigheid datumEindeGeldigheid van Land.
     */
    public Land(final Landcode code, final NaamEnumeratiewaarde naam, final ISO31661Alpha2 iSO31661Alpha2,
            final Datum datumAanvangGeldigheid, final Datum datumEindeGeldigheid)
    {
        super(code, naam, iSO31661Alpha2, datumAanvangGeldigheid, datumEindeGeldigheid);
    }

}
