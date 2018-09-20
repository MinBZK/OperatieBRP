/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * De Class AdministratieveHandelingBericht, het basisbericht waarvan de andere berichten afstammen. Voor onbekende berichten is dit de basis.
 */
public class AdministratieveHandelingBericht {

    private static final String VERWERKINGSWIJZE_PREVALIDATIE = "P";

    private final Long id;

    private final String soort;

    private final String partij;

    private final Date tijdOntlening;

    private final String toelichtingOntlening;

    private final Date tijdRegistratie;

    private final String bzm;

    private final String verwerkingswijze;

    private final List<Melding> meldingen = new ArrayList<Melding>();

    /**
     * Instantieert een administratieve handeling bericht op basis van een administratieve handeling.
     * @param administratieveHandeling de administratieve handeling.
     */
    public AdministratieveHandelingBericht(final AdministratieveHandeling administratieveHandeling) {
        id = administratieveHandeling.getAdministratieveHandelingId();
        partij = administratieveHandeling.getPartij();
        tijdOntlening = administratieveHandeling.getTijdstipOntlening();
        soort = administratieveHandeling.getSoortAdministratieveHandeling();
        tijdRegistratie = administratieveHandeling.getTijdstipRegistratie();
        toelichtingOntlening = administratieveHandeling.getToelichtingOntlening();
        bzm = administratieveHandeling.getBzm();
        meldingen.addAll(administratieveHandeling.getMeldingen());
        verwerkingswijze = administratieveHandeling.getVerwerkingswijze();
    }

    public Long getId() {
        return id;
    }

    public String getSoort() {
        return soort;
    }

    public String getPartij() {
        return partij;
    }

    public String getToelichtingOntlening() {
        return toelichtingOntlening;
    }

    public Date getTijdOntlening() {
        return tijdOntlening;
    }

    public Date getTijdRegistratie() {
        return tijdRegistratie;
    }

    public String getBzm() {
        return bzm;
    }

    public List<Melding> getMeldingen() {
        return meldingen;
    }

    /**
     * Controleert of de waarde gelijk is aan prevalidatie.
     *
     * @return true, als waarde gelijk is aan prevalidatie
     */
    public boolean isPrevalidatie() {
        if (verwerkingswijze == null) {
            return false;
        }
        return VERWERKINGSWIJZE_PREVALIDATIE.equalsIgnoreCase(verwerkingswijze);
    }

    /**
     * Creer tekst voor de administratieve handeling.
     *
     * @return De tekst voor de handeling als string.
     */
    public String creeerBerichtTekst() {
        String format = "Onbekende administratieve handeling op %s in %s van het soort %s.";
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return String.format(format, dateFormat.format(tijdRegistratie), partij, soort);
    }

    /**
     * Creeer details tekst.
     *
     * @return de string
     */
    public String creeerDetailsTekst() {
        String tekst = "";
        if (getMeldingen() != null && getMeldingen().size() != 0) {
            if (!tekst.equalsIgnoreCase("")) {
                tekst += "\n";
            }
            tekst += creeerDetailTekstVoorMeldingen(getMeldingen());
        }

        return tekst;
    }

    /**
     * Creeer detail tekst voor meldingen.
     *
     * @param meldingenParam de meldingen
     * @return de string
     */
    private String creeerDetailTekstVoorMeldingen(final List<Melding> meldingenParam) {
        final StringBuffer tekst = new StringBuffer();
        if (meldingenParam != null && meldingenParam.size() != 0) {
            for (Melding melding : meldingenParam) {
                if (tekst.length() != 0) {
                    tekst.append("\n");
                }
                tekst.append(melding.getSoort().getNaam());
                tekst.append(": ");
                tekst.append(melding.getTekst());
            }
        }
        return tekst.toString();
    }

    /**
     * Creeer de bsn lijst, leeg in deze superklasse omdat het berichttype hier niet bekend is. Hierdoor
     * is niet te bepalen welke personen door de administratieve handeling geraakt worden.
     * @return De lijst van bsn nummers, in dit geval null.
     */
    public List<Integer> creeerBsnLijst() {
        return null;
    }

    /**
     * Geeft de soort bijhouding voor dit type, in dit geval ONBEKEND omdat het om de default gaat.
     * @return De soort administratieve handeling.
     */
    public OndersteundeBijhoudingsTypes getSoortBijhouding() {
        return OndersteundeBijhoudingsTypes.ONBEKEND;
    }

}
