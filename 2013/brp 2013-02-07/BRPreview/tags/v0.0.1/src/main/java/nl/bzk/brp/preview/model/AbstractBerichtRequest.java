/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;


/**
 * POJO voor het bericht van de BRP.
 */
public abstract class AbstractBerichtRequest {

    /** De kenmerken. */
    private BerichtKenmerken kenmerken;

    /** De verwerking. */
    private Verwerking verwerking;

    /** De meldingen. */
    private List<Melding>    meldingen;

    /**
     * Constructor.
     */
    public AbstractBerichtRequest() {
    }

    /**
     * Constructor met berichtkenmerken.
     *
     * @param berichtKenmerken de bericht kenmerken
     */
    public AbstractBerichtRequest(final BerichtKenmerken berichtKenmerken) {
        kenmerken = berichtKenmerken;
    }

    /**
     *Geef de kenmerken.
     *
     * @return the kenmerken
     */
    public final BerichtKenmerken getKenmerken() {
        return kenmerken;
    }

    /**
     * Zet de kenmerken.
     *
     * @param kenmerken the new kenmerken
     */
    public final void setKenmerken(final BerichtKenmerken kenmerken) {
        this.kenmerken = kenmerken;
    }

    /**
     * Geef de verwerking.
     *
     * @return de verwerking
     */
    public final Verwerking getVerwerking() {
        return verwerking;
    }

    /**
     * Zet de verwerking.
     *
     * @param berichtVerwerking de new verwerking
     */
    public final void setVerwerking(final Verwerking berichtVerwerking) {
        verwerking = berichtVerwerking;
    }

    /**
     * Geef de meldingen.
     *
     * @return de meldingen
     */
    public final List<Melding> getMeldingen() {
        return meldingen;
    }

    /**
     * Zet de meldingen.
     *
     * @param meldingLijst de new meldingen
     */
    public final void setMeldingen(final List<Melding> meldingLijst) {
        meldingen = meldingLijst;
    }

    /**
     * Geef de soort bijhouding, implementeer in afgeleide types.
     *
     * @return de soort bijhouding
     */
    @JsonIgnore
    public abstract OndersteundeBijhoudingsTypes getSoortBijhouding();

    /**
     * Valideer.
     */
    @JsonIgnore
    public void valideer() {
        if (getKenmerken() == null) {
            throw new IllegalStateException("Kenmerken ontbreken");
        }
        if (getKenmerken().getVerzendendePartij() == null) {
            throw new IllegalStateException("Verzendende partij ontbreekt");
        }
        if (getKenmerken().getBurgerZakenModuleNaam() == null) {
            throw new IllegalStateException("Verzendende partij ontbreekt");
        }
        if (getVerwerking() == null) {
            throw new IllegalStateException("Verwerkingstatus ontbreekt");
        }
        if (getVerwerking().getVerwerkingsmoment() == null) {
            throw new IllegalStateException("Verwerkingmoment ontbreekt");
        }
        if (getVerwerking().getStatus() == null) {
            throw new IllegalStateException("Verwerkingmoment ontbreekt");
        }
    }

    /**
     * Creeer bericht tekst.
     *
     * @return de string
     */
    @JsonIgnore
    public abstract String creeerBerichtTekst();

    /**
     * Creeer bsn lijst.
     *
     * @return de list
     */
    @JsonIgnore
    public abstract List<Integer> creeerBsnLijst();

    /**
     * Creeer details tekst.
     *
     * @return de string
     */
    @JsonIgnore
    public String creeerDetailsTekst() {
        String tekst;

        if (getVerwerking().getStatus() == VerwerkingStatus.GESLAAGD) {
            tekst = creeerDetailTekstVoorGevolg();
        } else {
            tekst = "";
        }

        if (getMeldingen() != null && getMeldingen().size() != 0) {
            if (!tekst.equalsIgnoreCase("")) {
                tekst += "\n";
            }
            tekst += creeerDetailTekstVoorMeldingen(getMeldingen());
        }

        return tekst;
    }

    /**
     * Creeer generieke tekst voor meldingen.
     *
     * @param meldingen de meldingen
     * @return de string
     */
    protected String creeerGeneriekeTekstVoorMeldingen(final List<Melding> meldingen) {
        String tekst;
        if (meldingen == null || meldingen.size() == 0) {
            tekst = "";
        } else {
            tekst = " " + meldingen.size();
            if (meldingen.size() == 1) {
                tekst += " melding.";
            } else {
                tekst += " meldingen.";
            }
        }
        return tekst;
    }

    /**
     * Creeer detail tekst voor gevolg.
     *
     * @return de string
     */
    protected abstract String creeerDetailTekstVoorGevolg();

    /**
     * Creeer prevalidatie tekst.
     *
     * @param prevalidatie de prevalidatie
     * @return de string
     */
    protected final String creeerPrevalidatieTekst(final boolean prevalidatie) {
        String tekst;
        if (prevalidatie) {
            tekst = " prevalidatie";
        } else {
            tekst = "";
        }
        return tekst;
    }

    /**
     * Creeer detail tekst voor meldingen.
     *
     * @param meldingen de meldingen
     * @return de string
     */
    protected String creeerDetailTekstVoorMeldingen(final List<Melding> meldingen) {
        String tekst = "";
        if (meldingen != null && meldingen.size() != 0) {
            for (Melding melding : meldingen) {
                if (!tekst.equalsIgnoreCase("")) {
                    tekst += "\n";
                }
                tekst += melding.getSoort().getNaam() + ": " + melding.getTekst();
            }
        }
        return tekst;
    }

}
