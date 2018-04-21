/**
 * Created by zhaolong on 18/1/10
 */

module.exports = function(grunt) {
    var path = {
        src: './src/main/webapp/WEB-INF/resources/',
        templateSrc: 'src/main/webapp/WEB-INF/views/',
        dest: './src/main/webapp/WEB-INF/resources/release',
        tmp: '.tmp'
    };
    var requireJsModules = [];
    var rev_code = Date.now();
    console.log(requireJsModules);
    // Project configuration.
    grunt.initConfig({
        path: path,
        clean: {
            beforebuild: {
                files: [{
                    src: [
                        '<%= path.dest %>/',
                        '<%= path.tmp %>/',
                        '<%= adminPath.dest %>/',
                        '<%= adminPath.tmp %>/'
                    ]
                }]
            },
            afterbuild: {
                files: [{
                    src: ['<%= path.dest %>/libs',
                        '<%= path.dest %>/new.css',
                        '<%= path.dest %>/css',
                        '<%= path.tmp %>/'
                    ]
                }]
            }
        },
        sprite: {
            list: {
                src: ['src/main/webapp/static/user/images/icons/*.png'],
                dest: 'src/main/webapp/static/user/images/dxs_icons.png',
                destCss: 'src/main/webapp/static/user/css/dxs_icons.css',
                padding: 2
            }
        },
        less: {
            development: {
                options: {
                    // compress: true,
                    yuicompress: true,
                    optimization: 2
                },
                files: {
                    "<%= path.dest %>/css/project.css": "<%= path.src %>/css/project.less",
                    "<%= path.dest %>/css/user.css": "<%= path.src %>/css/user.less",
                    "<%= path.dest %>/css/admin.css": "<%= path.src %>/css/admin.less"
                }
            }
        },
        preprocess: {
            options: {
                context: {
                    NODE_ENV: 'production',
                    VER: rev_code
                }
            },
            js: {
                src: 'src/main/webapp/static/user/release/js/config.js',
                dest: 'src/main/webapp/static/user/release/js/config.js'
            },
            head: {
                src: '<%= path.templateSrc %>/common/head.ftl',
                dest: '<%= path.templateSrc %>/common/head.ftl'
            },
            foot: {
                src: '<%= path.templateSrc %>/common/footer.ftl',
                dest: '<%= path.templateSrc %>/common/footer.ftl'
            }
        },
        copy: {
            build: {
                files: [{
                    expand: true,
                    cwd: 'src/main/webapp/static/user/images/',
                    src: ['**/**.png', '**/**.jpg', '**/**.gif'],
                    dest: '<%= path.dest %>/images'
                }, {
                    expand: true,
                    cwd: 'src/main/webapp/static/user/js/',
                    src: ['**/**.js'],
                    dest: '<%= path.dest %>/js'
                }, {
                    expand: true,
                    cwd: 'src/main/webapp/static/user/css/',
                    src: ['**/*.css', '**/**.png', '**/**.jpg', '**/**.gif'],
                    dest: '<%= path.dest %>/css'
                }, {
                    expand: true,
                    cwd: 'src/main/webapp/static/user/font/',
                    src: ['**/*'],
                    dest: '<%= path.dest %>/font'
                }, {
                    expand: true,
                    cwd: 'src/main/webapp/static/admin/images/',
                    src: ['**/**.png', '**/**.jpg', '**/**.gif'],
                    dest: '<%= adminPath.dest %>/images'
                }, {
                    expand: true,
                    cwd: 'src/main/webapp/static/admin/js/',
                    src: ['**/**.js'],
                    dest: '<%= adminPath.dest %>/js'
                }, {
                    expand: true,
                    cwd: 'src/main/webapp/static/admin/css/',
                    src: ['**/*.css', '**/**.png', '**/**.jpg', '**/**.gif'],
                    dest: '<%= adminPath.dest %>/css'
                }, {
                    expand: true,
                    cwd: 'src/main/webapp/static/admin/fonts/',
                    src: ['**/*'],
                    dest: '<%= adminPath.dest %>/fonts'
                }]
            }
        },
        rev: {
            options: {
                algorithm: 'md5',
                length: 8
            },
            build: {
                files: {
                    src: ['<%= path.dest %>/**/*.{js,css}', '<%= adminPath.dest %>/**/*.{js,css}']
                }
            }
        },
        usemin: {

            html: 'src/main/webapp/WEB-INF/views/*.ftl',

            options: {

                blockReplacements: {
                    css: function(block) {
                        return '<link rel="stylesheet" href="' +
                            block.dest + '?ver=' + rev_code +'" />'; //此处为css标签的定制
                    }
                }
            }

        },
        requirejs: {
            compile: {
                options: {
                    baseUrl: 'src/main/webapp/static/user/release/js',
                    mainConfigFile: "src/main/webapp/static/user/release/js/config.js",
                    name: "main", // assumes a production build using almond
                    out: "src/main/webapp/static/user/release/js/main.js"

                }
            }
        },
        concat: {
            dist: {
                src: ['<%= path.tmp %>/**/*.css'],
                dest: 'src/main/webapp/static/user/release/css/new.css'
            }
        },
        cssmin: {
            options: {
                shorthandCompacting: false,
                roundingPrecision: -1,
                report: 'gzip'
            },
            user: {
                files: [{
                    expand: true,
                    cwd: 'src/main/webapp/static/user/release/css/',
                    src: ['*.css'],
                    dest: 'src/main/webapp/static/user/release/css/'
                }]
            },
            adminTarget: {
                files: [{
                    expand: true,
                    cwd: 'src/main/webapp/static/admin/release/css/',
                    src: ['*.css'],
                    dest: 'src/main/webapp/static/admin/release/css/'
                }]
            }
        },
        uglify: {
            user: {
                files: [{
                    expand: true,
                    cwd: 'src/main/webapp/static/user/release/js',
                    src: '**/*.js',
                    dest: 'src/main/webapp/static/user/release/js'
                }]
            },
            admin: {
                files: [{
                    expand: true,
                    cwd: 'src/main/webapp/static/admin/release/js',
                    src: '**/*.js',
                    dest: 'src/main/webapp/static/admin/release/js'
                }]
            }
        },
        replace: {
            beforeBuild: {
                options: {
                    patterns: [
                        {
                            match: /\/static\/user\/([^']*)'/g,
                            replacement: '/static/user/release/$1\''
                        },
                        {
                            match: /\/((js|css)\/[^']*)'/g,
                            replacement: '/$1?ver=' + rev_code + '\''
                        }
                    ]
                },
                files: [
                    {
                        expand: true,
                        flatten: true,
                        src: ['<%= path.templateSrc %>/lib/config.ftl'],
                        dest: '<%= path.templateSrc %>/lib/'
                    },
                    {
                        expand: true,
                        flatten: true,
                        src: ['<%= adminPath.templateSrc %>/lib/config.ftl'],
                        dest: '<%= adminPath.templateSrc %>/lib/'
                    }
                ]
            },
            pre: {
                options: {
                    patterns: [
                        {
                            match: /\<\@utilHelper\.hm environment="test"\>/g,
                            replacement: '<@utilHelper.hm environment="pre">'
                        }
                    ]
                },
                files: [
                    {
                        expand: true,
                        flatten: true,
                        src: ['<%= path.templateSrc %>/common/head.ftl'],
                        dest: '<%= path.templateSrc %>/common/'
                    }
                ]
            },
            online: {
                options: {
                    patterns: [
                        {
                            match: /\<\@utilHelper\.hm environment="test"\>/g,
                            replacement: '<@utilHelper.hm environment="online">'
                        }
                    ]
                },
                files: [
                    {
                        expand: true,
                        flatten: true,
                        src: ['<%= path.templateSrc %>/common/head.ftl'],
                        dest: '<%= path.templateSrc %>/common/'
                    }
                ]
            },
            images: {
                options: {
                    patterns: [
                        {
                            match: /\/static\/user\/([^"]*)"/g,
                            replacement: '/static/user/release/$1?ver=' + rev_code + '\"'
                        },
                        {
                            match: /\/static\/admin\/([^"]*)"/g,
                            replacement: '/static/admin/release/$1?ver=' + rev_code + '\"'
                        }
                    ]
                },
                files: [
                    {
                        expand: true,
                        flatten: true,
                        src: ['<%= adminPath.templateSrc %>/*.ftl'],
                        dest: '<%= adminPath.templateSrc %>/'
                    }
                ]
            }
        },
        watch:
        {
            css:
                {
                    files: ['src/main/webapp/WEB-INF/resources/css/device/*.less','src/main/webapp/WEB-INF/resources/css/user/*.less','src/main/webapp/WEN-INF/resources/css/*.less'],
                    tasks: ['less'],
                    options: {livereload:false}
                }
        }
    });



    // 加载插件
    require('load-grunt-tasks')(grunt);

    grunt.registerTask('image', ['sprite']);
    grunt.registerTask('clone', ['copy']);
    grunt.registerTask('les', ['less']);
    grunt.registerTask('cat', ['concat:dist']);
    grunt.registerTask('watcher',['watch']);

    //ces-user打包脚本任务
    grunt.registerTask('default', ['clean:beforebuild', 'copy',
        'less', 'cssmin', 'preprocess', 'uglify', 'replace:beforeBuild', 'replace:online', 'replace:images']);

    // 默认被执行的任务列表。
    /*grunt.registerTask('list',['sprite:list']);
     grunt.registerTask('require',['requirejs']);
     grunt.registerTask('default2', ['clean:beforebuild', 'less', 'copy:build',  'useminPrepare',
     'concat', 'cssmin', 'uglify','preprocess', 'rev', 'usemin', 'clean:afterbuild']);*/

};