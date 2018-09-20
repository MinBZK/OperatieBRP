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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AutoriteitVanAfgifteReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReisdocumentNummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingInhoudingVermissingReisdocumentAttribuut;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieGroepBericht;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonReisdocumentStandaardGroepBasis;

/**
 * 1. Vorm van historie: alleen formeel. Motivatie: de gegevens van een reisdocument zijn enerzijds de gegevens die in
 * het document staan, zoals lengte van de houder. Anderzijds zijn het gegevens die normaliter éénmalig wijzigen, zoals
 * reden vervallen. Omdat hetzelfde reisdocument niet tweemaal wordt uitgegeven, is formele historie voldoende.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonReisdocumentStandaardGroepBericht extends AbstractFormeleHistorieGroepBericht implements Groep,
        PersoonReisdocumentStandaardGroepBasis, MetaIdentificeerbaar
{

    private static final Integer META_ID = 3577;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(3741, 3744, 6126, 3745, 3742, 3746, 3747);
    private ReisdocumentNummerAttribuut nummer;
    private AutoriteitVanAfgifteReisdocumentCodeAttribuut autoriteitVanAfgifte;
    private DatumEvtDeelsOnbekendAttribuut datumIngangDocument;
    private DatumEvtDeelsOnbekendAttribuut datumEindeDocument;
    private DatumEvtDeelsOnbekendAttribuut datumUitgifte;
    private DatumEvtDeelsOnbekendAttribuut datumInhoudingVermissing;
    private String aanduidingInhoudingVermissingCode;
    private AanduidingInhoudingVermissingReisdocumentAttribuut aanduidingInhoudingVermissing;

    /**
     * {@inheritDoc}
     */
    @Override
    public ReisdocumentNummerAttribuut getNummer() {
        return nummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AutoriteitVanAfgifteReisdocumentCodeAttribuut getAutoriteitVanAfgifte() {
        return autoriteitVanAfgifte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumIngangDocument() {
        return datumIngangDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumEindeDocument() {
        return datumEindeDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumUitgifte() {
        return datumUitgifte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumInhoudingVermissing() {
        return datumInhoudingVermissing;
    }

    /**
     * Retourneert Aanduiding inhouding/vermissing van Standaard.
     *
     * @return Aanduiding inhouding/vermissing.
     */
    public String getAanduidingInhoudingVermissingCode() {
        return aanduidingInhoudingVermissingCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AanduidingInhoudingVermissingReisdocumentAttribuut getAanduidingInhoudingVermissing() {
        return aanduidingInhoudingVermissing;
    }

    /**
     * Zet Nummer van Standaard.
     *
     * @param nummer Nummer.
     */
    public void setNummer(final ReisdocumentNummerAttribuut nummer) {
        this.nummer = nummer;
    }

    /**
     * Zet Autoriteit van afgifte van Standaard.
     *
     * @param autoriteitVanAfgifte Autoriteit van afgifte.
     */
    public void setAutoriteitVanAfgifte(final AutoriteitVanAfgifteReisdocumentCodeAttribuut autoriteitVanAfgifte) {
        this.autoriteitVanAfgifte = autoriteitVanAfgifte;
    }

    /**
     * Zet Datum ingang document van Standaard.
     *
     * @param datumIngangDocument Datum ingang document.
     */
    public void setDatumIngangDocument(final DatumEvtDeelsOnbekendAttribuut datumIngangDocument) {
        this.datumIngangDocument = datumIngangDocument;
    }

    /**
     * Zet Datum einde document van Standaard.
     *
     * @param datumEindeDocument Datum einde document.
     */
    public void setDatumEindeDocument(final DatumEvtDeelsOnbekendAttribuut datumEindeDocument) {
        this.datumEindeDocument = datumEindeDocument;
    }

    /**
     * Zet Datum uitgifte van Standaard.
     *
     * @param datumUitgifte Datum uitgifte.
     */
    public void setDatumUitgifte(final DatumEvtDeelsOnbekendAttribuut datumUitgifte) {
        this.datumUitgifte = datumUitgifte;
    }

    /**
     * Zet Datum inhouding/vermissing van Standaard.
     *
     * @param datumInhoudingVermissing Datum inhouding/vermissing.
     */
    public void setDatumInhoudingVermissing(final DatumEvtDeelsOnbekendAttribuut datumInhoudingVermissing) {
        this.datumInhoudingVermissing = datumInhoudingVermissing;
    }

    /**
     * Zet Aanduiding inhouding/vermissing van Standaard.
     *
     * @param aanduidingInhoudingVermissingCode Aanduiding inhouding/vermissing.
     */
    public void setAanduidingInhoudingVermissingCode(final String aanduidingInhoudingVermissingCode) {
        this.aanduidingInhoudingVermissingCode = aanduidingInhoudingVermissingCode;
    }

    /**
     * Zet Aanduiding inhouding/vermissing van Standaard.
     *
     * @param aanduidingInhoudingVermissing Aanduiding inhouding/vermissing.
     */
    public void setAanduidingInhoudingVermissing(final AanduidingInhoudingVermissingReisdocumentAttribuut aanduidingInhoudingVermissing) {
        this.aanduidingInhoudingVermissing = aanduidingInhoudingVermissing;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (nummer != null) {
            attributen.add(nummer);
        }
        if (autoriteitVanAfgifte != null) {
            attributen.add(autoriteitVanAfgifte);
        }
        if (datumIngangDocument != null) {
            attributen.add(datumIngangDocument);
        }
        if (datumEindeDocument != null) {
            attributen.add(datumEindeDocument);
        }
        if (datumUitgifte != null) {
            attributen.add(datumUitgifte);
        }
        if (datumInhoudingVermissing != null) {
            attributen.add(datumInhoudingVermissing);
        }
        if (aanduidingInhoudingVermissing != null) {
            attributen.add(aanduidingInhoudingVermissing);
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
