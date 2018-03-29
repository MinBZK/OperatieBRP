/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.lo3naarbrp;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDerdeHeeftGezagIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpMigratieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderlijkGezagInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpStaatloosIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerstrekkingsbeperkingIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.bzk.migratiebrp.test.common.output.TestOutput;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

/**
 * Deze utility wordt gebruikt om de 'expected' xml van testgevallen aan te passen voor de nieuwe conversie regels rond
 * historie die gemaakt zijn om brp leveren te verbeteren. Zie ook Jira story ORANJE-3258.
 *
 * De class wordt uitgevoerd als Main class, met als parameters één of meer paden waaronder de aan te passen expecteds
 * gevonden worden.
 */
public class ConverteerBrpExpecteds {
    /**
     * De main van deze class.
     * @param args een lijst met paden waarbinnen de expecteds zullen worden aangepast.
     */
    public static void main(final String[] args) {
        for (final String rootPath : args) {
            final File root = new File(rootPath);
            for (final File brpXmlFile : FileUtils.listFiles(root, new BrpXmlFileFilter(), TrueFileFilter.INSTANCE)) {
                verwerkXml(brpXmlFile);
            }
        }
    }

    private static void verwerkXml(final File brpXmlFile) {
        final BrpPersoonslijst brpIn = TestOutput.readXml(BrpPersoonslijst.class, brpXmlFile);
        final BrpPersoonslijst brpOut = verwerkPl(brpIn);
        if (brpIn != brpOut) {
            TestOutput.outputXmlEnHtml(brpOut, brpXmlFile, null);
        }
    }

