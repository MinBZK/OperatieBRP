const gulp = require('gulp');
const zip = require('gulp-zip');
const deployer = require('nexus-deployer');
const packageJson = require('./package.json');

gulp.task('zip', () =>
    gulp.src('dist/*')
        .pipe(zip('dist.zip'))
        .pipe(gulp.dest('dist'))
);

gulp.task('deploy', ['zip'], function(callback) {

    var snapshot = {
        groupId: 'nl.bzk.brp.beheerng2',
        artifactId: 'beheer-ng2',
        version: packageJson.version,
        packaging: 'zip',
        auth: {
            username:'admin',
            password:'admin123'
        },
        pomDir: 'pom',
        url: 'https://www.modernodam.nl/nexus3/repository/releases',
        artifact: 'dist/dist.zip',
        noproxy: 'localhost',
        cwd: '',
        quiet: false,
        insecure: true
    };

    deployer.deploy(snapshot, callback);

});