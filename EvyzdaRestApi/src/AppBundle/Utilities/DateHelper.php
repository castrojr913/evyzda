<?php

namespace AppBundle\Utilities;

/**
 * Created: 3/5/18.
 * Author: jesus.castro
 */
class DateHelper {

  public static $DEFAULT_TIME_ZONE = 'America/Bogota';
  public static $DF_YMD_HM = 'Y-m-d H:i';
  public static $DF_YMD = 'Y-m-d';

  function parseDateTime($dateString, $includeTZ) {
    $dateTime = new \DateTime($dateString);
    if($includeTZ) $dateTime->setTimezone(new \DateTimeZone(self::$DEFAULT_TIME_ZONE));
    return $dateTime;
  }

}
