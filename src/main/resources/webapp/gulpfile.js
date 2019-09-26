var gulp = require('gulp'); //引入本地安装的 gulp模块
var connect = require('gulp-connect'); //web模块
var del = require('del'); //删除模块
var minimist = require('minimist');

var releaseDir = './dist'; //发行版本目录

//1.默认服务
gulp.task('default', ['copy', 'webserver','watch'], function () {//default 为默认任务名，这种情况只需要在命令行中输入 gulp即可。 如果有特定的taskName,需要在命令行中实行 gulp taskName
});

//2.web服务
gulp.task('webserver', function () {
    connect.server({
        root: './dist',
        livereload: true,
        port: 1002
    });
});

//3.文件删除(依赖del和minimist插件)
//参数
var argv = require('minimist')(process.argv.slice(2), {
    default: {
        ver: 'all'
    }
});

gulp.task('clear', function (cb) {
    return del(['./' + (argv.rc ? 'rc' : 'dist') + '/*'], cb);
});


//4.复制服务
var task = {
    mv: function () {
        gulp.src('./src/**/*').pipe(gulp.dest(releaseDir));  //环境
        //gulp.src('./src/starter/**/*').pipe(gulp.dest(releaseDir)); //主页
        //gulp.src('./src/module/**/*').pipe(gulp.dest(releaseDir + '/module')); //项目
    }
}

gulp.task('copy', ['clear'], function () {
    for (var key in task) {
        task[key]();
    }
});

//5.文件修改监听服务
gulp.task('watch', function () {
    gulp.watch('src/**/*.*', ['copy']) // 监听文件修改，当文件被修改则执行copy任务
})