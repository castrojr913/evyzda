<?php

namespace AppBundle\Utilities;

use \SplFileObject;

/**
 * Created: 3/4/18.
 * Author: jesus.castro
 */
class FileHelper {


    // TODO Change correct path

    private static $FILES_DIR = 'path_absolute_to_log_file';

    public static function readFile($fileName) {
        $file = new SplFileObject(self::$FILES_DIR . $fileName);
        $result = '';
        while (!$file->eof()) {
            $result .= $file->fgets();
        }
        return $result;
    }

    public static function writeFile($fileName, $text) {
        $file = new SplFileObject(self::$FILES_DIR . $fileName, "w");
        return $file->fwrite($text);
    }

    function getPHPExecutableFromPath() {
      // http://stackoverflow.com/questions/3889486/how-to-get-the-path-of-the-php-bin-from-php
      $paths = explode(PATH_SEPARATOR, getenv('PATH'));
      foreach ($paths as $path) {
        // we need this for XAMPP (Windows)
        if (strstr($path, 'php.exe') && isset($_SERVER["WINDIR"]) && file_exists($path) && is_file($path)) {
            return $path;
        } else {
            $php_executable = $path . DIRECTORY_SEPARATOR . "php" . (isset($_SERVER["WINDIR"]) ? ".exe" : "");
            if (file_exists($php_executable) && is_file($php_executable)) {
               return $php_executable;
            }
        }
      }
      return FALSE; // not found
    }

}
