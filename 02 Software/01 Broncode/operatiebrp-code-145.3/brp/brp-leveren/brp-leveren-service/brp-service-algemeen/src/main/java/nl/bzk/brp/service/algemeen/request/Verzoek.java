/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.request;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;

/**
 * Basisklasse voor verzoeken.
 */
public interface Verzoek {

    /**
     * Geef de stuurgegevens van dit verzoek.
     * @return de stuurgegevens
     */
    Stuurgegevens getStuurgegevens();

    /**
     * Zet de soort dienst.
     * @param soortDienst soort dienst
     */
    void setSoortDienst(SoortDienst soortDienst);


    /**
     * Geef de soort dienst van dit verzoek.
     * @return de soort dienst
     */
    SoortDienst getSoortDienst();

    /**
     * Zet het xml bericht.
     * @param xmlBericht get xml bericht
     */
    void setXmlBericht(String xmlBericht);

    /**
     * Geef het xml bericht.
     * @return het xml bericht
     */
    String getXmlBericht();

    /**
     * Zet de OIN gegeven van het verzoek.
     * @param oin een OIN
     */
    void setOin(OIN oin);

    /**
     * Geef de OIN van de ondertekenaar van dit verzoek.
     * @return de OIN van de ondertekenaar
     */
    OIN getOin();

    /**
     * Geeft aan of verzoek wel of niet via koppelvlak is gedaan.
     * @return true indien {@link Verzoek} via koppelvlak is binnengekomen.
     */
    boolean isBrpKoppelvlakVerzoek();

    /**
     * Zet waarde van brpKoppelvlakVerzoek (geeft aan of verzoek wel of niet via koppelvlak is gedaan)
     * @param brpKoppelvlakVerzoek het {@link Verzoek} is wel/niet via koppelvlak binnen gekomen.
     */
    void setBrpKoppelvlakVerzoek(boolean brpKoppelvlakVerzoek);

    /**
     * De basisklasse voor berichtgegevens. Bevat de communicatie ID.
     */
    class BerichtGegevens {
        private String communicatieId;

        public final String getCommunicatieId() {
            return communicatieId;
        }

        public final void setCommunicatieId(final String communicatieId) {
            this.communicatieId = communicatieId;
        }
    }

    /**
     * De stuurgegevens van een verzoekbericht.
     */
    final class Stuurgegevens extends BerichtGegevens {
        private ZonedDateTime tijdstipVerzending;
        private String zendendePartijCode;
        private String zendendSysteem;
        private String referentieNummer;

        public ZonedDateTime getTijdstipVerzending() {
            return tijdstipVerzending;
        }

        public void setTijdstipVerzending(final ZonedDateTime tijdstipVerzending) {
            this.tijdstipVerzending = tijdstipVerzending;
        }

        public String getZendendePartijCode() {
            return zendendePartijCode;
        }

        public void setZendendePartijCode(final String zendendePartijCode) {
            this.zendendePartijCode = zendendePartijCode;
        }

        public String getZendendSysteem() {
            return zendendSysteem;
        }

        public void setZendendSysteem(final String zendendSysteem) {
            this.zendendSysteem = zendendSysteem;
        }

        public String getReferentieNummer() {
            return referentieNummer;
        }

        public void setReferentieNummer(final String referentieNummer) {
            this.referentieNummer = referentieNummer;
        }
    }
}
