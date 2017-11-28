/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.objectsleutel;


import java.security.SecureRandom;
import java.util.Objects;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;

/**
 * Een objectsleutel wijst een root entiteit aan in de BRP database. Dit kan een Persoon, Relatie of Betrokkenheid zijn. Een objectsleutel kan
 * gemaskeerd worden om gebruikt te worden in berichten van de BRP koppelvlakken. De wijze waarop een sleutel wordt gemaskeerd kan verschillen per
 * soort entiteit. De objectsleutels die personen in de BRP aanwijzen moeten de achterliggende database id maskeren.
 * <p/>
 * Objectsleutels kunnen optioneel een versie bevatten, hiermee kan een objectsleutel tijdelijk worden gemaakt. Voor personen geldt dat een objectsleutel
 * niet meer geldig is nadat een persoon is gewijzigd want dan gaat het versienummer van een persoon omhoog.
 */
@Bedrijfsregel(Regel.R1834)
public final class ObjectSleutel {

    private final long databaseId;
    private final ObjectSleutelSoort soort;
    private final Long versie;

    /**
     * Maakt een nieuw ObjectSleutel object.
     * @param databaseId de database id
     * @param soort het soort entiteit dat wordt aangewezen met deze sleutel
     * @param versie de versie van de entiteit die wordt aangewezen met deze sleutel, mag null zijn behalve als <code>soort</code> gelijk is aan {@link
     * ObjectSleutelSoort#PERSOON}
     */
    ObjectSleutel(final long databaseId, final ObjectSleutelSoort soort, final Long versie) {
        Objects.requireNonNull(soort, "soort mag niet null zijn");
        if (soort.isVersieVerplicht) {
            Objects.requireNonNull(versie, "versie mag niet null zijn");
        }
        this.databaseId = databaseId;
        this.soort = soort;
        this.versie = versie;
    }

    /**
     * Geef de waarde van databaseId.
     * @return databaseId
     */
    public long getDatabaseId() {
        return databaseId;
    }

    /**
     * Geef de waarde van versie of null als deze objectsleutel geen versie heeft.
     * @return versie of null
     */
    public Long getVersie() {
        return versie;
    }

    /**
     * Dit maskeert deze sleutels als een string die kan worden gebruikt in BRP XML berichten.
     * @return de gemaskeerde sleutel
     */
    public String maskeren() {
        return ObjectSleutelSoort.maskeren(this);
    }

    /**
     * Ontmaskerd een gemaskeerde sleutel.
     * @param gemaskeerdeSleutel de gemaskeerde sleutel
     * @param soort het soort sleutel
     * @return de ontmaskerde objectsleutel
     * @throws OngeldigeObjectSleutelException wanneerde gemaskeerde sleutel ongeldig is
     */
    public static ObjectSleutel ontmaskeren(final String gemaskeerdeSleutel, final ObjectSleutelSoort soort) throws OngeldigeObjectSleutelException {
        return soort.ontmaskeren(gemaskeerdeSleutel);
    }

    /**
     * Objectsleutels kunnen verschillende soorten entiteiten aanwijzen.
     */
    enum ObjectSleutelSoort {

        PERSOON(true),
        RELATIE(false),
        BETROKKENHEID(false),
        ONDERZOEK(false);

        private static final long VERSIE_MASK = 0x2CB4_9CFD_B03F_2AA8L;
        private static final long ID_MASK = 0x2B5B_EDB6_53E3_200BL;
        private static final String SEPARATOR = "_";

        private boolean isVersieVerplicht;

        ObjectSleutelSoort(final boolean isVersieVerplicht) {
            this.isVersieVerplicht = isVersieVerplicht;
        }

        private ObjectSleutel ontmaskeren(final String gemaskeerdeSleutel) throws OngeldigeObjectSleutelException {
            if (this.equals(PERSOON)) {
                return ontmaskerenPersoonSleutel(gemaskeerdeSleutel);
            } else {
                try {
                    return new ObjectSleutel(Long.parseLong(gemaskeerdeSleutel), this, null);
                } catch (NumberFormatException nfe) {
                    throw new OngeldigeObjectSleutelException(Regel.R1833, nfe);
                }
            }
        }

        private ObjectSleutel ontmaskerenPersoonSleutel(final String gemaskeerdeSleutel) throws OngeldigeObjectSleutelException {
            final int randomMaskSeparatorIndex = gemaskeerdeSleutel.indexOf(SEPARATOR);
            final int versionSeparatorIndex = gemaskeerdeSleutel.lastIndexOf(SEPARATOR);
            if (randomMaskSeparatorIndex > 0 && versionSeparatorIndex > 0 && randomMaskSeparatorIndex != versionSeparatorIndex) {
                try {
                    final long randomMask = Long.parseLong(gemaskeerdeSleutel.substring(0, randomMaskSeparatorIndex));
                    final long
                            versie =
                            Long.parseLong(gemaskeerdeSleutel.substring(randomMaskSeparatorIndex + 1, versionSeparatorIndex)) ^ randomMask ^ VERSIE_MASK;
                    final long databaseId = Long.valueOf(gemaskeerdeSleutel.substring(versionSeparatorIndex + 1)) ^ randomMask ^ ID_MASK;
                    return new ObjectSleutel(databaseId, PERSOON, versie);
                } catch (NumberFormatException nfe) {
                    throw new OngeldigeObjectSleutelException(Regel.R1833, nfe);
                }
            }
            throw new OngeldigeObjectSleutelException(Regel.R1833);
        }

        private static String maskeren(final ObjectSleutel objectSleutel) {
            if (PERSOON.equals(objectSleutel.soort)) {
                return maskerenPersoonSleutel(objectSleutel);
            } else {
                return String.valueOf(objectSleutel.getDatabaseId());
            }
        }

        private static String maskerenPersoonSleutel(final ObjectSleutel objectSleutel) {
            final long randomMask = getRandomMask();
            final long gemaskeerdeVersie = objectSleutel.versie ^ VERSIE_MASK ^ randomMask;
            final long gemaskeerdeDatabaseId = objectSleutel.databaseId ^ ID_MASK ^ randomMask;
            return String.format("%s%s%s%s%s", String.valueOf(randomMask), SEPARATOR, String.valueOf(gemaskeerdeVersie), SEPARATOR,
                    String.valueOf(gemaskeerdeDatabaseId));
        }

        private static long getRandomMask() {
            final SecureRandom random = new SecureRandom();
            long result = random.nextLong();
            while (result < 0L) {
                result = random.nextLong();
            }
            return result;
        }
    }
}