    private static BrpPersoonslijst verwerkPl(final BrpPersoonslijst brpIn) {
        boolean plAangepast = false;

        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder(brpIn);

        final BrpStapel<BrpGeboorteInhoud> geboorteStapel = verwerkFormeleHistorie(brpIn.getGeboorteStapel());
        if (geboorteStapel != brpIn.getGeboorteStapel()) {
            plAangepast = true;
            builder.geboorteStapel(geboorteStapel);
        }

        final BrpStapel<BrpNaamgebruikInhoud> naamgebruikStapel = verwerkFormeleHistorie(brpIn.getNaamgebruikStapel());
        if (naamgebruikStapel != brpIn.getNaamgebruikStapel()) {
            plAangepast = true;
            builder.naamgebruikStapel(naamgebruikStapel);
        }

        final BrpStapel<BrpAdresInhoud> adresStapel = verwerkMaterieleHistorie(brpIn.getAdresStapel());
        if (adresStapel != brpIn.getAdresStapel()) {
            plAangepast = true;
            builder.adresStapel(adresStapel);
        }

        final BrpStapel<BrpMigratieInhoud> migratieStapel = verwerkMaterieleHistorie(brpIn.getMigratieStapel());
        if (migratieStapel != brpIn.getMigratieStapel()) {
            plAangepast = true;
            builder.migratieStapel(migratieStapel);
        }

        final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtStapel = verwerkMaterieleHistorie(brpIn.getGeslachtsaanduidingStapel());
        if (geslachtStapel != brpIn.getGeslachtsaanduidingStapel()) {
            plAangepast = true;
            builder.geslachtsaanduidingStapel(geslachtStapel);
        }

        final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldenaamStapel = verwerkMaterieleHistorie(brpIn.getSamengesteldeNaamStapel());
        if (samengesteldenaamStapel != brpIn.getSamengesteldeNaamStapel()) {
            plAangepast = true;
            builder.samengesteldeNaamStapel(samengesteldenaamStapel);
        }

        final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> geslachtsnaamStapels = verwerkMaterieleHistorie(brpIn.getGeslachtsnaamcomponentStapels());
        if (geslachtsnaamStapels != brpIn.getGeslachtsnaamcomponentStapels()) {
            plAangepast = true;
            builder.geslachtsnaamcomponentStapels(geslachtsnaamStapels);
        }

        final List<BrpStapel<BrpVoornaamInhoud>> voornaamnaamStapels = verwerkMaterieleHistorie(brpIn.getVoornaamStapels());
        if (voornaamnaamStapels != brpIn.getVoornaamStapels()) {
            plAangepast = true;
            builder.voornaamStapels(voornaamnaamStapels);
        }

        final BrpStapel<BrpIdentificatienummersInhoud> idStapel = verwerkMaterieleHistorie(brpIn.getIdentificatienummerStapel());
        if (idStapel != brpIn.getIdentificatienummerStapel()) {
            plAangepast = true;
            builder.identificatienummersStapel(idStapel);
        }

        final List<BrpStapel<BrpNationaliteitInhoud>> nationaliteitStapels = verwerkMaterieleHistorie(brpIn.getNationaliteitStapels());
        if (nationaliteitStapels != brpIn.getNationaliteitStapels()) {
            plAangepast = true;
            builder.nationaliteitStapels(nationaliteitStapels);
        }

        final BrpStapel<BrpBijhoudingInhoud> bijhoudingStapel = verwerkMaterieleHistorie(brpIn.getBijhoudingStapel());
        if (bijhoudingStapel != brpIn.getBijhoudingStapel()) {
            plAangepast = true;
            builder.bijhoudingStapel(bijhoudingStapel);
        }

        final BrpStapel<BrpVerblijfsrechtInhoud> verblijfsrechtStapel = verwerkMaterieleHistorie(brpIn.getVerblijfsrechtStapel());
        if (verblijfsrechtStapel != brpIn.getVerblijfsrechtStapel()) {
            plAangepast = true;
            builder.verblijfsrechtStapel(verblijfsrechtStapel);
        }

        final BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderStapel =
                verwerkMaterieleHistorie(brpIn.getBehandeldAlsNederlanderIndicatieStapel());
        if (behandeldAlsNederlanderStapel != brpIn.getBehandeldAlsNederlanderIndicatieStapel()) {
            plAangepast = true;
            builder.behandeldAlsNederlanderIndicatieStapel(behandeldAlsNederlanderStapel);
        }

        final BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldNietNederlanderStapel =
                verwerkMaterieleHistorie(brpIn.getVastgesteldNietNederlanderIndicatieStapel());
        if (vastgesteldNietNederlanderStapel != brpIn.getVastgesteldNietNederlanderIndicatieStapel()) {
            plAangepast = true;
            builder.vastgesteldNietNederlanderIndicatieStapel(vastgesteldNietNederlanderStapel);
        }

        final BrpStapel<BrpStaatloosIndicatieInhoud> staatloosStapel = verwerkMaterieleHistorie(brpIn.getStaatloosIndicatieStapel());
        if (staatloosStapel != brpIn.getStaatloosIndicatieStapel()) {
            plAangepast = true;
            builder.staatloosIndicatieStapel(staatloosStapel);
        }

        final BrpStapel<BrpDerdeHeeftGezagIndicatieInhoud> derdeHeeftGezagStapel = verwerkMaterieleHistorie(brpIn.getDerdeHeeftGezagIndicatieStapel());
        if (derdeHeeftGezagStapel != brpIn.getDerdeHeeftGezagIndicatieStapel()) {
            plAangepast = true;
            builder.derdeHeeftGezagIndicatieStapel(derdeHeeftGezagStapel);
        }

        final BrpStapel<BrpOnderCurateleIndicatieInhoud> onderCurateleStapel = verwerkMaterieleHistorie(brpIn.getOnderCurateleIndicatieStapel());
        if (onderCurateleStapel != brpIn.getOnderCurateleIndicatieStapel()) {
            plAangepast = true;
            builder.onderCurateleIndicatieStapel(onderCurateleStapel);
        }

        final BrpStapel<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> bijzondereVerblijfsrechtelijkePositieStapel =
                verwerkFormeleHistorie(brpIn.getBijzondereVerblijfsrechtelijkePositieIndicatieStapel());
        if (bijzondereVerblijfsrechtelijkePositieStapel != brpIn.getBijzondereVerblijfsrechtelijkePositieIndicatieStapel()) {
            plAangepast = true;
            builder.bijzondereVerblijfsrechtelijkePositieIndicatieStapel(bijzondereVerblijfsrechtelijkePositieStapel);
        }

        final BrpStapel<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud> signaleringStapel =
                verwerkFormeleHistorie(brpIn.getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel());
        if (signaleringStapel != brpIn.getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel()) {
            plAangepast = true;
            builder.signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel(signaleringStapel);
        }

        final BrpStapel<BrpVerstrekkingsbeperkingIndicatieInhoud> verstrekkingsbeperkingStapel =
                verwerkFormeleHistorie(brpIn.getVerstrekkingsbeperkingIndicatieStapel());
        if (verstrekkingsbeperkingStapel != brpIn.getVerstrekkingsbeperkingIndicatieStapel()) {
            plAangepast = true;
            builder.verstrekkingsbeperkingIndicatieStapel(verstrekkingsbeperkingStapel);
        }

        final List<BrpRelatie> relaties = verwerkRelaties(brpIn.getRelaties());
        if (relaties != brpIn.getRelaties()) {
            plAangepast = true;
            builder.relaties(relaties);
        }

        if (plAangepast) {
            return builder.build();
        } else {
            return brpIn;
        }
    }

