/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.basis.AbstractPartij;


/**
 * Een voor de BRP relevant overheidsorgaan of derde, zoals bedoeld in de Wet BRP, of onderdeel daarvan, die met een
 * bepaalde gerechtvaardigde doelstelling is aangesloten op de BRP.
 *
 * Dit betreft - onder andere - gemeenten, (overige) overheidsorganen en derden.
 *
 * 1. Er is voor gekozen om gemeenten, overige overheidsorganen etc te zien als soorten partijen, en ze allemaal op te
 * nemen in een partij tabel. Van oudsher voorkomende tabellen zoals 'de gemeentetabel' is daarmee een subtype van de
 * partij tabel geworden. RvdP 29 augustus 2011
 *
 * Voor Partij, maar ook Partij/Rol worden toekomst-mutaties toegestaan. Dat betekent dat een normale 'update via
 * trigger' methode NIET werkt voor de A laag: het zetten op "Materieel = Nee" gaat NIET via een update trigger op de
 * C(+D) laag tabel.
 * Derhalve suggestie voor de DBA: de 'materieel?' velden niet via een trigger maar via een view c.q. een 'do instead'
 * actie??
 * RvdP 10 oktober 2011
 *
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "Partij")
public class Partij extends AbstractPartij {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected Partij() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     * CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param naam naam van Partij.
     * @param soort soort van Partij.
     * @param code code van Partij.
     * @param datumEinde datumEinde van Partij.
     * @param datumAanvang datumAanvang van Partij.
     * @param sector sector van Partij.
     * @param voortzettendeGemeente voortzettendeGemeente van Partij.
     * @param onderdeelVan onderdeelVan van Partij.
     * @param gemeenteStatusHis gemeenteStatusHis van Partij.
     * @param partijStatusHis partijStatusHis van Partij.
     */
    public Partij(final NaamEnumeratiewaarde naam, final SoortPartij soort, final GemeenteCode code,
            final Datum datumEinde, final Datum datumAanvang, final Sector sector, final Partij voortzettendeGemeente,
            final Partij onderdeelVan, final StatusHistorie gemeenteStatusHis, final StatusHistorie partijStatusHis)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(naam, soort, code, datumEinde, datumAanvang, sector, voortzettendeGemeente, onderdeelVan,
                gemeenteStatusHis, partijStatusHis);
    }

    @Override
    // Public override van de getID, zodat die gebruikt kan worden in de dataaccess en business.
    @Transient
    public Short getID() {
        return super.getID();
    }

}
