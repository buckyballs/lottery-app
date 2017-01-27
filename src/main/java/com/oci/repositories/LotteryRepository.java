package com.oci.repositories;

import com.oci.domain.Lottery;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by maqsoodi on 1/26/2017.
 */
public interface LotteryRepository extends CrudRepository<Lottery, Integer> {
}
