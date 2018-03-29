myApp
	.factory('runService', ['md5', '$q', function(md5, $q) {

		var finished=[false, false, false];

		function gethash(password) {
			var hash = '';
			if (password && password != '') {
				hash = md5.createHash(password);
			}
			
			return hash;
		}
		
		function getdummyurl(what, stage, password) {
			var hash = gethash(password);
			return DUMMY_URL + "?what=" + what + "&stage=" + stage;
		}
		
		function geturl(what, stage, password) {
			var hash = gethash(password);
			if (stage && stage != '') {
				what += "_" + stage;	
			}

			switch (what) {
				case "stop_isc":
					return STOP_ISC_URI;
				case "copy_db":
					return COPY_BRP_DB_URI + "?stage=IV";
				case "copy_db_" + stage:
					return COPY_BRP_DB_URI + "?stage=" + stage;
				case "run_afterburner":
					return RUN_AFTERBURNER_URI;
				case "start_isc":
					return START_ISC_URI;
				case "start_beheer":
					return START_BEHEER_URI;
				case "draai_levauts":
					return RUN_LEVAUTS_URI;
				case "draai_mutaties_" + stage:
					return RUN_MUTATIES_URI + "?stage=" + stage;
				case "ophalen_resultaten_SQL_" + stage:
					return OPHALEN_RESULTATEN_URI + "?flavour=SQL&stage=" + stage;	
				case "ophalen_resultaten_GBA_" + stage:
					return OPHALEN_RESULTATEN_URI + "?flavour=GBA&stage=" + stage;	
				case "ophalen_resultaten_BRP_" + stage:
					return OPHALEN_RESULTATEN_URI + "?flavour=BRP&stage=" + stage;	
				case "truncate_pl":
					return TRUNCATE_IV_URI + "?type=pl";
				case "create_pl":
					return CREATE_IV_URI + "?type=pl";
				case "run_pl":
					return RUN_IV_URI + "?type=pl";
				case "truncate_aut":
					return TRUNCATE_IV_URI + "?type=aut";
				case "create_aut":
					return CREATE_IV_URI + "?type=aut";
				case "run_aut":
					return RUN_IV_URI + "?type=aut";
				case "truncate_ai":
					return TRUNCATE_IV_URI + "?type=ai";
				case "create_ai":
					return CREATE_IV_URI + "?type=ai";
				case "run_ai":
					return RUN_IV_URI + "?type=ai";
				case "truncate_proto":
					return TRUNCATE_IV_URI + "?type=proto";
				case "create_proto":
					return CREATE_IV_URI + "?type=proto";
				case "run_proto":
					return RUN_IV_URI + "?type=proto";				
				case "run_script":
					return RUN_SCRIPT_URI;
				case "run_tc":
					return RUN_TC_URI;
				case "stop_iv":
					return STOP_IV_URI;
				case "start_iv":
					return START_IV_URI;
				case "ophalen_leveringen_koppelvlak_voisc":
					return OPHALEN_VOISC_URI;
				case "ophalen_leveringen_koppelvlak_voisc_" + stage:
					return OPHALEN_VOISC_URI + "?stage=" + stage;
				case "ophalen_leveringen_koppelvlak_brp_" + stage:
					return OPHALEN_BRP_URI + "?stage=" + stage;
				case "clean_actuals_iv":
					return CLEAN_ACTUALS + "?stage=IV&flavour=SQL";
				case "clean_actuals_tc":
					return CLEAN_ACTUALS + "?stage=TC&flavour=GBA";
				case "clean_actuals_" + stage:
					return CLEAN_ACTUALS + "?stage=" + stage;
				case "backup_db_IV":
					return BACKUP_DB_URI + "?stage=IV";
				case "backup_db_" + stage:
					return BACKUP_DB_URI + "?stage=" + stage;
				case "merge_results":
					return MERGE_RESULTS_URI;
				case "copy_data_set":
					return COPY_DATA_SET_URI;
				case "clean_gbav_db":
					return CLEAN_GBAV_DB_URI;
				case "read_into_gbav":
					return READ_INTO_GBAV_URI;
				case "update":
					return UPDATE_URI;
				case "check_tc_" + stage:
					return OPHALEN_TC_URI;
				default:
					return getdummyurl(what, password);
			}
		}

		/** default implementation **/
		function handleError(what, xhr) {
			console.error("fout opgetreden in " + what);
			console.error(xhr);
		}
		
		/** default implementation **/
		function handleStep(status, step, handleError, stage) {
			var stage_text = step;
			if (stage) {
				stage_text += " " + stage;
			}

			console.log("voer " + step + " uit");
			status(stage_text, STATUS_RUN);
			
			var promise = $.ajax({
				url: geturl(step, stage, DEFAULT_PASSWORD),
				error: function(xhr) {
					handleError(step, xhr);
				},
				success: function(data) {
					console.log(step + " uitgevoerd");
					status(stage_text, STATUS_RUN);
				}
			});
			
			return promise;
		}
		
		function call(i, status, geturl, handleError, setLog, finish) {
			var step1 = 'truncate_' + IV_TYPES[i];
			console.log("voer " + step1 + " uit");
			status(step1, STATUS_RUN);
			$.ajax({
				url: geturl(step1, '', DEFAULT_PASSWORD),
				error: function(xhr) {
					handleError(step1, xhr);
				},
				success: function(data) {
					setLog(step1, data);
					status(step1, STATUS_RUN);
					console.log(step1 + " uitgevoerd");

					var step2 = 'create_' + IV_TYPES[i];
					console.log("voer " + step2 + " uit");
					status(step2, STATUS_RUN);
					$.ajax({
						url: geturl(step2, '', DEFAULT_PASSWORD),
						error: function(xhr) {
							handleError(step2, xhr);
						},
						success: function(data) {
							setLog(step2, data);
							status(step2, STATUS_RUN);
							console.log(step2 + " uitgevoerd");

							var step3 = 'run_' + IV_TYPES[i];
							console.log("voer " + step3 + " uit");
							status(step3, STATUS_RUN);
							$.ajax({
								url: geturl(step3, '', DEFAULT_PASSWORD),
								error: function(xhr) {
									handleError(step3, xhr);
								},
								success: function(data) {
									setLog(step3, data);
									status(step3, STATUS_RUN);
									console.log(step3 + " uitgevoerd");

									if (i < (IV_TYPES.length - 1)) {
										call (i + 1, status, geturl, handleError, setLog, finish);
									} else {
										finish("timeout1");
									}
								}
							});
						}
					});
				}
			});				
		}

		function getLO3Results (stage, substage, status, geturl, handleError, setLog, finish) {
			var step = "ophalen_leveringen_koppelvlak_voisc";
			status(step, STATUS_QUERY);
			console.log("voer " + step + "_" + stage + " uit");

			var check_voisc = $.ajax({
				url: geturl(step, stage, DEFAULT_PASSWORD),
				error: function(xhr) {
					handleError(step + "_" + stage, xhr);
				},
				success: function(data) {
					setLog(step, data);
					status(step, STATUS_QUERY);
					console.log(step + " uitgevoerd");
					finish();
				}
			});	
			
			return check_voisc;
		}

		function getBRPResults (stage, substage, status, geturl, handleError, setLog, finish) {
			var step = "ophalen_leveringen_koppelvlak_brp";
			status(step, STATUS_QUERY);		
			console.log("voer " + step + "_" + stage + " uit");
			
			var check_brp = $.ajax({
				url: geturl(step, stage, DEFAULT_PASSWORD),
				error: function(xhr) {
					handleError(step + "_" + stage, xhr);
				},
				success: function(data) {
					setLog(step, data);
					status(step, STATUS_QUERY);
					console.log(step + "_" + stage + " uitgevoerd");
					finish();
				}
			});
			
			return check_brp;
		}
		
		/** ophalen van resultaten, query (SQL, BRP, GBA, etc) en wegschrijven naar Stages directory **/
		function getQueryResults (stage, substage, flavour, status, geturl, handleError, setLog, finish) {
			var step = "ophalen_resultaten_" + flavour;
			var stage_text = step + " " + stage + " " + flavour;
			
			status(stage_text, STATUS_QUERY);
			console.log("voer " + step + "_" + stage + " uit");
			var check = $.ajax({
				url: geturl(step, stage, DEFAULT_PASSWORD),
				error: function(xhr, status, error) {
					console.log(status);
					console.log(error);
					handleError(step + "_" + stage, xhr);
				},
				success: function(data) {
					if (flavour == "SQL") {
						finished[0]=true;
					} else if (flavour == "GBA") {
						finished[1]=true;		
					} else if (flavour == "BRP") {
						finished[2]=true;		
					}
					
					// finished variable voor stap IV en TC voor BRP altijd op true, want er is geen BRP
					if (stage == "IV" || stage == "TC") {
						finished[2]=true;		
					}

					mergeResults(status, finish);	

					setLog(step, data);
					status(stage_text, STATUS_QUERY);
					console.log(step + "_" + stage + " uitgevoerd");
				}
			});
			
			return check;
		}

		function startIV (handleStep, status, geturl, handleError, setLog, finish) {
			var promise = cleanIVEnv(handleStep, status, geturl, setLog);
			promise
				.then(function() {
					call(0, status, geturl, handleError, setLog, finish);
				});
		}
		
		function startTC (status, geturl, handleError, setLog, finish) {
			var step = "run_tc";
			status(step, STATUS_RUN);
			console.log("voer " + step + " uit");
			$.ajax({
				url: geturl(step, '', DEFAULT_PASSWORD),
				error: function(xhr, status, error) {
					console.log(status);
					console.log(error);
					
					handleError(step, xhr);
				},
				success: function(data) {
					setLog(step, data);
					status(step, STATUS_RUN);
					console.log(step + " uitgevoerd");
					finish();
				}
			});
		}

		/**
		 * Functies voor het makkelijk uitvoeren van de verschillende stappen
		 */
		function doIV(status, finish) {
			startIV (handleStep, status, geturl, handleError, function(){}, finish);			
		}

		function doTC(status, finish) {
			startTC (status, geturl, handleError, function(){}, finish);			
		}

		function doMutaties(status, stage, substage, finish) {
			var update_task = handleStep(status, "update", handleError);
			var clean_actuals_task = handleStep(status, "clean_actuals", handleError, stage);
			var stop_task = handleStep(status, "stop_isc", handleError);
			var copy_db_task = handleStep(status, "copy_db", handleError, stage);
		
			$.when(stop_task)
				.done(function() {					
					$.when(copy_db_task)
						.done (function() {
							if (stage == "M01") {
								var start_beheer = handleStep(status, "start_beheer", handleError);
								$.when(start_beheer)
									.done (function() {	
										var run_levauts = handleStep(status, "draai_levauts", handleError);
										$.when(run_levauts)
											.done(function() {
												var stop_task2 = handleStep(status, "stop_isc", handleError);
												var run_afterburner = handleStep(status, "run_afterburner", handleError);
												$.when(stop_task2)
													.done (function() {	
													var start_task = handleStep(status, "start_isc", handleError);
													$.when(start_task)
														.done(function() {
															var run_task = handleStep(status, "draai_mutaties", handleError, stage);
															$.when(run_task)
																.done(function() {
																	console.log("klaar: " + stage);
																	finish();
															});
													});
												});
										});
									});
							} else {
								var start_task = handleStep(status, "start_isc", handleError);
								$.when(start_task)
									.done(function() {
										var run_task = handleStep(status, "draai_mutaties", handleError, stage);
										$.when(run_task)
											.done(function() {
												console.log("klaar: " + stage);
												finish();
											});
									});
							}
						});
				});
		}

		function mergeResults(status, finish) {
			if (finished[0] && finished[1] && finished[2]) {
				var what = "merge_results";
				console.log("voer " + what + " uit");
				status(what, STATUS_RUN);
				$.ajax({
					url: geturl(what, '', DEFAULT_PASSWORD),
					error: function(xhr) {
						handleError(what, xhr);
					},
					success: function(data) {
						status(what, STATUS_RUN);
						console.log(what + " uitgevoerd");
						finish(data);
					}
				});
				
				finished=[false, false, false];
			} else {
				return null;
			}
		}
		
		function backupDB(stage, substage, status, finish) {
			var what = "backup_db";
			console.log("voer " + what + "_" + stage + " uit");
			status("backup_db", STATUS_QUERY);
			var backupdb = $.ajax({
				url: geturl(what, stage, DEFAULT_PASSWORD),
				error: function(xhr) {
					handleError(what, xhr);
				},
				success: function(data) {
					status("backup_db", STATUS_QUERY);
					console.log(what + "_" + stage + " uitgevoerd");
					finish(data);
				}
			});
			
			return backupdb;
		}

		function getAllResults(status, stage, substage, finish) {
			backupDB(stage, substage, status, function(){});
			var checkLO3 = getLO3Results (stage, substage, status, geturl, handleError, function(){}, function() {
				getQueryResults (stage, substage, 'GBA', status, geturl, handleError, function(){}, finish);
			});
			var checkBRP = getBRPResults (stage, substage, status, geturl, handleError, function(){}, function() {
				getQueryResults (stage, substage, 'BRP', status, geturl, handleError, function(){}, finish);
			});
			var checkSQL = getQueryResults (stage, substage, 'SQL', status, geturl, handleError, function(){}, finish);

			// op verzoek gedeactiveerd
			// var checkRESBIJ = getQueryResults (stage, substage, 'RESBIJ', status, geturl, handleError, function(){}, finish);
			// var checkRESBEV = getQueryResults (stage, substage, 'RESBEV', status, geturl, handleError, function(){}, finish);
		}
		
		function getIVResults(status, finish) {
			var promise = getQueryResults ('IV', '', 'SQL', status, geturl, handleError, function(){}, finish);
			$.when(promise)
				.done(function() {
					backupDB('IV', '', status, finish);
				});					
		}

		function getTCResults(status, finish) {
			getQueryResults ('TC', '', 'GBA', status, geturl, handleError, function(){}, finish);	
		}

		/*  TBV stappenplan */
		function cleanMutatieEnv (handleStep, status, geturl, setLog, handleError) {
			var deferred = $q.defer();

			var clean_actuals_task = handleStep(status, "clean_actuals_M01", handleError);
			var clean_actuals_task = handleStep(status, "clean_actuals_M02", handleError);
			var copy_db_task = handleStep(status, "copy_db", handleError);
			var stop_task = handleStep(status, "stop_isc", handleError);
			
			$.when(copy_db_task)
				.done (function() {	
					var step1 = "run_afterburner";
					status(step1, STATUS_RUN);
					console.log("voer " + step1 + " uit");
					$.ajax({
						url: geturl(step1),
						error: function(xhr) {
							handleError(step1, xhr);
						},
						success: function(data) {
							status(step1, STATUS_RUN);
							setLog(step1, data);
							console.log(step1 + " uitgevoerd");
						}
					});
				});
						
			$.when(stop_task)
				.done(function() {
				$.when(copy_db_task)
					.done (function() {	
						var step1 = "start_isc";
						status(step1, STATUS_RUN);
						console.log("voer " + step1 + " uit");
						$.ajax({
							url: geturl(step1, '', DEFAULT_PASSWORD),
							error: function(xhr) {
								handleError(step1, xhr);
							},
							success: function(data) {
								setLog(step1, data);
								status(step1, STATUS_RUN);
								console.log(step1 + " uitgevoerd");
								deferred.resolve();
							}
						});
					});
				});
			
			return deferred.promise;
		}

		function cleanIVEnv (handleStep, status, geturl, setLog) {
			var deferred = $q.defer();
			var stop_iv_task = handleStep(status, "stop_iv", handleError);
			var clean_actuals_task = handleStep(status, "clean_actuals_iv", handleError);
			var clean_actuals_task_tc = handleStep(status, "clean_actuals_tc", handleError);

			$.when(stop_iv_task)
				.done(function() {
				var step4 = "start_iv";
				console.log("voer " + step4 + " uit");
				status(step4, STATUS_RUN);
				$.ajax({
					url: geturl(step4, '', DEFAULT_PASSWORD),
					error: function(xhr) {
						handleError(step4, xhr);
					},
					success: function(data) {
						setLog(step4, data);
						status(step4, STATUS_RUN);
						console.log(step4 + " uitgevoerd");
						deferred.resolve();
					}
				});
			});
			
			return deferred.promise;
		}

		return {
			geturl : geturl,
			startIV : startIV,
			doIV : doIV,
			doTC : doTC,
			startTC : startTC,
			cleanIVEnv : cleanIVEnv,
			cleanMutatieEnv : cleanMutatieEnv,
			doMutaties : doMutaties,
			getAllResults : getAllResults,
			getQueryResults : getQueryResults,
			getLO3Results : getLO3Results,
			getBRPResults : getBRPResults,
			getIVResults : getIVResults,
			getTCResults : getTCResults,
			getdummyurl : getdummyurl,
			mergeResults : mergeResults
		}
	}]);