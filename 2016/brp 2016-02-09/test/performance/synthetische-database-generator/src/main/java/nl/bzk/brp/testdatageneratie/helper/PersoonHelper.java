/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.helper;

import static nl.bzk.brp.testdatageneratie.utils.RandomUtil.isFractie;
import static nl.bzk.brp.testdatageneratie.utils.RandomUtil.random;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import nl.bzk.brp.testdatageneratie.dataaccess.BronnenRepo;
import nl.bzk.brp.testdatageneratie.dataaccess.MetaRepo;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Locatie;
import nl.bzk.brp.testdatageneratie.domain.bronnen.NaamGebruik;
import nl.bzk.brp.testdatageneratie.domain.bronnen.NaamGebruikId;
import nl.bzk.brp.testdatageneratie.domain.kern.Aandverblijfsr;
import nl.bzk.brp.testdatageneratie.domain.kern.Adellijketitel;
import nl.bzk.brp.testdatageneratie.domain.kern.Partij;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.domain.kern.Plaats;
import nl.bzk.brp.testdatageneratie.domain.kern.Predicaat;
import nl.bzk.brp.testdatageneratie.utils.Constanten;
import nl.bzk.brp.testdatageneratie.utils.GenUtil;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;
import org.apache.commons.lang.RandomStringUtils;


/**
 * Persoon helper.
 */
public class PersoonHelper {

    /**
     * Random bSN service.
     */
    private final RandomBsnHelper randomBSNService;

    /**
     * Instantieert Persoon helper.
     *
     * @param randomBSNService random bSN service
     */
    public PersoonHelper(final RandomBsnHelper randomBSNService) {
        this.randomBSNService = randomBSNService;
    }

    /**
     * Generate pers.
     *
     * @param voornamen voornamen
     * @return the pers
     */
    public Pers generatePers(final String[] voornamen) {
        Pers pers = new Pers();
        Integer datGeboorte = RandomUtil.getDatGeboorte();
        setIdentificatieNummers(pers);
        // bolie: dit is volgens mij ook verkeerde verhouding
        pers.setSrt((short) RandomUtil.getSrtpers().ordinal());
        pers.setBijhaard((short) RandomUtil.getBijhaard().ordinal());
        pers.setNaderebijhaard((short) NadereBijhoudingsaard.ACTUEEL.ordinal());

        geboorte(pers, datGeboorte);
        bijhoudingsPartij(pers);
        setInschrijving(pers);
        setGeslachtsaanduiding(pers);
        setVoornamen(pers, voornamen);
        setSamenGesteldeNaam(pers);
        setAanschrijving(pers);
        setOverlijden(pers);
        setAandverblijfsr(pers);
        setEUVerkiezingen(pers);
        setUitsluitendNLKiesrecht(pers);

        pers.setTslaatstewijz(RandomUtil.getPastTimestamp());

        if (isFractie(Constanten.VIJF)) {
            final Partij partij = MetaRepo.get(Partij.class);
            if (partij != null) {
                pers.setGempk(partij.getId());
            }
            pers.setIndpkvollediggeconv(!isFractie(Constanten.VIER));
        }

        return pers;
    }

    /**
     * Geboorte void.
     *
     * @param pers pers
     * @param datGeboorte dat geboorte
     */
    private void geboorte(final Pers pers, final Integer datGeboorte) {
        //BEGIN SECTION Geboorte:
        pers.setDatgeboorte(datGeboorte);

        final Locatie geboorteplaats = RandomUtil.getGeboortePlaats();
        if (geboorteplaats.getLandgebied() != null) {
            pers.setLandgebiedgeboorte(geboorteplaats.getLandgebied().getId());
            if (geboorteplaats.isNederland()) {
                if (geboorteplaats.getGemeente() != null) {
                    pers.setGemgeboorte(geboorteplaats.getGemeente().getId());
                }
                Plaats plaats = RandomUtil.getWplGeboorte();
                if (plaats != null) {
                    pers.setWplnaamgeboorte(plaats.getNaam());
                }
            } else {
                pers.setBlplaatsgeboorte(geboorteplaats.getPlaats());
                if (isFractie(Constanten.TIEN)) {
                    pers.setBlregiogeboorte(RandomStringUtils.randomAlphabetic(Constanten.VIJFENDERTIG));
                }
            }
        }
    }

    /**
     * Bijhoudingspartij.
     *
     * @param pers pers
     */
    private void bijhoudingsPartij(final Pers pers) {
        //Bijhoudings gemeente:
        Short partij = RandomUtil.getPartijByBijhgem();
        if (partij != null) {
            pers.setBijhpartij(partij);
        }
        pers.setIndonverwdocaanw(isFractie(Constanten.TIENDUIZEND));
    }

