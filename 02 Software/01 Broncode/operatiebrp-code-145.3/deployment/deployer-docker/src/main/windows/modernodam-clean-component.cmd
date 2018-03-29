set docker_host=
set docker_tls_verify=
set docker_cert_path=
set docker_machine_name=

set docker_host=test-sync-init-dok.modernodam.nl
call docker-clean.cmd

set docker_host=test-sync-sync-dok.modernodam.nl
call docker-clean.cmd

set docker_host=test-voisc-dok.modernodam.nl
call docker-clean.cmd

set docker_host=test-isc-dok.modernodam.nl
call docker-clean.cmd

set docker_host=test-isc-beheer-dok.modernodam.nl
call docker-clean.cmd

set docker_host=test-brp-dok.modernodam.nl
call docker-clean.cmd

set docker_host=test-brp-beheer-dok.modernodam.nl
call docker-clean.cmd

set docker_host=test-iv-logging-dok.modernodam.nl
call docker-clean.cmd

set docker_host=test-iv-naarbrp-dok.modernodam.nl
call docker-clean.cmd
