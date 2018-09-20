/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.migblok;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.ProcessInstantieIDAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.migblok.RedenBlokkeringAttribuut;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.migblok.Blokkering;


/**
 * Indicatie dat de persoon (tijdelijk) geblokkeerd is voor mutaties omdat deze aan het verhuizen is van een GBA naar BRP gemeente (of vice versa).
 * <p/>
 * De blokkering tabel heeft bestaansrecht totdat alle gemeenten over zijn naar de BRP, daarna kan deze vervallen.
 * <p/>
 * Deze tabel was reeds gemaakt door migratie en is achteraf opgenomen in BMR. Om het aantal wijzigingen te beperken in de implementatie (mede gezien de
 * tijdelijkheid van de tabel), is de gemeente geen Partij maar een vrije code.
 */
@Entity
@Table(schema = "MigBlok", name = "Blokkering")
public class BlokkeringModel extends AbstractBlokkeringModel implements Blokkering, ModelIdentificeerbaar<Integer> {

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     */
    protected BlokkeringModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param administratienummer    administratienummer van Blokkering.
     * @param redenBlokkering        redenBlokkering van Blokkering.
     * @param processInstantieID     processInstantieID van Blokkering.
     * @param lO3GemeenteVestiging   lO3GemeenteVestiging van Blokkering.
     * @param lO3GemeenteRegistratie lO3GemeenteRegistratie van Blokkering.
     * @param tijdstipRegistratie    tijdstipRegistratie van Blokkering.
     */
    public BlokkeringModel(final AdministratienummerAttribuut administratienummer,
        final RedenBlokkeringAttribuut redenBlokkering, final ProcessInstantieIDAttribuut processInstantieID,
        final LO3GemeenteCodeAttribuut lO3GemeenteVestiging, final LO3GemeenteCodeAttribuut lO3GemeenteRegistratie,
        final DatumTijdAttribuut tijdstipRegistratie)
    {
        super(administratienummer, redenBlokkering, processInstantieID, lO3GemeenteVestiging, lO3GemeenteRegistratie,
            tijdstipRegistratie);
    }

}