    /**
     * Zet geslachtsaanduiding.
     *
     * @param pers pers
     */
    private void setGeslachtsaanduiding(final Pers pers) {
        NaamGebruik ng = BronnenRepo.getBron(NaamGebruik.class);
        if (ng != null) {
            NaamGebruikId naamGebruik = ng.getId();

            if (naamGebruik != null) {
                pers.setGeslachtsaand((short) naamGebruik.getGeslacht().ordinal());
            }
        }
    }

    /**
     * Zet identificatie nummers.
     *
     * @param pers pers
     */
    private void setIdentificatieNummers(final Pers pers) {
        //Zero inclusive
        pers.setBsn(randomBSNService.randomBsn());
        if (isFractie(Constanten.TWEE)) {
            // ook een verkeerde aanname, moet een 'elfproef' a-nummer zijn.
            pers.setAnr((long) random.nextInt(Integer.MAX_VALUE));
        }
    }

    /**
     * Zet inschrijving.
     *
     * @param pers pers
     */
    private void setInschrijving(final Pers pers) {
        pers.setVersienr(1L);
        if (pers.getLandgebiedgeboorte().intValue() == Locatie.LAND_CODE_NL) {
            pers.setDatinschr(pers.getDatgeboorte());
        } else {
            // todo (verhuizing oid.
            pers.setDatinschr(RandomUtil.randomDateNaDatum(pers.getDatgeboorte()));
        }

        pers.setDattijdstempel(GenUtil.vanBrpDatum(pers.getDatinschr()));

        NaamGebruik ng = BronnenRepo.getBron(NaamGebruik.class);
        if (ng != null) {
            NaamGebruikId naamGebruik = ng.getId();
            pers.setNaamgebruik((short) naamGebruik.getNaamGebruik().ordinal());
        }
    }

    /**
     * Zet voornamen.
     *
     * @param pers pers
     * @param voornamen voornamen
     */
    private void setVoornamen(final Pers pers, final String[] voornamen) {
        //Voornamen:
        StringBuilder voornamenBuilder = new StringBuilder();
        for (final String voornaam : voornamen) {
            voornamenBuilder.append(voornaam).append(" ");
        }
        String voornamenString = voornamenBuilder.toString().trim();
        pers.setVoornamen(voornamenString);
    }

    /**
     * Zet samen gestelde naam.
     *
     * @param pers pers
     */
    private void setSamenGesteldeNaam(final Pers pers) {
        //Geslachtsnaam:
        pers.setGeslnaamstam(RandomUtil.getGeslnaam());

        if (isFractie(Constanten.TWEEDUIZEND_DRIEHONDERD_ZEVENENVEERTIG)) {
            Adellijketitel adellijketitel = null;
            while (adellijketitel == null) {
                adellijketitel = RandomUtil.getAdellijketitel();
            }
            pers.setAdellijketitel(((Integer) adellijketitel.ordinal()).shortValue());
            // bolie: dit is een foute aanname: bij adellijke titel GEEN predikaat (en omgekeerd)
            if (null != pers.getAdellijketitel()) {
                if (pers.getAdellijketitel() == Adellijketitel.P.ordinal()) {
                    pers.setPredicaat(((Integer) Predicaat.K.ordinal()).shortValue());
                } else {
                    pers.setPredicaat(((Integer) Predicaat.J.ordinal()).shortValue());
                }

            }
        }

        if (isFractie(Constanten.VIJFTIG)) {
            String scheidingsTeken = RandomUtil.nextScheidingsTeken();
            pers.setScheidingsteken(scheidingsTeken);
            if (!isFractie(Constanten.TWINTIG)) {
                pers.setScheidingstekennaamgebruik(scheidingsTeken);
            }
        }
        if (isFractie(Constanten.TWEEENTWINTIG)) {
            String voorvoegsel = RandomUtil.getVoorvoegsel();
            pers.setVoorvoegsel(voorvoegsel);
            pers.setVoorvoegselnaamgebruik(voorvoegsel);
        }

    }

    /**
     * Zet aanschrijving.
     *
     * @param pers pers
     */
    private void setAanschrijving(final Pers pers) {
        pers.setVoornamennaamgebruik(pers.getVoornamen());
        pers.setGeslnaamstamnaamgebruik(pers.getGeslnaamstam());
        pers.setPredicaatnaamgebruik(pers.getPredicaat());
        pers.setIndnaamgebruikafgeleid(!isFractie(Constanten.DUIZEND));
        //TODO check of dit de correcte setting is
        pers.setIndnreeks(isFractie(Constanten.TIENDUIZEND));
        pers.setIndafgeleid(!isFractie(Constanten.DUIZEND));
    }

