/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ISO31661Alpha2Attribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;


/**
 * Onafhankelijke staat, zoals door Nederland erkend, of een gebied.
 * <p/>
 * De erkenning van de ene staat door de andere, is een eenzijdige rechtshandeling van die andere staat, die daarmee te kennen geeft dat hij de nieuw
 * erkende staat als lid van het internationale statensysteem aanvaardt en alle gevolgen van die erkenning wil accepteren. Naast staten bevat de tabel ook
 * incidenteel gebieden voor die gevallen waarin erkenning door de Nederlandse staat niet aan de orde is (geweest).
 * <p/>
 * 1. Bij de uitbreiding met ISO codes is een cruciale keuze: voegen we de tweeletterige code ('ISO 3166-1 alpha 2') toe, of gaan we voor de drieletterige
 * code. Omdat de tweeletterige iets vaker wordt gebruikt, en omdat deze ook (gratis) kan worden gebruikt zonder dat daarvoor iets hoeft te worden
 * aangeschaft, is de keus op de tweeletterige code gevallen. RvdP 5 september 2011. 2. Naam is aangepast naar Land of gebied, conform overeenkomstige
 * wijzigingen uit afstemming gegevensset. RvdP 20 december 2013
 */
@Entity
@Table(schema = "Kern", name = "LandGebied")
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class LandGebied extends AbstractLandGebied {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected LandGebied() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code                   code van LandGebied.
     * @param naam                   naam van LandGebied.
     * @param iSO31661Alpha2         iSO31661Alpha2 van LandGebied.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van LandGebied.
     * @param datumEindeGeldigheid   datumEindeGeldigheid van LandGebied.
     */
    public LandGebied(final LandGebiedCodeAttribuut code, final NaamEnumeratiewaardeAttribuut naam,
        final ISO31661Alpha2Attribuut iSO31661Alpha2, final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid)
    {
        super(code, naam, iSO31661Alpha2, datumAanvangGeldigheid, datumEindeGeldigheid);
    }

}
