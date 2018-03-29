/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.internbericht.selectie;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.util.Set;

/**
 * SelectieTaakResultaat. Resultaat boekhouding object voor terugkoppeling.
 */
@JsonAutoDetect
public final class SelectieTaakResultaat {
    private int selectieRunId;
    private String uuid;
    //gevuld vanuit verwerker
    private int schrijfTaken;
    private int aantalAfnemerindicatieTaken;
    private int aantalVerwerktePersonenNetwerk;
    private TypeResultaat type;
    //alleen gevuld vanuit schrijver en afnemerindicatie service
    private Integer taakId;
    //alleen gevuld vanuit verwerker voor markering ongeldige taken
    private Set<Integer> ongeldigeTaken;

    /**
     * Gets uuid.
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets uuid.
     * @param uuid the uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets type.
     * @return the type
     */
    public TypeResultaat getType() {
        return type;
    }

    /**
     * Sets type.
     * @param type the type
     */
    public void setType(TypeResultaat type) {
        this.type = type;
    }

    /**
     * Gets selectie run id.
     * @return the selectie run id
     */
    public int getSelectieRunId() {
        return selectieRunId;
    }

    /**
     * Sets selectie run id.
     * @param selectieRunId the selectie run id
     */
    public void setSelectieRunId(int selectieRunId) {
        this.selectieRunId = selectieRunId;
    }

    /**
     * Gets schrijf taken.
     * @return the schrijf taken
     */
    public int getSchrijfTaken() {
        return schrijfTaken;
    }

    /**
     * Geeft het aantal plaats afnemerindicatie taken welke volgen uit deze verwerktaak.
     * @return aantal aantal afnemerindicatie taken
     */
    public int getAantalAfnemerindicatieTaken() {
        return aantalAfnemerindicatieTaken;
    }

    /**
     * Sets schrijf taken.
     * @param schrijfTaken the schrijf taken
     */
    public void setSchrijfTaken(int schrijfTaken) {
        this.schrijfTaken = schrijfTaken;
    }

    /**
     * Zet het aantal plaats afnemerindicatie taken welke volgen uit deze verwerktaak.
     * @param aantalAfnemerindicatieTaken the schrijf taken
     */
    public void setAantalAfnemerindicatieTaken(final int aantalAfnemerindicatieTaken) {
        this.aantalAfnemerindicatieTaken = aantalAfnemerindicatieTaken;
    }

    /**
     * Geeft het aantal verwerkte personen welke volgen uit deze verwerktaak.
     * @return aantal aantal afnemerindicatie taken
     */
    public int getAantalVerwerktePersonenNetwerk() {
        return aantalVerwerktePersonenNetwerk;
    }

    /**
     * Zet het aantal verwerkte personen netwerk welke volgen uit deze verwerktaak.
     * @param aantalVerwerktePersonenNetwerk het aantal verwerkte personen
     */
    public void setAantalVerwerktePersonenNetwerk(final int aantalVerwerktePersonenNetwerk) {
        this.aantalVerwerktePersonenNetwerk = aantalVerwerktePersonenNetwerk;
    }

    /**
     * Sets taak id.
     * @param taakId the taak id
     */
    public void setTaakId(final Integer taakId) {
        this.taakId = taakId;
    }

    /**
     * Gets taak id.
     * @return the taak id
     */
    public Integer getTaakId() {
        return taakId;
    }

    /**
     * Gets ongeldige taken.
     * @return the ongeldige taken
     */
    public Set<Integer> getOngeldigeTaken() {
        return ongeldigeTaken;
    }

    /**
     * Sets ongeldige taken.
     * @param ongeldigeTaken the ongeldige taken
     */
    public void setOngeldigeTaken(Set<Integer> ongeldigeTaken) {
        this.ongeldigeTaken = ongeldigeTaken;
    }
}
