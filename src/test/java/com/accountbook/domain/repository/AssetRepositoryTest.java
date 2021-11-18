package com.accountbook.domain.repository;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.accountbook.domain.entity.Asset;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.enums.AssetType;
import com.accountbook.dto.AssetRequest;
import com.accountbook.dto.user.UserRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class AssetRepositoryTest {

    @Autowired
    AssetRepository assetRepository;

    @Autowired
    UserRepository userRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @Rollback(value = false)
    public void registAsset() {
        LocalDateTime localDateTime = LocalDateTime.now();
        UserRequest userRequest = new UserRequest("test", "password", "Mingeon", "mingeon@kakao.com", localDateTime);
        User user = User.createUser(userRequest);
        // userRepository.save(user);

        AssetRequest assetRequest = new AssetRequest("월급", "test", 20000L, AssetType.BANK, user, true, localDateTime, localDateTime, localDateTime, true);
        Asset asset = Asset.createAsset(assetRequest);

        assetRepository.save(asset);
        System.out.println("@@@@@@@@@@@@@ : " + asset.toString());
    }
}