    private static List<BrpRelatie> verwerkRelaties(final List<BrpRelatie> inRelaties) {
        boolean relatiesAangepast = false;
        final List<BrpRelatie> relaties = new ArrayList<>();

        if (inRelaties != null) {
            for (final BrpRelatie inRelatie : inRelaties) {
                final BrpRelatie relatie = verwerkRelatie(inRelatie);
                if (relatie != inRelatie) {
                    relatiesAangepast = true;
                }
                relaties.add(relatie);
            }
        }

        if (relatiesAangepast) {
            return relaties;
        } else {
            return inRelaties;
        }
    }

    private static BrpRelatie verwerkRelatie(final BrpRelatie inRelatie) {
        boolean relatieAangepast = false;

        final BrpRelatie.Builder builder = new BrpRelatie.Builder(inRelatie, inRelatie.getRelatieId(), new LinkedHashMap<Long, BrpActie>());

        final BrpStapel<BrpRelatieInhoud> relatieStapel = verwerkFormeleHistorie(inRelatie.getRelatieStapel());
        if (relatieStapel != inRelatie.getRelatieStapel()) {
            relatieAangepast = true;
            builder.relatieStapel(relatieStapel);
        }

        final List<BrpBetrokkenheid> betrokkenheden = verwerkBetrokkenheden(inRelatie.getBetrokkenheden());
        if (betrokkenheden != inRelatie.getBetrokkenheden()) {
            relatieAangepast = true;
            builder.betrokkenheden(betrokkenheden);
        }

        if (relatieAangepast) {
            return builder.build();
        } else {
            return inRelatie;
        }
    }

    private static List<BrpBetrokkenheid> verwerkBetrokkenheden(final List<BrpBetrokkenheid> inBetrokkenheden) {
        boolean betrokkenhedenAangepast = false;
        final List<BrpBetrokkenheid> betrokkenheden = new ArrayList<>();

        if (inBetrokkenheden != null) {
            for (final BrpBetrokkenheid inBetrokkenheid : inBetrokkenheden) {
                final BrpBetrokkenheid betrokkenheid = verwerkBetrokkenheid(inBetrokkenheid);
                if (betrokkenheid != inBetrokkenheid) {
                    betrokkenhedenAangepast = true;
                }
                betrokkenheden.add(betrokkenheid);
            }
        }

        if (betrokkenhedenAangepast) {
            return betrokkenheden;
        } else {
            return inBetrokkenheden;
        }
    }

    private static BrpBetrokkenheid verwerkBetrokkenheid(final BrpBetrokkenheid inBetrokkenheid) {
        boolean betrokkenheidAangepast = false;

        final BrpBetrokkenheid.Builder builder = new BrpBetrokkenheid.Builder(inBetrokkenheid);

        final BrpStapel<BrpOuderInhoud> ouderStapel = verwerkMaterieleHistorie(inBetrokkenheid.getOuderStapel());
        if (ouderStapel != inBetrokkenheid.getOuderStapel()) {
            betrokkenheidAangepast = true;
            builder.ouderStapel(ouderStapel);
        }

        final BrpStapel<BrpOuderlijkGezagInhoud> ouderlijkGezagStapel = verwerkMaterieleHistorie(inBetrokkenheid.getOuderlijkGezagStapel());
        if (ouderlijkGezagStapel != inBetrokkenheid.getOuderlijkGezagStapel()) {
            betrokkenheidAangepast = true;
            builder.ouderlijkGezagStapel(ouderlijkGezagStapel);
        }

        if (betrokkenheidAangepast) {
            return builder.build();
        } else {
            return inBetrokkenheid;
        }
    }

