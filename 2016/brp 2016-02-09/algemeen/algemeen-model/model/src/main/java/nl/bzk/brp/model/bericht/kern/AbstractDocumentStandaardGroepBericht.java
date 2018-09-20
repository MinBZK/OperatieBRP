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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AktenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentIdentificatieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieGroepBericht;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.DocumentStandaardGroepBasis;

/**
 * Vorm van historie: formeel. Motivatie: het objecttype wordt gebruikt voor de verantwoording van een specifieke BRP
 * actie. Denkbaar is dat twee verschillende BRP acties verwijzen naar hetzelfde Document; relevant is welke gegevens er
 * toen geregistreerd stonden bij het Document, vandaar dat formele historie relevant is. NB: dit onderdeel van het
 * model is nog in ontwikkeling. Denkbaar is dat de modellering anders wordt.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractDocumentStandaardGroepBericht extends AbstractFormeleHistorieGroepBericht implements Groep, DocumentStandaardGroepBasis,
        MetaIdentificeerbaar
{

    private static final Integer META_ID = 3784;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(3160, 3786, 3162, 3139);
    private DocumentIdentificatieAttribuut identificatie;
    private AktenummerAttribuut aktenummer;
    private DocumentOmschrijvingAttribuut omschrijving;
    private String partijCode;
    private PartijAttribuut partij;

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentIdentificatieAttribuut getIdentificatie() {
        return identificatie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AktenummerAttribuut getAktenummer() {
        return aktenummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentOmschrijvingAttribuut getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert Partij van Standaard.
     *
     * @return Partij.
     */
    public String getPartijCode() {
        return partijCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PartijAttribuut getPartij() {
        return partij;
    }

    /**
     * Zet Identificatie van Standaard.
     *
     * @param identificatie Identificatie.
     */
    public void setIdentificatie(final DocumentIdentificatieAttribuut identificatie) {
        this.identificatie = identificatie;
    }

    /**
     * Zet Aktenummer van Standaard.
     *
     * @param aktenummer Aktenummer.
     */
    public void setAktenummer(final AktenummerAttribuut aktenummer) {
        this.aktenummer = aktenummer;
    }

    /**
     * Zet Omschrijving van Standaard.
     *
     * @param omschrijving Omschrijving.
     */
    public void setOmschrijving(final DocumentOmschrijvingAttribuut omschrijving) {
        this.omschrijving = omschrijving;
    }

    /**
     * Zet Partij van Standaard.
     *
     * @param partijCode Partij.
     */
    public void setPartijCode(final String partijCode) {
        this.partijCode = partijCode;
    }

    /**
     * Zet Partij van Standaard.
     *
     * @param partij Partij.
     */
    public void setPartij(final PartijAttribuut partij) {
        this.partij = partij;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (identificatie != null) {
            attributen.add(identificatie);
        }
        if (aktenummer != null) {
            attributen.add(aktenummer);
        }
        if (omschrijving != null) {
            attributen.add(omschrijving);
        }
        if (partij != null) {
            attributen.add(partij);
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
