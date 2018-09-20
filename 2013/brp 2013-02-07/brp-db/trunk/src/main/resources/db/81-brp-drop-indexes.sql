--
-- Bolie: woensdag 12 december 2012: Upgrade naar 1.0.3
-- Dit is de tegen partij van 20-brpIndices.sql en 21-brp-indexen.sql

DROP INDEX IF EXISTS His_Authenticatiemiddel_ActieInh ;
DROP INDEX IF EXISTS His_Authenticatiemiddel_ActieVerval ;
DROP INDEX IF EXISTS His_Autorisatiebesluit_ActieInh ;
DROP INDEX IF EXISTS His_Autorisatiebesluit_ActieVerval ;
DROP INDEX IF EXISTS His_AutorisatiebesluitBijhau_ActieInh ;
DROP INDEX IF EXISTS His_AutorisatiebesluitBijhau_ActieVerval ;
DROP INDEX IF EXISTS His_Bijhautorisatie_ActieInh ;
DROP INDEX IF EXISTS His_Bijhautorisatie_ActieVerval ;
DROP INDEX IF EXISTS His_Bijhsituatie_ActieInh ;
DROP INDEX IF EXISTS His_Bijhsituatie_ActieVerval ;
DROP INDEX IF EXISTS His_Doelbinding_ActieInh ;
DROP INDEX IF EXISTS His_Doelbinding_ActieVerval ;
DROP INDEX IF EXISTS His_Doelbinding_ActieAanpGel ;
DROP INDEX IF EXISTS His_Uitgeslotene_ActieInh ;
DROP INDEX IF EXISTS His_Uitgeslotene_ActieVerval ;
DROP INDEX IF EXISTS His_Regelsituatie_ActieInh ;
DROP INDEX IF EXISTS His_Regelsituatie_ActieVerval ;
DROP INDEX IF EXISTS His_Partij_ActieInh ;
DROP INDEX IF EXISTS His_Partij_ActieVerval ;
DROP INDEX IF EXISTS His_PartijGem_ActieInh ;
DROP INDEX IF EXISTS His_PartijGem_ActieVerval ;
DROP INDEX IF EXISTS His_Abonnement_ActieInh ;
DROP INDEX IF EXISTS His_Abonnement_ActieVerval ;
DROP INDEX IF EXISTS His_AbonnementSrtBer_ActieInh ;
DROP INDEX IF EXISTS His_AbonnementSrtBer_ActieVerval ;
DROP INDEX IF EXISTS Lev_GebaseerdOp ;
DROP INDEX IF EXISTS LevCommunicatie_UitgaandBer ;
DROP INDEX IF EXISTS LevPers_Pers ;
DROP INDEX IF EXISTS Ber_AntwoordOp ;

-- update 1.0.3 tabel vervallen, ber heeft 1-1 met kern.admhnd
DROP INDEX IF EXISTS BerBer_AdmHnd ;
-- DROP INDEX IF EXISTS BerActie_Ber ;
-- DROP INDEX IF EXISTS BerActie_Actie ;

DROP INDEX IF EXISTS BerBijgehoudenPers_Ber ;
DROP INDEX IF EXISTS BerBijgehoudenPers_Pers ;
DROP INDEX IF EXISTS BerMelding_Ber ;
DROP INDEX IF EXISTS BerMelding_Melding ;
DROP INDEX IF EXISTS BerOverrule_Ber ;
DROP INDEX IF EXISTS BerOverrule_Overrule ;

-- update 1.0.3 tabel 
DROP INDEX IF EXISTS Actie_AdministratieveHnd ;

DROP INDEX IF EXISTS Betr_Relatie ;
DROP INDEX IF EXISTS Betr_Pers ;

-- update 1.0.3 Bron is nu ActieBron
DROP INDEX IF EXISTS Bron_Actie ;
DROP INDEX IF EXISTS Bron_Doc ;