    private static <T extends BrpGroepInhoud> BrpStapel<T> verwerkFormeleHistorie(final BrpStapel<T> inStapel) {
        boolean stapelAangepast = false;
        final List<BrpGroep<T>> groepen = new ArrayList<>();

        if (inStapel != null) {
            for (final BrpGroep<T> groep : inStapel) {
                if (groep.getHistorie().getDatumTijdVerval() != null) {
                    final BrpHistorie.Builder hisBuilder = new BrpHistorie.Builder(groep.getHistorie());
                    hisBuilder.setDatumTijdVerval(groep.getHistorie().getDatumTijdRegistratie());
                    groepen.add(
                            new BrpGroep<>(groep.getInhoud(), hisBuilder.build(), groep.getActieInhoud(), groep.getActieInhoud(), groep.getActieGeldigheid()));
                    stapelAangepast = true;
                } else {
                    groepen.add(groep);
                }
            }
        }

        if (stapelAangepast) {
            return new BrpStapel<>(groepen);
        } else {
            return inStapel;
        }
    }

    private static <T extends BrpGroepInhoud> List<BrpStapel<T>> verwerkMaterieleHistorie(final List<BrpStapel<T>> inStapels) {
        boolean stapelsAangepast = false;
        final List<BrpStapel<T>> stapels = new ArrayList<>();

        if (inStapels != null) {
            for (final BrpStapel<T> inStapel : inStapels) {
                final BrpStapel<T> stapel = verwerkMaterieleHistorie(inStapel);
                if (stapel != inStapel) {
                    stapelsAangepast = true;
                }
                stapels.add(stapel);
            }
        }

        if (stapelsAangepast) {
            return stapels;
        } else {
            return inStapels;
        }
    }

    private static <T extends BrpGroepInhoud> BrpStapel<T> verwerkMaterieleHistorie(final BrpStapel<T> inStapel) {
        boolean stapelAangepast = false;
        final List<BrpGroep<T>> groepen = new ArrayList<>();

        if (inStapel != null) {
            for (final BrpGroep<T> groep : inStapel) {
                if (groep.getHistorie().getDatumEindeGeldigheid() != null && groep.getActieGeldigheid() == null) {
                    final BrpGroep<T> opvolger = zoekOpvolger(groep, inStapel);
                    if (opvolger != null) {
                        groepen.add(new BrpGroep<>(
                                groep.getInhoud(),
                                groep.getHistorie(),
                                groep.getActieInhoud(),
                                groep.getActieVerval(),
                                opvolger.getActieInhoud()));
                        stapelAangepast = true;
                    }
                } else {
                    groepen.add(groep);
                }
            }
        }

        if (stapelAangepast) {
            return verwerkFormeleHistorie(new BrpStapel<>(groepen));
        } else {
            return verwerkFormeleHistorie(inStapel);
        }
    }

    private static <T extends BrpGroepInhoud> BrpGroep<T> zoekOpvolger(final BrpGroep<T> basis, final BrpStapel<T> inStapel) {
        final BrpDatum basisEindeGeldigheid = basis.getHistorie().getDatumEindeGeldigheid();
        BrpGroep<T> opvolger = null;
        int aantalGevondenOpvolgers = 0;

        for (final BrpGroep<T> groep : inStapel) {
            if (groep.getHistorie().getDatumTijdVerval() == null
                    && groep.getHistorie().getDatumAanvangGeldigheid().getWaarde().equals(basisEindeGeldigheid.getWaarde())
                    && groep != basis) {
                opvolger = groep;
                aantalGevondenOpvolgers += 1;
            }
        }

        if (aantalGevondenOpvolgers == 1) {
            return opvolger;
        } else {
            return null;
        }
    }

    private static class BrpXmlFileFilter extends AbstractFileFilter {
        final static private List<String> ACCEPTED_DIRS = Arrays.asList("xml-brp", "xml-lezen");

        @Override
        public boolean accept(final File directory, final String filename) {
            return filename.endsWith(".xml") && ACCEPTED_DIRS.contains(directory.getName());
        }
    }
}
