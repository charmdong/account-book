package com.accountbook.domain.repository;

import java.util.List;

import com.accountbook.domain.entity.Asset;

public interface AssetRepositoryCustom {

    List<Asset> findByUserId(String userId);
}
