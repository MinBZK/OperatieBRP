/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.monitor.schedular;

/**
 * DTO om de telling van fouten bij te houden.
 *
 */
public class Fouten {

    private int soapfault;
    private int foutenInfo;
    private int foutenWaarschuwing;
    private int foutenFout;
    private int foutenSysteem;

    /**
     * Constructor. Zet alle attributen op waarde 0.
     */
    public Fouten() {
        soapfault = 0;
        foutenInfo = 0;
        foutenWaarschuwing = 0;
        foutenFout = 0;
        foutenSysteem = 0;
    }

    /**
     * Constructor om de attributen te zetten op een bepaalde waarden.
     *
     * @param soapfault aantal soap fouten
     * @param foutenInfo aantal fouten van de level Info
     * @param foutenWaarschuwing aantal fouten van de level Waarschuwing
     * @param foutenFout aantal fouten van de level Fout
     * @param foutenSysteem aantal fouten van de level Systeem
     */
    public Fouten(final int soapfault, final int foutenInfo, final int foutenWaarschuwing, final int foutenFout,
            final int foutenSysteem)
    {
        this.soapfault = soapfault;
        this.foutenInfo = foutenInfo;
        this.foutenWaarschuwing = foutenWaarschuwing;
        this.foutenFout = foutenFout;
        this.foutenSysteem = foutenSysteem;
    }

    public int getSoapfault() {
        return soapfault;
    }

    public void setSoapfault(final int soapfault) {
        this.soapfault = soapfault;
    }

    public int getFoutenInfo() {
        return foutenInfo;
    }

    public void setFoutenInfo(final int foutenInfo) {
        this.foutenInfo = foutenInfo;
    }

    public int getFoutenWaarschuwing() {
        return foutenWaarschuwing;
    }

    public void setFoutenWaarschuwing(final int foutenWaarschuwing) {
        this.foutenWaarschuwing = foutenWaarschuwing;
    }

    public int getFoutenFout() {
        return foutenFout;
    }

    public void setFoutenFout(final int foutenFout) {
        this.foutenFout = foutenFout;
    }

    public int getFoutenSysteem() {
        return foutenSysteem;
    }

    public void setFoutenSysteem(final int foutenSysteem) {
        this.foutenSysteem = foutenSysteem;
    }
}
