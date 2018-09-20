/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bijhouding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import nl.bzk.brp.business.dto.BerichtVerwerkingsResultaat;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.validatie.Melding;

/**
 * DTO klasse die het resultaat van een bijhouding representeert en onder andere de tijdens de bericht uitvoering
 * opgetreden meldingen en bijgehouden/aangepaste personen bevat.
 */
public class BijhoudingResultaat extends BerichtVerwerkingsResultaat {

    private List<Persoon>  bijgehoudenPersonen;
    private Date           tijdstipRegistratie;

    /**
     * Standaard constructor die direct de velden initialiseert, waarbij de resultaatcode standaard op "Goed" wordt
     * gezet en de meldingen worden geinitialiseerd naar de opgegeven meldingen of een lege lijst van meldingen indien
     * opgegeven meldingen <code>null</code> zijn.
     * De lijst van bijgehouden/aangepaste personen wordt ook reeds geinitialiseerd naar een lege lijst.
     *
     * @param meldingen de meldingen waarvoor het berichtresultaat geinstantieerd dient te worden.
     */
    public BijhoudingResultaat(final List<Melding> meldingen) {
        super(meldingen);

        bijgehoudenPersonen = new ArrayList<Persoon>();
    }

    /**
     * Retourneert de (onaanpasbare) lijst van personen die zijn bijgehouden/aangepast tijdens de bericht verwerking.
     *
     * @return de lijst van bijgehouden/aangepaste personen.
     */
    public List<Persoon> getBijgehoudenPersonen() {
        return Collections.unmodifiableList(bijgehoudenPersonen);
    }

    /**
     * Voegt een persoon toe aan de huidige reeks van bijgehouden/aangepaste personen.
     *
     * @param persoon De toe te voegen persoon.
     */
    public void voegPersoonToe(final Persoon persoon) {
        bijgehoudenPersonen.add(persoon);
    }

    /**
     * Voegt een lijst van personen toe aan de huidige reeks van bijgehouden/aangepaste personen.
     *
     * @param personen De toe te voegen personen.
     */
    public void voegPersonenToe(final List<Persoon> personen) {
        this.bijgehoudenPersonen.addAll(personen);
    }

    /**
     * Retourneert het tijdstip waarop de registratie van de bijhoudingen heeft plaatsgevonden.
     *
     * @return het tijdstip waarop de registratie van de bijhoudingen heeft plaatsgevonden.
     */
    public Date getTijdstipRegistratie() {
        final Date resultaat;
        if (tijdstipRegistratie == null) {
            resultaat = null;
        } else {
            resultaat = (Date) tijdstipRegistratie.clone();
        }
        return resultaat;
    }

    /**
     * Zet het tijdstip waarop de registratie van de bijhoudingen heeft plaatsgevonden.
     *
     * @param tijdstipRegistratie het tijdstip waarop de registratie van de bijhoudingen heeft plaatsgevonden.
     */
    public void setTijdstipRegistratie(final Date tijdstipRegistratie) {
        if (tijdstipRegistratie == null) {
            this.tijdstipRegistratie = null;
        } else {
            this.tijdstipRegistratie = (Date) tijdstipRegistratie.clone();
        }
    }
}
