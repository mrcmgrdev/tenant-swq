package com.example.fh3.tenant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TenantServiceTest {

  @Mock private TenantRepository tenantRepository;

  @InjectMocks private TenantService tenantService;

  @Test
  void create_shouldSaveTenantWithGivenName() {
    Tenant saved = new Tenant("My Company");
    saved.setId(1L);
    when(tenantRepository.save(any(Tenant.class))).thenReturn(saved);

    Tenant result = tenantService.create("My Company");

    assertThat(result.getName()).isEqualTo("My Company");
    assertThat(result.getId()).isEqualTo(1L);
    verify(tenantRepository).save(any(Tenant.class));
  }
}
