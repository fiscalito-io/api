package online.tufactura.api.infrastructure.adapters.persistence;

import online.tufactura.api.infrastructure.adapters.entity.SoftDeleteBean;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SoftDeleteBeanJpaRepository<T extends SoftDeleteBean> extends BaseBeanJpaRepository<T> {
}
