package com.accountbook.domain.repository.setting;

import com.accountbook.domain.entity.CustomSetting;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class CustomSettingCustomImpl implements CustomSettingCustom {

    private final EntityManager em;

    @Override
    public void addSetting (CustomSetting setting) {
        em.persist(setting);
    }
}
