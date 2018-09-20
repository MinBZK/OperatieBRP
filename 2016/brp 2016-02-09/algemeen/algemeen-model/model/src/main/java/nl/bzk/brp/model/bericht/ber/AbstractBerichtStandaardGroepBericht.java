/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.BerichtdataAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.logisch.ber.BerichtStandaardGroepBasis;

/**
 * Logischerwijs zou de standaard-groep volgen na de identiteit. Deze is echter verplaatst vanwege problemen in het
 * (Java) generatieproces omdat er bepaalde verwijzigingen niet lekker liepen. Verplaatsen naar onderen loste dit
 * probleem op.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractBerichtStandaardGroepBericht implements Groep, BerichtStandaardGroepBasis, MetaIdentificeerbaar {

    private static final Integer META_ID = 11441;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(9301, 4808, 5612);
    private AdministratieveHandelingBericht administratieveHandeling;
    private BerichtdataAttribuut data;
    private BerichtBericht antwoordOp;

    /**
     * Retourneert Administratieve handeling van Standaard.
     *
     * @return Administratieve handeling.
     */
    public AdministratieveHandelingBericht getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BerichtdataAttribuut getData() {
        return data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BerichtBericht getAntwoordOp() {
        return antwoordOp;
    }

    /**
     * Zet Administratieve handeling van Standaard.
     *
     * @param administratieveHandeling Administratieve handeling.
     */
    public void setAdministratieveHandeling(final AdministratieveHandelingBericht administratieveHandeling) {
        this.administratieveHandeling = administratieveHandeling;
    }

    /**
     * Zet Data van Standaard.
     *
     * @param data Data.
     */
    public void setData(final BerichtdataAttribuut data) {
        this.data = data;
    }

    /**
     * Zet Antwoord op van Standaard.
     *
     * @param antwoordOp Antwoord op.
     */
    public void setAntwoordOp(final BerichtBericht antwoordOp) {
        this.antwoordOp = antwoordOp;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (data != null) {
            attributen.add(data);
        }
        return attributen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getMetaId() {
        return META_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean bevatElementMetMetaId(final Integer metaId) {
        return ONDERLIGGENDE_ATTRIBUTEN.contains(metaId);
    }

}
