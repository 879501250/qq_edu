DROP TABLE IF EXISTS `crm_banner`;
CREATE TABLE `crm_banner`
(
    `id` CHAR(19) Binary DEFAULT '' NOT NULL,
    `title` VARCHAR(20) DEFAULT '',
    `image_url` VARCHAR(500) DEFAULT '' NOT NULL,
    `link_url` VARCHAR(500) DEFAULT '',
    `sort` INTEGER DEFAULT 0 NOT NULL,
    `is_deleted` INTEGER DEFAULT 0 NOT NULL,
    `gmt_create` DATETIME(3) NOT NULL,
    `gmt_modified` DATETIME(3) NOT NULL,
    PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS `edu_article`;
CREATE TABLE `edu_article`
(
    `id` CHAR(19) Binary DEFAULT '' NOT NULL,
    `title` VARCHAR(100) DEFAULT '' NOT NULL,
    `author` VARCHAR(50) DEFAULT '' NOT NULL,
    `content` MEDIUMTEXT,
    `view_count` BIGINT DEFAULT 0 NOT NULL,
    `introduction` MEDIUMTEXT,
    `comment_count` INTEGER DEFAULT 0 NOT NULL,
    `like_count` INTEGER DEFAULT 0 NOT NULL,
    `is_deleted` INTEGER DEFAULT 0,
    `gmt_create` DATETIME(3) NOT NULL,
    `gmt_modified` DATETIME(3) NOT NULL,
    PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS `edu_article_user`;
CREATE TABLE `edu_article_user`
(
    `id` CHAR(19) DEFAULT '',
    `article_id` CHAR(19) DEFAULT '',
    `user_id` CHAR(19) DEFAULT ''
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS `edu_chapter`;
CREATE TABLE `edu_chapter`
(
    `id` CHAR(19) Binary DEFAULT '' NOT NULL,
    `course_id` CHAR(19) DEFAULT '' NOT NULL,
    `title` VARCHAR(50) DEFAULT '' NOT NULL,
    `sort` INTEGER DEFAULT 0 NOT NULL,
    `gmt_create` DATETIME(3) NOT NULL,
    `gmt_modified` DATETIME(3) NOT NULL,
    PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS `edu_comment`;
CREATE TABLE `edu_comment`
(
    `id` CHAR(19) Binary DEFAULT '' NOT NULL,
    `parent_id` VARCHAR(19) DEFAULT '' NOT NULL,
    `user_id` VARCHAR(19) DEFAULT '' NOT NULL,
    `nickname` VARCHAR(50) DEFAULT '',
    `avatar` VARCHAR(255) DEFAULT '',
    `content` MEDIUMTEXT,
    `type` INTEGER DEFAULT 0,
    `is_deleted` INTEGER DEFAULT 0 NOT NULL,
    `gmt_create` DATETIME(3) NOT NULL,
    `gmt_modified` DATETIME(3) NOT NULL,
    PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS `edu_course`;
CREATE TABLE `edu_course`
(
    `id` CHAR(19) Binary DEFAULT '' NOT NULL,
    `subject_id` CHAR(19) DEFAULT '' NOT NULL,
    `subject_parent_id` CHAR(19) DEFAULT '' NOT NULL,
    `title` VARCHAR(50) DEFAULT '' NOT NULL,
    `price` DECIMAL(10, 2) DEFAULT 0.00 NOT NULL,
    `lesson_num` INTEGER DEFAULT 0 NOT NULL,
    `cover` VARCHAR(255) DEFAULT '' NOT NULL,
    `view_count` BIGINT DEFAULT 0 NOT NULL,
    `version` BIGINT DEFAULT 1 NOT NULL,
    `status` VARCHAR(10) DEFAULT 'Draft' NOT NULL,
    `is_deleted` INTEGER DEFAULT 0,
    `gmt_create` DATETIME(3) NOT NULL,
    `gmt_modified` DATETIME(3) NOT NULL,
    `comment_num` INTEGER DEFAULT 0 NOT NULL,
    PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS `edu_course_description`;
CREATE TABLE `edu_course_description`
(
    `id` CHAR(19) Binary DEFAULT '' NOT NULL,
    `description` MEDIUMTEXT,
    `gmt_create` DATETIME(3) NOT NULL,
    `gmt_modified` DATETIME(3) NOT NULL,
    PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS `edu_game`;
CREATE TABLE `edu_game`
(
    `id` CHAR(19) Binary DEFAULT '' NOT NULL,
    `title` VARCHAR(50) DEFAULT '' NOT NULL,
    `cover` VARCHAR(255) DEFAULT '' NOT NULL,
    `content` MEDIUMTEXT,
    `is_deleted` INTEGER DEFAULT 0,
    `gmt_create` DATETIME(3) NOT NULL,
    `gmt_modified` DATETIME(3) NOT NULL,
    PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS `edu_material`;
CREATE TABLE `edu_material`
(
    `id` CHAR(19) Binary DEFAULT '' NOT NULL,
    `title` VARCHAR(50) DEFAULT '' NOT NULL,
    `cover` VARCHAR(255) DEFAULT '' NOT NULL,
    `link` VARCHAR(255) DEFAULT '' NOT NULL,
    `introduction` MEDIUMTEXT,
    `type` INTEGER DEFAULT 0 NOT NULL,
    `is_abroad` INTEGER DEFAULT 0 NOT NULL,
    `is_deleted` INTEGER DEFAULT 0,
    `gmt_create` DATETIME(3) NOT NULL,
    `gmt_modified` DATETIME(3) NOT NULL,
    PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS `edu_subject`;
CREATE TABLE `edu_subject`
(
    `id` CHAR(19) Binary DEFAULT '' NOT NULL,
    `title` VARCHAR(50) DEFAULT '' NOT NULL,
    `parent_id` CHAR(19) DEFAULT '' NOT NULL,
    `sort` INTEGER DEFAULT 0 NOT NULL,
    `gmt_create` DATETIME(3) NOT NULL,
    `gmt_modified` DATETIME(3) NOT NULL,
    PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS `edu_video`;
CREATE TABLE `edu_video`
(
    `id` CHAR(19) Binary DEFAULT '' NOT NULL,
    `course_id` CHAR(19) DEFAULT '' NOT NULL,
    `chapter_id` CHAR(19) DEFAULT '' NOT NULL,
    `title` VARCHAR(100) DEFAULT '' NOT NULL,
    `video_source_id` VARCHAR(100) DEFAULT '',
    `video_original_name` VARCHAR(100) DEFAULT '',
    `sort` INTEGER DEFAULT 0 NOT NULL,
    `version` BIGINT DEFAULT 1 NOT NULL,
    `gmt_create` DATETIME(3) NOT NULL,
    `gmt_modified` DATETIME(3) NOT NULL,
    PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS `statistics_daily`;
CREATE TABLE `statistics_daily`
(
    `id` CHAR(19) Binary DEFAULT '' NOT NULL,
    `date_calculated` VARCHAR(20) DEFAULT '' NOT NULL,
    `register_num` INTEGER DEFAULT 0 NOT NULL,
    `login_num` INTEGER DEFAULT 0 NOT NULL,
    `video_view_num` INTEGER DEFAULT 0 NOT NULL,
    `article_view_num` INTEGER DEFAULT 0 NOT NULL,
    `gmt_create` DATETIME(3) NOT NULL,
    `gmt_modified` DATETIME(3) NOT NULL,
    PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `id` CHAR(19) Binary DEFAULT '' NOT NULL,
    `openid` VARCHAR(128) DEFAULT '',
    `mobile` VARCHAR(11) DEFAULT '',
    `password` VARCHAR(255) DEFAULT '',
    `nickname` VARCHAR(50) DEFAULT '',
    `sex` INTEGER DEFAULT 0,
    `age` INTEGER DEFAULT 0,
    `avatar` VARCHAR(255) DEFAULT '',
    `sign` VARCHAR(100) DEFAULT '',
    `is_disabled` INTEGER DEFAULT 0 NOT NULL,
    `is_deleted` INTEGER DEFAULT 0 NOT NULL,
    `gmt_create` DATETIME(3) NOT NULL,
    `gmt_modified` DATETIME(3) NOT NULL,
    PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

