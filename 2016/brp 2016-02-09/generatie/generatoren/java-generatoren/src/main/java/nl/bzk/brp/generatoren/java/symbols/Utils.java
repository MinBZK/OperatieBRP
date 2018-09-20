/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.symbols;

import nl.bzk.brp.generatoren.algemeen.basis.AbstractGenerator;
import nl.bzk.brp.generatoren.algemeen.common.BmrSoortInhoud;
import nl.bzk.brp.generatoren.algemeen.common.BmrTargetPlatform;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.BasisType;
import nl.bzk.brp.metaregister.model.ObjectType;

/**
 * Utility class voor generatie van symbol table.
 */
public final class Utils {

    /**
     * Private constructor voor utility class.
     */
    private Utils() {
    }

    /**
     * Geeft het basistype van een attribuut in Java.
     *
     * @param attribuut Attribuut waarvan het basistype bepaald moet worden.
     * @param generator Generator waarin het symbool wordt gebruikt.
     * @return Basistype van het attribuut.
     */
    public static String getJavaBasisType(final Attribuut attribuut, final AbstractGenerator generator) {
        String expressieType;
        String javaBasisType;

        if (generator.isAttribuutTypeAttribuut(attribuut)) {
            AttribuutType attribuutType = generator.getBmrDao().getAttribuutTypeVoorAttribuut(attribuut);
            expressieType =
                    generator.getBmrDao().getBasisTypeVoorAttribuutType(attribuutType, BmrTargetPlatform.EXPRESSIES)
                            .getBasisType().getNaam();
            javaBasisType = SymbolTableConstants.EXPRESSIETYPE_NAAR_JAVA_TYPE.get(expressieType);
        } else {
            // Het attribuut is een object type. De expressietaal herkent dit niet als apart type (in tegenstelling
            // tot Persoon e.d.). Het attribuut wordt afgebeeld op de logische identiteit van het object type.
            ObjectType ot = generator.getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);

            if (ot.getSoortInhoud() != BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode()) {
                Attribuut logischeIdentiteit = generator.bepaalLogischeIdentiteitVoorStamgegeven(ot);
                AttribuutType attribuutType = generator.getBmrDao().getAttribuutTypeVoorAttribuut(logischeIdentiteit);
                final BasisType bt =
                        generator.getBmrDao().getBasisTypeVoorAttribuutType(attribuutType, BmrTargetPlatform.JAVA).
                                getBasisType();
                JavaType jt = JavaGeneratieUtil.bepaalJavaBasisTypeVoorAttribuutType(attribuutType, bt);
                javaBasisType = jt.getNaam();

            } else {
                expressieType = SymbolTableConstants.STRING_TYPE;
                javaBasisType = SymbolTableConstants.EXPRESSIETYPE_NAAR_JAVA_TYPE.get(expressieType);
            }
        }

        return javaBasisType;
    }

    /**
     * Geeft het (basis)type van een attribuut in de expressietaal.
     *
     * @param attribuut Attribuut waarvan het basistype bepaald moet worden.
     * @param generator Generator waarin het symbool wordt gebruikt.
     * @return Basistype van het attribuut.
     */
    public static String getExpressieBasisType(final Attribuut attribuut, final AbstractGenerator generator) {
        String basisType;

        if (generator.isAttribuutTypeAttribuut(attribuut)) {
            AttribuutType attribuutType = generator.getBmrDao().getAttribuutTypeVoorAttribuut(attribuut);

            basisType =
                    generator.getBmrDao().getBasisTypeVoorAttribuutType(attribuutType, BmrTargetPlatform.EXPRESSIES)
                            .getBasisType().getNaam();

            if (basisType.equals(SymbolTableConstants.NUMBER_TYPE) && attribuutType.getMaximumLengte() != null) {
                final int maxLengte = attribuutType.getMaximumLengte();
                if (maxLengte > JavaGeneratieUtil.MAX_LENGTH_INTEGER) {
                    basisType = SymbolTableConstants.BIG_NUMBER_TYPE;
                }
            }
        } else {
            /*
             Het attribuut is een object type. De expressietaal herkent dit niet als apart type (in tegenstelling
             tot Persoon e.d.). Het attribuut wordt afgebeeld op de logische identiteit van het object type.
             */
            ObjectType ot = generator.getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);

            if (ot.getSoortInhoud() != BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode()) {
                Attribuut logischeIdentiteit = generator.bepaalLogischeIdentiteitVoorStamgegeven(ot);
                AttribuutType attribuutType = generator.getBmrDao().getAttribuutTypeVoorAttribuut(logischeIdentiteit);
                basisType =
                        generator.getBmrDao().getBasisTypeVoorAttribuutType(attribuutType, BmrTargetPlatform.EXPRESSIES)
                                .getBasisType().getNaam();
            } else {
                basisType = SymbolTableConstants.STRING_TYPE;
            }
        }

        return basisType;
    }

    /**
     * Geeft TRUE als het attribuut een statisch stamgegeven is, anders FALSE.
     *
     * @param attribuut Te onderzoeken attribuut.
     * @return TRUE als het attribuut een statisch stamgegeven is.
     */
    public static boolean isStatischStamgegevenAttribuut(final Attribuut attribuut) {
        String typeCode = attribuut.getType().getSoortElement().getCode();
        Character inhoud = attribuut.getType().getSoortInhoud();
        return "OT".equals(typeCode) && Character.valueOf('X').equals(inhoud);
    }
}
