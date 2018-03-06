<?php

namespace AppBundle\Utilities;

use AppBundle\Utilities\FileHelper;
use AppBundle\Utilities\DateHelper;

/**
 * Created: 3/4/18.
 * Author: jesus.castro
 */
class LogHelper {

    private static $LOG_FILE = 'log.txt';
    private static $LOG_DATE_FORMAT = 'Y/m/d H:i:s';

    public static function log($text, $source) {
        $dt = DateHelper::parseDateTime("now", true);
        $dt->setTimestamp(time()); //adjust the object to correct timestamp
        $logDate = $dt->format(self::$LOG_DATE_FORMAT);
        $oldText = FileHelper::readFile(self::$LOG_FILE);
        $line = $oldText . "\n" . $logDate . " => " . (isset($source) ? $source . " : " . $text : $text);
        FileHelper::writeFile(self::$LOG_FILE, $line);
    }

    public static function logClass($text, $class) {
        self::log($text, get_class($class));
    }

}
