// Gulp
var gulp = require('gulp');
var exec = require('child_process').exec;
var karma = require('karma').server;

// Plugins
var connect = require('gulp-connect');
var jshint = require('gulp-jshint');
var uglify = require('gulp-uglify');
var minifyCSS = require('gulp-minify-css');
var rm = require('gulp-rm');

// Taken
gulp.task('frontend-dep', function () {
    gulp.src([
        './node_modules/bootstrap/dist/css/bootstrap.min.css',
        './node_modules/bootstrap/dist/css/bootstrap-theme.min.css',
        './node_modules/moment/min/moment.min.js',
        './node_modules/angular/angular.min.js',
        './node_modules/angular-resource/angular-resource.min.js',
        './node_modules/angular-route/angular-route.min.js',
        './node_modules/angular-local-storage/dist/angular-local-storage.min.js',
        './node_modules/angular-bootstrap/ui-bootstrap-tpls.min.js',
        './node_modules/jquery/dist/jquery.min.js',
        './node_modules/bootstrap/dist/js/bootstrap.min.js',
        './node_modules/bootstrap/dist/fonts/glyphicons-halflings-regular.eot',
        './node_modules/bootstrap/dist/fonts/glyphicons-halflings-regular.svg',
        './node_modules/bootstrap/dist/fonts/glyphicons-halflings-regular.ttf',
        './node_modules/bootstrap/dist/fonts/glyphicons-halflings-regular.woff',
        './node_modules/bootstrap/dist/fonts/glyphicons-halflings-regular.woff2',
        './node_modules/angular-loading-bar/build/loading-bar.min.css',
        './node_modules/angular-loading-bar/build/loading-bar.min.js',
        './node_modules/angular-ui-select/select.min.js',
        './node_modules/angular-ui-select/select.min.css',
        './node_modules/angular-sanitize/angular-sanitize.min.js'
    ], {base: 'node_modules'}).pipe(gulp.dest('./app/ea/'));
});

gulp.task('datetimepicker', function () {
    gulp.src([
        './node_modules/angular-bootstrap-datetimepicker/src/css/datetimepicker.css',
        './node_modules/angular-bootstrap-datetimepicker/src/js/datetimepicker.js'
    ], {base: 'node_modules/angular-bootstrap-datetimepicker/src'}).pipe(gulp.dest('./app/ea/angular-bootstrap-datetimepicker/'))
});

gulp.task('lint', function () {
    gulp.src(['./app/**/*.js', '!./app/ea/**'])
        .pipe(jshint())
        .pipe(jshint.reporter('default'))
        .pipe(jshint.reporter('fail'));
});

gulp.task('clean', function () {
    gulp.src(['./dist/**/*'], {read: false})
        .pipe(rm({async: false}));
});

gulp.task('minify-css', function () {
    var opts = {comments: true, spare: true};
    gulp.src(['./app/**/*.css', '!./app/ea/**'])
        .pipe(minifyCSS(opts))
        .pipe(gulp.dest('./dist/'))
});

gulp.task('minify-js', function () {
    gulp.src(['./app/**/*.js', '!./app/ea/**'])
        .pipe(uglify({
            // inSourceMap:
            // outSourceMap: "app.js.map"
        }))
        .pipe(gulp.dest('./dist/'))
});

gulp.task('copy-ea-components', function () {
    gulp.src('./app/ea/**')
        .pipe(gulp.dest('dist/ea'));
});

gulp.task('copy-html-files', function () {
    gulp.src('./app/**/*.html')
        .pipe(gulp.dest('dist/'));
});

gulp.task('copy-fonts', function () {
    gulp.src('./app/fonts/**')
        .pipe(gulp.dest('dist/fonts'));
});

gulp.task('copy-img', function () {
    gulp.src('./app/img/**')
        .pipe(gulp.dest('dist/img'));
});

gulp.task('test', function (done) {
    karma.start({
        configFile: __dirname + '/brpbeheer.conf.js',
        singleRun: true
    }, function () {
        done();
    });
});

gulp.task('connect', function () {
    connect.server({
       root: 'app/',
       port: 8888
    });
});

gulp.task('mvninstall', function (cb) {
    exec('mvn clean install -Pgulp', function (err, stdout, stderr) {
        console.log(stdout);
        console.log(stderr);
        cb(err);
    });
});

gulp.task('connectDist', function () {
    connect.server({
        root: 'dist/',
        port: 9999
    });
});

// default task
gulp.task('default',
    ['frontend-dep', 'datetimepicker', 'lint', 'connect']
);

// build task
gulp.task('build',
    ['frontend-dep', 'datetimepicker', 'lint', 'minify-css', 'minify-js', 'copy-html-files', 'copy-ea-components', 'copy-fonts', 'copy-img']
);

gulp.task('cleaningapiproject', function () {
    gulp.src([
        '../beheer-api/src/main/webapp/css/**/*',
        '../beheer-api/src/main/webapp/ea/**/*',
        '../beheer-api/src/main/webapp/fields/**/*',
        '../beheer-api/src/main/webapp/fonts/**/*',
        '../beheer-api/src/main/webapp/img/**/*',
        '../beheer-api/src/main/webapp/js/**/*',
        '../beheer-api/src/main/webapp/views/**/*',
        '../beheer-api/src/main/webapp/index.html'
    ], {read: false})
        .pipe(rm({async: false}));
});

gulp.task('cleancommit', ['cleaningapiproject', 'frontend-dep', 'datetimepicker', 'lint', 'test'], function (done) {
    done();
});

gulp.task('precommit', ['cleancommit'], function () {
    return gulp.src(['./app/**/*', './app/*.html', '!./app/conf/**'])
        .pipe(gulp.dest('../beheer-api/src/main/webapp'));
});

gulp.task('commit', ['cleancommit', 'precommit']);
