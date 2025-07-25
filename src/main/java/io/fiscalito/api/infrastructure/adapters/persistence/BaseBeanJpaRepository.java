package io.fiscalito.api.infrastructure.adapters.persistence;

import io.fiscalito.api.infrastructure.adapters.entity.BaseBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseBeanJpaRepository<T extends BaseBean> extends JpaRepository<T, String> {
}
