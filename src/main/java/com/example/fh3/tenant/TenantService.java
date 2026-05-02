package com.example.fh3.tenant;

import org.springframework.stereotype.Service;

@Service
public class TenantService {

  private final TenantRepository tenantRepository;

  public TenantService(TenantRepository tenantRepository) {
    this.tenantRepository = tenantRepository;
  }

  public Tenant create(String name) {
    Tenant tenant = new Tenant(name);
    return tenantRepository.save(tenant);
  }
}
