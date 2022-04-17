package com.accountbook.domain.repository.setting;

import com.accountbook.domain.entity.CustomSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * CustomSettingRepository
 *
 * @author donggun
 * @since 2022/04/12
 */
@Repository
public interface CustomSettingRepository extends JpaRepository<CustomSetting, String>, CustomSettingCustom {

    Optional<CustomSetting> findById(String userId);

}
