<?php

namespace AppBundle\Utilities;

use Symfony\Component\HttpFoundation\JsonResponse;

/**
 * Created: 3/5/18.
 * Author: jesus.castro
 */
class ResponseHelper {

  public static function buildDefaultResponse() {
    return self::buildResponse(array("message" => "ok"), 200);
  }

  public static function buildResponse($array, $status) {
    return new JsonResponse($array, $status);
  }

}
