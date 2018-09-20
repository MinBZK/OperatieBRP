/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bijhouding;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import nl.bzk.brp.business.stappen.BerichtVerwerkingsResultaat;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.validatie.Melding;


/**
 * DTO klasse die het resultaat van een bijhouding representeert en onder andere de tijdens de bericht uitvoering
 * opgetreden meldingen en bijgehouden/aangepaste personen bevat.
 */
public class BijhoudingResultaat extends BerichtVerwerkingsResultaat {

    private Set<Persoon> bijgehoudenPersonen;
    private Date tijdstipRegistratie;

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

        bijgehoudenPersonen = new TreeSet<Persoon>(new Comparator<Persoon>() {
            @Override
            public int compare(final Persoon persoon1, final Persoon persoon2) {
                //TODO refactor naar gebruik van CompareToBuilder
                if (persoon2.getIdentificatienummers() == null
                        || persoon2.getIdentificatienummers().getBurgerservicenummer() == null)
                {
                    return -1;
                } else if (persoon1.getIdentificatienummers() == null
                        || persoon1.getIdentificatienummers().getBurgerservicenummer() == null)
                {
                    return 1;
                } else {
                    return persoon1.getIdentificatienummers().getBurgerservicenummer().getWaarde()
                            .compareTo(
                                    persoon2.getIdentificatienummers().getBurgerservicenummer().getWaarde());
                }
            }
        });
    }

    /**
     * Retourneert de (onaanpasbare) lijst van personen die zijn bijgehouden/aangepast tijdens de bericht verwerking.
     *
     * @return de lijst van bijgehouden/aangepaste personen.
     */
    public Set<Persoon> getBijgehoudenPersonen() {
        return Collections.unmodifiableSet(bijgehoudenPersonen);
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
        bijgehoudenPersonen.addAll(personen);
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
     * @param newTijdstipRegistratie het tijdstip waarop de registratie van de bijhoudingen heeft plaatsgevonden.
     */
    public void setTijdstipRegistratie(final Date newTijdstipRegistratie) {
        if (newTijdstipRegistratie == null) {
            this.tijdstipRegistratie = null;
        } else {
            this.tijdstipRegistratie = (Date) newTijdstipRegistratie.clone();
        }
    }
}
