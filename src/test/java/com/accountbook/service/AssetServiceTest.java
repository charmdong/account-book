package com.accountbook.service;

import java.time.LocalDateTime;
import java.util.List;

import com.accountbook.domain.entity.User;
import com.accountbook.domain.enums.AssetType;
import com.accountbook.domain.repository.user.UserRepository;
import com.accountbook.dto.asset.AssetDto;
import com.accountbook.dto.asset.AssetRequest;
import com.accountbook.dto.user.UserRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class AssetServiceTest {

    @Autowired
    AssetService assetService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    // asset list 조회
    @Test
    public void getAssetList() throws Exception {
        String userId = "test";
        List<AssetDto> assetList = assetService.getAssetList(userId);

        for (AssetDto asset : assetList) {
            log.info(asset.toString());
        }
    }

    // asset 상세 조회
    @Test
    public void getAsset() throws Exception{
        long assetSeq = 13;
        AssetDto asset = assetService.getAsset(assetSeq);

        log.info(asset.toString());
    }

    // asset 등록
    @Test
    @Rollback(value = false)
    public void registAsset() throws Exception{
        LocalDateTime localDateTime = LocalDateTime.now();
        UserRequest userRequest = new UserRequest("test", "password", "Mingeon", "mingeon@kakao.com", localDateTime);
        User user = User.createUser(userRequest);
        userService.addUser(userRequest);
        user = userRepository.findById("test").orElseThrow();

        AssetRequest assetRequest = new AssetRequest("월급", "regist test", 20000L, AssetType.BANK, user, true, localDateTime, localDateTime, localDateTime, true);
        assetService.registAsset(assetRequest);

        var asset = assetService.getAssetList("test");
        log.info(" >> regist asset : {}",asset.toString());
    }

    // asset 수정
    @Test
    @Rollback(value = false)
    public void updateAseet() throws Exception{
        LocalDateTime localDateTime = LocalDateTime.now();
        User user = userRepository.findById("test").orElseThrow();

        AssetRequest assetRequest = new AssetRequest("월급 3", "update test", 20000L, AssetType.BANK, user, true, localDateTime, localDateTime, localDateTime, true);

        AssetDto updatedAsset = assetService.updateAseet(1, assetRequest);
        log.info(" >> updatedAsset : {}", updatedAsset.toString());
    }

    // asset 삭제
    @Test
    @Rollback(value = false)
    public void removeAsset() throws Exception{
        long assetSeq = 1;
        Boolean result = assetService.removeAsset(assetSeq);
        log.info(" >> removeAsset : {} ", result);
    }

}
