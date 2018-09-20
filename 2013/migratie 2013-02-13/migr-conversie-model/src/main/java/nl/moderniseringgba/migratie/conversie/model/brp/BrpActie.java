/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPartijCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerdragCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Een BRP actie.
 * 
 * De id (technische sleutel) wordt hier tweeledig gebruikt. Bij de conversie van Lo3 -> BRP wordt de ID gebuikt een een
 * enkele logische actie te identificeren (die technisch meerdere keren gebruikt kan worden; lees meerdere instanties
 * kan hebben). De Sync DAL zal dus een tijdelijk vertaling van conversie-actie-id naar echte-actie-id moeten hebben.
 * Bij de conversie van BRP -> Lo3 wordt het id gebruikt om te bepalen welke wijzigingen door dezelfde actie zijn
 * gedaan.
 * 
 * Deze klass is immutable en thread-safe.
 * 
 * 
 * 
 */
public final class BrpActie {

    // Aktie
    private final Long id;
    private final BrpSoortActieCode soortActieCode;
    private final BrpPartijCode partijCode;
    private final BrpVerdragCode verdragCode;
    private final BrpDatumTijd datumTijdOntlening;
    private final BrpDatumTijd datumTijdRegistratie;

    // Attribuut wat gebruikt kan worden voor de sortering indien datumTijdRegistratie gelijk is
    private final int sortering;

    private final List<BrpStapel<BrpDocumentInhoud>> documentStapels;
    private final Lo3Herkomst lo3Herkomst;

    /**
     * Maakt een BrpActie object.
     * 
     * @param id
     *            technisch id, mag niet null zijn
     * @param soortActieCode
     *            soort actie
     * @param partijCode
     *            partij, mag niet null zijn
     * @param verdragCode
     *            verdrag
     * @param datumTijdOntlening
     *            datum tijd ontlening
     * @param datumTijdRegistratie
     *            datum tijd registratie
     * @param documentStapels
     *            lijst gerelateerde documenten
     * @param sortering
     *            een int die de sortering bepaald bij het serializeren van een BRPActie
     * @param lo3Herkomst
     *            de herkomst van de BRP actie
     * @throws NullPointerException
     *             als id null is
     */
    // CHECKSTYLE:OFF - Parameters
    public BrpActie(
    // CHECKSTYLE:ON
            final Long id,
            final BrpSoortActieCode soortActieCode,
            final BrpPartijCode partijCode,
            final BrpVerdragCode verdragCode,
            final BrpDatumTijd datumTijdOntlening,
            final BrpDatumTijd datumTijdRegistratie,
            final List<BrpStapel<BrpDocumentInhoud>> documentStapels,
            final int sortering,
            final Lo3Herkomst lo3Herkomst) {
        if (id == null) {
            throw new NullPointerException("id mag niet null zijn");
        }
        if (partijCode == null) {
            throw new NullPointerException("partijCode mag niet null zijn");
        }
        this.id = id;
        this.soortActieCode = soortActieCode;
        this.partijCode = partijCode;
        this.verdragCode = verdragCode;
        this.datumTijdOntlening = datumTijdOntlening;
        this.datumTijdRegistratie = datumTijdRegistratie;
        this.documentStapels = kopieerDocumentStapelsEnZetActieInhoud(documentStapels);
        this.sortering = sortering;
        this.lo3Herkomst = lo3Herkomst;
    }

    /**
     * @param documentStapels
     *            de te kopieren lijst met document stapels.
     * @return een kopie van de lijst met document stapels waarbij voor alle groepen de actie inhoud gelijk is gezet aan
     *         de huidige actie.
     */
    private List<BrpStapel<BrpDocumentInhoud>> kopieerDocumentStapelsEnZetActieInhoud(
            final List<BrpStapel<BrpDocumentInhoud>> documentStapels) {
        List<BrpStapel<BrpDocumentInhoud>> result = null;
        if (documentStapels != null) {
            result = new ArrayList<BrpStapel<BrpDocumentInhoud>>();
            for (final BrpStapel<BrpDocumentInhoud> documentStapel : documentStapels) {
                result.add(kopieerDocumentStapelEnZetActieInhoud(documentStapel));
            }
        }
        return result;
    }

    /**
     * Deze methode kopieert de meegegeven document stapel en voegt de huidige actie als actieInhoud toe aan de brp
     * groepen in de stapel. Omdat het model immutable is moet deze gekopieert worden.
     * 
     * @param documentStapel
     *            de te kopieren document stapel
     * @return een kopie van de meegegeven document stapel met actie inhoud gezet op alle brp groepen in de stapel
     */
    private BrpStapel<BrpDocumentInhoud> kopieerDocumentStapelEnZetActieInhoud(
            final BrpStapel<BrpDocumentInhoud> documentStapel) {
        final List<BrpGroep<BrpDocumentInhoud>> groepen = new ArrayList<BrpGroep<BrpDocumentInhoud>>();
        for (final BrpGroep<BrpDocumentInhoud> brpGroep : documentStapel) {
            groepen.add(new BrpGroep<BrpDocumentInhoud>(brpGroep.getInhoud(), brpGroep.getHistorie(), this, brpGroep
                    .getActieVerval(), brpGroep.getActieGeldigheid()));
        }
        return new BrpStapel<BrpDocumentInhoud>(groepen);
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the soortActieCode
     */
    public BrpSoortActieCode getSoortActieCode() {
        return soortActieCode;
    }

    /**
     * @return the partijCode
     */
    public BrpPartijCode getPartijCode() {
        return partijCode;
    }

    /**
     * @return the verdragCode
     */
    public BrpVerdragCode getVerdragCode() {
        return verdragCode;
    }

    /**
     * @return the datumTijdOntlening
     */
    public BrpDatumTijd getDatumTijdOntlening() {
        return datumTijdOntlening;
    }

    /**
     * @return the datumTijdRegistratie
     */
    public BrpDatumTijd getDatumTijdRegistratie() {
        return datumTijdRegistratie;
    }

    /**
     * @return the sortering
     */
    public int getSortering() {
        return sortering;
    }

    /**
     * @return the stapel van documenten
     */
    public List<BrpStapel<BrpDocumentInhoud>> getDocumentStapels() {
        return documentStapels == null ? null : new ArrayList<BrpStapel<BrpDocumentInhoud>>(documentStapels);
    }

    /**
     * @return de herkomst van deze brp actie
     */
    public Lo3Herkomst getLo3Herkomst() {
        return lo3Herkomst;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpActie)) {
            return false;
        }
        final BrpActie castOther = (BrpActie) other;
        return new EqualsBuilder().append(soortActieCode, castOther.soortActieCode)
                .append(partijCode, castOther.partijCode).append(verdragCode, castOther.verdragCode)
                .append(datumTijdOntlening, castOther.datumTijdOntlening)
                .append(datumTijdRegistratie, castOther.datumTijdRegistratie)
                .append(documentStapels, castOther.documentStapels).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(soortActieCode).append(partijCode).append(verdragCode)
                .append(datumTijdOntlening).append(datumTijdRegistratie).append(documentStapels).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("soortActieCode", soortActieCode)
                .append("partijCode", partijCode).append("verdragCode", verdragCode)
                .append("datumTijdOntlening", datumTijdOntlening)
                .append("datumTijdRegistratie", datumTijdRegistratie).append("sortering", sortering)
                .append("documenten", documentStapels).toString();
    }

}