DROP INDEX IF EXISTS GegevenInOnderzoek_Onderzoek ;
DROP INDEX IF EXISTS MultiRealiteitRegel_GeldigVoor ;
DROP INDEX IF EXISTS MultiRealiteitRegel_Pers ;
DROP INDEX IF EXISTS MultiRealiteitRegel_MultiRealiteitPers ;
DROP INDEX IF EXISTS MultiRealiteitRegel_Relatie ;
DROP INDEX IF EXISTS MultiRealiteitRegel_Betr ;
DROP INDEX IF EXISTS Pers_VorigePers ;
DROP INDEX IF EXISTS Pers_VolgendePers ;
DROP INDEX IF EXISTS PersAdres_Pers ;
DROP INDEX IF EXISTS PersGeslnaamcomp_Pers ;
DROP INDEX IF EXISTS PersIndicatie_Pers ;
DROP INDEX IF EXISTS PersNation_Pers ;
DROP INDEX IF EXISTS PersOnderzoek_Pers ;
DROP INDEX IF EXISTS PersOnderzoek_Onderzoek ;
DROP INDEX IF EXISTS PersReisdoc_Pers ;
DROP INDEX IF EXISTS PersVerificatie_Geverifieerde ;
DROP INDEX IF EXISTS PersVoornaam_Pers ;
DROP INDEX IF EXISTS Regelverantwoording_Actie ;

-- update 1.0.3 Bron is nu RegelVerantwoording
--DROP INDEX IF EXISTS RegelVerantwoording_Actie ;
--DROP INDEX IF EXISTS RegelVerantwoording_Regel ;

-- update 1.0.3 His_BetrOuderlijkGezag is nu His_OuderOuderlijkGezag
DROP INDEX IF EXISTS His_OuderOuderlijkGezag_Betr ;
DROP INDEX IF EXISTS His_OuderOuderlijkGezag_ActieInh ;
DROP INDEX IF EXISTS His_OuderOuderlijkGezag_ActieVerval ;
DROP INDEX IF EXISTS His_OuderOuderlijkGezag_ActieAanpGel ;

-- update 1.0.3 His_BetrOuderlijkGezag is nu His_OuderOuderschap
--DROP INDEX IF EXISTS His_OuderOuderschap_Betr ;
--DROP INDEX IF EXISTS His_OuderOuderschap_ActieInh ;
--DROP INDEX IF EXISTS His_OuderOuderschap_ActieVerval ;
--DROP INDEX IF EXISTS His_OuderOuderschap_ActieAanpGel ;

DROP INDEX IF EXISTS His_Doc_Doc ;
DROP INDEX IF EXISTS His_Doc_ActieInh ;
DROP INDEX IF EXISTS His_Doc_ActieVerval ;
DROP INDEX IF EXISTS His_MultiRealiteitRegel_MultiRealiteitRegel ;
DROP INDEX IF EXISTS His_MultiRealiteitRegel_ActieInh ;
DROP INDEX IF EXISTS His_MultiRealiteitRegel_ActieVerval ;
DROP INDEX IF EXISTS His_Onderzoek_Onderzoek ;
DROP INDEX IF EXISTS His_Onderzoek_ActieInh ;
DROP INDEX IF EXISTS His_Onderzoek_ActieVerval ;
DROP INDEX IF EXISTS His_PersAanschr_Pers ;

-- update 1.0.3 His_PersAanschr is nu een formele historie (was materieel)
DROP INDEX IF EXISTS His_PersAanschr_ActieInh ;
DROP INDEX IF EXISTS His_PersAanschr_ActieVerval ;
--DROP INDEX IF EXISTS His_PersAanschr_ActieAanpGel ;

DROP INDEX IF EXISTS His_PersBijhgem_Pers ;
DROP INDEX IF EXISTS His_PersBijhgem_ActieInh ;
DROP INDEX IF EXISTS His_PersBijhgem_ActieVerval ;
DROP INDEX IF EXISTS His_PersBijhgem_ActieAanpGel ;

-- update 1.0.3 His_PersBVP is bijgekomen
DROP INDEX IF EXISTS His_PersBVP_Pers ;
DROP INDEX IF EXISTS His_PersBVP_ActieInh ;
DROP INDEX IF EXISTS His_PersBVP_ActieVerval ;

