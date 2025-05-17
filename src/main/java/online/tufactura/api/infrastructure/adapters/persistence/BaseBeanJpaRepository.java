package online.tufactura.api.infrastructure.adapters.persistence;

import online.tufactura.api.infrastructure.adapters.entity.BaseBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseBeanJpaRepository<T extends BaseBean> extends JpaRepository<T, String> {
}
