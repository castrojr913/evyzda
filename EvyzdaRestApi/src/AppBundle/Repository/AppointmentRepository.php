<?php

namespace AppBundle\Repository;

/**
 * AppointmentRepository
 *
 * This class was generated by the Doctrine ORM. Add your own custom
 * repository methods below.
 */
class AppointmentRepository extends \Doctrine\ORM\EntityRepository {

  public function findAppointments() {
      return $this->createQueryBuilder("o")
                  ->leftJoin("o.patient", "p")->addSelect("p")
                  ->getQuery()->getResult(\Doctrine\ORM\Query::HYDRATE_ARRAY) ;
  }

}
