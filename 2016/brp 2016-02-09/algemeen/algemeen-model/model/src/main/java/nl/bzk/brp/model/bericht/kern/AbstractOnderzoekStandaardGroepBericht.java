/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OnderzoekOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusOnderzoekAttribuut;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieGroepBericht;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.OnderzoekStandaardGroepBasis;

/**
 * Vorm van historie: formeel. Motivatie: 'onderzoek' is een construct om vast te leggen dat een bepaald gegeven
 * onderwerp is van onderzoek. Hierbij is het in principe alleen relevant of een gegeven NU in onderzoek is. Verder is
 * het voldoende om te weten of tijdens een bepaalde levering een gegeven wel of niet als 'in onderzoek' stond
 * geregistreerd. NB: de gegevens over het onderzoek zelf staan niet in de BRP, maar in bijvoorbeeld de zaaksystemen.
 * Omdat formele historie dus volstaat, wordt de materiële historie onderdrukt.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractOnderzoekStandaardGroepBericht extends AbstractFormeleHistorieGroepBericht implements Groep, OnderzoekStandaardGroepBasis,
        MetaIdentificeerbaar
{

    private static final Integer META_ID = 3774;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(3178, 10848, 3179, 3772, 10849);
    private DatumEvtDeelsOnbekendAttribuut datumAanvang;
    private DatumEvtDeelsOnbekendAttribuut verwachteAfhandeldatum;
    private DatumEvtDeelsOnbekendAttribuut datumEinde;
    private OnderzoekOmschrijvingAttribuut omschrijving;
    private StatusOnderzoekAttribuut status;

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getVerwachteAfhandeldatum() {
        return verwachteAfhandeldatum;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OnderzoekOmschrijvingAttribuut getOmschrijving() {
        return omschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StatusOnderzoekAttribuut getStatus() {
        return status;
    }

    /**
     * Zet Datum aanvang van Standaard.
     *
     * @param datumAanvang Datum aanvang.
     */
    public void setDatumAanvang(final DatumEvtDeelsOnbekendAttribuut datumAanvang) {
        this.datumAanvang = datumAanvang;
    }

    /**
     * Zet Verwachte afhandeldatum van Standaard.
     *
     * @param verwachteAfhandeldatum Verwachte afhandeldatum.
     */
    public void setVerwachteAfhandeldatum(final DatumEvtDeelsOnbekendAttribuut verwachteAfhandeldatum) {
        this.verwachteAfhandeldatum = verwachteAfhandeldatum;
    }

    /**
     * Zet Datum einde van Standaard.
     *
     * @param datumEinde Datum einde.
     */
    public void setDatumEinde(final DatumEvtDeelsOnbekendAttribuut datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Zet Omschrijving van Standaard.
     *
     * @param omschrijving Omschrijving.
     */
    public void setOmschrijving(final OnderzoekOmschrijvingAttribuut omschrijving) {
        this.omschrijving = omschrijving;
    }

    /**
     * Zet Status van Standaard.
     *
     * @param status Status.
     */
    public void setStatus(final StatusOnderzoekAttribuut status) {
        this.status = status;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (datumAanvang != null) {
            attributen.add(datumAanvang);
        }
        if (verwachteAfhandeldatum != null) {
            attributen.add(verwachteAfhandeldatum);
        }
        if (datumEinde != null) {
            attributen.add(datumEinde);
        }
        if (omschrijving != null) {
            attributen.add(omschrijving);
        }
        if (status != null) {
            attributen.add(status);
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
