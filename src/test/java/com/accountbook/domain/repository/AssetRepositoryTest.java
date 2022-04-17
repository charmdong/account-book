package com.accountbook.domain.repository;

import com.accountbook.domain.entity.User;
import com.accountbook.domain.enums.AssetType;
import com.accountbook.domain.repository.asset.AssetRepository;
import com.accountbook.domain.repository.user.UserRepository;
import com.accountbook.dto.asset.AssetRequest;
import com.accountbook.dto.user.UserCreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class AssetRepositoryTest {

    @Autowired
    AssetRepository assetRepository;

    @Autowired
    UserRepository userRepository;

    // asset 등록
    @Test
    @Rollback(value = false)
    public void registAsset() {
        LocalDateTime localDateTime = LocalDateTime.now();
        UserCreateRequest userRequest = new UserCreateRequest();

        userRequest.setId("test");
        userRequest.setPassword("password");
        userRequest.setName("Mingeon");
        userRequest.setEmail("mingeon@kakao.com");
        userRequest.setBirthDate(localDateTime);

        User user = User.createUser(userRequest);
        // userRepository.save(user);

        AssetRequest assetRequest = new AssetRequest("월급", "test", 20000L, AssetType.BANK, user, true, localDateTime, localDateTime, localDateTime, true);
        Asset asset = Asset.createAsset(assetRequest);

        assetRepository.save(asset);
        log.info("Add asset : " + asset.toString());
    }

    // assetlist 조회
    @Test
    public void getAssetList() {
        String userId = "test";
        List<Asset> assetList = assetRepository.findByUserId(userId);

        for (Asset asset : assetList) {
            log.info("Get asset list : " + asset.toString());
        }
    }

    // asset 상세 조회
    @Test
    public void getDetailAsset() {
        long assetSeq = 13;
        Asset asset = assetRepository.findById(assetSeq).orElseThrow();

        log.info("Get detail asset : " + asset.toString());
    }

    // asset 삭제
    @Test
    public void deleteAsset(){
        long assetSeq = 13;
        assetRepository.deleteById(assetSeq);

        log.info("seq : {} asset have been deleted.",assetSeq);
    }
}
