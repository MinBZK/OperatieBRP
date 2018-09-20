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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingVerblijfsrechtAttribuut;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieGroepBericht;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonVerblijfsrechtGroepBasis;

/**
 * Deze groep bevat geen materiele historie (meer). De IND stuurt namelijk alleen de actuele status. Daarmee is het
 * verleden niet meer betrouwbaar (er zijn geen correcties doorgevoerd op de materiele tijdlijn). Daarom wordt er
 * uitsluitend 1 actueel voorkomen geregistreerd die aanvangt op een bepaalde datum.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonVerblijfsrechtGroepBericht extends AbstractFormeleHistorieGroepBericht implements Groep,
        PersoonVerblijfsrechtGroepBasis, MetaIdentificeerbaar
{

    private static final Integer META_ID = 3517;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(3310, 21315, 3325, 3481);
    private String aanduidingVerblijfsrechtCode;
    private AanduidingVerblijfsrechtAttribuut aanduidingVerblijfsrecht;
    private DatumEvtDeelsOnbekendAttribuut datumAanvangVerblijfsrecht;
    private DatumEvtDeelsOnbekendAttribuut datumMededelingVerblijfsrecht;
    private DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeVerblijfsrecht;

    /**
     * Retourneert Aanduiding verblijfsrecht van Verblijfsrecht.
     *
     * @return Aanduiding verblijfsrecht.
     */
    public String getAanduidingVerblijfsrechtCode() {
        return aanduidingVerblijfsrechtCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AanduidingVerblijfsrechtAttribuut getAanduidingVerblijfsrecht() {
        return aanduidingVerblijfsrecht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangVerblijfsrecht() {
        return datumAanvangVerblijfsrecht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumMededelingVerblijfsrecht() {
        return datumMededelingVerblijfsrecht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumVoorzienEindeVerblijfsrecht() {
        return datumVoorzienEindeVerblijfsrecht;
    }

    /**
     * Zet Aanduiding verblijfsrecht van Verblijfsrecht.
     *
     * @param aanduidingVerblijfsrechtCode Aanduiding verblijfsrecht.
     */
    public void setAanduidingVerblijfsrechtCode(final String aanduidingVerblijfsrechtCode) {
        this.aanduidingVerblijfsrechtCode = aanduidingVerblijfsrechtCode;
    }

    /**
     * Zet Aanduiding verblijfsrecht van Verblijfsrecht.
     *
     * @param aanduidingVerblijfsrecht Aanduiding verblijfsrecht.
     */
    public void setAanduidingVerblijfsrecht(final AanduidingVerblijfsrechtAttribuut aanduidingVerblijfsrecht) {
        this.aanduidingVerblijfsrecht = aanduidingVerblijfsrecht;
    }

    /**
     * Zet Datum aanvang verblijfsrecht van Verblijfsrecht.
     *
     * @param datumAanvangVerblijfsrecht Datum aanvang verblijfsrecht.
     */
    public void setDatumAanvangVerblijfsrecht(final DatumEvtDeelsOnbekendAttribuut datumAanvangVerblijfsrecht) {
        this.datumAanvangVerblijfsrecht = datumAanvangVerblijfsrecht;
    }

    /**
     * Zet Datum mededeling verblijfsrecht van Verblijfsrecht.
     *
     * @param datumMededelingVerblijfsrecht Datum mededeling verblijfsrecht.
     */
    public void setDatumMededelingVerblijfsrecht(final DatumEvtDeelsOnbekendAttribuut datumMededelingVerblijfsrecht) {
        this.datumMededelingVerblijfsrecht = datumMededelingVerblijfsrecht;
    }

    /**
     * Zet Datum voorzien einde verblijfsrecht van Verblijfsrecht.
     *
     * @param datumVoorzienEindeVerblijfsrecht Datum voorzien einde verblijfsrecht.
     */
    public void setDatumVoorzienEindeVerblijfsrecht(final DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeVerblijfsrecht) {
        this.datumVoorzienEindeVerblijfsrecht = datumVoorzienEindeVerblijfsrecht;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (aanduidingVerblijfsrecht != null) {
            attributen.add(aanduidingVerblijfsrecht);
        }
        if (datumAanvangVerblijfsrecht != null) {
            attributen.add(datumAanvangVerblijfsrecht);
        }
        if (datumMededelingVerblijfsrecht != null) {
            attributen.add(datumMededelingVerblijfsrecht);
        }
        if (datumVoorzienEindeVerblijfsrecht != null) {
            attributen.add(datumVoorzienEindeVerblijfsrecht);
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