    /**
     * Zet overlijden.
     *
     * @param pers pers
     */
    private void setOverlijden(final Pers pers) {
        if (isFractie(Constanten.DRIE)) {
            pers.setDatoverlijden(RandomUtil.randomDateNaDatum(pers.getDatgeboorte()));
            final Locatie locatie = RandomUtil.getGeboortePlaats();
            if (locatie.getLandgebied() != null) {
                pers.setLandgebiedoverlijden(locatie.getLandgebied().getId());
            }
            pers.setNaderebijhaard((short) NadereBijhoudingsaard.OVERLEDEN.ordinal());
            if (locatie.isNederland()) {
                if (locatie.getGemeente() != null) {
                    pers.setGemoverlijden(locatie.getGemeente().getId());
                }
                final Plaats plaats = RandomUtil.getWplGeboorte();
                if (plaats != null) {
                    pers.setWplnaamoverlijden(plaats.getNaam());
                }
            } else if (locatie.getLandCode() == Locatie.LAND_CODE_ONBEKEND) {
                //Land onbekend, gebruik omschrijving:
                pers.setOmslocoverlijden(locatie.getPlaats());
            } else {
                pers.setBlplaatsoverlijden(RandomStringUtils.randomAlphabetic(Constanten.VEERTIG));
                if (isFractie(Constanten.TIEN)) {
                    pers.setBlregiooverlijden(RandomStringUtils.randomAlphabetic(Constanten.VIJFENDERTIG));
                }
            }
        } else {
            //4% is opgeschort volgens bronnen tabel: 2,6 miljoen personen
            if (isFractie(Constanten.VIJFENTWINTIG)) {
                //Reden opschorting
                // bolie: fout !!, bij overlijden heeft hij PER definitie reden opschorting 'OVERLEDEN', en deze
                // zit ook al bij deze 4% !
                pers.setNaderebijhaard((short) RandomUtil.getNadereBijhaard().ordinal());
            }
        }
    }

    /**
     * Zet verblijfs titel.
     *
     * @param pers pers
     */
    private void setAandverblijfsr(final Pers pers) {
        if (pers.getLandgebiedgeboorte() != Locatie.LAND_CODE_ONBEKEND) {
            pers.setDatmededelingverblijfsr(pers.getDatinschr());
            if (!isFractie(Constanten.DRIE)) {
                pers.setDatvoorzeindeverblijfsr(RandomUtil.randomDateNaDatum(pers.getDatgeboorte()));
            }
            final Aandverblijfsr vt = RandomUtil.getAandverblijfsr();
            if (vt != null) {
                pers.setAandverblijfsr(vt.getId());
            }
            if (isFractie(Constanten.HONDERD)) {
                final Locatie locatie = RandomUtil.getGeboortePlaats();
                if (locatie.getLandgebied() != null) {
                    pers.setLandgebiedmigratie(locatie.getLandgebied().getId());
                }
            }
        }
    }

    /**
     * Zet eU verkiezingen.
     *
     * @param pers pers
     */
    private void setEUVerkiezingen(final Pers pers) {
        if (isFractie(Constanten.TIEN)) {
            if (isFractie(Constanten.TWEE)) {
                pers.setDataanlaanpdeelneuverkiezing(RandomUtil.randomDateNaDatum(pers.getDatgeboorte()));
                pers.setInddeelneuverkiezingen(isFractie(Constanten.TWEE));
            } else {
                pers.setInddeelneuverkiezingen(true);
            }
        }

    }

    /**
     * Zet uitsluitend nL kiesrecht.
     *
     * @param pers pers
     */
    private void setUitsluitendNLKiesrecht(final Pers pers) {
        if (isFractie(Constanten.HONDERD)) {
            pers.setInduitslkiesr(true);
            if (isFractie(Constanten.HONDERD)) {
                pers.setDatvoorzeindeuitslkiesr(RandomUtil.randomDateNaDatum(pers.getDatgeboorte()));
            }
        }

        if (isFractie(Constanten.DUIZEND)) {
            pers.setDatvoorzeindeuitslkiesr(RandomUtil.randomDateNaDatum(pers.getDatgeboorte()));
        }

    }

    /**
     * Geeft voorvoegsel.
     *
     * @return voorvoegsel
     */
    public String getVoorvoegsel() {
        String voorvoegsel = null;

        switch (RandomUtil.random.nextInt(Constanten.ACHT)) {
            case Constanten.NUL:
                voorvoegsel = "de";
                break;
            case Constanten.EEN:
                voorvoegsel = "van";
                break;
            case Constanten.TWEE:
                voorvoegsel = "van de";
                break;
            case Constanten.DRIE:
                voorvoegsel = "van der";
                break;
            default:
                break;
        }

        return voorvoegsel;
    }

}