DROP INDEX IF EXISTS His_PersBijhverantwoordelijk_Pers ;
DROP INDEX IF EXISTS His_PersBijhverantwoordelijk_ActieInh ;
DROP INDEX IF EXISTS His_PersBijhverantwoordelijk_ActieVerval ;
DROP INDEX IF EXISTS His_PersBijhverantwoordelijk_ActieAanpGel ;
DROP INDEX IF EXISTS His_PersEUVerkiezingen_Pers ;
DROP INDEX IF EXISTS His_PersEUVerkiezingen_ActieInh ;
DROP INDEX IF EXISTS His_PersEUVerkiezingen_ActieVerval ;
DROP INDEX IF EXISTS His_PersGeboorte_Pers ;
DROP INDEX IF EXISTS His_PersGeboorte_ActieInh ;
DROP INDEX IF EXISTS His_PersGeboorte_ActieVerval ;
DROP INDEX IF EXISTS His_PersGeslachtsaand_Pers ;
DROP INDEX IF EXISTS His_PersGeslachtsaand_ActieInh ;
DROP INDEX IF EXISTS His_PersGeslachtsaand_ActieVerval ;
DROP INDEX IF EXISTS His_PersGeslachtsaand_ActieAanpGel ;
DROP INDEX IF EXISTS His_PersIDs_Pers ;
DROP INDEX IF EXISTS His_PersIDs_ActieInh ;
DROP INDEX IF EXISTS His_PersIDs_ActieVerval ;
DROP INDEX IF EXISTS His_PersIDs_ActieAanpGel ;
DROP INDEX IF EXISTS His_PersImmigratie_Pers ;
DROP INDEX IF EXISTS His_PersImmigratie_ActieInh ;
DROP INDEX IF EXISTS His_PersImmigratie_ActieVerval ;
DROP INDEX IF EXISTS His_PersImmigratie_ActieAanpGel ;
DROP INDEX IF EXISTS His_PersInschr_Pers ;
DROP INDEX IF EXISTS His_PersInschr_ActieInh ;
DROP INDEX IF EXISTS His_PersInschr_ActieVerval ;
DROP INDEX IF EXISTS His_PersInschr_VorigePers ;
DROP INDEX IF EXISTS His_PersInschr_VolgendePers ;
DROP INDEX IF EXISTS His_PersOpschorting_Pers ;
DROP INDEX IF EXISTS His_PersOpschorting_ActieInh ;
DROP INDEX IF EXISTS His_PersOpschorting_ActieVerval ;
DROP INDEX IF EXISTS His_PersOpschorting_ActieAanpGel ;
DROP INDEX IF EXISTS His_PersOverlijden_Pers ;
DROP INDEX IF EXISTS His_PersOverlijden_ActieInh ;
DROP INDEX IF EXISTS His_PersOverlijden_ActieVerval ;
DROP INDEX IF EXISTS His_PersPK_Pers ;
DROP INDEX IF EXISTS His_PersPK_ActieInh ;
DROP INDEX IF EXISTS His_PersPK_ActieVerval ;
DROP INDEX IF EXISTS His_PersSamengesteldeNaam_Pers ;
DROP INDEX IF EXISTS His_PersSamengesteldeNaam_ActieInh ;
DROP INDEX IF EXISTS His_PersSamengesteldeNaam_ActieVerval ;
DROP INDEX IF EXISTS His_PersSamengesteldeNaam_ActieAanpGel ;
DROP INDEX IF EXISTS His_PersUitslNLKiesr_Pers ;
DROP INDEX IF EXISTS His_PersUitslNLKiesr_ActieInh ;
DROP INDEX IF EXISTS His_PersUitslNLKiesr_ActieVerval ;

-- update 1.0.3 His_PersVerblijfsr is nu His_PersVerblijfsTitel 
DROP INDEX IF EXISTS His_PersVerblijfsTitel_Pers ;
DROP INDEX IF EXISTS His_PersVerblijfsTitel_ActieInh ;
DROP INDEX IF EXISTS His_PersVerblijfsTitel_ActieVerval ;
DROP INDEX IF EXISTS His_PersVerblijfsTitel_ActieAanpGel ;

