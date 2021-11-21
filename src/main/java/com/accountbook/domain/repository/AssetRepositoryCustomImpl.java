package com.accountbook.domain.repository;

import java.util.List;

import com.accountbook.domain.entity.Asset;
import com.accountbook.domain.entity.QAsset;
import com.accountbook.domain.entity.QUser;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class AssetRepositoryCustomImpl extends QuerydslRepositorySupport implements AssetRepositoryCustom {
    
    public AssetRepositoryCustomImpl() {
        super(Asset.class);
    }

    @Override
    public List<Asset> findByUserId(String userId) {
        QAsset asset = QAsset.asset;
        QUser user = QUser.user;

        return from(asset)
                .innerJoin(asset.user,user)
                .fetchJoin()
                .where(
                    user.id.eq(userId))
                .fetch();
    }
}
