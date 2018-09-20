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

import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.validatie.Melding;

/**
 * DTO klasse die het resultaat van een bijhouding representeert en onder andere de tijdens de bericht uitvoering
 * opgetreden meldingen en bijgehouden/aangepaste personen bevat.
 */
public class BijhoudingResultaat extends BerichtResultaat {

    private List<Persoon>  bijgehoudenPersonen;
    private Date           tijdstipRegistratie;
    private BijhoudingCode bijhoudingCode;

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

        // Bijhoudingcode wordt voorlopig nog op direct verwerkt gezet totdat we fiatering bouwen.
        bijhoudingCode = BijhoudingCode.DIRECT_VERWERKT;
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
        if (null == tijdstipRegistratie) {
            return null;
        } else {
            return (Date) tijdstipRegistratie.clone();
        }
    }

    /**
     * Zet het tijdstip waarop de registratie van de bijhoudingen heeft plaatsgevonden.
     *
     * @param tijdstipRegistratie het tijdstip waarop de registratie van de bijhoudingen heeft plaatsgevonden.
     */
    public void setTijdstipRegistratie(final Date tijdstipRegistratie) {
        this.tijdstipRegistratie = (Date) tijdstipRegistratie.clone();
    }

    /**
     * Retourneert de bijhouding code die aangeeft of een bericht direct is verwerkt of is uitgesteld.
     *
     * @return de bijhouding code die aangeeft of een bericht direct is verwerkt of is uitgesteld.
     */
    public BijhoudingCode getBijhoudingCode() {
        return bijhoudingCode;
    }

    /**
     * Zet de bijhouding code die aangeeft of een bericht direct is verwerkt of is uitgesteld.
     *
     * @param bijhoudingCode de bijhouding code die aangeeft of een bericht direct is verwerkt of is uitgesteld.
     */
    public void setBijhoudingCode(final BijhoudingCode bijhoudingCode) {
        this.bijhoudingCode = bijhoudingCode;
    }
}
