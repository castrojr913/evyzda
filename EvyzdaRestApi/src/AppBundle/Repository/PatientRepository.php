<?php

namespace AppBundle\Repository;

/**
 * PatientRepository
 *
 * This class was generated by the Doctrine ORM. Add your own custom
 * repository methods below.
 */
class PatientRepository extends \Doctrine\ORM\EntityRepository {

  public function findPatients() {
    return $this->createQueryBuilder("o")
                ->getQuery()->getResult(\Doctrine\ORM\Query::HYDRATE_ARRAY) ;
  }

}
