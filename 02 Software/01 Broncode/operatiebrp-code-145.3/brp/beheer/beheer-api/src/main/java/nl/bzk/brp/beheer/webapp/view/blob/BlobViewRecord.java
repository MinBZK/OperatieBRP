/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.view.blob;

import java.sql.Timestamp;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;

/**
 * View op een record.
 */
public final class BlobViewRecord {

    private final Long voorkomensleutel;
    private final SortedMap<Element, Object> attributen = new TreeMap<>(BlobMapper::compareElementOnVolgnummer);

    private final BlobViewObject actieInhoud;
    private final BlobViewObject actieVerval;
    private final BlobViewObject actieAanpassingGeldigheid;
    private final BlobViewObject actieVervalTbvLeveringMutaties;
    private final Boolean indicatieVoorkomenTbvLeveringMutaties;
    private final String nadereAanduidingVerval;
    private final Integer datumAanvangGeldigheid;
    private final Integer datumEindeGeldigheid;

    private final Timestamp tijdstipRegistratie;
    private final Timestamp tijdstipVerval;

    /**
     * Constructor.
     *
     * @param blobRecord
     *            blob record
     * @param actieLocator
     *            methode om de actie te zoeken
     */
    BlobViewRecord(final BlobRecord blobRecord, final ActieLocator actieLocator) {
        voorkomensleutel = blobRecord.getVoorkomenSleutel();
        actieInhoud = actieLocator.getActie(blobRecord.getActieInhoud());
        tijdstipRegistratie = geefTijdstipRegistratie(actieInhoud);
        actieVerval = actieLocator.getActie(blobRecord.getActieVerval());
        tijdstipVerval = geefTijdstipRegistratie(actieVerval);
        actieAanpassingGeldigheid = actieLocator.getActie(blobRecord.getActieAanpassingGeldigheid());
        actieVervalTbvLeveringMutaties = actieLocator.getActie(blobRecord.getActieVervalTbvLeveringMutaties());
        indicatieVoorkomenTbvLeveringMutaties = blobRecord.isIndicatieTbvLeveringMutaties();
        nadereAanduidingVerval = blobRecord.getNadereAanduidingVerval();
        datumAanvangGeldigheid = blobRecord.getDatumAanvangGeldigheid();
        datumEindeGeldigheid = blobRecord.getDatumEindeGeldigheid();

        if (blobRecord.getAttributen() != null) {
            for (final Map.Entry<Integer, Object> attribuut : blobRecord.getAttributen().entrySet()) {
                attributen.put(Element.parseId(attribuut.getKey()), attribuut.getValue());
            }
        }
    }

    private Timestamp geefTijdstipRegistratie(final BlobViewObject actie) {
        if (actie == null) {
            return null;
        }
        final BlobViewGroep identiteitGroep = actie.getGroep(Element.ACTIE_IDENTITEIT);
        final BlobViewRecord identiteit = identiteitGroep.getRecords().iterator().next();

        final Object waarde = identiteit.getAttributen().get(Element.ACTIE_TIJDSTIPREGISTRATIE);
        final Timestamp resultaat;
        if (waarde == null) {
            resultaat = null;
        } else if (waarde instanceof Long) {
            resultaat = new Timestamp((Long) waarde);
        } else if (waarde instanceof Timestamp) {
            resultaat = (Timestamp) waarde;
        } else {
            throw new IllegalArgumentException("Tijdstip registratie van actie is van ongeldig type: " + waarde.getClass());
        }

        return resultaat;
    }

    /** @return voorkomensleutel */
    public Long getVoorkomensleutel() {
        return voorkomensleutel;
    }

    /** @return actie inhoud */
    public BlobViewObject getActieInhoud() {
        return actieInhoud;
    }

    /** @return actie verval */
    public BlobViewObject getActieVerval() {
        return actieVerval;
    }

    /** @return actie aanpassinggeldigheid */
    public BlobViewObject getActieAanpassingGeldigheid() {
        return actieAanpassingGeldigheid;
    }

    /** @return actie verval tbv levering mutatues */
    public BlobViewObject getActieVervalTbvLeveringMutaties() {
        return actieVervalTbvLeveringMutaties;
    }

    /** @return indicatie voorkomen tbv levering mutatie */
    public Boolean getIndicatieVoorkomenTbvLeveringMutaties() {
        return indicatieVoorkomenTbvLeveringMutaties;
    }

    /** @return nadere aanduiding verval */
    public String getNadereAanduidingVerval() {
        return nadereAanduidingVerval;
    }

    /** @return datum aanvang geldigheid */
    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /** @return datum einde geldigheid */
    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /** @return tijdstip registratie */
    public Timestamp getTijdstipRegistratie() {
        return tijdstipRegistratie == null ? null : new Timestamp(tijdstipRegistratie.getTime());
    }

    /** @return tijdstip verval */
    public Timestamp getTijdstipVerval() {
        return tijdstipVerval == null ? null : new Timestamp(tijdstipVerval.getTime());
    }

    /**
     * Geef de attributen (map is gesorteerd op Element.volgnummer).
     *
     * @return attributen
     */
    public SortedMap<Element, Object> getAttributen() {
        return attributen;
    }

    @Override
    public String toString() {
        return "BlobViewRecord [voorkomensleutel=" + voorkomensleutel + ", attributen=" + attributen + "]";
    }

    /**
     * Methode om een actie te zoeken.
     */
    @FunctionalInterface
    public interface ActieLocator {
        /**
         * Geef de actie met het gegeven id.
         *
         * @param id
         *            id
         * @return actie
         */
        BlobViewObject getActie(Long id);
    }

}