DROP INDEX IF EXISTS His_PersAdres_PersAdres ;
DROP INDEX IF EXISTS His_PersAdres_ActieInh ;
DROP INDEX IF EXISTS His_PersAdres_ActieVerval ;
DROP INDEX IF EXISTS His_PersAdres_ActieAanpGel ;
DROP INDEX IF EXISTS His_PersGeslnaamcomp_PersGeslnaamcomp ;
DROP INDEX IF EXISTS His_PersGeslnaamcomp_ActieInh ;
DROP INDEX IF EXISTS His_PersGeslnaamcomp_ActieVerval ;
DROP INDEX IF EXISTS His_PersGeslnaamcomp_ActieAanpGel ;
DROP INDEX IF EXISTS His_PersIndicatie_PersIndicatie ;
DROP INDEX IF EXISTS His_PersIndicatie_ActieInh ;
DROP INDEX IF EXISTS His_PersIndicatie_ActieVerval ;
DROP INDEX IF EXISTS His_PersIndicatie_ActieAanpGel ;
DROP INDEX IF EXISTS His_PersNation_PersNation;
DROP INDEX IF EXISTS His_PersNation_ActieInh ;
DROP INDEX IF EXISTS His_PersNation_ActieVerval ;
DROP INDEX IF EXISTS His_PersNation_ActieAanpGel ;
DROP INDEX IF EXISTS His_PersReisdoc_PersReisdoc ;
DROP INDEX IF EXISTS His_PersReisdoc_ActieInh ;
DROP INDEX IF EXISTS His_PersReisdoc_ActieVerval ;
DROP INDEX IF EXISTS His_PersVerificatie_PersVerificatie ;
DROP INDEX IF EXISTS His_PersVerificatie_ActieInh ;
DROP INDEX IF EXISTS His_PersVerificatie_ActieVerval ;
DROP INDEX IF EXISTS His_PersVoornaam_PersVoornaam ;
DROP INDEX IF EXISTS His_PersVoornaam_ActieInh ;
DROP INDEX IF EXISTS His_PersVoornaam_ActieVerval ;
DROP INDEX IF EXISTS His_PersVoornaam_ActieAanpGel ;

-- update 1.0.3 His_Relatie_Relatie gesplist in his_huwelijkgeregistreerdpar, his_erkenningongeborenvrucht, his_erkenningongeborenvrucht, his_ontkenningouderschaponge 
DROP INDEX IF EXISTS his_huwelijkgeregistreerdpar_Relatie ;
DROP INDEX IF EXISTS his_huwelijkgeregistreerdpar_ActieInh ;
DROP INDEX IF EXISTS his_huwelijkgeregistreerdpar_ActieVerval ;

DROP INDEX IF EXISTS his_erkenningongeborenvrucht_Relatie ;
DROP INDEX IF EXISTS his_erkenningongeborenvrucht_ActieInh ;
DROP INDEX IF EXISTS his_erkenningongeborenvrucht_ActieVerval ;

DROP INDEX IF EXISTS his_erkenningongeborenvrucht_Relatie ;
DROP INDEX IF EXISTS his_erkenningongeborenvrucht_ActieInh ;
DROP INDEX IF EXISTS his_erkenningongeborenvrucht_ActieVerval ;

DROP INDEX IF EXISTS his_ontkenningouderschaponge_Relatie ;
DROP INDEX IF EXISTS his_ontkenningouderschaponge_ActieInh ;
DROP INDEX IF EXISTS his_ontkenningouderschaponge_ActieVerval ;

					
DROP INDEX IF EXISTS autaut.indaut001;
DROP INDEX IF EXISTS kern.indkern001;
DROP INDEX IF EXISTS kern.indkern302;
DROP INDEX IF EXISTS kern.indkern303;
DROP INDEX IF EXISTS kern.indkern304;
DROP INDEX IF EXISTS kern.indkern901;
DROP INDEX IF EXISTS kern.indkern902;
DROP INDEX IF EXISTS kern.indkern903;
DROP INDEX IF EXISTS kern.indkern904;
