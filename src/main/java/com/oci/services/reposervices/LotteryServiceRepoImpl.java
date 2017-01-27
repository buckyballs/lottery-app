package com.oci.services.reposervices;

import com.oci.domain.Lottery;
import com.oci.repositories.LotteryRepository;
import com.oci.services.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Created by maqsoodi on 1/26/2017.
 */
@Service
@Profile("springdatajpa")
public class LotteryServiceRepoImpl implements LotteryService {

    private LotteryRepository lotteryRepository;
    private Lottery lottery;

    @Autowired
    public void setLotteryRepository(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    @Autowired
    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
    }

    @Override
    public Lottery getById(Integer id) {
        return lotteryRepository.findOne(id);
    }

    @Override
    public Lottery saveOrUpdate(Lottery domainObject) {
        return lotteryRepository.save(domainObject);
    }

}
