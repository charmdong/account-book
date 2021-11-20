package com.accountbook.service;

import java.time.LocalDateTime;
import java.util.List;

import com.accountbook.domain.entity.User;
import com.accountbook.domain.enums.AssetType;
import com.accountbook.dto.AssetDto;
import com.accountbook.dto.AssetRequest;
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

    // asset list 조회
    @Test
    public void getAssetList() {
        String userId = "test";
        List<AssetDto> assetList = assetService.getAssetList(userId);

        for (AssetDto asset : assetList) {
            log.info(asset.toString());
        }
    }

    // asset 상세 조회
    @Test
    public void getAsset(){
        long assetSeq = 13;
        AssetDto asset = assetService.getAsset(assetSeq);

        log.info(asset.toString());
    }

    // asset 등록
    @Test
    @Rollback(value = false)
    public void registAsset(){
        LocalDateTime localDateTime = LocalDateTime.now();
        UserRequest userRequest = new UserRequest("test", "password", "Mingeon", "mingeon@kakao.com", localDateTime);
        User user = User.createUser(userRequest);
        AssetRequest assetRequest = new AssetRequest("월급", "regist test", 20000L, AssetType.BANK, user, true, localDateTime, localDateTime, localDateTime, true);

        assetService.registAsset(assetRequest);
    }

    // asset 수정
    @Test
    @Rollback(value = false)
    public void updateAseet(){
        long assetSeq = 13;
        LocalDateTime localDateTime = LocalDateTime.now();
        UserRequest userRequest = new UserRequest("test", "password", "Mingeon", "mingeon@kakao.com", localDateTime);
        User user = User.createUser(userRequest);
        AssetRequest assetRequest = new AssetRequest("월급 2", "update test", 20000L, AssetType.BANK, user, true, localDateTime, localDateTime, localDateTime, true);

        assetService.updateAseet(assetSeq, assetRequest);
    }

    // asset 삭제
    @Test
    @Rollback(value = false)
    public void removeAsset(){
        long assetSeq = 1;
        assetService.removeAsset(assetSeq);
    }

}
