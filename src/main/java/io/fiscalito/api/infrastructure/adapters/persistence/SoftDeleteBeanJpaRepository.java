package io.fiscalito.api.infrastructure.adapters.persistence;

import io.fiscalito.api.infrastructure.adapters.entity.SoftDeleteBean;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SoftDeleteBeanJpaRepository<T extends SoftDeleteBean> extends BaseBeanJpaRepository<T> {
}
